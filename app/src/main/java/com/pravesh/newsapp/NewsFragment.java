package com.pravesh.newsapp;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import androidx.fragment.app.Fragment;

public class NewsFragment extends Fragment {

    public static NewsFragment newInstance(int image, String newsHeadline, String newsDescription, String newsContent, String newsPublishedAt, String newsSource) {

        Bundle args = new Bundle();
        args.putInt("image", image);
        args.putString("newsHeadline", newsHeadline);
        args.putString("newsDescription", newsDescription);
        args.putString("newContent", newsContent);
        args.putString("newsPublishedAt", newsPublishedAt);
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
        TextView newsPublished = rootView.findViewById(R.id.txtPublished);
        TextView newsSource = rootView.findViewById(R.id.txtSource);

        Bundle bundle = getArguments();
        if (bundle != null) {
            int nImage = bundle.getInt("image");
            String nHeadline = bundle.getString("newsHeadline");
            String nDescription = bundle.getString("newsDescription");
            String nContent = bundle.getString("newsContent");
            String nPublishedAt = bundle.getString("newsPublishedAt");
            String nSource = bundle.getString("newsSource");

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
            if (nPublishedAt != null) {
                newsPublished.setText(nPublishedAt);
            } else {
                newsPublished.setText(getString(R.string.unknown));
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
