package com.sanjay.laravel.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

public class SessionManager {
    // LogCat tag
    private static String TAG = SessionManager.class.getSimpleName();

    // Shared preferences file name
    private static final String PREF_NAME = "laravel";
    private static final String Token = "token";
    private static final String User_name = "user_name";
    private static final String User_Email = "user_email";
    private static final String User_Avatar = "user_avatar";
    private static final String Token_type = "Token_type";
    // Shared Preferences
    private SharedPreferences pref;
    private SharedPreferences.Editor editor;
    private Context _context;

    private static final String KEY_IS_LOGGEDIN = "isLoggedIn";

    private static final String IS_FIRST_TIME_LAUNCH = "IsFirstTimeLaunch";

    // Shared pref mode
    private int PRIVATE_MODE = 0;

    public SessionManager(Context context) {
        this._context = context;
        pref = _context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = pref.edit();
    }

    public void setLogin(boolean isLoggedIn) {

        editor.putBoolean(KEY_IS_LOGGEDIN, isLoggedIn);

        // commit changes
        editor.commit();

        Log.d(TAG, "User login session modified!");
    }

    public boolean isLoggedIn() {
        return pref.getBoolean(KEY_IS_LOGGEDIN, false);
    }

    public String getToken() {
        return pref.getString(Token, null);
    }

    public void setToken(String token) {
        editor.putString(Token, token);
        editor.commit();
    }

    public boolean isFirstTimeLaunch() {
        return pref.getBoolean(IS_FIRST_TIME_LAUNCH, true);
    }

    public void setFirstTimeLaunch(boolean isFirstTime) {
        editor.putBoolean(IS_FIRST_TIME_LAUNCH, isFirstTime);
        editor.commit();
    }

    public String getName() {
        return pref.getString(User_name, null);
    }

    public void setName(String Name) {
        editor.putString(User_name, Name);
        editor.commit();
    }

    public String getEmail() {
        return pref.getString(User_Email, null);
    }

    public void setEmail(String Email) {
        editor.putString(User_Email, Email);
        editor.commit();
    }

    public String getAvatar() {
        return pref.getString(User_Avatar, null);
    }

    public void setAvatar(String Avatar) {
        editor.putString(User_Avatar, Avatar);
        editor.commit();
    }

    public String getToken_type() {
        return pref.getString(Token_type, null);
    }

    public void setToken_type(String tokentype) {
        editor.putString(Token_type, tokentype);
        editor.commit();
    }
}
