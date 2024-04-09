package com.example.hotelreservationsystem.dto;

import androidx.annotation.NonNull;

public class Guest {
    private String guest_name;
    private int age;
    private String gender;

    public Guest() {
    }

    public Guest(String name, int age, String gender) {
        this.guest_name = name;
        this.age = age;
        this.gender = gender;
    }

    public String getGuest_name() {
        return guest_name;
    }

    public void setGuest_name(String guest_name) {
        this.guest_name = guest_name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    @NonNull
    @Override
    public String toString() {
        return "Guest{" +
                "name='" + guest_name + '\'' +
                ", age=" + age +
                ", gender='" + gender + '\'' +
                '}';
    }
}
