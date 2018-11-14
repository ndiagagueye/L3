package com.example.gueye.memoireprevention2018.modele;

import java.util.Date;

/**
 * Created by gueye on 23/09/18.
 */

public class Likes {
    String user_id;

    Date timestamp;
    public Likes(Date timestamp, String user_id) {
        this.timestamp = timestamp;
        this.user_id = user_id;
    }

    public Likes() {
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }
}
