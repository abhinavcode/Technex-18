package com.iitbhu.technex18.JSONParses;

import com.iitbhu.technex18.container.Notification;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by abhinav on 21/10/17.
 */

public class JSONParseNotification {
    public static String[] times;
    public static String[] msg;
    public static Boolean[] read;
    private JSONArray notification = null;


    List<Notification> items ;


    private String json;

    public void parseJSONNotification(String json){
//        JSONObject jsonObject=null;
        this.json = json;
        try {

            notification = new JSONArray(json);


            times = new String[notification.length()];
            msg= new String[notification.length()];

            read= new Boolean[notification.length()];
            items = new ArrayList<Notification>();



            for(int i=0;i< notification.length();i++){
                Notification object =  new Notification();

                JSONObject jsonObject = notification.getJSONObject(i);

                msg[i] = jsonObject.getString("message");
                times[i] = jsonObject.getString("creation_time");
                read[i]=jsonObject.getBoolean("mark_read");
                object.setMessage(msg[i]);
                object.setRead(read[i]);
                object.setTime(times[i]);


//                  System.out.println("link is "+hyperlink[i]);
                items.add(object);



            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    public List<Notification> getItems()
    {
        //function to return the final populated list
        return items;
    }


}

