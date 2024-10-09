package com.android.msd.capstone.project.gardennerds.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.android.msd.capstone.project.gardennerds.R;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AboutFragment} factory method to
 * create an instance of this fragment.
 */
public class AboutFragment extends Fragment {

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment AboutFragment.
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_about, container, false);
        return view;
    }
}