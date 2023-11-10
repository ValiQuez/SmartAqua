/*  CENG-322-0NA: Group 6
    Denis Shwaloff - N01422583
    Alvaro Rodrigo Chavez Moya - N01455107
    Paolo Adrian Quezon - N01424883
    Nicholas Dibiase - N01367109            */

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

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import java.util.Random;

public class SmartAquaLight extends Fragment {

    private static final String CHANNEL_ID = "LightNotificationChannel";
    private static final int NOTIFICATION_ID = 1;
    private static final int SENSOR_DELAY = 2000; // 2 seconds

    private TextView luxLevelTextView;
    private NotificationManager notificationManager;
    private ImageView lightBulbImage;
    private Random random;

    public SmartAquaLight() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_smart_aqua_light, container, false);

        luxLevelTextView = view.findViewById(R.id.notificationTextView);
        lightBulbImage = view.findViewById(R.id.SmartAqualightbulb);
        notificationManager = (NotificationManager) requireActivity().getSystemService(Context.NOTIFICATION_SERVICE);
        random = new Random();

        // Simulate sensor reading after view is created
        simulateSensorReading();

        return view;
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
    }

    public void onSensorChanged(float lux) {
        // Update the UI with the simulated lux value
        luxLevelTextView.setText(getString(R.string.lux_level_normal, lux)); // Use string resource for normal lux level
    }
}
