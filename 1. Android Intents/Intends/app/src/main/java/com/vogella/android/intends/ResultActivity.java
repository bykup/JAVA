package com.vogella.android.intends;

import android.app.Activity; //we know that
import android.content.Intent; //that too
import android.os.Bundle; //duh
import android.widget.TextView; //well
import android.widget.EditText; //easy

 /* Created by Byku on 19.04.2016.
 */
public class ResultActivity extends Activity { //declaring a new activity
    @Override

    public void onCreate(Bundle bundle){ //oncreation duh
        super.onCreate(bundle); //runs this code IN ADDITION to the existing code
        setContentView(R.layout.activity_result); //obvious
        //Budle - a mapping from string values to various Parcelable types
        Bundle extras = getIntent().getExtras();
        //gets string from extras with KEY(map) "onclickvalue"
        String inputString = extras.getString("onclickvalue");
        TextView view = (TextView) findViewById(R.id.displayintentextra);
        view.setText(inputString); //sets text to view
    }

     public void finish(){ //method onDestroy() is called! It's not a destructor,
         //the app still works, processes are kept around so the startup phase will be faster
         Intent intent = new Intent(this,MainActivity.class); //new intent that
         //we are passing to MainActivity class
         EditText text = (EditText) findViewById(R.id.returnValue);
         //putting add. data to intent
         intent.putExtra("returnkey",text.getText().toString());
         setResult(RESULT_OK,intent); //set's result in intent
         super.finish(); //now the rest of the code is launched at the end!
         //otherwise it MIGHT delete all stuff before the declarations above
     }
}
