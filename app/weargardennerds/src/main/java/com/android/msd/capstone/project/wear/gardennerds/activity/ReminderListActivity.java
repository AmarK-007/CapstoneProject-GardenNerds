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
import com.android.msd.capstone.project.wear.gardennerds.models.tempModels.Reminders;

import java.util.ArrayList;

public class ReminderListActivity extends AppCompatActivity {

    ActivityReminderListBinding binding;


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

        setRecyclerView();


    }

    public void setRecyclerView(){
        ArrayList<Reminders> remindersArrayList = new ArrayList<>();
        remindersArrayList.add(new Reminders("Jade","2","Watering"));
        remindersArrayList.add(new Reminders("Rose","1","Fertilize"));
        remindersArrayList.add(new Reminders("Basil","3","Sun Light"));

        binding.rvReminders.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));
        binding.rvReminders.setAdapter(new RemindersListAdapter(remindersArrayList,this));

    }
}