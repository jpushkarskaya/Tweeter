package com.codepath.apps.Tweeter.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.codepath.apps.Tweeter.R;
import com.codepath.apps.Tweeter.adapters.TweetArrayAdapter;
import com.codepath.apps.Tweeter.models.Tweet;
import com.codepath.apps.Tweeter.service.TwitterApplication;
import com.codepath.apps.Tweeter.service.TwitterClient;

import static android.app.Activity.RESULT_OK;

/**
 * Created by epushkarskaya on 11/5/16.
 */

public class TweetsListFragment extends Fragment {

    private final int COMPOSE_CODE = 20;

    protected ListView lvTweets;
    protected TweetArrayAdapter adapter;
    protected TwitterClient client;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_tweets_list, parent, false);
        lvTweets = (ListView) v.findViewById(R.id.lvTweets);
        lvTweets.setAdapter(adapter);
        return v;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        adapter = new TweetArrayAdapter(getActivity());
        client = TwitterApplication.getRestClient();
        populateTimeline(0, 1);
    }

    protected void populateTimeline(long maxId, long sinceId) {
        // subclasses must override
    }
}
