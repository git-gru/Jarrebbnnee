package com.jarrebbnnee;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by vi6 on 22-Mar-17.
 */

public class FragmentEditProfile extends Fragment{
    EditText et_fname, et_lname, et_email_signup, et_password, et_mobile, et_address;
    Button btn_save;
    SharedPreferences fbPref;
    ArrayList<HashMap<String,String>> countrylist= new ArrayList<HashMap<String,String>>();
    HashMap<String,String> cmap;
    public static final String FacebookData = "FacebookData", LoginManager="LoginManager";
    CountryListAdapter2 spinnerAdapter;
    Spinner sp_country;
    URLCollection collection= new URLCollection();
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.layout_edit_profile, container, false);


        et_fname = (EditText)view. findViewById(R.id.et_fname_account);
        et_lname = (EditText)view. findViewById(R.id.et_lname_account);

        et_email_signup = (EditText)view. findViewById(R.id.et_email_signup_account);
        et_mobile = (EditText)view. findViewById(R.id.et_mobile_account);
        et_address = (EditText)view. findViewById(R.id.et_address_account);
        btn_save = (Button) view.findViewById(R.id.btn_save);

        sp_country = (Spinner) view.findViewById(R.id.spCountry_account);
        fbPref = getActivity().getSharedPreferences(FacebookData, Context.MODE_PRIVATE);
        spinnerAdapter= new CountryListAdapter2(getActivity(), countrylist);
        sp_country.setAdapter(spinnerAdapter);
        Typeface custom_font = Typeface.createFromAsset(getActivity().getAssets(), "tt0140m.ttf");
        new  Country().execute();
        et_fname.setTypeface(custom_font);
        et_lname.setTypeface(custom_font);
        et_email_signup.setTypeface(custom_font);
        et_mobile.setTypeface(custom_font);
        et_address.setTypeface(custom_font);
        btn_save.setTypeface(custom_font);
        return view;
    }

    class Country extends AsyncTask<Void, Void, Void> {
        ProgressDialog progressDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = new ProgressDialog(getActivity());
            progressDialog.setMessage("Loading");
            progressDialog.setCancelable(false);
        }

        @Override
        protected Void doInBackground(Void... params) {
            String url_country_list = collection.countryList;
            StringRequest sr=new StringRequest(Request.Method.GET, "http://pr.veba.co/~shubantech/jarrebbnnee/jarrebbnnee_api/index.php?action=countryList", new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    try {
                        JSONObject obj= new JSONObject(response);
                        String status = obj.getString("status");
                        JSONArray data = obj.getJSONArray("data");

                        if (status.equals("0")) {

                            for (int i=0; i<data.length(); i++) {
                                JSONObject object = data.getJSONObject(i);
                                cmap= new HashMap<>();
                                String id= object.getString("id");
                                String iso= object.getString("iso");
                                String iso3= object.getString("iso3");
                                String fips= object.getString("fips");
                                String country= object.getString("country");
                                String continent= object.getString("continent");
                                String currency_code= object.getString("currency_code");
                                String currency_name= object.getString("currency_name");
                                String phone_prefix= object.getString("phone_prefix");
                                String postal_code= object.getString("postal_code");
                                String languages= object.getString("languages");
                                String geonameid= object.getString("geonameid");
                                String is_display= object.getString("is_display");
                                cmap.put("id",id);
                                cmap.put("iso",iso);
                                cmap.put("iso3",iso3);
                                cmap.put("fips",fips);
                                cmap.put("country",country);
                                //     Log.e("added ", country);
                                cmap.put("continent",continent);
                                cmap.put("currency_code",currency_code);
                                cmap.put("currency_name",currency_name);
                                cmap.put("phone_prefix",phone_prefix);
                                cmap.put("postal_code",postal_code);
                                cmap.put("languages",languages);
                                cmap.put("geonameid",geonameid);
                                cmap.put("is_display",is_display);
                                countrylist.add(cmap);
                                spinnerAdapter.notifyDataSetChanged();
                               /* countryPOJO pojo= new countryPOJO(id,iso,iso3,fips,country,continent,currency_code,currency_name,phone_prefix,postal_code,languages,geonameid,is_display);
                                country_list_fb.add(pojo);*/
                            }
                            String u_email = fbPref.getString("u_email",null);
                            String u_first_name = fbPref.getString("u_first_name", null);
                            String u_last_name = fbPref.getString("u_last_name", null);
                            String u_phone = fbPref.getString("u_phone", null);
                            et_fname.setText(u_first_name);
                            et_lname.setText(u_last_name);
                            et_email_signup.setText(u_email);
                            et_mobile.setText(u_phone);



                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.e("spninner", "json exception");
                }
            });

            AppController.getInstance().addToRequestQueue(sr, "url_country_list");
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);




        }
    }
}
