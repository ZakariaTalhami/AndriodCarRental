package com.rental.shaltal.carrental.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.rental.shaltal.carrental.R;
import com.rental.shaltal.carrental.models.Car;

import java.util.ArrayList;

public class CarAdapter extends ArrayAdapter implements View.OnClickListener {

    private static final String TAG ="CarAdapter";
    private ArrayList<Car> dataSet;
    Context mContext;

    public CarAdapter(ArrayList<Car> data, Context context) {
        super(context, R.layout.custom_car_layout, data);
        this.dataSet = data;
        this.mContext=context;

    }

    private static class ViewHolder {
        TextView tv_Title;
        TextView tv_Year;
        TextView tv_Distance;
        TextView tv_Price;
        TextView tv_Offer;
        ImageView iv_Fav;
        ImageView iv_Res;
    }

    @Override
    public int getCount() {
        Log.i(TAG, "getCount: "+dataSet.size());
        return dataSet.size();
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        Log.i(TAG, "getView: start");
       Car car = (Car)getItem(position);
        ViewHolder viewHolder;
        final View result;
        if (convertView == null){
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.custom_car_layout, parent, false);
            viewHolder = new ViewHolder();

            viewHolder.tv_Title = (TextView) convertView.findViewById(R.id.tv_carItemTitle);
            viewHolder.tv_Year = (TextView) convertView.findViewById(R.id.tv_carItemYear);
            viewHolder.tv_Distance = (TextView) convertView.findViewById(R.id.tv_carItemDistance);
            viewHolder.tv_Price = (TextView) convertView.findViewById(R.id.tv_carItemPrice);
            viewHolder.tv_Offer = (TextView) convertView.findViewById(R.id.tv_carItemOffer);
            viewHolder.iv_Fav = (ImageView) convertView.findViewById(R.id.iv_carItemFav);
            viewHolder.iv_Res = (ImageView) convertView.findViewById(R.id.iv_carItemRes);

            result = convertView;

            convertView.setTag(viewHolder);

        }else {
            viewHolder = (ViewHolder) convertView.getTag();
            result=convertView;
        }

        viewHolder.tv_Title.setText(car.getMake()+" "+car.getModel());
        viewHolder.tv_Distance.setText(car.getDistance());
        viewHolder.tv_Year.setText(car.getYear());
        viewHolder.tv_Price.setText(car.getPrice()+"");
        if (car.isOffer())
            viewHolder.tv_Offer.setText("Car on Offer");
        else
            viewHolder.tv_Offer.setText("No offers");

        Log.i(TAG, "getView: end");
        return convertView;

    }

    @Override
    public void onClick(View v) {

    }
}
