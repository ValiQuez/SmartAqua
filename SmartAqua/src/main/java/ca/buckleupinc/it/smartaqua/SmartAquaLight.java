package ca.buckleupinc.it.smartaqua;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Random;

public class SmartAquaLight extends Fragment {

    private static final int SENSOR_DELAY = 2000; // 2 seconds
    private static final String LIGHT_DATA_REF = "ReadingsRPi/SmartAqua_Readings/illuminance";
    private TextView luxLevelTextView;
    private NotificationManager notificationManager;
    private ImageView lightBulbImage;
    private Random random;
    private Handler handler;
    private DatabaseReference lightDbRef;
    private ValueEventListener lightVEL;
    private static final String OFFLINE = "Connect to Wi-Fi...";

    public SmartAquaLight() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_smart_aqua_light, container, false);

        luxLevelTextView = view.findViewById(R.id.notificationTextView);
        lightBulbImage = view.findViewById(R.id.SmartAqualightbulb);

        //random = new Random();

        lightDbRef = FirebaseDatabase.getInstance().getReference(LIGHT_DATA_REF);
        // Setup network connectivity listener
        setupNetworkConnectivityListener();
        // Simulate sensor reading after view is created
        readLightSensorData();

        return view;
    }

    private void readLightSensorData() {
        lightVEL = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Double luxValue = snapshot.getValue(Double.class);

                if (luxValue != null) {
                    displayLuxValue(luxValue);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Handle any errors that occur during data reading
                Toast.makeText(getActivity(), R.string.failedDB, Toast.LENGTH_SHORT).show();
            }
        };

        lightDbRef.addValueEventListener(lightVEL);
    }
        /*lightDbRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                // Retrieve the lux value from Firebase
                Float luxValue = snapshot.getValue(Float.class);

                // Check if the lux value is not null
                if (luxValue != null) {
                    onSensorChanged(luxValue);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Handle any errors that occur during data reading
                Toast.makeText(getActivity(), R.string.failedDB, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void simulateSensorReading() {
        // Handler to simulate sensor data reading with a delay
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                // Generate a random lux value between 0 and 1000 for simulation
                float simulatedLuxValue = random.nextFloat() * 1000;
                onSensorChanged(simulatedLuxValue);

                // Schedule the next sensor reading
                handler.postDelayed(this, SENSOR_DELAY);
            }
        }, SENSOR_DELAY); // Simulate after the initial delay
    }*/

    public void displayLuxValue(Double lux) {
        if (isAdded()) { // Check if the fragment is attached to a context
            // Update the UI with the lux value
            luxLevelTextView.setText(String.valueOf(lux)); // Use string resource for normal lux level
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        // Remove the ValueEventListener when the fragment is destroyed
        if (lightDbRef != null && lightVEL != null) {
            lightDbRef.removeEventListener(lightVEL); // Remove all listeners
        }
    }

    private void setupNetworkConnectivityListener() {
        DatabaseReference connectedRef = FirebaseDatabase.getInstance().getReference(".info/connected");
        connectedRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                boolean isOnline = snapshot.getValue(Boolean.class);
                if (!isOnline) {
                    // If offline, display a placeholder text
                    luxLevelTextView.setText(OFFLINE);
                }
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Handle network connectivity error
                Toast.makeText(getActivity(), R.string.failedDB, Toast.LENGTH_SHORT).show();
            }
        });
    }
}
