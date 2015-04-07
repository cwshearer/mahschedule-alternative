package se.k3.isak.mahschedule.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import se.k3.isak.mahschedule.R;
import se.k3.isak.mahschedule.activities.MainActivity;

/**
 * Created by isak on 2015-04-02.
 */
public class MainFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_main, container, false);
        Log.i(MainActivity.TAG, "onCreateView MainFragment");
        return v;
    }
}
