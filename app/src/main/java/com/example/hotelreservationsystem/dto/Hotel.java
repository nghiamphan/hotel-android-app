package com.example.hotelreservationsystem.dto;


import androidx.annotation.NonNull;

public class Hotel {

    private int id;
    private String name;
    private String address;
    private float price;
    private boolean availability;

    public Hotel() {
    }

    public Hotel(int id, String name, String address, float price, boolean availability) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.price = price;
        this.availability = availability;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public boolean isAvailability() {
        return availability;
    }

    public void setAvailability(boolean availability) {
        this.availability = availability;
    }

    @NonNull
    @Override
    public String toString() {
        return "Hotel{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", address='" + address + '\'' +
                ", price=" + price +
                ", availability=" + availability +
                '}';
    }
}
