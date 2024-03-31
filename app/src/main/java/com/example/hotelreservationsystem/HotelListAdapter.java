package com.example.hotelreservationsystem;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hotelreservationsystem.dto.Hotel;

import java.util.List;
import java.util.Locale;

public class HotelListAdapter extends RecyclerView.Adapter<HotelListAdapter.HotelViewHolder> {

    private final List<Hotel> hotels;

    public HotelListAdapter(List<Hotel> hotels) {
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
        Hotel hotel = hotels.get(position);
        holder.hotelNameTextView.setText(hotel.getName());
        holder.hotelPriceTextView.setText(String.format(Locale.US, "$%.2f", hotel.getPrice()));
        holder.hotelAvailabilityTextView.setText(hotel.isAvailability() ? "Available" : "Not Available");
    }

    @Override
    public int getItemCount() {
        return hotels.size();
    }

    public static class HotelViewHolder extends RecyclerView.ViewHolder {
        TextView hotelNameTextView;
        TextView hotelPriceTextView;
        TextView hotelAvailabilityTextView;

        public HotelViewHolder(View view) {
            super(view);
            hotelNameTextView = view.findViewById(R.id.hotel_name_text_view);
            hotelPriceTextView = view.findViewById(R.id.hotel_price_text_view);
            hotelAvailabilityTextView = view.findViewById(R.id.hotel_availability_text_view);
        }
    }
}
