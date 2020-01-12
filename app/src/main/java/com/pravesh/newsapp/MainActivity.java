package com.pravesh.newsapp;

import android.os.Bundle;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends FragmentActivity {
    RequestQueue requestQueue;
    private ViewPager viewPager;
    private PagerAdapter pagerAdapter;
    JSONArray articleArray;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        viewPager = findViewById(R.id.pager);
        //Setting page change animation
        viewPager.setPageTransformer(true, new ZoomOutPageTransformer());

        //instantiate the request queue
        requestQueue = Volley.newRequestQueue(this);


        //creating GET request
        Request jsonObject = new JsonObjectRequest(Request.Method.GET,
                "https://newsapi.org/v2/top-headlines?country=in&apiKey=141f1a8c46654adfa9a6f780afd65141", null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            if (response.getString("status").equals("ok")) {
                                articleArray = response.getJSONArray("articles");
                                pagerAdapter = new ScreenSlidePagerAdapter(getSupportFragmentManager(), articleArray);
                                viewPager.setAdapter(pagerAdapter);


                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(MainActivity.this, "Something went wrong", Toast.LENGTH_SHORT).show();

                    }
                });
        requestQueue.add(jsonObject);


    }

    @Override
    public void onBackPressed() {
        if (viewPager.getCurrentItem() == 0) {
            super.onBackPressed();
        } else {
            viewPager.setCurrentItem(viewPager.getCurrentItem() - 1);
        }
    }

    private class ScreenSlidePagerAdapter extends FragmentStatePagerAdapter {
        JSONArray articles;

        public ScreenSlidePagerAdapter(FragmentManager fm, JSONArray articles) {
            super(fm);
            this.articles = articles;
        }

        @Override
        public Fragment getItem(int position) {

            JSONObject articles;
            NewsFragment newsFragment = null;
            try {
                articles = articleArray.getJSONObject(position);

                JSONObject source = articles.getJSONObject("source");
                String sourcename = getString(R.string.source) + source.getString("name");
                String headline = articles.getString("title");
                String description = articles.getString("description");
                String content = articles.getString("content");
                String imageurl = articles.getString("urlToImage");
                newsFragment = NewsFragment.newInstance(imageurl, headline, description, content, sourcename);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return newsFragment;

        }

        @Override
        public int getCount() {
            return articles.length();
        }
    }

}
