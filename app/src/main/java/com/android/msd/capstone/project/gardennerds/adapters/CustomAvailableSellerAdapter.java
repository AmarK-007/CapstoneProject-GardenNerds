package com.android.msd.capstone.project.gardennerds.adapters;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.android.msd.capstone.project.gardennerds.databinding.CustomAvailableSellerViewBinding;
import com.android.msd.capstone.project.gardennerds.models.productResponses.OnlineSeller;

import java.util.ArrayList;

public class CustomAvailableSellerAdapter extends RecyclerView.Adapter<CustomAvailableSellerAdapter.MyViewHolder> {

    ArrayList<OnlineSeller> onlineSellers = new ArrayList<>();
    Context context;

    public CustomAvailableSellerAdapter(ArrayList<OnlineSeller> onlineSellers, Context context) {
        this.onlineSellers = onlineSellers;
        this.context = context;
    }

    @NonNull
    @Override
    public CustomAvailableSellerAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyViewHolder(CustomAvailableSellerViewBinding.inflate(LayoutInflater.from(parent.getContext()),parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull CustomAvailableSellerAdapter.MyViewHolder holder, int position) {
        holder.bind(onlineSellers.get(position));
    }

    @Override
    public int getItemCount() {
        return onlineSellers.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        CustomAvailableSellerViewBinding binding;
        public MyViewHolder(CustomAvailableSellerViewBinding viewBinding) {
            super(viewBinding.getRoot());
            this.binding = viewBinding;
        }

        @SuppressLint("SetTextI18n")
        public void bind(OnlineSeller seller){
            binding.sellerName.setText(seller.getName());
            binding.sellerPrice.setText(seller.getBasePrice() + " +" + seller.getAdditionalPrice().getTax() + " Tax");
            binding.sellerLink.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String url;

                    url = Uri.encode(seller.getLink(),":/?&=");
                    Intent intent = new Intent(Intent.ACTION_VIEW,Uri.parse(url));
                    Log.d("TAG",seller.getDirectLink() + " link " + seller.getLink());

                    if (intent.resolveActivity(context.getPackageManager()) != null) {
                        context.startActivity(intent);
                    } else {
                        // Handle the case where no browser is available
                        System.out.println("No application can handle this request.");
                    }
                }
            });
        }
    }
}
