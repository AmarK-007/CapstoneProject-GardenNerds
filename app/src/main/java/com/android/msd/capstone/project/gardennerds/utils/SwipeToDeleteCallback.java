package com.android.msd.capstone.project.gardennerds.utils;

import android.content.Context;

import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.android.msd.capstone.project.gardennerds.adapters.MyGardenAdapter;
import com.android.msd.capstone.project.gardennerds.adapters.MyPlantAdapter;
import com.android.msd.capstone.project.gardennerds.adapters.ReminderAdapter;
import com.android.msd.capstone.project.gardennerds.db.GardenDataSource;
import com.android.msd.capstone.project.gardennerds.db.PlantDataSource;
import com.android.msd.capstone.project.gardennerds.db.ReminderDataSource;
import com.android.msd.capstone.project.gardennerds.models.Garden;
import com.android.msd.capstone.project.gardennerds.models.Plant;
import com.android.msd.capstone.project.gardennerds.models.Reminder;

import java.util.List;

public class SwipeToDeleteCallback extends ItemTouchHelper.SimpleCallback {

    private final SwipeToDeleteListener listener;

    public SwipeToDeleteCallback(SwipeToDeleteListener listener) {
        super(0, ItemTouchHelper.LEFT); // Configure swipe directions
        this.listener = listener;
    }

    @Override
    public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
        return false; // We don't handle move operations here
    }

    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
        int position = viewHolder.getAdapterPosition();
        //listener.onSwiped(position); // Notify the listener when an item is swiped

        // Show confirmation dialog
        new AlertDialog.Builder(viewHolder.itemView.getContext())
                .setTitle("Delete Confirmation")
                .setMessage("Are you sure you want to delete this item?")
                .setPositiveButton("Delete", (dialog, which) -> {
                    // Proceed with deletion
                    listener.onSwiped(position);
                })
                .setNegativeButton("Cancel", (dialog, which) -> {
                    // Notify adapter to restore the swiped item
                    dialog.dismiss();
                    if (position != RecyclerView.NO_POSITION) {
                        RecyclerView.Adapter adapter = ((RecyclerView) viewHolder.itemView.getParent()).getAdapter();
                        if (adapter != null) {
                            adapter.notifyItemChanged(position);
                        }
                    }
                })
                .setCancelable(false)
                .show();
    }

    public interface SwipeToDeleteListener {
        void onSwiped(int position);
    }
}

