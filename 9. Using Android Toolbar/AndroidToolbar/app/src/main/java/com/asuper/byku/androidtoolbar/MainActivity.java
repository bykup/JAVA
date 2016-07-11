package com.asuper.byku.androidtoolbar;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.ShareActionProvider;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by Byku on 15.06.2016.
 */
//7. Making the action bar dynamic - adding a custom view for a button or a field

/**
 * Use "setCustomView" method for this from Actionview class.
 * pass "ActionBar.DISPLAY_SHOW_CUSTOM" to "setDisplayOptions()" - to enable display of custom
 * views. *
 */
//7.2. Action View
/**
 * It is a widget that appears as substitute for an action item's button.
 * Action view can be defined through "android:actionLayout" or "android:actionViewClass" to specify
 * a layout resource or widget class to use.
 */

public class MainActivity extends Activity {

    private MenuItem menuItem;
    private ShareActionProvider provider;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ActionBar actionBar = getActionBar();
        actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_HOME | ActionBar.DISPLAY_SHOW_TITLE | ActionBar.DISPLAY_SHOW_CUSTOM);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        //getMenuInflater().inflate(R.menu.main,menu);
        getMenuInflater().inflate(R.menu.activity_main,menu); //ex 8.0
        provider = (ShareActionProvider) menu.findItem(R.id.menu_share).getActionProvider();
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
     switch(item.getItemId()){
         case R.id.menu_load:
             menuItem = item;
             menuItem.setActionView(R.layout.progressbar);
             menuItem.expandActionView();
             TestTask task = new TestTask();
             task.execute("test");
             break;
         case R.id.menu_share:
             doShare();
             break;
         default:
             break;
     }
        return true;
    }

    public void doShare(){
        //populate the share intent with data
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_TEXT,"this is a message for you babe");
        provider.setShareIntent(intent);
    }

    private class TestTask extends AsyncTask<String,Void,String> {
        @Override
        protected String doInBackground(String... params){
            try{
                Thread.sleep(2000);
            }catch(InterruptedException e){
                e.printStackTrace();
            }
            return null;
        }
        @Override
        protected void onPostExecute(String result){
            menuItem.collapseActionView();
            menuItem.setActionView(null);
        }
    }
}
//8. Action pro
/**
 * 8.1. What is an action provider
 *  It defines rich menu interaction in single component:
 *      - it generates action views used in the action bar
 *      - populate dynamically submenus of an action and handles default action invocations
 *      - handles default action invocations
 *
 * "ActionProvider" class - base class for an action provider
 *
 * Currently we have two action providers: "MediaRouteActionProvider", "ShareActionProvider"
 *  "ShareActionProvider" - allows to grap selected content from app which have registered the
 *  "Intent.ACTION_SEND" intent. Example in "activity_share"
 */
//9. Navigation via the application icon
/**
 * 9.1. Application icon as home
 *  Home icon - icon on action bar
 *  When clicked "onOptionsItemSelected() method is called with an action which has the
 *  android.R.id.home ID.
 */