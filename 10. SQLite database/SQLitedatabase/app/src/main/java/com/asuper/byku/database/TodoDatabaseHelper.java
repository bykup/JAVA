package com.asuper.byku.database;


import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by Byku on 21.06.2016.
 */
//9.3. Dtabase classes - manges daabase creation and version management
public class TodoDatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "todotable.db";
    private static final int DATABASE_VERSION = 1;

    public TodoDatabaseHelper(Context context) {
        //if DATABASE_VERSION is higher then onUpgrade will be used
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        Log.i("LOG","TodoDatabaseHelper: constructor");
    }

    // Method is called during creation of the database
    @Override
    public void onCreate(SQLiteDatabase database) { //8
        //creating data for every line(?)
        Log.i("LOG","TodoDatabaseHelper: onCreate");
        TodoTable.onCreate(database);
    }

    // Method is called during an upgrade of the database,
    // e.g. if you increase the database version
    @Override
    public void onUpgrade(SQLiteDatabase database, int oldVersion,
                          int newVersion) {
        Log.i("LOG","TodoDatabaseHelper: onUpgrade");
        TodoTable.onUpgrade(database, oldVersion, newVersion);
    }

}