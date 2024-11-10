package com.android.msd.capstone.project.gardennerds.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.msd.capstone.project.gardennerds.databinding.CustomCardCategoryLayoutBinding;
import com.android.msd.capstone.project.gardennerds.models.Category;

import java.util.ArrayList;

public class CustomCategoryAdapter extends RecyclerView.Adapter<CustomCategoryAdapter.MyViewHolder> {
    ArrayList<Category> categories = new Category().getCategoryList();
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyViewHolder(CustomCardCategoryLayoutBinding.inflate(LayoutInflater.from(parent.getContext()),parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
    holder.bind(categories.get(position));
    }

    @Override
    public int getItemCount() {
        return categories.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        CustomCardCategoryLayoutBinding binding;
        public MyViewHolder(CustomCardCategoryLayoutBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(Category category){
            binding.ivCategory.setImageResource(category.getImageId());
            binding.tvCategory.setText(category.getTitle());
        }
    }
}
