package com.jarrebbnnee;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

public class SaveSharedPrefrence {

	Context context;
	boolean isSuccess;
	SharedPreferences sharedPreferences;

	public static final String PREFS_NAME = "MyPref";
	public static final String KEY_USER_ID= "u_id";
	public static final String KEY_USER_FNAME= "u_first_name";
	public static final String KEY_USER_LNAME= "u_last_name";
	public static final String KEY_USER_EMAIL= "u_email";
	public static final String KEY_USER_PHONE= "u_phone";
	public static final String KEY_USER_IMAGE= "key_user_image";
	public static final String KEY_USER_COUNTRY= "u_country";
	public static final String KEY_DEVICE_TOKEN= "device_token";
	public static final String KEY_IS_LOGGED_IN= "is_logged_in";
	public static final String SM_SOCIAL_PROVIDER_ID= "sm_social_provider_id";

	public void saveSM_SOCIAL_PROVIDER_ID(Context context, String sm_social_provider_id) {

		sharedPreferences = context.getSharedPreferences(PREFS_NAME,
				Context.MODE_PRIVATE);

		Editor editor = sharedPreferences.edit();
		editor.putString(SM_SOCIAL_PROVIDER_ID, sm_social_provider_id);


		editor.commit();

	}
	public String getSM_SOCIAL_PROVIDER_ID(Context context) {

		sharedPreferences = context.getSharedPreferences(PREFS_NAME,
				Context.MODE_PRIVATE);
		String sm_social_provider_id = sharedPreferences.getString(SM_SOCIAL_PROVIDER_ID, "");

		return sm_social_provider_id;
	}

	public void saveisLoggedIn(Context context, String is_logged_in) {

		sharedPreferences = context.getSharedPreferences(PREFS_NAME,
				Context.MODE_PRIVATE);

		Editor editor = sharedPreferences.edit();
		editor.putString(KEY_IS_LOGGED_IN, is_logged_in);


		editor.commit();

	}
	public String getisLoggedIn(Context context) {

		sharedPreferences = context.getSharedPreferences(PREFS_NAME,
				Context.MODE_PRIVATE);
		String is_logged_in = sharedPreferences.getString(KEY_IS_LOGGED_IN, "null");

		return is_logged_in;
	}



	public void saveDeviceToken(Context context, String key_device_token) {

		sharedPreferences = context.getSharedPreferences(PREFS_NAME,
				Context.MODE_PRIVATE);

		Editor editor = sharedPreferences.edit();
		editor.putString(KEY_DEVICE_TOKEN, key_device_token);


		editor.commit();

	}
	public String getDeviceToken(Context context) {

		sharedPreferences = context.getSharedPreferences(PREFS_NAME,
				Context.MODE_PRIVATE);
		String key_device_token = sharedPreferences.getString(KEY_DEVICE_TOKEN, "");

		return key_device_token;
	}

	public void saveUserID(Context context, String user_id) {

		sharedPreferences = context.getSharedPreferences(PREFS_NAME,
				Context.MODE_PRIVATE);

		Editor editor = sharedPreferences.edit();
		editor.putString(KEY_USER_ID, user_id);


		editor.commit();

	}

	public String getUserID(Context context) {

		sharedPreferences = context.getSharedPreferences(PREFS_NAME,
				Context.MODE_PRIVATE);
		String user_id = sharedPreferences.getString(KEY_USER_ID, "");

		return user_id;
	}
	public void saveUserImage(Context context, String user_image) {

		sharedPreferences = context.getSharedPreferences(PREFS_NAME,
				Context.MODE_PRIVATE);

		Editor editor = sharedPreferences.edit();
		editor.putString(KEY_USER_IMAGE, user_image);


		editor.commit();

	}

	public String getUserImage(Context context) {

		sharedPreferences = context.getSharedPreferences(PREFS_NAME,
				Context.MODE_PRIVATE);
		String user_image = sharedPreferences.getString(KEY_USER_IMAGE, "");

		return user_image;
	}

	public void saveUserFName(Context context, String user_fname) {

		sharedPreferences = context.getSharedPreferences(PREFS_NAME,
				Context.MODE_PRIVATE);

		Editor editor = sharedPreferences.edit();
		editor.putString(KEY_USER_FNAME, user_fname);


		editor.commit();

	}

	public String getUserFName(Context context) {

		sharedPreferences = context.getSharedPreferences(PREFS_NAME,
				Context.MODE_PRIVATE);
		String user_fname = sharedPreferences.getString(KEY_USER_FNAME, "");

		return user_fname;
	}
	public void saveUserLName(Context context, String user_lname) {

		sharedPreferences = context.getSharedPreferences(PREFS_NAME,
				Context.MODE_PRIVATE);

		Editor editor = sharedPreferences.edit();
		editor.putString(KEY_USER_LNAME, user_lname);


		editor.commit();

	}

	public String getUserLName(Context context) {

		sharedPreferences = context.getSharedPreferences(PREFS_NAME,
				Context.MODE_PRIVATE);
		String user_lname = sharedPreferences.getString(KEY_USER_LNAME, "");

		return user_lname;
	}
	public void saveUserEmail(Context context, String user_email) {

		sharedPreferences = context.getSharedPreferences(PREFS_NAME,
				Context.MODE_PRIVATE);

		Editor editor = sharedPreferences.edit();
		editor.putString(KEY_USER_EMAIL, user_email);


		editor.commit();

	}

	public String getUserEmail(Context context) {

		sharedPreferences = context.getSharedPreferences(PREFS_NAME,
				Context.MODE_PRIVATE);
		String user_email = sharedPreferences.getString(KEY_USER_EMAIL, "");

		return user_email;
	}
	public void saveUserPhone(Context context, String user_phone) {

		sharedPreferences = context.getSharedPreferences(PREFS_NAME,
				Context.MODE_PRIVATE);

		Editor editor = sharedPreferences.edit();
		editor.putString(KEY_USER_PHONE, user_phone);


		editor.commit();

	}

	public String getUserPhone(Context context) {

		sharedPreferences = context.getSharedPreferences(PREFS_NAME,
				Context.MODE_PRIVATE);
		String user_phone = sharedPreferences.getString(KEY_USER_PHONE, "");

		return user_phone;
	}
	public void saveUserCountry(Context context, String user_country) {

		sharedPreferences = context.getSharedPreferences(PREFS_NAME,
				Context.MODE_PRIVATE);

		Editor editor = sharedPreferences.edit();
		editor.putString(KEY_USER_COUNTRY, user_country);


		editor.commit();

	}

	public String getUserCountry(Context context) {

		sharedPreferences = context.getSharedPreferences(PREFS_NAME,
				Context.MODE_PRIVATE);
		String user_country = sharedPreferences.getString(KEY_USER_COUNTRY, "");

		return user_country;
	}
	public void DeletePrefrence(Context context) {

		sharedPreferences = context.getSharedPreferences(PREFS_NAME,
				Context.MODE_PRIVATE);

		Editor editor = sharedPreferences.edit();
		editor.clear();
		editor.commit();

	}

}
