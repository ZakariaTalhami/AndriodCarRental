package com.rental.shaltal.carrental.singleton;

import com.rental.shaltal.carrental.models.Car;
import com.rental.shaltal.carrental.models.User;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CarSingleton {

    private static CarSingleton carSingleton = new CarSingleton();
    private User user;
    private List<Car> carList;

    private CarSingleton() {
        this.carList = new ArrayList<>();
    }

    public static CarSingleton getInstance() {
        return carSingleton;
    }

    public List<Car> getCarList(){
        return this.carList;
    }

    public void setCarList(List<Car> cars){
        this.carList.addAll(cars);
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
