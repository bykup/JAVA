package com.vogella.android.listorgrids;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Arrays;

public class MainActivity extends Activity {
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    String[] myDataset = new String[]{"dude","damn","awesome"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mRecyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);

        //use this setting to improve performance if you know that changes in
        //content do not change the layout size of the RecyclerView
        mRecyclerView.setHasFixedSize(true);

        //use linear layout manager
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        //specify an adapter(see also next example)
        mAdapter=new MyAdapter(myDataset);
        mRecyclerView.setAdapter(mAdapter);
    }



}

//Using list or grids in Android - using RecyclerView
    /*
    RecyclerView - delivered in v7 support library, improved version of ListView, GridView.
        - has default animations of removing and adding elements
    Using a view holder(static inner class) - stores references to views, don't have to use
        findViewById() in an adapter - so it is faster by app. 15%.
    Adapters - manages data model, adapts it to the individual entries in a widget.
        For recycler view - extends "RecyclerView.Adapter". Every entry in recycler view consists of a
        view hierarchy filled with the data model.
        Adapter - fills layout for each entry in recycler view - returns ViewHolder per visual entry.
        getItemCount() - returns number of items.
        onCreateViewHolder() - inflates the layout and creates instance of ViewHolder class.
        ViewHolder - instance is used to access the views in the inflated layout.
        onCreateViewHolder() - only called when a new view must be created
        onBindViewHolder() - in this method you assign the data to the individual views in the row.

        Adapter is assigned to the RecyclerView via the setAdapter() on the RecyclerView.
     Possible input types for the adapter - any arbitrary java object. RecyclerView adapter extracts
        correct data from the dataobject and assigns this data to the individual items in the list or
        grid represented by the RecyclerView implementation.
     Default layout manager - decides how data is displayed in RecyclerView
        - LinearLayoutManager - shows items in a vertical or horizontal scrolling list
        - GridLayoutManager - shows items in a grid
        - StaggeredGridLayoutManager - shows items in a staggered grid
     */
//More on recycler view
    /*
    Important classes of the RecyclerView API
        - Adapter - provides data, responsible for creating views for the individual entry - required
        - ViewHolder - contains references for all views - required
        - LayoutManager - responsible for the arrangement of view container - required, default implementation
        available
        - ItemDecoration - responsible for drawing decoration around or on top of the view container of
        an entry - default behaviour, can be overridden
        - ItemAnimator - resp. to define the animation if entries are added, removed o reordered. Default
        behaviour, but can be overridden.
     Handling click events in RecyclerView - handled by the views, if view should trigger something in the
        object n which it is used(activity or fragment), you can pass via the constructor of the adapter
        to it. This allows the adapter to store a reference to the object and call its methods for feedback.
     Using different layouts in recycler view - layout created for each row of the list.  Root: ViewGroup
        (layout manager) containing other views(ImageView,TextView). getItemViewType() - within it recycler
        view determines which type should be used for data. onCreateViewHolder() - called automatically
        by framework if needed for this type. In it you inflate the correct layout for the type and return
        a fitting view holder.
     Custom animations
        RecyclerView.ItemAnimator - define animation and the assign it to widget by
        RecyclerView.setItemAnimator()
     Filtering and sorting
        Handled by adapter - programmer has to implement logic to custom adapter implementation
     Data updates in the adapter
        notifyItemInserted(position) - method on the adapter must be called if a new entry has been
        inserted on the defined position
        notifyItemRemoved(position) - method mus be called onec the data in this position has beed deleted
     */