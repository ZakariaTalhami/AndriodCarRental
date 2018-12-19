package com.rental.shaltal.carrental;


import android.os.Bundle;
import android.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.rental.shaltal.carrental.adapter.CarAdapter;
import com.rental.shaltal.carrental.adapter.FavCarAdapter;
import com.rental.shaltal.carrental.adapter.ResCarAdapter;
import com.rental.shaltal.carrental.adapter.customCarAddapter;
import com.rental.shaltal.carrental.models.Car;
import com.rental.shaltal.carrental.models.FavCar;
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
    private int mode =0;

    public static final int DEFAULT_MODE =0;
    public static final int RESERVED_MODE =1;
    public static final int FAVORED_MODE =2;

    public CarMenu() {
        carList = new ArrayList<>();
        carSingleton = CarSingleton.getInstance();
    }

    public List<Car> getCarList() {
        return carList;
    }

    public int getMode() {
        return mode;
    }

    public void setMode(int mode) {
        this.mode = mode;
    }

    public void setCarList(List<Car> carList) {
        this.carList = carList;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.i("", "onCreateView: Creating CarMenu Frag");
        View view = inflater.inflate(R.layout.fragment_car_menu, container, false);;
        ListView listView = (ListView) view.findViewById(R.id.lv_CarMenu);
        Log.i(TAG, "onCreateView: List View "+listView);

        switch (this.mode){
            case DEFAULT_MODE:
                CarAdapter customCarAddapter = new CarAdapter((ArrayList<Car>) this.carList,getActivity().getApplicationContext());
                listView.setAdapter(customCarAddapter);
                break;
            case RESERVED_MODE:
                ResCarAdapter resCarAdapter = new ResCarAdapter((ArrayList<Car>) this.carList,getActivity().getApplicationContext());
                listView.setAdapter(resCarAdapter);
                break;
            case FAVORED_MODE:
                FavCarAdapter favCarAddapter = new FavCarAdapter((ArrayList<Car>) this.carList,getActivity().getApplicationContext());
                listView.setAdapter(favCarAddapter);
                break;
        }

//        CarAdapter customCarAddapter = new CarAdapter((ArrayList<Car>) this.carList,getActivity().getApplicationContext());
//        listView.setAdapter(customCarAddapter);

        if(this.mode == DEFAULT_MODE){
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    TextView tv_Year = (TextView) view.findViewById(R.id.tv_carItemYear);
                    TextView tv_Distance = (TextView) view.findViewById(R.id.tv_carItemDistance);
                    TextView tv_Price = (TextView) view.findViewById(R.id.tv_carItemPrice);
                    TextView tv_Offer = (TextView) view.findViewById(R.id.tv_carItemOffer);
                    ImageView iv_Fav = (ImageView) view.findViewById(R.id.iv_carItemFav);
                    ImageView iv_Res = (ImageView) view.findViewById(R.id.iv_carItemRes);

                    int visibility = (tv_Year.getVisibility() == View.VISIBLE)? View.GONE:View.VISIBLE;

                    tv_Year.setVisibility(visibility);
                    tv_Distance.setVisibility(visibility);
                    tv_Price.setVisibility(visibility);
                    tv_Offer.setVisibility(visibility);
                    iv_Fav.setVisibility(visibility);
                    iv_Res.setVisibility(visibility);
                }
            });
        }else if (this.mode == FAVORED_MODE){
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    TextView tv_Year = (TextView) view.findViewById(R.id.tv_carItemYear);
                    ImageView iv_Fav = (ImageView) view.findViewById(R.id.iv_carItemFav);
                    ImageView iv_Res = (ImageView) view.findViewById(R.id.iv_carItemRes);
                    int visibility = (tv_Year.getVisibility() == View.VISIBLE)? View.GONE:View.VISIBLE;
                    tv_Year.setVisibility(visibility);
                    iv_Fav.setVisibility(visibility);
                    iv_Res.setVisibility(visibility);
                }
            });
        }else if(this.mode == RESERVED_MODE){
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    TextView tv_Year = (TextView) view.findViewById(R.id.tv_carItemYear);
                    TextView tv_Distance = (TextView) view.findViewById(R.id.tv_carItemDistance);
                    ImageView iv_Fav = (ImageView) view.findViewById(R.id.iv_carItemFav);
                    ImageView iv_Res = (ImageView) view.findViewById(R.id.iv_carItemRes);
                    int visibility = (tv_Year.getVisibility() == View.VISIBLE)? View.GONE:View.VISIBLE;
                    tv_Year.setVisibility(visibility);
                    tv_Distance.setVisibility(visibility);
                    iv_Fav.setVisibility(visibility);
                    iv_Res.setVisibility(visibility);
                }
            });
        }


        return view;
    }

}
