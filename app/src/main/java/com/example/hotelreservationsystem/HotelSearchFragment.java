package com.example.hotelreservationsystem;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.hotelreservationsystem.util.Constants;

import java.util.Calendar;
import java.util.Date;


public class HotelSearchFragment extends Fragment {
    private EditText guestNameEditText;
    private EditText nGuestsEditText;
    private EditText checkInDateEditText;
    private EditText checkOutDateEditText;

    private Calendar checkInDateCalendar;
    private Calendar checkOutDateCalendar;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.hotel_search, container, false);
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        guestNameEditText = view.findViewById(R.id.guest_name_edit_text);
        nGuestsEditText = view.findViewById(R.id.n_guests_edit_text);

        checkInDateEditText = view.findViewById(R.id.check_in_date_edit_text);
        checkInDateEditText.setOnTouchListener((editTextView, event) -> {
            hideKeyboard(editTextView);

            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    final Calendar calendar = Calendar.getInstance();
                    int defaultYear = calendar.get(Calendar.YEAR);
                    int defaultMonth = calendar.get(Calendar.MONTH);
                    int defaultDay = calendar.get(Calendar.DAY_OF_MONTH);

                    DatePickerDialog datePickerDialog = new DatePickerDialog(
                            requireActivity(),
                            (datePickerView, year, month, dayOfMonth) -> {
                                checkInDateEditText.setText(getString(R.string.date_format, dayOfMonth, month + 1, year));

                                checkInDateCalendar = Calendar.getInstance();
                                checkInDateCalendar.set(year, month, dayOfMonth, 0, 0, 0);
                            }, defaultYear, defaultMonth, defaultDay);

                    datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis()); // set the minimum date to today
                    if (!checkOutDateEditText.getText().toString().trim().isEmpty()) {
                        datePickerDialog.getDatePicker().setMaxDate(checkOutDateCalendar.getTimeInMillis()); // set the maximum date to the check-out date
                    }

                    datePickerDialog.show();
                    break;
                case MotionEvent.ACTION_UP:
                    break;
            }
            return true;
        });

        checkOutDateEditText = view.findViewById(R.id.check_out_date_edit_text);
        checkOutDateEditText.setOnTouchListener((editTextView, event) -> {
            hideKeyboard(editTextView);

            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    final Calendar calendar = Calendar.getInstance();
                    int defaultYear = calendar.get(Calendar.YEAR);
                    int defaultMonth = calendar.get(Calendar.MONTH);
                    int defaultDay = calendar.get(Calendar.DAY_OF_MONTH);

                    DatePickerDialog datePickerDialog = new DatePickerDialog(
                            requireActivity(),
                            (datePickerView, year, month, dayOfMonth) -> {
                                checkOutDateEditText.setText(getString(R.string.date_format, dayOfMonth, month + 1, year));

                                checkOutDateCalendar = Calendar.getInstance();
                                checkOutDateCalendar.set(year, month, dayOfMonth, 23, 59, 59);
                            }, defaultYear, defaultMonth, defaultDay);

                    if (checkInDateCalendar != null && checkInDateCalendar.getTimeInMillis() > System.currentTimeMillis()) {
                        datePickerDialog.getDatePicker().setMinDate(checkInDateCalendar.getTimeInMillis()); // set the minimum date to the check-in date
                    } else {
                        datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis()); // set the minimum date to today
                    }

                    datePickerDialog.show();
                    break;
                case MotionEvent.ACTION_UP:
                    break;
            }
            return true;
        });

        Button searchButton = view.findViewById(R.id.search_button);
        searchButton.setOnClickListener(v -> {
            // validate input fields
            if (!validateInputFields()) {
                return;
            }

            String guestName = guestNameEditText.getText().toString();
            int nGuests = Integer.parseInt(nGuestsEditText.getText().toString());
            Date checkInDate = checkInDateCalendar.getTime();
            Date checkOutDate = checkOutDateCalendar.getTime();

            // save the input fields to shared preferences
            if (getActivity() != null) {
                SharedPreferences sharedPreferences = getActivity().getSharedPreferences(Constants.SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString(Constants.GUEST_NAME_KEY, guestName);
                editor.putInt(Constants.N_GUESTS_KEY, nGuests);
                editor.putLong(Constants.CHECK_IN_DATE_KEY, checkInDate.getTime());
                editor.putLong(Constants.CHECK_OUT_DATE_KEY, checkOutDate.getTime());
                editor.apply();
            }

            // create the hotel list fragment
            HotelListFragment hotelListFragment = new HotelListFragment();

            // start fragment transaction
            FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
            transaction.replace(R.id.frame_layout, hotelListFragment);
            transaction.addToBackStack(HotelSearchFragment.this.getClass().getName());
            transaction.commit();
        });
    }

    private boolean validateInputFields() {
        boolean isValid = true;

        String guestName = guestNameEditText.getText().toString();
        if (guestName.trim().isEmpty() || guestName.length() < 3) {
            guestNameEditText.setError(getString(R.string.guest_name_error));
            isValid = false;
        }

        int nGuests;
        try {
            nGuests = Integer.parseInt(nGuestsEditText.getText().toString());
            if (nGuests <= 0) {
                nGuestsEditText.setError(getString(R.string.n_guests_error));
                isValid = false;
            }
        } catch (NumberFormatException e) {
            nGuestsEditText.setError(getString(R.string.n_guests_error));
            isValid = false;
        }

        if (checkInDateCalendar == null || checkOutDateCalendar == null) {
            if (checkInDateCalendar == null) {
                checkInDateEditText.setError(getString(R.string.check_in_date_null_error));
            }
            if (checkOutDateCalendar == null) {
                checkOutDateEditText.setError(getString(R.string.check_out_date_null_error));
            }
            isValid = false;
        } else {
            Calendar calendar = Calendar.getInstance();
            calendar.set(Calendar.HOUR_OF_DAY, 0);
            calendar.set(Calendar.MINUTE, 0);
            calendar.set(Calendar.SECOND, 0);
            calendar.set(Calendar.MILLISECOND, 0);


            if (checkInDateCalendar.before(calendar)) {
                checkInDateEditText.setError(getString(R.string.check_in_before_today_error));
                Toast.makeText(getContext(), getString(R.string.check_in_before_today_error), Toast.LENGTH_SHORT).show();
                isValid = false;
            }

            if (checkOutDateCalendar.before(checkInDateCalendar)) {
                checkInDateEditText.setError(getString(R.string.check_out_before_check_in_error));
                checkOutDateEditText.setError(getString(R.string.check_out_before_check_in_error));
                Toast.makeText(getContext(), getString(R.string.check_out_before_check_in_error), Toast.LENGTH_SHORT).show();
                isValid = false;
            }
        }

        return isValid;
    }

    private void hideKeyboard(View view) {
        InputMethodManager inputMethodManager = (InputMethodManager) requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        if (inputMethodManager != null) {
            inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }
}
