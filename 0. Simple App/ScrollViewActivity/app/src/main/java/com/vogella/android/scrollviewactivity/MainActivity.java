package com.vogella.android.scrollviewactivity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView view =  (TextView) findViewById(R.id.TextView02);
        String s="";
        for (int i=0; i < 500; i++) {
            s += "vogella.com ";
        }
        view.setText(s);
    }
}
