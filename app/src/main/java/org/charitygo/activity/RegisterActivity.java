package org.charitygo.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import org.charitygo.R;
import org.charitygo.model.StepHistory;
import org.charitygo.model.User;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

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

    private DatabaseReference dataRefStore;
    private DatabaseReference stepRefStore;
    private StorageReference storageRef = FirebaseStorage.getInstance().getReference();

    private final static int GET_FROM_GALLERY = 8000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

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
    }

    public void registerAccount(View view) {
        String usern = username.getText().toString();
//        String passw = password.getText().toString();
//        String repassw = retypePassword.getText().toString();
        String phone = contactNum.getText().toString();

        //Get the selected index of the radio group
        int gen = gender.getCheckedRadioButtonId();
        selectedGender = (RadioButton) findViewById(gen);

        View radiobtn = gender.findViewById(gen);
        int index = gender.indexOfChild(radiobtn);
        //Get the text of radio button in radio group
        RadioButton gend = (RadioButton) gender.getChildAt(index);
        String genStr = gend.getText().toString();

        String eml = email.getText().toString();

        boolean notValid = false;
        View focusView = null;


//        StorageReference tohruRef = storageRef.child("tohru.jpg");
//        StorageReference tohruImgRef = storageRef.child("images/tohru.jpg");
//        tohruRef.getName().equals(tohruImgRef.getName()); //true
//        tohruRef.getName().equals(tohruImgRef.getPath()); //false

        //Upload from a local file
        Uri file = Uri.fromFile(new File("path/to/images/tohru.jpg"));
        StorageReference tohruRef = storageRef.child("images/"+file.getLastPathSegment());
        UploadTask uploadTask = tohruRef.putFile(file);
        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

            }
        });

        //Get download URL
        final StorageReference reference = storageRef.child("images/tohru.jpg");
        uploadTask = reference.putFile(file);
        Task<Uri> uriTask = uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
            @Override
            public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                if(!task.isSuccessful()){
                    throw task.getException();
                }else{
                    return null;
                }
            }
        }).addOnCompleteListener(new OnCompleteListener<Uri>() {
            @Override
            public void onComplete(@NonNull Task<Uri> task) {
                if(task.isSuccessful()){
                    Uri downloadUri = task.getResult();
                }else{

                }
            }
        });

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

        if(eml.isEmpty()){
            email.setError("Please Enter Your Email");
            focusView = email;
            notValid = true;
        } else if(!eml.contains("@") && !eml.contains(".com")){
            email.setError("Please Enter a Valid Email Address");
            focusView = email;
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
            //Need to store the user details entered
            //Suppose to be in register activity
            String uid = currentUser.getUid();

            Map<String, User> googleUsers = new HashMap<>();
//            Map<String, StepHistory> userSteps = new HashMap<>();
            User userClass = new User(usern, currentUser.getEmail(), phone, genStr, 0);
            StepHistory steps = new StepHistory(uid, System.currentTimeMillis() , System.currentTimeMillis(), 0, 0);
            googleUsers.put(userClass.name, userClass);
/*            userSteps.put(uid, steps);*/
            dataRefStore = ref.child("user");
            dataRefStore.child(uid).setValue(googleUsers);
            stepRefStore = ref.child("stepHistory");
            stepRefStore.child(uid).setValue(steps);

            Intent intent = new Intent(getApplicationContext(),MainUI.class);
            startActivity(intent);
            Toast.makeText(RegisterActivity.this, "Successfully Registered !",Toast.LENGTH_LONG).show();
        }
    }

    public void cancelRegister(View view) {
        this.finish();
    }

    public void addImage(View view){
        startActivityForResult(new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.INTERNAL_CONTENT_URI), GET_FROM_GALLERY);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //Detects request codes
        if(requestCode==GET_FROM_GALLERY && resultCode == Activity.RESULT_OK) {
            Uri selectedImage = data.getData();
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
