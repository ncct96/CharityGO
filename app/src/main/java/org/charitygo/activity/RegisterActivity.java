package org.charitygo.activity;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.internal.Storage;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import org.charitygo.DateFormat;
import org.charitygo.R;
import org.charitygo.model.StepHistory;
import org.charitygo.model.StepsRanking;
import org.charitygo.model.User;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class RegisterActivity extends AppCompatActivity{

    private EditText username;
//    private EditText password;
//    private EditText retypePassword;
    private RadioGroup gender;
    private RadioButton selectedGender;
    private RadioButton maleRadioBtn;
    private RadioButton femaleRadioBtn;
    private EditText email;
    private EditText contactNum;
    private FirebaseAuth fireAuth = FirebaseAuth.getInstance();
    private FirebaseUser currentUser = fireAuth.getCurrentUser();
    private DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
    private GoogleSignInClient googleSignClient;

    private DatabaseReference dataRefStore = ref.child("users");
    private DatabaseReference stepRefStore, rankRefStore;
    private FirebaseStorage storage = FirebaseStorage.getInstance();
    private StorageReference storageRef = storage.getReference();

    private ImageView imagetoUpload; private static final int RESULT_LOAD_IMAGE = 1;
    private Uri selectedImage; private String uploadURL;

    private String usern;
    private String phone;
    private String genStr;
    private String uriImage;

    private ProgressDialog progressDialog;

    private final static int GET_FROM_GALLERY = 8000;

    private static long timestamp = System.currentTimeMillis();
    private DateFormat df = new DateFormat();
    private String monthYearPath = String.valueOf(df.longToYearMonth(timestamp));
    private String dayDatePath = String.valueOf(df.longToYearMonthDay(timestamp));

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        progressDialog = new ProgressDialog(RegisterActivity.this);
        progressDialog.setTitle("Creating New Account");
        progressDialog.setMessage("Please Wait For A Moment");
        progressDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {

            }
        });

        Button signupBtn = (Button) findViewById(R.id.buttonReg);
        signupBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerAccount(v);
            }
        });

        email = findViewById(R.id.editEmail);
        username = findViewById(R.id.editUsername);
        contactNum = findViewById(R.id.editPhone);
        gender = (RadioGroup) findViewById(R.id.radioGroupGender);
        maleRadioBtn = (RadioButton) findViewById(R.id.radioButtonMale);
        femaleRadioBtn = (RadioButton) findViewById(R.id.radioButtonFemale);

        if(currentUser != null){
            email.setText(currentUser.getEmail());
        }

        imagetoUpload = findViewById(R.id.imageView);
        imagetoUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent galleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
//                startActivityForResult(galleryIntent, RESULT_LOAD_IMAGE);
                startActivityForResult(new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.INTERNAL_CONTENT_URI), GET_FROM_GALLERY);
            }
        });
    }

    public String getFileExtension(Uri uri){
        ContentResolver cR = getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cR.getType(uri));
    }

    public void uploadImageRegister(){
        final String photoPath = "images/"+System.currentTimeMillis()+"."+getFileExtension(selectedImage);
        final StorageReference storageImgRef = storageRef.child(photoPath);
        final UploadTask uploadTask = storageImgRef.putFile(selectedImage);
        uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Task<Uri> uriTask = storageImgRef.putFile(selectedImage).continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                    @Override
                    public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                        if(!task.isSuccessful()){
                            throw task.getException();
                        }
                        uploadURL = storageImgRef.getDownloadUrl().toString();
                        return storageImgRef.getDownloadUrl();
                    }
                }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                    @Override
                    public void onComplete(@NonNull Task<Uri> task) {
                        if(task.isSuccessful()){
                            uploadURL = task.getResult().toString();
                            //Store the user details entered
                            User newUser = new User(usern, currentUser.getEmail(), phone, genStr, uploadURL, photoPath, 0);
                            dataRefStore.child(currentUser.getUid()).setValue(newUser);

                            //Create new step history for newly registered user
                            String uid = currentUser.getUid();
                            StepHistory steps = new StepHistory(0, 0);
                            stepRefStore = ref.child("stepHistory");
                            stepRefStore.child(dayDatePath).child(uid).setValue(steps);
                          
                            //Create parent node to store user accumulate steps for every month
                            StepsRanking rank = new StepsRanking(0);
                            rankRefStore = ref.child("stepRanking");
                            rankRefStore.child(monthYearPath).child(uid).setValue(rank);

                            Intent intent = new Intent(getApplicationContext(), MainUI.class);
                            startActivity(intent);
                            Toast.makeText(RegisterActivity.this, "Successfully Registered !",Toast.LENGTH_LONG).show();
                        }

                    }
                });
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(RegisterActivity.this, "Image Unable To be Upload", Toast.LENGTH_SHORT).show();
            }
        }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                progressDialog.show();
            }
        });
    }

    public void registerAccount(View view) {
        boolean notValid = false;
        View focusView = null;
        usern = username.getText().toString();
        phone = contactNum.getText().toString();

        //Get the selected index of the radio group
        int gen = gender.getCheckedRadioButtonId();
        selectedGender = (RadioButton) findViewById(gen);

        View radiobtn = gender.findViewById(gen);
        int index = gender.indexOfChild(radiobtn);

        //Get Uri of Image
        uriImage = imagetoUpload.getTag().toString();

        //Get the text of radio button in radio group
        RadioButton gend = (RadioButton) gender.getChildAt(index);
        if(gend != null){
            genStr = gend.getText().toString();
        }

        if(uriImage.equals(null)){
            focusView = imagetoUpload;
            notValid = true;
        }

        //Contact Number Validations
        if(phone.isEmpty()){
            contactNum.setError("Please Enter Contact Number");
            focusView = contactNum;
            notValid = true;
        } else if(!phone.matches("[0-9]+")){
            contactNum.setError("Only Numbers Are Allowed");
            focusView = contactNum;
            notValid = true;
        } else if(phone.length() < 7){
            contactNum.setError("Too Short to be Contact Numbers");
            focusView = contactNum;
            notValid = true;
        }

        //Username Validations
        if(usern.equals("Admin")){
            username.setError("Username Taken, Please Try Other");
            focusView = username;
            notValid = true;
        } else if(TextUtils.isEmpty(usern)){
            username.setError("Please Enter Username");
            focusView = username;
            notValid = true;
        } else if(usern.contains(" ")){
            username.setError("Username Cannot Contain Blank Space");
            focusView = username;
            notValid = true;
        } else if(usern.length() <= 5){
            username.setError("Username Must be at least 5 Characters");
            focusView = username;
            notValid = true;
        }

        if(gen <= 0){
            maleRadioBtn.setError("Please Select Your Gender");
            femaleRadioBtn.setError("Please Select Your Gender");
            notValid = true;
        } else if(gen >= 1){
            maleRadioBtn.setError(null);
            femaleRadioBtn.setError(null);
            notValid = false;
        }

        if(notValid){
            if(focusView!=null){
                focusView.requestFocus();
            }
        } else {
            uploadImageRegister();
        }
    }

    public void cancelRegister(View view) {
        FirebaseAuth.getInstance().signOut();
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        googleSignClient = GoogleSignIn.getClient(this, gso);
        googleSignClient.signOut().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) { }
        });
        startActivity(new Intent(this, GoogleLoginActivity.class));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //Detects request codes
        if(requestCode==GET_FROM_GALLERY && resultCode == Activity.RESULT_OK) {
            selectedImage = data.getData();
            imagetoUpload.setImageURI(selectedImage);
            Bitmap bitmap = null;
            try {
                bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), selectedImage);
            } catch (FileNotFoundException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }
}
