package com.rental.shaltal.carrental.helpers;

import android.app.Activity;
import android.os.AsyncTask;
import android.util.Log;

public class ConnectionAsyncTask extends AsyncTask<String,String,Boolean>{
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
    protected void onPostExecute(Boolean bool) {
        Log.i(TAG, "onPostExecute: "+bool);
        super.onPostExecute(bool);

        if (bool){
            moveToNextPage();
        }
        else{
            stayAndDisplayError();
        }



    }

    @Override
    protected void onProgressUpdate(String... values) {
        super.onProgressUpdate(values);
    }

    @Override
    protected Boolean doInBackground(String... strings) {
        return RestConnection.testConnection(strings[0]);
    }


    private void moveToNextPage() {

    }

    private void stayAndDisplayError(){

    }
}
