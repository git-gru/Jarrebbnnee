package com.jarrebbnnee;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by vi6 on 18-Mar-17.
 */

public class loadPreference {

    public static void loadSharedPrefs(Context context, String ... prefs) {

        // Logging messages left in to view Shared Preferences. I filter out all logs except for ERROR; hence why I am printing error messages.

        Log.e("Loading Shared Prefs", "-----------------------------------");
        Log.e("----------------", "---------------------------------------");

        for (String pref_name: prefs) {

            SharedPreferences preference = context.getSharedPreferences(pref_name, MODE_PRIVATE);
            for (String key : preference.getAll().keySet()) {

                Log.e(String.format("Shared Preference : %s - %s", pref_name, key),
                        preference.getString(key, "error!"));

            }

            Log.e("----------------", "---------------------------------------");

        }

        Log.e("Finished Shared Prefs", "----------------------------------");

    }
}
