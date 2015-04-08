package se.k3.isak.mahschedule.activities;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import java.lang.ref.WeakReference;

import butterknife.ButterKnife;
import butterknife.InjectView;
import se.k3.isak.mahschedule.R;
import se.k3.isak.mahschedule.fragments.MainFragment;
import se.k3.isak.mahschedule.helpers.FragmentHelper;
import se.k3.isak.mahschedule.navigation_drawer.DrawerOpenListener;
import se.k3.isak.mahschedule.navigation_drawer.NavDrawer;
import se.k3.isak.mahschedule.schedule_settings.ScheduleSettings;

public class MainActivity extends ActionBarActivity {

    public static final String TAG = "ISAK";
    public static final int SEARCH_REQUEST_CODE = 42;

    WeakReference<Activity> mActivityWeakReference;

    String mMainFragmentTag;
    FragmentHelper mFragmentHelper;
    NavDrawer navDrawer;
    String[] mDrawerTitles;
    boolean mIsDrawerOpen;

    @InjectView(R.id.toolbar) Toolbar mToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivityWeakReference = new WeakReference<Activity>(this);
        setContentView(R.layout.activity_main);
        ButterKnife.inject(mActivityWeakReference.get());

        setSupportActionBar(mToolbar);
        mMainFragmentTag = getResources().getString(R.string.main_fragment);
        setTitle(mMainFragmentTag);

        setupFragmentHelper();
        setupNavDrawer();

        if(savedInstanceState == null) {
            mFragmentHelper.addFragment(new MainFragment(), mMainFragmentTag, false);
            handleIntent(getIntent());
        } else {
            setTitle(savedInstanceState.getCharSequence("title"));
        }
    }

    void setupFragmentHelper() {
        mFragmentHelper = new FragmentHelper(mActivityWeakReference.get(), getFragmentManager());
        mFragmentHelper.fragmentManager.addOnBackStackChangedListener(new FragmentManager.OnBackStackChangedListener() {
            @Override
            public void onBackStackChanged() {
                boolean backStackEntryCountNil = (mFragmentHelper.fragmentManager.getBackStackEntryCount() == 0);
                navDrawer.drawerToggle.setDrawerIndicatorEnabled(backStackEntryCountNil);
                navDrawer.drawerToggle.syncState();
                if(backStackEntryCountNil) {
                    updateTitle();
                }
            }
        });
    }

    void setupNavDrawer() {
        mDrawerTitles = getResources().getStringArray(R.array.drawer_items);
        navDrawer = new NavDrawer(
                mActivityWeakReference.get(),
                mFragmentHelper,
                mDrawerTitles,
                new DrawerOpenListener() {
                    @Override
                    public boolean isDrawerOpened(boolean isOpen) {
                        updateTitle();
                        return mIsDrawerOpen = isOpen;
                    }
                },
                getSupportActionBar());
    }

    void updateTitle() {
        for(String s : mDrawerTitles) {
            if(mFragmentHelper.findVisibleFragment(s)) {
                setTitle(s);
            }
        }
        if(mFragmentHelper.findVisibleFragment(mMainFragmentTag)) {
            setTitle(mMainFragmentTag);
        }
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        Log.i(MainActivity.TAG, "MainActivity onNewIntent");
        setIntent(intent);
        handleIntent(intent);
    }

    void handleIntent(Intent intent) {
        Log.i(MainActivity.TAG, "MainActivity handleIntent");
        if(Intent.ACTION_SEARCH.equals(intent.getAction())) {
            String query = intent.getStringExtra(SearchManager.QUERY);
            final Intent searchIntent = new Intent(mActivityWeakReference.get(), SearchActivity.class);
            searchIntent.putExtra(SearchManager.QUERY, query);
            startActivityForResult(searchIntent, SEARCH_REQUEST_CODE);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == SEARCH_REQUEST_CODE) {
            if(resultCode == RESULT_OK) {
                String result = data.getStringExtra("RESULT");
                Log.i(MainActivity.TAG, result);
                try {
                    ((OnSearchActivityResultListener) mFragmentHelper.fragmentManager.findFragmentByTag(getResources().getString(R.string.manage_schedules))).onResult(result);
                } catch (ClassCastException e) {
                    throw new ClassCastException(mActivityWeakReference.get().toString() + " not implementing OnSearchActivityResultListener interface");
                }
            }
        }
    }

    public interface OnSearchActivityResultListener {
        public void onResult(String result);
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
        menu.findItem(R.id.search).setVisible(mFragmentHelper.findVisibleFragment(getResources().getString(R.string.manage_schedules)));
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
        if(mIsDrawerOpen) {
            navDrawer.closeDrawer();
        } else {
            if (getFragmentManager().getBackStackEntryCount() > 0) {
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
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putCharSequence("title", getTitle());
        Log.i(MainActivity.TAG, "title saved: " + getTitle());
    }
}
