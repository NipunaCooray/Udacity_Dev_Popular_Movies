package com.example.nipunac.popularmovies_v1;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.example.nipunac.popularmovies_v1.utilities.NetworkUtils;

import java.net.URL;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        URL movieRequestURL = NetworkUtils.buildUrl();
        Log.d("URL",movieRequestURL.toString());

    }
}
