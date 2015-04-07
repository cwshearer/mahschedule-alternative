package se.k3.isak.mahschedule.navigation_drawer;

import android.animation.ValueAnimator;
import android.app.Activity;
import android.support.v4.widget.DrawerLayout;
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

    DrawerLayout mDrawerLayout;
    ListView mDrawerList;
    ValueAnimator mAnim;

    public ActionBarDrawerToggle drawerToggle;
    public boolean isDrawerOpened = false;

    public NavDrawer(Builder builder) {
        this.mActivity = builder.mActivity;
        this.mFragmentHelper =  builder.mFragmentHelper;
        this.mDrawerItems = builder.mDrawerItems;
        setupDrawerToggle();
        setupAnim();
    }

    private void setupDrawerToggle() {
        mDrawerLayout = (DrawerLayout) mActivity.findViewById(R.id.drawer_layout);
        mDrawerList = (ListView) mActivity.findViewById(R.id.drawer_list);
        mDrawerList.setAdapter(new ArrayAdapter<String>(mActivity, R.layout.drawer_list_item, mDrawerItems));
        mDrawerList.setOnItemClickListener(this);

        drawerToggle = (new ActionBarDrawerToggle(mActivity, mDrawerLayout, R.string.drawer_opened, R.string.drawer_closed) {
            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                mActivity.invalidateOptionsMenu();
                isDrawerOpened = true;
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
                mActivity.invalidateOptionsMenu();
                isDrawerOpened = false;
            }
        });

        mDrawerLayout.setDrawerListener(drawerToggle);
        ((ActionBarActivity)mActivity).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ((ActionBarActivity)mActivity).getSupportActionBar().setHomeButtonEnabled(true);
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
        if(!mFragmentHelper.isFragmentVisible(tag)) {
            switch (position) {
                case 0:
                    mFragmentHelper.addFragment(new ScheduleSettings(), tag, true);
                    break;
                case 1:
                    mFragmentHelper.addFragment(new SettingsFragment(), tag, true);
                    break;
            }
        }
        closeDrawer();
    }

    public void closeDrawer() {
        mDrawerLayout.closeDrawer(mDrawerList);
    }

    public void animStart() {
        mAnim.start();
    }

    public static class Builder {

        private final Activity mActivity;
        private final FragmentHelper mFragmentHelper;

        private String[] mDrawerItems;

        public Builder(Activity mActivity, FragmentHelper mFragmentHelper) {
            this.mActivity = mActivity;
            this.mFragmentHelper = mFragmentHelper;
        }

        public Builder drawerItems(String[] mDrawerItems) {
            this.mDrawerItems = mDrawerItems;
            return this;
        }

        public NavDrawer build() {
            return new NavDrawer(this);
        }
    }
}
