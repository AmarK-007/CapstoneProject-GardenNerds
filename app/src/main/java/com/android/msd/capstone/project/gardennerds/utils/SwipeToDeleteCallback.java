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

/**
 * Callback class for handling swipe-to-delete actions in a RecyclerView.
 */
public class SwipeToDeleteCallback extends ItemTouchHelper.SimpleCallback {

    private final SwipeToDeleteListener listener;

    /**
     * Constructor for SwipeToDeleteCallback.
     *
     * @param listener The listener to notify when an item is swiped.
     */
    public SwipeToDeleteCallback(SwipeToDeleteListener listener) {
        super(0, ItemTouchHelper.LEFT); // Configure swipe directions
        this.listener = listener;
    }

    /**
     * Called when an item is moved. Not used in this implementation.
     *
     * @param recyclerView The RecyclerView.
     * @param viewHolder The ViewHolder being moved.
     * @param target The target ViewHolder.
     * @return Always returns false as move operations are not handled.
     */
    @Override
    public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
        return false; // We don't handle move operations here
    }

    /**
     * Called when an item is swiped.
     *
     * @param viewHolder The ViewHolder being swiped.
     * @param direction The direction of the swipe.
     */
    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
        int position = viewHolder.getAdapterPosition();

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

    /**
     * Interface for notifying when an item is swiped.
     */
    public interface SwipeToDeleteListener {
        void onSwiped(int position);
    }
}