package com.iitbhu.technex18.adapter;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.iitbhu.technex18.R;
import com.iitbhu.technex18.container.LeaderBoard;
import com.iitbhu.technex18.fragment.NotificationFragment;

import java.util.List;

import static com.iitbhu.technex18.utils1.Constants.PREFERENCES;

/**
 * Created by abhinav on 21/10/17.
 */

public class NotificationFragmentAdapter extends RecyclerView.Adapter<NotificationFragmentAdapter.ViewHolder>{


        Context context;
    private List<LeaderBoard> mDataset;

    public  NotificationFragmentAdapter(List<LeaderBoard> myDataset,Context context) {
        mDataset = myDataset;
        this.context=context;
    }

    public void add(int position, LeaderBoard item) {
        mDataset.add(position, item);
        notifyItemInserted(position);
    }

    public void remove(LeaderBoard item) {
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
    public void onBindViewHolder(ViewHolder holder, int position) {
//        System.out.println("hello"+mDataset.get(position).getRank());
        holder.rank.setText(""+mDataset.get(position).getRank());
        holder.email.setText(""+mDataset.get(position).getEmail());
        holder.point.setText(""+mDataset.get(position).getPoints());
        holder.cardView.setBackgroundColor(Color.WHITE);

        SharedPreferences myprefs=context.getSharedPreferences(PREFERENCES, Context.MODE_PRIVATE);
        String s=myprefs.getString(EMAIL,"user");
        String s1=mDataset.get(position).getEmail();
        if((s).equals(s1)){
//            System.out.println("eq"+s+s1);
//            holder.rank.setTypeface(Typeface.DEFAULT_BOLD);
//            holder.email.setTypeface(Typeface.DEFAULT_BOLD);
//            holder.point.setTypeface(Typeface.DEFAULT_BOLD);
            holder.cardView.setBackgroundColor(Color.LTGRAY);
        }
    }

    @Override
    public int getItemCount() {
        return mDataset.size();

    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView rank;
        public TextView email;
        public TextView point;
        public CardView cardView;
        public ViewHolder(View v) {
            super(v);
            cardView=(CardView)v.findViewById(R.id.card_view_leaderboard);
            rank=(TextView)v.findViewById(R.id.rank);
            email=(TextView)v.findViewById(R.id.email);
            point=(TextView)v.findViewById(R.id.point);
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



}
