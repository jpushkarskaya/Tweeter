package com.codepath.apps.Tweeter.models;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by epushkarskaya on 10/27/16.
 */
public class User {

    private String name;
    private long uid;
    private String screenName;
    private String profileImageUrl;

    public User(JSONObject json) {
        try {
            this.name = json.getString("name");
            this.uid = json.getLong("id");
            this.screenName = json.getString("screen_name");
            this.profileImageUrl = json.getString("profile_image_url");
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
        return screenName;
    }

    public String getProfileImageUrl() {
        return profileImageUrl;
    }

}
