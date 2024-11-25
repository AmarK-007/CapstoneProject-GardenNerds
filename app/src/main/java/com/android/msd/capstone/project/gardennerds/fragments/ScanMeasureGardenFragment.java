package com.android.msd.capstone.project.gardennerds.fragments;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.android.msd.capstone.project.gardennerds.R;
import com.android.msd.capstone.project.gardennerds.databinding.FragmentMeasurementBinding;
import com.android.msd.capstone.project.gardennerds.utils.Utility;
import com.google.ar.core.ArCoreApk;
import com.google.ar.core.Frame;
import com.google.ar.core.HitResult;
import com.google.ar.core.Pose;
import com.google.ar.core.Session;
import com.google.ar.core.exceptions.CameraNotAvailableException;
import com.google.ar.core.exceptions.TextureNotSetException;
import com.google.ar.core.exceptions.UnavailableApkTooOldException;
import com.google.ar.core.exceptions.UnavailableArcoreNotInstalledException;
import com.google.ar.core.exceptions.UnavailableDeviceNotCompatibleException;
import com.google.ar.core.exceptions.UnavailableSdkTooOldException;
import com.google.ar.sceneform.ArSceneView;
import com.google.ar.sceneform.math.Vector3;
import com.google.ar.core.Config;
import com.google.ar.core.Config.UpdateMode;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ScanMeasureGardenFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ScanMeasureGardenFragment extends Fragment {

    private static final String TAG = "MeasurementFragment";
    private static final int CAMERA_PERMISSION_REQUEST_CODE = 100;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private ArSceneView sceneView;
    private Session arSession;
    private List<Vector3> points = new ArrayList<>();

    private FragmentMeasurementBinding binding;

    public ScanMeasureGardenFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MeasurementFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ScanMeasureGardenFragment newInstance(String param1, String param2) {
        ScanMeasureGardenFragment fragment = new ScanMeasureGardenFragment();
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

        ArCoreApk.Availability availability = ArCoreApk.getInstance().checkAvailability(getActivity());
        switch (availability) {
            case SUPPORTED_INSTALLED:
                Log.d("AR", "ARCore is supported and installed");
                break;
            case SUPPORTED_APK_TOO_OLD:
                Log.e("AR", "ARCore APK is too old, please update");
                break;
            case SUPPORTED_NOT_INSTALLED:
                Log.e("AR", "ARCore APK is not installed, please install");
                break;
            case UNKNOWN_ERROR:
                Log.e("AR", "ARCore availability check returned UNKNOWN_ERROR");
                break;
            case UNKNOWN_CHECKING:
                Log.d("AR", "ARCore availability check is in progress");
                break;
            case UNKNOWN_TIMED_OUT:
                Log.e("AR", "ARCore availability check timed out");
                break;
            case UNSUPPORTED_DEVICE_NOT_CAPABLE:
                Log.e("AR", "This device does not support ARCore");
                break;
            default:
                Log.e("AR", "ARCore availability check returned an unknown status");
                break;
        }
        if (availability.isTransient()) {
            Log.e("AR", "ARCore is not available");
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment using view binding
        binding = FragmentMeasurementBinding.inflate(inflater, container, false);
        View view = binding.getRoot();
        sceneView = binding.sceneView;

        // Request camera permission if not granted
        if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(getActivity(),
                    new String[]{Manifest.permission.CAMERA},
                    CAMERA_PERMISSION_REQUEST_CODE);
        } else {
            initializeArSession();
        }

        // Setup touch listener for the SceneView
        sceneView.setOnTouchListener((v, event) -> {
            if (event.getAction() == MotionEvent.ACTION_DOWN) {
                try {
                    placeMeasurementPoint(event);
                } catch (CameraNotAvailableException e) {
                    throw new RuntimeException(e);
                }
            }
            return true;
        });

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Ensure Activity and ActionBar are available
        if (getActivity() != null && getActivity() instanceof AppCompatActivity) {
            AppCompatActivity activity = (AppCompatActivity) getActivity();
            activity.getSupportActionBar().setTitle(getString(R.string.text_scan_garden));
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    private void promptUserToInstallARCore() {
        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
        builder.setTitle("ARCore not installed")
                .setMessage("Please install ARCore from the Google Play Store to use this feature.")
                .setPositiveButton("Install", (dialog, which) -> {
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=com.google.ar.core"));
                    startActivity(intent);
                })
                .setNegativeButton("Cancel", (dialog, which) -> dialog.dismiss())
                .show();
    }

    private void promptUserToUpdateARCore() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("ARCore update required")
                .setMessage("Please update ARCore from the Google Play Store to use this feature.")
                .setPositiveButton("Update", (dialog, which) -> {
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=com.google.ar.core"));
                    startActivity(intent);
                })
                .setNegativeButton("Cancel", (dialog, which) -> dialog.dismiss())
                .show();
    }

    private void initializeArSession() {
        try {
            if (arSession == null) {
                arSession = new Session(getActivity());
                Config config = new Config(arSession);
                config.setUpdateMode(UpdateMode.LATEST_CAMERA_IMAGE);
                arSession.configure(config);
            }
            arSession.resume();
            sceneView.resume();
            sceneView.setupSession(arSession);
        } catch (UnavailableArcoreNotInstalledException e) {
            Log.e(TAG, "ARCore is not installed", e);
            promptUserToInstallARCore();
        } catch (UnavailableApkTooOldException e) {
            Log.e(TAG, "ARCore APK is too old", e);
            promptUserToUpdateARCore();
        } catch (UnavailableSdkTooOldException e) {
            Log.e(TAG, "ARCore SDK is too old", e);
            Toast.makeText(getActivity(), "Please update this app to use AR features", Toast.LENGTH_LONG).show();
        } catch (UnavailableDeviceNotCompatibleException e) {
            Log.e(TAG, "This device does not support ARCore", e);
            Toast.makeText(getActivity(), "This device does not support AR features", Toast.LENGTH_LONG).show();
        } catch (Exception e) {
            Log.e(TAG, "Error initializing AR session: " + e.getMessage());
        }
    }

    private void placeMeasurementPoint(MotionEvent event) throws CameraNotAvailableException {
        if (arSession == null) {
            Log.e("TAG", "AR Session is not initialized");
            return;
        }

        // Get the current frame
        Frame frame;
        try {
            frame = arSession.update();
        } catch (CameraNotAvailableException | TextureNotSetException e) {
            Log.e(TAG, "Camera not available or texture not set", e);
            return;
        }

        if (frame == null) {
            Log.e(TAG, "Frame is null");
            return;
        }

        // Log camera position for debugging
        Pose cameraPose = frame.getCamera().getPose();
        Log.d(TAG, "Camera position: " + cameraPose.toString());

        // Log the number of detected planes
        int planeCount = frame.getUpdatedTrackables(com.google.ar.core.Plane.class).size();
        Log.d(TAG, "Number of detected planes: " + planeCount);

        if (planeCount == 0) {
            Log.d(TAG, "No planes detected. Move the device around to help ARCore detect planes.");
            Toast.makeText(getActivity(), "No planes detected. Move the device around to help ARCore detect planes.", Toast.LENGTH_SHORT).show();
            return;
        }
        // Log the touch coordinates
        Log.d(TAG, "Touch coordinates: (" + event.getX() + ", " + event.getY() + ")");

        List<HitResult> hitResults = frame.hitTest(event.getX(), event.getY());
        if (hitResults.isEmpty()) {
            Log.d(TAG, "No hit results");
            return;
        }
        // Perform hit test based on screen coordinates
        for (HitResult hit : hitResults) {
            Pose pose = hit.getHitPose();
            Vector3 position = new Vector3(pose.tx(), pose.ty(), pose.tz());

            points.add(position); // Add the position to the list of measurement points
            Log.d(TAG, "Point added at: " + position.toString());

            if (points.size() == 2) {
                float length = calculateDistance(points.get(0), points.get(1));
                Log.d(TAG, "Calculated Length: " + length);
                displayMeasurement("Length: " + length + " meters");
            } else if (points.size() == 4) {
                float width = calculateDistance(points.get(2), points.get(3));
                Log.d(TAG, "Calculated Width: " + width);
                displayMeasurement("Width: " + width + " meters");
            } else if (points.size() >= 2) {
                float area = calculateArea(points); // Calculate area once we have at least 2 points
                Log.d(TAG, "Calculated Area: " + area);
                //rounding off the area to 2 decimal places
                String squareFeet = Utility.getSquareMeterToSquareFeet(area + "");
               // String roundedArea = String.format(Locale.getDefault(), "%.2f", Double.parseDouble(area + ""));
                displayMeasurement("Area: " + squareFeet + " sq. feet");
            }

            break; // Exit after processing the first touch hit
        }
    }

    private float calculateArea(List<Vector3> points) {
        if (points.size() < 3) return 0; // At least 3 points are needed to form a polygon

        float area = 0;
        int j = points.size() - 1; // The last vertex to connect with the first

        for (int i = 0; i < points.size(); i++) {
            area += (points.get(j).x + points.get(i).x) * (points.get(j).z - points.get(i).z);
            j = i; // j is the previous vertex to i
        }

        area = Math.abs(area / 2);
        return area; // Area in square meters (assuming that the coordinates are in meters)
    }

    private float calculateDistance(Vector3 point1, Vector3 point2) {
        return Vector3.subtract(point1, point2).length();
    }

    private void displayMeasurement(String measurement) {
        // Assuming you have a TextView in your layout to display the measurement
        TextView measurementTextView = binding.measurementTextView;
        measurementTextView.setText(measurement);

        // if the measurement is Area: xyz sq. meters, take a screen shot of the scene and save it to the device and show it in a pop up dialog
        // with a button below the image to move to the next fragment i.e. ApiSearchResultsFragment and hit the API with Area value
        if (measurement.startsWith("Area:")) {
            // 1. Take Screenshot
            Bitmap screenshot = takeScreenshot();

            // 2. Create Dialog
            AlertDialog.Builder builder = new AlertDialog.Builder(requireContext(), R.style.MyDialogTheme);

            LinearLayout container = new LinearLayout(getActivity());
            container.setOrientation(LinearLayout.VERTICAL);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            params.setMargins(50, 20, 50, 20);

            TextView titleTextView = new TextView(getActivity());
            titleTextView.setLayoutParams(params);
            titleTextView.setTextColor(getResources().getColor(R.color.colorAccent));
            titleTextView.setHintTextColor(getResources().getColor(R.color.colorGrayLight));
            titleTextView.setPadding(20, 20, 20, 20);
            titleTextView.setTextSize(20);
            titleTextView.setGravity(Gravity.LEFT);
            titleTextView.setText(getResources().getString(R.string.area_measured));
            builder.setCustomTitle(titleTextView);

            final TextView areaTextView = Utility.showStyledAlertDialog(requireContext());
            areaTextView.setText(extractAreaValue(measurement));
            //gravity in the center
            areaTextView.setGravity(Gravity.CENTER);
            container.addView(areaTextView);

            builder.setView(container);
            builder.setPositiveButton(getString(R.string.find_products), new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                    //Navigate to Next Fragment
                    String areaValue = extractAreaValue(measurement);
                    moveToProductListFragment("Gardening " + areaValue);
                }
            });
            builder.setNegativeButton("Cancel", null);
            AlertDialog dialog = builder.create();
            dialog.show();
            // Get the positive button and set its text color
            Button positiveButton = dialog.getButton(AlertDialog.BUTTON_POSITIVE);
            positiveButton.setTextColor(getResources().getColor(R.color.colorAccent));
            // Get the negative button and set its text color
            Button negativeButton = dialog.getButton(AlertDialog.BUTTON_NEGATIVE);
            negativeButton.setTextColor(getResources().getColor(R.color.colorAccent));


            /*// Create and inflate the dialog layout
            DialogMeasuredAreaBinding dialogBinding = DialogMeasuredAreaBinding.inflate(getLayoutInflater());
            LinearLayout container = (LinearLayout) dialogBinding.getRoot(); // Get the root LinearLayout

            TextView areaTextView = dialogBinding.areaMeasuredTextView;
            areaTextView.setText(extractAreaValue(measurement));
//            // Set the screenshot image
//            ImageView screenshotImageView = dialogBinding.screenshotImageView;
//            screenshotImageView.setImageBitmap(screenshot);

            // Set the "Next" button click listener
            Button nextButton = dialogBinding.nextButton;
            nextButton.setOnClickListener(v -> {
                // 3. Navigate to Next Fragment
                String areaValue = extractAreaValue(measurement);
                Bundle bundle = new Bundle();
                bundle.putString("area", areaValue);

                APISearchResultFragment fragment = new APISearchResultFragment();
                fragment.setArguments(bundle);

                *//*requireActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.frames, fragment)
                        .commit();*//*

                // Dismiss the dialog
                builder.create().dismiss();
            });

            // Set the view and show the dialog
            builder.setView(container);
            builder.show();*/
        }
    }

    public void moveToProductListFragment(String query) {
        Bundle bundle = new Bundle();
        bundle.putString("QUERY", query);

        Fragment fragment = new ProductListFragment();
        fragment.setArguments(bundle);
        FragmentManager supportFragmentManager = requireActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = supportFragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frames, fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }


    private Bitmap takeScreenshot() {
        // Get the root view of your layout
        View rootView = requireActivity().getWindow().getDecorView().getRootView();

        // Create a bitmap
        Bitmap bitmap = Bitmap.createBitmap(rootView.getWidth(), rootView.getHeight(), Bitmap.Config.ARGB_8888);

        // Draw the view onto the bitmap
        Canvas canvas = new Canvas(bitmap);
        rootView.draw(canvas);

        return bitmap;
    }

    private String extractAreaValue(String measurement) {
        // Implement logic to extract the area value from the measurement string
        // Example: If measurement is "Area: 123 sq. meters", extract "123"
        String extractedAreaValue = measurement.replace("Area: ", "").replace(" sq. feet", "");
        extractedAreaValue = extractedAreaValue.trim() + " ft x " + extractedAreaValue.trim() + " ft";
        Log.d(TAG, "Extracted Area Value: " + extractedAreaValue);
        return extractedAreaValue;
    }

    @Override
    public void onResume() {
        super.onResume();
        if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.CAMERA)
                == PackageManager.PERMISSION_GRANTED) {
            if (arSession == null) {
                initializeArSession();
            } else {
                try {
                    arSession.resume();
                    sceneView.resume();
                } catch (CameraNotAvailableException e) {
                    Log.e(TAG, "Camera not available during onResume", e);
                    arSession = null;
                }
            }
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if (arSession != null) {
            arSession.pause();
        }
        sceneView.pause();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == CAMERA_PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                initializeArSession();
            } else {
                Log.e(TAG, "Camera permission is required for AR functionality");
            }
        }
    }
}