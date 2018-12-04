package com.rental.shaltal.carrental.helpers;


import android.util.Log;

import java.net.URL;
import java.net.URLConnection;


public class RestConnection {


    private static String TAG = "RestConnection";
    public static String connect(String URL , String action){
        String res=null;

        res = testConnection(URL);

//        switch (){
//
//        }


        return res;
    }

    private static String testConnection(String url){

        try{
            URL myUrl = new URL(url);
            URLConnection connection = myUrl.openConnection();
            connection.setConnectTimeout(1000);
            connection.connect();
            return "";
        } catch (Exception e){
//            return false;
            Log.e(TAG, "testConnection: ", e);
        }
        return null;
    }
}
