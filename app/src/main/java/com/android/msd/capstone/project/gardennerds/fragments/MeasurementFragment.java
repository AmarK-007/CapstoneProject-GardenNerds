package com.android.msd.capstone.project.gardennerds.fragments;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.android.msd.capstone.project.gardennerds.R;
import com.google.ar.core.ArCoreApk;
import com.google.ar.core.Frame;
import com.google.ar.core.HitResult;
import com.google.ar.core.Pose;
import com.google.ar.core.Session;
import com.google.ar.core.exceptions.CameraNotAvailableException;
import com.google.ar.core.exceptions.TextureNotSetException;
import com.google.ar.sceneform.ArSceneView;
import com.google.ar.sceneform.math.Vector3;
import com.google.ar.core.Config;
import com.google.ar.core.Config.UpdateMode;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MeasurementFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MeasurementFragment extends Fragment {

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

    public MeasurementFragment() {
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
    public static MeasurementFragment newInstance(String param1, String param2) {
        MeasurementFragment fragment = new MeasurementFragment();
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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_measurement, container, false);

        sceneView = view.findViewById(R.id.scene_view);

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
            sceneView.setupSession(arSession); // Ensure the SceneView is set up with the AR session
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
                displayMeasurement("Area: " + area + " sq. meters");
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
        TextView measurementTextView = getView().findViewById(R.id.measurement_text_view);
        measurementTextView.setText(measurement);
    }

    @Override
    public void onResume() {
        super.onResume();
        if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.CAMERA)
                == PackageManager.PERMISSION_GRANTED) {
            initializeArSession();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if (arSession != null) {
            /**Mann Commented because there wasn't any thing understandable*/
//            msfadmin
//                msf
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