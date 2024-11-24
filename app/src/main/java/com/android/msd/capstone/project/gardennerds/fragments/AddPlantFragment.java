package com.android.msd.capstone.project.gardennerds.fragments;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.msd.capstone.project.gardennerds.R;
import com.android.msd.capstone.project.gardennerds.broadcastReceivers.ReminderReceiver;
import com.android.msd.capstone.project.gardennerds.databinding.FragmentAddPlantBinding;
import com.android.msd.capstone.project.gardennerds.models.Plant;
import com.android.msd.capstone.project.gardennerds.models.Reminder;
import com.android.msd.capstone.project.gardennerds.utils.Utility;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AddPlantFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AddPlantFragment extends Fragment implements View.OnClickListener, AddReminderFragment.OnReminderAddedListener {

    private static final String TAG = AddPlantFragment.class.getSimpleName();
    private FragmentAddPlantBinding addPlantBinding;

    private List<Reminder> reminderlist = new ArrayList<>();
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public AddPlantFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AddPlantFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static AddPlantFragment newInstance(String param1, String param2) {
        AddPlantFragment fragment = new AddPlantFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        addPlantBinding = FragmentAddPlantBinding.inflate(inflater, container, false);
        init();
        return addPlantBinding.getRoot();
    }

    private void init() {
        addPlantBinding.fabAddReminder.setOnClickListener(this);
        addPlantBinding.fabSavePlant.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == addPlantBinding.fabSavePlant.getId()) {
            savePlant();
        } else if (v.getId() == addPlantBinding.fabAddReminder.getId()) {
            // Replace the current fragment with AddPlantFragment
            requireActivity().getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.frames, new AddReminderFragment())
                    .addToBackStack(null)  // Add this transaction to the back stack
                    .commit();
        }
    }

    private void savePlant() {
        if (validatePlant()) {
            Plant plant = new Plant();
            plant.setPlantName(addPlantBinding.edtPlantName.getText().toString());
            plant.setPlantType(addPlantBinding.rgPlantType.getCheckedRadioButtonId() == R.id.rbIndoor ? getString(R.string.text_indoor) : getString(R.string.text_outdoor));
            plant.setMoistureLevel(addPlantBinding.edtPlantMoistureLevel.getText().toString());
            plant.setTemperatureLevel(addPlantBinding.edtPlantTemperature.getText().toString());
            plant.setWateringInterval(addPlantBinding.edtPlantWateringInterval.getText().toString());
            plant.setSunlightLevel(addPlantBinding.edtPlantSunlightRequired.getText().toString());
            plant.setNutrientRequired(addPlantBinding.edtPlantNutritionRequired.getText().toString());
            /**Mann's Code*/
//            setWateringReminder();
            /**till here*/
            // Pass data back to GardenDetailsFragment
            if (getParentFragment() instanceof OnPlantAddedListener) {
                ((OnPlantAddedListener) getParentFragment()).onPlantAdded(plant);
            }
            // move back to previous fragment with plant object
            getActivity().getSupportFragmentManager().popBackStack();
        }
    }

    /**Mann's code*/
    private void setWateringReminder(String reminderType) {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.SECOND, 20); // Set reminder for 1 minute later (adjust as needed)

        Intent intent = new Intent(requireActivity(), ReminderReceiver.class).putExtra("ReminderType",reminderType);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(
                requireContext(), 0, intent, PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE
        );

        AlarmManager alarmManager = (AlarmManager) requireContext().getSystemService(Context.ALARM_SERVICE);
        if (alarmManager != null) {

                alarmManager.setExact(
                        AlarmManager.RTC_WAKEUP,
                        calendar.getTimeInMillis(),
                        pendingIntent
                );


        }
    }


    private void setAlarmsForFrequency(int frequency,String reminderType) {
        Calendar calendar = Calendar.getInstance();

        // Start time (e.g., 10 AM)
        calendar.set(Calendar.HOUR_OF_DAY, 12);  // 10 AM
        calendar.set(Calendar.MINUTE, 58);
        calendar.set(Calendar.SECOND, 0);

        // Loop to set alarms based on the frequency
        for (int i = 0; i < frequency; i++) {
            // Calculate the time for each alarm
            Calendar alarmTime = (Calendar) calendar.clone();
            alarmTime.add(Calendar.HOUR_OF_DAY, i * 2);  // Increment hours by 2 for each frequency

            // Log alarm times (for debugging)
            Log.d("Alarm", "Setting alarm for: " + alarmTime.getTime());

            // Set the alarm
            setAlarm(alarmTime,i,reminderType);
        }
    }

    // Helper method to set a single alarm
    private void setAlarm(Calendar alarmTime,int uniqueCode,String reminderType) {
        Intent intent = new Intent(requireContext(), ReminderReceiver.class).putExtra("ReminderType",reminderType);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(requireContext(), uniqueCode, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        AlarmManager alarmManager = (AlarmManager) requireContext().getSystemService(Context.ALARM_SERVICE);

        // Set the alarm to trigger at the exact time
        if (alarmManager != null) {
            alarmManager.setExact(AlarmManager.RTC_WAKEUP, alarmTime.getTimeInMillis(), pendingIntent);
        }
    }

    /**Till here*/

    private boolean validatePlant() {
        /**Mann's Code*/
//        setWateringReminder();//yet to implement in save plant

        /**Reminder Type
         * Fertilize
         * Watering
         * Sunlight
         * Change Soil
         * */
        setAlarmsForFrequency(1,"Fertilize");
        /**till here*/
        if (addPlantBinding.edtPlantName.getText().toString().isEmpty() ||
                addPlantBinding.rgPlantType.getCheckedRadioButtonId() == -1 ||
                addPlantBinding.edtPlantMoistureLevel.getText().toString().isEmpty() ||
                addPlantBinding.edtPlantTemperature.getText().toString().isEmpty() ||
                addPlantBinding.edtPlantWateringInterval.getText().toString().isEmpty() ||
                addPlantBinding.edtPlantSunlightRequired.getText().toString().isEmpty() ||
                addPlantBinding.edtPlantNutritionRequired.getText().toString().isEmpty()) {
            Toast.makeText(getContext(), "Please fill all fields", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    @Override
    public void onReminderAdded(Reminder reminder) {
        reminderlist.add(reminder);

    }

    public interface OnPlantAddedListener {
        void onPlantAdded(Plant plant);
    }
}