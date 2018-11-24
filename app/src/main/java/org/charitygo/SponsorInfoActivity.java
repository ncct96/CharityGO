package org.charitygo;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;

import org.charitygo.model.YoutubeConfig;

import java.util.ArrayList;
import java.util.List;

public class SponsorInfoActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sponsor_info);

        Toolbar toolbar = (Toolbar) findViewById(R.id.spon_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    public void popUpYoutube(View view) {
        startActivity(new Intent(getApplicationContext(), YoutubeActivity.class));
    }
}
