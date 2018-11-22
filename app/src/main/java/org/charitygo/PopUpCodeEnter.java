package org.charitygo;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.AutoCompleteTextView;

public class PopUpCodeEnter extends AppCompatActivity {
    private AutoCompleteTextView password;
    private AutoCompleteTextView repassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.popupcodeenter);

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        int width = dm.widthPixels; int height = dm.heightPixels;
        getWindow().setLayout((int)(width*.8), (int)(height*.5));
    }

    public void savePassword(){

        boolean cancel = false;
        View focusView = null;

        password = (AutoCompleteTextView) findViewById(R.id.enter_password);
        repassword = (AutoCompleteTextView) findViewById(R.id.enter_repassword);
        String passwordEnter = password.getText().toString();
        String repasswordEnter = repassword.getText().toString();

        if(TextUtils.isEmpty(passwordEnter)){
            password.setError("Please Do Not Leave Password Blank !");
            focusView = password;
            cancel = true;
        }else if(!passwordEnter.equals(repasswordEnter)){
            repassword.setError("Password Mismatched !");
            focusView = repassword;
            cancel = true;
        }else if(TextUtils.isEmpty(repasswordEnter)){
            repassword.setError("Please Do Not Leave Password Blank !");
            focusView = repassword;
            cancel = true;
        }

        if(cancel){
            focusView.requestFocus();
        }else{
            createDialog();
        }
    }

    public void createDialog(   ){
        AlertDialog ad = new AlertDialog.Builder(PopUpCodeEnter.this)
                .setCancelable(true)
                .setTitle("Create New Password")
                .setMessage("Save Changes?")
                .setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        //save password to database and update
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
