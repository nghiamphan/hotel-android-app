package com.example.hotelreservationsystem;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hotelreservationsystem.dto.Hotel;
import com.example.hotelreservationsystem.util.Constants;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class HotelListFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.hotel_list, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Bundle bundle = getArguments();

        TextView infoTextView = view.findViewById(R.id.info_text_view);
        if (bundle != null) {
            String guestName = bundle.getString(Constants.GUEST_NAME_KEY);
            int nGuests = bundle.getInt(Constants.N_GUESTS_KEY);
            Serializable checkInDate = bundle.getSerializable(Constants.CHECK_IN_DATE_KEY);
            Serializable checkOutDate = bundle.getSerializable(Constants.CHECK_OUT_DATE_KEY);

            SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
            String checkInDateString = dateFormat.format(checkInDate);
            String checkOutDateString = dateFormat.format(checkOutDate);
            
            infoTextView.setText(getString(R.string.reservation_info, guestName, nGuests, checkInDateString, checkOutDateString));
        }


        RecyclerView hotelListRecyclerView = view.findViewById(R.id.hotel_list_recycler_view);
        hotelListRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        List<Hotel> hotels = getHotels();

        HotelListAdapter hotelListAdapter = new HotelListAdapter(hotels);
        hotelListRecyclerView.setAdapter(hotelListAdapter);
    }

    public List<Hotel> getHotels() {
        List<Hotel> hotels = new ArrayList<>();
        hotels.add(new Hotel("Starry Summit Chalet", 150, true));
        hotels.add(new Hotel("Driftwood Shores Inn", 220, false));
        hotels.add(new Hotel("The Clocktower Courtyard", 180, true));
        hotels.add(new Hotel("Whispering Pines Resort", 300, true));
        hotels.add(new Hotel("Hidden Harbor Hideaway", 120, true));
        hotels.add(new Hotel("Urban Oasis Rooftop", 250, false));
        hotels.add(new Hotel("The Dragonfly Sanctuary", 110, true));
        hotels.add(new Hotel("Moonlight Cove Bungalows", 175, true));
        hotels.add(new Hotel("The Alchemist's Loft", 210, false));
        hotels.add(new Hotel("Grand Bison Ranch", 400, true));
        hotels.add(new Hotel("Sunset Serenity Villas", 350, true));
        hotels.add(new Hotel("The Wandering Bard's Hostel", 80, true));
        hotels.add(new Hotel("Cloudtop Canopy Suites", 280, true));
        hotels.add(new Hotel("The Timeworn Tapestry", 130, false));
        hotels.add(new Hotel("Salty Siren's Sea Shack", 160, false));
        hotels.add(new Hotel("Northern Lights Lodge", 240, true));
        hotels.add(new Hotel("The Gilded Griffin", 320, true));
        hotels.add(new Hotel("Whispering Canyon Cabins", 190, true));
        hotels.add(new Hotel("The Bibliophile's Nook", 140, true));
        hotels.add(new Hotel("Tranquil Turtle Cove", 200, false));
        return hotels;
    }
}
