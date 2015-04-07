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

    RequestQueue mRequestQueue;
    VolleyInterface mVolleyInterface;

    public VolleyInstance(RequestQueue requestQueue, VolleyInterface volleyInterface) {
        this.mRequestQueue = requestQueue;
        this.mVolleyInterface = volleyInterface;
    }

    public void newJsonArrayRequest(String baseUrl, String query) {
        String url = baseUrl + query;
        final ArrayList<String> results = new ArrayList<>();

        JsonArrayRequest jsonOArrayRequest = new JsonArrayRequest(url, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                for(int i = 0; i < response.length(); i++) {
                    try {
                        String resultString = Cleaner.cleanKronoxResponse(response.get(i).toString());
                        if(!resultString.equals("invalid")) {
                            results.add(resultString);
                        }
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
