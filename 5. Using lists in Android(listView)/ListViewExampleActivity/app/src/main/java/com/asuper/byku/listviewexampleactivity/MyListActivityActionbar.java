package com.asuper.byku.listviewexampleactivity;

import android.app.ListActivity;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.Toast;

/**
 * Created by Byku on 16.05.2016.
 */
//ActionMode.Callback - it's an interface for action modes
public class MyListActivityActionbar extends ListActivity implements ActionMode.Callback {

    protected Object mActionMode;
    //so no item is selected
    public int selectedItem = -1;

    @Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        String[] values = new String[] { "Android",
                "iPhone", "WindowsMobile",
                "Blackberry", "WebOS", "Ubuntu",
                "Windows7", "Max OS X", "Linux", "OS/2",
                "Ubuntu", "Windows7", "Max OS X",
                "Linux", "OS/2", "Ubuntu",
                "Windows7", "Max OS X",
                "Linux", "OS/2" };

        MySimpleArrayAdapter adapter = new MySimpleArrayAdapter(this, values);
        setListAdapter(adapter);

        getListView().setOnItemLongClickListener(new OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                if (mActionMode != null) {
                    return false;
                }
                selectedItem = position;

                // Start the CAB using the ActionMode.Callback defined above
                mActionMode = MyListActivityActionbar.this.startActionMode(MyListActivityActionbar.this);
                view.setSelected(true);

                if(view.isSelected()){
                    //Toast.makeText(MyListActivityActionbar.this, "Selected",Toast.LENGTH_SHORT).show();
                }else{
                    //Toast.makeText(MyListActivityActionbar.this, "Not selected",Toast.LENGTH_SHORT).show();
                }
                return true;
            }
        });

        getListView().setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //Toast.makeText(MyListActivityActionbar.this, "Small click",Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void show() {
        //Toast.makeText(MyListActivityActionbar.this, String.valueOf(selectedItem), Toast.LENGTH_SHORT).show();
    }

    // Called when the action mode is created; startActionMode() was called
    @Override
    public boolean onCreateActionMode(ActionMode mode, Menu menu) {
        // Inflate a menu resource providing context menu items
        MenuInflater inflater = mode.getMenuInflater();
        // Assumes that you have "contexual.xml" menu resources
        inflater.inflate(R.menu.my_menu, menu);

        mode.setTitle("Wot");
        mode.setSubtitle("Mate");

        return true;
    }

    // Called each time the action mode is shown. Always called after
    // onCreateActionMode, but
    // may be called multiple times if the mode is invalidated.
    @Override
    public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
        Log.i("Info ","MyListActivityActiobar: onPrepareActionMode");
        return false; // Return false if nothing is done
    }
    // Called when the user selects a contextual menu item

    @Override
    public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
        switch (item.getItemId()) {
            case R.id.new_game:
                //show();
                Log.i("Info ","MyListActivityActiobar: onActionItemClicked: new_game");
                // Action picked, so close the CAB
                mode.finish();
                return true;
            case R.id.help:
                Log.i("Info ","MyListActivityActiobar: onActionItemClicked: help");
                //Toast.makeText(MyListActivityActionbar.this, "onActionHelp!",Toast.LENGTH_LONG).show();
                mode.finish();
                return true;
            default:
                return false;
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()){
            case R.id.new_game:
                //Toast.makeText(MyListActivityActionbar.this,"OnOptionsNewGame",Toast.LENGTH_SHORT).show();
                return true;
            case R.id.help:
                //Toast.makeText(MyListActivityActionbar.this,"OnOptionsHelp",Toast.LENGTH_SHORT).show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    // Called when the user exits the action mode
    @Override
    public void onDestroyActionMode(ActionMode mode) {
        mActionMode = null;
        selectedItem = -1;
    }

}

//11. Contextual action mode for ListViews
/*
    setOnItemLongClickListener() - on ListView - set it, to assign a contextual action mode
to a long click on an individual item. In this method you can start ActionMode
 */