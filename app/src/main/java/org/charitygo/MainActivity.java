package org.charitygo;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    protected void goToAbout(View view){
        Intent intent = new Intent(this, AboutActivity.class);
        startActivity(intent);
    }

    protected void goToMain(View view){
        boolean isLog = getLoggedStatus(getApplicationContext());
        if(isLog){
            Intent intent = new Intent(this, MainUI.class);
            startActivity(intent);
        } else {
            Intent intent1 = new Intent(this, LoginActivity.class);
            startActivity(intent1);
        }

    }

    protected void goToOrganizations(View view){
        Intent intent = new Intent(this, OrganizationActivity.class);
        startActivity(intent);
    }

    public static boolean getLoggedStatus(Context context){
        return LoginActivity.getPreference(context).getBoolean(LoginActivity.Logged_IN,false);
    }
}
