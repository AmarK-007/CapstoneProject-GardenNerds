package com.android.msd.capstone.project.gardennerds.adapters;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.android.msd.capstone.project.gardennerds.R;
import com.android.msd.capstone.project.gardennerds.databinding.CustomProductListingGridLayoutBinding;
import com.android.msd.capstone.project.gardennerds.databinding.CustomProductListingGridLayoutBinding;
import com.android.msd.capstone.project.gardennerds.databinding.CustomProductListingListLayoutBinding;
import com.android.msd.capstone.project.gardennerds.fragments.ProductDetailsFragment;
import com.android.msd.capstone.project.gardennerds.interfaces.AdapterInterface;
import com.android.msd.capstone.project.gardennerds.models.shoppingResponses.ShoppingResult;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class CustomProductListAdapter extends RecyclerView.Adapter<CustomProductListAdapter.MyViewHolder> {

    private List<ShoppingResult> shoppingResults= new ArrayList<>();
//    private AppCompatActivity activity;
    private Boolean isList;
    AdapterInterface<ShoppingResult> adapterInterface;

    public CustomProductListAdapter( List<ShoppingResult> results,Boolean isList, AdapterInterface<ShoppingResult> adapterInterface){
        this.shoppingResults = results;

        this.isList = isList;
        this.adapterInterface = adapterInterface;
    }

    @SuppressLint("NotifyDataSetChanged")
    public void submitList(List<ShoppingResult> products) {
//        this.shoppingResults.clear();
//        this.shoppingResults.addAll(products);
        this.shoppingResults = products;
//        this.isList = isList;
        Log.d("TAG", products.size() + " adapter number");

        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (isList){
            return new MyViewHolder(CustomProductListingListLayoutBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false),adapterInterface);
        }else {
            return new MyViewHolder(CustomProductListingGridLayoutBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false),adapterInterface);
        }

    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        ShoppingResult products =  shoppingResults.get(position);
        if (isList){
            holder.bindList(products);
        }else {
            holder.bindGrid(products);
        }

    }

    @Override
    public int getItemCount() {
        return shoppingResults.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        CustomProductListingGridLayoutBinding gridBinding;
        CustomProductListingListLayoutBinding listBinding;
        AdapterInterface<ShoppingResult> adapterInterface;
        public MyViewHolder(CustomProductListingGridLayoutBinding binding, AdapterInterface<ShoppingResult> adapterInterface) {
            super(binding.getRoot());
            this.gridBinding = binding;
            this.adapterInterface = adapterInterface;
        }
        public MyViewHolder(CustomProductListingListLayoutBinding binding,AdapterInterface<ShoppingResult> adapterInterface) {
            super(binding.getRoot());
            this.listBinding = binding;
            this.adapterInterface = adapterInterface;

        }

        public void bindGrid(ShoppingResult products){
            gridBinding.productPrice.setText(products.getPrice());
            gridBinding.productSource.setText(products.getSource());
            gridBinding.productTitle.setText(products.getTitle());

            Picasso.get().load(products.getThumbnail()).placeholder(R.drawable.designer).into(gridBinding.productImage);

            gridBinding.getRoot().setOnClickListener(v ->{
                adapterInterface.onItemSelected(products,getAdapterPosition());

//                Bundle bundle = new Bundle();
//                bundle.putString("PRODUCT_ID", products.getProductID());
//
//                Fragment fragment = new ProductDetailsFragment();
//                fragment.setArguments(bundle);
//                FragmentManager supportFragmentManager = activity.getSupportFragmentManager();
//                FragmentTransaction fragmentTransaction = supportFragmentManager.beginTransaction();
//                fragmentTransaction.replace(R.id.frames, fragment);
//                fragmentTransaction.commit();
            });


        }

        public void bindList(ShoppingResult products){
            listBinding.productPrice.setText(products.getPrice());
            listBinding.productSource.setText(products.getSource());
            listBinding.productTitle.setText(products.getTitle());

            Picasso.get().load(products.getThumbnail()).placeholder(R.drawable.designer).into(listBinding.productImage);

            listBinding.getRoot().setOnClickListener(v ->{
                adapterInterface.onItemSelected(products,getAdapterPosition());
//                Bundle bundle = new Bundle();
//                bundle.putString("PRODUCT_ID", products.getProductID());
//
//                Fragment fragment = new ProductDetailsFragment();
//                fragment.setArguments(bundle);
//                FragmentManager supportFragmentManager = activity.getSupportFragmentManager();
//                FragmentTransaction fragmentTransaction = supportFragmentManager.beginTransaction();
//                fragmentTransaction.replace(R.id.frames, fragment);
//                fragmentTransaction.commit();
            });


        }
    }
}
