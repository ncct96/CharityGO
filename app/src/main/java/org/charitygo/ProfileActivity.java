package org.charitygo;

import android.graphics.drawable.Drawable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.view.menu.ActionMenuItemView;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class ProfileActivity extends AppCompatActivity {

    private FloatingActionButton fab;
    private EditText Email;
    private EditText Username;
    private EditText ContactNumber;
    private TextView EmailView;
    private TextView UsernameView;
    private TextView ContactNumberView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        //Get Edit Text
        Email = findViewById(R.id.editEmail);
        Username = findViewById(R.id.editUsername);
        ContactNumber = findViewById(R.id.editPhone);

        //Get Text View
        EmailView = findViewById(R.id.email);
        UsernameView = findViewById(R.id.username);
        ContactNumberView = findViewById(R.id.contactnum);

        //Get Text View Text
        String PreEmail = EmailView.getText().toString();
        String PreUsername = UsernameView.getText().toString();
        String PreNumber = ContactNumberView.getText().toString();

        //Set Edit Text's Text
        Email.setText(PreEmail);
        Username.setText(PreUsername);
        ContactNumber.setText(PreNumber);

        fab = findViewById(R.id.editProf);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //SET THE ICON TO CANCEL
                if(Email.getVisibility() == View.VISIBLE && Username.getVisibility() == View.VISIBLE && ContactNumber.getVisibility() == View.VISIBLE){
                    //Set Text View to Invisible
                    EmailView.setVisibility(View.VISIBLE);
                    UsernameView.setVisibility(View.VISIBLE);
                    ContactNumberView.setVisibility(View.VISIBLE);
                    Email.setVisibility(View.GONE);
                    Username.setVisibility(View.GONE);
                    ContactNumber.setVisibility(View.GONE);
                } else if (EmailView.getVisibility() == View.VISIBLE && UsernameView.getVisibility() == View.VISIBLE && ContactNumberView.getVisibility() == View.VISIBLE){
                    //Set Edit Text to Visible
                    Email.setVisibility(View.VISIBLE);
                    Username.setVisibility(View.VISIBLE);
                    ContactNumber.setVisibility(View.VISIBLE);
                    EmailView.setVisibility(View.GONE);
                    UsernameView.setVisibility(View.GONE);
                    ContactNumberView.setVisibility(View.GONE);
                }
            }
        });

        Email.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(Email.getText().toString().equals(null)){
                    String emailChg = Email.getText().toString();
                    EmailView.setText(emailChg);
                }
            }
        });
        Username.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(Username.getText().toString().equals(null)){
                    String usernChg = Username.getText().toString();
                    UsernameView.setText(usernChg);
                }
            }
        });
        ContactNumber.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(ContactNumber.getText().toString().equals(null)){
                    String contNChg = ContactNumber.getText().toString();
                    ContactNumberView.setText(contNChg);
                }
            }
        });
    }
}
