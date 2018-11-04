package org.charitygo;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

public class OrganizationInfoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(R.style.AppThemeTranslucent_NoActionBar);
        setContentView(R.layout.activity_organization_info);

        String organizationID = getIntent().getStringExtra("EXTRA_ID");

        initializeUIValues(organizationID);
        initializeActionWidgets();
    }

    protected void initializeUIValues(String id){

        //Get info from database here

        TextView background = (TextView) findViewById(R.id.info_text_background);
        background.setText("Founded in 1678 by Richard B. Patterson, this organization has grown to over 50 branches acrross Malaysia.");

        TextView about = (TextView) findViewById(R.id.info_text_about);
        about.setText("Sell stuff, I guess...");

        TextView contact = (TextView) findViewById(R.id.info_text_contact);
        contact.setText("Email: info@mail.noreply.com");
    }

    protected void initializeActionWidgets(){
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton email = (FloatingActionButton) findViewById(R.id.info_email);
        email.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
}
