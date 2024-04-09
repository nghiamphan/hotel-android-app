package com.example.hotelreservationsystem.service;

import com.example.hotelreservationsystem.dto.Hotel;
import com.example.hotelreservationsystem.dto.Reservation;

import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface HotelServiceInterface {
    @GET("/api/hotels")
    Call<List<Hotel>> getHotels();

    @POST("/api/reservations/")
    Call<Map<String, Integer>> createReservation(@Body Reservation reservation);
}