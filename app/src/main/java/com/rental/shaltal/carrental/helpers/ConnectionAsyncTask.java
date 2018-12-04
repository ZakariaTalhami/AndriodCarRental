package com.rental.shaltal.carrental.helpers;

import android.app.Activity;
import android.os.AsyncTask;
import android.util.Log;

public class ConnectionAsyncTask extends AsyncTask<String,String,String>{
    private static String TAG = "ConnectionAsyncTask";

    private Activity activity;
    public ConnectionAsyncTask(Activity activity){
        this.activity = activity;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected void onPostExecute(String s) {
        Log.i(TAG, "onPostExecute: "+s);
        super.onPostExecute(s);
    }

    @Override
    protected void onProgressUpdate(String... values) {
        super.onProgressUpdate(values);
    }

    @Override
    protected String doInBackground(String... strings) {
        try {
            if (strings.length != 2)
                throw new Exception("");
            return RestConnection.connect(strings[0], strings[1]);
        }
        catch (Exception e){
            Log.e(TAG, "doInBackground: ", e);
        }

        return null;
    }
}
