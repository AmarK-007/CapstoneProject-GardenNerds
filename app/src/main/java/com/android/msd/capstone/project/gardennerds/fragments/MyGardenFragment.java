package com.android.msd.capstone.project.gardennerds.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.android.msd.capstone.project.gardennerds.R;
import com.android.msd.capstone.project.gardennerds.adapters.MyGardenAdapter;
import com.android.msd.capstone.project.gardennerds.databinding.FragmentMyGardenBinding;
import com.android.msd.capstone.project.gardennerds.models.Garden;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MyGardenFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MyGardenFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private FragmentMyGardenBinding myGardenBinding;
    private MyGardenAdapter gardenAdapter;
    private List<Garden> gardenList;

    public MyGardenFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MyGardenFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static MyGardenFragment newInstance(String param1, String param2) {
        MyGardenFragment fragment = new MyGardenFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        myGardenBinding = FragmentMyGardenBinding.inflate(inflater, container, false);

        init();

        return myGardenBinding.getRoot();
    }

    private void init() {

        myGardenBinding.fabAddGarden.setOnClickListener(v -> {
            // Replace the current fragment with AddGardenFragment
            requireActivity().getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.frames, new AddGardenFragment())
                    .addToBackStack(null)  // Add this transaction to the back stack
                    .commit();
        });

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Initialize garden list
        gardenList = new ArrayList<>();
        // Demo data - This will eventually be replaced by data from a local database
        loadGardens();

        // Show or hide the "No gardens added" message based on the list size
        if (gardenList.isEmpty()) {
            myGardenBinding.tvNoGardens.setVisibility(View.VISIBLE);
        } else {
            myGardenBinding.tvNoGardens.setVisibility(View.GONE);
        }

        bindAdapter();
    }

    //initializing and setting adapter
    private void bindAdapter() {

        // Initialize garden adapter
        gardenAdapter = new MyGardenAdapter(gardenList, requireActivity());
        myGardenBinding.recyclerViewGardens.setAdapter(gardenAdapter);
        myGardenBinding.recyclerViewGardens.setLayoutManager(new LinearLayoutManager(getContext()));
    }

    private void loadGardens() {
        // Add demo gardens (URL is null for placeholder image)
        gardenList.add(new Garden("Indoor Garden", "This is garden details and description", "150", "", "", "Shady", "3", null));
        gardenList.add(new Garden("Backyard Garden", "This is garden details and description", "300", "", "", "Partial Sunlight", "2", null));
        gardenList.add(new Garden("Frontyard Garden", "This is garden details and description", "200", "", "", "Shady", "3", null));
        gardenList.add(new Garden("Balcony Garden", "This is garden details and description", "50", "", "", "Full Sunlight", "1", null));
    }
}