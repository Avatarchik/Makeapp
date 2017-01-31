package com.app.makeapp.etc;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/**
 * Created by gleb on 24.12.16.
 */

public class SharedPrefs {

    public static void setToken(Context context, String result, int userId, int userObjectId) {
        PreferenceManager.getDefaultSharedPreferences(context)
                .edit()
                .putString(Constants.TOKEN, result)
                .putInt(Constants.USER_ID, userId)
                .putInt(Constants.USER_OBJECT_ID, userObjectId)
                .commit();
    }

    public static String getToken(Context context) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        return preferences.getString(Constants.TOKEN, "");
    }

    public static int getUserId(Context context) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        return preferences.getInt(Constants.USER_ID, 0);
    }

    public static int getUserObjectId(Context context) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        return preferences.getInt(Constants.USER_OBJECT_ID, 0);
    }

    public static void setInformationId(Context context, int infoId) {
        PreferenceManager.getDefaultSharedPreferences(context)
                .edit()
                .putInt(Constants.INFO_ID, infoId)
                .commit();
    }

    public static int getInformationId(Context context) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        return preferences.getInt(Constants.INFO_ID, 0);
    }
}
