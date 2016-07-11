package com.asuper.byku.myapplication;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private ArrayList<Movie> movieList = new ArrayList<>();
    private RecyclerView recyclerView; //replaces ListView - it is better
    private MoviesAdapter mAdapter;
    private static final String TAG = "Log";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
            Log.i(TAG,"MainActivity: onCreate");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //make our toolbar the main toolbar(=actionBar)
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        //binding android.support.v7.widget.RecyclerView to our RecyclerView(indirect subclass of View)
        //findViewById returns View object
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);

            Log.i(TAG,"MainActivity: onCreate: initializing mAdapter");
        mAdapter = new MoviesAdapter(movieList);
        recyclerView.setHasFixedSize(true);
        //LayoutManager - let's us position items exactly like ListView, implementation of RecyclerView.LayoutManager
        //getApplicationContext - returns class Context, global class containing app env. information
        //LinearLayoutManager(Context context) - creates a vertical LinearLayoutManager
        //LayoutManager - responsible for positioning and measuring item views in a RecyclerView
            Log.i(TAG,"MainActivity: onCreate: initializing mLayoutManager");
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        //LayoutManager to use
        recyclerView.setLayoutManager(mLayoutManager);
        //ItemAnimator - defines animations that take place on items as changes are made to the adapter, subclass
        //of itemAnimator can be used to implement custom animations for actions on VieHolder
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new DividerItemDecoration(this,LinearLayoutManager.VERTICAL));
        recyclerView.setAdapter(mAdapter); //runs adapter finally
            Log.i(TAG,"MainActivity: onCreate: initializing prepareMovieData");
        prepareMovieData(); //implementing data
        //Intercepting touch events, takes RecyclerView.OnItemTouchListener
        //(apps environment, recyclerView on which it will work and clickListener(we've defined interface for it below,
        //we override onClick and onLongClick
            Log.i(TAG,"MainActivity: onCreate: adding TouchListener");
        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(getApplicationContext(), recyclerView,new ClickListener(){
            @Override
            //it takes a position in view
            public void onClick(View view, int position){
                    Log.i(TAG,"MainActivity: OnCreate: new ClickListener(): onClick");
                Movie movie = movieList.get(position);
                Toast.makeText(getApplicationContext(),movie.getTitle() + " is selected!", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onLongClick(View view, int position){
                    Log.i(TAG,"MainActivity: OnCreate: new ClickListener(): onLongClick");
            }
        }));//*/
    }

    private void prepareMovieData() {
            Log.i(TAG,"MainActivity: prepareMovieData");
        Movie movie = new Movie("Mad Max: Fury Road", "Action & Adventure", "2015");
        movieList.add(movie);

        movie = new Movie("Inside Out", "Animation, Kids & Family", "2015");
        movieList.add(movie);

        movie = new Movie("Star Wars: Episode VII - The Force Awakens", "Action", "2015");
        movieList.add(movie);

        movie = new Movie("Shaun the Sheep", "Animation", "2015");
        movieList.add(movie);

        movie = new Movie("The Martian", "Science Fiction & Fantasy", "2015");
        movieList.add(movie);

        movie = new Movie("Mission: Impossible Rogue Nation", "Action", "2015");
        movieList.add(movie);

        movie = new Movie("Up", "Animation", "2009");
        movieList.add(movie);

        movie = new Movie("Star Trek", "Science Fiction", "2009");
        movieList.add(movie);

        movie = new Movie("The LEGO Movie", "Animation", "2014");
        movieList.add(movie);

        movie = new Movie("Iron Man", "Action & Adventure", "2008");
        movieList.add(movie);

        movie = new Movie("Aliens", "Science Fiction", "1986");
        movieList.add(movie);

        movie = new Movie("Chicken Run", "Animation", "2000");
        movieList.add(movie);

        movie = new Movie("Back to the Future", "Science Fiction", "1985");
        movieList.add(movie);

        movie = new Movie("Raiders of the Lost Ark", "Action & Adventure", "1981");
        movieList.add(movie);

        movie = new Movie("Goldfinger", "Action & Adventure", "1965");
        movieList.add(movie);

        movie = new Movie("Guardians of the Galaxy", "Science Fiction & Fantasy", "2014");
        movieList.add(movie);
            Log.i(TAG,"MainActivity: prepareMovieData: notifyDataSetChanged");
        mAdapter.notifyDataSetChanged();
    }
    //Interface used in RecyclerTouchListener
    public interface ClickListener{
        void onClick(View view, int position);
        void onLongClick(View view, int position);
    }

    public void onClick(View view){
        switch(view.getId()){
            case R.id.start:
                    Log.i(TAG,"MainActivity: onClick: start");
                prepareMovieData();
                break;
        }
    }

    public static class RecyclerTouchListener implements RecyclerView.OnItemTouchListener{
        private GestureDetector gestureDetector;
        private MainActivity.ClickListener clickListener; //creating an object from interface

        //constructor
        public RecyclerTouchListener(Context context, final RecyclerView recyclerView, final MainActivity.ClickListener clickListener) {
            this.clickListener = clickListener; //creating a copy
            //------------------------------------
            final String hue = context.toString();
            //------------------------------------

                Log.i(TAG,"MainActivity: RecyclerTouchListener: Constructor");
            //inicialization of gestureDetector, creates a gesture detector with the supplied listener
            //SimpleOnGestureListener - extend it if i want to listen for a subset of all the gestures
            gestureDetector = new GestureDetector(context, new GestureDetector.SimpleOnGestureListener() {
                @Override
                public boolean onSingleTapUp(MotionEvent e) {
                        Log.i(TAG,"MainActivity: RecyclerTouchListener: Constructor: onSingleTapUp");
                    return true;
                }

                @Override
                public void onLongPress(MotionEvent e) {
                        Log.i(TAG,"MainActivity: RecyclerTouchListener: Constructor: onLongPress");
                    View child = recyclerView.findChildViewUnder(e.getX(), e.getY());
                    if (child != null && clickListener != null) {
                            Log.i(TAG,"MainActivity: RecyclerTouchListener: Constructor: onLongPress: in if child!=null && clickListener !=null ");
                        clickListener.onLongClick(child, recyclerView.getChildAdapterPosition(child));
                    }
                }
            });
        }

        @Override
        public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e){
                Log.i(TAG,"MainActivity: RecyclerTouchListener: OnInterceptTouchEvent");
            View child = rv.findChildViewUnder(e.getX(), e.getY());
            if(child !=null && clickListener != null && gestureDetector.onTouchEvent(e)){
                    Log.i(TAG,"MainActivity: RecyclerTouchListener: OnInterceptTouchEvent: If");
                clickListener.onClick(child,rv.getChildAdapterPosition(child));
            }
            return false;
        }

        @Override
        public void onTouchEvent(RecyclerView rv, MotionEvent e){
                Log.i(TAG,"MainActivity: RecyclerTouchListener: onTouchEvent");
        }
        @Override
        public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept){
                Log.i(TAG,"MainActivity: RecyclerTouchListener: OnRequestDisallowInterceptTouchEvent");
        }
    }
}
