package com.asuper.byku.listviewexampleactivity;


import android.app.ListActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

/**
 * Created by Byku on 15.05.2016.
 */
public class MainActivity2 extends ListActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        String[] values = new String[]{"a", "b", "c", "d", "e", "f", "g",
                "h", "i", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s",
                "t", "u", "w", "x", "y", "z"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
            this,R.layout.simple_list_item_single_choice,values);
        setListAdapter(adapter);
        getListView().setChoiceMode(ListView.CHOICE_MODE_SINGLE);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.my_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        Toast.makeText(this, String.valueOf(getListView().getCheckedItemCount()),
                Toast.LENGTH_LONG).show();
        return true;
    }
}
