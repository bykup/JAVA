package com.vogella.android.applife;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

/**
 * Created by Byku on 24.04.2016.
 */
public class FirstActivity extends MainActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void onClick(View view){
        Intent intent = new Intent(this, SecondActivity.class);
        startActivity(intent);
    }
}
