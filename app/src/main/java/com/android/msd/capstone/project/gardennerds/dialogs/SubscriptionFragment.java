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


public class SubscriptionFragment extends DialogFragment {

  FragmentSubscriptionBinding binding;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentSubscriptionBinding.inflate(inflater,container,false);
        // Inflate the layout for this fragment



        binding.layMonthly.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        binding.layYearly.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.layMonthly.setBackground(ContextCompat.getDrawable(requireContext(),R.drawable.outline_background));
                binding.layYearly.setBackground(ContextCompat.getDrawable(requireContext(),R.drawable.outline_background_selected));
            }
        });

        binding.layMonthly.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.layYearly.setBackground(ContextCompat.getDrawable(requireContext(),R.drawable.outline_background));
                binding.layMonthly.setBackground(ContextCompat.getDrawable(requireContext(),R.drawable.outline_background_selected));

            }
        });

        binding.btnBuyNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String appPackageName = requireContext().getPackageName(); // getPackageName() from Context or Activity object
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