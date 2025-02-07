package com.android.msd.capstone.project.gardennerds.fragments;

import android.Manifest;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.android.msd.capstone.project.gardennerds.R;
import com.android.msd.capstone.project.gardennerds.databinding.FragmentAddReminderBinding;
import com.android.msd.capstone.project.gardennerds.models.Reminder;
import com.android.msd.capstone.project.gardennerds.models.SharedViewModel;
import com.android.msd.capstone.project.gardennerds.utils.Constants;
import com.android.msd.capstone.project.gardennerds.utils.Utility;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AddReminderFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AddReminderFragment extends Fragment implements View.OnClickListener, RadioGroup.OnCheckedChangeListener {

    private static final String TAG = AddReminderFragment.class.getSimpleName();
    private FragmentAddReminderBinding addReminderBinding;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private SharedViewModel sharedViewModel;

    // TODO: Rename and change types of parameters
    private int plantId;

    public AddReminderFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @return A new instance of fragment AddReminderFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static AddReminderFragment newInstance(int param1) {
        AddReminderFragment fragment = new AddReminderFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_PARAM1, param1);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sharedViewModel = new ViewModelProvider(requireActivity()).get(SharedViewModel.class);
        if (getArguments() != null) {
            plantId = getArguments().getInt(ARG_PARAM1);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        addReminderBinding = FragmentAddReminderBinding.inflate(inflater, container, false);
        init();
        return addReminderBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        // Ensure Activity and ActionBar are available
        if (getActivity() != null && getActivity() instanceof AppCompatActivity) {
            AppCompatActivity activity = (AppCompatActivity) getActivity();
            activity.getSupportActionBar().setTitle(getString(R.string.text_add_reminder));
        }
    }

    private void init() {
        // Initialize views

        addReminderBinding.rgReminderType.setOnCheckedChangeListener(this);
        addReminderBinding.edtTime.setOnClickListener(this);
        addReminderBinding.fabSaveReminder.setOnClickListener(this);
        setFormVisibiltyBasedOnSelection(addReminderBinding.rgReminderType.getCheckedRadioButtonId());
        requestNotificationPermission();
    }

    private void setFormVisibiltyBasedOnSelection(int checkedId) {

        addReminderBinding.edtMoistureLevel.setVisibility(View.GONE);
        addReminderBinding.edtTemperature.setVisibility(View.GONE);
        addReminderBinding.edtSunlightRequired.setVisibility(View.GONE);
        addReminderBinding.edtNutritionRequired.setVisibility(View.GONE);

        addReminderBinding.edtFrequency.setVisibility(View.VISIBLE);
        addReminderBinding.edtTime.setVisibility(View.VISIBLE);
        addReminderBinding.edtTime.setHint("Please enter time in HH:MM format");
        if (checkedId == addReminderBinding.rbWatering.getId()) {
            addReminderBinding.edtMoistureLevel.setVisibility(View.VISIBLE);
            addReminderBinding.edtFrequency.setHint("Please enter Frequency between 1 - 30 in days");
        } else if (checkedId == addReminderBinding.rbFertilize.getId()) {
            addReminderBinding.edtNutritionRequired.setVisibility(View.VISIBLE);
            addReminderBinding.edtFrequency.setHint("Please enter Frequency between 1 - 90 in days");
        } else if (checkedId == addReminderBinding.rbSunlight.getId()) {
            addReminderBinding.edtTemperature.setVisibility(View.VISIBLE);
            addReminderBinding.edtSunlightRequired.setVisibility(View.VISIBLE);
            addReminderBinding.edtFrequency.setHint("Please enter Frequency between 1 - 30 in days");
        } else if (checkedId == addReminderBinding.rbChangeSoil.getId()) {
            addReminderBinding.edtFrequency.setHint("Please enter Frequency between 1 - 365 in days");
        }
    }


    private void saveReminder() {
        // Save reminder
        if (validateReminder()) {
            Reminder reminder = new Reminder();
            if (addReminderBinding.rgReminderType.getCheckedRadioButtonId() == addReminderBinding.rbWatering.getId()) {
                reminder.setReminderTypeId(Constants.REMINDER_TYPE_WATER);
                reminder.setFrequency(addReminderBinding.edtFrequency.getText().toString());
                reminder.setMoistureLevel(addReminderBinding.edtMoistureLevel.getText().toString());
                reminder.setReminderTime(addReminderBinding.edtTime.getText().toString());
            } else if (addReminderBinding.rgReminderType.getCheckedRadioButtonId() == addReminderBinding.rbFertilize.getId()) {
                reminder.setReminderTypeId(Constants.REMINDER_TYPE_FERTILIZE);
                reminder.setFrequency(addReminderBinding.edtFrequency.getText().toString());
                reminder.setNutrientRequired(addReminderBinding.edtNutritionRequired.getText().toString());
                reminder.setReminderTime(addReminderBinding.edtTime.getText().toString());
            } else if (addReminderBinding.rgReminderType.getCheckedRadioButtonId() == addReminderBinding.rbSunlight.getId()) {
                reminder.setReminderTypeId(Constants.REMINDER_TYPE_SUNLIGHT);
                reminder.setFrequency(addReminderBinding.edtFrequency.getText().toString());
                reminder.setTemperatureLevel(addReminderBinding.edtTemperature.getText().toString());
                reminder.setSunlightLevel(addReminderBinding.edtSunlightRequired.getText().toString());
                reminder.setReminderTime(addReminderBinding.edtTime.getText().toString());
            } else if (addReminderBinding.rgReminderType.getCheckedRadioButtonId() == addReminderBinding.rbChangeSoil.getId()) {
                reminder.setReminderTypeId(Constants.REMINDER_TYPE_CHANGE_SOIL);
                reminder.setFrequency(addReminderBinding.edtFrequency.getText().toString());
                reminder.setReminderTime(addReminderBinding.edtTime.getText().toString());
            }
            reminder.setDateTime(Utility.getCurrentDateTime());

            sharedViewModel.setReminder(reminder);
            if (plantId > 0) {
                Log.d("AddReminder", "Reminder id " + reminder.getReminderTypeId());
                //this means we are adding reminder from plant detail fragment
                reminder.setPlantId(plantId);
                //Utility.setAlarmsForFrequency(requireContext(), 0, Integer.parseInt(reminder.getFrequency()), reminder.getReminderTypeId());

            }

            /*// Pass data back to parent activity
            if (getActivity() instanceof OnReminderAddedListener) {
                ((OnReminderAddedListener) getActivity()).onReminderAdded(reminder);
            }*/
            // move back to previous fragment with reminder object
            //mann
            /**Reminder Type
             * Fertilize
             * Watering
             * Sunlight
             * Change Soil
             * */
            //Utility.setAlarmsForFrequency(requireContext(),1,reminder.getReminderTypeId(),plantId);
            Log.e("AddReminder", String.valueOf(reminder.getReminderId()) + " Also plantID " + plantId);

            if (plantId > 0) {
                //which means flow is from plantdetailsfragment
            }
            getActivity().getSupportFragmentManager().popBackStack();

        }

    }

    private boolean validateReminder() {
        // Validate reminder
        if (addReminderBinding.rgReminderType.getCheckedRadioButtonId() == -1) {
            Toast.makeText(getContext(), "Please select a reminder type", Toast.LENGTH_SHORT).show();
            return false;
        } else if (addReminderBinding.rgReminderType.getCheckedRadioButtonId() == addReminderBinding.rbWatering.getId()) {
            if (addReminderBinding.edtFrequency.getText().toString().isEmpty() || addReminderBinding.edtMoistureLevel.getText().toString().isEmpty()
                    || addReminderBinding.edtTime.getText().toString().isEmpty()) {
                Toast.makeText(getContext(), "Please fill all fields", Toast.LENGTH_SHORT).show();
                return false;
            } else if (Integer.parseInt(addReminderBinding.edtFrequency.getText().toString()) < 1 || Integer.parseInt(addReminderBinding.edtFrequency.getText().toString()) > 30) {
                Toast.makeText(getContext(), "Please enter Frequency between 1 - 30", Toast.LENGTH_SHORT).show();
                addReminderBinding.edtFrequency.setError("Please enter Frequency between 1 - 30");
                return false;
            } else if (Integer.parseInt(addReminderBinding.edtMoistureLevel.getText().toString()) < 1 || Integer.parseInt(addReminderBinding.edtMoistureLevel.getText().toString()) > 100) {
                Toast.makeText(getContext(), "Please enter Moisture Level between 1 - 100", Toast.LENGTH_SHORT).show();
                addReminderBinding.edtMoistureLevel.setError("Please enter Moisture Level between 1 - 100");
                return false;
            } else if (!Utility.isValidTime(addReminderBinding.edtTime.getText().toString())) {
                Toast.makeText(getContext(), "Please enter valid time in HH:MM format", Toast.LENGTH_SHORT).show();
                addReminderBinding.edtTime.setError("Please enter valid time in HH:MM format");
                return false;
            }
        } else if (addReminderBinding.rgReminderType.getCheckedRadioButtonId() == addReminderBinding.rbFertilize.getId()) {
            if (addReminderBinding.edtFrequency.getText().toString().isEmpty() || addReminderBinding.edtNutritionRequired.getText().toString().isEmpty()) {
                Toast.makeText(getContext(), "Please fill all fields", Toast.LENGTH_SHORT).show();
                return false;
            } else if (Integer.parseInt(addReminderBinding.edtFrequency.getText().toString()) < 1 || Integer.parseInt(addReminderBinding.edtFrequency.getText().toString()) > 90) {
                Toast.makeText(getContext(), "Please enter Frequency between 1 - 90", Toast.LENGTH_SHORT).show();
                addReminderBinding.edtFrequency.setError("Please enter Frequency between 1 - 90");
                return false;
            } else if (!Utility.isValidTime(addReminderBinding.edtTime.getText().toString())) {
                Toast.makeText(getContext(), "Please enter valid time in HH:MM format", Toast.LENGTH_SHORT).show();
                addReminderBinding.edtTime.setError("Please enter valid time in HH:MM format");
                return false;
            }
        } else if (addReminderBinding.rgReminderType.getCheckedRadioButtonId() == addReminderBinding.rbSunlight.getId()) {
            if (addReminderBinding.edtFrequency.getText().toString().isEmpty() || addReminderBinding.edtTemperature.getText().toString().isEmpty() || addReminderBinding.edtSunlightRequired.getText().toString().isEmpty()) {
                Toast.makeText(getContext(), "Please fill all fields", Toast.LENGTH_SHORT).show();
                return false;
            } else if (Integer.parseInt(addReminderBinding.edtFrequency.getText().toString()) < 1 || Integer.parseInt(addReminderBinding.edtFrequency.getText().toString()) > 30) {
                Toast.makeText(getContext(), "Please enter Frequency between 1 - 30", Toast.LENGTH_SHORT).show();
                addReminderBinding.edtFrequency.setError("Please enter Frequency between 1 - 30");
                return false;
            } else if (Integer.parseInt(addReminderBinding.edtTemperature.getText().toString()) < 1 || Integer.parseInt(addReminderBinding.edtTemperature.getText().toString()) > 50) {
                Toast.makeText(getContext(), "Please enter Temperature between 1 - 50", Toast.LENGTH_SHORT).show();
                addReminderBinding.edtTemperature.setError("Please enter Temperature between 1 - 50");
                return false;
            } else if (Integer.parseInt(addReminderBinding.edtSunlightRequired.getText().toString()) < 1 || Integer.parseInt(addReminderBinding.edtSunlightRequired.getText().toString()) > 24) {
                Toast.makeText(getContext(), "Please enter Sunlight Required between 1 - 24", Toast.LENGTH_SHORT).show();
                addReminderBinding.edtSunlightRequired.setError("Please enter Sunlight Required between 1 - 24");
                return false;
            } else if (!Utility.isValidTime(addReminderBinding.edtTime.getText().toString())) {
                Toast.makeText(getContext(), "Please enter valid time in HH:MM format", Toast.LENGTH_SHORT).show();
                addReminderBinding.edtTime.setError("Please enter valid time in HH:MM format");
                return false;
            }
        } else if (addReminderBinding.rgReminderType.getCheckedRadioButtonId() == addReminderBinding.rbChangeSoil.getId()) {
            if (addReminderBinding.edtFrequency.getText().toString().isEmpty()) {
                Toast.makeText(getContext(), "Please fill all fields", Toast.LENGTH_SHORT).show();
                return false;
            } else if (Integer.parseInt(addReminderBinding.edtFrequency.getText().toString()) < 1 || Integer.parseInt(addReminderBinding.edtFrequency.getText().toString()) > 365) {
                Toast.makeText(getContext(), "Please enter Frequency between 1 - 365", Toast.LENGTH_SHORT).show();
                addReminderBinding.edtFrequency.setError("Please enter Frequency between 1 - 365");
                return false;
            } else if (!Utility.isValidTime(addReminderBinding.edtTime.getText().toString())) {
                Toast.makeText(getContext(), "Please enter valid time in HH:MM format", Toast.LENGTH_SHORT).show();
                addReminderBinding.edtTime.setError("Please enter valid time in HH:MM format");
                return false;
            }
        }
        return true;
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {

        if (group.getId() == addReminderBinding.rgReminderType.getId()) {
            setFormVisibiltyBasedOnSelection(checkedId);
        }
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == addReminderBinding.fabSaveReminder.getId()) {
            saveReminder();
        } else if (v.getId() == addReminderBinding.edtTime.getId()) {
            Utility.showTimePickerDialog(requireContext(), addReminderBinding.edtTime);
        }
    }

    public interface OnReminderAddedListener {
        void onReminderAdded(Reminder reminder);
    }

    // ad permission to postNotification after checking if not available request live

    private void requestNotificationPermission() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(requireActivity(), Manifest.permission.POST_NOTIFICATIONS)) {
            new AlertDialog.Builder(requireActivity())
                    .setTitle("Permission needed")
                    .setMessage("This permission is needed to set reminders for your plants")
                    .setPositiveButton("OK", (dialog, which) -> {
                        ActivityCompat.requestPermissions(requireActivity(), new String[]{Manifest.permission.POST_NOTIFICATIONS}, Constants.REQUEST_CODE_NOTIFICATION_PERMISSION);
                    })
                    .setNegativeButton("Cancel", (dialog, which) -> dialog.dismiss())
                    .create().show();
        } else {
            ActivityCompat.requestPermissions(requireActivity(), new String[]{Manifest.permission.POST_NOTIFICATIONS}, Constants.REQUEST_CODE_NOTIFICATION_PERMISSION);
        }
    }
}