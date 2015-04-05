package se.k3.isak.mahschedule.Helpers;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.util.Log;

import se.k3.isak.mahschedule.R;

/**
 * Created by isak on 2015-04-02.
 */
public class FragmentHelper {

    Activity activity;
    FragmentManager fragmentManager;

    public FragmentHelper(Activity activity) {
        this.activity = activity;
        this.fragmentManager = activity.getFragmentManager();
    }

    public void addFragment(Fragment fragment, String tag, boolean isAddToBackStack) {
        FragmentTransaction fragmentTransaction = activity.getFragmentManager().beginTransaction();
        fragmentTransaction.add(R.id.content_frame, fragment, tag);
        if(isAddToBackStack) {
            fragmentTransaction.addToBackStack(null);
        }
        fragmentTransaction.commit();
    }

    public void replaceFragment(Fragment fragment, String tag, boolean isAddToBackStack) {
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.content_frame, fragment, tag);
        if(isAddToBackStack) {
            fragmentTransaction.addToBackStack(null);
        }
        fragmentTransaction.commit();
    }

    public boolean isFragmentVisible(String tag) {
        Fragment test = fragmentManager.findFragmentByTag(tag);
        if(test != null && test.isVisible()) {
            Log.i("isak", "fragment exists");
            return true;
        }
        return false;
    }
}
