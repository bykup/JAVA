package com.asuper.byku.listviewexampleactivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Byku on 19.05.2016.
 */
//will hold domain model for the ExpandableListView
public class Group {
    public String string;

    public final List<String> children = new ArrayList<String>();

    public Group(String string){
        this.string = string;
    }
}
//16.Implementing an expandable ListView