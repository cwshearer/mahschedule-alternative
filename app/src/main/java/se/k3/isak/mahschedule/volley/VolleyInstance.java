package se.k3.isak.mahschedule.volley;

import android.util.Log;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;

import se.k3.isak.mahschedule.activities.MainActivity;
import se.k3.isak.mahschedule.helpers.Cleaner;

/**
 * Created by isak on 2015-04-07.
 */
public class VolleyInstance {

    String mBaseUrl;
    RequestQueue mRequestQueue;
    VolleyInterface mVolleyInterface;

    public VolleyInstance(String baseUrl, RequestQueue requestQueue, VolleyInterface volleyInterface) {
        this.mBaseUrl = baseUrl;
        this.mRequestQueue = requestQueue;
        this.mVolleyInterface = volleyInterface;
    }

    public void newJsonArrayRequest(String query) {
        String url = mBaseUrl + query;
        final ArrayList<String> results = new ArrayList<>();

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
                mVolleyInterface.onVolleyJsonArrayRequestResponse(results);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                mVolleyInterface.onVolleyJsonArrayRequestError(error.toString());
            }
        });
        mRequestQueue.add(jsonOArrayRequest);
    }
}
