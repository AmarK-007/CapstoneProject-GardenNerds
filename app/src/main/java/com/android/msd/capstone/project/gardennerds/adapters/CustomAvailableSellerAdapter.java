package com.android.msd.capstone.project.gardennerds.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.msd.capstone.project.gardennerds.databinding.CustomAvailableSellerViewBinding;
import com.android.msd.capstone.project.gardennerds.models.productResponses.OnlineSeller;

import java.util.ArrayList;

/**
 * Adapter class for displaying a list of online sellers in a RecyclerView.
 */
public class CustomAvailableSellerAdapter extends RecyclerView.Adapter<CustomAvailableSellerAdapter.MyViewHolder> {

    private final ArrayList<OnlineSeller> onlineSellers;
    private final Context context;

    /**
     * Constructor for CustomAvailableSellerAdapter.
     *
     * @param onlineSellers List of online sellers to display.
     * @param context       Context in which the adapter is used.
     */
    public CustomAvailableSellerAdapter(ArrayList<OnlineSeller> onlineSellers, Context context) {
        this.onlineSellers = onlineSellers;
        this.context = context;
    }

    @NonNull
    @Override
    public CustomAvailableSellerAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyViewHolder(CustomAvailableSellerViewBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull CustomAvailableSellerAdapter.MyViewHolder holder, int position) {
        holder.bind(onlineSellers.get(position));
    }

    @Override
    public int getItemCount() {
        return onlineSellers.size();
    }

    /**
     * ViewHolder class for CustomAvailableSellerAdapter.
     */
    public class MyViewHolder extends RecyclerView.ViewHolder {
        private final CustomAvailableSellerViewBinding binding;

        /**
         * Constructor for MyViewHolder.
         *
         * @param viewBinding Binding object for the view.
         */
        public MyViewHolder(CustomAvailableSellerViewBinding viewBinding) {
            super(viewBinding.getRoot());
            this.binding = viewBinding;
        }

        /**
         * Binds the data of an OnlineSeller to the view.
         *
         * @param seller The OnlineSeller object containing the data.
         */
        @SuppressLint("SetTextI18n")
        public void bind(OnlineSeller seller) {
            binding.sellerName.setText(seller.getName());
            binding.sellerPrice.setText(seller.getBasePrice() + " +" + seller.getAdditionalPrice().getTax() + " Tax");
            binding.sellerLink.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String url = Uri.encode(seller.getLink(), ":/?&=");
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                    Log.d("TAG", seller.getDirectLink() + " link " + seller.getLink());

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