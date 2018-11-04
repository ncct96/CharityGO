package org.charitygo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.InputFilter;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

public class DonateActivity extends AppCompatActivity implements View.OnClickListener {

    private int points = 0;
    private final int minPoints = 1, step = 1;
    private static int maxPoints;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_donate);

        String organizationID = getIntent().getStringExtra("EXTRA_ID");

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        ImageButton btnPlus = (ImageButton) findViewById(R.id.donate_imageButton_add);
        btnPlus.setOnClickListener(this);
        ImageButton btnMinus = (ImageButton) findViewById(R.id.donate_imageButton_minus);
        btnMinus.setOnClickListener(this);

        initializeUIValues(organizationID);
    }

    public void initializeUIValues(String id) {
        int userPoints = 0, orgPoints = 0;

        //Get points from database here
        userPoints = 15000;
        orgPoints = 6000000;

        if(userPoints > orgPoints)
            maxPoints = orgPoints;
        else
            maxPoints = userPoints;

        EditText setPoints = (EditText)findViewById(R.id.donate_editText_amount);
        setPoints.setFilters(new InputFilter[]{ new MinMaxFilter(minPoints, maxPoints)});

        TextView textUserPoints = (TextView) findViewById(R.id.donate_points_user);
        textUserPoints.setText(userPoints + " points");

        TextView textOrgPoints = (TextView) findViewById(R.id.donate_points_org);
        textOrgPoints.setText(orgPoints + " points");
    }

    @Override
    public void onClick(View v) {
        EditText editPoints = (EditText) findViewById(R.id.donate_editText_amount);

        if(v.getId() == R.id.donate_imageButton_add){
            if(editPoints.getText().toString().equals("")){
                points += step;
            }
            else if(editPoints.getText().toString().equals(String.valueOf(maxPoints))){
                points = maxPoints;
            }
            else {
                points = Integer.parseInt(String.valueOf(editPoints.getText())) + step;
            }
        }
        else if(v.getId() == R.id.donate_imageButton_minus){
            if(editPoints.getText().toString().equals("")){
                points = minPoints;
            }
            else if(editPoints.getText().toString().equals("1")){
                points = minPoints;
            }
            else {
                points = Integer.parseInt(String.valueOf(editPoints.getText())) - step;
            }
        }
        editPoints.setText(""+points);
    }

    protected void donateTransaction(View view){

        EditText editPoints = (EditText) findViewById(R.id.donate_editText_amount);

        if(editPoints.getText().toString().equals("")){
            editPoints.requestFocus();
        }
        else{
            int points = Integer.parseInt(editPoints.getText().toString());

            //Update database here

            this.finish();
        }
    }
}
