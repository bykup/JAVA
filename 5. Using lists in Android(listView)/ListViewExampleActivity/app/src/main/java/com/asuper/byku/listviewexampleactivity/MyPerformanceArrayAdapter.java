package com.asuper.byku.listviewexampleactivity;

import android.app.Activity;
import android.view.View;
import android.view.LayoutInflater;
import android.widget.ArrayAdapter;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


/**
 * Created by Byku on 15.05.2016.
 */

//uses performance optimized adapter implementation which reuses existing views
// and implements the holder pattern
public class MyPerformanceArrayAdapter extends ArrayAdapter<String> {
    private final Activity context;
    private final String[] names;

    //similar to InteractiveArrayAdapter, holds data for each view
    static class ViewHolder{
        public TextView text;
        public ImageView image;
    }

    public MyPerformanceArrayAdapter(Activity context, String[] names){
        //launching constructor for base class ArrayAdapter
        super(context, R.layout.rowlayout, names);
        this.context=context; //saves pointer to this context
        this.names = names; //saves pointer to this names
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        View rowView = convertView; //pointer to convertView, current view
        //reuse view
        if(rowView==null){
            //pointer to current LayoutInflater
            LayoutInflater inflater = context.getLayoutInflater();
            //inflate current view with rowlayout2
            rowView = inflater.inflate(R.layout.rowlayout2,null);
            //configure view holder for THIS view
            ViewHolder viewHolder = new ViewHolder();
            viewHolder.text = (TextView) rowView.findViewById(R.id.textRL2);
            viewHolder.image = (ImageView) rowView.findViewById(R.id.iconRL2);
            //assign this viewHolder with this rowView
            rowView.setTag(viewHolder);
        }

        //fill data with ViewHolder, as above in initialization with setTag as viewHolder
        ViewHolder holder = (ViewHolder) rowView.getTag();
        String s = names[position];
        //assign the text in current rowView holder
        holder.text.setText(s);
        if(s.startsWith("Windows7") || s.startsWith("iPhone")||s.startsWith("Solaris")){
            holder.image.setImageResource(R.drawable.no);
        } else{
            holder.image.setImageResource(R.drawable.ok);
        }

        return rowView;
    }
}

//9. ListViews and performance
/*
    9.1. Motivation
ArrayAdapter - is performance optimized

    9.2. Time consumingg operations
Every view from XML layout file results in Java Object - creating them takes time and memory.
findViewById() - is time consuming, but not as bad as XML inflating.

    9.3. Avoiding lyout inflation and object creation
When views in list are not visible, they are reused by getView() method of adapter
by ConvertView paremeter to inflate views that are about to be seen. When view
cannot be reused the Android passes null to the convertView parameter. Adapter
implementation needs to check for this.

    9.4. View holder pattern
ViewHolder implementation allows to avoid the findViewById() method in an adapter.
ViewHolder - static inner class in your adapter which holds references to the
relevant view in your layout. Reference is assigned to the row view as a tag via
setTag() method.
When we receive convertView object, we can get instance of the ViewHolder(holds references
to views) via getTag() and assign the new attributes to the views via the ViewHolder reference.
It is 15%(!!!!!!!!!!!!!!!!!!!!!!!!!!!! :OOOOOOOOOOOOOO) faster than using the findViewById()
method.
 */