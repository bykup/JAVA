package com.asuper.byku.listviewexampleactivity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

/**
 * Created by Byku on 16.05.2016.
 */
public class UndoAction extends AppCompatActivity {
    private View viewContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.undo_layout);
        ListView l = (ListView) findViewById(R.id.undo_listview);
        String[] values = new String[]{"Ubuntu", "Android", "iPhone",
                "Windows", "Ubuntu", "Android", "iPhone", "Windows"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1,values);
        viewContainer = findViewById(R.id.undo_undobar);
        l.setAdapter(adapter);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        //Inflate the menu, this adds items to the action bar if it is present
        getMenuInflater().inflate(R.menu.my_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        showUndo(viewContainer);
        return true;
    }

    public void onClick(View view){
        Toast.makeText(this,"Deletion undone", Toast.LENGTH_LONG).show();
        viewContainer.setVisibility(View.GONE);
    }

    public static void showUndo(final View viewContainer){
        viewContainer.setVisibility(View.VISIBLE);
        viewContainer.setAlpha(1);
        viewContainer.animate().alpha(0.4f).setDuration(5000).withEndAction(new Runnable(){
            @Override
            public void run(){
                viewContainer.setVisibility(View.GONE);
            }
        });
    }
}

//12.Implementing undo for an action
/*
XLS - uses FrameLayout to show different parts of the user interface
The button is initially hidden.
 */