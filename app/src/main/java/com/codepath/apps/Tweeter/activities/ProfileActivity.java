package com.codepath.apps.Tweeter.activities;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.codepath.apps.Tweeter.R;
import com.codepath.apps.Tweeter.fragments.UserTimelineFragment;
import com.codepath.apps.Tweeter.models.User;
import com.codepath.apps.Tweeter.service.TwitterApplication;
import com.codepath.apps.Tweeter.service.TwitterClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.squareup.picasso.Picasso;

import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

public class ProfileActivity extends AppCompatActivity {

    TwitterClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        User user = (User) getIntent().getSerializableExtra("user");
        String screenname = user == null ? null : user.getScreenName();
        if (user == null) {
            client = TwitterApplication.getRestClient();
            client.getUserInfo(new JsonHttpResponseHandler() {

                @Override
                public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                    User user = new User(response);
                    getSupportActionBar().setTitle(user.getScreenName());
                    populateProfileHeader(user);
                }

            });
        } else {
            getSupportActionBar().setTitle(user.getScreenName());
            populateProfileHeader(user);
        }

        if (savedInstanceState == null) {
            UserTimelineFragment fragment = UserTimelineFragment.newInstance(screenname);
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.flContainer, fragment);
            ft.commit();
        }



    }

    private void populateProfileHeader(User user){
        ImageView ivProfileImage = (ImageView) findViewById(R.id.ivProfileImage);
        Picasso.with(this).load(user.getProfileImageUrl()).into(ivProfileImage);
        TextView tvName = (TextView) findViewById(R.id.tvName);
        tvName.setText(user.getName());
        TextView tvTagline = (TextView) findViewById(R.id.tvTagline);
        tvTagline.setText(user.getTagline());

        TextView tvTweets = (TextView) findViewById(R.id.tvTweets);
        tvTweets.setText(String.valueOf(user.getNumTweets()));
        TextView tvFollowing = (TextView) findViewById(R.id.tvFollowing);
        tvFollowing.setText(String.valueOf(user.getNumFollowing()));
        TextView tvFollowers = (TextView) findViewById(R.id.tvFollowers);
        tvFollowers.setText(String.valueOf(user.getNumFollowers()));
    }

}
