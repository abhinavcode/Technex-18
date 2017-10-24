package com.iitbhu.technex18.Activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.widget.VideoView;

import com.iitbhu.technex18.R;

import static com.iitbhu.technex18.utils1.Constants.PREFERENCES;
import static com.iitbhu.technex18.utils1.Constants.REGISTERED;

/**
 * Created by abhinav on 29/9/17.
 */

public class Splash extends AppCompatActivity {
    VideoView videoView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.splash);
      videoView=(VideoView)findViewById(R.id.videoView);

        final Uri path=Uri.parse("android.resource://"+getPackageName()+"/"+R.raw.splash);
        videoView.setVideoURI(path);

        SharedPreferences myprefs=getSharedPreferences(PREFERENCES, Context.MODE_PRIVATE);
        final Boolean b=myprefs.getBoolean(REGISTERED,false);
        Handler handler=new Handler();
        final Runnable runnable=new Runnable() {
            @Override
            public void run() {

            }
        };
        videoView.start();
        videoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mediaPlayer) {
                if(!b) {
                    startActivity(new Intent(Splash.this, HomeActivity.class));
                }
                else{
                    startActivity(new Intent(Splash.this, Dashboard.class));
                }
                finish();

            }
        });
    }
    @Override
    protected void onStop(){
        super.onStop();
        videoView.start();
    }
    @Override
    public void onPause(){
        super.onPause();
        videoView.start();;
    }
    public void onResume(){
        super.onResume();
        videoView.start();
    }
    @Override
    public void onStart(){
        super.onStart();
        videoView.start();
    }
}
