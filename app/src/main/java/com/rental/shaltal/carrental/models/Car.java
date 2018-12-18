package com.rental.shaltal.carrental.models;

public class
Car {
    private String year;
    private String make;
    private String model;
//    Distance as String ????
    private String distance;
    private int price;
    private boolean accidents;
    private boolean offer;
    private int id;


    public Car() {
    }

    public Car(String year, String make, String model, String distance, int price, boolean accidents, boolean offer) {
        this.year = year;
        this.make = make;
        this.model = model;
        this.distance = distance;
        this.price = price;
        this.accidents = accidents;
        this.offer = offer;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getMake() {
        return make;
    }

    public void setMake(String make) {
        this.make = make;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getDistance() {
        return distance;
    }

    public void setDistance(String distance) {
        this.distance = distance;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public boolean isAccidents() {
        return accidents;
    }

    public void setAccidents(boolean accidents) {
        this.accidents = accidents;
    }

    public boolean isOffer() {
        return offer;
    }

    public void setOffer(boolean offer) {
        this.offer = offer;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Car{" +"\n"+
                "year='" + year + '\'' +"\n"+
                ", make='" + make + '\'' +"\n"+
                ", model='" + model + '\'' +"\n"+
                ", Distance='" + distance + '\'' +"\n"+
                ", price=" + price +"\n"+
                ", accidents=" + accidents +"\n"+
                ", offer=" + offer +"\n"+
                '}'+"\n";
    }
}
