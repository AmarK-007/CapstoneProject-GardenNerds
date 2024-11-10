package com.android.msd.capstone.project.gardennerds.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.android.msd.capstone.project.gardennerds.adapters.CustomCategoryAdapter;
import com.android.msd.capstone.project.gardennerds.databinding.FragmentHomeBinding;


public class HomeFragment extends Fragment {
    FragmentHomeBinding binding;
    CustomCategoryAdapter adapter;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
//            mParam1 = getArguments().getString(ARG_PARAM1);
//            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentHomeBinding.inflate(inflater, container, false);
        // Inflate the layout for this fragment
//        View view = inflater.inflate(R.layout.fragment_home, container, false);
// on click listener for all buttons in home fragment

//        Button btnMeasurement = view.findViewById(R.id.btnMeasurement);
//        Button btnApiSearch = view.findViewById(R.id.btnApiSearch);
//        Button btnSoilSensor = view.findViewById(R.id.btnSoilSensor);
//
//        btnMeasurement.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                // navigate to measurement fragment
//                Fragment fragment = new MeasurementFragment();
//                FragmentManager supportFragmentManager = requireActivity().getSupportFragmentManager();
//                FragmentTransaction fragmentTransaction = supportFragmentManager.beginTransaction();
//                fragmentTransaction.replace(R.id.frames, fragment);
//                fragmentTransaction.commit();
//            }
//        });
//
//        btnApiSearch.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                // navigate to api search fragment
//                Fragment fragment = new ProductListFragment();
//                FragmentManager supportFragmentManager = requireActivity().getSupportFragmentManager();
//                FragmentTransaction fragmentTransaction = supportFragmentManager.beginTransaction();
//                fragmentTransaction.replace(R.id.frames, fragment);
//                fragmentTransaction.commit();
//            }
//        });
//
//        btnSoilSensor.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                // navigate to soil sensor fragment
//                Fragment fragment = new SoilSensorFragment();
//                FragmentManager supportFragmentManager = requireActivity().getSupportFragmentManager();
//                FragmentTransaction fragmentTransaction = supportFragmentManager.beginTransaction();
//                fragmentTransaction.replace(R.id.frames, fragment);
//                fragmentTransaction.commit();
//            }
//        });

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setRecyclerView();
        onSearch();
    }

    public void onSearch() {

    }

    public void setRecyclerView() {
        adapter = new CustomCategoryAdapter();
        binding.homeRv.setAdapter(adapter);
        binding.homeRv.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
    }
}