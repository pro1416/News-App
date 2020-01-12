package com.pravesh.newsapp;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import androidx.fragment.app.Fragment;

import com.squareup.picasso.Picasso;

public class NewsFragment extends Fragment {

    public static NewsFragment newInstance(String imageurl, String newsHeadline, String newsDescription, String newsContent, String newsSource) {

        Bundle args = new Bundle();
        args.putString("imgurl", imageurl);
        args.putString("newsHeadline", newsHeadline);
        args.putString("newsDescription", newsDescription);
        args.putString("newsContent", newsContent);
        args.putString("newsSource", newsSource);
        NewsFragment newsFragment = new NewsFragment();
        newsFragment.setArguments(args);
        return newsFragment;

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(
                R.layout.news_fragment, container, false);

        ImageView newsImage = rootView.findViewById(R.id.newsImage);
        TextView newsHeadline = rootView.findViewById(R.id.txtHeadline);
        TextView newsDescription = rootView.findViewById(R.id.txtDescription);
        TextView newsContent = rootView.findViewById(R.id.txtContent);
        TextView newsSource = rootView.findViewById(R.id.txtSource);

        Bundle bundle = getArguments();
        if (bundle != null) {
            String nImage = bundle.getString("imgurl");
            String nHeadline = bundle.getString("newsHeadline");
            String nDescription = bundle.getString("newsDescription");
            String nContent = bundle.getString("newsContent");
            String nSource = bundle.getString("newsSource");

            if (nImage != null) {
                Picasso.with(getContext())
                        .load(nImage)
                        .into(newsImage);
            }
            if (nHeadline != null) {
                newsHeadline.setText(nHeadline);
            } else {
                newsHeadline.setText(getString(R.string.head_missing));
            }
            if (nDescription != null) {
                newsDescription.setText(nDescription);
            } else {
                newsDescription.setText(getString(R.string.desc_missing));
            }
            if (nContent != null) {
                newsContent.setText(nContent);
            } else {
                newsContent.setText(getString(R.string.story_miss));
            }

            if (nSource != null) {
                newsSource.setText(nSource);
            } else {
                newsSource.setText(getString(R.string.unknown));
            }
        }


        return rootView;
    }
}
