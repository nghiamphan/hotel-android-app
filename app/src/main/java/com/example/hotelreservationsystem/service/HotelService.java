package com.example.hotelreservationsystem.service;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class HotelService {
    //    private static final String BASE_URL = "http://10.0.2.2:8000";
    private static final String BASE_URL = "http://hotel-backend-django-dev.us-west-2.elasticbeanstalk.com";

    private static Retrofit retrofit;

    public static HotelServiceInterface getHotelServiceInterface() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }

        return retrofit.create(HotelServiceInterface.class);
    }
}
