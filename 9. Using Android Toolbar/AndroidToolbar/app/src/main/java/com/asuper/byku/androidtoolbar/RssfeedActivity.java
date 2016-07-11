package com.asuper.byku.androidtoolbar;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;
import android.widget.Toolbar;


public class RssfeedActivity extends Activity implements MyListFragment.OnItemSelectedListener, ActionMode.Callback {

    private RssItem selectedRssItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rssfeed);
        Toolbar tb = (Toolbar) findViewById(R.id.toolbar);
        setActionBar(tb);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        Toolbar tb = (Toolbar) findViewById(R.id.toolbar);
        tb.inflateMenu(R.menu.mainmenu);
        tb.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener(){
            @Override
            public boolean onMenuItemClick(MenuItem item){
                return onOptionsItemSelected(item);
            }
        });
        return true;
    }

    @Override
    public void onRssItemSelected(String link){
        boolean dual_pane = getResources().getBoolean(R.bool.dual_pane);
        if(dual_pane) {
            DetailFragment fragment = (DetailFragment) getFragmentManager().findFragmentById(R.id.detailFragment);
            fragment.setText(link);
        } else {
            Intent intent = new Intent(getApplicationContext(),DetailActivity.class);
            intent.putExtra(DetailActivity.EXTRA_URL,link);
            startActivity(intent);
        }
    }

    //NEW
    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()){
            case R.id.action_refresh:
                MyListFragment fragment = (MyListFragment) getFragmentManager().findFragmentById(R.id.listFragment);
                fragment.updateListContent();
                break;
            case R.id.action_settings:
                Intent intent = new Intent(this, MyPreferences.class);
                startActivity(intent);
                Toast.makeText(RssfeedActivity.this, "Action Settings selected", Toast.LENGTH_SHORT).show();
                break;
            default:
                break;
        }
        return true;
    }

    @Override
    public void onRssItemSelected(String link){
        if(getResources().getBoolean(R.bool.dual_pane)){
            DetailFragment fragment = (DetailFragment) getFragmentManager().findFragmentById(R.id.detailFragment);
            fragment.setText(link);
        } else {
            Intent intent = new Intent(getApplicationContext(), DetailActivity.class);
            intent.putExtra(DetailActivity.EXTRA_URL,link);
            startActivity(intent);
        }
    }

    @Override
    public void showContextmenu(RssItem item){
        this.selectedRssItem=item;
        startActionMode(this);
    }

    @Override
    public void goToActionMode(RssItem item){
        this.selectedRssItem = item;
        startActionMode(this);
    }

    @Override
    public boolean onCreateActionMode(ActionMode mode, Menu menu){
        MenuInflater inflater = mode.getMenuInflater();
        inflater.inflate(R.menu.actionmode, menu);
        return true;
    }

    @Override
    public boolean onPrepareActionMode(ActionMode mode, Menu menu){
        return false;
    }

    @Override
    public boolean onActionItemClicked(ActionMode mode, MenuItem item){
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.putExtra(Intent.EXTRA_TEXT,"I found this interesting link" + selectedRssItem.getLink());
        intent.setType("text/plain");
        startActivity(intent);
        mode.finish();
        selectedRssItem = null;
        return true;
    }

    @Override
    public void onDestroyActionMode(ActionMode mode) {
    }
}//*/

//1. Introduction to the toolbar
/**
 * 1.1. What is the toolbar(action bar)
 *  In A 5.0 - Toolbar viewgroup. Before A 5.0 it was hardcoded. It is enabled for devices
 *  targeting API version 11 or higher. With API less that 11 the apps use options menu.
 *
 * 1.3. Action bar on devices lower that API 21
 *  Use appcompat-v7 support library.
 *  Add com.android.support:appcompat to gradle build file.
 */
//2. Using the toolbar
/**
 * 2.1. Entries in the action bar(actions)
 *  Are called actions. Can be created via code, but they are typically defined in an XML resource file.
 *  Each menu definition contained in res/menu folder.
 *
 * 2.2. Creating actions
 *  @creating_actions.xml
 *  To inflate in action bar:
 @Override
 public boolean onCreateOptionsMenu(Menu menu){
    MenuInflater inflater = getMenuInflater();
    inflater.inflate(R.menu.creating_actions, menu);
    return true;
 }
 *
 *  Good practice: define actions in XML files, otherwise code might be boilerplate.
 *
 * 2.3. Reacting to action selection
 *  onOptionsItemSelected in when corresponding act called. Get's selected
 *  action as parameter.
 @Override
 public boolean onOptionsItemSelected(MenuItem item){
    switch(item.getItemId()){
    //action with ID action_refresh was selected
    case R.id.action_refresh:
        Toast.makeText(this, "Refresh selected", Toast.LENGTH_SHORT).show();
        break;
    //action wth ID action_settings was selected
    case R.id.action_settings:
        Toast.makeText(this, "Settings selected", Toast.LENGTH_SHORT).show();
        break;
    default:
        break;
    }
    return true;
 }
 *
 * 2.4. Search and action in the action bar
 *  Use findItem() of the menu class. Allows to search by id.
 *
 * 2.5. Changing the menu
 *  onCreateOptionsMenu() - called once, if you want to change it use:
 *  invalidateOptionsMenu(), then method above is called again
 */
//3. Customizing the toolbar
/**
 * 3.1. Chaning the visibility of the toolbar bar
 getActionBar().hide().show()
 *  or changing its text at runtime
 getActionBar().setSubtitle("mytest").setTitle("vogella.com");
 *
 * 3.2. Assigning a drawable
 *  ActionBar.setBackgroundDrawable() (best to provide a scalable image)
 *  As og A 4.2 the background can be animated through AnimationDrawable
 *
 * 3.3. Dimming the navigation buttons
 *  (those below)
 *  getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
 *
 *  3.4. Using immersive full screen mode
 @method
 private void hideSystemUi(){
    getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_FULLSCREEEN | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION |
 View.SYSTEM_UI_FLAG_FULLSCREEN | View.SYSTEM_UI_FLAG_IMMERSIVE
 }
 *
 * 3.5. Enabling the split toolbar
 *  You can define spliting toolbar if not enough space by default, use:
 *  android:uiOptions="SplitActionBarWhenNarrow" in AndroidManifest.xml
 */
//4. Contributing to the action bar with fragments
/**
 * setHasOptionsMenu(true) - in onCreate() method of fragment. The android
 * framework will cann onCreateOptionsMenu() method of frragment class and add
 * its menu items to the ones added by the activity.
 */
//5. Making the toolbar context sensitive with the contextual action mode.
/**
 * Contextual actio mode - activates a toolbar that overlays the application
 * toolbar for the duration of a particualr sub-task.
 *
 * startActionMode() - on view or on activity, it get ActionModeCallback which
 * is responsible for life cycle of contextual action bar.
 *
 * registerForContextMenu(view) - assigning conext menu to view by click or long
 * press
 *
 * onCreateContextMenu() - is called every time a context menu is activated,
 * contextMenu is discarted after each usage.
 * You should prefer to use contextual action mode over usage of context menu.
 */
//6. Exercise: Using the contextual action mode
/**
 * 6.1. Target
 *  Adding contextual menu
 * 6.2. Creating menu resource in the res/menu folder
 */