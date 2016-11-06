package com.codepath.apps.Tweeter.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

import com.astuetz.PagerSlidingTabStrip;
import com.codepath.apps.Tweeter.R;
import com.codepath.apps.Tweeter.fragments.HomeTimelineFragment;
import com.codepath.apps.Tweeter.fragments.MentionsTimelineFragment;
import com.codepath.apps.Tweeter.models.Tweet;

public class TimelineActivity extends AppCompatActivity {

    private final int COMPOSE_CODE = 20;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timeline);

        ViewPager viewpager = (ViewPager) findViewById(R.id.viewpager);
        viewpager.setAdapter(new TweetsPagerAdapter(getSupportFragmentManager()));

        PagerSlidingTabStrip tabs = (PagerSlidingTabStrip) findViewById(R.id.tabs);
        tabs.setViewPager(viewpager);
    }

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

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        // COMPOSE_CODE is defined above
        if (resultCode == RESULT_OK && requestCode == COMPOSE_CODE) {

            // Extract name value from result extras
            int code = data.getExtras().getInt("code", 0);
            if (code == COMPOSE_CODE) {
                Tweet tweet = (Tweet) data.getExtras().getSerializable("tweet");
                //adapter.insert(tweet, 0);
                //adapter.notifyDataSetChanged();
            }
        }
    }

    // return the order of the fragments in the viewpager
    public class TweetsPagerAdapter extends FragmentPagerAdapter {

        private String[] tabTitles = {"Home", "Mentions"};

        public TweetsPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            if (position == 0) {
                return new HomeTimelineFragment();
            } else if (position == 1) {
                return new MentionsTimelineFragment();
            } else {
                return null;
            }
        }

        @Override
        public int getCount() {
            return tabTitles.length;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return tabTitles[position];
        }
    }

}
