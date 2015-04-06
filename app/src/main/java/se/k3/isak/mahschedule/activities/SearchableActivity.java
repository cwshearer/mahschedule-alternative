package se.k3.isak.mahschedule.activities;

import android.app.Activity;
import android.app.SearchManager;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import se.k3.isak.mahschedule.R;
import se.k3.isak.mahschedule.helpers.Cleaner;
import se.k3.isak.mahschedule.helpers.Constants;
import se.k3.isak.mahschedule.models.CustomBaseAdapter;

/**
 * Created by isak on 2015-04-05.
 */
public class SearchableActivity extends Activity implements ListView.OnItemClickListener {

    ListView responseList;
    CustomBaseAdapter adapter;
    ArrayList<String> results = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        responseList = (ListView) findViewById(R.id.response_list);

        adapter = new CustomBaseAdapter(this, results);
        responseList.setAdapter(adapter);
        responseList.setOnItemClickListener(this);

        handleIntent(getIntent());
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        handleIntent(intent);
    }

    void handleIntent(Intent intent) {
        Log.i(MainActivity.TAG, "handle search intent");
        if(Intent.ACTION_SEARCH.equals(intent.getAction())) {
            String query = intent.getStringExtra(SearchManager.QUERY);
            getFromKronoxAndShow(query);
        }
    }

    void getFromKronoxAndShow(String query) {
        String url = Constants.KRONOX_URL + query;
        RequestQueue requestQueue = Volley.newRequestQueue(this);

        JsonArrayRequest jsonOArrayRequest = new JsonArrayRequest(url, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                for(int i = 0; i < response.length(); i++) {
                    try {
                        results.add(Cleaner.cleanKronoxResponse(response.get(i).toString()));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                adapter.notifyDataSetChanged();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.i(MainActivity.TAG, error.toString());
            }
        });
        requestQueue.add(jsonOArrayRequest);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Log.i(MainActivity.TAG, "click on position: " + String.valueOf(position));
    }
}
