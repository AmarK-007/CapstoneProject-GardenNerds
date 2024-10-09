package com.android.msd.capstone.project.gardennerds.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.android.msd.capstone.project.gardennerds.R;


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
        View row = inflater.inflate(R.layout.spinner_item, parent, false);
        TextView textView = row.findViewById(R.id.text1);
        textView.setText(mProvinces[position]);
        return row;
    }
}
