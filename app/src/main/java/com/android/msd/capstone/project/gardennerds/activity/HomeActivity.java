package com.android.msd.capstone.project.gardennerds.activity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.android.msd.capstone.project.gardennerds.R;
import com.android.msd.capstone.project.gardennerds.broadcastReceivers.ReminderReceiver;
import com.android.msd.capstone.project.gardennerds.broadcastReceivers.newReminder.ReminderManager;
import com.android.msd.capstone.project.gardennerds.databinding.ActivityHomeBinding;
import com.android.msd.capstone.project.gardennerds.databinding.MenuDrawerHeaderBinding;
import com.android.msd.capstone.project.gardennerds.db.PlantDataSource;
import com.android.msd.capstone.project.gardennerds.fragments.AboutFragment;
import com.android.msd.capstone.project.gardennerds.fragments.HomeFragment;
import com.android.msd.capstone.project.gardennerds.fragments.MyGardenFragment;
import com.android.msd.capstone.project.gardennerds.fragments.ProfileFragment;
import com.android.msd.capstone.project.gardennerds.fragments.ScanMeasureGardenFragment;
import com.android.msd.capstone.project.gardennerds.fragments.SupportFragment;
import com.android.msd.capstone.project.gardennerds.models.Plant;
import com.android.msd.capstone.project.gardennerds.utils.Constants;
import com.android.msd.capstone.project.gardennerds.utils.Utility;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;

import java.util.Calendar;

/**
 * This is the main activity for the application. It manages the navigation drawer and the fragments that are displayed within the activity.
 * It also handles the toolbar and the back press.
 */
public class HomeActivity extends AppCompatActivity {
    private static final String TAG = HomeActivity.class.getSimpleName();
    ActionBarDrawerToggle toggle;
    MaterialToolbar materialToolbar;
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    NavigationView navDrawerMenu;
    MenuDrawerHeaderBinding navHeaderBinding;
    TextView userName;
    ActivityHomeBinding homeBinding;
    AlertDialog.Builder alertDialog;

    /**
     * This method is called when the activity is first created.
     * It initializes the toolbar, drawer layout, and navigation view. It also sets up the navigation drawer with its items and their respective click listeners.
     *
     * @param savedInstanceState
     */
    @SuppressLint("SourceLockedOrientationActivity")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        homeBinding = ActivityHomeBinding.inflate(getLayoutInflater());
        View view = homeBinding.getRoot();
        setContentView(view);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        // Set navigation bar and status bar color
        Utility.setNavigationAndStatusBarColor(this);



        // Initialize the toolbar, drawer layout, and navigation view.
        materialToolbar = homeBinding.homeToolBar;
        drawerLayout = homeBinding.drawerMenu;
        navigationView = homeBinding.navMenu;
        navDrawerMenu = homeBinding.navMenu;
        View headerView = navDrawerMenu.getHeaderView(0);
        navHeaderBinding = MenuDrawerHeaderBinding.bind(headerView);
        toggle = new ActionBarDrawerToggle(this, drawerLayout, materialToolbar, R.string.open, R.string.close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        setSupportActionBar(materialToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toggle.getDrawerArrowDrawable().setColor(getResources().getColor(R.color.colorAccent));
        setDrawer();
        setBottomNavigation();
        changeFragment(new HomeFragment());
        setUsernameInDrawer();
        getSupportFragmentManager().addOnBackStackChangedListener(new FragmentManager.OnBackStackChangedListener() {
            @Override
            public void onBackStackChanged() {
                Fragment currentFragment = getSupportFragmentManager().findFragmentById(homeBinding.frames.getId());
                updateTitle(currentFragment);
            }
        });

        /**MAnn's code*/
        if (getIntent().getBooleanExtra("showDialog", false)) {
            String reminderType = getIntent().getStringExtra("ReminderType");
            int plantId = getIntent().getIntExtra("PlantID", 0);
            showWateringReminderDialog(reminderType, plantId);
        }
//        if (getIntent().getBooleanExtra("showPopUP", false)) {
//            String reminderType = getIntent().getStringExtra("ReminderType");
//            showWateringReminderDialog(reminderType);
//        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.SCHEDULE_EXACT_ALARM) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.SCHEDULE_EXACT_ALARM}, Constants.REQUEST_CODE_SCHEDULE_EXACT_ALARM);
            }
        }
    }

    /**
     * This method sets up the navigation drawer with its items and their respective click listeners.
     */
    private void setDrawer() {
        navDrawerMenu.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                int i = item.getItemId();

                if (i == homeBinding.navMenu.getMenu().findItem(R.id.myProfile).getItemId()) {
                    changeFragment(new ProfileFragment());
                } else if (i == homeBinding.navMenu.getMenu().findItem(R.id.support).getItemId()) {
                    changeFragment(new SupportFragment());
                } else if (i == homeBinding.navMenu.getMenu().findItem(R.id.about).getItemId()) {
                    changeFragment(new AboutFragment());
                } else if (i == homeBinding.navMenu.getMenu().findItem(R.id.logout).getItemId()) {
                    showAppExitingAlertLogout(HomeActivity.this);
                }
                return false;
            }
        });
    }

    /**
     * This method inflates the menu for the toolbar.
     *
     * @param menu
     * @return
     */
    /**
     * Mann commented code
     */
//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        getMenuInflater().inflate(R.menu.appbar_menu, menu);
//
//        return super.onCreateOptionsMenu(menu);
//    }
    private void setBottomNavigation() {
        homeBinding.bottomNavigationView.setOnItemSelectedListener(new BottomNavigationView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                int i = item.getItemId();
                // Check the selected menu item ID
                if (i == R.id.btm_home) {
                    changeFragment(new HomeFragment());
                } else if (i == R.id.btm_my_garden) {
                    changeFragment(new MyGardenFragment());
                } else if (i == R.id.btm_scanner) {
                    changeFragment(new ScanMeasureGardenFragment());
                }
                return false;

            }
        });
    }

    /**
     * This method handles the selection of items in the toolbar.
     *
     * @param item
     * @return
     */
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.home) {
            changeFragment(new HomeFragment());
        } else if (id == R.id.search) {
            showSearchDialog();
        }

        if (toggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * This method handles the back press. If the current fragment is the home fragment, it shows an alert dialog to confirm the exit. Otherwise, it pops the back stack.
     */
    @SuppressLint("MissingSuperCall")
    @Override
    public void onBackPressed() {
        Fragment currentFragment = getSupportFragmentManager().findFragmentById(homeBinding.frames.getId());
        if (currentFragment instanceof HomeFragment) {
            showAppExitingAlert(this);
        } else {
            getSupportFragmentManager().popBackStack();
        }
    }

    /**
     * This method updates the title of the toolbar based on the current fragment.
     *
     * @param fragment
     */
    public void updateTitle(Fragment fragment) {
        String title = "";

        if (fragment instanceof ProfileFragment) {
            title = "Profile";
        } else if (fragment instanceof SupportFragment) {
            title = "Support";
        } else if (fragment instanceof AboutFragment) {
            title = "About";
        } else if (fragment instanceof HomeFragment) {
            title = getResources().getString(R.string.app_name);
        }

        if (!title.isEmpty()) {
            getSupportActionBar().setTitle(title);
        }
    }

    /**
     * This method changes the current fragment that is displayed in the activity.
     *
     * @param fragment
     */
    public void changeFragment(Fragment fragment) {
        updateTitle(fragment);

        FragmentManager supportFragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = supportFragmentManager.beginTransaction();

        // Check if the fragment is already in the stack
        String fragmentTag = fragment.getClass().getSimpleName();
        Fragment existingFragment = supportFragmentManager.findFragmentByTag(fragmentTag);

        if (existingFragment != null) {
            // If the fragment is already in the stack, pop back to it
            supportFragmentManager.popBackStack(fragmentTag, 0);

        } else {
            // If the fragment is not in the stack, add it
            fragmentTransaction.replace(homeBinding.frames.getId(), fragment, fragmentTag);
            if (!(fragment instanceof ScanMeasureGardenFragment))
                fragmentTransaction.addToBackStack(fragmentTag);
            fragmentTransaction.commit();
        }
        drawerLayout.closeDrawers();
    }

    /**
     * This method shows a dialog to search for a product.
     */
    public void showSearchDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this, R.style.MyDialogTheme);
        builder.setCustomTitle(Utility.showStyledAlertDialog(this));

        LinearLayout container = new LinearLayout(this);
        container.setOrientation(LinearLayout.VERTICAL);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        params.setMargins(50, 20, 50, 20);

        final EditText input = new EditText(this);
        input.setLayoutParams(params);
        input.setHint("Search for a product");
        input.setTextColor(getResources().getColor(R.color.colorAccent));
        input.setHintTextColor(getResources().getColor(R.color.colorGrayLight));
        container.addView(input);

        builder.setView(container);
        builder.setPositiveButton("Search", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                Toast.makeText(HomeActivity.this, "No product found", Toast.LENGTH_SHORT).show();
            }
        });
        builder.setNegativeButton("Cancel", null);
        builder.show();
    }

    /**
     * This method shows an alert dialog to confirm the exit of the application.
     *
     * @param context
     */
    public void showAppExitingAlert(final Context context) {
        alertDialog = new AlertDialog.Builder(context, R.style.MyDialogTheme);
        alertDialog.setCustomTitle(Utility.showStyledAlertDialog(context));
        alertDialog.setMessage(getString(R.string.msg_app_exit));
        alertDialog.setPositiveButton(getString(R.string.button_ok),
                (dialog, which) -> {
                    dialog.dismiss();
                    alertDialog = null;
                    Log.v(TAG, "App Exited via Alert Dialog");
                    Log.v(TAG, "from exitDialog HomeActivity called");
                    //HomeActivity.this.finishAffinity();
                    HomeActivity.this.finishAndRemoveTask();
                });
        alertDialog.setNegativeButton(getString(R.string.button_cancel), (dialog, which) -> {
            dialog.dismiss();
            alertDialog = null;
        });
        alertDialog.show();
    }

    /**
     * This method shows the username in the navigation drawer.
     */
    public void setUsernameInDrawer() {
        userName = navHeaderBinding.welcomeUser;
        SharedPreferences sharedPreferences = getSharedPreferences("Login_Username", Context.MODE_PRIVATE);
        String getUsername = sharedPreferences.getString("userName", "");
        userName.setText("Welcome, " + Utility.capitalizeFirstLetter(getUsername));
    }

    /**
     * This method shows an alert dialog to confirm the logout of the application.
     *
     * @param context
     */
    public void showAppExitingAlertLogout(final Context context) {
        alertDialog = new AlertDialog.Builder(context, R.style.MyDialogTheme);
        alertDialog.setCustomTitle(Utility.showStyledAlertDialog(context));
        alertDialog.setMessage(getString(R.string.msg_app_exit));
        alertDialog.setPositiveButton(getString(R.string.button_ok),
                (dialog, which) -> {
                    dialog.dismiss();
                    alertDialog = null;
                    Log.v(TAG, "App Exited via Alert Dialog");
                    Log.v(TAG, "from exitDialog HomeActivity called");
                    Intent intent = new Intent(this, LoginScreenActivity.class);
                    startActivity(intent);
                    finish();
                });
        alertDialog.setNegativeButton(getString(R.string.button_cancel), (dialog, which) -> {
            dialog.dismiss();
            alertDialog = null;
        });
        alertDialog.show();
    }

    /**
     * Mann's Code
     */
    private void showWateringReminderDialog(String reminderType, int plantID) {


        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View dialogView = inflater.inflate(R.layout.custom_dialog_reminder, null);

        PlantDataSource plantDataSource = new PlantDataSource(this);
        Plant plant = plantDataSource.getPlant(plantID);

        // Initialize UI elements from the custom dialog layout
        Button snoozeBtn = dialogView.findViewById(R.id.btnSnooze);
        Button doneBtn = dialogView.findViewById(R.id.btnDone);
        ImageView image = dialogView.findViewById(R.id.ivReminderType);
        TextView reminderTitle = dialogView.findViewById(R.id.tvReminderTitle);
        TextView reminderMessage = dialogView.findViewById(R.id.tvReminderMessage);

//        if (Objects.equals(reminderType, "Fertilizer")){
//            image.setImageResource(R.drawable.fertilize);
//        }
        assert reminderType != null;
        if (plantID != 0) {
            switch (reminderType) {
                case "Fertilize":
                    reminderTitle.setText("Fertilize " + plant.getPlantName() + "!");
                    reminderMessage.setText("It's time to fertilize " + plant.getPlantName() + " for healthy growth.");
                    image.setImageResource(R.drawable.fertilize);
                    break;
                case "Watering":
                    reminderTitle.setText("Water " + plant.getPlantName() + "!");
                    reminderMessage.setText("It's time to water " + plant.getPlantName() + ". Keep it hydrated!");
                    image.setImageResource(R.drawable.watering_plants);
                    break;
                case "Sunlight":
                    reminderTitle.setText("Provide Sunlight to " + plant.getPlantName() + "!");
                    reminderMessage.setText("Ensure " + plant.getPlantName() + " gets the required amount of sunlight.");
                    image.setImageResource(R.drawable.sunlight);
                    break;
                case "Change Soil":
                    reminderTitle.setText("Change the Soil for " + plant.getPlantName() + "!");
                    reminderMessage.setText(plant.getPlantName() + " may need new soil for better growth.");
                    image.setImageResource(R.drawable.soil);
                    break;
                default:
                    reminderTitle.setText("Reminder for " + plant.getPlantName() + "!");
                    reminderMessage.setText("Don't forget to take care of " + plant.getPlantName() + "!");
                    image.setImageResource(R.drawable.ic_plant); // Use a generic image
                    break;
            }
        } else {

            switch (reminderType) {
                case "Fertilize":
                    reminderTitle.setText("Fertilize Your Plants!");
                    reminderMessage.setText("It's time to fertilize your plants for healthy growth.");
                    image.setImageResource(R.drawable.fertilize);
                    break;
                case "Watering":


                    reminderTitle.setText("Water Your Plants!");
                    reminderMessage.setText("It's time to water your plants. Keep them hydrated!");
                    image.setImageResource(R.drawable.watering_plants);
                    break;
                case "Sunlight":

                    reminderTitle.setText("Provide Sunlight to Your Plants!");
                    reminderMessage.setText("Ensure your plants get the required amount of sunlight.");
                    image.setImageResource(R.drawable.sunlight);

                    break;
                case "Change Soil":

                    reminderTitle.setText("Change the Soil!");
                    reminderMessage.setText("Your plants may need new soil for better growth.");
                    image.setImageResource(R.drawable.soil);

                    break;
            }

        }

        // Create the dialog
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setView(dialogView)
                .setCancelable(true);
        AlertDialog dialog = builder.create();
        // Handle button click
        doneBtn.setOnClickListener(v -> {
//            String inputText = inputEditText.getText().toString();
            // Do something with the input text
            Toast.makeText(this, "Done", Toast.LENGTH_SHORT).show();
            // Dismiss the dialog
            dialog.dismiss();
        });

        snoozeBtn.setOnClickListener(v -> {
//            String inputText = inputEditText.getText().toString();
            // Do something with the input text
//            setSnoozeReminder(reminderType);
            Utility.setSnoozeReminder(reminderType, plantID, this);
            Toast.makeText(this, "Snooze: Reminder set for 5 Minutes", Toast.LENGTH_SHORT).show();
            // Dismiss the dialog
            dialog.dismiss();
        });


        // Show the dialog
//        AlertDialog dialog = builder.create();
        dialog.show();
    }


    private void setSnoozeReminder(String reminderType) {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.SECOND, 20); // Set reminder for 1 minute later (adjust as needed)

        Intent intent = new Intent(this, ReminderReceiver.class).putExtra("ReminderType", reminderType);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(
                this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE
        );

        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        if (alarmManager != null) {

            alarmManager.setExact(
                    AlarmManager.RTC_WAKEUP,
                    calendar.getTimeInMillis(),
                    pendingIntent
            );


        }
    }

    /*public androidx.appcompat.widget.Toolbar getToolbar() {
        return homeBinding.homeToolBar;
    }*/

    // Method to change the navigation icon from a fragment
    /*public void setNavigationIcon(int resId) {
        if (getSupportActionBar() != null) {
            getSupportActionBar().setHomeAsUpIndicator(resId);  // Change the icon
        }
    }*/

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == Constants.REQUEST_CODE_SCHEDULE_EXACT_ALARM) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission granted, you can now set exact alarms
            } else {
                // Permission denied, handle accordingly
            }
        }
    }
}
