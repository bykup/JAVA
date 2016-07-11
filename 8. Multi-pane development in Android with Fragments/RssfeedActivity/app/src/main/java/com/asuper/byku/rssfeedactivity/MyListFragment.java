package com.asuper.byku.rssfeedactivity;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;


/**
 * Created by Byku on 29.05.2016.
 */

//3.2. Application communication with fragment
//fragment is communicating a value to its parent activity, example implementation:
public class MyListFragment extends Fragment{

    //we create an object from interface
    private OnItemSelectedListener listener;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        //inflating view
        /**
         * @layout xml definition
         * @container the viewGroup in which to inflate
         * @attachedTo should it be attached to root paraemeter
         */
        View view = inflater.inflate(R.layout.fragment_rsslist_overview,container, false);
        Log.i("Info "," MyListFfragment: onCreateView");
        Button button = (Button) view.findViewById(R.id.button1);
        button.setOnClickListener(new View.OnClickListener(){
            //set's on click listener
            @Override
            public void onClick(View w){
                Log.i("Info "," MyListFfragment: onCreateView: setOnClickListener: onClick");
                //well it doesn't use the string parameter, so i have no idea why is it here, there is absolutely no point in that, no point at ALL.
                updateDetail("fake");
            }
        });
        return view;
    }

    public interface OnItemSelectedListener{
        public void onRssItemSelected(String link);
    }

    @Override //called when activity is attached to the context
    public void onAttach(Context context){
        super.onAttach(context);
        //instanceof - context is implementing or extending onItemSelectedListener
        /**
         * Example:
         * Our main class RssfeedActivity extends AppCompatActivity and IMPLEMENTS MyListFragment.OnItemSelected
         * Then we launch our fragment which is attached to our RssfeedActivity
         */
        Log.i("Info "," MyListFfragment: onAttach");
        if(context instanceof OnItemSelectedListener){
            Log.i("Info "," MyListFfragment: onAttach: if context onItem");
            listener = (OnItemSelectedListener) context;
        } else {
            throw new ClassCastException(context.toString() + " must implement MyListFragment.OnItemSelectedListener");
        }
    }

    @Override
    public void onDetach(){
        super.onDetach();
        Log.i("Info "," MyListFfragment: onDetach");
        //well we make our listener null, will we get memory leak if not?
        listener = null;
    }

    //may also be triggered from the activity
    public void updateDetail(String uri){
        //create a string just for testing
        Log.i("Info "," MyListFfragment: updateDetail");
        String newTime = String.valueOf(System.currentTimeMillis());

        //inform the Activity about the change based
        //interface definition
        listener.onRssItemSelected(newTime);
    }
}
