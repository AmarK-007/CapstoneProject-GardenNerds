package com.android.msd.capstone.project.gardennerds.fragments;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.android.msd.capstone.project.gardennerds.R;
import com.android.msd.capstone.project.gardennerds.databinding.FragmentProductDetailsBinding;
import com.android.msd.capstone.project.gardennerds.models.productResponses.ProductResults;
import com.android.msd.capstone.project.gardennerds.viewmodels.ProductViewModel;
import com.squareup.picasso.Picasso;


public class ProductDetailsFragment extends Fragment {
    FragmentProductDetailsBinding binding;
    public String productID;
    public ProductViewModel viewModel;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        assert getArguments() != null;
        productID = getArguments().getString("PRODUCT_ID");
        binding = FragmentProductDetailsBinding.inflate(inflater,container,false);
        viewModel = new ViewModelProvider(this).get(ProductViewModel.class);
        viewModel.getProductResults(productID).observe(requireActivity(), new Observer<ProductResults>() {
            @Override
            public void onChanged(ProductResults productResults) {
                setData(productResults);
            }
        });
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Log.d("TAG", productID);

    }

    @SuppressLint("SetTextI18n")
    private void setData(ProductResults products){

        Log.d("TAG",products.toString());

        binding.productTitle.setText(products.getTitle());
        if (!products.getMedia().get(0).getLink().isEmpty() || products.getMedia().get(0).getLink() != null) {
            Picasso.get().load(products.getMedia().get(0).getLink()).placeholder(R.drawable.designer).into(binding.productImageOne);
        }
//        if (!products.getMedia().get(1).getLink().isEmpty() || products.getMedia().get(1).getLink() != null){
//            Picasso.get().load(products.getMedia().get(1).getLink()).placeholder(R.drawable.designer).into(binding.productImageTwo);
//        }
//        if (!products.getMedia().get(2).getLink().isEmpty() || products.getMedia().get(2).getLink() != null){
//            Picasso.get().load(products.getMedia().get(2).getLink()).placeholder(R.drawable.designer).into(binding.productImageThree);
//        }

        binding.productPrice.setText(products.getPrices()[0]);
        binding.productRating.setText("Rating: " + products.getRating() + "★");
        binding.productReviews.setText(products.getReviews() + " Reviews");
        binding.productDescription.setText(products.getDescription());
//        binding.productHighlights.setText(products.getHighlights()[0]);

//        binding.shimmerLayout.stopShimmer();
//        binding.shimmerLayout.hideShimmer();
    }
}