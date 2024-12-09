package com.android.msd.capstone.project.wear.gardennerds.activity;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.android.msd.capstone.project.wear.gardennerds.R;
import com.android.msd.capstone.project.wear.gardennerds.adapters.RemindersListAdapter;
import com.android.msd.capstone.project.wear.gardennerds.databinding.ActivityReminderListBinding;
import com.android.msd.capstone.project.wear.gardennerds.db.ReminderDataSource;
import com.android.msd.capstone.project.wear.gardennerds.models.Reminder;

import java.util.ArrayList;

public class ReminderListActivity extends AppCompatActivity {

    ActivityReminderListBinding binding;
    ArrayList<Reminder> remindersArrayList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        binding = ActivityReminderListBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        // Initialize and set up the RecyclerView
        setRecyclerView();


    }

    public void setRecyclerView() {


        loadreminders();
        binding.rvReminders.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        binding.rvReminders.setAdapter(new RemindersListAdapter(remindersArrayList, this));

    }

    private void loadreminders() {
        ReminderDataSource reminderDataSource = new ReminderDataSource(this);
        remindersArrayList.clear();
        remindersArrayList.addAll(reminderDataSource.getAllReminders());
        if (remindersArrayList == null || remindersArrayList.isEmpty()) {
            addDemoReminders();
        }
    }

    private void addDemoReminders() {
        remindersArrayList.add(new Reminder("Red Anthrium", "6", 1, "12:00AM"));
        remindersArrayList.add(new Reminder("Red Anthrium", "60", 2, "12:00AM"));
        remindersArrayList.add(new Reminder("Red Anthrium", "6", 3, "12:00AM"));
        remindersArrayList.add(new Reminder("Red Anthrium", "60", 4, "12:00AM"));
        remindersArrayList.add(new Reminder("Pine", "6", 1, "12:00AM"));
        remindersArrayList.add(new Reminder("Pine", "60", 2, "12:00AM"));
    }
}