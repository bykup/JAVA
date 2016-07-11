package com.asuper.byku.exercise_add_toolbar_to_rssfeed;

/**
 * Created by Byku on 15.06.2016.
 */
import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;
import android.widget.Toolbar;

public class DetailActivity extends Activity {
    public static final String EXTRA_URL = "url";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // check if dual pane mode is active
        // if yes, finish this activity
        if (getResources().getBoolean(R.bool.dual_pane)) {
            finish();
            return;
        }
        setContentView(R.layout.activity_detail);
        Toolbar tb = (Toolbar) findViewById(R.id.toolbara);
        setActionBar(tb);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            String url = extras.getString(EXTRA_URL);
            DetailFragment detailFragment = (DetailFragment) getFragmentManager()
                    .findFragmentById(R.id.detailFragment);
            detailFragment.setText(url);
        }
    }

    //New
    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        Toolbar tb = (Toolbar) findViewById(R.id.toolbara);
        tb.inflateMenu(R.menu.mainmenu);
        tb.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                return onOptionsItemSelected(item);
            }
        });
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
}

