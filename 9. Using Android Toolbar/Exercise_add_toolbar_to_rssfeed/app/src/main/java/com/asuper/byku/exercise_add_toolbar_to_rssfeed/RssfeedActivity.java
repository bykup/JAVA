package com.asuper.byku.exercise_add_toolbar_to_rssfeed;

/**
 * Created by Byku on 15.06.2016.
 */
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;
import android.widget.Toolbar;

public class RssfeedActivity extends Activity implements
        MyListFragment.OnItemSelectedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rssfeed);
        //toolbar reference that is in activity_rssfeed
        Toolbar tb = (Toolbar) findViewById(R.id.toolbar);
        //ensure that toolbar acts as action bar
        setActionBar(tb);
    }

    @Override
    public CharSequence onCreateDescription(){
        Toast.makeText(RssfeedActivity.this, "Howdy ho", Toast.LENGTH_SHORT).show();
        return "Howdy ho";
    }

    //Used when creating options menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        Toolbar tb = (Toolbar) findViewById(R.id.toolbar);
        tb.inflateMenu(R.menu.mainmenu);
        tb.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                //calling method below
                return onOptionsItemSelected(item);
            }
        });

        //when false - option items are not created
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch(item.getItemId()){
            case R.id.action_refresh:
                Toast.makeText(this, "Action settings selected",Toast.LENGTH_SHORT).show();
                break;
            default:
                break;
        }
        return true;
    }
    @Override
    public void onRssItemSelected(String link) {
        boolean dual_pane = getResources().getBoolean(R.bool.dual_pane);
        if (dual_pane) {
            DetailFragment fragment = (DetailFragment) getFragmentManager()
                    .findFragmentById(R.id.detailFragment);
            fragment.setText(link);
        } else {
            Intent intent = new Intent(getApplicationContext(),
                    DetailActivity.class);
            intent.putExtra(DetailActivity.EXTRA_URL, link);
            startActivity(intent);

        }
    }

}

