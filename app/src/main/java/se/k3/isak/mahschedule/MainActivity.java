package se.k3.isak.mahschedule;

import android.content.res.Configuration;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import se.k3.isak.mahschedule.Fragments.MainFragment;
import se.k3.isak.mahschedule.Helpers.FragmentHelper;
import se.k3.isak.mahschedule.NavigationDrawer.NavDrawer;

public class MainActivity extends ActionBarActivity {

    public static final String TAG = "isak";

    FragmentHelper fragmentHelper;
    NavDrawer navDrawer;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fragmentHelper = new FragmentHelper(this);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        navDrawer = new NavDrawer(this, fragmentHelper, toolbar);

        if(savedInstanceState == null) {
            fragmentHelper.addFragment(new MainFragment(), "main", false);
        }
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        menu.findItem(R.id.action_settings).setVisible(!navDrawer.isDrawerOpened);
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        if(navDrawer.isDrawerOpened) {
            navDrawer.closeDrawer();
        } else {
            if (getFragmentManager().getBackStackEntryCount() > 0 ){
                getFragmentManager().popBackStack();
            } else {
                super.onBackPressed();
            }
        }
    }

    @Override
    public void onPostCreate(Bundle savedInstanceState, PersistableBundle persistentState) {
        super.onPostCreate(savedInstanceState, persistentState);
        navDrawer.getDrawerToggle().syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        navDrawer.getDrawerToggle().onConfigurationChanged(newConfig);
    }
}
