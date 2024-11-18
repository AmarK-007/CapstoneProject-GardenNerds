package com.android.msd.capstone.project.gardennerds.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.android.msd.capstone.project.gardennerds.R;
import com.android.msd.capstone.project.gardennerds.databinding.ActivitySignUpScreenBinding;
import com.android.msd.capstone.project.gardennerds.db.UserDataSource;
import com.android.msd.capstone.project.gardennerds.models.User;
import com.android.msd.capstone.project.gardennerds.utils.Utility;


/**
 * This class is responsible for handling the signup screen activity.
 */
public class SignUpScreenActivity extends AppCompatActivity {
    private static final String TAG = SignUpScreenActivity.class.getSimpleName();
    private EditText firstnameEditText;
    private EditText lastnameEditText;
    private EditText usernameEditText;
    private EditText emailEditText;
    private EditText passwordEditText;
    ActivitySignUpScreenBinding signUpScreenBinding;

    /**
     * This method is called when the activity is first created.
     * It sets the content view and initializes the first name, last name, username, email, password, signup button and login text view.
     *
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        signUpScreenBinding = ActivitySignUpScreenBinding.inflate(getLayoutInflater());
        View view = signUpScreenBinding.getRoot();
        setContentView(view);

        firstnameEditText = signUpScreenBinding.firstname;
        lastnameEditText = signUpScreenBinding.lastname;
        usernameEditText = signUpScreenBinding.username;
        emailEditText = signUpScreenBinding.email;
        passwordEditText = signUpScreenBinding.password;
        Button signupButton = signUpScreenBinding.signupBtn;
        TextView loginTextView = signUpScreenBinding.loginTxt;

        signupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String firstname = firstnameEditText.getText().toString();
                String lastname = lastnameEditText.getText().toString();
                String username = usernameEditText.getText().toString();
                String email = emailEditText.getText().toString();
                String password = passwordEditText.getText().toString();

                if (firstname.isEmpty()) {
                    Toast.makeText(SignUpScreenActivity.this, getString(R.string.msg_first_name_empty), Toast.LENGTH_SHORT).show();
                    return;
                }

                if (lastname.isEmpty()) {
                    Toast.makeText(SignUpScreenActivity.this, getString(R.string.msg_last_name_empty), Toast.LENGTH_SHORT).show();
                    return;
                }
                if (!Utility.validateUserName(username)) {
                    Toast.makeText(SignUpScreenActivity.this, getString(R.string.msg_not_valid_username), Toast.LENGTH_SHORT).show();
                    return;
                }

                if (Utility.validateSymbolsInUserName(username)) {
                    Toast.makeText(SignUpScreenActivity.this, getString(R.string.msg_enter_alpha_numeric), Toast.LENGTH_SHORT).show();
                    return;
                }

                if (Utility.validateSpaceInUserName(username)) {
                    Toast.makeText(SignUpScreenActivity.this, getString(R.string.msg_space_not_allowed), Toast.LENGTH_SHORT).show();
                    return;
                }

                if (!Utility.validateEmail(email)) {
                    Toast.makeText(SignUpScreenActivity.this, getString(R.string.msg_enter_valid_email), Toast.LENGTH_SHORT).show();
                    return;
                }
                if (Utility.validateIsPasswordEmpty(password)) {
                    Toast.makeText(SignUpScreenActivity.this, getString(R.string.msg_password_field_cant_be_empty), Toast.LENGTH_SHORT).show();
                    return;
                }

                if (!Utility.validatePassword(password)) {
                    Toast.makeText(SignUpScreenActivity.this, getString(R.string.msg_enter_min_5characters), Toast.LENGTH_SHORT).show();
                    return;
                }

                // If all validations pass, proceed with signup
                User user = new User();
                user.setName(firstname + " " + lastname); // 'name' is a combination of first and last names
                user.setUsername(username);
                user.setEmail(email);
                user.setPassword(password);
                user.setPurchaseHistory("");
                user.setShippingAddress1("");
                user.setShippingAddress2("");
                user.setCity("");
                user.setProvince("");
                user.setPincode("");

                try {
                    UserDataSource userDataSource = new UserDataSource(SignUpScreenActivity.this);
                    boolean success = userDataSource.insertUser(user);

                    if (success) {
                        showAppSuccessAlert(SignUpScreenActivity.this);
                    } else {
                        showAppFailureAlert(SignUpScreenActivity.this);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    showAppFailureAlert(SignUpScreenActivity.this);
                }
            }
        });
        loginTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SignUpScreenActivity.this, LoginScreenActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    AlertDialog.Builder alertDialog;

    /**
     * This method is called when the user successfully signs up.
     * It displays an alert dialog with a success message.
     *
     * @param context
     */
    public void showAppSuccessAlert(final Context context) {
        alertDialog = new AlertDialog.Builder(context, R.style.MyDialogTheme);
        alertDialog.setTitle(Utility.getAppNameString(context));
        alertDialog.setMessage(getString(R.string.msg_signup_success));
        alertDialog.setPositiveButton(getString(R.string.button_ok),
                (dialog, which) -> {
                    dialog.dismiss();
                    alertDialog = null;
                    SignUpScreenActivity.this.finishAndRemoveTask();
                });
        alertDialog.setNegativeButton(getString(R.string.button_cancel), (dialog, which) -> {
            dialog.dismiss();
            alertDialog = null;
        });
        alertDialog.show();
    }

    /**
     * This method is called when the user fails to sign up.
     * It displays an alert dialog with a failure message.
     *
     * @param context
     */
    public void showAppFailureAlert(final Context context) {
        alertDialog = new AlertDialog.Builder(context, R.style.MyDialogTheme);
        alertDialog.setTitle(Utility.getAppNameString(context));
        alertDialog.setMessage(getString(R.string.msg_signup_failed));
        alertDialog.setPositiveButton(getString(R.string.button_ok),
                (dialog, which) -> {
                    dialog.dismiss();
                    alertDialog = null;
                });
        alertDialog.show();
    }
}