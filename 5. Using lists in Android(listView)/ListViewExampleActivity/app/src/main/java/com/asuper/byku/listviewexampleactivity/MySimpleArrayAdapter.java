package com.asuper.byku.listviewexampleactivity;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by Byku on 11.05.2016.
 */ //Example
public class MySimpleArrayAdapter extends ArrayAdapter<String> {
    //private final Activity context;
    private final Context context;
    private final String[] values;

    public MySimpleArrayAdapter(Activity context, String[] values){
        super(context, R.layout.rowlayout, values);
        this.context = context;
        this.values = values;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        //LayoutInflater inflater = context.getLayoutInflater();
        View rowView = inflater.inflate(R.layout.rowlayout,parent,false);
        TextView textView = (TextView) rowView.findViewById(R.id.labelR);
        TextView textView2 = (TextView) rowView.findViewById(R.id.labelR2);
        ImageView imageView = (ImageView) rowView.findViewById(R.id.iconR);
        textView.setText(values[position]);
        textView2.setText(values[position]);
        //change the icon for Windows and iPhone
        String s = values[position];
        if(s.startsWith("iPhone")){
            imageView.setImageResource(R.drawable.no);
        }else{
            imageView.setImageResource(R.drawable.ok);
        }
        return rowView;
    }
}


//4. Custom adapter implementations
/*
    Developing custom adapter
    ArrayAdapter - is limited, supports only mapping of toString() to one view in the row layout.
To control data assignemnt and to support several views, you have to creat your custom adapter
implementation. You could extend an existing adapter(easier) or subclass the BaseAdapter

    Preparing a row for the list
The adapter needs to creat a layout for each row of the list.
- ListView - calls - getView() - on the adapter for each data element. In this method, adapter
creates the row layout and maps the data to the views in the layout.
- root of the layout is typically a ViewGroup and contains several other viws.
- within the getView() - you would inflate XML bases layout
- use LayoutInflator to inflate XML layout file(getLayoutInflator())
 */