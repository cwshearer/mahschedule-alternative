package se.k3.isak.mahschedule.activities;

import android.app.FragmentManager;
import android.app.SearchManager;
import android.content.Context;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.NonNull;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import se.k3.isak.mahschedule.R;
import se.k3.isak.mahschedule.fragments.MainFragment;
import se.k3.isak.mahschedule.helpers.FragmentHelper;
import se.k3.isak.mahschedule.navigation_drawer.NavDrawer;

public class MainActivity extends ActionBarActivity implements FragmentManager.OnBackStackChangedListener {

    public static final String TAG = "ISAK";

    String mMainFragmentTag;
    FragmentHelper mFragmentHelper;
    NavDrawer navDrawer;
    Toolbar toolbar;
    String[] mDrawerTitles;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mMainFragmentTag = getResources().getString(R.string.main_fragment);
        setTitle(mMainFragmentTag);

        mFragmentHelper = new FragmentHelper.Builder(this, getFragmentManager()).build();
        mFragmentHelper.fragmentManager.addOnBackStackChangedListener(this);

        mDrawerTitles = getResources().getStringArray(R.array.drawer_items);
        navDrawer = new NavDrawer.Builder(this, mFragmentHelper)
                .drawerItems(mDrawerTitles).build();

        if(savedInstanceState == null) {
            mFragmentHelper.addFragment(new MainFragment(), mMainFragmentTag, false);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(navDrawer.drawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        if(mFragmentHelper.fragmentManager.getBackStackEntryCount() != 0) {
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        menu.findItem(R.id.search).setVisible(mFragmentHelper.isFragmentVisible(getResources().getString(R.string.manage_schedules)));
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);

        MenuItem searchItem = menu.findItem(R.id.search);
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchItem);
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        return true;
    }

    @Override
    public void onBackPressed() {
        if(navDrawer.drawerLayout.isDrawerOpen(navDrawer.drawerList)) {
            navDrawer.closeDrawer();
        } else {
            if (getFragmentManager().getBackStackEntryCount() > 0 ){
                getFragmentManager().popBackStack();
                navDrawer.animStart();
            } else {
                super.onBackPressed();
            }
        }
        invalidateOptionsMenu();
    }

    @Override
    public void onPostCreate(Bundle savedInstanceState, PersistableBundle persistentState) {
        super.onPostCreate(savedInstanceState, persistentState);
        navDrawer.drawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        navDrawer.drawerToggle.onConfigurationChanged(newConfig);
    }

    @Override
    public void onBackStackChanged() {
        navDrawer.drawerToggle.setDrawerIndicatorEnabled(mFragmentHelper.fragmentManager.getBackStackEntryCount() == 0);
        navDrawer.drawerToggle.syncState();
        Log.i(MainActivity.TAG, "onBackStackChanged");
        updateTitle();
    }

    void updateTitle() {
        for(String s : mDrawerTitles) {
            if(mFragmentHelper.isFragmentVisible(s)) {
                setTitle(s);
            }
        }
        if(mFragmentHelper.isFragmentVisible(mMainFragmentTag)) {
            setTitle(mMainFragmentTag);
        }
    }
}
