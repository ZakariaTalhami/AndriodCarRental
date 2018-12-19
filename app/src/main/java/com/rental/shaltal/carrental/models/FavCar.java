package com.rental.shaltal.carrental.models;

public class FavCar extends Car {
    private User user;

    public FavCar() {
    }

    public FavCar(String year, String make, String model, String distance, int price, boolean accidents, boolean offer) {
        super( year,  make,  model,  distance,  price,  accidents,  offer);
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "FavCar{" +
                super.toString()+
                "user=" + user +
                '}';
    }
}
