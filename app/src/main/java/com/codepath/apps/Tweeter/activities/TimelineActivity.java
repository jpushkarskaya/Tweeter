package com.codepath.apps.Tweeter.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

import com.codepath.apps.Tweeter.R;
import com.codepath.apps.Tweeter.adapters.TweetArrayAdapter;
import com.codepath.apps.Tweeter.fragments.TweetsListFragment;
import com.codepath.apps.Tweeter.models.Tweet;

public class TimelineActivity extends AppCompatActivity {

    private final int COMPOSE_CODE = 20;

    private TweetsListFragment fragmentTweetsList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timeline);

        if (savedInstanceState == null) {
            fragmentTweetsList = (TweetsListFragment) getSupportFragmentManager().findFragmentById(R.id.fragment_timeline);
        }
    }

    // Inflate the menu; this adds items to the action bar if it is present.
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_timeline, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_compose) {
            Intent intent = new Intent(this, ComposeActivity.class);
            startActivityForResult(intent, COMPOSE_CODE);
        }
        return super.onOptionsItemSelected(item);
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // COMPOSE_CODE is defined above
        if (resultCode == RESULT_OK && requestCode == COMPOSE_CODE) {

            // Extract name value from result extras
            Tweet tweet = (Tweet) data.getExtras().getSerializable("tweet");
            int code = data.getExtras().getInt("code", 0);
            if (code == COMPOSE_CODE) {
                TweetArrayAdapter adapter = fragmentTweetsList.getAdapter();
                adapter.insert(tweet, 0);
                adapter.notifyDataSetChanged();
            }
        }
    }

}
