package com.iitbhu.technex18.fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
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
import com.iitbhu.technex18.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import static com.iitbhu.technex18.helper.URLs.DASHBOARD;
import static com.iitbhu.technex18.helper.URLs.DASHBOARD_UPDATE;
import static com.iitbhu.technex18.utils1.Constants.EMAIL;
import static com.iitbhu.technex18.utils1.Constants.FIRST_NAME;
import static com.iitbhu.technex18.utils1.Constants.LAST_NAME;
import static com.iitbhu.technex18.utils1.Constants.PREFERENCES;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link HomeFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    ImageView banner;
    TextView welcome;
    SharedPreferences myPrefs;
    LinearLayout lldir,llstu;
    CardView carddir,cardstu;
    Button updatedir,updatestu;
    EditText dir,stu;
    RequestQueue queue;
    LinearLayout loading;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public HomeFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HomeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
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
        View v= inflater.inflate(R.layout.fragment_home, container, false);
        myPrefs=this.getActivity().getSharedPreferences(PREFERENCES, Context.MODE_PRIVATE);
        loading=(LinearLayout)v.findViewById(R.id.llhome);
        banner=(ImageView)v.findViewById(R.id.banner);
        welcome=(TextView)v.findViewById(R.id.welcome);
        welcome.setText(Html.fromHtml("Welcome <b>"+myPrefs.getString(FIRST_NAME,"firstName")+" "+ myPrefs.getString(LAST_NAME,"user")+"</b>.<br>You have been selected as Campus Ambassador. Stay Tuned."));
        dir=(EditText)v.findViewById(R.id.dir);
        stu=(EditText)v.findViewById(R.id.stu);
        updatedir=(Button)v.findViewById(R.id.updateDir);
        updatestu=(Button)v.findViewById(R.id.updateStu);
        carddir=(CardView)v.findViewById(R.id.card_view_dir);
        cardstu=(CardView) v.findViewById(R.id.card_view_stu);
        lldir=(LinearLayout)v.findViewById(R.id.lldir);
        llstu=(LinearLayout)v.findViewById(R.id.llstu);
        sendRequest();
        carddir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(lldir.getVisibility()==View.GONE){
                    lldir.setVisibility(View.VISIBLE);

                }
                else{
                    lldir.setVisibility(View.GONE);
                }
            }
        });

        cardstu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(llstu.getVisibility()==View.GONE){
                    llstu.setVisibility(View.VISIBLE);
                }
                else{
                    llstu.setVisibility(View.GONE);
                }
            }
        });

        updatedir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                update();
            }
        });
        updatestu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                update();
            }
        });
        return v;
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




    private void sendRequest(){
//        final ProgressDialog pDialog = new ProgressDialog(getActivity());
//        pDialog.setMessage("Loading...");
//        pDialog.show();
        loading.setVisibility(View.VISIBLE);
        final String TAG = "TECHNEX DASHBOARD";
        queue = Volley.newRequestQueue(getActivity());

        Map<String, String> params = new HashMap<String, String>();
        params.put("email",myPrefs.getString(EMAIL,EMAIL));
        String url=DASHBOARD;
        JsonObjectRequest strReq = new JsonObjectRequest(Request.Method.POST,
                url, new JSONObject(params), new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject resp) {
                Log.d(TAG, resp.toString());


                try {
                    Log.d(TAG, "Dashboard parser executed!");

                    int status = resp.getInt("status");
                    //save and and to my server
                    if (status == 1) {
                        String director=resp.getString("directordetail").toString();
                        String student= resp.getString("studentbodydetail").toString();
                        dir.setText(director);
                        stu.setText(student);
                    } else if (status == 0) {
                        Log.d(TAG,"wrong method");
                    }else if(status==4){
                        Toast.makeText(getActivity(), "Update Student Head and Director details", Toast.LENGTH_SHORT).show();
                    }
                    else if(status==2){
                        String student= resp.getString("studentbodydetail").toString();
                        stu.setText(student);
                        Toast.makeText(getActivity(), "Update Director Detail", Toast.LENGTH_SHORT).show();

                    }
                    else if(status==3){
                        String director=resp.getString("directordetail").toString();
                        dir.setText(director);
                        Toast.makeText(getActivity(), "Update Student Head Detail", Toast.LENGTH_SHORT).show();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();

                }
                loading.setVisibility(View.GONE);

            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(TAG, "Error: " + error.getMessage());
                System.out.println("Error: "+ error.getMessage());

                loading.setVisibility(View.GONE);

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


    public void update(){
        loading.setVisibility(View.VISIBLE);
        final String TAG = "TECHNEX DASHBOARD UPDATE";
        queue = Volley.newRequestQueue(getActivity());

        Map<String, String> params = new HashMap<String, String>();
        params.put("email",myPrefs.getString(EMAIL,EMAIL));
        params.put("directordetail",dir.getText().toString().trim());
        params.put("studentbodydetail",stu.getText().toString().trim());
        String url=DASHBOARD_UPDATE;
        JsonObjectRequest strReq = new JsonObjectRequest(Request.Method.POST,
                url, new JSONObject(params), new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject resp) {
                Log.d(TAG, resp.toString());


                try {
                    Log.d(TAG, "Dashboard Update parser executed!");

                    int status = resp.getInt("status");
                    //save and and to my server
                    if (status == 1||status==2) {
                        Toast.makeText(getActivity(), "Update Successful", Toast.LENGTH_SHORT).show();
//                        Toast.makeText(getActivity(), "Cannot Update", Toast.LENGTH_SHORT).show();

                    }
                } catch (JSONException e) {
                    e.printStackTrace();

                }
                loading.setVisibility(View.GONE);

            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(TAG, "Error: " + error.getMessage());
                System.out.println("Error: "+ error.getMessage());

                loading.setVisibility(View.GONE);

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
