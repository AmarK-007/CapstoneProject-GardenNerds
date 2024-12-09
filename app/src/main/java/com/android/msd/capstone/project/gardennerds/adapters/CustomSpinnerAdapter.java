package com.android.msd.capstone.project.gardennerds.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.android.msd.capstone.project.gardennerds.R;
import com.android.msd.capstone.project.gardennerds.databinding.SpinnerItemBinding;

/**
 * Custom adapter for displaying a list of provinces in a Spinner.
 */
public class CustomSpinnerAdapter extends ArrayAdapter<String> {

    private final Context mContext;
    private final String[] mProvinces;

    /**
     * Constructor for CustomSpinnerAdapter.
     *
     * @param context   the context in which the adapter is used
     * @param provinces the array of provinces to display
     */
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

    /**
     * Returns a custom view for the Spinner item.
     *
     * @param position    the position of the item within the adapter's data set
     * @param convertView the old view to reuse, if possible
     * @param parent      the parent that this view will eventually be attached to
     * @return the custom view for the Spinner item
     */
    public View getCustomView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        SpinnerItemBinding binding = SpinnerItemBinding.inflate(inflater, parent, false);
        binding.text1.setText(mProvinces[position]);
        return binding.getRoot();
    }
}