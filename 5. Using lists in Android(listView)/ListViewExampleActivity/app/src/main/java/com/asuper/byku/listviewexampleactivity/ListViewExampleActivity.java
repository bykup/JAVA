package com.asuper.byku.listviewexampleactivity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.content.Context;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;

public class ListViewExampleActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listviewexampleactivity);

        final ListView listview = (ListView) findViewById(R.id.listview);
        String[] values = new String[]{ "Android", "iPhone", "WindowsMobile",
                "Blackberry", "WebOS", "Ubuntu", "Windows7", "Max OS X",
                "Linux", "OS/2", "Ubuntu", "Windows7", "Max OS X", "Linux",
                "OS/2", "Ubuntu", "Windows7", "Max OS X", "Linux", "OS/2",
                "Android", "iPhone", "WindowsMobile" };

        View header = getLayoutInflater().inflate(R.layout.header,null);
        View footer = getLayoutInflater().inflate(R.layout.footer,null);

        listview.addHeaderView(header);
        listview.addFooterView(footer);

        final ArrayList<String> list = new ArrayList<String>();
        for(int i = 0; i<values.length;++i){
            list.add(values[i]);
        }

        list.add("wow");

        final StableArrayAdapter adapter = new StableArrayAdapter(this,
                android.R.layout.simple_list_item_1,list);//creating adapter


        listview.setAdapter(adapter); //inicializing list

        //Intent intent = new Intent(this, MyListActivity.class);
        //startActivity(intent);

        listview.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, final View view, int position, long id){
                final String item = (String) parent.getItemAtPosition(position);
                view.animate().setDuration(1000).alpha(0).withEndAction(new Runnable(){
                    @Override
                    public void run(){
                        list.remove(item);
                        adapter.notifyDataSetChanged();
                        view.setAlpha(1);
                    }
                });
            }
        });

        listview.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(ListViewExampleActivity.this,"Long click",Toast.LENGTH_SHORT).show();
                return true;
            }
        });
    }


    private class StableArrayAdapter extends ArrayAdapter<String>{
        HashMap<String, Integer> mIdMap = new HashMap<String, Integer>();
        //ArrayList<String> mIdMap = new ArrayList<String>(); //changed as experiment, now works list.add

        public StableArrayAdapter(Context context, int textViewResourceid,
                                  List<String> objects){
            super(context,textViewResourceid,objects);
            //Log.i("Info ","StableArrayAdapter: In!");
            for(int i = 0; i<objects.size(); ++i){
                mIdMap.put(objects.get(i),i);
                //mIdMap.add(objects.get(i)); //experiment, now works list.add
            }
        }

        @Override
        public long getItemId(int position){
            String item = getItem(position);
            //Log.i("Info ","getItemId: In!");
            return mIdMap.get(item);//*/
            //return position; //as an experiment now works add
        }

        @Override
        public boolean hasStableIds(){
            return false;
        } //dum dum dum
    }
}


//Android and the listView widget
/*
    Views for handling lists
    ListView, ExpandableListView classes- display scrollable list of items, the second one supports grouping
of items.

    Possible input types for lists
    Arbitrary Java objects can be items in the list. Adapter extracts correct data from the data object and
assigns this data to the views in the row of the ListView.These items are typically called the data model of
the list. An adapter can receive data as input.

    Adapters
    Manages data model and adapts to individual entries in widget. It extends BaseAdapter class.
Every line consists of a layout.

    Adapter would inflate each row in its getView(), it will assign data to individual
views in the row. Adapter is assigned to the ListView, via setAdapter method on the ListView.
Adapters are also used by Spinner, GridView, Gallery, StackView.

    Filtering and sorting
Handled by adapter, logic has to be implemented.

    Data updates in the adapter
notifyDataSetChanged() - called on adapter if data has changed
notifyDataSetInvalidated() - if data is not available

    Listener
    To react to selections in the list set OnItemClickListener
    ----------------------------------------------------------
    listView.setOnItemClickListener(new OnItemClickListener(){
        @Override
        public void onItemClick(AdapterView<?> parent, View view,
            int position, long id){
            Toast.makeText(getApplicationContext(), "Click ListItemNumber " + position,
            Toast.LENGTH_LONG).show();
        }
    })
    ----------------------------------------------------------
 */

//3. Default adapter
/*
    Default platform adapter:
Default implementations of adapter:
    - ArrayAdapter - handles data based on Arrays or java.util.List
    - SimpleCursorAdapter - can handle database related data

    Using ArrayAdapter
    Handles a list or array of Java objects as input. Each one is mapped to one row,
    by default, it maps the toString() method of the object to a view in the row layout.
    Define ID of the view in the constructor of the ArrayAdapter, otherwise android.R.id.text1 is default.
    - clear() - removing all elements in its underlying data structure(method in ArrayAdapter)
    - add() - method, adds new elements
    - addAll() - adds a Collection
    - notifyDataSetChanged() - if we directly modify data, we should call this method to notify adapter
    about those changes
    When modifying data in adapter, the underlying data structure must support this operation
    (for example ArrayList).
 */

//4. Custom adapter implementations
/*
In MySimpelArrayAddapter.java
 */

//5. ListAcitivity and List Fragment
/*
Default container for using ListView
ListFragment - use lists in fragments, it is not needed to assign a layout to these elements
    Contains single ListView by default
    Let's you override onListItemClick() - handles selection of list items
    Allow to set the adapter to the default ListView via the setListAdapter()

    ListActivity and ListFragment searches for ListView with pre-defined android:id.

    When @android:id/empty - shows it automaticly when ListView is empty and hides it otherwise(good
    for error message)
 */
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
//10. Storing the selection of a vview
/*
    ListView - by default no selection mode active. setChoiceMode() - can activate it.
Pass ListView.CHOICE_MODE_MULTIPLE or ListView.CHOICE_MODE_SINGLE.
To get selected items of a ListView, use getCheckedItemPostion() for single selction or
listView.getCheckedItemPostions() for multiple selections.
If has stable ID, use getCheckedItemIds() method to get the selcted IDs.
Default layout for this:
android.R.layout.simple_list_item_multiple_choice, which contains a configured
CheckedTextView.
 */
//11. Contextual action mode for ListViews
/*
    setOnItemLongClickListener() - on ListView - set it, to assign a contextual action mode
to a long click on an individual item. In this method you can start ActionMode
 */

//12.Implementing undo for an action
/*
XLS - uses FrameLayout to show different parts of the user interface
The button is initially hidden.
 */

//14.How to display two items in a ListView
/*
SimpleAdapter can be used to show the data of two elements.
 */

//17. Header and footer
