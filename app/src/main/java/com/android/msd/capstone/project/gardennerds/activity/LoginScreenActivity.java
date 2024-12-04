package com.android.msd.capstone.project.gardennerds.activity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.android.msd.capstone.project.gardennerds.R;
import com.android.msd.capstone.project.gardennerds.databinding.ActivityLoginScreenBinding;
import com.android.msd.capstone.project.gardennerds.db.UserDataSource;
import com.android.msd.capstone.project.gardennerds.utils.DataSyncUtil;
import com.android.msd.capstone.project.gardennerds.utils.Utility;

public class LoginScreenActivity extends AppCompatActivity {

    private static final String TAG = LoginScreenActivity.class.getSimpleName();
    private EditText usernameEditText;
    private EditText passwordEditText;
    private Button loginButton;

    ActivityLoginScreenBinding loginScreenBinding;
    /**
     * This method is called when the activity is first created.
     * It sets the content view and initializes the username, password and login button.
     *
     * @param savedInstanceState
     */
    @SuppressLint("SourceLockedOrientationActivity")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        loginScreenBinding = ActivityLoginScreenBinding.inflate(getLayoutInflater());
        View view = loginScreenBinding.getRoot();
        setContentView(view);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        // Set navigation bar color
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setNavigationBarColor(getResources().getColor(R.color.colorPrimary));
        }

        usernameEditText = loginScreenBinding.username;
        passwordEditText = loginScreenBinding.password;
        loginButton = loginScreenBinding.loginBtn;

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = usernameEditText.getText().toString();
                String password = passwordEditText.getText().toString();

                if (!Utility.validateUserName(username)) {
                    Toast.makeText(LoginScreenActivity.this, getString(R.string.msg_not_valid_username), Toast.LENGTH_SHORT).show();
                    return;
                }

                if (Utility.validateSymbolsInUserName(username)) {
                    Toast.makeText(LoginScreenActivity.this, getString(R.string.msg_enter_alpha_numeric), Toast.LENGTH_SHORT).show();
                    return;
                }

                if (Utility.validateSpaceInUserName(username)) {
                    Toast.makeText(LoginScreenActivity.this, getString(R.string.msg_space_not_allowed), Toast.LENGTH_SHORT).show();
                    return;
                }

                if (Utility.validateIsPasswordEmpty(password)) {
                    Toast.makeText(LoginScreenActivity.this, getString(R.string.msg_password_field_cant_be_empty), Toast.LENGTH_SHORT).show();
                    return;
                }

                if (!Utility.validatePassword(password)) {
                    Toast.makeText(LoginScreenActivity.this, getString(R.string.msg_enter_min_5characters), Toast.LENGTH_SHORT).show();
                    return;
                }

                // If all validations pass, proceed with login
                performLogin(username, password);
            }
        });

        TextView signUpButton = loginScreenBinding.signUpTxt;

        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginScreenActivity.this, SignUpScreenActivity.class);
                startActivity(intent);
            }
        });
    }

    /**
     * This method is called when the user clicks on the login button.
     * It validates the user credentials and logs the user in if the credentials are correct.
     *
     * @param username
     * @param password
     */
    private void performLogin(String username, String password) {
        try {
            UserDataSource userDataSource = new UserDataSource(this);
            if (userDataSource.validateUser(username, password)) {

                SharedPreferences sharedPreferences = getSharedPreferences("Login_Username", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("userName", username);
                editor.apply();
                Utility.storeUser(userDataSource.getUser(username, password), this);
                Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
                startActivity(intent);
                // send username data to wearableDataSyncUtil.sendUserDataToWear(this, "login", "userName", username);
                finish();
            } else {
                Toast.makeText(LoginScreenActivity.this, getString(R.string.msg_login_failed), Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(LoginScreenActivity.this, getString(R.string.msg_login_failed), Toast.LENGTH_SHORT).show();
        }
    }
}