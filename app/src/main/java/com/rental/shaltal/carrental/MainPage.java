package com.rental.shaltal.carrental;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.rental.shaltal.carrental.helpers.DatabaseHelper;
import com.rental.shaltal.carrental.helpers.SharedPrefHelper;
import com.rental.shaltal.carrental.models.Car;
import com.rental.shaltal.carrental.models.ReservedCar;
import com.rental.shaltal.carrental.models.User;
import com.rental.shaltal.carrental.singleton.CarSingleton;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class MainPage extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private static final String TAG = "MainPage";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_page);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar2);
        setSupportActionBar(toolbar);


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close){
            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerClosed(drawerView);
                setNameAndEmailInDrawerHeader(drawerView);

                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }

        };
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        
        showHome();
    }

    private void setNameAndEmailInDrawerHeader(View headerView) {




        User loggedInUser = CarSingleton.getInstance().getUser();
        if(loggedInUser != null){
            Log.i(TAG, "setNameAndEmailInDrawerHeader: Changing the names in the menu");
            TextView nav_header_eamil = (TextView) headerView.findViewById(R.id.nav_header_email);
            TextView nav_header_name = (TextView) headerView.findViewById(R.id.nav_header_name);

            nav_header_eamil.setText(loggedInUser.getEmail());
            nav_header_name.setText(loggedInUser.getFirstName()+" "+ loggedInUser.getLastName());
        }
    }



    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main_page, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_home) {
            // Handle the camera action
            showHome();
        } else if (id == R.id.nav_car) {
            showCarMenu();
        } else if (id == R.id.nav_reservations) {
            showReservedCars();
        } else if (id == R.id.nav_favorites) {
            showFavCars();
        } else if (id == R.id.nav_offers) {
            showSpecialOffer();
        } else if (id == R.id.nav_Profile) {
            showProfile();
        } else if (id == R.id.nav_callus) {
            showContactPage();
        } else if (id == R.id.nav_logout) {
            goToLoginPage();
        }


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void showHome() {
        clearFrags();

        HomePage_Fragment homePage = new HomePage_Fragment();
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.frag_container, homePage,"HomePage");
        fragmentTransaction.commit();
    }

    private void showProfile() {
    }

    private void showReservedCars() {
        clearFrags();

        DatabaseHelper databaseHelper = new DatabaseHelper(this);
        List<Car> reservedCars = new ArrayList<>();
        reservedCars.addAll(databaseHelper.getReservedCars(CarSingleton.getInstance().getUser()));
        Log.i(TAG, "showReservedCars: Loaded "+reservedCars.size()+" Reserved Cars");
        CarMenu carMenu = new CarMenu();
        carMenu.setCarList(reservedCars);
        carMenu.setMode(CarMenu.RESERVED_MODE);
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.frag_container , carMenu , "CarMenu");
        fragmentTransaction.commit();
    }

    private void showCarMenu() {
        clearFrags();

        CarMenu carMenu = new CarMenu();
        carMenu.setCarList(CarSingleton.getInstance().getCarList());
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.frag_container , carMenu , "CarMenu");
        fragmentTransaction.commit();


    }

    private void showFavCars() {
        clearFrags();

        DatabaseHelper databaseHelper = new DatabaseHelper(this);
        List<Car> favCars = new ArrayList<>();
        favCars.addAll(databaseHelper.getFavoriteCars(CarSingleton.getInstance().getUser()));
        Log.i(TAG, "showReservedCars: Loaded "+favCars.size()+" favorite Cars");
        CarMenu carMenu = new CarMenu();
        carMenu.setCarList(favCars);
        carMenu.setMode(CarMenu.FAVORED_MODE);
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.frag_container , carMenu , "CarMenu");
        fragmentTransaction.commit();
    }

    private void showSpecialOffer() {
        clearFrags();

        List<Car> offerCars = CarSingleton.getInstance().getCarList().stream()
                .filter(car -> car.isOffer())
                .collect(Collectors.toList());
        Log.i(TAG, "showSpecialOffer: Loaded "+offerCars.size()+" Cars on offer");
        CarMenu carMenu = new CarMenu();
        carMenu.setCarList(offerCars);
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.frag_container , carMenu , "CarMenu");
        fragmentTransaction.commit();

    }

    private void showContactPage() {
        clearFrags();

        ContactUsFragment ContactUs = new ContactUsFragment();
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.frag_container, ContactUs,"ContactUs");
        fragmentTransaction.commit();
    }

    private void goToLoginPage(){
        SharedPrefHelper.removeLoggedIn(this);
        Intent intent = new Intent(MainPage.this , LoginScreen.class);
        this.startActivity(intent);
    }

    private void clearFrags(){
        FrameLayout frag_container = (FrameLayout) findViewById(R.id.frag_container);
        frag_container.removeAllViews();
    }
}
