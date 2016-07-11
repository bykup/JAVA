package com.asuper.byku.androidtoolbar;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;



/**
 * Created by Byku on 29.05.2016.
 */
public class DetailFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.fragment_rssitem_detail,container,false);
        return view;
    }
    public void setText(String url){
        TextView view = (TextView) getView().findViewById(R.id.detailsText);
        view.setText(url);
    }
}
