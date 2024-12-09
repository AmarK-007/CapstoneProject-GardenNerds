package com.android.msd.capstone.project.gardennerds.adapters;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.msd.capstone.project.gardennerds.R;
import com.android.msd.capstone.project.gardennerds.databinding.CustomProductListingGridLayoutBinding;
import com.android.msd.capstone.project.gardennerds.databinding.CustomProductListingListLayoutBinding;
import com.android.msd.capstone.project.gardennerds.interfaces.AdapterInterface;
import com.android.msd.capstone.project.gardennerds.models.productResponses.DifferentBrand;
import com.android.msd.capstone.project.gardennerds.models.shoppingResponses.ShoppingResult;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Adapter class for displaying a list of products in a RecyclerView.
 */
public class CustomProductListAdapter extends RecyclerView.Adapter<CustomProductListAdapter.MyViewHolder> {

    private List<ShoppingResult> shoppingResults = new ArrayList<>();
    private List<DifferentBrand> differentBrands = new ArrayList<>();
    private Boolean isBrands = false;
    private Boolean isList = false;
    private AdapterInterface<ShoppingResult> adapterInterface;

    /**
     * Constructor for CustomProductListAdapter.
     *
     * @param results          List of shopping results to display.
     * @param isList           Boolean indicating if the list layout should be used.
     * @param adapterInterface Interface for handling item selection.
     */
    public CustomProductListAdapter(List<ShoppingResult> results, Boolean isList, AdapterInterface<ShoppingResult> adapterInterface) {
        this.shoppingResults = results;
        this.isList = isList;
        this.adapterInterface = adapterInterface;
    }

    /**
     * Constructor for CustomProductListAdapter.
     *
     * @param results  List of different brands to display.
     * @param isList   Boolean indicating if the list layout should be used.
     * @param isBrands Boolean indicating if the brands layout should be used.
     */
    public CustomProductListAdapter(List<DifferentBrand> results, Boolean isList, Boolean isBrands) {
        this.differentBrands = results;
        this.isList = isList;
        this.isBrands = isBrands;
    }

    @SuppressLint("NotifyDataSetChanged")
    public void submitList(List<ShoppingResult> products) {
        this.shoppingResults = products;
        Log.d("TAG", products.size() + " adapter number");
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (isBrands) {
            return new MyViewHolder(CustomProductListingGridLayoutBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
        } else {
            if (isList) {
                return new MyViewHolder(CustomProductListingListLayoutBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false), adapterInterface);
            } else {
                return new MyViewHolder(CustomProductListingGridLayoutBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false), adapterInterface);
            }
        }
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        if (isBrands) {
            DifferentBrand brand = differentBrands.get(position);
            holder.bindBrandGrid(brand);
        } else {
            ShoppingResult products = shoppingResults.get(position);
            if (isList) {
                holder.bindList(products);
            } else {
                holder.bindGrid(products);
            }
        }
    }

    @Override
    public int getItemCount() {
        return isBrands ? differentBrands.size() : shoppingResults.size();
    }

    /**
     * ViewHolder class for CustomProductListAdapter.
     */
    public static class MyViewHolder extends RecyclerView.ViewHolder {
        CustomProductListingGridLayoutBinding gridBinding;
        CustomProductListingListLayoutBinding listBinding;
        AdapterInterface<ShoppingResult> adapterInterface;

        /**
         * Constructor for MyViewHolder.
         *
         * @param binding           Binding object for the grid layout.
         * @param adapterInterface  Interface for handling item selection.
         */
        public MyViewHolder(CustomProductListingGridLayoutBinding binding, AdapterInterface<ShoppingResult> adapterInterface) {
            super(binding.getRoot());
            this.gridBinding = binding;
            this.adapterInterface = adapterInterface;
        }

        /**
         * Constructor for MyViewHolder.
         *
         * @param binding           Binding object for the list layout.
         * @param adapterInterface  Interface for handling item selection.
         */
        public MyViewHolder(CustomProductListingListLayoutBinding binding, AdapterInterface<ShoppingResult> adapterInterface) {
            super(binding.getRoot());
            this.listBinding = binding;
            this.adapterInterface = adapterInterface;
        }

        /**
         * Constructor for MyViewHolder.
         *
         * @param binding Binding object for the grid layout.
         */
        public MyViewHolder(CustomProductListingGridLayoutBinding binding) {
            super(binding.getRoot());
            this.gridBinding = binding;
        }

        /**
         * Binds the data of a ShoppingResult to the grid view.
         *
         * @param products The ShoppingResult object containing the data.
         */
        public void bindGrid(ShoppingResult products) {
            gridBinding.productPrice.setText(products.getPrice());
            gridBinding.productSource.setText(products.getSource());
            gridBinding.productTitle.setText(products.getTitle());

            Picasso.get().load(products.getThumbnail()).placeholder(R.drawable.designer).into(gridBinding.productImage);

            gridBinding.getRoot().setOnClickListener(v -> adapterInterface.onItemSelected(products, getAdapterPosition()));
        }

        /**
         * Binds the data of a DifferentBrand to the grid view.
         *
         * @param brand The DifferentBrand object containing the data.
         */
        public void bindBrandGrid(DifferentBrand brand) {
            gridBinding.productPrice.setText(brand.getPrice());
            gridBinding.productSource.setText("");
            gridBinding.productTitle.setText(brand.getTitle());

            Picasso.get().load(brand.getThumbnail()).placeholder(R.drawable.designer).into(gridBinding.productImage);

            gridBinding.getRoot().setOnClickListener(v -> {
                // Handle brand item click
            });
        }

        /**
         * Binds the data of a ShoppingResult to the list view.
         *
         * @param products The ShoppingResult object containing the data.
         */
        public void bindList(ShoppingResult products) {
            listBinding.productPrice.setText(products.getPrice());
            listBinding.productSource.setText(products.getSource());
            listBinding.productTitle.setText(products.getTitle());

            Picasso.get().load(products.getThumbnail()).placeholder(R.drawable.designer).into(listBinding.productImage);

            listBinding.getRoot().setOnClickListener(v -> adapterInterface.onItemSelected(products, getAdapterPosition()));
        }
    }
}