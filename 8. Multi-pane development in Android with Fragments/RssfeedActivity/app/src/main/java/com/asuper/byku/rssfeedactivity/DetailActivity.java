package com.asuper.byku.rssfeedactivity;

import android.support.v7.app.AppCompatActivity;
import android.app.Application;
import android.content.res.Configuration;
import android.os.Bundle;

/**
 * Created by Byku on 03.06.2016.
 */
public class DetailActivity extends AppCompatActivity {
    public static final String EXTRA_URL = "url";

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        //check if dual pane mode is active
        //if yes, finish this activity
        if(getResources().getBoolean(R.bool.dual_pane)){
            finish(); //exits activity, returns to parent
            return;
        }
        //ok, the activity is in horizontal view, let's start it.
        setContentView(R.layout.activity_detail);
        //getting extras
        Bundle extras = getIntent().getExtras();
        if(extras != null){
            //recognises extra through EXTRA_URL variable
            String url = extras.getString(EXTRA_URL);
            //as we are already defining fragment in xlm file, we don't have to use fragmenttransactions
            DetailFragment detailFragment = (DetailFragment) getFragmentManager().findFragmentById(R.id.detailFragment);
            detailFragment.setText(url);
        }
    }
}
