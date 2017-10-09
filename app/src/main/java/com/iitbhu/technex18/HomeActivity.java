package com.iitbhu.technex18;

import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.support.customtabs.CustomTabsIntent;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

public class HomeActivity extends AppCompatActivity {
    private TextView register;
    private Button login;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getSupportActionBar().hide();

        setContentView(R.layout.activity_home);
//        initialise
        register=(TextView) findViewById(R.id.register);
        login=(Button)findViewById(R.id.calogin);

        //button
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String url="http://ca.splash.in/register/";

                CustomTabsIntent.Builder builder = new CustomTabsIntent.Builder();
                builder.setToolbarColor(ContextCompat.getColor(HomeActivity.this, R.color.colorAccent));
                builder.addDefaultShareMenuItem();
                Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.ic_action_name);
                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.setType("text/plain");
                intent.putExtra(Intent.EXTRA_TEXT, url);
                int requestCode = 100;
                PendingIntent pendingIntent = PendingIntent.getActivity(HomeActivity.this,
                        requestCode,
                        intent,
                        PendingIntent.FLAG_UPDATE_CURRENT);
                builder.setActionButton(bitmap, "Share Link", pendingIntent, true);
                CustomTabsIntent customTabsIntent = builder.build();
                customTabsIntent.launchUrl(HomeActivity.this, Uri.parse(url));
            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(HomeActivity.this,LoginActivity.class));
            }
        });

    }
}
