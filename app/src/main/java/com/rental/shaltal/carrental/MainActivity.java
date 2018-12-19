package com.rental.shaltal.carrental;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.rental.shaltal.carrental.constants.GenderEnum;
import static com.rental.shaltal.carrental.constants.RestConstants.*;
import com.rental.shaltal.carrental.helpers.ConnectionAsyncTask;
import com.rental.shaltal.carrental.helpers.DatabaseHelper;
import com.rental.shaltal.carrental.helpers.GetCarsAsyncTask;
import com.rental.shaltal.carrental.helpers.Md5;
import com.rental.shaltal.carrental.helpers.SharedPrefHelper;
import com.rental.shaltal.carrental.models.Car;
import com.rental.shaltal.carrental.models.User;


import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        SharedPrefHelper.removeLoggedIn(this);
        Button btnRestTest = (Button) findViewById(R.id.bt_TestRestConnection);
        btnRestTest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TestRestConnection(v);
            }
        });
        DatabaseHelper databaseHelper = new DatabaseHelper(this);
        databaseHelper.insertUser(new User("Shal",
                "Tal",
                GenderEnum.OTHER,
                Md5.md5("ShalTal&21"),
                "Palestine",
                "Ramallah",
                "911",
                "admin@shaltal.com",
                true,
                ""
        ));
        databaseHelper.insertUser(new User("Shal",
                "Tal",
                GenderEnum.OTHER,
                Md5.md5("ShalTal&21"),
                "Palestine",
                "Ramallah",
                "911",
                "admin2@shaltal.com",
                true,
                ""
        ));

    }

    private void TestRestConnection(View v) {
        ConnectionAsyncTask connectionAsyncTask = new ConnectionAsyncTask(MainActivity.this );
        connectionAsyncTask.execute(CAR_URL);
    }



    @Override
    protected void onResume() {
        super.onResume();

//        String key = "classMe";
//        Log.i(TAG, "onResume: ENC:> "+ Md5.md5(key));

//        ConnectionAsyncTask connectionAsyncTask = new ConnectionAsyncTask(MainActivity.this);
//        connectionAsyncTask.execute("http://www.mocky.io/v2/5bfea5963100006300bb4d9a");
//
//        GetCarsAsyncTask getCarsAsyncTask = new GetCarsAsyncTask(MainActivity.this);
//        getCarsAsyncTask.execute("http://www.mocky.io/v2/5bfea5963100006300bb4d9a");
            DatabaseHelper databaseHelper = new DatabaseHelper(this);
          databaseHelper.insertUser(new User("how",
                    "how",
                    GenderEnum.OTHER,
                    Md5.md5("how&21"),
                    "how",
                    "how",
                    "how",
                    "how",
                    false,
                    ""
            ));

          databaseHelper.updateUser("how" ,new User("how2",
                  "how2",
                  GenderEnum.OTHER,
                  Md5.md5("how&21"),
                  "how",
                  "how",
                  "how2",
                  "how2",
                  false,
                  ""
          ));

//        User user = databaseHelper.getUser("how");
//        Log.i("Main", "onResume: Returned User "+user);

//        boolean x = databaseHelper.insertUser(new User("how","how", GenderEnum.MALE,"how","how","how","how","how","how"));
//        Log.i("Fofo", "onResume: x = "+x);
//
//        user = databaseHelper.getUser("how");
//        Log.i("Main", "onResume: Returned User "+user);


//        Car car = databaseHelper.getCar(3);
//        Log.i("Main", "onResume: Returned car "+car);
//
//        int y = databaseHelper.insertCar(new Car("how2","how2","how","how",200,false,true));
//        Log.i("Fofo", "onResume: x = "+y);
//
//        car = databaseHelper.getCar(y);
//        Log.i("Main", "onResume: Returned car "+car);

//        boolean z = databaseHelper.insertReservedCar(user, new Car("res4","how2","how","how",200,false,true));
//        Log.i("Fofo", "onResume: z = "+z);
//        z = databaseHelper.insertReservedCar(user, new Car("res2","how2","how","how",200,false,true));
//        Log.i("Fofo", "onResume: z = "+z);
//        z = databaseHelper.insertReservedCar(user, new Car("res3","how2","how","how",200,false,true));
//        Log.i("Fofo", "onResume: z = "+z);
//
//        List<Car> carList = databaseHelper.getReservedCars(user);
//        Log.i("", "onResume: Reserved Cars :> "+carList);


//        int id = databaseHelper.getCarID(car);
//        Log.e("", "onResume: The ID = "+id );


//        boolean z = databaseHelper.insertFavoriteCar(user, new Car("res2","how2","how","how",200,false,true));
//        Log.i("Fofo", "onResume: z = "+z);
//        z = databaseHelper.insertFavoriteCar(user, new Car("res3","how2","how","how",200,false,true));
//        Log.i("Fofo", "onResume: z = "+z);
//
//
//        List<Car> carList = databaseHelper.getFavoriteCars(user);
//        Log.i("", "onResume: Reserved Cars :> "+carList);
//
//        z = databaseHelper.deleteFavoriteCar(user, new Car("res8","how2","how","how",200,false,true));
//        Log.i("Fofo", "onResume: z = "+z);
//
//        carList = databaseHelper.getFavoriteCars(user);
//        Log.i("", "onResume: Reserved Cars :> "+carList);


    }
}
