package org.charitygo;

import android.app.LoaderManager;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;
import android.app.LoaderManager.LoaderCallbacks;

import java.util.ArrayList;
import java.util.List;

public class RegisterActivity extends AppCompatActivity{

    private EditText username;
    private EditText password;
    private EditText retypePassword;
    private RadioGroup gender;
    private RadioButton selectedGender;
    private RadioButton maleRadioBtn;
    private RadioButton femaleRadioBtn;
    private EditText email;
    private EditText contactNum;

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
    }

    public void registerAccount(View view) {
        username = findViewById(R.id.editUsername);
        password = findViewById(R.id.editPassword);
        retypePassword = findViewById(R.id.editRePassword);
        contactNum = findViewById(R.id.editPhone);
        gender = (RadioGroup) findViewById(R.id.radioGroupGender);
        maleRadioBtn = (RadioButton) findViewById(R.id.radioButtonMale);
        femaleRadioBtn = (RadioButton) findViewById(R.id.radioButtonFemale);
        email = findViewById(R.id.editEmail);

        String usern = username.getText().toString();
        String passw = password.getText().toString();
        String repassw = retypePassword.getText().toString();
        String phone = contactNum.getText().toString();

        //Get the selected index of the radio group
        int gen = gender.getCheckedRadioButtonId();
        selectedGender = (RadioButton) findViewById(gen);

        /*View radiobtn = gender.findViewById(gen);
        int index = gender.indexOfChild(radiobtn);
        //Get the text of radio button in radio group
        RadioButton gend = (RadioButton) gender.getChildAt(index);
        //String genStr = gend.getText().toString();*/

        String eml = email.getText().toString();

        boolean notValid = false;
        View focusView = null;

        if(repassw.isEmpty()){
            retypePassword.setError("Please Retype Your Password");
            focusView = retypePassword;
            notValid = true;
        } else if(!repassw.equals(passw)){
            retypePassword.setError("Entered Password Mismatch");
            focusView = retypePassword;
            notValid = true;
        }

        if(passw.isEmpty()){
            password.setError("Password Cannot Be Empty !");
            focusView = password;
            notValid = true;
        } else if(passw.length() <= 6){
            password.setError("Password must be at least 6 Characters");
            focusView = password;
            notValid = true;
        } else if(passw.contains(" ")){
            password.setError("Password Cannot Contain Blank Space");
            focusView = password;
            notValid = true;
        }

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
            notValid = true;
        }

        if(notValid){
            if(focusView!=null){
                focusView.requestFocus();
            }
        } else {
            //Need to store the user details entered
            Intent intent = new Intent(getApplicationContext(),LoginActivity.class);
            startActivity(intent);
            Toast.makeText(RegisterActivity.this, "Successfully Registered !",Toast.LENGTH_LONG).show();
        }
    }

    public void cancelRegister(View view) {
        Intent intent = new Intent(getApplicationContext(), MainUI.class);
        startActivity(intent);
    }
}