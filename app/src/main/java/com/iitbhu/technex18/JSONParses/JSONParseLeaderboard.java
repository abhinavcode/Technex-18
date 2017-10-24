package com.iitbhu.technex18.JSONParses;

import com.iitbhu.technex18.container.LeaderBoard;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by abhinav on 16/10/17.
 */

public class JSONParseLeaderboard {
    public static int[] ranks;
    public static int[] points;
    public static String[] email;
    private JSONArray leaderboard = null;


    List<LeaderBoard> items ;


    private String json;

    public void parseJSONLeaderboard(String json){
//        JSONObject jsonObject=null;
        this.json = json;
        try {

            leaderboard = new JSONArray(json);


            ranks = new int[leaderboard.length()];
            points= new int[leaderboard.length()];

            email= new String[leaderboard.length()];
            items = new ArrayList<LeaderBoard>();



            for(int i=0;i< leaderboard.length();i++){
                LeaderBoard object =  new LeaderBoard();

                JSONObject jsonObject = leaderboard.getJSONObject(i);

                ranks[i] = jsonObject.getInt("rank");
                points[i] = jsonObject.getInt("points");
                email[i]=jsonObject.getString("username");
                object.setEmail(email[i]);
                object.setRank(ranks[i]);
                object.setPoints(points[i]);
//                System.out.println("link is "+hyperlink[i]);
                items.add(object);



            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    public List<LeaderBoard> getItems()
    {
        //function to return the final populated list
        return items;
    }


}
