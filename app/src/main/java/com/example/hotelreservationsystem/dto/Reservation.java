package com.example.hotelreservationsystem.dto;

import androidx.annotation.NonNull;

import java.util.List;

public class Reservation {

    private int hotel;
    private String check_in;
    private String check_out;
    private List<Guest> guest_list;

    public Reservation() {
    }

    public int getHotel() {
        return hotel;
    }

    public void setHotel(int hotel) {
        this.hotel = hotel;
    }

    public String getCheck_in() {
        return check_in;
    }

    public void setCheck_in(String check_in) {
        this.check_in = check_in;
    }

    public String getCheck_out() {
        return check_out;
    }

    public void setCheck_out(String check_out) {
        this.check_out = check_out;
    }

    public List<Guest> getGuest_list() {
        return guest_list;
    }

    public void setGuest_list(List<Guest> guest_list) {
        this.guest_list = guest_list;
    }

    @NonNull
    @Override
    public String toString() {
        return "Reservation{" +
                "hotel=" + hotel +
                ", check_in='" + check_in + '\'' +
                ", check_out='" + check_out + '\'' +
                ", guest_list=" + guest_list +
                '}';
    }
}
