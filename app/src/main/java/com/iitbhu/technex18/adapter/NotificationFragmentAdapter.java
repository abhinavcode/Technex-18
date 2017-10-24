package com.iitbhu.technex18.adapter;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
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
import com.iitbhu.technex18.container.Notification;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static android.view.View.GONE;
import static com.iitbhu.technex18.helper.URLs.DASHBOARD_UPDATE;
import static com.iitbhu.technex18.utils1.Constants.EMAIL;
import static com.iitbhu.technex18.utils1.Constants.PREFERENCES;

/**
 * Created by abhinav on 20/10/17.
 */

public class NotificationFragmentAdapter  extends RecyclerView.Adapter<NotificationFragmentAdapter.ViewHolder>{
    SharedPreferences myPrefs;


    Context context;
    LinearLayout progresslayout;
    private RequestQueue queue;

    private List<Notification> mDataset;

    public  NotificationFragmentAdapter(List<Notification> myDataset,Context context, LinearLayout progresslayout) {
        mDataset = myDataset;
        this.context=context;
        this.progresslayout=progresslayout;
    }

    public void add(int position, Notification item) {
        mDataset.add(position, item);
        notifyItemInserted(position);
    }

    public void remove(Notification item) {
        int position = mDataset.indexOf(item);
        mDataset.remove(position);
        notifyItemRemoved(position);
    }

    @Override
    public NotificationFragmentAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                                    int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_layout_notification, parent, false);
//        RecyclerView.LayoutParams lp = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
//        v.setLayoutParams(lp);
        ViewHolder vh = new ViewHolder(v);
        return vh;

    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
//        System.out.println("hello"+mDataset.get(position).getRank());
        holder.msg.setText(""+mDataset.get(position).getMessage());
        holder.time.setText(""+mDataset.get(position).getDate());
//        holder.point.setText(""+mDataset.get(position).getPoints());
        holder.cardView.setBackgroundColor(Color.WHITE);
        holder.time.setTextColor(Color.DKGRAY);
        holder.read.setVisibility(GONE);
        Boolean b=mDataset.get(position).getRead();
        if(!b){
           holder.read.setVisibility(View.VISIBLE);
            holder.time.setTextColor(context.getResources().getColor(R.color.colorPrimary));
            holder.cardView.setBackgroundColor(context.getResources().getColor(R.color.colorBGNotif));
            holder.cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    progresslayout.setVisibility(View.VISIBLE);
                   int x= sendRequest(position,holder);
                    System.out.println("status is "+ x);
                    progresslayout.setVisibility(GONE);


                }
            });

            holder.read.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    progresslayout.setVisibility(View.VISIBLE);
                    int x= sendRequest(position,holder);
                    progresslayout.setVisibility(GONE);

                }
            });

        }

    }

    @Override
    public int getItemCount() {
        return mDataset.size();

    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView msg;
        public TextView time;
        public ImageButton read;
        public CardView cardView;
        public ViewHolder(View v) {
            super(v);
            cardView=(CardView)v.findViewById(R.id.card_view_notification);
            msg=(TextView)v.findViewById(R.id.notification);
            time=(TextView)v.findViewById(R.id.date);
            read=(ImageButton) v.findViewById(R.id.mark_as_read);
            cardView.setBackgroundColor(Color.WHITE);
//            imageView.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    int position = getAdapterPosition();
//                    System.out.println("position "+position);
//
//                    System.out.println("link "+mDataset.get(position).getHyperlinkimg());
//                    String url = mDataset.get(position).getHyperlinkimg();
//                    Intent i = new Intent(Intent.ACTION_VIEW);
//                    i.setData(Uri.parse(url));
//                    System.out.println("url "+url);
//                    v.getContext().startActivity(i);
//                }
//            });

//            text.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    int position = getAdapterPosition();
//                    System.out.println("position "+position);
//
//                    System.out.println("link "+mDataset.get(position).getHyperlinkimg());
////                    Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(mDataset.get(position).getHyperlinkimg()));
////                    imageView.getContext().startActivity(browserIntent);
//                }
//            });
        }




    }

    int sendRequest(int position, final ViewHolder holder){
//        final ProgressDialog pDialog = new ProgressDialog(getActivity());
//        pDialog.setMessage("Loading...");
//        pDialog.show();
        final String TAG = "TECHNEX UPDATE NOTIF";
        queue = Volley.newRequestQueue(context);
        myPrefs=context.getSharedPreferences(PREFERENCES, Context.MODE_PRIVATE);
        String s=myPrefs.getString(EMAIL,EMAIL);
        String url=DASHBOARD_UPDATE;
        JSONObject object=new JSONObject();
        JSONObject objectnotif=new JSONObject();
        JSONArray notifarray=new JSONArray();
        try {
            object.put("email",s);
            objectnotif.put("creation_time",mDataset.get(position).getTime());
            objectnotif.put("mark_read",true);
            notifarray.put(objectnotif);
            object.put("notification",notifarray);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        final int[] status = {0};

//            System.out.println("yo"+LEADERBOARD);
        System.out.println("obj "+object.toString());
        JsonObjectRequest strReq = new JsonObjectRequest(Request.Method.POST,
                url, object, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject resp) {
                Log.d(TAG, resp.toString());

                try {
                    Log.d(TAG, "Update parser executed!");

                    status[0] = resp.getInt("status");

                    System.out.println("status[0] " + status[0]);
                    //save and and to my server
                    int x=status[0];
                    progresslayout.setVisibility(GONE);
                    if(x==1){
                        holder.read.setVisibility(GONE);
                        holder.cardView.setBackgroundColor(Color.WHITE);
                    }
                    else{
                        Toast.makeText(context, "Couldn't mark", Toast.LENGTH_SHORT).show();
                    }

                    Log.d(TAG, "Update parser executed properly!");

                } catch (JSONException e) {
                    e.printStackTrace();
                    Log.d(TAG, "Update parser failed!");
//                        spinner.setVisibility(View.GONE);

                }


            }

        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(TAG, "Error: " + error.getMessage());
                System.out.println("Error: "+ error.getMessage());
//                    Toast.makeText(getActivity(),"Network Unreachable!",Toast.LENGTH_SHORT).show();
//                    spinner.setVisibility(View.GONE);

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

        return status[0];
    }


}