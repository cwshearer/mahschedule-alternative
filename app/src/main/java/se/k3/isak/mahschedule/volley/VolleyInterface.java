package se.k3.isak.mahschedule.volley;

import java.util.ArrayList;

/**
 * Created by isak on 2015-04-07.
 */
public interface VolleyInterface {
    public void onVolleyJsonArrayRequestResponse(ArrayList<String> results);
    public void onVolleyJsonArrayRequestError(String errorMsg);
}
