package com.asuper.byku.listviewexampleactivity;

import android.app.ListActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

/**
 * Created by Byku on 12.05.2016.
 */

//6. Exercise: Using ListView and ListActivity
public class MyListActivity extends ListActivity{
    public void onCreate(Bundle icicle){
        super.onCreate(icicle);
        String[] values = new String[]{"Android", "iPhone","WindowsMoile","Blackberry","WebOS","Ubuntu",
        "Windows7","Mac OS X","Linux","OS/2"};
        //MyPerformanceArrayAdapter adapter = new MyPerformanceArrayAdapter(this, values);
        //ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,values);
        //setListAdapter(adapter);
        setListAdapter(new MyPerformanceArrayAdapter(this,values));
    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id){
        String item = (String) getListAdapter().getItem(position);
        Toast.makeText(this,item+" selected", Toast.LENGTH_LONG).show();
    }
}
