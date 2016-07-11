package com.asuper.byku.contentprovider;

import java.util.Arrays;
import java.util.HashSet;

import android.content.ContentProvider;
import android.content.ContentResolver;
import android.content.ContentValues;

import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.text.TextUtils;
import android.util.Log;

import com.asuper.byku.database.TodoTable;
import com.asuper.byku.database.TodoDatabaseHelper;



/**
 * Created by Byku on 21.06.2016.
 */
//ContentProvider - an interface that allows data manipulation on device
//Uri - an address of the data we want to use
public class MyTodoContentProvider extends ContentProvider {
    //database
    private TodoDatabaseHelper database;

    //used for the UriMacher
    private static final int TODOS = 10;
    private static final int TODO_ID = 20;

    private static final String AUTHORITY = "com.asuper.byku.contentprovider";
    private static final String BASE_PATH = "byku";
    //probably an "address" of our database
    public static final Uri CONTENT_URI = Uri.parse("content://" + AUTHORITY + "/"
    + BASE_PATH);

    public static final String CONTENT_TYPE = ContentResolver.CURSOR_DIR_BASE_TYPE + "/todos";

    public static final String CONTENT_ITEM_TYPE = ContentResolver.CURSOR_ITEM_BASE_TYPE + "/todo";
    //used in ContentProvider where it needs to respond to a number of different URIs
    private static final UriMatcher sURIMatcher = new UriMatcher(UriMatcher.NO_MATCH);
    static{
        //authority, path, code
        sURIMatcher.addURI(AUTHORITY, BASE_PATH, TODOS);
        sURIMatcher.addURI(AUTHORITY, BASE_PATH + "/#", TODO_ID);
    }

    @Override
    public boolean onCreate(){ //1
        Log.i("LOG","MyContentProvider: OnCreate");
        database = new TodoDatabaseHelper(getContext());
        return false;
    }
//query - handles requests from clients, can be called from multiple threads
    @Override //Uri, projection - list of columns to put into cursor(null - all columns)
    //selection - a selection criteria to apply when filtering rows(null - all rows included)
    //selectionArgs - string from selection will be replaced in database by strings
    //in selectionArgs
    //sort order
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder){//5
        Log.i("LOG","MyContentProvider: query");
        //Using SQLiteQueryBuilder instead of query() method, this class helps
        //build sql queries
        SQLiteQueryBuilder queryBuilder = new SQLiteQueryBuilder();
        //check if the caller has requested a column which does not exist
        checkColumns(projection);
        //set the table(can set multiple tables like"foo LEFT OUTER JOIN bar ON(...)" - to query
        queryBuilder.setTables(TodoTable.TABLE_TODO);

        //thanks to Uri matcher we can match uri to a number, the using sURIMatcher.match()
        //we return that number so we can use it in switch for example
        int uriType = sURIMatcher.match(uri);
        switch(uriType){
            case TODOS:
                Log.i("LOG","MyContentProvider: query: TODOS");
                break;
            case TODO_ID:
                //adding the ID to the original query, so it is "SELECT "TABLE_TODO" WHERE COLUMN_ID"
                queryBuilder.appendWhere(TodoTable.COLUMN_ID + "=" + uri.getLastPathSegment());
                //getLastPathSegment - returns the number of row
                Log.i("LOG","MyContentProvider: query: " + uri.getLastPathSegment());
                break;
            default:
                throw new IllegalArgumentException("Unknown URI: " + uri);
        }
        //database - TodoDatabaseHelper class - returns a database ready to read/write
        SQLiteDatabase db = database.getWritableDatabase();
        //now we are operating on base with query from above
        Cursor cursor = queryBuilder.query(db,projection,selection,selectionArgs,null, null, sortOrder);
        //make sure that potential listeners are getting notified
        cursor.setNotificationUri(getContext().getContentResolver(),uri);
        return cursor;
    }

    @Override
    public String getType(Uri uri){
        Log.i("LOG","MyContentProvider: getType");
        return null;
    }

    @Override
    public Uri insert(Uri uri, ContentValues values){
        Log.i("LOG","MyContentProvider: insert");
        int uriType = sURIMatcher.match(uri);
        SQLiteDatabase sqlDB = database.getWritableDatabase();
        long id = 0;
        switch(uriType){
            case TODOS:
                //for inserting a row into database
                id = sqlDB.insert(TodoTable.TABLE_TODO,null,values);
                break;
            default:
                throw new IllegalArgumentException("Unknown URI: " + uri);
        }
        getContext().getContentResolver().notifyChange(uri, null);
        return Uri.parse(BASE_PATH + "/" + id);
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs){
        Log.i("LOG","MyContentProvider: delete");
        int uriType = sURIMatcher.match(uri);
        SQLiteDatabase sqlDB = database.getWritableDatabase();
        int rowsDeleted = 0;
        switch(uriType){
            case TODOS:
                rowsDeleted = sqlDB.delete(TodoTable.TABLE_TODO, selection, selectionArgs);
                break;
            case TODO_ID:
                String id = uri.getLastPathSegment();
                if(TextUtils.isEmpty(selection)){
                    rowsDeleted = sqlDB.delete(TodoTable.TABLE_TODO, TodoTable.COLUMN_ID + "=" + id, null);
                } else {
                    rowsDeleted = sqlDB.delete(TodoTable.TABLE_TODO, TodoTable.COLUMN_ID + "=" + id + " and " + selection,
                            selectionArgs);
                }
                break;
            default:
                throw new IllegalArgumentException("Unknown URI: " + uri);
        }
        getContext().getContentResolver().notifyChange(uri, null);
        return rowsDeleted;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs){
        Log.i("LOG","MyContentProvider: update");
        int uriType = sURIMatcher.match(uri);
        SQLiteDatabase sqlDB = database.getWritableDatabase();
        int rowsUpdated = 0;
        switch(uriType){
            case TODOS:
                rowsUpdated = sqlDB.update(TodoTable.TABLE_TODO, values, selection, selectionArgs);
                Log.i("LOG","MyContentProvider: update: TODOS: " + rowsUpdated);
                break;
            case TODO_ID:
                //gets the row
                String id = uri.getLastPathSegment();
                if(TextUtils.isEmpty(selection)){
                    rowsUpdated = sqlDB.update(TodoTable.TABLE_TODO, values, TodoTable.COLUMN_ID + "=" + id, null);
                } else {
                    Log.i("LOG","MyContentProvider: update: TODO_ID: isEmpty:" + selection);
                    rowsUpdated = sqlDB.update(TodoTable.TABLE_TODO, values, TodoTable.COLUMN_ID + "=" + id + selection, selectionArgs);
                }
                Log.i("LOG","MyContentProvider: update: TODO_ID:" + rowsUpdated);
                Log.i("LOG","MyContentProvider: update: getLastPathSegment:" + id);
                break;
            default:
                throw new IllegalArgumentException("Unknown URI: " + uri);
        }
        getContext().getContentResolver().notifyChange(uri, null);
        return rowsUpdated;
    }
    //compares columns requested with columns available
    private void checkColumns(String[] projection){//6
        Log.i("LOG","MyContentProvider: checkColumns");
        String[] available = { TodoTable.COLUMN_CATEGORY,
                TodoTable.COLUMN_SUMMARY, TodoTable.COLUMN_DESCRIPTION,
                TodoTable.COLUMN_ID };
        if(projection != null){
            HashSet<String> requestedColumns = new HashSet<String>(Arrays.asList(projection));
            HashSet<String> availableColumns = new HashSet<String>(Arrays.asList(available));

            //check if all columns which are request are available
            //Log.i("LOG","MyContentProvider: checkColumns: projection != null");
            if(!availableColumns.containsAll(requestedColumns)){
                throw new IllegalArgumentException("Unknown columns in projection");
            }
        }
    }
}
