package com.example.hotelreservationsystem;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hotelreservationsystem.dto.Hotel;
import com.example.hotelreservationsystem.util.Constants;

import java.util.List;
import java.util.Locale;

public class HotelListAdapter extends RecyclerView.Adapter<HotelListAdapter.HotelViewHolder> {

    private final Fragment fragment;
    private final List<Hotel> hotels;

    public HotelListAdapter(Fragment fragment, List<Hotel> hotels) {
        this.fragment = fragment;
        this.hotels = hotels;
    }

    @NonNull
    @Override
    public HotelViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.hotel_list_item, parent, false);
        return new HotelViewHolder(view);
    }

    @Override
    public void onBindViewHolder(HotelViewHolder holder, int position) {
        holder.hotel = hotels.get(position);
        holder.hotelNameTextView.setText(holder.hotel.getName());
        holder.hotelAddressTextView.setText(holder.hotel.getAddress());
        holder.hotelPriceTextView.setText(String.format(Locale.getDefault(), "$%.2f", holder.hotel.getPrice()));
        holder.hotelAvailabilityTextView.setText(holder.hotel.isAvailability() ? "Available" : "Not Available");

        // Set the view clickable only if hotel.availability is true
        holder.itemView.setClickable(holder.hotel.isAvailability());

        // Set the view color based on availability
        if (holder.hotel.isAvailability()) {
            holder.itemView.setBackgroundColor(ContextCompat.getColor(fragment.requireContext(), R.color.teal));
        } else {
            holder.itemView.setBackgroundColor(ContextCompat.getColor(fragment.requireContext(), R.color.light_gray));
        }
    }

    @Override
    public int getItemCount() {
        return hotels.size();
    }

    public class HotelViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        Hotel hotel;
        TextView hotelNameTextView;
        TextView hotelAddressTextView;
        TextView hotelPriceTextView;
        TextView hotelAvailabilityTextView;

        public HotelViewHolder(View view) {
            super(view);
            hotelNameTextView = view.findViewById(R.id.hotel_name_text_view);
            hotelAddressTextView = view.findViewById(R.id.hotel_address_text_view);
            hotelPriceTextView = view.findViewById(R.id.hotel_price_text_view);
            hotelAvailabilityTextView = view.findViewById(R.id.hotel_availability_text_view);

            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            // create a new reservation form fragment
            ReservationFormFragment reservationFormFragment = new ReservationFormFragment();

            // pass the selected hotel id to the reservation form fragment
            Bundle bundle = new Bundle();
            bundle.putParcelable(Constants.SELECTED_HOTEL_KEY, hotel);
            reservationFormFragment.setArguments(bundle);

            // replace the current fragment with the reservation form fragment
            FragmentTransaction transaction = fragment.getParentFragmentManager().beginTransaction();
            transaction.replace(R.id.frame_layout, reservationFormFragment);
            transaction.addToBackStack(fragment.getClass().getName());
            transaction.commit();
        }
    }
}
