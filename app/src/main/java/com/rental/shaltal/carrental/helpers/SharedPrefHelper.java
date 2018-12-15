package com.rental.shaltal.carrental.helpers;

import android.content.Context;
import android.content.SharedPreferences;

import static com.rental.shaltal.carrental.constants.AppConstants.*;
import com.rental.shaltal.carrental.models.User;

public class SharedPrefHelper {

    public static void addLoginUser(Context context, String email , String pass){

        SharedPreferences sharedPreferences = context.getSharedPreferences(SHARED_PREF , Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(SAVED_EMAIL , email);
        editor.putString(SAVED_PASS , pass);
        editor.apply();

    }

    public static String getSavedEmail(Context context){
        SharedPreferences sharedPreferences = context.getSharedPreferences(SHARED_PREF , Context.MODE_PRIVATE);
        String email = sharedPreferences.getString(SAVED_EMAIL , "");
        return email;
    }

    public static String getSavedPass(Context context){
        SharedPreferences sharedPreferences = context.getSharedPreferences(SHARED_PREF , Context.MODE_PRIVATE);
        String pass = sharedPreferences.getString(SAVED_PASS , "");
        return pass;
    }

    public static void setLoggedIn(Context context){
        SharedPreferences sharedPreferences = context.getSharedPreferences(SHARED_PREF , Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(LOGIN_FLAG , true);
        editor.apply();
    }

    public static void removeLoggedIn(Context context){
        SharedPreferences sharedPreferences = context.getSharedPreferences(SHARED_PREF , Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(LOGIN_FLAG , false);
        editor.apply();
    }

    public static boolean getLoggedIn(Context context){
        SharedPreferences sharedPreferences = context.getSharedPreferences(SHARED_PREF , Context.MODE_PRIVATE);
        boolean isLoggedIn = sharedPreferences.getBoolean(LOGIN_FLAG , false);
        return isLoggedIn;
    }
}
