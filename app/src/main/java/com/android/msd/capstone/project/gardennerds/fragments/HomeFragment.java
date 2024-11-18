package com.android.msd.capstone.project.gardennerds.fragments;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.android.msd.capstone.project.gardennerds.R;
import com.android.msd.capstone.project.gardennerds.adapters.CustomCategoryAdapter;
import com.android.msd.capstone.project.gardennerds.databinding.FragmentHomeBinding;
import com.android.msd.capstone.project.gardennerds.interfaces.AdapterInterface;
import com.android.msd.capstone.project.gardennerds.models.Category;

import java.util.Calendar;
import java.util.Objects;


public class HomeFragment extends Fragment implements AdapterInterface<Category>, View.OnClickListener {
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
        binding.searchEdt.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH){
                    if (!Objects.requireNonNull(binding.searchEdt.getText()).toString().isEmpty()){
                       moveToProductListFragment("Gardening "+binding.searchEdt.getText().toString());
                    }
                    return true;
                }
                return false;
            }
        });
    }

    public void setRecyclerView() {
        adapter = new CustomCategoryAdapter(this);
        binding.homeRv.setAdapter(adapter);
        binding.homeRv.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
    }

    public void moveToProductListFragment(String query){
        Bundle bundle = new Bundle();
        bundle.putString("QUERY", query);

        Fragment fragment = new ProductListFragment();
        fragment.setArguments(bundle);
        FragmentManager supportFragmentManager = requireActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = supportFragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frames, fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }


    @Override
    public void onItemSelected(Category data, int position) {
        moveToProductListFragment(data.getQuery());
    }

    @Override
    public void onItemRemoved() {

    }

    @Override
    public void onClick(View v) {
        if (v == binding.btnSubscribe){
            //subscrive page
        }
    }
}