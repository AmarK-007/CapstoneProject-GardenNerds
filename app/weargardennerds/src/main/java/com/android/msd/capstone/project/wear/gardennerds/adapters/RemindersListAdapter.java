package com.android.msd.capstone.project.wear.gardennerds.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.msd.capstone.project.wear.gardennerds.databinding.CustomReminderListItemBinding;
import com.android.msd.capstone.project.wear.gardennerds.models.Reminder;
import com.android.msd.capstone.project.wear.gardennerds.utils.Utility;

import java.util.ArrayList;

public class RemindersListAdapter extends RecyclerView.Adapter<RemindersListAdapter.MyViewHolder> {
    ArrayList<Reminder> arrayList = new ArrayList<>();
    Context context;

    public RemindersListAdapter(ArrayList<Reminder> arrayList, Context context) {
        this.arrayList = arrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public RemindersListAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyViewHolder(CustomReminderListItemBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull RemindersListAdapter.MyViewHolder holder, int position) {
        holder.bind(arrayList.get(position));
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        CustomReminderListItemBinding binding;

        public MyViewHolder(CustomReminderListItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(Reminder reminders) {
            binding.tvName.setText(reminders.getPlantId());
            binding.tvFrequency.setText(reminders.getFrequency());
            binding.tvType.setText(Utility.getReminderTypeString(context, reminders.getReminderTypeId()));
        }
    }
}
