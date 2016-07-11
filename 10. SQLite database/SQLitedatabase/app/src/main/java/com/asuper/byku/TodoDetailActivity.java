package com.asuper.byku;

import android.app.Activity;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.asuper.byku.contentprovider.MyTodoContentProvider;
import com.asuper.byku.database.TodoTable;

/**
 * Created by Byku on 21.06.2016.
 *
 * TodoDetailActivity - allows to enter a new todo item or to change existing
 */
public class TodoDetailActivity extends Activity {
    private Spinner mCategory;
    private EditText mTitleText;
    private EditText mBodyText;

    private Uri todoUri;

    @Override
    protected void onCreate(Bundle bundle) {
        Log.i("LOG","TodoDetailActivity: onCreate");
        super.onCreate(bundle);
        setContentView(R.layout.todo_edit);

        mCategory = (Spinner) findViewById(R.id.category);
        mTitleText = (EditText) findViewById(R.id.todo_edit_summary);
        mBodyText = (EditText) findViewById(R.id.todo_edit_description);
        Button confirmButton = (Button) findViewById(R.id.todo_edit_button);
        //gets it from:  i.putExtra(MyTodoContentProvider.CONTENT_ITEM_TYPE, todoUri); in TodosOverviewActivity
        Bundle extras = getIntent().getExtras();

        // check from the saved Instance
        todoUri = (bundle == null) ? null : (Uri) bundle.getParcelable(MyTodoContentProvider.CONTENT_ITEM_TYPE);
        Log.i("LOG","TodoDetailActivity: onCreate: "+todoUri);
        // Or passed from the other activity
        if (extras != null) {
            todoUri = extras.getParcelable(MyTodoContentProvider.CONTENT_ITEM_TYPE);
            Log.i("LOG","TodoDetailActivity: onCreate: "+todoUri);
            fillData(todoUri);
        }


        //above - passing data if we click on an item on the list
        confirmButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                if (TextUtils.isEmpty(mTitleText.getText().toString())) {
                    Log.i("LOG","TodoDetailActivity: onCreate: onClick: empty");
                    makeToast();
                } else {
                    Log.i("LOG","TodoDetailActivity: onCreate: onClick: not empty");
                    saveToBase();
                    setResult(RESULT_OK);
                    finish();
                }
            }

        });
    }

    private void fillData(Uri uri) {
        Log.i("LOG","TodoDetailActivity: fillData");
        String[] projection = { TodoTable.COLUMN_SUMMARY,
                TodoTable.COLUMN_DESCRIPTION, TodoTable.COLUMN_CATEGORY };
        Cursor cursor = getContentResolver().query(uri, projection, null, null,
                null);
        if (cursor != null) {
            //moves cursor to the first row
            cursor.moveToFirst();
            //getString(columnIndex)
            //returns zero-based index of the given column name
            String category = cursor.getString(cursor
                    .getColumnIndexOrThrow(TodoTable.COLUMN_CATEGORY));
            Log.i("LOG","TodoDetailActivity: fillData, cur: " + cursor +", cat: " + category);
            //mCategory - spinner
            Log.i("LOG","TodoDetailActivity: fillData, mCategory.getCount(): " + mCategory.getCount());
            for (int i = 0; i < mCategory.getCount(); i++) {
                String s = (String) mCategory.getItemAtPosition(i);
                Log.i("LOG","TodoDetailActivity: fillData, mCategory.getItemAtPosition(): " + mCategory.getItemAtPosition(i));
                //if two strings equal(it does not consider case)
                if (s.equalsIgnoreCase(category)) {
                    //jumps directly into specific data, ret void
                    mCategory.setSelection(i);
                }
            }

            mTitleText.setText(cursor.getString(cursor
                    .getColumnIndexOrThrow(TodoTable.COLUMN_SUMMARY)));
            mBodyText.setText(cursor.getString(cursor
                    .getColumnIndexOrThrow(TodoTable.COLUMN_DESCRIPTION)));

            // always close the cursor
            cursor.close();
        }
    }

    protected void onSaveInstanceState(Bundle outState) {
        Log.i("LOG","TodoDetailActivity: onSaveInstanceState");
        super.onSaveInstanceState(outState);
        saveState();
        outState.putParcelable(MyTodoContentProvider.CONTENT_ITEM_TYPE, todoUri);
    }
    //when confirming the activity pauses
    @Override
    protected void onPause() {
        Log.i("LOG","TodoDetailActivity: onPause");
        super.onPause();
        saveState();
    }
    //then launches saveState
    private void saveState() {
    }

    private void makeToast() {
        Log.i("LOG","TodoDetailActivity: makeToast");
        Toast.makeText(TodoDetailActivity.this, "Please maintain a summary",
                Toast.LENGTH_LONG).show();
    }

    public void saveToBase(){
        Log.i("LOG","TodoDetailActivity: saveState");
        String category = mCategory.getSelectedItem().toString();
        String summary = mTitleText.getText().toString();
        String description = mBodyText.getText().toString();

        // only save if either summary or description
        // is available

        if (description.length() == 0 && summary.length() == 0) {
            return;
        }
        //this class can store values that content resolver processes
        ContentValues values = new ContentValues();
        //key and value
        values.put(TodoTable.COLUMN_CATEGORY, category);
        values.put(TodoTable.COLUMN_SUMMARY, summary);
        values.put(TodoTable.COLUMN_DESCRIPTION, description);

        if (todoUri == null) {
            // New todo
            todoUri = getContentResolver().insert(MyTodoContentProvider.CONTENT_URI, values);
        } else {
            // Update todo
            getContentResolver().update(todoUri, values, null, null);
        }
    }
}