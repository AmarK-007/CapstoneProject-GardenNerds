package com.android.msd.capstone.project.gardennerds.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.msd.capstone.project.gardennerds.databinding.LayoutItemReminderBinding;
import com.android.msd.capstone.project.gardennerds.models.Plant;
import com.android.msd.capstone.project.gardennerds.models.Reminder;
import com.android.msd.capstone.project.gardennerds.utils.Constants;
import com.android.msd.capstone.project.gardennerds.utils.Utility;

import java.util.List;

public class ReminderAdapter extends RecyclerView.Adapter<ReminderAdapter.ReminderViewHolder> {

    private final Context context;
    private List<Reminder> reminderList;

    public ReminderAdapter(List<Reminder> reminderList, Context context) {
        this.context = context;
        this.reminderList = reminderList;
    }

    @NonNull
    @Override
    public ReminderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutItemReminderBinding binding = LayoutItemReminderBinding.inflate(LayoutInflater.from(context), parent, false);
        return new ReminderViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ReminderViewHolder holder, int position) {
        Reminder reminder = reminderList.get(position);

        // Display reminder type string
        String reminderTypeString = Utility.getReminderTypeString(context, reminder.getReminderTypeId());
        holder.binding.tvMessage.setText(reminderTypeString);
        holder.binding.tvDateTime.setText(reminder.getDateTime());

        // Display respective data based on reminder type
        switch (reminder.getReminderTypeId()) {
            case Constants.REMINDER_TYPE_WATER:
                holder.binding.tvDetails.setText("Frequency: " + reminder.getFrequency() + "\nMoisture Level: " + reminder.getMoistureLevel());
                break;
            case Constants.REMINDER_TYPE_FERTILIZE:
                holder.binding.tvDetails.setText("Frequency: " + reminder.getFrequency() + "\nNutrient Required: " + reminder.getNutrientRequired());
                break;
            case Constants.REMINDER_TYPE_SUNLIGHT:
                holder.binding.tvDetails.setText("Frequency: " + reminder.getFrequency() + "\nTemperature Level: " + reminder.getTemperatureLevel() + "\nSunlight Level: " + reminder.getSunlightLevel());
                break;
            case Constants.REMINDER_TYPE_CHANGE_SOIL:
                holder.binding.tvDetails.setText("Frequency: " + reminder.getFrequency());
                break;
            default:
                holder.binding.tvDetails.setText("Unknown reminder type");
                break;
        }
    }

    @Override
    public int getItemCount() {
        return reminderList.size();
    }

    public void setReminders(List<Reminder> newReminderList) {
        this.reminderList = newReminderList;
        notifyDataSetChanged();
    }


    public static class ReminderViewHolder extends RecyclerView.ViewHolder {

        final LayoutItemReminderBinding binding;

        public ReminderViewHolder(@NonNull LayoutItemReminderBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

}
