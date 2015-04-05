package se.k3.isak.mahschedule.Fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import se.k3.isak.mahschedule.MainActivity;
import se.k3.isak.mahschedule.NavigationDrawer.NavDrawer;
import se.k3.isak.mahschedule.R;

/**
 * Created by isak on 2015-04-02.
 */
public class MainFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_main, container, false);

        return v;
    }
}
