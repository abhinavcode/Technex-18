package com.iitbhu.technex18.fragment;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ScrollView;
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
import com.iitbhu.technex18.Activity.LoginActivity;
import com.iitbhu.technex18.R;
import com.iitbhu.technex18.helper.URLs;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import static com.iitbhu.technex18.R.id.submit;
import static com.iitbhu.technex18.utils1.Constants.ADDRESS;
import static com.iitbhu.technex18.utils1.Constants.COLLEGE;
import static com.iitbhu.technex18.utils1.Constants.CONTACT;
import static com.iitbhu.technex18.utils1.Constants.EMAIL;
import static com.iitbhu.technex18.utils1.Constants.FIRST_NAME;
import static com.iitbhu.technex18.utils1.Constants.LAST_NAME;
import static com.iitbhu.technex18.utils1.Constants.PIN;
import static com.iitbhu.technex18.utils1.Constants.PREFERENCES;
import static com.iitbhu.technex18.utils1.Constants.REGISTERED;
import static com.iitbhu.technex18.utils1.Constants.WHATSAPP_NUMBER;
import static com.iitbhu.technex18.utils1.Constants.YEAR;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ProfileFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ProfileFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ProfileFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    TextInputEditText firstName;
    TextInputEditText lastName;
    TextInputEditText email;
    TextInputEditText mobileNumber;
    TextInputEditText whatsappNumber;
    TextInputEditText postalAddress;
    TextInputEditText pinCode;
    TextInputEditText year;
    SharedPreferences myPrefs;
    CardView cardView;
    ScrollView scrollView;
    private RequestQueue queue;
    private ProgressDialog progress;

    Button button;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public ProfileFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ProfileFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ProfileFragment newInstance(String param1, String param2) {
        ProfileFragment fragment = new ProfileFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v= inflater.inflate(R.layout.fragment_profile, container, false);
        myPrefs=this.getActivity().getSharedPreferences(PREFERENCES, Context.MODE_PRIVATE);
        scrollView=(ScrollView)v.findViewById(R.id.scrollview_profile);
        cardView=(CardView)v.findViewById(R.id.card_view_profile);
        firstName=(TextInputEditText)v.findViewById(R.id.first_name);
        button=(Button)v.findViewById(submit);
        lastName=(TextInputEditText)v.findViewById(R.id.last_name);
        email=(TextInputEditText)v.findViewById(R.id.email);
        pinCode=(TextInputEditText)v.findViewById(R.id.pincode);
        whatsappNumber=(TextInputEditText)v.findViewById(R.id.whatsapp);
        mobileNumber=(TextInputEditText)v.findViewById(R.id.mobile);
        year=(TextInputEditText)v.findViewById(R.id.year);
        postalAddress=(TextInputEditText)v.findViewById(R.id.postal);
        firstName.setText(myPrefs.getString(FIRST_NAME,"user"));
        lastName.setText(myPrefs.getString(LAST_NAME,"user"));
        email.setText(myPrefs.getString(EMAIL,"email"));
        pinCode.setText(myPrefs.getString(PIN,PIN));
        whatsappNumber.setText(myPrefs.getString(WHATSAPP_NUMBER,WHATSAPP_NUMBER));
        mobileNumber.setText(myPrefs.getString(CONTACT,CONTACT));
        year.setText(myPrefs.getString(YEAR,YEAR));
        postalAddress.setText(myPrefs.getString(COLLEGE,COLLEGE));
        button.bringToFront();

        DisplayMetrics displayMetrics = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int height = displayMetrics.heightPixels;
        int width=displayMetrics.widthPixels;
//        scrollView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,height/2));
//        scrollView.getLayoutParams().height=height/2;
        cardView.getLayoutParams().height=height/2+height/8;
        cardView.getLayoutParams().width=(3*width)/4;
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                update();
            }
        });
        return v;


    }
    public void update(){
        progress = new ProgressDialog(getActivity());
        Log.d(LoginActivity.class.getSimpleName(), "progressdialog");

        progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progress.setMessage("Updating...");
        progress.setCancelable(false);
        progress.setIndeterminate(true);
        progress.show();
        cardView.setVisibility(View.GONE);

        final String TAG = "TECHNEX PROFILE UPDATE";

        queue = Volley.newRequestQueue(getActivity().getBaseContext());
        String url= URLs.EDIT_PROFILE;
        JSONObject object=new JSONObject();
//        Map<String, String> params = new HashMap<String, String>();
        try {
            object.put("email", email.getText().toString().trim());

            object.put("first_name", firstName.getText().toString().trim());
            object.put("last_name", lastName.getText().toString().trim());
            object.put("mobileNumber", Long.parseLong(mobileNumber.getText().toString().trim()));
            object.put("whatsappNumber", Long.parseLong(whatsappNumber.getText().toString().trim()));
            object.put("pinCode", Long.parseLong(pinCode.getText().toString().trim()));
            object.put("postal_address",postalAddress.getText().toString().trim());
            object.put("year",Integer.parseInt(year.getText().toString().trim()));
        } catch (JSONException e) {
            e.printStackTrace();
        }
//        Log.d("PARAMS LOGIN", params.toString());

        JsonObjectRequest strReq = new JsonObjectRequest(Request.Method.POST,
                url,object, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject resp) {
                Log.d(TAG, resp.toString());


                try {
                    Log.d(TAG, "Login parser executed!");

                    String status = resp.getString("status");
                    //save and and to my server
                    if (status.equals("OK")) {

                        SharedPreferences.Editor editor=myPrefs.edit();
                        editor.putBoolean(REGISTERED,true);
                        editor.putString(EMAIL, email.getText().toString().trim());
                        editor.putString(FIRST_NAME, firstName.getText().toString().trim());
                        editor.putString(LAST_NAME, lastName.getText().toString().trim());
                        editor.putString(CONTACT, mobileNumber.getText().toString().trim());
                        editor.putString(WHATSAPP_NUMBER, whatsappNumber.getText().toString().trim());
                        editor.putString(PIN, pinCode.getText().toString().trim());
                        editor.putString(ADDRESS, postalAddress.getText().toString().trim());
                        editor.putString(YEAR, year.getText().toString().trim());
                        editor.commit();
                        Toast.makeText(getActivity(), "Update Successful", Toast.LENGTH_SHORT).show();
                        progress.dismiss();
                        cardView.setVisibility(View.VISIBLE);

//                        Intent intent = new Intent(getBaseContext(), HomeActivity.class);
//                        startActivity(intent);

                    } else  {
                        cardView.setVisibility(View.VISIBLE);
                        progress.dismiss();
                        cardView.setVisibility(View.VISIBLE);

                        Toast.makeText(getActivity(), "Cannot Update", Toast.LENGTH_LONG).show();
                    }
                } catch (JSONException e) {
                    cardView.setVisibility(View.VISIBLE);

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
                Toast.makeText(getActivity(),"Network Unreachable!",Toast.LENGTH_SHORT).show();
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




    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
//        if (context instanceof OnFragmentInteractionListener) {
//            mListener = (OnFragmentInteractionListener) context;
//        } else {
//            throw new RuntimeException(context.toString()
//                    + " must implement OnFragmentInteractionListener");
//        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }


}
