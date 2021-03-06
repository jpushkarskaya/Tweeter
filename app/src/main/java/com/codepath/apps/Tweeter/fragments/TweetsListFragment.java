package com.codepath.apps.Tweeter.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.codepath.apps.Tweeter.EndlessScrollListener;
import com.codepath.apps.Tweeter.R;
import com.codepath.apps.Tweeter.activities.ProfileActivity;
import com.codepath.apps.Tweeter.adapters.TweetArrayAdapter;
import com.codepath.apps.Tweeter.models.User;
import com.codepath.apps.Tweeter.service.TwitterApplication;
import com.codepath.apps.Tweeter.service.TwitterClient;

/**
 * Created by epushkarskaya on 11/5/16.
 */

public abstract class TweetsListFragment extends Fragment {

    protected ListView lvTweets;
    protected TweetArrayAdapter adapter;
    protected TwitterClient client;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_tweets_list, parent, false);
        lvTweets = (ListView) v.findViewById(R.id.lvTweets);
        lvTweets.setAdapter(adapter);
        lvTweets.setOnScrollListener(new EndlessScrollListener() {
            @Override
            public boolean onLoadMore(int page, int totalItemsCount) {
                // Triggered only when new data needs to be appended to the list
                // Add whatever code is needed to append new items to your AdapterView
                populateTimeline(adapter.getMaxId() - 1);
                return true; // ONLY if more data is actually being loaded; false otherwise.
            }
        });
        lvTweets.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getActivity(), ProfileActivity.class);
                User user = adapter.getItem(position).getUser();
                intent.putExtra("user", user);
                startActivity(intent);
            }
        });
        return v;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        adapter = new TweetArrayAdapter(getActivity());
        client = TwitterApplication.getRestClient();
        populateTimeline(Long.MAX_VALUE - 1);
    }

    protected abstract void populateTimeline(long maxId);

    public TweetArrayAdapter getAdapter(){
        return adapter;
    }

}
