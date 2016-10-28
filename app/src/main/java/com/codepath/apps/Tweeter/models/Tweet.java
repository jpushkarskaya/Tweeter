package com.codepath.apps.Tweeter.models;

import android.text.format.DateUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * Created by epushkarskaya on 10/27/16.
 */
public class Tweet {

    public static long newestTweetId = 1;
    public static long oldestTweetId = Long.MAX_VALUE;

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
            if (uid < oldestTweetId) {
                oldestTweetId = uid;
            }
            if (uid > newestTweetId) {
                newestTweetId = uid;
            }
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

    public String getRelativeTime() { return getRelativeTimeAgo(timestamp); }

    // getRelativeTimeAgo("Mon Apr 01 21:16:23 +0000 2014");
    private String getRelativeTimeAgo(String rawJsonDate) {
        String twitterFormat = "EEE MMM dd HH:mm:ss ZZZZZ yyyy";
        SimpleDateFormat sf = new SimpleDateFormat(twitterFormat, Locale.ENGLISH);
        sf.setLenient(true);

        String relativeDate = "";
        try {
            long dateMillis = sf.parse(rawJsonDate).getTime();
            relativeDate = DateUtils.getRelativeTimeSpanString(dateMillis,
                    System.currentTimeMillis(), DateUtils.SECOND_IN_MILLIS).toString();
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return relativeDate;
    }

}
