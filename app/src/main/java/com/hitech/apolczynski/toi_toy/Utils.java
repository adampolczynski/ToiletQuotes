package com.hitech.apolczynski.toi_toy;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

/**
 * Created by Admin on 2017-02-13.
 */

public class Utils {

    interface VolleyCallback {
        void onSuccess(JSONArray response);
        void onError();
    }
    private static String TAG = "UtilsTag";

    public static void getQuote(final Context context, final VolleyCallback callback, int category) {

        String url = "http://zupelnieniepotrzebnie.comxa.com/toitoi/toitoi.php?category="+category+"&lang="+lang;
        String lang = Locale.getDefault().getLanguage();
        if (lang.equals("nl") || lang.equals("de")) {
            lang = "en";
        }


        JsonArrayRequest jsonArrRequest = new JsonArrayRequest(url, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                    callback.onSuccess(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                callback.onError();
                Log.d(TAG, error.toString());
                //Toast.makeText(context, "Connection problem"+error.toString(), Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Content-Type", "application/json");
                headers.put("not-typical-header0", "value0123");
                return headers;
            }

        };
        Singleton.getInstance(context).addToRequestQueue(jsonArrRequest);
    }

}
