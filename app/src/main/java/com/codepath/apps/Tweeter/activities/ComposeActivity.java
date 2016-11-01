package com.codepath.apps.Tweeter.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.codepath.apps.Tweeter.R;
import com.codepath.apps.Tweeter.models.Tweet;
import com.codepath.apps.Tweeter.models.User;
import com.codepath.apps.Tweeter.service.TwitterApplication;
import com.codepath.apps.Tweeter.service.TwitterClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

public class ComposeActivity extends AppCompatActivity {

    private TwitterClient client;
    EditText etTweet;
    Button btnCompose;
    TextView tvCharacterCounter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compose);

        etTweet = (EditText) findViewById(R.id.etTweet);
        btnCompose = (Button) findViewById(R.id.btnCompose);

        tvCharacterCounter = (TextView) findViewById(R.id.tvCharacterCounter);

        etTweet.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                int length = s.length();
                tvCharacterCounter.setText(String.format("%s/140", length));
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

         client = TwitterApplication.getRestClient();
    }

    public void onTweet(View view) {
        final String tweetText = etTweet.getText().toString();
        client.composeTweet(new JsonHttpResponseHandler(){

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject json) {
                try {
                    User user = new User(json.getJSONObject("user"));
                    Tweet tweet = new Tweet();
                    tweet.setBody(tweetText);
                    tweet.setUser(user);
                    tweet.setTimestamp(json.getString("created_at"));
                    tweet.setUid(json.getLong("id"));

                    Intent data = new Intent();
                    // Pass relevant data back as a result
                    data.putExtra("tweet", tweet);
                    data.putExtra("code", 200); // ints work too

                    // Activity finished ok, return the data
                    setResult(RESULT_OK, data); // set result code and bundle data for response
                    finish();

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                Log.d("DEBUG", errorResponse.toString());
            }
        }, tweetText);
    }

}
