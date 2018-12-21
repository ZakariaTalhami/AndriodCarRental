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
import android.widget.Toast;

import com.rental.shaltal.carrental.R;
import com.rental.shaltal.carrental.helpers.DatabaseHelper;
import com.rental.shaltal.carrental.models.Car;
import com.rental.shaltal.carrental.models.ReservedCar;
import com.rental.shaltal.carrental.models.User;
import com.rental.shaltal.carrental.singleton.CarSingleton;

import java.util.ArrayList;

public class AdminResCarAdapter extends ArrayAdapter implements View.OnClickListener {

    private static final String TAG ="CarAdapter";
    private ArrayList<Car> dataSet;
    Context mContext;

    public AdminResCarAdapter(ArrayList<Car> data, Context context) {
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
       User user = ((ReservedCar)car).getUser();
        ViewHolder viewHolder;
        final View result;
        if (convertView == null){
            DatabaseHelper databaseHelper = new DatabaseHelper(mContext);
            boolean reserved = databaseHelper.isReserved(car , user);
            boolean favored = databaseHelper.isFavored(car , user);
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

            viewHolder.tv_Year.setVisibility(View.GONE);
            viewHolder.tv_Distance.setVisibility(View.GONE);
            viewHolder.tv_Price.setVisibility(View.GONE);
            viewHolder.tv_Offer.setVisibility(View.GONE);
            viewHolder.iv_Fav.setVisibility(View.GONE);
            viewHolder.iv_Res.setVisibility(View.GONE);


            if (favored){
                viewHolder.iv_Fav.setImageResource(R.drawable.ic_favorite_border_black_24dp);
            }
            else {
                viewHolder.iv_Fav.setImageResource(R.drawable.ic_favorite_black_32dp);
            }

            if (reserved){
                viewHolder.iv_Res.setImageResource(R.drawable.ic_bookmark_border_black_24dp);
            }
            else {
                viewHolder.iv_Res.setImageResource(R.drawable.ic_book_black_32dp);
            }




            result = convertView;

            convertView.setTag(viewHolder);

        }else {
            viewHolder = (ViewHolder) convertView.getTag();
            result=convertView;
        }
        viewHolder.tv_Title.setText(car.getMake()+" "+car.getModel());
        viewHolder.tv_Year.setText(car.getYear());
        viewHolder.tv_Distance.setText(((ReservedCar)car).getReservedDate());
        viewHolder.tv_Price.setText(user.getFirstName()+" "+user.getLastName());
        viewHolder.tv_Offer.setText(user.getEmail());
        viewHolder.iv_Res.setOnClickListener(this);
        viewHolder.iv_Res.setTag(position);
        viewHolder.iv_Fav.setOnClickListener(this);
        viewHolder.iv_Fav.setTag(position);

        Log.i(TAG, "getView: end");
        return convertView;

    }

    @Override
    public void onClick(View v) {
        Car clickedCar = (Car) getItem((Integer) v.getTag());
        User user = CarSingleton.getInstance().getUser();
        DatabaseHelper databaseHelper = new DatabaseHelper(mContext);

        switch (v.getId()){
            case R.id.iv_carItemFav:
                Log.i(TAG, "onClick: Clicked on Fav");
                boolean favored = databaseHelper.isFavored(clickedCar , user);
                if (favored){
                    databaseHelper.deleteFavoriteCar(user, clickedCar);
                    Toast.makeText(mContext, "Added To favorites", Toast.LENGTH_SHORT).show();
                    ((ImageView)v).setImageResource(R.drawable.ic_favorite_black_32dp);
                }else{
                    databaseHelper.insertFavoriteCar(user, clickedCar);
                    Toast.makeText(mContext, "Removed from favorites", Toast.LENGTH_SHORT).show();
                    ((ImageView)v).setImageResource(R.drawable.ic_favorite_border_black_24dp);
                }
                break;
            case R.id.iv_carItemRes:
                Log.i(TAG, "onClick: Clicked on Book");
                boolean reserved = databaseHelper.isReserved(clickedCar , user);
                if(!reserved){
                    databaseHelper.insertReservedCar(user, clickedCar);
                    Toast.makeText(mContext, "Car Reserved", Toast.LENGTH_SHORT).show();
                    ((ImageView)v).setImageResource(R.drawable.ic_bookmark_border_black_24dp);
                }
                break;
        }
    }
}
