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
import com.rental.shaltal.carrental.models.User;
import com.rental.shaltal.carrental.singleton.CarSingleton;

import java.util.ArrayList;

public class UserAdapter extends ArrayAdapter implements View.OnClickListener {

    private static final String TAG ="CarAdapter";
    private ArrayList<User> dataSet;
    Context mContext;

    public UserAdapter(ArrayList<User> data, Context context) {
        super(context, R.layout.custom_car_layout, data);
        this.dataSet = data;
        this.mContext=context;

    }

    private static class ViewHolder {
        TextView tv_Name;
        TextView tv_Gender;
        TextView tv_Location;
        TextView tv_Phone;
        TextView tv_Email;
        ImageView iv_Delete;
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
       User user = (User)getItem(position);
       User loggedUser = CarSingleton.getInstance().getUser();
        ViewHolder viewHolder;
        final View result;
        if (convertView == null){
//            DatabaseHelper databaseHelper = new DatabaseHelper(mContext);
//            boolean reserved = databaseHelper.isReserved(car , user);
//            boolean favored = databaseHelper.isFavored(car , user);
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.custom_user_layout, parent, false);
            viewHolder = new ViewHolder();

            viewHolder.tv_Name = (TextView) convertView.findViewById(R.id.tv_userName);
            viewHolder.tv_Gender = (TextView) convertView.findViewById(R.id.tv_userGender);
            viewHolder.tv_Location = (TextView) convertView.findViewById(R.id.tv_userLocation);
            viewHolder.tv_Phone = (TextView) convertView.findViewById(R.id.tv_userPhone);
            viewHolder.tv_Email = (TextView) convertView.findViewById(R.id.tv_userEmail);
            viewHolder.iv_Delete = (ImageView) convertView.findViewById(R.id.iv_userDelete);


            viewHolder.tv_Gender.setVisibility(View.GONE);
            viewHolder.tv_Location.setVisibility(View.GONE);
            viewHolder.tv_Phone.setVisibility(View.GONE);
            viewHolder.tv_Email.setVisibility(View.GONE);
            viewHolder.iv_Delete.setVisibility(View.GONE);

            viewHolder.iv_Delete.setImageResource(R.drawable.ic_delete_black_24dp);

            result = convertView;

            convertView.setTag(viewHolder);

        }else {
            viewHolder = (ViewHolder) convertView.getTag();
            result=convertView;
        }

        viewHolder.tv_Name.setText(user.getFirstName()+" "+user.getLastName());
        viewHolder.tv_Gender.setText(user.getGender().toString());
        viewHolder.tv_Location.setText(user.getCity()+" "+user.getCountry());
        viewHolder.tv_Phone.setText(user.getPhoneNumber());
        viewHolder.tv_Email.setText(user.getEmail());

        viewHolder.iv_Delete.setOnClickListener(this);
        viewHolder.iv_Delete.setTag(position);

        Log.i(TAG, "getView: end");
        return convertView;

    }

    @Override
    public void onClick(View v) {
        User clickedUser = (User) getItem((Integer) v.getTag());
        User user = CarSingleton.getInstance().getUser();
        DatabaseHelper databaseHelper = new DatabaseHelper(mContext);
        databaseHelper.deleteUser(clickedUser);
        this.dataSet.remove((int)v.getTag());
        this.notifyDataSetChanged();
        v.setVisibility(View.GONE);
    }
}
