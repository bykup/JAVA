package com.asuper.byku.listviewexampleactivity;

import java.util.ArrayList;

import android.app.Activity;
import android.os.Bundle;
//SparseArray - more efficient version of Array or HashMap, holds integers to objects
//doesn't compact array immediately after item deletion, but later in one buld during
//garbage collector run
import android.util.SparseArray;
import android.view.Menu;
import android.widget.ExpandableListView;

/**
 * Created by Byku on 19.05.2016.
 */

//16. Expandable List View
public class expListView extends Activity{
    //More efficient than HashMap for mapping integers to objects
    SparseArray<Group> groups = new SparseArray<Group>();

    @Override
    protected void onCreate(Bundle instanceState){
        super.onCreate(instanceState);
        //layout has to have expandablelistview
        setContentView(R.layout.activity_main);
        //fills groups
        createData();
        //expandablelistview in LinearLayout
        ExpandableListView listView = (ExpandableListView) findViewById(R.id.am_listView);
        //Adapter of course
        MyExpandableListAdapter adapter = new MyExpandableListAdapter(this, groups);
        //sets adapter
        listView.setAdapter(adapter);
    }

    public void createData(){
        int children = 0;
        for(int j = 0; j<20;j++){
            Group group = new Group("Test " + j);
            for(int i = 0; i<10;i++){
                group.children.add("Sub item " + (children++));
            }
            groups.append(j,group);
        }
    }
}
