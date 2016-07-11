package com.asuper.byku.rssfeedactivity;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.Toast;

public class RssfeedActivityDynamic extends AppCompatActivity implements MyListFragment.OnItemSelectedListener {

    FrameLayout frameLayout;
    MyListFragment myListFragment;
    DetailFragment detailFragment;
    FragmentTransaction fragmentTransaction;
    FragmentManager fragmentManager;
    boolean dual_pane;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dynamic_activity_rssfeed);
        dual_pane = getResources().getBoolean(R.bool.dual_pane);
        if(!dual_pane){
            myListFragment = new MyListFragment();
            detailFragment = new DetailFragment();

            fragmentManager = getFragmentManager();
            fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.add(R.id.da_listcontainer,myListFragment).add(R.id.da_detailscontainer,detailFragment);
            fragmentTransaction.commit();
        }

    }

    @Override
    public void onRssItemSelected(String link){
        if(dual_pane) {
            DetailFragment fragment = (DetailFragment) getFragmentManager().findFragmentById(R.id.da_detailFragment);
            fragment.setText(link);
        } else {
            Toast.makeText(RssfeedActivityDynamic.this, "dual_pane false", Toast.LENGTH_SHORT).show();
            frameLayout = (FrameLayout) getWindow().getDecorView().findViewById(R.id.da_listcontainer);
            frameLayout.setVisibility(View.GONE);
            detailFragment.setText(link);
            frameLayout = (FrameLayout) getWindow().getDecorView().findViewById(R.id.da_detailscontainer);
            frameLayout.setVisibility(View.VISIBLE);
        }
    }
}
