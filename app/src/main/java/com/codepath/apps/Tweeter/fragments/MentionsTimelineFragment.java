package com.codepath.apps.Tweeter.fragments;

import android.util.Log;

import com.codepath.apps.Tweeter.models.Tweet;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

/**
 * Created by epushkarskaya on 11/5/16.
 */

public class MentionsTimelineFragment extends TweetsListFragment {

    @Override
    protected void populateTimeline(long maxId, long sinceId) {
        client.getMentionsTimeline(new JsonHttpResponseHandler(){

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray json) {
                Log.d("DEBUG", json.toString());;
                adapter.addAll(Tweet.fromJSONArray(json));
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                Log.d("DEBUG", errorResponse.toString());
            }
        });

    }
}
