package com.android.msd.capstone.project.gardennerds.fragments;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.net.Uri;
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
import com.android.msd.capstone.project.gardennerds.adapters.ImageSliderAdapter;
import com.android.msd.capstone.project.gardennerds.databinding.FragmentProductDetailsBinding;
import com.android.msd.capstone.project.gardennerds.models.productResponses.ProductDetail;
import com.android.msd.capstone.project.gardennerds.models.productResponses.ProductResults;
import com.android.msd.capstone.project.gardennerds.viewmodels.ProductViewModel;
import com.google.android.material.tabs.TabLayoutMediator;
import com.squareup.picasso.Picasso;


public class ProductDetailsFragment extends Fragment implements View.OnClickListener {
    FragmentProductDetailsBinding binding;
    public String productID;
    public ProductViewModel viewModel;
    private Dialog loadingDialog= null;
    private ProductDetail productDetails;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        assert getArguments() != null;
        showLoadingPopup();
        productID = getArguments().getString("PRODUCT_ID");
        binding = FragmentProductDetailsBinding.inflate(inflater,container,false);
        viewModel = new ViewModelProvider(this).get(ProductViewModel.class);
        viewModel.getProductResults(productID).observe(requireActivity(), new Observer<ProductDetail>() {
            @Override
            public void onChanged(ProductDetail productDetail) {
                dismissLoadingPopup();
                productDetails = productDetail;
                setData(productDetails.getProductResults());
            }
        });
        binding.btnBuynow.setOnClickListener(this);
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
//        if (!products.getMedia().get(0).getLink().isEmpty() || products.getMedia().get(0).getLink() != null) {
//            Picasso.get().load(products.getMedia().get(0).getLink()).placeholder(R.drawable.designer).into(binding.productImageOne);
//        }
//        if (!products.getMedia().get(1).getLink().isEmpty() || products.getMedia().get(1).getLink() != null){
//            Picasso.get().load(products.getMedia().get(1).getLink()).placeholder(R.drawable.designer).into(binding.productImageTwo);
//        }
//        if (!products.getMedia().get(2).getLink().isEmpty() || products.getMedia().get(2).getLink() != null){
//            Picasso.get().load(products.getMedia().get(2).getLink()).placeholder(R.drawable.designer).into(binding.productImageThree);
//        }

        ImageSliderAdapter adapter = new ImageSliderAdapter(products.getMedia(), requireContext());
        binding.viewPager.setAdapter(adapter);

        // Link TabLayout with ViewPager2
        new TabLayoutMediator(binding.tabLayout, binding.viewPager,
                (tab, position) -> {
                    // Optionally set tab icons or text here
                }).attach();

        binding.productPrice.setText(products.getPrices()[0]);
        binding.productRating.setText("Rating: " + products.getRating() + "★");
        binding.productReviews.setText(products.getReviews() + " Reviews");
        binding.tvDescription.setText(products.getDescription());

        StringBuilder bulletList = new StringBuilder();
//        Log.d("TAG","highlights length "+products.getHighlights().length);
        if (products.getHighlights() != null){
        for (String highlight : products.getHighlights()) {
            bulletList.append("\u2022 ").append(highlight).append("\n");
        }
        }
       binding.productHighlights.setText(bulletList);
//        binding.productHighlights.setText(products.getHighlights()[0]);

//        binding.shimmerLayout.stopShimmer();
//        binding.shimmerLayout.hideShimmer();
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

    @Override
    public void onClick(View v) {
        if (v == binding.btnBuynow){
            openWebPage(productDetails.getSearchMetadata().getGoogle_product_url());
        }
    }

    private void openWebPage(String url) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse(url));
        if (intent.resolveActivity(getActivity().getPackageManager()) != null) {
            startActivity(intent);
        } else {
            // Handle the case where no browser is available
            System.out.println("No application can handle this request.");
        }
    }
}