package com.android.msd.capstone.project.gardennerds.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.android.msd.capstone.project.gardennerds.adapters.CustomProductListAdapter;
import com.android.msd.capstone.project.gardennerds.databinding.FragmentProductListBinding;
import com.android.msd.capstone.project.gardennerds.models.shoppingResponses.ShoppingResult;
import com.android.msd.capstone.project.gardennerds.viewmodels.ShoppingViewModel;

import java.util.ArrayList;
import java.util.List;


public class ProductListFragment extends Fragment {

    FragmentProductListBinding binding;
    CustomProductListAdapter adapter;
    private ShoppingViewModel viewModel;
    private String searchQuery;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentProductListBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        init();
    }


    public void init() {
        searchQuery = "gardening 10ft x 10ft";
        adapter = new CustomProductListAdapter((AppCompatActivity) getActivity(), new ArrayList<>());
        setRecyclerViewAdapter();
        viewModel = new ViewModelProvider(this).get(ShoppingViewModel.class);
        viewModel.getShoppingResults(searchQuery).observe(requireActivity(), new Observer<List<ShoppingResult>>() {
            @Override
            public void onChanged(List<ShoppingResult> shoppingResults) {
                if(shoppingResults!=null) {
                    Log.e("TAG", shoppingResults.get(0).getTitle().toString());
                    adapter.submitList(shoppingResults);
                }
            }
        });
    }


    public void setRecyclerViewAdapter() {
        binding.rvProductList.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
        binding.rvProductList.setAdapter(adapter);

    }
}