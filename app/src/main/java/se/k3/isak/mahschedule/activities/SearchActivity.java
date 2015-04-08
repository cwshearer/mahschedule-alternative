package se.k3.isak.mahschedule.activities;

import android.app.Activity;
import android.app.SearchManager;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.toolbox.Volley;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Collections;

import butterknife.ButterKnife;
import butterknife.InjectView;
import se.k3.isak.mahschedule.R;
import se.k3.isak.mahschedule.helpers.Constants;
import se.k3.isak.mahschedule.presenter.CustomBaseAdapter;
import se.k3.isak.mahschedule.volley.VolleyInstance;
import se.k3.isak.mahschedule.volley.VolleyInterface;

/**
 * Created by isak on 2015-04-05.
 */
public class SearchActivity extends Activity {

    VolleyInstance volleyInstance;
    @InjectView(R.id.response_list) ListView responseList;
    ArrayList<String> results = new ArrayList<>();
    CustomBaseAdapter adapter;
    WeakReference<Activity> mActivityWeakReference;
    String courseTitle = "Courses";
    String programTitle = "Programs";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivityWeakReference = new WeakReference<Activity>(this);
        setContentView(R.layout.activity_search);
        ButterKnife.inject(this, mActivityWeakReference.get());

        adapter = new CustomBaseAdapter(mActivityWeakReference.get(), results);
        responseList.setAdapter(adapter);

        responseList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String item = results.get(position);
                Log.i(MainActivity.TAG, item);
                if(!item.equals(courseTitle) && !item.equals(programTitle)) {
                    Intent result = new Intent();
                    result.putExtra("RESULT", item);
                    setResult(RESULT_OK, result);
                    finish();
                }
            }
        });

        volleyListener();
        handleIntent(getIntent());
    }

    void volleyListener() {
        volleyInstance = new VolleyInstance(Volley.newRequestQueue(mActivityWeakReference.get()), new VolleyInterface() {
            @Override
            public void onVolleyJsonArrayRequestResponse(ArrayList<String> _results, String type) {
                if(type.equals(Constants.KRONOX_URL_COURSE)) {
                    results.add(courseTitle);
                } else if (type.equals(Constants.KRONOX_URL_PROGRAM)) {
                    results.add(programTitle);
                }
                for(String s : _results) {
                    results.add(s);
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onVolleyJsonArrayRequestError(String errorMsg) {
                Toast.makeText(mActivityWeakReference.get(),
                        getResources().getString(R.string.volley_error),
                        Toast.LENGTH_LONG).show();
                Log.i(MainActivity.TAG, errorMsg);
            }
        });
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        //Log.i(MainActivity.TAG, "SearchableActivity onNewIntent");
        setIntent(intent);
        handleIntent(intent);
    }

    void handleIntent(Intent intent) {
        String query = intent.getStringExtra(SearchManager.QUERY);
        volleyInstance.newJsonArrayRequest(Constants.KRONOX_URL_PROGRAM, query);
        volleyInstance.newJsonArrayRequest(Constants.KRONOX_URL_COURSE, query);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }
}
