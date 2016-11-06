package com.codepath.apps.Tweeter.models;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

/**
 * Created by epushkarskaya on 10/27/16.
 */
public class User implements Serializable{

    private String name;
    private long uid;
    private String screenName;
    private String profileImageUrl;
    private String tagline;
    private int numTweets;
    private int numFollowers;
    private int numFollowing;

    public User(JSONObject json) {
        try {
            this.name = json.getString("name");
            this.uid = json.getLong("id");
            this.screenName = json.getString("screen_name");
            this.profileImageUrl = json.getString("profile_image_url");
            this.tagline = json.getString("description");
            this.numTweets = json.getInt("statuses_count");
            this.numFollowers = json.getInt("followers_count");
            this.numFollowing = json.getInt("friends_count");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public String getName() {
        return name;
    }

    public long getUid() {
        return uid;
    }

    public String getScreenName() {
        return String.format("@%s", screenName);
    }

    public String getProfileImageUrl() {
        return profileImageUrl;
    }

    public String getTagline() {
        return tagline;
    }

    public int getNumTweets() {
        return numTweets;
    }

    public int getNumFollowers() {
        return numFollowers;
    }

    public int getNumFollowing() {
        return numFollowing;
    }
}
