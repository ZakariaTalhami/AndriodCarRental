package com.rental.shaltal.carrental;


import android.os.Bundle;
import android.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.rental.shaltal.carrental.adapter.CarAdapter;
import com.rental.shaltal.carrental.adapter.customCarAddapter;
import com.rental.shaltal.carrental.models.Car;
import com.rental.shaltal.carrental.singleton.CarSingleton;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class CarMenu extends Fragment {


    private static final String TAG = "CarMenu";
    private CarSingleton carSingleton;
    private List<Car> carList;
    public CarMenu() {
        carList = new ArrayList<>();
        carSingleton = CarSingleton.getInstance();
    }

    public List<Car> getCarList() {
        return carList;
    }

    public void setCarList(List<Car> carList) {
        this.carList = carList;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.i("", "onCreateView: Creating CarMenu Frag");
        // Inflate the layout for this fragment

//        List<Car> testinglist = new ArrayList<>();
////        testinglist.add(new Car("how2","how2","how","how",200,false,true));
////        testinglist.add(new Car("how2","how2","how","how",200,false,true));
////        testinglist.add(new Car("how2","how2","how","how",200,false,true));
//        testinglist.add(new Car("how2","how2","how","how",200,false,true));
        View view = inflater.inflate(R.layout.fragment_car_menu, container, false);;
        ListView listView = (ListView) view.findViewById(R.id.lv_CarMenu);
        Log.i(TAG, "onCreateView: List View "+listView);
        CarAdapter customCarAddapter = new CarAdapter((ArrayList<Car>) this.carList,getActivity().getApplicationContext());

        listView.setAdapter(customCarAddapter);

        return view;
    }

}
