package com.vogella.android.intends;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;


public class MainActivity extends Activity {

    private static final int REQUEST_CODE = 10;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }


    public void onClick(View view){
       EditText text = (EditText) findViewById(R.id.inputforintent);
        String value = text.getText().toString();

        Intent intent = new Intent(this,ResultActivity.class);
        intent.putExtra("onclickvalue",value);
        //startActivity(intent);
        startActivityForResult(intent,REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        if(resultCode==RESULT_OK && requestCode == REQUEST_CODE){
            if(data.hasExtra("returnkey")){
                String result = data.getExtras().getString("returnkey");
                if(result != null && result.length()>0){
                    Toast.makeText(this,result, Toast.LENGTH_SHORT).show();
                }
            }
        }
    }




//Explicit intend - class ActivityTwo represents an activity
    /*
    Intent i = new Intent(this,ActivityTwo.class);
    i.putExtra("Value","This value one for acivity Two");
    i.putExtra("Value","This value one for acivity One");
     */

//Tells android to view a webpage - implicit
    /*
    Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse("www.facebook.com"));
    startActivity(i);
    */

//Intents got header data = the desired action etc - recieving intend
    /*
        //getting data from bundle
    Bundle extras = getIntent().getExtras();
    if (extras == null) {
        return;
    }
        // get data via the key
    String value1 = extras.getString(Intent.EXTRA_TEXT);
    if (value1 != null) {
        // do something with the data
    }*/


// After button click - sends intent
    /*
    Intent intent = new Intent(Intent.ACTION_SEND);
    intent.setType("text/plain");
    intent.putExtra(android.content.Intent.EXTRA_TEXT, "News for you!");
    startActivity(intent);
     */

//Retrieving result data from a sub-activity
    /*
    When closing an app:
    - if activity started by startActivity(intent) - no result required, no feedback
    - startActivityForResult() - expect feedback, you can specify result code to
    determine which act. started, it is returned to you. The started activity can
    also set a result code which the caller can use to determine if the act.
    was canceled or not. Once activity ends
    - onActivityResult() - is called, can perform actions based on result
    - finish() - used by sub-activity, to create new intent and put data into it
    - setResult() - used by sub-activity
     */

//How to trigger an intent with startActivityForResult()
    /*
    public void onClick(View view)
    {
    Intent i = new Intent(this, ActivityTwo.class);
    i.putExtra("Value1","This value one for ActivityTwo ");
    i.putExtra("Value2","This value two ActivityTwo");
    //set the request code to any code you like
    //you can identify the callback via this code
    startActivityForResult(i,REQUEST_CODE); //started activity is called sub-activity
    }
     */

//When sub-activity is finished - send back data by intent - this is done via finish()
    /*
    @Override
    public void finish()
    {
    //prepare data intent
    Intent data = new Intent();
    data.putExtra("returnKey1","Swinging on a star.");
    data.putExtra("returnKey2","You could be better than you are.");
    //activity finieshed ok, return the data
    setResult(RESULT_OK,data);
    super.finish();
    }
     */

//Once sub-activity finishes, a method onActivityResult() in the calling activity is called
    /*
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
    if(resultCode == RESULT_OK && requestCode == REQUEST_CODE){
        if(data.hasExtra("returnKey1")){
            Toast.makeText(this, data.getExtras().getString("returnKey1"),
                Toast.LENGTH_SHORT).show();
        }
    }
     */

//Register an activity as browser
    /*
    <activity android:name=".BrowserActivity"
              android:label="@string/app_name">
      <intent-filter>
        <action android:name="android.intent.action.VIEW" />
        <category android:name="android.intent.category.DEFAULT" />
        <data android:scheme="http" />
      </intent-filter>
     </activity>
     */

//Register an activity for the share intent - for ACTION_SENT, only relevant
//for the text/plain mime tyoe
    /*
    <activity>
        android:name=".ActivityTest"
            android:label="@string/app_name">
            <intent-filter>
                <action android:name="android.intent.action.SEND"/>

                <category android:name="android.intent.category.DEFAULT"/>

                <data android:mimeType="text/plain" />
        </intent-filter>
     </activity>
     //if component does not define an intent filter, it can only be called by explicit intents
     */

//Determine valid intent receivers
    /*
    public static boolean isIntentAvailable(Context ctx, Intent intent)
    {
        final PackageManager mgr = ctx.getPackageManager();
        List<ResolveInfo> list = mgr.queryIntentActivities(intent,
            PackageManager.MATCH_DEFAULT_ONLY);
        return list.size() > 0;
     } //base on result you can hide or disable ceratin menus
     */

}

/*
Task - reuse of other apps components, example: trigger photos from android
    to use in your app.
Intends - android.content.Intend - send them to system to define the components
    you are targeting(own app or different). Via startActivity() - should start
    activity. Can obtain data via Bundle.
    To start activity use startActivity(intent)

    Starting new act via intend:
Intent i = new Intent(this, ActivityTwo.class);
startActivity(i);

Intends - implicit(evaluate based on data), explicit - define target component

Component can register itself via an intent filter(specifies to what type of
data it responds) - can be registered statistically via AndroidManifest.xml or
dynamically via code(when broadcast recieving). If intent is sent to the Android,
it runs receiver determination. If serveral comp. have registered for the same
intent filter, the user can decide which component should be started.

Intents can be used to send broadcast messages. Broadcast receiver can reg. such event.
Your app can register such events and react(new email, system boot)
*/