package com.iitbhu.technex18.Activity;

import android.app.PendingIntent;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.customtabs.CustomTabsIntent;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import com.iitbhu.technex18.R;

import static com.iitbhu.technex18.utils1.Constants.LOGIN;

public class HomeActivity extends AppCompatActivity {
    private TextView register;
    private Button login;
    private Button gallery;
    private CardView cardView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
//        getSupportActionBar().hide();

        setContentView(R.layout.activity_home);
//        initialise
        register=(TextView) findViewById(R.id.register);
        login=(Button)findViewById(R.id.calogin);
        gallery=(Button)findViewById(R.id.gallery);
        cardView=(CardView)findViewById(R.id.card_view_home);
        //button
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String url="http://ca.technex.in/register/";

                CustomTabsIntent.Builder builder = new CustomTabsIntent.Builder();
                builder.setToolbarColor(ContextCompat.getColor(HomeActivity.this, R.color.colorAccent));
                builder.addDefaultShareMenuItem();
//                Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.ic_action_name);
                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.setType("text/plain");
                intent.putExtra(Intent.EXTRA_TEXT, url);
                int requestCode = 100;
                PendingIntent pendingIntent = PendingIntent.getActivity(HomeActivity.this,
                        requestCode,
                        intent,
                        PendingIntent.FLAG_UPDATE_CURRENT);
//                builder.setActionButton(bitmap, "Share Link", pendingIntent, true);
                CustomTabsIntent customTabsIntent = builder.build();
                customTabsIntent.launchUrl(HomeActivity.this, Uri.parse(url));
            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivityForResult(new Intent(HomeActivity.this,LoginActivity.class),LOGIN);
                cardView.setVisibility(View.GONE);
            }
        });
        gallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(HomeActivity.this,GridViewActivity.class));
            }
        });

    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        // check if the request code is same as what is passed  here it is 2
        System.out.println("res");
        if(requestCode==LOGIN)
        {
            System.out.println("yo");
            if(data!=null) {
                String message = data.getStringExtra("MESSAGE");
                startActivity(new Intent(this, Dashboard.class));
                finish();
            }

            else{
                cardView.setVisibility(View.VISIBLE);
            }
        }
    }
}
