package com.android.msd.capstone.project.gardennerds.fragments;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import com.android.msd.capstone.project.gardennerds.R;
import com.android.msd.capstone.project.gardennerds.databinding.FragmentSupportBinding;
import com.android.msd.capstone.project.gardennerds.utils.Utility;


// SupportFragment class that extends Fragment
public class SupportFragment extends Fragment {

    private FragmentSupportBinding binding;
    private EditText nameEditText;
    private EditText emailEditText;
    private EditText issueEditText;
    private Button submitButton;

    /**
     * onCreate method for initializing fragment
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment using view binding
        binding = FragmentSupportBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

        nameEditText = binding.supportName;
        emailEditText = binding.supportEmail;
        issueEditText = binding.issueText;
        submitButton = binding.supportBtn;

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = nameEditText.getText().toString();
                String email = emailEditText.getText().toString();
                String issue = issueEditText.getText().toString();

                if (Utility.validateIsNameEmpty(name)) {
                    Toast.makeText(requireContext(), "Name is Empty", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (!Utility.validateEmail(email)) {
                    Toast.makeText(requireContext(), "Invalid Email address", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (Utility.validateIsTextAreaEmpty(issue)) {
                    Toast.makeText(requireContext(), "Your Issue is Empty", Toast.LENGTH_SHORT).show();
                }
                showSupportRequestSendDialog(requireContext());

            }
        });
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    /**
     * Method to empty all the fields
     */
    public void emptyAllFields() {
        nameEditText.setText("");
        emailEditText.setText("");
        issueEditText.setText("");
    }

    AlertDialog.Builder alertDialog;

    /**
     * Method to show the dialog for the support request
     *
     * @param context
     */
    public void showSupportRequestSendDialog(final Context context) {
        alertDialog = new AlertDialog.Builder(context, R.style.MyDialogTheme);
        alertDialog.setMessage(R.string.request_message);
        alertDialog.setNeutralButton("Ok", (dialog, which) -> {
            dialog.dismiss();
            emptyAllFields();
        });
        alertDialog.show();
    }


}