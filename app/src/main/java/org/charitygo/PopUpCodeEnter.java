package org.charitygo;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;

public class PopUpCodeEnter extends AppCompatActivity {
    private AutoCompleteTextView password;
    private AutoCompleteTextView repassword;

    public class UndoListener implements View.OnClickListener{
        @Override
        public void onClick(View v){

        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.popupcodeenter);

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        int width = dm.widthPixels; int height = dm.heightPixels;
        getWindow().setLayout((int)(width*.8), (int)(height*.5));

        Button savebtn = (Button) findViewById(R.id.save_changes_btn);
        savebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                savePassword();
            }
        });
    }

    public void createDialogChanges(){
        AlertDialog ad = new AlertDialog.Builder(PopUpCodeEnter.this, R.style.AlertDialogStyle)
                .setCancelable(true)
                .setTitle("Save Password")
                .setMessage("Are you sure to save this new password?")
                .setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        Snackbar snake = Snackbar.make(findViewById(R.id.coordinatorLayout), "Changes Saved", Snackbar.LENGTH_SHORT);
                        snake.setAction("UNDO", new UndoListener());
                        snake.show();
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
            createDialogChanges();
        }
    }
}
