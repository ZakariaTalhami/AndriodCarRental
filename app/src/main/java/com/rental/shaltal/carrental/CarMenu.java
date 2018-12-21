package com.rental.shaltal.carrental;


import android.os.Bundle;
import android.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.rental.shaltal.carrental.adapter.AdminResCarAdapter;
import com.rental.shaltal.carrental.adapter.CarAdapter;
import com.rental.shaltal.carrental.adapter.FavCarAdapter;
import com.rental.shaltal.carrental.adapter.ResCarAdapter;
import com.rental.shaltal.carrental.dialogs.FilterDialog;
import com.rental.shaltal.carrental.models.Car;
import com.rental.shaltal.carrental.singleton.CarSingleton;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


/**
 * A simple {@link Fragment} subclass.
 */
public class CarMenu extends Fragment  implements FilterDialog.FilterDialogInterfae{


    private static final String TAG = "CarMenu";
    private CarSingleton carSingleton;
    private List<Car> carList;
    private int mode =0;

    public static final int DEFAULT_MODE =0;
    public static final int RESERVED_MODE =1;
    public static final int FAVORED_MODE =2;
    public static final int ADMIN_RESERVED_MODE =3;

    private ListView thisListView;
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
        thisListView = listView;
        Button bt_filter = (Button) view.findViewById(R.id.bt_filter);
        bt_filter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openFilterDialog();
            }
        });


        Log.i(TAG, "onCreateView: List View "+listView);


        addListviewAdapter(listView , this.carList);

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
        }else if(this.mode == ADMIN_RESERVED_MODE){
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    TextView tv_Year = (TextView) view.findViewById(R.id.tv_carItemYear);
                    TextView tv_Distance = (TextView) view.findViewById(R.id.tv_carItemDistance);
                    TextView tv_offer = (TextView) view.findViewById(R.id.tv_carItemOffer);
                    TextView tv_price = (TextView) view.findViewById(R.id.tv_carItemPrice);
                    int visibility = (tv_Year.getVisibility() == View.VISIBLE)? View.GONE:View.VISIBLE;
                    tv_Year.setVisibility(visibility);
                    tv_Distance.setVisibility(visibility);
                    tv_offer.setVisibility(visibility);
                    tv_price.setVisibility(visibility);
                }
            });
        }


        return view;
    }

    private void openFilterDialog() {
        FilterDialog filterDialog = new FilterDialog();
        filterDialog.setTargetFragment(this , 0);
        filterDialog.show(getFragmentManager() , "filter");
    }

    @Override
    public void filterData(String price, String model, String make) {
        Log.i(TAG, "filterData: "+price+" "+model+" "+make+" ");
        List<Car> filteredList;
        try{
            boolean usePrice = (price==null || price.isEmpty());
            boolean useModel = (model==null || model.isEmpty());
            boolean useMake = (make==null || make.isEmpty());
            Log.i(TAG, "filterData: "+usePrice+" "+useModel+" "+useMake+" ");
            filteredList = this.carList.stream()
                    .filter(car ->
                                 (useMake || car.getMake().equalsIgnoreCase(make))&&
                                 (usePrice || car.getPrice() == Integer.parseInt(price))&&
                                 (useModel || car.getModel().equalsIgnoreCase(model))
                    ).collect(Collectors.toList());

            addListviewAdapter(thisListView , filteredList);
        }catch (Exception e){
            Toast.makeText(getActivity().getApplicationContext(), "failed to filter Cars", Toast.LENGTH_SHORT).show();
        }


//        thisListView.setAdapter(null);
    }

    private void addListviewAdapter(ListView listView , List<Car> carList){
        switch (this.mode){
            case DEFAULT_MODE:
                CarAdapter customCarAddapter = new CarAdapter((ArrayList<Car>) carList,getActivity().getApplicationContext());
                listView.setAdapter(customCarAddapter);
                break;
            case RESERVED_MODE:
                ResCarAdapter resCarAdapter = new ResCarAdapter((ArrayList<Car>) carList,getActivity().getApplicationContext());
                listView.setAdapter(resCarAdapter);
                break;
            case FAVORED_MODE:
                FavCarAdapter favCarAddapter = new FavCarAdapter((ArrayList<Car>) carList,getActivity().getApplicationContext());
                listView.setAdapter(favCarAddapter);
                break;
            case ADMIN_RESERVED_MODE:
                AdminResCarAdapter adminResCarAdapter = new AdminResCarAdapter((ArrayList<Car>)carList , getActivity().getApplicationContext());
                listView.setAdapter(adminResCarAdapter);
                break;
        }
    }
}
