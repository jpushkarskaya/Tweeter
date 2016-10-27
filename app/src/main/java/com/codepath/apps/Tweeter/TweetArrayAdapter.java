package com.codepath.apps.Tweeter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.codepath.apps.Tweeter.models.Tweet;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by epushkarskaya on 10/27/16.
 */
public class TweetArrayAdapter extends ArrayAdapter<Tweet> {

    public TweetArrayAdapter(Context context, List<Tweet> tweets) {
        super(context, android.R.layout.simple_list_item_1, tweets);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Tweet tweet = getItem(position);
        ViewHolder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_tweet, parent, false);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        }
        holder = (ViewHolder) convertView.getTag();

        holder.ivProfileImage.setImageResource(android.R.color.transparent);
        Picasso.with(getContext()).load(tweet.getUser().getProfileImageUrl()).into(holder.ivProfileImage );
        holder.tvUsername.setText(tweet.getUser().getName());
        holder.tvBody.setText(tweet.getBody());
        return convertView;
    }


    private class ViewHolder {
        ImageView ivProfileImage;
        TextView tvUsername;
        TextView tvBody;

        public ViewHolder(View view) {
            this.ivProfileImage = (ImageView) view.findViewById(R.id.ivProfileImage);
            this.tvUsername = (TextView) view.findViewById(R.id.tvUsername);
            this.tvBody = (TextView) view.findViewById(R.id.tvBody);
        }
    }

}
