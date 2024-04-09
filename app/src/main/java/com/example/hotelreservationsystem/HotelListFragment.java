package com.example.hotelreservationsystem;

import android.app.Activity;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hotelreservationsystem.dto.Hotel;
import com.example.hotelreservationsystem.service.HotelService;
import com.example.hotelreservationsystem.service.HotelServiceInterface;
import com.example.hotelreservationsystem.util.Constants;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HotelListFragment extends Fragment {

    public static String getInfoText(Activity activity) {
        SharedPreferences sharedPreferences = activity.getSharedPreferences(Constants.SHARED_PREFERENCES_NAME, 0);
        String guestName = sharedPreferences.getString(Constants.GUEST_NAME_KEY, "");
        int nGuests = sharedPreferences.getInt(Constants.N_GUESTS_KEY, 0);
        Date checkInDate = new Date(sharedPreferences.getLong(Constants.CHECK_IN_DATE_KEY, 0));
        Date checkOutDate = new Date(sharedPreferences.getLong(Constants.CHECK_OUT_DATE_KEY, 0));

        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
        String formattedCheckInDate = sdf.format(checkInDate);
        String formattedCheckOutDate = sdf.format(checkOutDate);

        String guestNameLabel = String.format("%-18s", "Guest Name:");
        String nGuestsLabel = String.format("%-18s", "Number of Guests:");
        String checkInDateLabel = String.format("%-18s", "Check-in Date:");
        String checkOutDateLabel = String.format("%-18s", "Check-out Date:");

        return guestNameLabel + guestName + "\n" +
                nGuestsLabel + nGuests + "\n" +
                checkInDateLabel + formattedCheckInDate + "\n" +
                checkOutDateLabel + formattedCheckOutDate;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.hotel_list, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if (getActivity() == null) {
            return;
        }

        String reservationInfo = getInfoText(getActivity());
        TextView infoTextView = view.findViewById(R.id.info_text_view);
        infoTextView.setTypeface(Typeface.MONOSPACE);
        infoTextView.setText(reservationInfo);

        RecyclerView hotelListRecyclerView = view.findViewById(R.id.hotel_list_recycler_view);
        hotelListRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        HotelServiceInterface hotelServiceInterface = HotelService.getHotelServiceInterface();
        hotelServiceInterface.getHotels().enqueue(new Callback<List<Hotel>>() {
            @Override
            public void onResponse(@NonNull Call<List<Hotel>> call, @NonNull Response<List<Hotel>> response) {
                if (!response.isSuccessful()) {
                    Log.e("HotelListFragment", "onResponse: " + response.message());
                    return;
                }
                
                List<Hotel> hotels = response.body();
                HotelListAdapter hotelListAdapter = new HotelListAdapter(HotelListFragment.this, hotels);
                hotelListRecyclerView.setAdapter(hotelListAdapter);
            }

            @Override
            public void onFailure(@NonNull Call<List<Hotel>> call, @NonNull Throwable t) {
                Log.e("HotelListFragment", "onFailure: " + t.getMessage());
            }
        });
    }
}
