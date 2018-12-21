package com.rental.shaltal.carrental;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
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
import android.widget.FrameLayout;
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

public class AdminMainPage extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_main_page);
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

        showCustomers();
    }

    private void setNameAndEmailInDrawerHeader(View headerView) {
        User loggedInUser = CarSingleton.getInstance().getUser();
        if(loggedInUser != null){
            TextView nav_header_email = (TextView) headerView.findViewById(R.id.nav_header_email);
            TextView nav_header_name = (TextView) headerView.findViewById(R.id.nav_header_name);

            nav_header_email.setText(loggedInUser.getEmail());
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
        getMenuInflater().inflate(R.menu.admin_main_page, menu);
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

        if (id == R.id.nav_customers) {
            showCustomers();
        } else if (id == R.id.nav_admins) {
            showAdmins();
        } else if (id == R.id.nav_addAdmins) {
            showAddAdmin();
        } else if (id == R.id.nav_adminReserve) {
            showReservedCars();
        } else if (id == R.id.nav_logout) {
            goToLoginPage();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void showReservedCars() {
        clearFrags();

        DatabaseHelper databaseHelper = new DatabaseHelper(this);
        User loggedInUser = CarSingleton.getInstance().getUser();
        List<Car> reservedCars = new ArrayList<>();
        reservedCars.addAll(databaseHelper.getAllReservedCars());
        CarMenu carMenu = new CarMenu();
        carMenu.setCarList(reservedCars);
        carMenu.setMode(CarMenu.ADMIN_RESERVED_MODE);
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.frag_container , carMenu , "CarMenu");
        fragmentTransaction.commit();

        setToolbarTittle("Reserved Cars");
    }

    private void showAdmins() {
        clearFrags();

        DatabaseHelper databaseHelper = new DatabaseHelper(this);
        User loggedInUser = CarSingleton.getInstance().getUser();
        List<User> userList = databaseHelper.getAllUsersOtherThan(loggedInUser.getEmail());
        userList = userList.stream().filter(user -> user.isAdmin()).collect(Collectors.toList());
        UserListFragment userListFragment = new UserListFragment();
        userListFragment.setUserList(userList);
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.frag_container, userListFragment,"userListFragment");
        fragmentTransaction.commit();

        setToolbarTittle("Admin Accounts");
    }

    private void showAddAdmin() {
        clearFrags();
        AdminRegister adminRegister= new AdminRegister();
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.frag_container, adminRegister,"userListFragment");
        fragmentTransaction.commit();

        setToolbarTittle("New Admin");
    }

    private void showCustomers() {
        clearFrags();

        DatabaseHelper databaseHelper = new DatabaseHelper(this);
        User loggedInUser = CarSingleton.getInstance().getUser();
        List<User> userList = databaseHelper.getAllUsersOtherThan(loggedInUser.getEmail());
        userList = userList.stream().filter(user -> !user.isAdmin()).collect(Collectors.toList());
        UserListFragment userListFragment = new UserListFragment();
        userListFragment.setUserList(userList);
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.frag_container, userListFragment,"userListFragment");
        fragmentTransaction.commit();

        setToolbarTittle("Customers");
    }


    private void goToLoginPage(){
        SharedPrefHelper.removeLoggedIn(this);
        Intent intent = new Intent(AdminMainPage.this , LoginScreen.class);
        this.startActivity(intent);
    }

    private void clearFrags(){
        FrameLayout frag_container = (FrameLayout) findViewById(R.id.frag_container);
        frag_container.removeAllViews();
    }

    private void setToolbarTittle(String title){
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar2);
        toolbar.setTitle(title);
    }
}
