package com.asuper.byku.listviewexampleactivity;

import java.util.List;

import android.app.Activity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

/**
 * Created by Byku on 18.05.2016.
 */
public class InteractiveArrayAdapter extends ArrayAdapter<Model> {

    private final List<Model> list;
    private final Activity context;
    //constructor - only once
    public InteractiveArrayAdapter(Activity context, List<Model> list){
        super(context,R.layout.rowbuttonlayout,list);
        Log.i("Info ","InteractiveArrayAdapter: Constructor");
        this.context=context;
        this.list = list;
    }
    static class ViewHolder{
        protected TextView text;
        protected CheckBox checkbox;
    }
    @Override //called when views are being created
    public View getView(int position, View convertView, ViewGroup parent){
        Log.i("Info ","InteractiveArrayAdapter: getView: " + position);
        View view = null;
        if(convertView == null){ //creates views to fill smartphone's screen
            Log.i("Info ","InteractiveArrayAdapter: getView: if");
            LayoutInflater inflater = context.getLayoutInflater();
            view = inflater.inflate(R.layout.rowbuttonlayout,null);
            //creates a ViewHolder for that new View
            final ViewHolder viewHolder = new ViewHolder();
            viewHolder.text = (TextView) view.findViewById(R.id.rbl_label); //adds a text to it
            viewHolder.checkbox = (CheckBox) view.findViewById(R.id.rbl_check); //and a checkbox
            //if we check the checkbox
            viewHolder.checkbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener(){
                @Override //obvious
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked){
                    //adds a tag to a checkbox that we've associated with new view
                    Model element = (Model) viewHolder.checkbox.getTag();
                    element.setSelected(buttonView.isChecked()); //ticks boolean variable in Model
                    //Model el = (Model) viewHolder.text.getTag();
                    //Log.i("Info ","InteractiveArrayAdapter: getView: if: "+el.getName());
                }
            });
            //to that view we set a tag of viewHolder
            view.setTag(viewHolder);
            //and to that viewHolder we set a Tag of the checkbox position
            viewHolder.checkbox.setTag(list.get(position));

            //viewHolder.text.setTag(list.get(position));
        }else{ //reuses the views created above to draw new items
            Log.i("Info ","InteractiveArrayAdapter: getView: else");
            //we add to view object current view
            view = convertView;
            //then we set a tag on current checkbox with current Model
            //((ViewHolder)convertView.getTag()).checkbox.setTag(list.get(position)); - like below
            ((ViewHolder) view.getTag()).checkbox.setTag(list.get(position));
        }
        //on current view we set stuff
        ViewHolder holder = (ViewHolder) view.getTag();
        holder.text.setText(list.get(position).getName());
        //returns from Model class "selected" variable which is boolean
        holder.checkbox.setChecked(list.get(position).isSelected());

        return view;
    }

}
//15. Selecting multiple items int he ListView
/*
When you select multiple items in ListView, the recycled view loose that selection, so you cannot
store selected items on the View level.
To persist selection you have to update your data model with the selected state.
- adapter adds listener on the checkbox view, if checbox is selected the underlying data of the mode
is changed. Checkbox gets the corresponding model element assigned via the getTag() method.
 */