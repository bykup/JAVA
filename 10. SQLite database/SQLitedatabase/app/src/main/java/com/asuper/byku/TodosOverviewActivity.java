package com.asuper.byku;

import android.app.ListActivity;
import android.app.LoaderManager;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

import com.asuper.byku.contentprovider.MyTodoContentProvider;
import com.asuper.byku.database.TodoTable;

/**
 * Created by Byku on 21.06.2016.
 */

/*
 * TodosOverviewActivity displays the existing todo items
 * in a list
 *
 * You can create new ones via the ActionBar entry "Insert"
 * You can delete existing ones via a long press on the item
 */
public class TodosOverviewActivity extends ListActivity implements
        LoaderManager.LoaderCallbacks<Cursor> {
    private static final int ACTIVITY_CREATE = 0;
    private static final int ACTIVITY_EDIT = 1;
    private static final int DELETE_ID = Menu.FIRST + 1;
    // private Cursor cursor;
    private SimpleCursorAdapter adapter;

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {//2
        Log.i("LOG","TodosOverviewActivity: OnCreate");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.todo_list);
        this.getListView().setDividerHeight(2);
        fillData();
        registerForContextMenu(getListView());
    }

    // Create the menu based on the XML defintion
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {//7
        Log.i("LOG","TodosOverviewActivity: OnCreateOptionsMenu");
        //MenuInflate used to inflate xml menu files to menu ui
        MenuInflater inflater = getMenuInflater();
        //well obvious
        inflater.inflate(R.menu.listmenu, menu);
        return true;
    }

    // Reaction to the menu selection
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {//10
        Log.i("LOG","TodosOverviewActivity: OnOptionsItemSelected");
        switch (item.getItemId()) {
            case R.id.insert:
                createTodo();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        Log.i("LOG","TodosOverviewActivity: onContextItemSelected");
        switch (item.getItemId()) {
            case DELETE_ID:
                AdapterContextMenuInfo info = (AdapterContextMenuInfo) item
                        .getMenuInfo();
                Uri uri = Uri.parse(MyTodoContentProvider.CONTENT_URI + "/"
                        + info.id);
                getContentResolver().delete(uri, null, null);
                fillData();
                return true;
        }
        return super.onContextItemSelected(item);
    }

    private void createTodo() {//11
        Log.i("LOG","TodosOverviewActivity: createTodo");
        Intent i = new Intent(this, TodoDetailActivity.class);
        //ACTIVITE_CREATE - it is id to identify intent
        startActivityForResult(i, ACTIVITY_CREATE);
    }

    // Opens the second activity if an entry is clicked
    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        Log.i("LOG","TodosOverviewActivity: onListItemClick");
        super.onListItemClick(l, v, position, id);
        Intent i = new Intent(this, TodoDetailActivity.class);
        Uri todoUri = Uri.parse(MyTodoContentProvider.CONTENT_URI + "/" + id);
        i.putExtra(MyTodoContentProvider.CONTENT_ITEM_TYPE, todoUri);

        // Activity returns a result if called with startActivityForResult
        startActivityForResult(i, ACTIVITY_EDIT);
    }

    // Called with the result of the other activity
    // requestCode was the origin request code send to the activity
    // resultCode is the return code, 0 is everything is ok
    // intend can be used to get data
    @Override
    protected void onActivityResult(int requestCode, int resultCode,
                                    Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
        Log.i("LOG","TodosOverviewActivity: onActivityResult");
    }

    //Prepares data to be loaded
    private void fillData() { //3
        Log.i("LOG","TodosOverviewActivity: fillData");
        // Fields from the database (projection)
        // Must include the _id column for the adapter to work
        String[] from = new String[] { TodoTable.COLUMN_SUMMARY};
        // Fields on the UI to which we map
        int[] to = new int[] { R.id.label};
        //Loader - class that performs asynchronous loading of data
        //initLoader(unique id, bundle - args, iterface the loader will call to report changes)
        //This interface is the LoaderCallbacs
        getLoaderManager().initLoader(0, null, this);
        //We've got two adapters - Cursor(user for databases) and Array(used for arrays)
        //@params: (Context, layout that defines the viw for list item,
        //the database cursor - can be null if it is not available yet,
        //a list of strings representing columns, int - list of columns
        //that should represent data from "from"
        adapter = new SimpleCursorAdapter(this, R.layout.todo_row,  null, from, to, 0);
        setListAdapter(adapter);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v,
                                    ContextMenuInfo menuInfo) {//9
        Log.i("LOG","TodosOverviewActivity: onCreateContextMenu");
        super.onCreateContextMenu(menu, v, menuInfo);
        //groupId(normaly none/0), itemId, order of the item, resource id for the string in item
        menu.add(0, DELETE_ID, 0, R.string.menu_delete);
    }

    // Creates a new loader after the initLoader () call
    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {//4
        Log.i("LOG","TodosOverviewActivity: onCreateLoader " + MyTodoContentProvider.CONTENT_URI.toString());
//Tables requested to be shown
        String[] projection = { TodoTable.COLUMN_ID, TodoTable.COLUMN_SUMMARY };
//Context, Uri, projection, selection
//selectionAgrs, sortOrder
        CursorLoader cursorLoader = new CursorLoader(this, MyTodoContentProvider.CONTENT_URI, projection, null, null, null);
        return cursorLoader;
    }

    //Called when Loader finishes data
    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {//9
        Log.i("LOG","TodosOverviewActivity: onLoadFinished");
        //Swap in a new Cursor, returning the old Cursor
        adapter.swapCursor(data);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        Log.i("LOG","TodosOverviewActivity: onLoaderReset");
        // data is not available anymore, delete reference
        adapter.swapCursor(null);
    }

}



