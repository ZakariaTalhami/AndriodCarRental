package com.rental.shaltal.carrental.models;

public class ReservedCar extends Car {

    private String reservedDate;
    private User user;
    public ReservedCar() {
    }

    public ReservedCar(String year, String make, String model, String distance, int price, boolean accidents, boolean offer, String reservedDate) {
        super( year,  make,  model,  distance,  price,  accidents,  offer);
        this.reservedDate = reservedDate;
    }

    public String getReservedDate() {
        return reservedDate;
    }

    public void setReservedDate(String reservedDate) {
        this.reservedDate = reservedDate;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "ReservedCar{" +
                super.toString()+
                "reservedDate='" + reservedDate + '\'' +
                '}';
    }
}
