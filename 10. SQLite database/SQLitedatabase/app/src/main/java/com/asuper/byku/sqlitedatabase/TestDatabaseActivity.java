package com.asuper.byku.sqlitedatabase;

import java.util.List;
import java.util.Random;

import android.app.ListActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;

import com.asuper.byku.R;

/**
 * Created by Byku on 19.06.2016.
 */

// our main class: uses Comment, CommentsDataSource
public class TestDatabaseActivity extends ListActivity {
    private CommentsDataSource dataSource;

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        dataSource = new CommentsDataSource(this);
        dataSource.open();

        List<Comment> values = dataSource.getAllComments();

        //use the SimpleCursorAdapter to show the elements in a ListView
        ArrayAdapter<Comment> adapter = new ArrayAdapter<Comment>(this, android.R.layout.simple_list_item_1,values);
        setListAdapter(adapter);
    }

    //Will be called via the onClick attribute
    //of the buttons in main.xml
    public void onClick(View view){
        @SuppressWarnings("unchecked")
        ArrayAdapter<Comment> adapter = (ArrayAdapter<Comment>) getListAdapter();
        Comment comment = null;
        switch(view.getId()){
            case R.id.add:
                String[] comments = new String[]{"Cool","Very Nice", "Hate it"};
                int nextInt = new Random().nextInt(3);
                //save the new comment to the database
                comment = dataSource.createComment(comments[nextInt]);
                adapter.add(comment);
                break;
            case R.id.delete:
                if(getListAdapter().getCount()>0){
                    comment = (Comment) getListAdapter().getItem(0);
                    dataSource.deleteComment(comment);
                    adapter.remove(comment);
                }
                break;
        }
        adapter.notifyDataSetChanged();
    }

    @Override
    protected void onResume(){
        dataSource.open();
        super.onResume();
    }

    @Override
    protected void onPause(){
        dataSource.close();
        super.onPause();
    }

}
