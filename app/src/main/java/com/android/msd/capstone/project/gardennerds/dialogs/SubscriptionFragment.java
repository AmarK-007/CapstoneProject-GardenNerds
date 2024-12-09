package com.android.msd.capstone.project.gardennerds.dialogs;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.DialogFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.msd.capstone.project.gardennerds.R;
import com.android.msd.capstone.project.gardennerds.databinding.FragmentSubscriptionBinding;

/**
 * DialogFragment for handling subscription options.
 */
public class SubscriptionFragment extends DialogFragment {

    private FragmentSubscriptionBinding binding;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentSubscriptionBinding.inflate(inflater, container, false);

        // Set click listener for monthly subscription option
        binding.layMonthly.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.layYearly.setBackground(ContextCompat.getDrawable(requireContext(), R.drawable.outline_background));
                binding.layMonthly.setBackground(ContextCompat.getDrawable(requireContext(), R.drawable.outline_background_selected));
            }
        });

        // Set click listener for yearly subscription option
        binding.layYearly.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.layMonthly.setBackground(ContextCompat.getDrawable(requireContext(), R.drawable.outline_background));
                binding.layYearly.setBackground(ContextCompat.getDrawable(requireContext(), R.drawable.outline_background_selected));
            }
        });

        // Set click listener for the buy now button
        binding.btnBuyNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String appPackageName = requireContext().getPackageName();
                try {
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + appPackageName)));
                } catch (android.content.ActivityNotFoundException anfe) {
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + appPackageName)));
                }
            }
        });

        return binding.getRoot();
    }
}