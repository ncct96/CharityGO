package org.charitygo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class OrganizationActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(R.style.AppThemeTranslucent_NoActionBar);
        setContentView(R.layout.activity_organization);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        createViews();
    }

    protected void goToDonate(View view){
        Intent intent = new Intent(this, DonateActivity.class);
        intent.putExtra("EXTRA_ID", String.valueOf(view.getId()));
        startActivity(intent);
    }

    protected void goToOrganization(View view){
        Intent intent = new Intent(this, OrganizationInfoActivity.class);
        intent.putExtra("EXTRA_ID", String.valueOf(view.getId()));
        startActivity(intent);
    }

    protected void createViews(){

        //Get info from database here

        //Adds cards.xml n number of times
        for(int i = 1; i < 6; i++) {
            LinearLayout linearLayout = (LinearLayout) findViewById(R.id.organization_linearLayout_cards);
            View view = getLayoutInflater().inflate(R.layout.cards, linearLayout, false);
            TextView textName = (TextView) view.findViewById(R.id.info_text);
            textName.setText("Card " + i);
            textName.setId(i);

            ImageView imageView = (ImageView) view.findViewById(R.id.info_image);
            imageView.setId(i);

            Button donate = (Button) view.findViewById(R.id.info_donate);
            donate.setId(i);

            ImageButton open = (ImageButton) view.findViewById(R.id.info_more);
            open.setId(i);

            TextView textPoints = (TextView) view.findViewById(R.id.info_points);
            textPoints.setText(i + "0000 points more");

            linearLayout.addView(view);
        }
    }
}
