package com.example.hotelreservationsystem;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.hotelreservationsystem.util.Constants;

public class ConfirmationFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.reservation_confirmation, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if (getActivity() == null || getArguments() == null) {
            return;
        }

        int reservationId = getArguments().getInt(Constants.RESERVATION_ID_KEY, 0);

        TextView confirmationTextView = view.findViewById(R.id.confirmation_text_view);
        confirmationTextView.setText(getString(R.string.confirmation, reservationId));
    }
}
