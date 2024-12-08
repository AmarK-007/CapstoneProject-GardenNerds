package com.android.msd.capstone.project.wear.gardennerds.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.msd.capstone.project.wear.gardennerds.databinding.CustomReminderListItemBinding;
import com.android.msd.capstone.project.wear.gardennerds.models.tempModels.Reminders;

import java.util.ArrayList;


/**
 * Adapter for displaying a list of reminders in a RecyclerView.
 */
public class RemindersListAdapter extends RecyclerView.Adapter<RemindersListAdapter.MyViewHolder> {
    // List of reminder items to display
    ArrayList<Reminders> arrayList = new ArrayList<>();
    // Context of the adapter
    Context context;


    /**
     * Constructor for the RemindersListAdapter.
     *
     * @param arrayList The list of reminder items to display.
     * @param context   The context of the adapter.
     */
    public RemindersListAdapter(ArrayList<Reminders> arrayList, Context context) {
        this.arrayList = arrayList;
        this.context = context;
    }

    /**
     * Creates a new ViewHolder instance.
     *
     * @param parent   The parent ViewGroup.
     * @param viewType The view type of the new View.
     * @return A new MyViewHolder instance.
     */
    @NonNull
    @Override
    public RemindersListAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyViewHolder(CustomReminderListItemBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
    }


    /**
     * Binds data to the ViewHolder at the specified position.
     *
     * @param holder   The ViewHolder to bind data to.
     * @param position The position of the item in the data set.
     */
    @Override
    public void onBindViewHolder(@NonNull RemindersListAdapter.MyViewHolder holder, int position) {
        holder.bind(arrayList.get(position));
    }

    /**
     * Returns the total number of items in the data set.
     *
     * @return The total number of items.
     */
    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    /**
     * ViewHolder class for holding and displaying reminder data.
     */
    public class MyViewHolder extends RecyclerView.ViewHolder {
        // View binding for the reminder list item layout
        CustomReminderListItemBinding binding;

        /**
         * Constructor for the MyViewHolder.
         *
         * @param binding The view binding for the reminder list item layout.
         */
        public MyViewHolder(CustomReminderListItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        /**
         * Binds reminder data to the views in the ViewHolder.
         *
         * @param reminders The reminder data to bind.
         */
        public void bind(Reminders reminders){
            // Set the text values for the name, frequency, and type
            binding.tvName.setText(reminders.getName());
            binding.tvFrequency.setText(reminders.getFrequency());
            binding.tvType.setText(reminders.getType());
        }
    }
}
