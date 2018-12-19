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

import com.rental.shaltal.carrental.adapter.UserAdapter;
import com.rental.shaltal.carrental.models.User;
import com.rental.shaltal.carrental.singleton.CarSingleton;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class UserListFragment extends Fragment {

    private static final String TAG = "UserListFragment";
    private List<User> userList;
    private User loggedUser;

    public UserListFragment() {
        // Required empty public constructor
        userList = new ArrayList<>();
        loggedUser = CarSingleton.getInstance().getUser();
    }

    public List<User> getUserList() {
        return userList;
    }

    public void setUserList(List<User> userList) {
        this.userList = userList;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.i(TAG, "onCreateView: Creating UserList Frag");
        View view = inflater.inflate(R.layout.fragment_user_list, container, false);;
        ListView listView = (ListView) view.findViewById(R.id.lv_UserList);
        Log.i(TAG, "onCreateView: List View "+listView);

        UserAdapter userAdapter = new UserAdapter((ArrayList<User>)userList , getActivity().getApplicationContext());
        listView.setAdapter(userAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                TextView tv_Name = (TextView) view.findViewById(R.id.tv_userName);
                TextView tv_Gender = (TextView) view.findViewById(R.id.tv_userGender);
                TextView tv_Location = (TextView) view.findViewById(R.id.tv_userLocation);
                TextView tv_Phone = (TextView) view.findViewById(R.id.tv_userPhone);
                TextView tv_email = (TextView) view.findViewById(R.id.tv_userEmail);
                ImageView iv_Delete= (ImageView) view.findViewById(R.id.iv_userDelete);

                if (tv_Gender.getVisibility() == View.GONE){
                    tv_Gender.setVisibility(View.VISIBLE);
                    tv_Location.setVisibility(View.VISIBLE);
                    tv_Phone.setVisibility(View.VISIBLE);
                    tv_email.setVisibility(View.VISIBLE);
                    iv_Delete.setVisibility(View.VISIBLE);
                }
                else if (tv_Gender.getVisibility() == View.VISIBLE){
                    tv_Gender.setVisibility(View.GONE);
                    tv_Location.setVisibility(View.GONE);
                    tv_Phone.setVisibility(View.GONE);
                    tv_email.setVisibility(View.GONE);
                    iv_Delete.setVisibility(View.GONE);
                }
            }
        });

        return view;
    }

}
