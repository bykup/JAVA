package com.asuper.byku.sqlitedatabase;

import android.app.Activity;
import android.os.Bundle;
import android.database.Cursor;
import android.net.Uri;
import android.provider.ContactsContract;
import android.widget.TextView;

import com.asuper.byku.R;

public class ContactsActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);
        TextView contactView = (TextView) findViewById(R.id.contactview);

        Cursor cursor = getContacts();

        while(cursor.moveToNext()){
            String displayName = cursor.getString(cursor.getColumnIndex(ContactsContract.Data.DISPLAY_NAME));
            contactView.append("Name: ");
            contactView.append(displayName);
            contactView.append("\n");
        }//*/
    }

    private Cursor getContacts(){
        //Run query
        Uri uri = ContactsContract.Contacts.CONTENT_URI;
        String[] projection = new String[] {ContactsContract.Contacts._ID,ContactsContract.Contacts.DISPLAY_NAME};
        String selection = ContactsContract.Contacts.IN_VISIBLE_GROUP + " = '" + ("1") + "'";
        String[] selectionArgs = null;
        String sortOrder = ContactsContract.Contacts.DISPLAY_NAME + " COLLATE LOCALIZED ASC";



        System.out.println(uri.toString());

        //return null;
        return getContentResolver().query(uri, projection, selection, selectionArgs, sortOrder);

    }
}

//1. SQLite and Android
/**
* 1.1. What is?
 *
*   Supports -  TEXT - string
*               INTEGER - long
*               REAL - double
* 1.2. SQLine in android:
*   Accessing database is slow - better to do it asynchronously
*   "DATA/data/APP_NAME/databases/FILENAME" - it is saved here
*   "DATA" - get it by Environment.getDataDirectory()
 */
//3. SQLite architecture
/**
* 3.1. Packages
*   "android.database" - all necessary classes
*   "android.database.sqlite" - package containgin SQLite specific classes*
* 3.2. Creating and updating database with SQLiteOpenHelper
*   Create subclass of "SQLiteOpenHelper"
*   Call "super()" method of SQLiteOpenHelper.
*   Override:
*       "onCreate()" - called by the framework, if the database is accessed but not yet created
*       "onUpgrade()" - if database version is increased in app code, thanks to it, it is possible
*       to update database and recreate it via "onCreate()"
*  They receive "SqLiteDatabase" object as parameter. It is a JAVA representation of database.
*  "getReadableDatabase()" - get access to SQLiteDatabase in read mode
*  "getWriteableDatabase()" - ... in write mode
*  Tables should use _id for primary key of table, several functions rely on this standard
*  - TIP!
*   Create separate class per table, this class defines static "onCreate()" and "onUpgrade()". These
*   methods are called in the corresponding methods of SQLiteOpenHelper. This way SQLiteOpenHelper
*   stays readable, even if i have several tables.
*
* 3.3. SQLiteDatabase
*   Has:
*   - insert()
*   - update()
*   - delete()
*   - execSQL() - allows execution of SQL statement directly
*   - ContentValues - allows definition of key/values(column id, value - content of this column).
*   They can be used for inserts and updates.
*   - rawQuery()/query()/SQLiteQueryBuilder() - creates query. First: directly accepts SQL statement
*   as input. Second: provides structured interface for specifying the SQL query. SQLiteQueryBuilder
*   is a convenience class that helps to build SQL queries.
*
* 3.4. rawQuery() example:
todo Cursor cursor = getReadableDatabase().rawQuery("select * from table where _id = ?", new String[]{id});
* 3.5. query() example:
todo return database.query(DATABASE_TABLE, new String[]{KEY_ROWID, KEY_CATEGORY, KEY_SUMMARY, KEY_DESCRIPTION}, null, null, null, null, null);
*   @params:
*   DATABASE_TABLE - name of table
*   string - a list of columns to return
*   whereClause - filter for the selection of data, null will select all data
*   selectionArgs - you may include may include in the "whereClause", these placeholders will get
*   replaced by the values from the selectionArgs
*   groupBy - a filter declaring how to group rows, null will cause the rows to not be grouped
*   having - filter for groupd, null means no filter
*   orderBy - null, means no ordering
*
* 3.6. Cursor
*   Cursor - result of query, points to one row of the query result. Android does not have to load all
*   data into memory.
*   "getCount()" - gets number of elements
*   "moveToFirst()" - move between rows
*   "moveToNext()" - move between rows
*   "isAfterLast()" - allows to check if end of query
*   "get*()" - method provied by cursor: "getLong(columnIndex", "getString(CI)"
*   "getColumnIndexOrThrow(String)" - get's column index fro a column name of the table.
*   !!
*   "close()" - cursor has to be closed
*
* 3.7. ListViews, ListActivities and SimpleCursorAdapter
*   ListViews,Views - allow to display a list of elements
*   ListActivities - are specialized activites that use ListViews easier
*   SimpleCursorAdapter - allows to set layout for each row of the ListViews
*   Also you can create arrays to hold IDS and column names.
*   SimpleCursorAdapter will map the columns to the views based on the cursor passed to it.
*   To obtain cursor you should use th loader class.
*/
//4. Tutorial: Using SQLite
/**
 * 4.1. Introduction to the project
 *  DAO - resp. for handling the database connection and for accessing and modyfing data. It converts
 *  database objects to real Java Objects. DAO is resource heavy. More efficient way:
 *  "ContentProvider" or accessing database directly.
 */
//5. Content provider and sharing data
/**
 * 5.1. What is?
 *  - use to share data with other apps
 *  - offers data encapsulation based on URI's(they start with content://)
 *  - allows app to access data stored in: SQLite database, file system, in flat files or remote
 *  server
 *  - it is typically used to share data with other apps
 *  - MUST be declared in manifest
 * 5.2. Base URI of the content provider
 *  -content://test/    - test is a space defined in manifest via android:authorities attr
 * 5.3. Accessing content provider
 *  - as it is required to know the URI's of the provider to access it, it is a good practice to
 *  provide public const for the URI's for other developers.
 * 5.4. Custom content provider
 *  - create class extending android.content.ContentProvider
 *  Declare it in manifest:

 <provider>
    android:authorities="....class name..."
    android:name=".contentprovider.MyTodoContentProvider"
 </provider>

 *  - contentProvider must implement: query(), insert(), update(), delete(), getType(), onCreate()
 *  - good practice to throw an UnsupportedOperationException()
 *  - query() must return Cursor object
 * 5.5. Security and content provider
 *  - cProvider has to be exported: android:exported=true|false in AndroidManifest.xml
 * 5.6. Thread Safety
 *  - cP can be accessed by several programs, use "synchronized" in front of all emthods of provider,
 *  so that only one thread can access these methods at the same time.
 *  - android;multiprocess=true - if your app does not require synchronized data access to provider,
 *  this creates an instance of proved on each client process.
 */
//7. Loader
/**
 * 7.1. Purpose of the Loader class
 *  - loads data asynchronously in an activity or fragment
 *  - can monitor source for changes
 *  - they persist data between configuration changes
 *  - it can cashe the data
 *
 * 7.2. Implementing a Loader
 *  - "AsynctaskLoader" - abstract class can be used as basis for loader implementation
 *  - "LoaderManager" - manages Loader instances
 *  - Loader creation(new or existing):
 todo getLoaderManager().initLoader(0,null,this);
 *  first param, unique ID so a callback class can identify that loader later, second:
 *  bundle that can be giving to callback class for more info, third: "initLoader()" - class which is
 *  called once the initialization has been started(callback class). This class has to implement
 *  "LoaderManager.LoaderCallbacks" interface. Activity or fragment that uses callbacks should
 *  implement "LoaderManager.LoaderCallbacks" interface.
 *  - Loader is not directly created by "getLoaderManager().initLoader()" but must be created by
 *  callback class in the "onCreateLoader()"
 *  - "onLoadFinished()" - is called when Loader has finished reading data asynchronously.
 *
 * 7.3. SQLite database and CursorLoader
 *  - "CursorLoader" - default Loader to handle SQLite database connections.
 *  - "ContentProvider" based on SQLite database would typically use CursorLoader - provides database
 *  query in background thread.
 *  - if "Cursor" becomes invalid, the "onLoaderReset()" method is called on the callback class.
 */
//8. Cursors and Loaders
/**
 * - "SimpleCursorAdapter" class can be used with "ListViews", which have "swapCursor()" method.
 * "Loader" can use this method to update "Cursor" in its "onLoadFinished()" method.
 * "CursorLoader" class reconnect the "Cursor" after configuration change.
 */
//9. Tutorial: SQLite, custom ContentProvider and Loader
/**
 * 9.1. Overview
 *  - creating to-do list - stored in SQLite database accessed via "ContentProvider"
 *  - two activities: one to see to-do items, second to create and change specific to-do item.
 *  - asynchronously loading and managing the "Cursor", main activity will use "Loader"
 */