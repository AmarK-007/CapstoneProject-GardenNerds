package com.android.msd.capstone.project.gardennerds.fragments;

import android.app.Dialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.android.msd.capstone.project.gardennerds.R;
import com.android.msd.capstone.project.gardennerds.adapters.CustomProductListAdapter;
import com.android.msd.capstone.project.gardennerds.databinding.FragmentProductListBinding;
import com.android.msd.capstone.project.gardennerds.interfaces.AdapterInterface;
import com.android.msd.capstone.project.gardennerds.models.shoppingResponses.ShoppingResult;
import com.android.msd.capstone.project.gardennerds.viewmodels.ShoppingViewModel;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;


public class ProductListFragment extends Fragment implements AdapterInterface<ShoppingResult>, View.OnClickListener{

    FragmentProductListBinding binding;
    CustomProductListAdapter adapter;
    private ShoppingViewModel viewModel;
    private String searchQuery;
    private ArrayList<ShoppingResult> products;
    public Boolean isAscending = true;
    private Dialog loadingDialog= null;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentProductListBinding.inflate(inflater, container, false);
        showLoadingPopup();
        init();
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        requireActivity().getOnBackPressedDispatcher().addCallback(getViewLifecycleOwner(), new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                // Pop the fragment from the back stack when back is pressed
                requireActivity().getSupportFragmentManager().popBackStack();
            }
        });

    }



    public void init() {
        products = new ArrayList<>();
        assert getArguments() != null;
        searchQuery = getArguments().getString("QUERY");

        binding.ivGrid.setOnClickListener(this);
        binding.ivList.setOnClickListener(this);
        binding.ivUpDown.setOnClickListener(this);


        setRecyclerViewAdapter(false);
        viewModel = new ViewModelProvider(this).get(ShoppingViewModel.class);
        viewModel.getShoppingResults(searchQuery).observe(requireActivity(), new Observer<List<ShoppingResult>>() {
            @Override
            public void onChanged(List<ShoppingResult> shoppingResults) {
                if(shoppingResults!=null) {
                    Log.e("TAG", shoppingResults.size() + " numbers");
                    products = (ArrayList<ShoppingResult>) shoppingResults;
                    dismissLoadingPopup();
                    adapter.submitList(products);
                }
            }
        });
    }


    public void setRecyclerViewAdapter(Boolean isList) {
        if (isList){
            adapter = new CustomProductListAdapter(products, true, this);
            binding.rvProductList.setLayoutManager(new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL));
            binding.rvProductList.setAdapter(adapter);
        }else {
            adapter = new CustomProductListAdapter(products, false, this);
            binding.rvProductList.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
            binding.rvProductList.setAdapter(adapter);
        }
    }

    @Override
    public void onItemSelected(ShoppingResult data, int position) {
        Bundle bundle = new Bundle();
        bundle.putString("PRODUCT_ID", data.getProductID());

        Fragment fragment = new ProductDetailsFragment();
        fragment.setArguments(bundle);
        FragmentManager supportFragmentManager = requireActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = supportFragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frames, fragment);
        fragmentTransaction.commit();
    }

    @Override
    public void onItemRemoved() {

    }

    @Override
    public void onClick(View v) {
        if (v == binding.ivList){
            setRecyclerViewAdapter(true);
        }
        if (v == binding.ivGrid){
            setRecyclerViewAdapter(false);
        }
        if (v== binding.ivUpDown){
            isAscending = !isAscending;
            sortProductsByPrice(products,isAscending);
            adapter.submitList(products);

        }
    }

    public static void sortProductsByPrice(List<ShoppingResult> products, boolean ascending) {
        if (ascending) {
            Collections.sort(products, new Comparator<ShoppingResult>() {
                @Override
                public int compare(ShoppingResult p1, ShoppingResult p2) {
                    return Double.compare(convertDouble(p1.getPrice()),convertDouble(p2.getPrice()));
                }
            });
        } else {
            Collections.sort(products, new Comparator<ShoppingResult>() {
                @Override
                public int compare(ShoppingResult p1, ShoppingResult p2) {
                    return Double.compare(convertDouble(p2.getPrice()), convertDouble(p1.getPrice()));
                }
            });
        }
    }
    public static Double convertDouble(String price) {
        // Remove the dollar sign and parse the number
        // Return true if price is greater than zero, otherwise false
        return Double.parseDouble(price.replace("$", ""));
    }


    private void showLoadingPopup() {
//        if (loadingDialog == null) {
            loadingDialog = new Dialog(requireContext());
            loadingDialog.setContentView(R.layout.custom_loading_dialog_view);
            loadingDialog.setCancelable(false); // Prevent the user from dismissing it
            if (loadingDialog.getWindow() != null) {
                loadingDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
            }
            loadingDialog.show();
//        }
    }

    private void dismissLoadingPopup() {
        if (loadingDialog != null) {
            loadingDialog.dismiss();
            loadingDialog = null;
        }
    }
}