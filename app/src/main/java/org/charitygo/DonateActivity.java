package org.charitygo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.InputFilter;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;

public class DonateActivity extends AppCompatActivity implements View.OnClickListener {

    private int points = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_donate);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        EditText editText = (EditText)findViewById(R.id.donate_editText_amount);
        editText.setFilters(new InputFilter[]{ new MinMaxFilter("1", "1890")});

        ImageButton btnPlus = (ImageButton) findViewById(R.id.donate_imageButton_add);
        btnPlus.setOnClickListener(this);
        ImageButton btnMinus = (ImageButton) findViewById(R.id.donate_imageButton_minus);
        btnMinus.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        EditText editText = (EditText) findViewById(R.id.donate_editText_amount);

        if(v.getId() == R.id.donate_imageButton_add){
            if(editText.getText().toString().equals("")){
                points += 1;
            }
            else if(editText.getText().toString().equals("1890")){
                points = 1890;
            }
            else {
                points = Integer.parseInt(String.valueOf(editText.getText())) + 1;
            }
        }
        else if(v.getId() == R.id.donate_imageButton_minus){
            if(editText.getText().toString().equals("")){
                points = 1;
            }
            else if(editText.getText().toString().equals("1")){
                points = 1;
            }
            else {
                points = Integer.parseInt(String.valueOf(editText.getText())) - 1;
            }
        }
        editText.setText(""+points);
    }
}
