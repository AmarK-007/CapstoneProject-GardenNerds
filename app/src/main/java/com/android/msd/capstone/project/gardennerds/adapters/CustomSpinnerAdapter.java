package com.android.msd.capstone.project.gardennerds.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.android.msd.capstone.project.gardennerds.R;
import com.android.msd.capstone.project.gardennerds.databinding.SpinnerItemBinding;


public class CustomSpinnerAdapter extends ArrayAdapter<String> {

    private final Context mContext;
    private final String[] mProvinces;

    public CustomSpinnerAdapter(Context context, String[] provinces) {
        super(context, R.layout.spinner_item, provinces);
        mContext = context;
        mProvinces = provinces;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return getCustomView(position, convertView, parent);
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        return getCustomView(position, convertView, parent);
    }

    public View getCustomView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        SpinnerItemBinding binding = SpinnerItemBinding.inflate(inflater, parent, false);
        binding.text1.setText(mProvinces[position]);
        return binding.getRoot();
    }
}
