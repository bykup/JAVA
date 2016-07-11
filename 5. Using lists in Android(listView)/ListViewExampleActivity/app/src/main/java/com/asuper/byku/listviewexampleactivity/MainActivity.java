package com.asuper.byku.listviewexampleactivity;

import android.app.ListActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.Toolbar;


/**
 * Created by Byku on 15.05.2016.
 */
//If you use following activities the ListView stores the selected values. It is not
// persisted in your data model.
public class MainActivity extends ListActivity {

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);


        String[] values = new String[]{"a", "b", "c", "d", "e", "f", "g",
                "h", "i", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s",
                "t", "u", "w", "x", "y", "z"};

        ArrayAdapter<String> adapter = new ArrayAdapter<String>
                (this,android.R.layout.simple_list_item_multiple_choice,values);
        setListAdapter(adapter);
        getListView().setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        Toast.makeText(this, "onCreateOptionsMenu",
                Toast.LENGTH_LONG).show();
        getMenuInflater().inflate(R.menu.my_menu,menu);
        return true;
    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        Toast.makeText(this, String.valueOf(getListView().getCheckedItemCount()),
                Toast.LENGTH_LONG).show();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        Toast.makeText(this, String.valueOf(getListView().getCheckedItemCount()),
                Toast.LENGTH_LONG).show();
        return true;
    }
}


//10. Storing the selection of a view
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