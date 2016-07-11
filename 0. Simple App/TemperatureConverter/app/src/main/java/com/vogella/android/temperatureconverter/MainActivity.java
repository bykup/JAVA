package com.vogella.android.temperatureconverter;

import android.app.Activity; //interact with user, single focused thing user can do
//starts the UI
import android.os.Bundle; //used for passing data between various activities
import android.view.View; //basic building block for user interface componenets
import android.widget.EditText; //overlay over textView, configures itself to be editable
import android.widget.RadioButton; //source for radiobutton control
import android.widget.Toast; //toast widget - shows stuff :P
import android.widget.ImageView;    //displays an arbitrary image, can load images
import android.os.Handler;

public class MainActivity extends Activity{ //my "main" class, always start with it
    private EditText text; //creates a "text" object from EditText class
    @Override
    public void onCreate(Bundle savedInstanceState){ //when activity(app) is created, bundle is
        //container with stored data, now it takes savedInstanceState, whis is null currently
        super.onCreate(savedInstanceState); //telling Dalvik to run this code in addition to the
        //existing code in onCreate() in parent class, super tells to override method from super class
        setContentView(R.layout.activity_main); //loads UI components from activity_main from /layout/
        text = (EditText) findViewById(R.id.inputValue); //throwing inputValue id at text
    }

    //this method is called at button click because we assigned
    //the name to the "onClick" property of the button
    boolean good = false, lay = false;
    public void onClick(View view){ //takes view object(it's status)
        switch(view.getId()){ //now the id from the widget
            case R.id.button1:
                RadioButton celsiusButton = (RadioButton) findViewById(R.id.radio0); //creating radiobutton object connected to radio0
                RadioButton fahrenheitButton = (RadioButton) findViewById(R.id.radio1); //and radio1 ui objects fromm activity_main.xml
                if(text.getText().length()==0){ //text assigned to inputValue button, checking if it's empty!
                    Toast.makeText(this,"Please enter a valid number", //creates a toast, points to "this"
                            Toast.LENGTH_LONG).show(); //with text and how long it should stick
                    Handler handler = new Handler(); //let's create handler
                    handler.postDelayed(new Runnable(){ //that waits 2000 ms
                        @Override
                        public void run()
                        {   //to show another toast
                            Toast.makeText(MainActivity.this,"PROGRAMMING BITCH! YEAH!",Toast.LENGTH_LONG).show();
                        }
                    },2000);
                    return;
                }
                //below - obvious
                float inputValue = Float.parseFloat(text.getText().toString()); //from text parsing to inputValue
                if(celsiusButton.isChecked()){
                    text.setText(String.valueOf(ConverterUtil.convertFahrenheitToCelsius(inputValue)));
                    celsiusButton.setChecked(false);
                    fahrenheitButton.setChecked(true);
                }else{
                    text.setText(String.valueOf(ConverterUtil.convertCelsiusToFahrenheit(inputValue)));
                    fahrenheitButton.setChecked(false);
                    celsiusButton.setChecked(true);
                }
                break;
            case R.id.button2:
                ImageView image = (ImageView) findViewById(R.id.myicon);
                if(!good) {
                    image.setImageResource(R.drawable.ic_drafts);
                    good = true;
                } else {
                    image.setImageResource(R.drawable.ic_tethering);
                    good = false;
                }
                break;
            case R.id.buttonla:
                if(!lay) {
                    setContentView(R.layout.activity_second);
                    lay = true;
                } else {
                    setContentView(R.layout.activity_main);
                    lay = false;
                }
                break;
        }
    }

}