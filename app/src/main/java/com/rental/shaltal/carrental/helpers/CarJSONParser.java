package com.rental.shaltal.carrental.helpers;

import android.util.Log;

import com.rental.shaltal.carrental.models.Car;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class CarJSONParser {

    private static String TAG = "CarJSONParser";
    public static List<Car> getCarsFromJSON(String json){
        List<Car> cars = null;
        try{
            JSONArray jsonArray = new JSONArray(json);
            cars = new ArrayList<>();
            for (int i = 0; i < jsonArray.length(); i++){
                JSONObject jsonObject = new JSONObject();
                jsonObject = (JSONObject) jsonArray.get(i);
                Car car = new Car();
                car.setYear(jsonObject.getString("year"));
                car.setMake(jsonObject.getString("make"));
                car.setModel(jsonObject.getString("model"));
                car.setDistance(jsonObject.getString("distance"));
                car.setPrice(jsonObject.getInt("price"));
                car.setAccidents(jsonObject.getBoolean("accidents"));
                car.setOffer(jsonObject.getBoolean("offers"));
                cars.add(car);
            }

        }
        catch (Exception e){
            Log.e(TAG, "getCarsFromJSON: Failed to parse to cars" );
            Log.e(TAG, "getCarsFromJSON: "+e);
        }
        return cars;
    }
}
