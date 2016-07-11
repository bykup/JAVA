package com.asuper.byku.listviewexampleactivity;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.app.ListFragment;

/**
 * Created by Byku on 12.05.2016.
 */

//Default container for using ListView
//ListFragment - use lists in fragments, it is not needed to assign a layout to these elements
    /*
    Contains single ListView by default
    Let's you override onListItemClick() - handles selection of list items
    Allow to set the adapter to the default ListView via the setListAdapter()
     */
public class MyListFragment extends ListFragment{
    @Override
    public void onActivityCreated(Bundle savedInstanceState){
        super.onActivityCreated(savedInstanceState);
        String[] values = new String[]{"Android", "iPhone", "WindowsMobile",
                "Blackberry", "WebOS", "Ubuntu", "Windows7", "Max OS X",
                "Linux", "OS/2"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),android.R.layout.simple_list_item_1,values);
        setListAdapter(adapter);
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id){
        //implement logic
    }
}



//