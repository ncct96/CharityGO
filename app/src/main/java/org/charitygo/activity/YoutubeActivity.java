package org.charitygo.activity;

import android.os.Bundle;
import android.util.DisplayMetrics;

import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;

import org.charitygo.R;
import org.charitygo.model.YoutubeConfig;

public class YoutubeActivity extends YouTubeBaseActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_youtube);
        onYouTubePlay();
        mYouTubePlayerView.initialize(YoutubeConfig.getApiKey(),mOnInitializedListener);

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        int width = dm.widthPixels; int height = dm.heightPixels;
        getWindow().setLayout((int)(width*1), (int)(height*.4));
    }

    YouTubePlayerView mYouTubePlayerView;
    YouTubePlayer.OnInitializedListener mOnInitializedListener;
    public void onYouTubePlay(){
        mYouTubePlayerView = (YouTubePlayerView) findViewById(R.id.youtubePlayer);
        mOnInitializedListener = new YouTubePlayer.OnInitializedListener() {
            @Override
            public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean b) {
                //FOR LOADING SINGLE VIDEO
                youTubePlayer.loadVideo("6Dh-RL__uN4");

                //FOR LOADING MULTIPLE VIDEOS
//                List<String> videoList = new ArrayList<>();
//                videoList.add("6Dh-RL__uN4");
//                videoList.add("aMWNOaFGsXo");
//                youTubePlayer.loadVideos(videoList);

                //FOR LOADING YOUTUBE PLAYLIST
//                youTubePlayer.loadPlaylist("PLYH8WvNV1YEm6ETE_AS0nV2sKIsU2cUPW");
            }

            @Override
            public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {

            }
        };
    }
}
