package com.asuper.byku.rssfeedactivity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

public class RssfeedActivity extends AppCompatActivity implements MyListFragment.OnItemSelectedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //depending on orientation: uses activity_rssfeed from layout(land) or layout-port
        //see: https://developer.android.com/guide/topics/resources/providing-resources.html
        setContentView(R.layout.activity_rssfeed);
    }

    @Override //uses interface class onRssItemSelected
    public void onRssItemSelected(String link){
        Log.i("Info "," RssfeedActivity: onRssItemSelected");
        //we're getting resource depending on orientation(port or land)
        boolean dual_pane = getResources().getBoolean(R.bool.dual_pane);
        //depending if it is true(portrait) or false(landscape)
        if(dual_pane) { //true = horizontal
            //we're initializing fragment with a class created from detailFragment fragment
            DetailFragment fragment = (DetailFragment) getFragmentManager().findFragmentById(R.id.detailFragment);
            fragment.setText(link);
        } else {
            //passing a new intent and launching DetailActivity
            Intent intent = new Intent(getApplicationContext(),DetailActivity.class);
            intent.putExtra(DetailActivity.EXTRA_URL,link);
            startActivity(intent);
        }
    }
}

//2. Fragments
/**
 * 2.1. What are single-pane or multi-pane layouts
 *  panel/panel - represents a part of ui. Depending on space shows more or les
 *
 * 2.2. What are fragments?
 *  Fragment - independent android component which can be used by an activity.
 *      Fragment encapsulates functionality. Runs in the context of an activity, but has its
 *      own life cycle, own user interface, possible to define headless fragments(without ui).
 *
 * 2.3. Fragments and context access
 *  Fragments don't subclass the Context class, have to use getActivity() to get parent activity.
 *
 * 2.4. Advantages of using fragments
 *  - simplify reuse of components in different layouts
 *  - it possible to dynamically add and remove fragments from an activity
 *
 * 2.5. How to support different screensizes with fragments
 *  - define it in layout file of an activity that it contains fragments(static definition)
 *  - modify fragments of an activity at runtime(dynamic definition)
 *
 *  To display different fragments in my activity based on the actual available space I can:
 *      - use one activity that displays two fragments for tablets and handset devices. Change at
 *      runtime the fragments displayed by the activity whenever necessary. In this scenario, you
 *      typically define instances of the FrameLayout class as placeholder in your layout and add
 *      the fragments at runtime to them.
 *      - use separate activities to host each fragment on a handset. Example: tablet UI uses two
 *      fragments in an activity, use the same activity for handsets but supply an alternative layout
 *      that includes just one fragment.If detailed fragment is there, the main activity tells the
 *      fragment that it should update itself. If the detail fragment is not available, the main
 *      activity starts the detailed activity. - dynamic option more flexible bit harder to implement *
 */
//3. Defining fragments
/**
 * 3.1. Defining fragments:
 *  - extend android.app.Fragment or any of subclasses(ListFragment, DialogFragment, PreferenceFragment,
 *  WebViewFragment)
 *
 * 3.2. Application communication with fragments
 *  - to increase reuse of fragments they should not communicate directly with each other
 *  - communication should be done by host activity
 *  - fragment should define interface as an inner type, and the activity which uses this fragment
 *  should implement it(the fragment has no clue about the mother activity). onAttach() - checks
 *  if the activity correctly implemented this interface
 */
//4. Fragment life-cycle
/**
 * Connected to life-cycle of hosting activity but has it's own life-cycle.
 *  onAttach() - the fragment is associated with activity instance(but are not fully initialized)
 *      As argument we get a reference to the activity which uses the fragment for a further
 *      initialization work.
 *  onCreate() - Fragment is created(called after onCreate() of activity, but before
 *      onCreateView() of the fragment)
 *  onCreateView() - creates view hierarchy. Here we create user interface. We can inflate layout
 *      via inflate() call of the Inflator object passed as a parameter to this method.
 *      Do not interact with activity here, it is not fully initialized.
 *  onActivityCreated() - activity, fragment instance, view hierarchy has been created.
 *      Can by accessed with findViewById(). In this method we can instantiate objects which
 *      require a Context object.
 *  --- VISIBLE ---
 *  onStart() - when fragment is visible
 *  onResume() - fragment is active
 *  onPause() - is visible but inactive. E.g. another activity is animating on top of the
 *      activity which contains te fragment.
 *  --- NOT VISIBLE ---
 *  onStop() - becomes not visible
 *  onDestroyView() - destroys view of fragment, if fragment recreated from backstack, this
 *      method is called and afterwards onCreateView().
 *  onDestroy() - not guaranteed to be called by the android platform
 */
//5. Defining fragments for your activity
/**
 * 5.1. Adding fragments statically to the layout
 *  To use new fragment, you can statically add it to XML layout.
 *  android:name - points to the corresponding class. Makes sense if you have different
 *  layouts for different device configurations.
 *
 * 5.2. Handling dynamics in fragments
 *  The class that can be accessed in the activity via the getFragmnetManager() method allows
 *  to add, remove and replace fragments in the layout of your activity. The modification
 *  must be performed in a transaction via FragmentTransaction class.
 *  to modify fragments in an activity you typically define a FrameLayout
 *  placeholder in layout file.
 *
   //get fragment manager
 FragmentManager fm = getFragmentManager();

    //add
 FragmentTransaction ft = fm.beginTransaction();
 ft.add(R.id.your_placeholder, new YourFragment());
    //alternatively add it with a tag
    //trx.add(R.id.your_placeholder, new YourFragment(), "detail");
 ft.commit();

    //replace
 FragmentTransaction ft = fm.beginTransaction();
 ft.replace(R.id.your_placeholder, new YourFragment());
 ft.commit();

    //remove
 Fragment fragment = fm.findFragmentById(R.id.your_placeholder);
 FragmentTransaction ft = fm.beginTransaction();
 ft.remove(fragment);
 ft.commit();

 *  This replaces an existing fragment in this container.
 *  If you want to add the transaction to the backstack of Android, you use the addToBackStack().
 *  This will add the action to the history stack of the activity. Will allow to revert the
 *  fragment changes via the back button.
 *
 * 5.3. Check if a fragment is present in the layout
 *  FragmentManager isInLayout() method works on if the fragment is added to the activity via
 *  its layout.
 DetailFragment fragment = (DetailFragment) getFragmentManager().findFragmentById(R.id.detail_frag);
 if(fragment==null || ! fragment.isInLayout()){
    //start new activity
 }
 else {
    fragment.update(...);
 }
 *
 * 5.4. Determine how many fragments are present
 *  One way is to define conf file in values resource folder with a key/value pair defaulting
 *  to false and additional configuration files for the different configurations
 *  setting this value to true.
 *  For example this is a possible default config.xml conf file:
 <resource>
    <item type="bool" name="dual_pane">false</item>
 </resource>
 *  For example in folder values_land you can place another config.xml with different value:
 <resource>
    <item type="bool" name="dual_pane">true</item>
 </resource>
 * in your code you can access the state via the following snipped:
 getResources().getBoolean(R.bool.dual_pane);
 */
//6. Adding fragments transition to the backstack
/**
 * By adding FragmentTransition to the backstack you allow the user to use back button
 * to reverse the transition.
 */
//7. Animations for fragment transitions
/**
 * To define animation during transition that are used on Property Animation API via the
 * setCustomAnimations().
 * Or use standard animations: setTransition(). These are defined via the constants starting with
 * FragmentTransaction.TRANSIT_FRAGMENT_*
 * Both allow defining entry and existing animtions.
 */
//8. Persisting data in fragments
/**
 * 8.1. Persisting data between application restarts
 *  To do it use SQLite database or a file.
 *
 * 8.2. Persisting data between configuration changes.
 *  We can use application object.
 *  setRetainState(true) - call it on fragment. This retains fragment instances
 *  between conf. changes but only if fragments are not added to the backstack.
 *  In this case the data must be stored as member(field).
 *  Not recommended for fragments which have user interface!
 *  If data, which is stored, is supported by the Bundle class, you can use onSaveInstanceState()
 *  to place the data in Bundle, and retrieve it in onActivityCreated().
 */
//9. Fragments for background processing
/**
 * 9.1. Headless Fragments - can be used without defining a user interface.
 *  Simply return null in the onCreateView() method of fragment.
 *  Better to use services for background processing.
 *  If want to use fragments - use headless fragments with setRetainInstance(), this way
 *  you don't have to handle the configuration changes during your asynchronous processing.
 *
 * 9.2. Retained headless fragments to handle configuration changes.
 *  Headless fragments can be used for background calculations or to encapsulate data.
 *  Choose your fragment to be retained. Call setRatainInstance() metod.
 *  To add such fragment to activity use add() of the FragemntManager() class.
 *  Add it with tag to be able to search for it via the findFragmentByTag() from
 *  FragmentManager();
 */
//10. Fragments Tutorial
/**
 * 10.1. Target of this exercise
 *  - how to use fragments in standard Android app without supp library
 *  - in portrait mode RssfeedActivity will show one fragment. From
 *  this user can navigate to another activity which contains another fragment.
 *  - in landscape mode - RssfeedActivity will show both fragments side by side
 *
 * 10.3. Remove compatibility layer
 *  - use activity instead of appcompatactivity
 *
 * 10.4. Create standard layouts
 */
//11. Exercise: Use different number of fragemnts depending on the configuration
