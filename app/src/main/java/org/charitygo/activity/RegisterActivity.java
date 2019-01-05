package org.charitygo.activity;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
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

    private DatabaseReference dataRefStore = ref.child("users");
    private DatabaseReference stepRefStore;
    private FirebaseStorage storage = FirebaseStorage.getInstance();
    private StorageReference storageRef = storage.getReference();

    private ImageView imagetoUpload; private static final int RESULT_LOAD_IMAGE = 1;
    private Uri selectedImage; private String uploadURL;

    private String usern;
    private String phone;
    private String genStr;

    private View progressView;
    private View registerView;

    private final static int GET_FROM_GALLERY = 8000;

    private static long timestamp = System.currentTimeMillis();
    private DateFormat df = new DateFormat();
    private String monthYearPath = String.valueOf(df.longToYearMonth(timestamp));
    private String dayDatePath = String.valueOf(df.longToYearMonthDay(timestamp));

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        progressView = findViewById(R.id.registerProgress);
        registerView = findViewById(R.id.registerScroll);

        Button signupBtn = (Button) findViewById(R.id.buttonReg);
        signupBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerAccount(v);
            }
        });

        email = findViewById(R.id.editEmail);
        username = findViewById(R.id.editUsername);
//        password = findViewById(R.id.editPassword);
//        retypePassword = findViewById(R.id.editRePassword);
        contactNum = findViewById(R.id.editPhone);
        gender = (RadioGroup) findViewById(R.id.radioGroupGender);
        maleRadioBtn = (RadioButton) findViewById(R.id.radioButtonMale);
        femaleRadioBtn = (RadioButton) findViewById(R.id.radioButtonFemale);

        email.setText(currentUser.getEmail());

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

    private void showProgress(final boolean show) {
        // On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
        // for very easy animations. If available, use these APIs to fade-in
        // the progress spinner.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {

            //Progress Dialog
            int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);

            registerView.setVisibility(show ? View.GONE : View.VISIBLE);
            registerView.animate().setDuration(shortAnimTime).alpha(
                    show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    registerView.setVisibility(show ? View.GONE : View.VISIBLE);
                }
            });

            progressView.setVisibility(show ? View.VISIBLE : View.GONE);
            progressView.animate().setDuration(shortAnimTime).alpha(
                    show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    progressView.setVisibility(show ? View.VISIBLE : View.GONE);
                }
            });
        } else {
            // The ViewPropertyAnimator APIs are not available, so simply show
            // and hide the relevant UI components.
            progressView.setVisibility(show ? View.VISIBLE : View.GONE);
            registerView.setVisibility(show ? View.GONE : View.VISIBLE);
        }
    }

    public void uploadImageRegister(){
        uploadURL = "images/"+System.currentTimeMillis()+"."+getFileExtension(selectedImage);
        StorageReference storageImgRef = storageRef.child(uploadURL);
        storageImgRef.putFile(selectedImage).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                //uploadURL = taskSnapshot.getMetadata().getReference().getDownloadUrl().toString();
                showProgress(false);
                //Store the user details entered
                User newUser = new User(usern, currentUser.getEmail(), phone, genStr, uploadURL,0);
                dataRefStore.child(currentUser.getUid()).setValue(newUser);
                Intent intent = new Intent(getApplicationContext(),MainUI.class);
                startActivity(intent);
                Toast.makeText(RegisterActivity.this, "Successfully Registered !",Toast.LENGTH_LONG).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(RegisterActivity.this, "Image Unable To be Upload", Toast.LENGTH_SHORT).show();
            }
        }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                //double progress = (100.0 * taskSnapshot.getBytesTransferred() / taskSnapshot.getTotalByteCount());
                //Toast.makeText(RegisterActivity.this, "Registering... Please Wait.."+String.format("%f",progress)+"Seconds..", Toast.LENGTH_SHORT).show();
                showProgress(true);
            }
        });
//        //Upload from a local file
//        Uri file = Uri.fromFile(new File("path/to/images/tohru.png"));
//        StorageReference tohruRef = storageRef.child("images/"+file.getLastPathSegment());
//        UploadTask uploadTask = tohruRef.putFile(file);
//        uploadTask.addOnFailureListener(new OnFailureListener() {
//            @Override
//            public void onFailure(@NonNull Exception e) {
//
//            }
//        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
//            @Override
//            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
//
//            }
//        });
//
//        //Get download URL
//        final StorageReference reference = storageRef.child("images/tohru.png");
//        uploadTask = reference.putFile(file);
//        Task<Uri> uriTask = uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
//            @Override
//            public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
//                if(!task.isSuccessful()){
//                    throw task.getException();
//                }else{
//                    return null;
//                }
//            }
//        }).addOnCompleteListener(new OnCompleteListener<Uri>() {
//            @Override
//            public void onComplete(@NonNull Task<Uri> task) {
//                if(task.isSuccessful()){
//                    Uri downloadUri = task.getResult();
//                }else{
//
//                }
//            }
//        });
    }

    public void registerAccount(View view) {
        usern = username.getText().toString();
//        String passw = password.getText().toString();
//        String repassw = retypePassword.getText().toString();
        phone = contactNum.getText().toString();

        //Get the selected index of the radio group
        int gen = gender.getCheckedRadioButtonId();
        selectedGender = (RadioButton) findViewById(gen);

        View radiobtn = gender.findViewById(gen);
        int index = gender.indexOfChild(radiobtn);
        //Get the text of radio button in radio group
        RadioButton gend = (RadioButton) gender.getChildAt(index);
        genStr = gend.getText().toString();

        boolean notValid = false;
        View focusView = null;

//        if(repassw.isEmpty()){
//            retypePassword.setError("Please Retype Your Password");
//            focusView = retypePassword;
//            notValid = true;
//        } else if(!repassw.equals(passw)){
//            retypePassword.setError("Entered Password Mismatch");
//            focusView = retypePassword;
//            notValid = true;
//        }

//        if(passw.isEmpty()){
//            password.setError("Password Cannot Be Empty !");
//            focusView = password;
//            notValid = true;
//        } else if(passw.length() <= 6){
//            password.setError("Password must be at least 6 Characters");
//            focusView = password;
//            notValid = true;
//        } else if(passw.contains(" ")){
//            password.setError("Password Cannot Contain Blank Space");
//            focusView = password;
//            notValid = true;
//        }

        //Contact Number Validations
        if(phone.isEmpty()){
            contactNum.setError("Please Enter Contact Number");
            focusView = contactNum;
            notValid = true;
        } else if(phone.matches("[a-zA-Z]+")){
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

//        if(eml.isEmpty()){
//            email.setError("Please Enter Your Email");
//            focusView = email;
//            notValid = true;
//        } else if(!eml.contains("@") && !eml.contains(".com")){
//            email.setError("Please Enter a Valid Email Address");
//            focusView = email;
//            notValid = true;
//        }

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
//            Map<String, User> googleUsers = new HashMap<>();
////            Map<String, StepHistory> userSteps = new HashMap<>();
//            User userClass = new User(usern, currentUser.getEmail(), phone, genStr, 0);
//            StepHistory steps = new StepHistory(uid, System.currentTimeMillis() , System.currentTimeMillis(), 0, 0);
//            googleUsers.put(userClass.name, userClass);
///*            userSteps.put(uid, steps);*/
//            dataRefStore = ref.child("user");
//            dataRefStore.child(uid).setValue(googleUsers);
//            stepRefStore = ref.child("stepHistory");
//            stepRefStore.child(uid).setValue(steps);
        }
    }

    public void cancelRegister(View view) {
        this.finish();
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
