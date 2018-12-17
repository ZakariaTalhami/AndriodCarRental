package com.rental.shaltal.carrental.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.rental.shaltal.carrental.R;
import com.rental.shaltal.carrental.models.Car;

import java.util.List;

public class customCarAddapter extends BaseAdapter {
    private List<Car> cars;
    private Context ctx;

    public customCarAddapter(Context context , List<Car> cars) {
        this.cars = cars;
        this.ctx = context;

    }

    @Override
    public int getCount() {
        return 0;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = LayoutInflater.from(ctx).inflate(R.layout.custom_car_layout,null);
        Car car = cars.get(position);
        TextView tv_Title = (TextView) view.findViewById(R.id.tv_carItemTitle);
        TextView tv_Year = (TextView) view.findViewById(R.id.tv_carItemYear);
        TextView tv_Distance = (TextView) view.findViewById(R.id.tv_carItemDistance);
        TextView tv_Price = (TextView) view.findViewById(R.id.tv_carItemPrice);
        TextView tv_Offer = (TextView) view.findViewById(R.id.tv_carItemOffer);
        ImageView iv_Fav = (ImageView) view.findViewById(R.id.iv_carItemFav);
        ImageView iv_Res = (ImageView) view.findViewById(R.id.iv_carItemRes);

        tv_Title.setText(car.getMake()+" "+car.getModel());
        tv_Distance.setText(car.getDistance());
        tv_Year.setText(car.getYear());
        tv_Price.setText(car.getPrice());
        if (car.isOffer())
            tv_Offer.setText("Car on Offer");
        else
            tv_Offer.setText("No offers");
        return view;
    }
}
