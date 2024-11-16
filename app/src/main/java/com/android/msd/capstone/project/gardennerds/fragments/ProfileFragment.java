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
import com.android.msd.capstone.project.gardennerds.databinding.FragmentProfileBinding;
import com.android.msd.capstone.project.gardennerds.databinding.UpdateAddressDialogBinding;


// ProfileFragment class that extends Fragment
public class ProfileFragment extends Fragment {

    private FragmentProfileBinding binding;
    // Declare variables
    private String selectedProvince;
    TextView firstName, lastName, email, addressLine1, addressLine2, city, postalCode, province;
    ImageButton editButton;

    // onCreateView method for creating the view of the fragment
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment using view binding
        binding = FragmentProfileBinding.inflate(inflater, container, false);
        View view = binding.getRoot();
        // Initialize TextViews and ImageButton
        firstName = binding.profileFirstName;
        lastName = binding.profileLastName;
        email = binding.profileEmail;
        addressLine1 = binding.profileAddressFirstLine;
        addressLine2 = binding.profileAddressSecondLine;
        city = binding.profileAddressCity;
        postalCode = binding.profileAddressPostal;
        province = binding.profileAddressProvince;
        editButton = binding.updateAddress;

        // Set onClickListener for the editButton
        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Create a new dialog
                Dialog updateDialog = new Dialog(getContext());
                // Use view binding for the dialog's layout
                UpdateAddressDialogBinding dialogBinding = UpdateAddressDialogBinding.inflate(getLayoutInflater());
                updateDialog.setContentView(dialogBinding.getRoot());


                String[] provinces = getResources().getStringArray(R.array.canadian_provinces);
                CustomSpinnerAdapter adapter = new CustomSpinnerAdapter(requireContext(), provinces);
//                binding.provinceSpinner.setAdapter(adapter);


                // Initialize EditTexts, Spinner, and Button
                EditText updatedAddress1 = dialogBinding.updateAddress1;
                EditText updatedAddress2 = dialogBinding.updateAddress2;
                EditText updatedCity = dialogBinding.updateCity;
                EditText updatedPostalCode = dialogBinding.updatePostalCode;
                Spinner updatedProvince = dialogBinding.updateProvinceSpinner;
                Button updateButton = dialogBinding.updateBtn;

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

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}