package com.example.hotelreservationsystem;

import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hotelreservationsystem.dto.Guest;
import com.example.hotelreservationsystem.dto.Reservation;
import com.example.hotelreservationsystem.service.HotelService;
import com.example.hotelreservationsystem.service.HotelServiceInterface;
import com.example.hotelreservationsystem.util.Constants;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ReservationFormFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.reservation_form, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if (getActivity() == null || getArguments() == null) {
            return;
        }

        int hotelId = getArguments().getInt(Constants.SELECTED_HOTEL_KEY, 0);

        SharedPreferences sharedPreferences = getActivity().getSharedPreferences(Constants.SHARED_PREFERENCES_NAME, 0);
        int nGuests = sharedPreferences.getInt(Constants.N_GUESTS_KEY, 0);
        Date checkInDate = new Date(sharedPreferences.getLong(Constants.CHECK_IN_DATE_KEY, 0));
        Date checkOutDate = new Date(sharedPreferences.getLong(Constants.CHECK_OUT_DATE_KEY, 0));

        String reservationInfo = HotelListFragment.getInfoText(getActivity());
        TextView infoTextView = view.findViewById(R.id.info_text_view);
        infoTextView.setTypeface(Typeface.MONOSPACE);
        infoTextView.setText(reservationInfo);

        List<Guest> guests = new ArrayList<>();
        for (int i = 0; i < nGuests; i++) {
            guests.add(new Guest());
        }

        RecyclerView guestListRecyclerView = view.findViewById(R.id.guest_list_recycler_view);
        guestListRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        GuestListAdapter guestListAdapter = new GuestListAdapter(guests);
        guestListRecyclerView.setAdapter(guestListAdapter);

        Button submitButton = view.findViewById(R.id.submit_button);
        submitButton.setOnClickListener(v -> {
            // validate guest list
            if (!validateGuestList(guestListRecyclerView)) {
                return;
            }

            // create reservation
            Reservation reservation = new Reservation();
            reservation.setHotel(hotelId);

            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
            reservation.setCheck_in(sdf.format(checkInDate));
            reservation.setCheck_out(sdf.format(checkOutDate));
            reservation.setGuest_list(guestListAdapter.getGuests(guestListRecyclerView));

            // send reservation to server
            HotelServiceInterface hotelServiceInterface = HotelService.getHotelServiceInterface();
            Call<Map<String, Integer>> call = hotelServiceInterface.createReservation(reservation);
            call.enqueue(new Callback<Map<String, Integer>>() {
                @Override
                public void onResponse(@NonNull Call<Map<String, Integer>> call, @NonNull Response<Map<String, Integer>> response) {
                    if (!response.isSuccessful()) {
                        Log.e("ReservationFormFragment", "onResponse: " + response.message());
                        return;
                    }

                    Map<String, Integer> body = response.body();

                    Integer reservationId = 0;
                    if (body != null && body.containsKey("confirmation_number")) {
                        reservationId = body.get("confirmation_number");
                    }

                    if (reservationId == null || reservationId == 0) {
                        Log.e("ReservationFormFragment", "onResponse: error getting reservation id from response: " + response.message());
                        return;
                    }

                    // create a new confirmation fragment
                    ConfirmationFragment confirmationFragment = new ConfirmationFragment();

                    // pass the reservation id to the confirmation fragment
                    Bundle bundle = new Bundle();
                    bundle.putInt(Constants.RESERVATION_ID_KEY, reservationId);
                    confirmationFragment.setArguments(bundle);

                    // replace the current fragment with the confirmation fragment
                    FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
                    transaction.replace(R.id.frame_layout, confirmationFragment);
                    transaction.addToBackStack(ReservationFormFragment.this.getClass().getName());
                    transaction.commit();
                }

                @Override
                public void onFailure(@NonNull Call<Map<String, Integer>> call, @NonNull Throwable t) {
                    Log.e("ReservationFormFragment", "onFailure: " + t.getMessage());
                }
            });
        });
    }

    private boolean validateGuestList(RecyclerView recyclerView) {
        boolean isValid = true;
        for (int i = 0; i < recyclerView.getChildCount(); i++) {
            RecyclerView.ViewHolder viewHolder = recyclerView.findViewHolderForAdapterPosition(i);
            if (viewHolder instanceof GuestListAdapter.GuestViewHolder) {
                GuestListAdapter.GuestViewHolder guestViewHolder = (GuestListAdapter.GuestViewHolder) viewHolder;

                // validate guest name (must be at least 3 characters long)
                String name = guestViewHolder.guestNameEditText.getText().toString();
                if (name.trim().isEmpty() || name.length() < 3) {
                    guestViewHolder.guestNameEditText.setError(getString(R.string.guest_name_error));
                    isValid = false;
                }

                // validate guest age (must be a number between 1 and 99)
                String ageString = guestViewHolder.guestAgeEditText.getText().toString();
                if (ageString.trim().isEmpty()) {
                    guestViewHolder.guestAgeEditText.setError(getString(R.string.guest_age_error));
                    isValid = false;
                } else {
                    try {
                        int age = Integer.parseInt(ageString);
                        if (age <= 0 || age > 99) {
                            guestViewHolder.guestAgeEditText.setError(getString(R.string.guest_age_error));
                            isValid = false;
                        }
                    } catch (NumberFormatException e) {
                        guestViewHolder.guestAgeEditText.setError(getString(R.string.guest_age_error));
                        isValid = false;
                    }
                }
            }
        }
        return isValid;
    }
}
