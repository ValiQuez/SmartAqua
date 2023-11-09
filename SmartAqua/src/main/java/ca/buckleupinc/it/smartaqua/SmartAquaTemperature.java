package ca.buckleupinc.it.smartaqua;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.fragment.app.Fragment;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import java.util.Random;

public class SmartAquaTemperature extends Fragment {
    private static final String CHANNEL_ID = "TemperatureNotificationChannel";
    private static final String TEMPERATURE_PROGRESS_KEY = "TemperatureProgress";
    private static final String TEMP_PREF_KEY = "TempPref";

    private ProgressBar progressBar;
    private TextView textView;
    private TextView percentTextView; // TextView to display the percentage
    private SharedPreferences tempPref;
    private Handler handler = new Handler();
    private static final int MAX_PROGRESS = 9; // Maximum progress value (corresponds to 27°C)
    private int currentProgress = 0; // Current progress value

    public SmartAquaTemperature() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_smart_aqua_temperature, container, false);
        progressBar = view.findViewById(R.id.SmartAquaTempProgressBar);
        textView = view.findViewById(R.id.SmartAquaTempReading3);
        percentTextView = view.findViewById(R.id.SmartAquaTempPercent); // Initialize the percentage TextView

        tempPref = PreferenceManager.getDefaultSharedPreferences(getContext()); // Changed variable assignment

        progressBar.setMax(MAX_PROGRESS); // Set the maximum value of the ProgressBar

        // Start a handler to update the temperature reading, ProgressBar, and percentage display
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (currentProgress < MAX_PROGRESS) {
                    currentProgress++; // Increment the progress value
                    progressBar.setProgress(currentProgress);
                    updateTemperatureText(currentProgress);
                } else {
                    currentProgress = 0; // Reset progress when it reaches the maximum
                    progressBar.setProgress(currentProgress);
                    updateTemperatureText(currentProgress);
                }

                // Calculate the percentage and update the TextView
                int percentage = (currentProgress * 100) / MAX_PROGRESS;
                percentTextView.setText(percentage + "%");

                handler.postDelayed(this, 5000); // Schedule the next update after 5 seconds
            }
        }, 0);

        return view;
    }

    private void updateTemperatureText(int temperatureRange) {
        // Generate a random temperature within the range (18-27)
        int randomTemperature = new Random().nextInt(10) + 18;
        String temperatureText = randomTemperature + getString(R.string.tempCelcius);

        if (randomTemperature > 20) {
            textView.setTextColor(Color.RED); // Set text color to red for temperatures above 20 degrees Celsius
        } else {
            textView.setTextColor(Color.BLUE); // Set text color to blue for temperatures below or equal to 20 degrees Celsius
        }

        textView.setText(temperatureText);
    }

    @SuppressLint("MissingPermission")
    private void displayNotification(String message) {
        Context context = requireContext();

        createNotificationChannel();

        Notification notification = new NotificationCompat.Builder(context, CHANNEL_ID)
                .setSmallIcon(R.drawable.notification)
                .setContentTitle(getString(R.string.tempContentTitle))
                .setContentText(message)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .build();

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);
        notificationManager.notify(1, notification);
    }

    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = getString(R.string.tempCharSequence);
            String description = getString(R.string.tempDescription);
            int importance = NotificationManager.IMPORTANCE_DEFAULT;

            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
            channel.setDescription(description);
            channel.enableLights(true);
            channel.setLightColor(Color.RED);

            NotificationManager notificationManager = requireContext().getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }
}
