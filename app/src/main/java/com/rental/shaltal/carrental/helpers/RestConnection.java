package com.rental.shaltal.carrental.helpers;


import android.util.Log;

import com.rental.shaltal.carrental.constants.RestActionEnum;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;


public class RestConnection {


    private static String TAG = "RestConnection";

    public static Boolean testConnection(String url){

        try{
            URL myUrl = new URL(url);
            URLConnection connection = myUrl.openConnection();
            connection.setConnectTimeout(1000);
            connection.connect();
            return Boolean.TRUE;
        } catch (Exception e){
//            return false;
            Log.e(TAG, "testConnection: ", e);
        }
        return Boolean.FALSE;
    }

    public static String getCars(String URL){
        String res= null;
        BufferedReader bufferedReader = null;
        try {
            URL url = new  URL(URL);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            bufferedReader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            StringBuilder stringBuilder = new StringBuilder();
//            String line = bufferedReader.readLine();
            String line = null;
            while((line = bufferedReader.readLine()) != null){
                stringBuilder.append(line+"\n");
            }
            res = stringBuilder.toString();
        }
        catch (Exception e){
            Log.i(TAG, "getCars: Failed to Read from the server");
        }


        return  res;
    }
}
