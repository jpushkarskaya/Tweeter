package com.codepath.apps.Tweeter.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.codepath.apps.Tweeter.EndlessScrollListener;
import com.codepath.apps.Tweeter.models.Tweet;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

/**
 * Created by epushkarskaya on 11/5/16.
 */

public class HomeTimelineFragment extends TweetsListFragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        View v  = super.onCreateView(inflater, parent, savedInstanceState);
        lvTweets.setOnScrollListener(new EndlessScrollListener() {
            @Override
            public boolean onLoadMore(int page, int totalItemsCount) {
                // Triggered only when new data needs to be appended to the list
                // Add whatever code is needed to append new items to your AdapterView
                populateTimeline(Tweet.oldestTweetId - 1, 0);
                return true; // ONLY if more data is actually being loaded; false otherwise.
            }
        });
        return v;
    }

    @Override
    protected void populateTimeline(long maxId, long sinceId) {
        client.getHomeTimeline(new JsonHttpResponseHandler(){

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray json) {
                Log.d("DEBUG", json.toString());;
                adapter.addAll(Tweet.fromJSONArray(json));
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                Log.d("DEBUG", errorResponse.toString());
            }
        }, maxId, sinceId);

    }
}