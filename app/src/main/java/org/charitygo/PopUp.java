package org.charitygo;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.Toast;

import org.charitygo.model.GmailSender;
import org.w3c.dom.Text;

import java.net.URI;
import java.util.Random;

public class PopUp extends AppCompatActivity {
    private Random rnd;
    private int n;
    private String randomNumber = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.popupforgot);

        initializeOnClick();

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        int width = dm.widthPixels; int height = dm.heightPixels;
        getWindow().setLayout((int)(width*.8), (int)(height*.5));
    }

    protected void initializeOnClick(){


        final Button sendEmail = (Button) findViewById(R.id.sendEmailBtn);
        Button verifyCode = (Button) findViewById(R.id.verifyCodeBtn);

        sendEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                View focusView = null; boolean cancel = false;
                AutoCompleteTextView emailEntered = (AutoCompleteTextView) findViewById(R.id.enter_email);
                String email = emailEntered.getText().toString();

                if(TextUtils.isEmpty(email)){
                    emailEntered.setError("Please Enter Your Email.");
                    focusView = emailEntered;
                    cancel = true;
                }else if(!email.contains("@")){
                    emailEntered.setError("Please Enter A Valid Email Address.");
                    focusView = emailEntered;
                    cancel = true;
                }

                if(cancel){
                    focusView.requestFocus();
                }else {
                    try{
                        createDialogEmail(email);
                    }catch(Exception e){
                        emailEntered.setError("Please Enter A Valid Email Address.");
                        focusView = emailEntered;
                        focusView.requestFocus();
                    }
                }
            }
        });

        verifyCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                View focusView = null;
                AutoCompleteTextView sendCode = (AutoCompleteTextView) findViewById(R.id.enter_code);
                String code = sendCode.getText().toString();

                if(TextUtils.isEmpty(code)){
                    sendCode.setError("Please Enter Code.");
                    focusView = sendCode;
                    focusView.requestFocus();
                }else if(!TextUtils.isDigitsOnly(code)){
                    sendCode.setError("Please Enter The Valid Code Format.");
                    focusView = sendCode;
                    focusView.requestFocus();
                }else{
                    verifyEnteredCode();
                }
            }
        });
    }

    private void verifyEnteredCode(){
        AutoCompleteTextView codeEntered = (AutoCompleteTextView) findViewById(R.id.enter_code);
        String code = codeEntered.getText().toString();
        if(code.equals(randomNumber)){
            //start new activity in resetting password
            startActivity(new Intent(PopUp.this, PopUpCodeEnter.class));
        }else {
            Toast.makeText(PopUp.this, "Invalid Code Entered, Try Again.",Toast.LENGTH_LONG).show();
        }
    }

    private void sendMessage(final String email) {
        rnd = new Random();
        n = 100000 + rnd.nextInt(900000);
        randomNumber = String.valueOf(n);

        final ProgressDialog dialog = new ProgressDialog(PopUp.this);
        dialog.setTitle("Sending Email");
        dialog.setMessage("Please wait");
        dialog.show();
        Thread sender = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    GmailSender sender = new GmailSender("keyboardwarrior854@gmail.com", "nceekay1998");
                    sender.sendMail("Your Recovery Password",
                            "Please Enter This Code to the Apps: " + randomNumber,
                            "keyboardwarrior854@gmail.com",
                            "" + email);
                    dialog.dismiss();
                } catch (Exception e) {
                    Log.e("mylog", "Error: " + e.getMessage());
                }
            }
        });
        sender.start();
    }

    public void createDialogEmail(final String email){
        AlertDialog ad = new AlertDialog.Builder(PopUp.this)
                .setCancelable(true)
                .setTitle("Send Email")
                .setMessage("Are you sure to send to this email?")
                .setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        //send email
                        sendMessage(email);
                            //invert the view of both different things
                            TextInputLayout enterEmail = (TextInputLayout) findViewById(R.id.pop_up_email);
                            TextInputLayout enterCode = (TextInputLayout) findViewById(R.id.pop_up_code);

                            Button sendEmail = (Button) findViewById(R.id.sendEmailBtn);
                            Button verifyCode = (Button) findViewById(R.id.verifyCodeBtn);

                            enterCode.setVisibility(View.VISIBLE);
                            verifyCode.setVisibility(View.VISIBLE);

                            enterEmail.setVisibility(View.GONE);
                            sendEmail.setVisibility(View.GONE);
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // do nothing
                    }
                })
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }

}
