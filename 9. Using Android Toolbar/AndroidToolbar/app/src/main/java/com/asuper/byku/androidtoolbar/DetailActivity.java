package com.asuper.byku.androidtoolbar;

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
        if(getResources().getBoolean(R.bool.dual_pane)){
            finish(); //exits activity, returns to parent
            return;
        }
        setContentView(R.layout.activity_detail);
        Bundle extras = getIntent().getExtras();
        if(extras != null){
            String url = extras.getString(EXTRA_URL);
            DetailFragment detailFragment = (DetailFragment) getFragmentManager().findFragmentById(R.id.detailFragment);
            detailFragment.setText(url);
        }
    }
}
