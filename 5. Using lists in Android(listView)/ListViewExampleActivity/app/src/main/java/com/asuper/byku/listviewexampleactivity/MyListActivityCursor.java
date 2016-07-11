package com.asuper.byku.listviewexampleactivity;

import android.app.ListActivity;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.widget.ListAdapter;
import android.widget.SimpleCursorAdapter;

/**
 * Created by Byku on 19.05.2016.
 */
public class MyListActivityCursor extends ListActivity {
    /*
    Called when the activity is first created
     */

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        Cursor mCursor = getContacts();
        startManagingCursor(mCursor); //use CursorLoader api! Ofloads UI
        //now create a new list adapter bound to the cursor
        //SimpleListAdapter is designed for binding to a Cursor
        ListAdapter adapter = new SimpleCursorAdapter(this,//Context
                android.R.layout.two_line_list_item,//specify row template
                mCursor,//pass cursor to bind
                new String[]{ContactsContract.Contacts._ID,ContactsContract.Contacts.DISPLAY_NAME},
                //array of cursor columns to bind
                new int[]{android.R.id.text1,android.R.id.text2});
                //parallel array of which template objects to bind to those
        //columns

        setListAdapter(adapter);
    }

    private Cursor getContacts(){
        //Run query
        Uri uri = ContactsContract.Contacts.CONTENT_URI;
        String[] projection = new String[]{ContactsContract.Contacts._ID,ContactsContract.Contacts.DISPLAY_NAME};
        String selection = ContactsContract.Contacts.IN_VISIBLE_GROUP + " = '" + ("1")+"'";
        String[] selectionArgs = null;
        String sortOrder = ContactsContract.Contacts.DISPLAY_NAME + " COLLATE LOCALIZED ASC";
        return managedQuery(uri, projection, selection, selectionArgs, sortOrder);
    }

}

//18. Simple Cursor Adapter
/*
- when working with content provider or directly with the database
    you can use the SimpleCursorAdapter to define data for ListView
 */