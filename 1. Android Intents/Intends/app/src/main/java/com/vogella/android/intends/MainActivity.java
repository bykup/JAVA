package com.vogella.android.intends;

import java.io.BufferedReader; //buffer an inpit from Reader, interactions are minimized
import java.io.FileNotFoundException;   //for signaling if opening a file has failed or not
import java.io.IOException; //signals if I/O exception of some sort has occurred(general class for I/O)
import java.io.InputStreamReader; //reads and returns a single char(or -1 if end of stream)
import java.io.InputStream; //a readable source of bytes - use InputStreamReader to adapt to a char stream
import java.net.URL; //a pointer to resource on www, transforms a url adress
import android.net.Uri; //builds URI - a string of chars to identify web resource(http://www.wykop.pl/costam)

import android.app.Activity; //interact with user, single focused thing user can do
//starts the UI
import android.content.Intent; //abstract describtion of operation to be performed
import android.os.Bundle; //a "basket", using to pass data between activities
import android.os.StrictMode;   //warning that we are not doing anything wrong with UI thread
import android.view.View; //basic building block for user interface componenets
import android.widget.EditText; //overlay over textView, configures itself to be editable
import android.widget.TextView; //well textView duh
import android.widget.Toast;    //toast widget
import android.widget.ImageView;    //displays arbitrary image, can load images
import android.graphics.Bitmap; //stores a bitmap returned from BitmapFactory
import android.graphics.BitmapFactory;  //creates a bitmap from various sources




public class MainActivity extends Activity {

    private static final int CODE_10 = 10;
    private static final int CODE_1 = 1;
    private Bitmap bitmap;
    private ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //allowing network access in the user interface thread
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().
                permitAll().build(); //creates an instance that should permitAll
        StrictMode.setThreadPolicy(policy); //enabling the policy above
        setContentView(R.layout.activity_main); //setting a layout

        TextView text = (TextView) findViewById(R.id.textView); //binds layout to variable
        imageView = (ImageView) findViewById(R.id.imageView);   //binds layout to variable

        Intent intent = getIntent(); //returns an intent that started activity
        String action = intent.getAction();//gets the action that should be performed
        //like "ACTION_VIEW"

                text.setText(action); //just for the test, text = action string
        // To get the action of the intent use
        if (!action.equals(Intent.ACTION_VIEW)) {
            return; //obvious, if Intent is ACTION_VIEW then stop
        }
        // To get the data use
        Uri data = intent.getData(); //Uri data holds a string similar do www adress
        URL url; //holds url adress
        try {
            url = new URL(data.getScheme(), data.getHost(), data.getPath()); //data. returns
            //strings, then url holds them binded(http://, www.wykop.pl/,strona.xml)
            BufferedReader rd = new BufferedReader(new InputStreamReader(url.openStream()));
            //opensStream, then - ISR - returns byte stream into char stream, then we
            // buffer the input
            String line = "";
            while ((line = rd.readLine()) != null) {
                text.append(line); //well we move lines to string
            }

        } catch (Exception e) {
            e.printStackTrace();  //prints throwable and its backtrace
            //to the standard error stream
        }//*/
    }


    public void onClick(View view){ //function onClick used by onClick method in layout
        if(view.getId() == R.id.startintent2) //checking ID
        {
            Intent intent = new Intent(Intent.ACTION_VIEW,Uri.parse("http://www.vogella.com")); //sets an intend with "ACTION_ViEW"
            startActivity(intent); //starts that intent
        } else if (view.getId() == R.id.trigger){ //obvious
            Intent intent = new Intent(this, CallIntentsActivity.class); //calls activity
            //activity: "CallIntentsActivity.class"
            startActivity(intent); //well obvious
        } else {
            //binds view to layout member "inputforintent"
            EditText text = (EditText) findViewById(R.id.inputforintent);
            String value = text.getText().toString(); //obvious

            Intent intent = new Intent(this, ResultActivity.class);
            intent.putExtra("onclickvalue", value); //"onclickvalue" - key, then string
            //as data
            //startActivity(intent);
            startActivityForResult(intent, CODE_10);//Starts an activity for results
            //then starts "onActivityResults" in which it is looking for CODE_10 */
        }
    }


    public void pickImage(View view)
    {   //we are launching an intent to get a picture(or get content)
        Intent intent = new Intent();
        intent.setType("image/*"); //sets a data that we want to send
        intent.setAction(Intent.ACTION_GET_CONTENT); //sets action
        intent.addCategory(Intent.CATEGORY_OPENABLE); //for clarifying intent
        startActivityForResult(intent,CODE_1); //starting activity for result obvious
    }
    //when the user is done with activity, it returns to onAcitivtyResult
    @Override   //requestCode(CODE_1,CODE_10), RESULT_OK || RESULT_CANCELED, Intent that carries data
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        InputStream stream = null; //superclass - representing a stream of bytes
        if(resultCode==RESULT_OK && requestCode == CODE_10){
            if(data.hasExtra("returnkey")){ //the kye it has to recognise
                String result = data.getExtras().getString("returnkey"); //gets data
                //from intent
                if(result != null && result.length()>0){
                    Toast.makeText(this,result, Toast.LENGTH_SHORT).show();
                    //writes that data as Toast
                }
            }
        }
        if(resultCode==RESULT_OK && requestCode==CODE_1){ //another rule
            try{
                //TextView text = (TextView) findViewById(R.id.textView);
               // text.setText(data.getDataString());
                //recyle unusaed bitmas
                if(bitmap != null){ //bitmap is not empty
                    bitmap.recycle();   //we ... well.. empty it
                }//get intent.getData() - returns URI - adress to a file(or http)
                //getContentResolver - returns abstract class ContentResolver
                //open a stream on the content associated with a content URI
                //FileNotFoundException - if no data associated with the URI
                stream = getContentResolver().openInputStream(data.getData());
                //decode input stream into bitmap - BitmapFactory - class
                //used to create bitmaps
                bitmap = BitmapFactory.decodeStream(stream);

                imageView.setImageBitmap(bitmap); //sett image bitpam
            }   catch (FileNotFoundException e){
                e.printStackTrace();
            } finally { //done always(even if return in catch, then return in finally final)
                if(stream!=null)
                    try{
                        stream.close(); //return IOException
                    }catch(IOException e){
                        //printra this throwable and its backtrace to the standard error
                        //stream
                        e.printStackTrace();
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