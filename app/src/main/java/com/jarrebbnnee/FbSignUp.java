package com.jarrebbnnee;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class FbSignUp extends AppCompatActivity {
    Toolbar toolbar;
    ImageView ivBack;
    ImageView image_captcha;
    EditText et_code, et_fname, et_lname, et_email_signup, et_password_signup, et_mobile, et_address;
    TextView tvDont, tvRefresh, tvTandC, tvAccept;
    Spinner sp_country;
    ArrayList<countryPOJO> country_list_fb;
    ArrayList<HashMap<String,String>> countrylist= new ArrayList<HashMap<String,String>>();
    HashMap<String,String> cmap;
    Button btn_signup_fb;
    String regId, sm_social_provider,sm_social_provider_id,u_first_name,u_last_name,u_email,u_country,u_phone,u_type, mr_device_type,mr_device_token;
    TextCaptcha textCaptcha;
    CheckBox cbox;
    SharedPreferences fbPref, loginPref;
    public static final String FacebookData = "FacebookData", LoginManager="LoginManager";
    SharedPreferences.Editor loginEditor;
    CountryListAdapter2 spinnerAdapter;
    SharedPreferences.Editor editor;
    SaveSharedPrefrence prefrence;
    URLCollection collection= new URLCollection();
    String countryId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fb_sign_up);
        prefrence= new SaveSharedPrefrence();
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        ivBack = (ImageView) findViewById(R.id.ivBack);
        ivBack.setImageResource(R.drawable.icon_back);
        ivBack.setVisibility(View.GONE);
        ivBack.setImageResource(R.drawable.icon_back);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle((Html.fromHtml("<font color=\"#FFFFFF\">" + "" + "</font>")));
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        sp_country = (Spinner) findViewById(R.id.spCountry_fb);
        country_list_fb= new ArrayList<countryPOJO>();
        //spinnerAdapter= new CountryListAdapter(FbSignUp.this, country_list_fb);
        //loadCountrySpinner();

        SharedPreferences pref = getApplicationContext().getSharedPreferences(Config.SHARED_PREF, 0);

       /* sp_country.setAdapter(spinnerAdapter);
*/

        fbPref = getSharedPreferences(FacebookData, Context.MODE_PRIVATE);
        loginPref = getSharedPreferences(LoginManager, Context.MODE_PRIVATE);
        loginEditor = loginPref.edit();
        editor = fbPref.edit();







        regId = pref.getString("regId", null);
        Toast.makeText(FbSignUp.this, regId, Toast.LENGTH_SHORT).show();

        et_code = (EditText) findViewById(R.id.et_code_fb);
        image_captcha = (ImageView) findViewById(R.id.image_captcha_fb);
        textCaptcha = new TextCaptcha(300, 90, 4, TextCaptcha.TextOptions.LETTERS_ONLY);
        image_captcha.setImageBitmap(textCaptcha.getImage());

        btn_signup_fb = (Button) findViewById(R.id.btn_signup_fb);
        et_fname = (EditText) findViewById(R.id.et_fname_fb);
        et_lname = (EditText) findViewById(R.id.et_lname_fb);
        et_email_signup = (EditText) findViewById(R.id.et_email_signup_fb);
        et_mobile = (EditText) findViewById(R.id.et_mobile_fb);
        et_address = (EditText) findViewById(R.id.et_address_fb);
        tvDont = (TextView) findViewById(R.id.tvDont_fb);
        tvRefresh = (TextView) findViewById(R.id.tvRefresh_fb);
        tvAccept = (TextView) findViewById(R.id.tvAccept_fb);
        tvTandC = (TextView) findViewById(R.id.tvTandC_fb);

        cbox = (CheckBox) findViewById(R.id.cbox_fb);

        Typeface custom_font = Typeface.createFromAsset(getApplicationContext().getAssets(), "tt0140m.ttf");

        et_code.setTypeface(custom_font);
        et_fname.setTypeface(custom_font);
        et_lname.setTypeface(custom_font);
        et_email_signup.setTypeface(custom_font);
        et_address.setTypeface(custom_font);
        et_mobile.setTypeface(custom_font);
        tvDont.setTypeface(custom_font);
        tvRefresh.setTypeface(custom_font);
        tvTandC.setTypeface(custom_font);
        tvAccept.setTypeface(custom_font);


        spinnerAdapter= new CountryListAdapter2(FbSignUp.this, countrylist);
        sp_country.setAdapter(spinnerAdapter);
        new Country().execute();


        tvRefresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                textCaptcha = new TextCaptcha(300, 90, 4, TextCaptcha.TextOptions.LETTERS_ONLY);
                image_captcha.setImageBitmap(textCaptcha.getImage());
            }
        });

        sp_country.setSelection(0);

        sp_country.post(new Runnable() {
            @Override
            public void run() {
                sp_country.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        HashMap<String, String> cmap1 = countrylist.get(position);
                        Toast.makeText(FbSignUp.this, "Name "+cmap1.get("country")+"\nID "+cmap1.get("id"), Toast.LENGTH_LONG).show();
                        countryId = cmap1.get("id");
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });
            }
        });

        btn_signup_fb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String mobile = et_mobile.getText().toString();
                if (et_mobile.getText().toString().length() < 10) {
                    Toast.makeText(FbSignUp.this, "Mobile number less than 10", Toast.LENGTH_SHORT).show();
                    return;
                } else {
                    callApi(mobile);
                }
            }
        });

    }

    private void callApi(final String mobile) {
        final String url= collection.SocialLoginUrl+"&u_email="+u_email+"&u_first_name="+u_first_name+"&u_last_name="+u_last_name+"&sm_social_provider="+sm_social_provider+"&sm_social_provider_id="+sm_social_provider_id+"&u_country="+countryId+"&u_phone="+mobile+"&device_type="+mr_device_type+"&device_token="+mr_device_token;


        StringRequest sr2= new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.e("json url", ""+url);
                Log.e("json response", ""+response);

                try {
                    JSONObject object = new JSONObject(response);
                    String status = object.getString("status");
                    if (status.equals("0")) {
                        Toast.makeText(FbSignUp.this, "Welcome " + u_first_name, Toast.LENGTH_LONG).show();
                     //   editor.putString("isLoggedIn", "true");
                        JSONObject object1 = object.getJSONObject("data");
                        String u_id = object1.getString("u_id");
                        editor.putString("u_id", u_id);
                        prefrence.saveUserID(FbSignUp.this,u_id);
                        editor.putString("u_country", countryId);

                        prefrence.saveUserCountry(FbSignUp.this, countryId);
                        editor.putString("u_phone", mobile);
                        prefrence.saveUserPhone(FbSignUp.this, mobile);
                        editor.commit();
                        loginEditor.putString("isLoggedIn", "true");
                        prefrence.saveisLoggedIn(FbSignUp.this,"true");
                        loginEditor.commit();
                        Intent intent= new Intent(FbSignUp.this, HomeActivity.class);
                        startActivity(intent);
                    } else {
                        String message = object.getString("message");
                        Toast.makeText(FbSignUp.this, message, Toast.LENGTH_LONG).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("JSON", "EXCEPTION");
            }
        });
        AppController.getInstance().addToRequestQueue(sr2, "Sociallogin");

    }


    class Country extends AsyncTask<Void, Void, Void> {
        ProgressDialog progressDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = new ProgressDialog(FbSignUp.this);
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
                            u_email = fbPref.getString("u_email",null);
                            sm_social_provider = fbPref.getString("sm_social_provider", null);
                            sm_social_provider_id = fbPref.getString("sm_social_provider_id", null);
                            u_first_name = fbPref.getString("u_first_name", null);
                            u_last_name = fbPref.getString("u_last_name", null);
                            mr_device_type = fbPref.getString("mr_device_type", null);
                            mr_device_token = fbPref.getString("mr_device_token", null);
                            et_fname.setText(u_first_name);
                            et_lname.setText(u_last_name);
                            et_email_signup.setText(u_email);




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
