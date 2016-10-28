package com.codepath.apps.Tweeter.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ListView;

import com.codepath.apps.Tweeter.EndlessScrollListener;
import com.codepath.apps.Tweeter.R;
import com.codepath.apps.Tweeter.adapters.TweetArrayAdapter;
import com.codepath.apps.Tweeter.models.Tweet;
import com.codepath.apps.Tweeter.service.TwitterApplication;
import com.codepath.apps.Tweeter.service.TwitterClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import cz.msebera.android.httpclient.Header;

import static com.codepath.apps.Tweeter.models.Tweet.fromJSONArray;

public class TimelineActivity extends AppCompatActivity {

    private TwitterClient client;
    private ListView lvTweets;
    private List<Tweet> tweets;
    private TweetArrayAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timeline);
        lvTweets = (ListView) findViewById(R.id.lvTweets);
        tweets = new ArrayList<>();
        adapter = new TweetArrayAdapter(this, tweets);
        lvTweets.setAdapter(adapter);
        lvTweets.setOnScrollListener(new EndlessScrollListener() {
            @Override
            public boolean onLoadMore(int page, int totalItemsCount) {
                // Triggered only when new data needs to be appended to the list
                // Add whatever code is needed to append new items to your AdapterView
                populateTimeline(Tweet.oldestTweetId, 0);
                return true; // ONLY if more data is actually being loaded; false otherwise.
            }
        });
        client = TwitterApplication.getRestClient();
        populateTimeline(0, 1);
    }

    // Send an API request to get the timeline json
    private void populateTimeline(long maxId, long sinceId) {
        client.getHomeTimeline(new JsonHttpResponseHandler(){

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray json) {
                Log.d("DEBUG", json.toString());
                adapter.addAll(fromJSONArray(json));
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                Log.d("DEBUG", errorResponse.toString());
            }
        }, maxId, sinceId);

    }

}
