package com.vogella.android.applife;


import android.app.Activity; //obvious
import android.app.Notification; //how a notification should be presented to user using(below)
import android.app.NotificationManager; //class that notifies the user of events that happen
import android.widget.Spinner; //we know that
import android.widget.ArrayAdapter; //and that
import android.content.Intent; // and this
import android.os.Bundle; //duh
import android.view.View;//well

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        notify("onCreate"); //initializing notification

        Spinner spinner = (Spinner) findViewById(R.id.spinner);
        String[] values = getResources().getStringArray(R.array.operating_systems); //populating an array
        ArrayAdapter<String> adapter = //using ad array to populate an item "simple_list_item_1" in THIS class
                new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, values);
        spinner.setAdapter(adapter); //starting it
    }

    @Override
    protected void onResume(){
        super.onResume();
        notify("onResume");
    }

    @Override
    protected void onStop(){
        super.onStop();
        notify("onResume");
    }

    @Override
    protected void onDestroy(){
        super.onDestroy();
        notify("onDestroy");
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState){
        super.onRestoreInstanceState(savedInstanceState);
        notify("onRestoreInstanceState");
    }

    @Override
    protected void onSaveInstanceState(Bundle savedInstaceState){
        super.onSaveInstanceState(savedInstaceState);
        notify("onSaveInstanceState");
    }

    public void onClick(View view){
        Intent intent = new Intent(this, FirstActivity.class);
        startActivity(intent);
    }

    private void notify(String methodName){
        String name = this.getClass().getName(); //getting class name
        String[] strings = name.split("\\."); //splitting string in "." and putting into array
        //using Builder class to build notification, then using it's methods to inicizalize and then finally
        //build()!
        Notification noti = new Notification.Builder(this).setContentTitle(methodName+" "+strings[strings.length-1]).setAutoCancel(true).setSmallIcon(R.drawable.ic_launcher).setContentText(name).build();
        NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE); //always getSystemService to bind to NOTIFICATION_SERVICE
        notificationManager.notify((int)System.currentTimeMillis(),noti);
        //sending our noti to notification manager with a distinct key(currentTimeMillis)
    }
}

//Managing the app life cycle
    /*  Priorities:
    1. Foreground
    2. Visible
    3. Service(background app)
    4. Background - with only stopped activities and no services running(or recievers)
    5. Empty(without anything active)

    Using LRU algorithm - least recently used are killed
    */
//Application
    /* New process with unique ID under Unique user. Following lifecycle methods:
    onCreate() - before first components start
    onLowMemory()
    onTrimMemory() - in a way tells when our program is in LRU que, "TRIM_MEMORY_MODERATE"
    tells an app to clean it's memory to survive a bit more.
    onTerminate() - for testing, not called in production
    onConfigurationChanged() - called whenever the conf changes(like horizontal to vertical)
    */
//Content provider Life cycle
    /*
    - it stops when whole app process is killed
     */
//Activity life cycle
    /*  States:
    - running(visible and interacts)
    - paused - visible, partially obscured, might be killed
    - stopped - not visible ut running, might be killed
    - killed - terminated by system or by call to its finish()
        Life cycle:
    - onCreate() - when activity created, used to initialize the activity
    - onResume() - when visible again and user starts interacting, use to initialize
    fields, register listeners, bind to services
    - onPause() - called before activity is not visible anymore, used to release resources,
    save aoo data(e.g. unregister listeners, intent receivers, unbind from services or
    remove system service listeners)
    - onStop() - once activity no longer visible. Guarantee to be called.
    - onDestroy() - not guarantee to be called, do not use it
        Android terminates usually whole processes not single activities.
     */
//Activity instance state
    /*
    Instance state of an activity - is data that is required to restore activity to the
    state user left it.
    onSaveInstanceState() - used to store instance state as Bundle. No called if BACK(button)
    or finish() has been called before. If home is pressend, the activity is saved.
    onRestoreInstanceState() - (use super implementation) - used to restore data,
    better to use it than onCreate() - separate restoring from starting.
     */
//Nonconfiguration instance scope - objects passed if configuration has been changed
    /*
    use - headless retained fragments
     */
//Avoiding configuration change restarts
    /*
    Configuration class - defines current configuration of the device.
    Use "android loader framework" or "headless retained fragments" to disallow
    oriengation changes.

        Fixing the orientation for an activity
     AndroidManifest.xml
     in <activity
            ...
            android:screenOrientation="landscape">

     */
//Security and permissions
    /*
    - each app is a new process(with unique group and user ID) - having own files and data
    - to share it - it has to be explicit via a service or content provider
    - permissions are granted whe app needs them(as of Android 6.0)
    https://developer.android.com/intl/es/training/permissions/requesting.html
     */

