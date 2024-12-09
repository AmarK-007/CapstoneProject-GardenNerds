package com.android.msd.capstone.project.gardennerds.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.msd.capstone.project.gardennerds.databinding.CustomCardCategoryLayoutBinding;
import com.android.msd.capstone.project.gardennerds.interfaces.AdapterInterface;
import com.android.msd.capstone.project.gardennerds.models.Category;

import java.util.ArrayList;

/**
 * Adapter class for displaying a list of categories in a RecyclerView.
 */
public class CustomCategoryAdapter extends RecyclerView.Adapter<CustomCategoryAdapter.MyViewHolder> {
    private final ArrayList<Category> categories = new Category().getCategoryList();
    private final AdapterInterface<Category> adapterInterface;

    /**
     * Constructor for CustomCategoryAdapter.
     *
     * @param adapterInterface Interface for handling item selection.
     */
    public CustomCategoryAdapter(AdapterInterface<Category> adapterInterface) {
        this.adapterInterface = adapterInterface;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyViewHolder(CustomCardCategoryLayoutBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.bind(categories.get(position));
    }

    @Override
    public int getItemCount() {
        return categories.size();
    }

    /**
     * ViewHolder class for CustomCategoryAdapter.
     */
    public class MyViewHolder extends RecyclerView.ViewHolder {
        private final CustomCardCategoryLayoutBinding binding;

        /**
         * Constructor for MyViewHolder.
         *
         * @param binding Binding object for the view.
         */
        public MyViewHolder(CustomCardCategoryLayoutBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        /**
         * Binds the data of a Category to the view.
         *
         * @param category The Category object containing the data.
         */
        public void bind(Category category) {
            binding.ivCategory.setImageResource(category.getImageId());
            binding.tvCategory.setText(category.getTitle());
            binding.getRoot().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    adapterInterface.onItemSelected(category, getAdapterPosition());
                }
            });
        }
    }
}