package com.iitbhu.technex18.Activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;
import android.widget.VideoView;

import com.google.firebase.messaging.FirebaseMessaging;
import com.iitbhu.technex18.R;
import com.iitbhu.technex18.utils1.Config;
import com.iitbhu.technex18.utils1.NotificationUtils;

import static com.iitbhu.technex18.utils1.Constants.PREFERENCES;
import static com.iitbhu.technex18.utils1.Constants.REGISTERED;

/**
 * Created by abhinav on 29/9/17.
 */

public class Splash extends AppCompatActivity {
    VideoView videoView;
    private BroadcastReceiver mRegistrationBroadcastReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.splash);
      videoView=(VideoView)findViewById(R.id.videoView);

        final Uri path=Uri.parse("android.resource://"+getPackageName()+"/"+R.raw.splash);
        videoView.setVideoURI(path);

        SharedPreferences myprefs=getSharedPreferences(PREFERENCES, Context.MODE_PRIVATE);
        final Boolean b=myprefs.getBoolean(REGISTERED,false);
        FirebaseMessaging.getInstance().subscribeToTopic(Config.TOPIC_GLOBAL);

        videoView.start();
        videoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mediaPlayer) {
                if(!b) {
                    startActivity(new Intent(Splash.this, HomeActivity.class));
                }
                else{
                    startActivity(new Intent(Splash.this, Dashboard.class));
                    FirebaseMessaging.getInstance().subscribeToTopic(Config.TOPIC_CA);


                }
                finish();

            }
        });



        mRegistrationBroadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {

                // checking for type intent filter
                if (intent.getAction().equals(Config.REGISTRATION_COMPLETE)) {
                    // gcm successfully registered
                    // now subscribe to `global` topic to receive app wide notifications
                    FirebaseMessaging.getInstance().subscribeToTopic(Config.TOPIC_GLOBAL);
                    if(b)
                        FirebaseMessaging.getInstance().subscribeToTopic(Config.TOPIC_CA);

                    displayFirebaseRegId();

                } else if (intent.getAction().equals(Config.PUSH_NOTIFICATION)) {
                    // new push notification is received

                    String message = intent.getStringExtra("message");

                    Toast.makeText(getApplicationContext(), "Notification: " + message, Toast.LENGTH_LONG).show();

                }
            }
        };

        displayFirebaseRegId();
    }

    // Fetches reg id from shared preferences
    // and displays on the screen
    private void displayFirebaseRegId() {
        SharedPreferences pref = getApplicationContext().getSharedPreferences(Config.SHARED_PREF, 0);
        String regId = pref.getString("regId", null);

        Log.e("technex", "Firebase reg id: " + regId);

//        if (!TextUtils.isEmpty(regId))
//            txtRegId.setText("Firebase Reg Id: " + regId);
//        else
//            txtRegId.setText("Firebase Reg Id is not received yet!");
    }

    @Override
    protected void onResume() {
        super.onResume();

        // register GCM registration complete receiver
        LocalBroadcastManager.getInstance(this).registerReceiver(mRegistrationBroadcastReceiver,
                new IntentFilter(Config.REGISTRATION_COMPLETE));

        // register new push message receiver
        // by doing this, the activity will be notified each time a new message arrives
        LocalBroadcastManager.getInstance(this).registerReceiver(mRegistrationBroadcastReceiver,
                new IntentFilter(Config.PUSH_NOTIFICATION));

        // clear the notification area when the app is opened
        NotificationUtils.clearNotifications(getApplicationContext());
        videoView.start();

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
        LocalBroadcastManager.getInstance(this).unregisterReceiver(mRegistrationBroadcastReceiver);

    }
    @Override
    public void onStart(){
        super.onStart();
        videoView.start();
    }
}
