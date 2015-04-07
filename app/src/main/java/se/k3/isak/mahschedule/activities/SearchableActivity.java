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

import se.k3.isak.mahschedule.R;
import se.k3.isak.mahschedule.helpers.Constants;
import se.k3.isak.mahschedule.presenter.CustomBaseAdapter;
import se.k3.isak.mahschedule.volley.VolleyInstance;
import se.k3.isak.mahschedule.volley.VolleyInterface;

/**
 * Created by isak on 2015-04-05.
 */
public class SearchableActivity extends Activity implements ListView.OnItemClickListener {

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
    }

    void setupVolley() {
        volleyInstance = new VolleyInstance(Constants.KRONOX_URL, Volley.newRequestQueue(this), new VolleyInterface() {
            @Override
            public void onVolleyJsonArrayRequestResponse(ArrayList<String> results) {
                for(String s : results) {
                    SearchableActivity.this.results.add(s);
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onVolleyJsonArrayRequestError(String errorMsg) {
                Toast.makeText(SearchableActivity.this,
                        getResources().getString(R.string.volley_error),
                        Toast.LENGTH_LONG).show();
                Log.i(MainActivity.TAG, errorMsg);
            }
        });
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        handleIntent(intent);
    }

    void handleIntent(Intent intent) {
        if(Intent.ACTION_SEARCH.equals(intent.getAction())) {
            String query = intent.getStringExtra(SearchManager.QUERY);
            volleyInstance.newJsonArrayRequest(query);
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Log.i(MainActivity.TAG, "searchableACtivity onItemClick, pos: " + String.valueOf(position));
    }
}
