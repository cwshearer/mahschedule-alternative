package se.k3.isak.mahschedule.navigation_drawer;

import android.animation.ValueAnimator;
import android.app.Activity;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBarDrawerToggle;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import se.k3.isak.mahschedule.fragments.ScheduleSettings;
import se.k3.isak.mahschedule.fragments.SettingsFragment;
import se.k3.isak.mahschedule.helpers.FragmentHelper;
import se.k3.isak.mahschedule.R;

/**
 * Created by isak on 2015-04-02.
 */
public class NavDrawer implements ListView.OnItemClickListener {

    Activity mActivity;
    FragmentHelper mFragmentHelper;
    String[] mDrawerItems;
    DrawerOpenListener mDrawerOpenListener;
    ActionBar mActionBar;

    ValueAnimator mAnim;
    ListView mDrawerList;
    DrawerLayout mDrawerLayout;

    public ActionBarDrawerToggle drawerToggle;

    public NavDrawer(Activity activity, FragmentHelper fragmentHelper, String[] drawerItems, DrawerOpenListener drawerOpenListener, ActionBar actionBar) {
        this.mActivity = activity;
        this.mFragmentHelper =  fragmentHelper;
        this.mDrawerItems = drawerItems;
        this.mDrawerOpenListener = drawerOpenListener;
        this.mActionBar = actionBar;
        setupDrawerToggle();
        setupAnim();
    }

    private void setupDrawerToggle() {
        mDrawerLayout = (DrawerLayout) mActivity.findViewById(R.id.drawer_layout);
        mDrawerList = (ListView) mActivity.findViewById(R.id.drawer_list);
        mDrawerList.setAdapter(new ArrayAdapter<>(mActivity, R.layout.drawer_list_item, mDrawerItems));
        mDrawerList.setOnItemClickListener(this);

        drawerToggle = (new ActionBarDrawerToggle(mActivity, mDrawerLayout, R.string.drawer_opened, R.string.drawer_closed) {
            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                mDrawerOpenListener.isDrawerOpened(true);
                mActivity.invalidateOptionsMenu();
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
                mDrawerOpenListener.isDrawerOpened(false);
                mActivity.invalidateOptionsMenu();
            }
        });

        mDrawerLayout.setDrawerListener(drawerToggle);
        mActionBar.setDisplayHomeAsUpEnabled(true);
        mActionBar.setHomeButtonEnabled(true);
        drawerToggle.syncState();
    }

    private void setupAnim() {
        mAnim = ValueAnimator.ofFloat(1, 0);
        mAnim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                float slideOffset = (Float) valueAnimator.getAnimatedValue();
                drawerToggle.onDrawerSlide(mDrawerLayout, slideOffset);
            }
        });
        mAnim.setInterpolator(new DecelerateInterpolator());
        mAnim.setDuration(300);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        selectItem(position);
    }

    private void selectItem(int position) {
        String tag = mDrawerItems[position];
        switch (position) {
            case 0:
                mFragmentHelper.replaceFragment(new ScheduleSettings(), tag, true);
                break;
            case 1:
                mFragmentHelper.replaceFragment(new SettingsFragment(), tag, true);
                break;
        }
        closeDrawer();
    }

    public void closeDrawer() {
        mDrawerLayout.closeDrawer(mDrawerList);
    }

    public void animStart() {
        mAnim.start();
    }
}
