package com.asuper.byku.listviewexampleactivity;

/**
 * Created by Byku on 18.05.2016.
 */

import java.util.ArrayList;
import java.util.List;

import android.app.ListActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Toast;

public class MyList extends ListActivity{
    /** Called when the activity is first created. */

    public void onCreate(Bundle icicle){
        super.onCreate(icicle);
        //create an array of String, that will be put to our ListActivity
        ArrayAdapter<Model> adapter = new InteractiveArrayAdapter(this, getModel());
        setListAdapter(adapter);

    }

    private List<Model> getModel(){
        List<Model> list = new ArrayList<Model>();
        list.add(get("Linux"));
        list.add(get("WindowsXp"));
        list.add(get("Solaris"));
        list.add(get("iOS"));
        list.add(get("Ubuntu"));
        list.add(get("Android"));
        list.add(get("iPhone"));
        list.add(get("Suse"));
        list.add(get("Linux"));
        list.add(get("WindowsXp"));
        list.add(get("Solaris"));
        list.add(get("iOS"));
        list.add(get("Ubuntu"));
        list.add(get("Android"));
        list.add(get("iPhone"));
        list.add(get("Suse"));
        list.add(get("Linux"));
        list.add(get("WindowsXp"));
        list.add(get("Solaris"));
        list.add(get("iOS"));
        list.add(get("Ubuntu"));
        list.add(get("Android"));
        list.add(get("iPhone"));
        list.add(get("Suse"));
        list.add(get("Linux"));
        list.add(get("WindowsXp"));
        list.add(get("Solaris"));
        list.add(get("iOS"));
        list.add(get("Ubuntu"));
        list.add(get("Android"));
        list.add(get("iPhone"));
        list.add(get("Suse"));
        //Initially select one of the items
        //list.get(1).setSelected(true);
        return list;
    }

    private Model get(String s){
        return new Model(s);
    }
}

//17. Tutorial: Miscellaneous