package com.android.msd.capstone.project.gardennerds.fragments;
// Import necessary libraries and packages

import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.android.msd.capstone.project.gardennerds.R;
import com.android.msd.capstone.project.gardennerds.adapters.CustomSpinnerAdapter;


// ProfileFragment class that extends Fragment
public class ProfileFragment extends Fragment {

    // Declare variables
    private String selectedProvince;
    TextView firstName, lastName, email, addressLine1, addressLine2, city, postalCode, province;
    ImageButton editButton;

    // onCreateView method for creating the view of the fragment
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        // Initialize TextViews and ImageButton
        firstName = view.findViewById(R.id.profile_firstName);
        lastName = view.findViewById(R.id.profile_lastName);
        email = view.findViewById(R.id.profile_email);
        addressLine1 = view.findViewById(R.id.profile_address_firstLine);
        addressLine2 = view.findViewById(R.id.profile_address_secondLine);
        city = view.findViewById(R.id.profile_address_city);
        postalCode = view.findViewById(R.id.profile_address_postal);
        province = view.findViewById(R.id.profile_address_province);
        editButton = view.findViewById(R.id.update_address);

        // Set onClickListener for the editButton
        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Create a new dialog
                Dialog updateDialog = new Dialog(getContext());
                updateDialog.setContentView(R.layout.update_address_dialog);


                String[] provinces = getResources().getStringArray(R.array.canadian_provinces);
                CustomSpinnerAdapter adapter = new CustomSpinnerAdapter(requireContext(), provinces);
//                binding.provinceSpinner.setAdapter(adapter);


                // Initialize EditTexts, Spinner, and Button
                EditText updatedAddress1 = updateDialog.findViewById(R.id.update_address1);
                EditText updatedAddress2 = updateDialog.findViewById(R.id.update_address2);
                EditText updatedCity = updateDialog.findViewById(R.id.update_city);
                EditText updatedPostalCode = updateDialog.findViewById(R.id.update_postalCode);
                Spinner updatedProvince = updateDialog.findViewById(R.id.update_provinceSpinner);
                Button updateButton = updateDialog.findViewById(R.id.update_btn);

                // Set the adapter for the Spinner
                updatedProvince.setAdapter(adapter);

                // Set OnItemSelectedListener for the Spinner
                updatedProvince.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        // Update the selectedProvince when an item is selected
                        selectedProvince = parent.getItemAtPosition(position).toString();
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {
                        // Implementation here...
                    }
                });

                // Set onClickListener for the updateButton
                updateButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // Update the TextViews with the new values and dismiss the dialog
                        addressLine1.setText(updatedAddress1.getText().toString());
                        addressLine2.setText(updatedAddress2.getText().toString());
                        city.setText(updatedCity.getText().toString());
                        postalCode.setText(updatedPostalCode.getText().toString());
                        province.setText(selectedProvince);
                        updateDialog.dismiss();
                    }
                });

                // Show the dialog
                updateDialog.show();
            }
        });

        // Return the root view
        return view;
    }
}