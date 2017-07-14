package com.jarrebbnnee;

import android.app.ProgressDialog;
import android.content.Context;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by vi6 on 28-Mar-17.
 */

public class ServerResponse {



    public static String lang = "english";
    public static String res="";
   public static JSONObject object1=null;


    public static void setResponse(String res1) {
        res = res1;
        try {

            object1= new JSONObject(res);
            Log.e("object1", ""+object1);
        } catch (JSONException e) {

            e.printStackTrace();
            Log.e("Error", e.toString());
        }
    }

    public static JSONObject getResponse() {
        Log.e("getResponse", res);
       /* try {
            Log.e("getResponse", res);
            object1= new JSONObject(res);
            Log.e("ServerResponse", "added object");
        } catch (JSONException e) {

            e.printStackTrace();
            Log.e("ServerResponse", e.toString());
        }*/

        return object1;
    }


    public static JSONObject CallApi(String url1, final Context context) {
        final JSONObject[] object = new JSONObject[1];

        Log.e("=======","=======");
        String url=url1+"&lang="+lang;
        Log.e("url ", url);
        final ProgressDialog pDialog = new ProgressDialog(context);
        pDialog.setMessage("Loading...");
        pDialog.show();

        StringRequest sr= new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.e("response", response);
                    pDialog.dismiss();
                try {

                    object1= new JSONObject(response);
                    Log.e("object1", ""+object1);

                } catch (JSONException e) {

                    e.printStackTrace();
                    Log.e("Error", e.toString());
                }
                   // setResponse(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        AppController.getInstance().addToRequestQueue(sr, url);
      //  Log.e("object", object.toString());

       // return res;
        return object1;
    }




}
