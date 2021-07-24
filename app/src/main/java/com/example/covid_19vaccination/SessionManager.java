package com.example.covid_19vaccination;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import java.util.HashMap;

public class SessionManager {
    public static final String KEY_EMAIL = "Email"; //Key to put in Session
    public static final String KEY_NAME = "Name";
    private static final String PREF_NAME = "LoginSession"; //Session Name
    private static final String IS_LOGIN = "IsLoggedIn"; //For validate log in.
    SharedPreferences mSharedPreferences;
    SharedPreferences.Editor mEditor;
    Context mContext;
    //Visible to other members of package com.example.covid_19vaccination;
    int SESSION_MODE = 0;

    public SessionManager(Context context) {
        this.mContext = context;
        mSharedPreferences = mContext.getSharedPreferences(PREF_NAME, SESSION_MODE);
        mEditor = mSharedPreferences.edit();
    }

    //Using email as session
    public void createLoginSession(String email, String name) {
        mEditor.putBoolean(IS_LOGIN, true);
        mEditor.putString(KEY_EMAIL, email);
        mEditor.putString(KEY_NAME, name);
        mEditor.commit();
    }

    public Boolean isLoggedIn() {
        return mSharedPreferences.getBoolean(IS_LOGIN, false);
    }


    public void checkLoginSession() {
        if (!this.isLoggedIn()) {
            Intent intent = new Intent(mContext, Dashboard.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            mContext.startActivity(intent);
            //  ((Activity)mContext).finish();
        }
    }

    //Get User Details from Session using HashMap
    public HashMap<String, String> getUserDetails() {
        HashMap<String, String> user = new HashMap<String, String>();
        user.put(KEY_EMAIL, mSharedPreferences.getString(KEY_EMAIL, null));
        user.put(KEY_NAME, mSharedPreferences.getString(KEY_NAME, null));
        return user;
    }

    public void logoutSession() {
        mEditor.remove(KEY_EMAIL); //delete d key
        mEditor.remove(KEY_NAME); //delete d key
        mEditor.clear(); //clear all data in sharedPreference
        mEditor.commit(); //commit the changes

        Intent intent = new Intent(mContext, Dashboard.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        mContext.startActivity(intent);
    }
}
