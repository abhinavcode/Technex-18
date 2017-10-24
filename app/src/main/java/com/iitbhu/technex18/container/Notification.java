package com.iitbhu.technex18.container;

/**
 * Created by abhinav on 21/10/17.
 */

public class Notification {
    String message;
    String time;
    Boolean read;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public Boolean getRead() {
        return read;
    }

    public void setRead(Boolean read) {
        this.read = read;
    }
    public String getDate(){
        String []s=this.time.split(" ");
        return s[0];
    }
}
