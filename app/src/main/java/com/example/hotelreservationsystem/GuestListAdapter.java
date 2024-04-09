package com.example.hotelreservationsystem;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hotelreservationsystem.dto.Guest;

import java.util.List;

public class GuestListAdapter extends RecyclerView.Adapter<GuestListAdapter.GuestViewHolder> {

    private final List<Guest> guests;

    public GuestListAdapter(List<Guest> guests) {
        this.guests = guests;
    }

    @NonNull
    @Override
    public GuestViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.guest_list_item, parent, false);
        return new GuestViewHolder(view);
    }

    @Override
    public void onBindViewHolder(GuestViewHolder holder, int position) {
        Guest guest = guests.get(position);
        holder.guestNameEditText.setText(guest.getGuest_name());
        if (guest.getAge() > 0)
            holder.guestAgeEditText.setText(String.valueOf(guest.getAge()));

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                holder.itemView.getContext(),
                R.array.gender_array,
                android.R.layout.simple_spinner_item
        );
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        holder.guestGenderSpinner.setAdapter(adapter);
        holder.guestGenderSpinner.setSelection(adapter.getPosition(guest.getGender()));
    }

    @Override
    public int getItemCount() {
        return guests.size();
    }

    public List<Guest> getGuests(RecyclerView recyclerView) {
        // Get the updated guest information from the RecyclerView and update the guest list
        for (int i = 0; i < guests.size(); i++) {
            RecyclerView.ViewHolder viewHolder = recyclerView.findViewHolderForAdapterPosition(i);
            if (viewHolder instanceof GuestViewHolder) {
                GuestViewHolder guestViewHolder = (GuestViewHolder) viewHolder;
                String name = guestViewHolder.guestNameEditText.getText().toString();
                int age = Integer.parseInt(guestViewHolder.guestAgeEditText.getText().toString());
                String gender = guestViewHolder.guestGenderSpinner.getSelectedItem().toString();

                Guest guest = guests.get(i);
                guest.setGuest_name(name);
                guest.setAge(age);
                guest.setGender(gender);
            }
        }
        return guests;
    }

    public static class GuestViewHolder extends RecyclerView.ViewHolder {

        EditText guestNameEditText;
        EditText guestAgeEditText;
        Spinner guestGenderSpinner;

        public GuestViewHolder(View view) {
            super(view);
            guestNameEditText = view.findViewById(R.id.guest_name_edit_text);
            guestAgeEditText = view.findViewById(R.id.guest_age_edit_text);
            guestGenderSpinner = view.findViewById(R.id.guest_gender_spinner);
            ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                    view.getContext(), R.array.gender_array, android.R.layout.simple_spinner_item
            );
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            guestGenderSpinner.setAdapter(adapter);
        }
    }
}
