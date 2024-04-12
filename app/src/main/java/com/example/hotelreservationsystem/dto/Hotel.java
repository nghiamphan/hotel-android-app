package com.example.hotelreservationsystem.dto;


import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

public class Hotel implements Parcelable {

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

    protected Hotel(Parcel in) {
        id = in.readInt();
        name = in.readString();
        address = in.readString();
        price = in.readFloat();
        availability = in.readByte() != 0;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(name);
        dest.writeString(address);
        dest.writeFloat(price);
        dest.writeByte((byte) (availability ? 1 : 0));
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
