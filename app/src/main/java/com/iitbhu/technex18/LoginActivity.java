package com.iitbhu.technex18;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import static com.iitbhu.technex18.utils1.Constants.COLLEGE;
import static com.iitbhu.technex18.utils1.Constants.CONTACT;
import static com.iitbhu.technex18.utils1.Constants.EMAIL;
import static com.iitbhu.technex18.utils1.Constants.NAME;
import static com.iitbhu.technex18.utils1.Constants.PASSWORD;
import static com.iitbhu.technex18.utils1.Constants.PAST_EXP;
import static com.iitbhu.technex18.utils1.Constants.PIN;
import static com.iitbhu.technex18.utils1.Constants.PREFERENCES;
import static com.iitbhu.technex18.utils1.Constants.REGISTERED;
import static com.iitbhu.technex18.utils1.Constants.WHATSAPP_NUMBER;
import static com.iitbhu.technex18.utils1.Constants.WHY_CHOOSE_YOU;
import static com.iitbhu.technex18.utils1.Constants.YEAR;
import static networkuse.URLs.LOGIN_URL;

public class LoginActivity extends AppCompatActivity {
    private TextInputEditText username;
    private TextInputEditText password;
    private TextInputLayout userLayout;
    private TextInputLayout passLayout;
    private CardView cardView;
    private Button login;
    private RequestQueue queue;
    private boolean data;
    SharedPreferences myprefs;
    private ProgressDialog progress;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
//        getSupportActionBar().hide();
        setContentView(R.layout.activity_login);
        //initialize
        cardView=(CardView)findViewById(R.id.card_view_login);
        login=(Button)findViewById(R.id.login);
        password=(TextInputEditText)findViewById(R.id.password);
        username=(TextInputEditText)findViewById(R.id.username);
        userLayout=(TextInputLayout)findViewById(R.id.userlayout);
        passLayout=(TextInputLayout)findViewById(R.id.passlayout);
        data=true;
        myprefs=getSharedPreferences(PREFERENCES, Context.MODE_PRIVATE);
        username.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                userLayout.setError(null);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        password.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                passLayout.setError(null);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(username.getText().toString().isEmpty()){
                    userLayout.setErrorEnabled(true);
                    userLayout.setError(getString(R.string.error));
                    data=false;
                }
               else if(!username.getText().toString().contains("@")){
                    userLayout.setError("Enter correct email ID");
                    data=false;
                }
                else if(password.getText().toString().isEmpty()){
                    passLayout.setError(getString(R.string.error));

                }
                else{
                    cardView.setVisibility(View.GONE);
                    Log.d(LoginActivity.class.getSimpleName(), "clicked");

                    progress = new ProgressDialog(LoginActivity.this);
                    Log.d(LoginActivity.class.getSimpleName(), "progressdialog");

                    progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                    progress.setMessage("Signing In...");
                    progress.setCancelable(false);
                    progress.setIndeterminate(true);
                    progress.show();
                  actionLogin(username.getText().toString(), password.getText().toString());
                }
            }
        });
    }
    public void actionLogin(final String email, final String password) {

        final String TAG = "TECHNEX NETWORK LOGIN";

        queue = Volley.newRequestQueue(getBaseContext());


        String url = LOGIN_URL;
        // mViewPager.setVisibility(View.GONE);
        //menuProgressLayout.setVisibility(View.VISIBLE);


        Map<String, String> params = new HashMap<String, String>();
        params.put("email", email.trim());
        params.put("password", password.trim());
        Log.d("PARAMS LOGIN", params.toString());

        JsonObjectRequest strReq = new JsonObjectRequest(Request.Method.POST,
                url, new JSONObject(params), new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject resp) {
                Log.d(TAG, resp.toString());


                try {
                    Log.d(TAG, "Login parser executed!");

                    int status = resp.getInt("status");
                    //save and and to my server
                    if (status == 1) {

                        SharedPreferences.Editor editor=myprefs.edit();
                        editor.putBoolean(REGISTERED,true);
                        editor.putString(EMAIL, email);
                        editor.putString(PASSWORD, password);
                        editor.putString(WHATSAPP_NUMBER, resp.getString("whatsappNumber"));
                        editor.putString(PAST_EXP,resp.getString("pastExp"));
                        editor.putString(WHY_CHOOSE_YOU,resp.getString("whyChooseYou"));
                        editor.putString(PIN, resp.getString("pinCode"));
                        editor.putString(COLLEGE, resp.getString("college"));
                        editor.putString(CONTACT, resp.getString("mobileNumber"));
                        editor.putString(NAME, resp.getString("name"));
                        editor.putString(YEAR, resp.getString("year"));
                        editor.commit();
                        Toast.makeText(LoginActivity.this, "Login Successful", Toast.LENGTH_SHORT).show();
                        progress.dismiss();

                        Intent intent = new Intent(getBaseContext(), HomeActivity.class);
                        startActivity(intent);
                        finish();

                    } else if (status == 0) {
                        cardView.setVisibility(View.VISIBLE);
                        progress.dismiss();
                        Toast.makeText(getBaseContext(), "CA profile not chosen.", Toast.LENGTH_LONG).show();
                    } else if (status == 3) {
                        cardView.setVisibility(View.VISIBLE);
                        progress.dismiss();
                        Toast.makeText(getBaseContext(), "Wrong email/password",Toast.LENGTH_LONG).show();
                    } else if (status == 2) {
                        cardView.setVisibility(View.VISIBLE);
                        progress.dismiss();
                        Toast.makeText(getBaseContext(), "Inactive User",Toast.LENGTH_LONG).show();
                    }

                    else{
                        cardView.setVisibility(View.VISIBLE);
                        progress.dismiss();
                        Toast.makeText(getBaseContext(), "Some error occurred. Please try again.",Toast.LENGTH_SHORT).show();
                    }
                    Log.d(TAG, "Login parser executed properly!");
                } catch (JSONException e) {
                    progress.dismiss();
                    e.printStackTrace();
                    Log.d(TAG, "Login parser failed!");
                }


            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(TAG, "Error: " + error.getMessage());
                System.out.println("Error: "+ error.getMessage());
                cardView.setVisibility(View.VISIBLE);
                progress.dismiss();
                Toast.makeText(getBaseContext(),"Network Unreachable!",Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headers = new HashMap<String, String>();
                headers.put("Content-Type", "application/json");
                Log.d("HEADERS LOGIN", headers.toString());
                return headers;
            }
        };
        strReq.setRetryPolicy(new DefaultRetryPolicy(
                30000,
                0,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        queue.add(strReq);

    }



}
