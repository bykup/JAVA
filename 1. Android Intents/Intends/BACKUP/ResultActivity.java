package com.vogella.android.intends;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.EditText;

 /* Created by Byku on 19.04.2016.
 */
public class ResultActivity extends Activity {
    @Override

    public void onCreate(Bundle bundle){
        super.onCreate(bundle);
        setContentView(R.layout.activity_result);

        Bundle extras = getIntent().getExtras();
        String inputString = extras.getString("onclickvalue");
        TextView view = (TextView) findViewById(R.id.displayintentextra);
        view.setText(inputString);
    }

     public void finish(){
         Intent intent = new Intent(this,MainActivity.class);
         EditText text = (EditText) findViewById(R.id.returnValue);

         intent.putExtra("returnkey",text.getText().toString());
         setResult(RESULT_OK,intent);
         super.finish();
     }
}
