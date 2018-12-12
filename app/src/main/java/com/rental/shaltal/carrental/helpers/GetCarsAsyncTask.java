package com.rental.shaltal.carrental.helpers;

import android.app.Activity;
import android.os.AsyncTask;
import android.util.Log;

import com.rental.shaltal.carrental.models.Car;
import com.rental.shaltal.carrental.singleton.CarSingleton;

import java.util.List;

public class GetCarsAsyncTask extends AsyncTask<String,String,String> {
    private static String TAG = "GetCarsAsyncTask";

    private Activity activity;
    public GetCarsAsyncTask(Activity activity){
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
        if (s != null && s.isEmpty()) {
            CarSingleton carSingleton = CarSingleton.getInstance();
            List<Car> carList = CarJSONParser.getCarsFromJSON(s);
            carSingleton.setCarList(carList);
            Log.i(TAG, "onPostExecute: " + carList);
        }else{
            Log.e(TAG, "onPostExecute: Connection with the server returned empty");
        }

    }

    @Override
    protected void onProgressUpdate(String... values) {
        super.onProgressUpdate(values);
    }

    @Override
    protected String doInBackground(String... strings) {
        return RestConnection.getCars(strings[0]);
    }
}
