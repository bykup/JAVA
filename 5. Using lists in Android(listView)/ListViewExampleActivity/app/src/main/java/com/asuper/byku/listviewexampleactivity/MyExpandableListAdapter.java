package com.asuper.byku.listviewexampleactivity;


import android.app.Activity;
import android.util.Log;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckedTextView;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by Byku on 19.05.2016.
 */
public class MyExpandableListAdapter extends BaseExpandableListAdapter {
    //obvious
    private final SparseArray<Group> groups;
    //three methods of creating layout:
    //in XML - importing thanks to setContentView
    //purely dynamic(programming) - due to addView
    //in XML in fragments, then putting back together dynamically(programming)
    //use LayoutInflater to use the third method
    public LayoutInflater inflater;
    public Activity activity;

    //constructor
    public MyExpandableListAdapter(Activity act, SparseArray<Group> groups){
        //gets activity
        activity = act;
        //gets groups
        this.groups = groups;
        //gets LayoutInflater that this instance recieved from its Context
        inflater = act.getLayoutInflater();
    }

    @Override
    public Object getChild(int groupPosition, int childPosition){
        //gets the object "child" of sparsearray
        return groups.get(groupPosition).children.get(childPosition);
    }

    @Override //theoretically should be unique
    public long getChildId(int groupPosition, int childPosition){
        return 0;
    }

    @Override //get the view which the child is using
    //position of group(groups), position of child(list in group class)
    /**
     *  View covertView: the old view to reuse, if possible. You should check that this view
     *  is non-null and of an appropriate type before using. If it is not possible to
     *  convert this view to display the correct data , this method can create a new view.
     *  It is not guaranteed that the convertView will have been previously created by
     *  getChildView(int, int, boolean, View, ViewGroup).
     */
    public View getChildView(int groupPosition, final int childPosition,
                             final boolean isLastChild, View convertView, ViewGroup parent){
        //gets the an object(which is a string in list) then throws it as string
        final String children = (String) getChild(groupPosition, childPosition);
        final boolean isLastChildFinally = isLastChild;
        TextView text = null;

        if (convertView == null) {
            //So if there are no layouts for childviews present, we create one
            //the id of the layout we want to inflate, the parent layout
            convertView=inflater.inflate(R.layout.listrow_details,null);
        }
        text = (TextView) convertView.findViewById(R.id.textView1);
        text.setText(children);
        //obvious - gets on View listeners
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isLastChildFinally == true){
                    Toast.makeText(activity, children+" is last", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(activity, children, Toast.LENGTH_LONG).show();
                }
            }
        });
        return convertView;
    }

    @Override //it is partly to draw children :3 noh noh
    public int getChildrenCount(int groupPosition){
        return groups.get(groupPosition).children.size();
    }

    @Override
    public Object getGroup(int groupPosition){
        //Log.i("Info: ","MyExpandableListAdapter: getGroup: " + ((Group) groups.get(groupPosition)).string);
        return groups.get(groupPosition);
    }

    @Override //used partly to draw Test(used as info how many groups to draw)
    public int getGroupCount(){
        return groups.size();
    }

    @Override //starts when a group has been collapsed
    public void onGroupCollapsed(int groupPosition){
        //Log.i("Info: ","MyExpandableListAdapter: onGroupCollapsed: " + groupPosition);
        super.onGroupCollapsed(groupPosition);
    }

    @Override //starts when group has been expanded
    public void onGroupExpanded(int groupPosition){
        super.onGroupExpanded(groupPosition);
    }

    @Override //returns group id
    public long getGroupId(int groupPosition){
        return (groups.get(groupPosition).string + groupPosition).hashCode();
    }

    @Override //returns View, passes - position, etc.
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent){
        if(convertView==null){ //if there is no child view then it iflates it
            convertView = inflater.inflate(R.layout.listrow_group,null);
        }
        Group group = (Group) getGroup(groupPosition);
        ((CheckedTextView)convertView).setText(group.string);
        ((CheckedTextView)convertView).setChecked(isExpanded);
        //Log.i("Info ","MyExpandableListAdapter: getGroupView: " + groupPosition + " | " + isExpanded + " | " + convertView.toString() + " | " + parent.toString() + " | END");
        return convertView;
    }

    @Override   //for optimzation, it allows to redraw only groups items that has changed between notifyDataSetChanged
    public boolean hasStableIds(){
        //Log.i("Info ","MyExpandableListAdapter: hasStableIds: ");
        return false;
    }

    @Override //um checks if child is selectable ;]
    public boolean isChildSelectable(int groupPosition, int childPosition){
        return false;
    }
}

