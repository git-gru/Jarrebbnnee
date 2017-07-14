package com.jarrebbnnee;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.widget.Toast;

import com.google.firebase.messaging.FirebaseMessaging;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class SplashActivity extends AppCompatActivity {

    BroadcastReceiver mRegistrationBroadcastReceiver;
    SharedPreferences loginPref;
    SharedPreferences.Editor loginEditor;
    SaveSharedPrefrence prefrence;
    public static final String LoginManager="LoginManager" ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        prefrence= new SaveSharedPrefrence();
        loginPref = getSharedPreferences(LoginManager, Context.MODE_PRIVATE);
        loginEditor = loginPref.edit();

        PackageInfo info;
        try {
            info = getPackageManager().getPackageInfo(getPackageName(),
                    PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest md;
                md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                String something = new String(Base64.encode(md.digest(), 0));
                // String something = new
                // String(Base64.encodeBytes(md.digest()));
                Log.e("hash key", something);
            }
        } catch (PackageManager.NameNotFoundException e1) {
            Log.e("name not found", e1.toString());
        } catch (NoSuchAlgorithmException e) {
            Log.e("no such an algorithm", e.toString());
        } catch (Exception e) {
            Log.e("exception", e.toString());
        }

        Thread timerThread = new Thread(){
            public void run(){
                try{
                    sleep(3000);
                }catch(InterruptedException e){
                    e.printStackTrace();
                }finally{
                    String check = prefrence.getisLoggedIn(SplashActivity.this);
                    Log.e("isLoggedIn", check);
                    if (check.equals("true")) {
                        Intent intent = new Intent(SplashActivity.this,HomeActivity.class);
                        startActivity(intent);
                        finish();

                    } else{
                        Intent intent = new Intent(SplashActivity.this,MainActivity.class);
                        startActivity(intent);
                        finish();
                    }

                }
            }
        };
        timerThread.start();
        FirebaseMessaging.getInstance().subscribeToTopic(Config.TOPIC_GLOBAL);
        mRegistrationBroadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {

// checking for type intent filter
                Log.e("iff", "iff");
                if (intent.getAction().equals(Config.REGISTRATION_COMPLETE)) {
// gcm successfully registered
// now subscribe to `global` topic to receive app wide notifications
                    Log.e("iff enter", "iff enter");
                    FirebaseMessaging.getInstance().subscribeToTopic(Config.TOPIC_GLOBAL);

                    displayFirebaseRegId();
                  //  DeviceRegisterRetrofit();


                } else if (intent.getAction().equals(Config.PUSH_NOTIFICATION)) {
// new push notification is received

                    String message = intent.getStringExtra("message");

                    Toast.makeText(getApplicationContext(), "Push notification: " + message, Toast.LENGTH_LONG).show();

//txtMessage.setText(message);
                }
            }
        };

        displayFirebaseRegId();
    }
    private void displayFirebaseRegId() {
        SharedPreferences pref = getApplicationContext().getSharedPreferences(Config.SHARED_PREF, 0);
        String regId = pref.getString("regId", null);
        Toast.makeText(SplashActivity.this, "Reg id \n\n" + regId, Toast.LENGTH_SHORT).show();
        Log.e("Firebase", "Firebase reg id: " + regId);

    }

}
