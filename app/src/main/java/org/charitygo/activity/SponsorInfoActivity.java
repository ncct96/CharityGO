package org.charitygo.activity;
import android.content.Intent;
import android.net.Uri;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;

import org.charitygo.R;

public class SponsorInfoActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sponsor_info);

        Toolbar toolbar = (Toolbar) findViewById(R.id.spon_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        initializeActionWidget();
    }

    public void popUpYoutube(View view) {
        startActivity(new Intent(getApplicationContext(), YoutubeActivity.class));
    }

    protected void initializeActionWidget(){
        Toolbar tb = (Toolbar) findViewById(R.id.spon_toolbar);
        setSupportActionBar(tb);

        FloatingActionButton email = (FloatingActionButton) findViewById(R.id.spon_email);
        email.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_SENDTO);
                intent.setType("message/rfc822");
                intent.setData(Uri.parse("mailto:recipient@example.com"));
                startActivity(intent);
            }
        });

        FloatingActionButton call = (FloatingActionButton) findViewById(R.id.spon_phone);
        call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:0123456789"));
                startActivity(intent);
            }
        });

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
}
