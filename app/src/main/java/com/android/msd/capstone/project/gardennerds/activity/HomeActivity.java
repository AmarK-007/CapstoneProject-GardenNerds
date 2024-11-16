package com.android.msd.capstone.project.gardennerds.activity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.android.msd.capstone.project.gardennerds.R;
import com.android.msd.capstone.project.gardennerds.databinding.ActivityHomeBinding;
import com.android.msd.capstone.project.gardennerds.databinding.MenuDrawerHeaderBinding;
import com.android.msd.capstone.project.gardennerds.fragments.AboutFragment;
import com.android.msd.capstone.project.gardennerds.fragments.HomeFragment;
import com.android.msd.capstone.project.gardennerds.fragments.ProfileFragment;
import com.android.msd.capstone.project.gardennerds.fragments.SupportFragment;
import com.android.msd.capstone.project.gardennerds.utils.Utility;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;

/**
 * This is the main activity for the application. It manages the navigation drawer and the fragments that are displayed within the activity.
 * It also handles the toolbar and the back press.
 */
public class HomeActivity extends AppCompatActivity {
    ActionBarDrawerToggle toggle;
    MaterialToolbar materialToolbar;
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    NavigationView navDrawerMenu;
    MenuDrawerHeaderBinding navHeaderBinding;
    TextView userName;
    ActivityHomeBinding homeBinding;

    private static final String TAG = HomeActivity.class.getSimpleName();

    /**
     * This method is called when the activity is first created.
     * It initializes the toolbar, drawer layout, and navigation view. It also sets up the navigation drawer with its items and their respective click listeners.
     *
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        homeBinding = ActivityHomeBinding.inflate(getLayoutInflater());
        View view = homeBinding.getRoot();
        setContentView(view);

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
        changeFragment(new HomeFragment());
        setUsernameInDrawer();
        getSupportFragmentManager().addOnBackStackChangedListener(new FragmentManager.OnBackStackChangedListener() {
            @Override
            public void onBackStackChanged() {
                Fragment currentFragment = getSupportFragmentManager().findFragmentById(homeBinding.frames.getId());
                updateTitle(currentFragment);
            }
        });
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
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.appbar_menu, menu);

        return super.onCreateOptionsMenu(menu);
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
        fragmentTransaction.replace(homeBinding.frames.getId(), fragment);
        fragmentTransaction.addToBackStack(null); // Add this line
        fragmentTransaction.commit();
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

    AlertDialog.Builder alertDialog;

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
}
