package se.k3.isak.mahschedule.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
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

import se.k3.isak.mahschedule.R;

/**
 * Created by isak on 2015-04-04.
 */
public class ScheduleSettings extends Fragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_settings, container, false);

        final TextView result = (TextView) v.findViewById(R.id.result);

        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        String url = "http://kronox.mah.se/ajax/ajax_autocompleteResurser.jsp?typ=program&term=interaktionsdesign";

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                result.setText(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                result.setText("whoops!");
            }
        });

        requestQueue.add(stringRequest);

        return v;
    }
}
