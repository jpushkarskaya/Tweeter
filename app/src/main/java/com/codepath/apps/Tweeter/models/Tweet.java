package com.codepath.apps.Tweeter.models;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by epushkarskaya on 10/27/16.
 */
public class Tweet {

    private String body;
    private long uid;
    private User user;
    private String timestamp;

    public Tweet(JSONObject json) {
        try {
            this.body = json.getString("text");
            this.uid = json.getLong("id");
            this.user = new User(json.getJSONObject("user"));
            this.timestamp = json.getString("created_at");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public static List<Tweet> fromJSONArray(JSONArray array) {
        List<Tweet> tweets = new ArrayList<>();

        for (int i = 0; i < array.length(); i++) {
            try {
                tweets.add(new Tweet(array.getJSONObject(i)));
            } catch (JSONException e) {
                e.printStackTrace();
                continue;
            }
        }

        return tweets;
    }

    public String getBody() {
        return body;
    }

    public long getUid() {
        return uid;
    }

    public User getUser() {
        return user;
    }

    public String getTimestamp() {
        return timestamp;
    }

}
