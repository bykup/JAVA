package com.asuper.byku.myapplication;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
//this binds the data from app-specific data set to views that are displayed within a recyclerView
//we pass to RecyclerView our class MyViewHolder which is a ViewHolder
public class MoviesAdapter extends RecyclerView.Adapter<MoviesAdapter.MyViewHolder> {

    private ArrayList<Movie> moviesList;
//ViewHolder class - describes an item view and metadata about its view within RecyclerView
    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView title, year, genre; //those are references
        //defining the class that implements Viewholder pattern
        public MyViewHolder(View view) {
            super(view);
                Log.i("Log","MovieAdapter: In MyViewHolder");
            title = (TextView) view.findViewById(R.id.title); //references to movie_list_row.xml
            genre = (TextView) view.findViewById(R.id.genre);
            year = (TextView) view.findViewById(R.id.year);
        }
    }


    public MoviesAdapter(ArrayList<Movie> moviesList) {
            Log.i("Log","MovieAdapter: In MoviesAdapter constructor");
        this.moviesList = moviesList;
    }

    @Override //important methods to override - we return MyViewHolder - inflating the row layout(movie_list_row)
    //called first
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            Log.i("Log","MovieAdapter: In onCreateViewHolder");
        //inflates layout with certain templates
        //Creates XML-Layout inflater FROM CONTEXT(global inf. about an apps environment)
        // from view(in parent) is running in, which is inflated with with
        // movie_list_row xml dom node, whose parent will be "parent". and
        // whether the inflated hierarchy should should be attached to the root parameter.
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.movie_list_row, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override //this also v. important - "holder" - that should be updated to represent the contents of
    //the item at a given position in the data set, "position" - the position of the item within the
    // adapter's data set
    public void onBindViewHolder(MyViewHolder holder, int position) {
            Log.i("Log","MovieAdapter: In onBindViewHolder");
        Movie movie = moviesList.get(position); //returns a movie
        holder.title.setText(movie.getTitle()); //sets a text for a given movie above in the holder
        holder.genre.setText(movie.getGenre());
        holder.year.setText(movie.getYear());
    }

    @Override
    public int getItemCount() {
            Log.i("Log","MovieAdapter: In getItemCount");
        return moviesList.size();
    }
}
