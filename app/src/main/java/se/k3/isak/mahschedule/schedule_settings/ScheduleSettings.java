package se.k3.isak.mahschedule.schedule_settings;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import butterknife.ButterKnife;
import butterknife.InjectView;
import se.k3.isak.mahschedule.R;
import se.k3.isak.mahschedule.activities.MainActivity;

/**
 * Created by isak on 2015-04-04.
 */
public class ScheduleSettings extends Fragment implements MainActivity.OnSearchActivityResultListener {

    @InjectView(R.id.response) TextView mResponseTextView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_schedule_settings, container, false);
        ButterKnife.inject(this, v);
        return v;
    }

    @Override
    public void onResult(String result) {
        mResponseTextView.setText(result);
    }
}
