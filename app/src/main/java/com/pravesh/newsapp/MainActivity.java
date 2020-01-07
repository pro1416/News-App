package com.pravesh.newsapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

public class MainActivity extends FragmentActivity {
    RequestQueue requestQueue;
    int noOfFragments = 3;
    private ViewPager viewPager;
    private PagerAdapter pagerAdapter;
    JSONArray articleArray;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        viewPager = findViewById(R.id.pager);
        pagerAdapter = new ScreenSlidePagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(pagerAdapter);
        //Setting page change animation
        viewPager.setPageTransformer(true, new ZoomOutPageTransformer());

        //instantiate the request queue
        requestQueue = Volley.newRequestQueue(this);
        Log.d("BeforeCall", "onCreate: " + noOfFragments);

        //creating GET request
        Request jsonObject = new JsonObjectRequest(Request.Method.GET,
                "https://newsapi.org/v2/top-headlines?country=in&apiKey=141f1a8c46654adfa9a6f780afd65141", null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d("response", "onResponse: Entering");
                        try {
                            if (response.getString("status").equals("ok")) {
                                noOfFragments = response.getInt("totalResults");
                                articleArray = response.getJSONArray("articles");
                                Log.d("DuringCall", "onResponse: " +articleArray.toString());

                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(MainActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                        Log.d("error", "onErrorResponse: "+error);

                    }
                });
        requestQueue.add(jsonObject);

        Log.d("AfterCall", "onCreate: " + noOfFragments);


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
        public ScreenSlidePagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {

            JSONObject articles;
            NewsFragment newsFragment = null;
            try {
                articles = articleArray.getJSONObject(position);

                JSONObject source = articles.getJSONObject("source");
                String sourcename = source.getString("name");
                String headline = articles.getString("title");
                String description = articles.getString("description");
                String pubishedAt = DateFormat.getDateInstance().format(articles.getString("publishedAt"));
                String content = articles.getString("content");
                newsFragment = NewsFragment.newInstance(0, headline, description, content, pubishedAt, sourcename);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return newsFragment;

        }

        @Override
        public int getCount() {
            return noOfFragments;
        }
    }

}
