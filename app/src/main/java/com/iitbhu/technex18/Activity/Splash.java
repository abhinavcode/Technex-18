package com.iitbhu.technex18.Activity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.VideoView;

import com.iitbhu.technex18.R;

/**
 * Created by abhinav on 29/9/17.
 */

public class Splash extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.splash);
        VideoView videoView=(VideoView)findViewById(R.id.videoView);
        Uri path=Uri.parse("android.resource://"+getPackageName()+"/"+R.raw.splash);
        videoView.setVideoURI(path);
        videoView.start();
        videoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mediaPlayer) {
//                if()
                startActivity(new Intent(Splash.this,HomeActivity.class));
                finish();
            }
        });
    }
}
