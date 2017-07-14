package com.jarrebbnnee;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
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
import java.util.Arrays;
import java.util.HashMap;

public class SignUp extends AppCompatActivity {

    Toolbar toolbar;
    ImageView ivBack;
    ImageView image_captcha;
    EditText et_code, et_fname, et_lname, et_email_signup, et_password_signup, et_mobile, et_address;
    TextView tvDont, tvRefresh, tvTandC, tvAccept;
    Spinner sp_country;
    ArrayList<String> country_list;
    ArrayList<HashMap<String,String>> countrylist= new ArrayList<HashMap<String,String>>();
    HashMap<String,String> cmap;
    Button btn_signup;
    String fname, lname, email, password, mobile, address, code, url, country;
    String regId;
    TextCaptcha textCaptcha;
    CheckBox cbox;
    SpinnerAdapter adapterCountry;
    ArrayList<countryPOJO> list;
    public static final String ServerData = "ServerData", LoginManager="LoginManager";
    String countryId;
    SharedPreferences serverPref,loginPref;
    SharedPreferences.Editor loginEditor;
    SharedPreferences.Editor editor;
    SaveSharedPrefrence prefrence;
    CountryListAdapter2 spinnerAdapter;
    URLCollection collection= new URLCollection();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
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

        SharedPreferences pref = getApplicationContext().getSharedPreferences(Config.SHARED_PREF, 0);
        serverPref = getApplicationContext().getSharedPreferences(ServerData, 0);
        loginPref = getSharedPreferences(LoginManager, Context.MODE_PRIVATE);
        loginEditor = loginPref.edit();
        editor = serverPref.edit();

        regId = pref.getString("regId", null);
        Toast.makeText(SignUp.this, regId, Toast.LENGTH_SHORT).show();
        list= new ArrayList<countryPOJO>();
        country_list = new ArrayList<String>();
        country_list.add("India1");
        country_list.add("India2");
        country_list.add("India3");
        country_list.add("India4");
        country_list.add("India5");
        sp_country = (Spinner) findViewById(R.id.spCountry);
        spinnerAdapter = new CountryListAdapter2(SignUp.this, countrylist);
        sp_country.setAdapter(spinnerAdapter);
    /*    adapterCountry = new SpinnerAdapter(
                SignUp.this,
                R.layout.view_spinner_item,
                country_list
        );
        sp_country.setAdapter(adapterCountry);*/


        et_code = (EditText) findViewById(R.id.et_code);
        image_captcha = (ImageView) findViewById(R.id.image_captcha);
        textCaptcha = new TextCaptcha(300, 90, 4, TextCaptcha.TextOptions.LETTERS_ONLY);
        image_captcha.setImageBitmap(textCaptcha.getImage());

        btn_signup = (Button) findViewById(R.id.btn_signup);
        et_fname = (EditText) findViewById(R.id.et_fname);
        et_lname = (EditText) findViewById(R.id.et_lname);
        et_email_signup = (EditText) findViewById(R.id.et_email_signup);
        et_password_signup = (EditText) findViewById(R.id.et_password_signup);
        et_mobile = (EditText) findViewById(R.id.et_mobile);
        et_address = (EditText) findViewById(R.id.et_address);
        tvDont = (TextView) findViewById(R.id.tvDont);
        tvRefresh = (TextView) findViewById(R.id.tvRefresh);
        tvAccept = (TextView) findViewById(R.id.tvAccept);
        tvTandC = (TextView) findViewById(R.id.tvTandC);

        cbox = (CheckBox) findViewById(R.id.cbox);

        Typeface custom_font = Typeface.createFromAsset(getApplicationContext().getAssets(), "tt0140m.ttf");

        et_code.setTypeface(custom_font);
        et_fname.setTypeface(custom_font);
        et_lname.setTypeface(custom_font);
        et_email_signup.setTypeface(custom_font);
        et_password_signup.setTypeface(custom_font);
        et_address.setTypeface(custom_font);
        et_mobile.setTypeface(custom_font);
        tvDont.setTypeface(custom_font);
        tvRefresh.setTypeface(custom_font);
        tvTandC.setTypeface(custom_font);
        tvAccept.setTypeface(custom_font);


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
                        Toast.makeText(SignUp.this, "Name "+cmap1.get("country")+"\nID "+cmap1.get("id"), Toast.LENGTH_LONG).show();
                        countryId = cmap1.get("id");
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });
            }
        });


        
        btn_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fname = et_fname.getText().toString().trim();
                lname = et_lname.getText().toString().trim();
                email = et_email_signup.getText().toString().trim();
                password = et_password_signup.getText().toString().trim();
                mobile = et_mobile.getText().toString().trim();
                address = et_address.getText().toString().trim();
                code = et_code.getText().toString().trim();


                if (!cbox.isChecked()) {
                    Toast.makeText(SignUp.this, "Please accept our Terms and Conditions ", Toast.LENGTH_SHORT).show();
                    return;
                } else {

                  /*  validateFname(fname);
                    validateLname(lname);
                    validatePassword(password);
                    validateEmail(email);
                    validateMobile(mobile);
                    validateAddress(address);

                    if (validateFname(fname) == false || validateLname(lname) == false || validatePassword(password) == false || validateEmail(email) == false || validateMobile(mobile) == false ||validateAddress(address) == false ||textCaptcha.checkAnswer(code) == false) {
                        Toast.makeText(SignUp.this, "Could not sign in", Toast.LENGTH_SHORT).show();
                        if (!textCaptcha.checkAnswer(et_code.getText().toString().trim())) {
                            et_code.setError("Captcha does not match");
                            //  numberOfCaptchaFalse++;
                        }
                    } else {*/

                        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
                        if (connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||
                                connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED) {
                            //we are connected to a network
                           SignUpUrlCall();
                        } else {
                            Toast.makeText(getApplicationContext(), "Please check your internet connection!", Toast.LENGTH_LONG).show();
                        }

                        /*
                        Intent intent = new Intent(SignUp.this, HomeActivity.class);
                        startActivity(intent);
                        finish();*/
                    /*}*/


                }

            }
        });

        new Country().execute();
    }

    class Country extends AsyncTask<Void, Void, Void> {
        ProgressDialog progressDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = new ProgressDialog(SignUp.this);
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
                                Log.e("added ", country);
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
                          //  prefrence.saveDeviceToken(SignUp.this, regId);


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



    private void SignUpUrlCall() {
        url=collection.RegisterUrl+"&u_email="+email+"&u_password="+password+"&u_first_name="+fname+"&u_last_name="+lname+"&u_address="+address+"&u_country="+country+"&u_phone"+mobile+"&device_type=0&device_token="+regId;
        Log.e("URL ",url);
        StringRequest sr= new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject object = new JSONObject(response);
                    Log.e("response ",response);
                    String status = object.getString("status");
                    if (status.equals("0")) {
                        Toast.makeText(SignUp.this, "Registration Success", Toast.LENGTH_SHORT).show();
                        String u_id = object.getString("u_id");

                        Log.e("status",status);
                        Log.e("u_id",u_id);
                        Log.e("u_first_name",fname);
                        Log.e("u_last_name",lname);
                        Log.e("u_email",email);
                        Log.e("u_country",countryId);
                        Log.e("u_phone",mobile);
                        editor.putString("u_id",u_id);
                        editor.putString("u_first_name",fname);
                        editor.putString("u_last_name",lname);
                        editor.putString("u_email",email);
                        editor.putString("u_country",countryId);
                        editor.putString("u_phone",mobile);
                        editor.putString("isLoggedIn", "true");
                        editor.commit();

                        prefrence.saveUserID(SignUp.this,u_id);
                        prefrence.saveUserFName(SignUp.this, fname);
                        prefrence.saveUserLName(SignUp.this,lname);
                        prefrence.saveUserEmail(SignUp.this,email);
                        prefrence.saveUserCountry(SignUp.this, countryId);
                        prefrence.saveUserPhone(SignUp.this, mobile);
                        prefrence.saveisLoggedIn(SignUp.this,"true");
                      //  prefrence.saveDeviceToken(SignUp.this,u_id);
                        prefrence.saveUserFName(SignUp.this, fname);
                        prefrence.saveUserLName(SignUp.this,lname);
                        prefrence.saveUserEmail(SignUp.this,email);
                        prefrence.saveUserCountry(SignUp.this, countryId);
                        prefrence.saveUserPhone(SignUp.this, mobile);
                        prefrence.saveisLoggedIn(SignUp.this,"true");

                        loginEditor.putString("isLoggedIn", "true");
                        loginEditor.commit();

                        Intent intent = new Intent(SignUp.this, HomeActivity.class);
                        startActivity(intent);
                    } else {
                       String msg=object.getString("message");
                        Log.e("status ", status);
                        Log.e("error ", msg);
                        Toast.makeText(SignUp.this, msg, Toast.LENGTH_LONG).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("JSON", "exception");
            }
        });
        AppController.getInstance().addToRequestQueue(sr, "Signup");
    }
}
