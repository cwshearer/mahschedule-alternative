package se.k3.isak.mahschedule.activities;

import android.app.Activity;
import android.app.SearchManager;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.toolbox.Volley;

import java.util.ArrayList;
import java.util.Collections;

import se.k3.isak.mahschedule.R;
import se.k3.isak.mahschedule.helpers.Constants;
import se.k3.isak.mahschedule.presenter.CustomBaseAdapter;
import se.k3.isak.mahschedule.volley.VolleyInstance;
import se.k3.isak.mahschedule.volley.VolleyInterface;

/**
 * Created by isak on 2015-04-05.
 */
public class SearchActivity extends Activity implements ListView.OnItemClickListener {

    VolleyInstance volleyInstance;
    ListView responseList;
    ArrayList<String> results = new ArrayList<>();
    CustomBaseAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        responseList = (ListView) findViewById(R.id.response_list);

        adapter = new CustomBaseAdapter(this, results);
        responseList.setAdapter(adapter);
        responseList.setOnItemClickListener(this);

        setupVolley();
        handleIntent(getIntent());
        //Log.i(MainActivity.TAG, "SearchableActivity onCreate");
    }

    void setupVolley() {
        volleyInstance = new VolleyInstance(Volley.newRequestQueue(this), new VolleyInterface() {
            @Override
            public void onVolleyJsonArrayRequestResponse(ArrayList<String> results) {
                Log.i(MainActivity.TAG, "onVolleyResponse: " + String.valueOf(results.size()));
                for(String s : results) {
                    SearchActivity.this.results.add(s);
                }
                Collections.sort(results, String.CASE_INSENSITIVE_ORDER);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onVolleyJsonArrayRequestError(String errorMsg) {
                Toast.makeText(SearchActivity.this,
                        getResources().getString(R.string.volley_error),
                        Toast.LENGTH_LONG).show();
                //Log.i(MainActivity.TAG, errorMsg);
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
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent result = new Intent();
        result.putExtra("RESULT", results.get(position));
        setResult(RESULT_OK, result);
        finish();
    }
}
