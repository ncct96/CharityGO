package org.charitygo.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.charitygo.R;

public class DonateActivity extends AppCompatActivity implements View.OnClickListener {

    private int points = 0;
    private final int minPoints = 0, step = 5;
    private static int maxPoints;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_donate);

        String organizationID = getIntent().getStringExtra("EXTRA_ID");

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Button btnPlus = (Button) findViewById(R.id.donate_imageButton_add);
        btnPlus.setOnClickListener(this);
        Button btnMinus = (Button) findViewById(R.id.donate_imageButton_minus);
        btnMinus.setOnClickListener(this);

        EditText editPoints = (EditText) findViewById(R.id.donate_editText_amount);
        final Button donateButton = (Button) findViewById(R.id.donate_button_donate);
        donateButton.setEnabled(false);
        donateButton.setBackgroundColor(Color.LTGRAY);

        editPoints.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                enableButton();
            }
        });

        initializeUIValues(organizationID);
    }

    private void enableButton() {
        EditText editPoints = (EditText) findViewById(R.id.donate_editText_amount);
        Button donateButton = (Button) findViewById(R.id.donate_button_donate);

        if (editPoints.getText().toString().equals("")) {
            donateButton.setEnabled(false);
            donateButton.setBackgroundColor(Color.LTGRAY);
            editPoints.requestFocus();
        } else if (Integer.parseInt(editPoints.getText().toString()) == 0) {
            donateButton.setEnabled(false);
            donateButton.setBackgroundColor(Color.LTGRAY);
        } else {
            donateButton.setEnabled(true);
            donateButton.setBackgroundColor(Color.parseColor("#00b0ff"));
        }
    }

    public void initializeUIValues(String id) {
        int userPoints = 0, orgPoints = 0;

        //Get points from database here
        userPoints = 1500;
        orgPoints = 1000000;

        if (userPoints > orgPoints)
            maxPoints = orgPoints;
        else
            maxPoints = userPoints;

        EditText setPoints = (EditText) findViewById(R.id.donate_editText_amount);
        setPoints.setFilters(new InputFilter[]{new MinMaxFilter(minPoints, maxPoints)});

        TextView textUserPoints = (TextView) findViewById(R.id.donate_points_user);
        textUserPoints.setText(userPoints + " points");

        TextView textOrgPoints = (TextView) findViewById(R.id.donate_points_org);
        textOrgPoints.setText(orgPoints + " points");
    }

    @Override
    public void onClick(View v) {

        EditText editPoints = (EditText) findViewById(R.id.donate_editText_amount);

        if (v.getId() == R.id.donate_imageButton_add) {
            if (editPoints.getText().toString().equals("")) {
                points += step;
            } else if (Integer.parseInt(editPoints.getText().toString()) + step >= maxPoints) {
                points = maxPoints;
            } else {
                points = Integer.parseInt(String.valueOf(editPoints.getText())) + step;
            }
        } else if (v.getId() == R.id.donate_imageButton_minus) {
            if (editPoints.getText().toString().equals("")) {
                points = minPoints;
            } else if (Integer.parseInt(editPoints.getText().toString()) - step <= minPoints) {
                points = minPoints;
            } else {
                points = Integer.parseInt(String.valueOf(editPoints.getText())) - step;
            }
        }
        editPoints.setText("" + points);
    }

    public void donateTransaction(View view) {
        EditText editPoints = (EditText) findViewById(R.id.donate_editText_amount);

        int points = Integer.parseInt(editPoints.getText().toString());

        //Update database here

        this.finish();
    }
}
