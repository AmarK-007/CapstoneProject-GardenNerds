package com.android.msd.capstone.project.gardennerds.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.android.msd.capstone.project.gardennerds.R;
import com.android.msd.capstone.project.gardennerds.adapters.MyGardenAdapter;
import com.android.msd.capstone.project.gardennerds.databinding.FragmentMyGardenBinding;
import com.android.msd.capstone.project.gardennerds.db.GardenDataSource;
import com.android.msd.capstone.project.gardennerds.models.Garden;
import com.android.msd.capstone.project.gardennerds.viewmodels.GardenViewModel;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MyGardenFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MyGardenFragment extends Fragment implements View.OnClickListener {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private FragmentMyGardenBinding myGardenBinding;
    private MyGardenAdapter gardenAdapter;
    private GardenDataSource gardenDataSource;
    private GardenViewModel gardenViewModel;


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

        myGardenBinding.fabAddGarden.setOnClickListener(this);
        gardenDataSource = new GardenDataSource(requireContext());

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        // Ensure Activity and ActionBar are available
        if (getActivity() != null && getActivity() instanceof AppCompatActivity) {
            AppCompatActivity activity = (AppCompatActivity) getActivity();
            activity.getSupportActionBar().setTitle(getString(R.string.text_my_garden));
        }

        // Get the ViewModel
        gardenViewModel = new ViewModelProvider(requireActivity()).get(GardenViewModel.class);

        bindAdapter();

        // Observe the garden list for updates
        gardenViewModel.getGardenList().observe(getViewLifecycleOwner(), gardens -> {
            if (gardens != null && !gardens.isEmpty()) {
                gardenAdapter.updateDataSet(gardens); // Refresh the RecyclerView
                myGardenBinding.tvNoGardens.setVisibility(View.GONE);
            }else{
                // Show or hide the "No gardens added" message based on the list size
                myGardenBinding.tvNoGardens.setVisibility(View.VISIBLE);
            }
        });

        // Load gardens from the database
        loadGardens();

    }

    //initializing and setting adapter
    private void bindAdapter() {

        // Initialize adapter with an empty list
        myGardenBinding.recyclerViewGardens.setLayoutManager(new LinearLayoutManager(requireActivity()));

        gardenAdapter = new MyGardenAdapter(new ArrayList<>(),requireActivity(),gardenViewModel);
        myGardenBinding.recyclerViewGardens.setAdapter(gardenAdapter);
    }

    private void loadGardens() {
        // Fetch all gardens from the database
        List<Garden> gardens = gardenDataSource.getAllGardens();
        gardenViewModel.setGardenList(gardens);

    }

    @Override
    public void onClick(View v) {
        // Handle click events
        if (v.getId() == myGardenBinding.fabAddGarden.getId()) {
            // Replace the current fragment with AddGardenFragment
            requireActivity().getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.frames, new AddGardenFragment(), "MyGardenFragmentTag")
                    .addToBackStack("MyGardenFragmentTag")  // Add this transaction to the back stack
                    .commit();
        }
    }

}