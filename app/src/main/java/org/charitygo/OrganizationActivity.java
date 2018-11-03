package org.charitygo;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

public class OrganizationActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(R.style.AppThemeTranslucent_NoActionBar);
        //getApplication().setTheme(R.style.MyLightTheme);
        setContentView(R.layout.activity_organization);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        createViews();
    }

    protected void goToDonate(View view){
        Intent intent = new Intent(this, DonateActivity.class);
        startActivity(intent);
    }

    protected void goToOrganization(View view){
        Intent intent = new Intent(this, OrganizationInfoActivity.class);
        startActivity(intent);
    }

    protected void createViews(){

        for(int i = 1; i < 6; i++) {
            LinearLayout linearLayout = (LinearLayout) findViewById(R.id.organization_linearLayout_cards);
            View view = getLayoutInflater().inflate(R.layout.cards, linearLayout, false);
            TextView textView = (TextView) view.findViewById(R.id.info_text);
            textView.setText("Card " + i);
            linearLayout.addView(view);
        }
    }
}
