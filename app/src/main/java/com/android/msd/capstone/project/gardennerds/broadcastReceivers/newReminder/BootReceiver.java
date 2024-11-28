package com.android.msd.capstone.project.gardennerds.broadcastReceivers.newReminder;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.android.msd.capstone.project.gardennerds.db.ReminderDataSource;

public class BootReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals(Intent.ACTION_BOOT_COMPLETED)) {
            // Reschedule the reminder after reboot
            ReminderManager reminderManager = new ReminderManager(context);
            ReminderDataSource reminderDataSource = new ReminderDataSource(context);
            reminderDataSource.getAllReminders().forEach(reminder -> {
                reminderManager.startReminder(reminder.getReminderId());
            });
             // Set for 9 AM
        }
    }
}