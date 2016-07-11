package com.vogella.android.intends;

import android.app.Activity; //m'kay
import android.content.Intent;  //obvious again
import android.net.Uri; // well we know that already
import android.os.Bundle; //come on
import android.view.View; //ha
//Items in the spinner(subclass of adapterView) come from adapter(ArrayAdapter) associated
// with this view. ArrayAdapter converts ArrayList of objects to View items, loaded into
// ListView container. DataSource <-> Adapter <-> AdapterView(List,Grid,Spinner)
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

public class CallIntentsActivity extends Activity {
    private Spinner spinner; //Spinner object


    /** Called when the activity is first created. */

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grid); //setting activity_grid
        spinner = (Spinner) findViewById(R.id.spinner); //Binds view to object
        //creates an array from external source(app env, id to use as data source,
        //the identifier of the layout to create views), simple_spinner_item default layout
        ArrayAdapter adapter = ArrayAdapter.createFromResource(this,
                R.array.intents, android.R.layout.simple_spinner_item);
        //setDropDownViewResource - specify layout the adaptar should use to display the list of spinner choices
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //adapting our spinner
        spinner.setAdapter(adapter);
    }

    public void spinnerClick(View view){
        int position = spinner.getSelectedItemPosition(); //gets the position
        Intent intent = null; //creates an null intent
        switch (position) {
            case 0:
                //intent = new Intent(Intent.ACTION_CALL,
                //        Uri.parse("tel:(+49)12345789"));
                break;
            case 1:
                intent = new Intent(Intent.ACTION_VIEW,
                        Uri.parse("http://www.vogella.com"));
                break;
            case 2:
                intent = new Intent(Intent.ACTION_DIAL,
                        Uri.parse("tel:(+49)12345789"));
                break;
            case 3:
                intent = new Intent(Intent.ACTION_VIEW,
                        Uri.parse("geo:50.123,7.1434?z=19"));
                break;
            case 4:
                intent = new Intent(Intent.ACTION_VIEW,
                        Uri.parse("geo:0,0?q=query"));
                break;
            case 5:
                intent = new Intent("android.media.action.IMAGE_CAPTURE");
                break;
            case 6:
                intent = new Intent(Intent.ACTION_VIEW,
                        Uri.parse("content://contacts/people/"));
                break;
            case 7:
                intent = new Intent(Intent.ACTION_EDIT,
                        Uri.parse("content://contacts/people/1"));
                break;

        }
        if (intent != null) {
            startActivity(intent);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == Activity.RESULT_OK && requestCode == 0) {
            String result = data.toUri(Intent.URI_INTENT_SCHEME);
            Toast.makeText(this, result, Toast.LENGTH_LONG);
        }
    }

}

