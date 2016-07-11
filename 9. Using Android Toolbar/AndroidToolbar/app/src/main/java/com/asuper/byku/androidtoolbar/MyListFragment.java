package com.asuper.byku.androidtoolbar;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;


/**
 * Created by Byku on 29.05.2016.
 */

public class MyListFragment extends Fragment{

    private OnItemSelectedListener listener;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.fragment_rsslist_overview,container, false);
        Button button = (Button) view.findViewById(R.id.button1);
        button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View w){
                updateDetail("fake");
            }
        });
        return view;
    }
    //----------------- 6.1 ------------------------------------------------------------------------

    public void goToActionMode(RssItem item){
        listener.goToActionMode(item);
    }
    //----------------------------------------------------------------------------------------------
    public interface OnItemSelectedListener{
        void onRssItemSelected(String link);
        //------------------------------------------------------------------------------------------
        void goToActionMode(RssItem item);
        //------------------------------------------------------------------------------------------
    }

    @Override
    public void onAttach(Context context){
        super.onAttach(context);
        Log.i("Info "," MyListFfragment: onAttach");
        if(context instanceof OnItemSelectedListener){
            Log.i("Info "," MyListFfragment: onAttach: if context onItem");
            listener = (OnItemSelectedListener) context;
        } else {
            throw new ClassCastException(context.toString() + " must implement MyListFragment.OnItemSelectedListener");
        }
    }

    @Override
    public void onDetach(){
        super.onDetach();
        Log.i("Info "," MyListFfragment: onDetach");
        listener = null;
    }

    public void updateDetail(String uri){
        Log.i("Info "," MyListFfragment: updateDetail");
        String newTime = String.valueOf(System.currentTimeMillis());
        listener.onRssItemSelected(newTime);
    }
}
//*/
