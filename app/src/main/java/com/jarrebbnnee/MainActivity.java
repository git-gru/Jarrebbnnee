package com.jarrebbnnee;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Arrays;

public class MainActivity extends AppCompatActivity {

    Toolbar toolbar;
    ImageView ivBack;
    EditText et_email,et_password;
    TextView tvForgot,tvDont,tvSignUp,tvOrUse;
    String email, password, url, regId, sm_social_provider,sm_social_provider_id,u_first_name,u_last_name,u_email,action,u_type,u_country,u_phone,mr_device_type,mr_device_token;
    Button btn_signin;
    LoginButton loginButton;
    CallbackManager callbackManager;
    SharedPreferences fbPref,loginPref;
    SharedPreferences.Editor loginEditor;
    public static final String FacebookData = "FacebookData", LoginManager="LoginManager" ;
    SharedPreferences.Editor editor;
    SaveSharedPrefrence prefrence;
    public static String sm_social_provider_key="sm_social_provider_key", sm_social_provider_id_key="sm_social_provider_id_key", u_first_name_key="u_first_name_key", u_lastString;
    URLCollection collection= new URLCollection();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        callbackManager = CallbackManager.Factory.create();
        setContentView(R.layout.sign_in);
        prefrence = new SaveSharedPrefrence();


        fbPref = getSharedPreferences(FacebookData, Context.MODE_PRIVATE);
        editor = fbPref.edit();
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        ivBack = (ImageView)findViewById(R.id.ivBack);
        ivBack.setVisibility(View.GONE);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle((Html.fromHtml("<font color=\"#FFFFFF\">" + "" + "</font>")));
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        SharedPreferences pref = getApplicationContext().getSharedPreferences(Config.SHARED_PREF, 0);
        regId = pref.getString("regId", null);
        prefrence.saveDeviceToken(MainActivity.this, regId);
        et_email=(EditText) findViewById(R.id.et_email);
        et_password = (EditText) findViewById(R.id.et_password);
        tvDont = (TextView) findViewById(R.id.tvDont);
        tvSignUp = (TextView) findViewById(R.id.tvSignup);
        tvForgot = (TextView) findViewById(R.id.tvForgot);
        tvOrUse = (TextView) findViewById(R.id.tvoruse);
        btn_signin = (Button) findViewById(R.id.btn_signin);


        loginButton = (LoginButton) findViewById(R.id.login_button);
        loginButton.setReadPermissions(Arrays.asList("public_profile", "email"));

        Typeface custom_font = Typeface.createFromAsset(getApplicationContext().getAssets(),  "tt0140m.ttf");
        et_email.setTypeface(custom_font);
        et_password.setTypeface(custom_font);
        tvSignUp.setTypeface(custom_font);
        tvDont.setTypeface(custom_font);
        tvForgot.setTypeface(custom_font);
        tvOrUse.setTypeface(custom_font);
        loginPref = getSharedPreferences(LoginManager, Context.MODE_PRIVATE);
        loginEditor = loginPref.edit();

        prefrence.saveDeviceToken(MainActivity.this, regId);
        btn_signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                email = et_email.getText().toString();
                password = et_password.getText().toString();

                ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
                if (connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||
                        connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED) {
                    //we are connected to a network
                    url=collection.LoginUrl+"&u_email="+email+"&u_password="+password+"&device_type=0&device_token="+regId;
                    SignInUrlCall(url);
                   // prefrence.saveDeviceToken(MainActivity.this, regId);
                } else {
                    Toast.makeText(getApplicationContext(), "Please check your internet connection!", Toast.LENGTH_LONG).show();
                }

                /*Intent intent = new Intent(MainActivity.this, HomeActivity.class);
                startActivity(intent);*/
            }
        });



        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
/*
                GraphRequest request = GraphRequest.newMeRequest(loginResult.getAccessToken(), new GraphRequest.GraphJSONObjectCallback() {
                    @Override
                    public void onCompleted(JSONObject object, GraphResponse response) {
                        Log.e("response ", response.toString());
                        try {
                            email = object.getString("email");
                        } catch (JSONException e) {
                            Log.e("facebook", "JSON ERROR");
                            e.printStackTrace();
                        }
                    }
                });

                Log.e("facebook", fb_email);*/
                RequestData();
                Toast.makeText(MainActivity.this, "Logged in ", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(MainActivity.this, FbSignUp.class);
                startActivity(intent);
            }

            @Override
            public void onCancel() {

            }

            @Override
            public void onError(FacebookException error) {
                Toast.makeText(MainActivity.this, "Facebook error ", Toast.LENGTH_LONG).show();
            }
        });


        tvSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(MainActivity.this,SignUp.class);
                startActivity(intent);
            }
        });
    }


    private void SignInUrlCall(String url) {
        Log.e("URL ",url);
        StringRequest sr= new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject object = new JSONObject(response);
                    Log.e("response ",response);
                    String status = object.getString("status");
                    if (status.equals("0")) {
                        Toast.makeText(MainActivity.this, "Login Success", Toast.LENGTH_SHORT).show();
                        JSONObject data = object.getJSONObject("data");
                        String u_id = data.getString("u_id");
                        String u_first_name = data.getString("u_first_name");
                        String u_last_name = data.getString("u_last_name");
                        String u_email = data.getString("u_email");
                        String u_country = data.getString("u_country");

                        Log.e("status",status);
                        Log.e("u_id",u_id);
                        Log.e("u_first_name",u_first_name);
                        Log.e("u_last_name",u_last_name);
                        Log.e("u_email",u_email);
                        Log.e("u_country",u_country);
                        prefrence.saveUserID(MainActivity.this, u_id);
                        prefrence.saveUserFName(MainActivity.this, u_first_name);
                        prefrence.saveUserLName(MainActivity.this, u_last_name);
                        prefrence.saveUserEmail(MainActivity.this, u_email);
                        prefrence.saveUserCountry(MainActivity.this, u_country);
                        prefrence.saveisLoggedIn(MainActivity.this,"true");
                        loginEditor.putString("isLoggedIn", "true");
                        loginEditor.commit();

                        prefrence.saveUserID(getApplicationContext(),u_id);
                        //String uid = prefrence.getUserID(getApplicationContext());
                        Intent intent = new Intent(MainActivity.this, HomeActivity.class);
                        startActivity(intent);
                    } else {
                        String msg=object.getString("message");
                        Log.e("status ", status);
                        Log.e("error ", msg);
                        Toast.makeText(MainActivity.this, msg, Toast.LENGTH_LONG).show();
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

    public void RequestData() {
        GraphRequest request = GraphRequest.newMeRequest(AccessToken.getCurrentAccessToken(), new GraphRequest.GraphJSONObjectCallback() {
            @Override
            public void onCompleted(JSONObject object, GraphResponse response) {

                JSONObject json = response.getJSONObject();
                Log.e("Json data :", response.toString());
                if (json != null) {
                    //String text = "<b>Id :</b> " +json.getString("id")+"<br><br><b>Name :</b> "+json.getString("name")+"<br><br><b>Email :</b> "+json.getString("email")+"<br><br><b>Profile link :</b> "+json.getString("link");
                    // details_txt.setText(Html.fromHtml(text));
                    // profile.setProfileId(json.getString("id"));
                    // System.out.println("text  :"+text);

//                    Log.e("email_id", email_id);
                    u_email = object.optString(getString(R.string.fbParamEmail));
                    editor.putString("u_email", u_email);
                    prefrence.saveUserEmail(MainActivity.this,u_email);
                    //  sharedPreferences.saveUserEmail(getApplicationContext(),u_email);
                    Log.e("u_email", u_email);

                    try {
                        u_first_name = object.getString("first_name");
                        editor.putString("u_first_name", u_first_name);
                        prefrence.saveUserFName(MainActivity.this, u_first_name);
                        Log.e("u_first_name", u_first_name);
                        //    sharedPreferences.saveUserFName(getApplicationContext(),u_first_name);

                        sm_social_provider_id = json.getString("id");
                        editor.putString("sm_social_provider_id", sm_social_provider_id);
                        prefrence.saveSM_SOCIAL_PROVIDER_ID(MainActivity.this,sm_social_provider_id);
                        Log.e("sm_social_provider_id", sm_social_provider_id);
                        //   sharedPreferences.saveSM_SOCIAL_PROVIDER_ID(getApplicationContext(),sm_social_provider_id);

                        u_last_name = object.getString("last_name");
                        editor.putString("u_last_name", u_last_name);
                        prefrence.saveUserLName(MainActivity.this,u_last_name);
                        Log.e("u_last_name", u_last_name);
                        //   sharedPreferences.saveUserLName(getApplicationContext(),u_last_name);

                        /*String gender1 = object.getString("gender");
                        if (gender1.equals("male")){
                            u_gender="M";
                        }else {
                            u_gender="F";
                        }*/
                        //   sharedPreferences.saveUserGender(getApplicationContext(),u_gender);

                        try {
                           /* String url = object.getString("link");
                            byte[] str = url.getBytes("UTF-8");
                            u_image = Base64.encodeToString(str, Base64.DEFAULT);
                            Log.e("base64",""+u_image);*/
                            String id=object.getString("id");
                            URL imageURL = new URL("https://graph.facebook.com/" + id + "/picture?type=large");
                            Log.e("imageURL",""+imageURL);
                            String url = String.valueOf(imageURL);
                            byte[] str = url.getBytes("UTF-8");
                            //u_image = Base64.encodeToString(str, Base64.DEFAULT);
                            //sharedPreferences.saveUserImage(getApplicationContext(),url);
                            Log.e("url",""+url);
                        } catch (MalformedURLException e) {
                            e.printStackTrace();
                        } catch (UnsupportedEncodingException e) {
                            e.printStackTrace();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    action= "sociallogin";
                    sm_social_provider = "facebook";
                    editor.putString("sm_social_provider", sm_social_provider);
                    mr_device_type = "0";
                    editor.putString("mr_device_type", mr_device_type);
                    mr_device_token = regId;
                    editor.putString("mr_device_token", mr_device_token);
                    prefrence.saveDeviceToken(MainActivity.this,mr_device_token);
                    editor.commit();
                    //u_password = "";

                  /*  sharedPreferences.saveSocialAction(getApplicationContext(),sm_social_provider);
                    Intent i = new Intent(getApplicationContext(),SocialSignUpActivity.class);
                    startActivity(i);
                    finish();*/
              //d      new SocialLogin(getApplicationContext()).execute();

                   /* Profile profile = Profile.getCurrentProfile();
                    if (profile != null) {

                        facebook_id=profile.getId();
                        Log.e("facebook_id", facebook_id);
                        m_name=profile.getMiddleName();
                        Log.e("m_name", m_name);
                        l_name=profile.getLastName();
                        Log.e("l_name", l_name);
                        full_name=profile.getName();
                        Log.e("full_name", full_name);
                        profile_image=profile.getProfilePictureUri(400, 400).toString();
                        Log.e("profile_image", profile_image);
                    }*/
                }

            }


        });
        Bundle parameters = new Bundle();
        parameters.putString("fields", "id,first_name,last_name,email,gender,birthday,location"); // Parámetros que pedimos a facebook
        request.setParameters(parameters);
        request.executeAsync();
    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }
}
