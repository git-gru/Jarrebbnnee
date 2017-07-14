package com.jarrebbnnee;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by vi6 on 30-Mar-17.
 */

public class ServerRequest {
    Context c;

    public ServerRequest(Context c) {
        this.c = c;
    }

    public void getServerResponce(final ServerSide rEsponce, String url, int request_method) {
        StringRequest request= new StringRequest(request_method, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    if (!response.equals("")) {
                        JSONObject jsonObject = new JSONObject(response);
                        rEsponce.onSuccess(jsonObject);
                    } else {
                        Toast.makeText(c, response, Toast.LENGTH_LONG).show();
                    }
                } catch (Exception e) {
                    Log.e("JSON", "Exception "+e);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        AppController.getInstance().addToRequestQueue(request);
    }



    public void getCategories(final ArrayList<HashMap<String,String> > arr, String email, String pass, final ReturnArraylist value){




        getServerResponce(new ServerSide() {
            @Override
            public void onSuccess(JSONObject responce) {
                try {

                    String status = responce.getString("status");

                } catch (Exception e) {

                }

            }
        },"fdsf", Request.Method.GET);

    }

}
