package com.codepath.apps.Tweeter;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ListView;

import com.codepath.apps.Tweeter.models.Tweet;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import cz.msebera.android.httpclient.Header;

import static com.codepath.apps.Tweeter.models.Tweet.fromJSONArray;

public class TimelineActivity extends AppCompatActivity {

    private TwitterClient client;
    ListView lvTweets;
    List<Tweet> tweets;
    TweetArrayAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timeline);
        lvTweets = (ListView) findViewById(R.id.lvTweets);
        tweets = new ArrayList<>();
        adapter = new TweetArrayAdapter(this, tweets);
        lvTweets.setAdapter(adapter);
        client = TwitterApplication.getRestClient();
        populateTimeline();
    }

    // Send an API request to get the timeline json
    private void populateTimeline() {
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
        });
    }

}
