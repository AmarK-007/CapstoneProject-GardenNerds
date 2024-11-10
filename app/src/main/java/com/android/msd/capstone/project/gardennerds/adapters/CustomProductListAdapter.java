package com.android.msd.capstone.project.gardennerds.adapters;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.android.msd.capstone.project.gardennerds.R;
import com.android.msd.capstone.project.gardennerds.databinding.CustomProductListingLayoutBinding;
import com.android.msd.capstone.project.gardennerds.fragments.ProductDetailsFragment;
import com.android.msd.capstone.project.gardennerds.models.shoppingResponses.ShoppingResult;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class CustomProductListAdapter extends RecyclerView.Adapter<CustomProductListAdapter.MyViewHolder> {

    private List<ShoppingResult> shoppingResults= new ArrayList<>();
    private AppCompatActivity activity;

    public CustomProductListAdapter(AppCompatActivity activity, List<ShoppingResult> results){
        this.shoppingResults = results;
        this.activity = activity;
    }

    @SuppressLint("NotifyDataSetChanged")
    public void submitList(List<ShoppingResult> products) {
//        this.shoppingResults.clear();
//        this.shoppingResults.addAll(products);
        this.shoppingResults = products;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyViewHolder(CustomProductListingLayoutBinding.inflate(LayoutInflater.from(parent.getContext()),parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        ShoppingResult products =  shoppingResults.get(position);
        holder.binding.productPrice.setText(products.getPrice());
        holder.binding.productSource.setText(products.getSource());
        holder.binding.productTitle.setText(products.getTitle());

        Picasso.get().load(products.getThumbnail()).placeholder(R.drawable.designer).into(holder.binding.productImage);

        holder.binding.getRoot().setOnClickListener(v ->{
            Bundle bundle = new Bundle();
            bundle.putString("PRODUCT_ID", products.getProductID());

            Fragment fragment = new ProductDetailsFragment();
            fragment.setArguments(bundle);
            FragmentManager supportFragmentManager = activity.getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = supportFragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.frames, fragment);
            fragmentTransaction.commit();
        });

    }

    @Override
    public int getItemCount() {
        return shoppingResults.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        CustomProductListingLayoutBinding binding;
        public MyViewHolder(CustomProductListingLayoutBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
