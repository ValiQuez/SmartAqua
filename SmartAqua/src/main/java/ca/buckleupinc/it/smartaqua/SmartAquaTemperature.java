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
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.ToggleButton;
import android.annotation.SuppressLint;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.fragment.app.Fragment;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class SmartAquaTemperature extends Fragment {
    private static final String CHANNEL_ID = "TemperatureNotificationChannel";
    private SeekBar seekBar;
    private TextView textView;
    private SharedPreferences sharedPreferences;


    public SmartAquaTemperature() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_smart_aqua_temperature, container, false);
        seekBar = view.findViewById(R.id.SmartAquaTempSeekBar);
        textView = view.findViewById(R.id.SmartAquaTempReading3);

        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getContext());
        int savedProgress = sharedPreferences.getInt("TemperatureProgress", 0);
        int savedTemperatureRange = sharedPreferences.getInt("TemperatureRange", 18); // Default value is 18
        boolean toggleState = sharedPreferences.getBoolean("TempPref", false);

        seekBar.setProgress(savedProgress); // Set the saved progress
        setTemperatureText(savedTemperatureRange); // Set the saved temperature range

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean b) {
                int temperatureRange = (int) (progress * 0.09) + 18; // Map progress from 18-27
                setTemperatureText(temperatureRange); // Update temperature text

                // Save the progress and temperature range in shared preferences
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putInt("TemperatureProgress", progress);
                editor.putInt("TemperatureRange", temperatureRange);
                editor.apply();
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });

        ToggleButton toggleButton = view.findViewById(R.id.SmartAquaTempToggleButton);
        toggleButton.setChecked(toggleState);
        toggleButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                String message = isChecked ? getString(R.string.tempNoti_ON) : getString(R.string.tempNoti_OFF);
                displayNotification(message);

                // Save the toggle state in shared preferences
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putBoolean("TempPref", isChecked);
                editor.apply();
            }
        });

        return view;
    }

    private void setTemperatureText(int temperatureRange) {
        String temperatureText = temperatureRange + getString(R.string.tempCelcius);
        if (temperatureRange > 20) {
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
