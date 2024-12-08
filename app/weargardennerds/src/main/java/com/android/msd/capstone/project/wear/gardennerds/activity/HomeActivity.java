package com.android.msd.capstone.project.wear.gardennerds.activity;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.wearable.view.WearableDialogHelper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.wear.activity.ConfirmationActivity;

import com.android.msd.capstone.project.wear.gardennerds.db.GardenDataSource;
import com.android.msd.capstone.project.wear.gardennerds.db.PlantDataSource;
import com.android.msd.capstone.project.wear.gardennerds.db.ReminderDataSource;
import com.android.msd.capstone.project.wear.gardennerds.receiver.DataReceiver;
import com.android.msd.capstone.project.wear.gardennerds.utils.DataRequestUtil;
import com.android.msd.capstone.project.wear.gardennerds.R;
import com.android.msd.capstone.project.wear.gardennerds.databinding.ActivityHomeBinding;
import com.android.msd.capstone.project.wear.gardennerds.databinding.CustomDialogLayoutBinding;
import com.android.msd.capstone.project.wear.gardennerds.utils.Utility;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.wearable.CapabilityClient;
import com.google.android.gms.wearable.CapabilityInfo;
import com.google.android.gms.wearable.MessageClient;
import com.google.android.gms.wearable.MessageEvent;
import com.google.android.gms.wearable.Node;
import com.google.android.gms.wearable.Wearable;

import java.util.Arrays;
import java.util.Set;

public class HomeActivity extends AppCompatActivity implements MessageClient.OnMessageReceivedListener{

    private static final String TAG = HomeActivity.class.getSimpleName();
    private ActivityHomeBinding activityHomeBinding;

    private Dialog loginDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        activityHomeBinding = ActivityHomeBinding.inflate(getLayoutInflater());
        View view = activityHomeBinding.getRoot();
        setContentView(view);

        init();

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    private void init() {
        Wearable.getMessageClient(this).addListener(this);
        checkLoginStatus();
    }

    @Override
    protected void onDestroy() {
        if (loginDialog != null && loginDialog.isShowing()) {
            loginDialog.dismiss();
        }
        // Unregister the receiver
        super.onDestroy();
    }

    @Override
    public void onMessageReceived(@NonNull MessageEvent messageEvent) {
        if (messageEvent.getPath().equals(DataRequestUtil.UPDATE_DATA_PATH)) {
            Utility.showToast(getBaseContext(),"In onMessageReceived");
            byte[] bytes = messageEvent.getData();
            Log.v(TAG, "Message from Phone Recieved. :::: " + Arrays.toString(bytes));
            if(bytes != null) {
                String data = new String(bytes);
                if(!data.equals(DataRequestUtil.LOGIN_USER.toString())) {
                    showSuccessActivity(this);
                } else {
                    showFailureActivity(this);
                }
            }

        }
    }

    private void requestUserDataFromPhone() {
        DataRequestUtil.findCapabilityClient(this);
    }

    private void checkLoginStatus() {

        // Check if the user is logged in
        if (!Utility.isLoggedIn(this)) {
            // Show the login dialog
            showLoginDialog();
            // Request user data from phone
            requestUserDataFromPhone();
        } else {
            // User is logged in
            // Proceed with the app
        }

        activityHomeBinding.btnMyGarden.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mIntent = new Intent(HomeActivity.this, MyGardenActivity.class);
                startActivity(mIntent);
            }
        });

        activityHomeBinding.btnReminders.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mIntent = new Intent(HomeActivity.this, ReminderListActivity.class);
                startActivity(mIntent);
            }
        });
    }


    // Show a success activity
    public static void showSuccessActivity(Context context) {
        Intent intent = new Intent(context, ConfirmationActivity.class);
        intent.putExtra(ConfirmationActivity.EXTRA_ANIMATION_TYPE, ConfirmationActivity.SUCCESS_ANIMATION);
        intent.putExtra(ConfirmationActivity.EXTRA_MESSAGE, context.getString(R.string.msg_login_using_phone));
        context.startActivity(intent);
    }

    // Show a failure activity
    public static void showFailureActivity(Context context) {
        Intent intent = new Intent(context, ConfirmationActivity.class);
        intent.putExtra(ConfirmationActivity.EXTRA_ANIMATION_TYPE, ConfirmationActivity.FAILURE_ANIMATION);
        intent.putExtra(ConfirmationActivity.EXTRA_MESSAGE, context.getString(R.string.msg_login_failed));
        context.startActivity(intent);
    }

    private void showLoginDialog() {
        CustomDialogLayoutBinding dialogBinding = CustomDialogLayoutBinding.inflate(getLayoutInflater());

        loginDialog = new WearableDialogHelper.DialogBuilder(this)
                .setView(dialogBinding.getRoot())
                .setCancelable(false)
                .create();
        //setcolor and layoutparams
        loginDialog.getWindow().setBackgroundDrawable(new ColorDrawable(getColor(R.color.colorGrayLight)));
        loginDialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        loginDialog.show();

        dialogBinding.dialogButtonOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Close the app
                //finish();
                loginDialog.dismiss();
            }
        });
    }


}