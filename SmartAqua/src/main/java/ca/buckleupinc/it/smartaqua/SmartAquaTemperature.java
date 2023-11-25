package ca.buckleupinc.it.smartaqua;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Random;

public class SmartAquaTemperature extends Fragment {
    private static final String CHANNEL_ID = "TemperatureNotificationChannel";
    private TextView textView;
    private ProgressBar temperatureProgressBar; // Updated ProgressBar
    private DatabaseReference tempDataPref;
    private List<Double> tempDataList;
    private boolean isOnline;
    private Random random;
    private Handler handler;
    private ValueEventListener tempValueEventListener;
    private static final String OFFLINE = "Connect to Wi-Fi...";

    public SmartAquaTemperature() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_smart_aqua_temperature, container, false);
        textView = view.findViewById(R.id.SmartAquaTempReading3);
        temperatureProgressBar = view.findViewById(R.id.SmartAquaTemperatureProgressBar);
        temperatureProgressBar.setMax(31 - 18); // Set the maximum value to the entire range

        //tempPref = PreferenceManager.getDefaultSharedPreferences(getContext());
        //random = new Random();
        tempDataList = new ArrayList<>();
        handler = new Handler(Looper.getMainLooper());

        // Reference to the "temperature" node in the database
        tempDataPref = FirebaseDatabase.getInstance().getReference("ReadingsRPi/SmartAqua_Readings/temperature");

        // Setup network connectivity listener
        setupNetworkConnectivityListener();
        // Start periodic temperature updates
        readTemperatureData();

        return view;
    }

    private void readTemperatureData() {
        tempValueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                tempDataList.clear();

                Double temperatureData = snapshot.getValue(Double.class);

                if (temperatureData != null) {
                    tempDataList.add(temperatureData);
                    displayTemperature(temperatureData);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Handle any errors that occur during data reading
                Toast.makeText(getActivity(), R.string.failedDB, Toast.LENGTH_SHORT).show();
            }
        };

        tempDataPref.addValueEventListener(tempValueEventListener);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        // Remove the ValueEventListener when the fragment is destroyed
        if (tempDataPref != null && tempValueEventListener != null) {
            tempDataPref.removeEventListener(tempValueEventListener);
        }
    }
        /*tempDataPref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                tempDataList.clear();

                Double temperatureData = dataSnapshot.getValue(Double.class);

                if (temperatureData != null) {
                    tempDataList.add(temperatureData);
                    displayTemperature(temperatureData);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Handle any errors that occur during data reading
                Toast.makeText(getActivity(), R.string.failedDB, Toast.LENGTH_SHORT).show();
            }
        });
    }*/

    /*private void updateRandomTemperature() {
        // Check if the fragment is attached to a context
        if (!isAdded()) {
            return;
        }

        Context context = requireContext();

        int randomTemperature = random.nextInt(14) + 18;
        String temperatureText = randomTemperature + context.getString(R.string.tempCelcius);

        // Update TextView
        textView.setText(temperatureText);

        // Update ProgressBar and its color based on temperature range
        updateProgressBar(randomTemperature);

        // Update the text color based on temperature range
        int textColor;
        if (randomTemperature >= 28) {
            textColor = Color.RED;
        } else if (randomTemperature >= 21) {
            textColor = Color.GREEN;
        } else {
            textColor = Color.BLUE;
        }

        // Set the text color
        textView.setTextColor(textColor);

        // Display a toast message indicating the temperature status
        displayTemperatureStatus(randomTemperature);

        displayNotification(temperatureText);
    }*/

    private void displayTemperature(double temperature) {

        if(!isAdded()){
            return;
        }

        Context context = requireContext();

        String temperatureText = String.format(Locale.getDefault(), "%.2f%s", temperature, context.getString(R.string.tempCelcius));
        textView.setText(temperatureText);

        updateProgressBar((int) temperature);
        //updateTextColor((int) temperature);
        int textColor;
        if (temperature >= 28) {
            textColor = Color.RED;
        } else if (temperature >= 21) {
            textColor = Color.GREEN;
        } else {
            textColor = Color.BLUE;
        }

        textView.setTextColor(textColor);

        // Display a toast message indicating the temperature status
        //displayTemperatureStatus((int) temperature);

        //displayNotification(temperatureText);
    }

    private void updateProgressBar(int temperature) {
        int progress = temperature - 18; // Calculate progress relative to the range
        temperatureProgressBar.setProgress(progress);

        // Set color based on the temperature range
        int color;
        if (temperature >= 28) {
            color = Color.RED;
        } else if (temperature >= 21) {
            color = Color.GREEN;
        } else {
            color = Color.BLUE;
        }

        // Set the progress color
        temperatureProgressBar.setProgressTintList(android.content.res.ColorStateList.valueOf(color));
    }
    private void setupNetworkConnectivityListener() {
        DatabaseReference connectedRef = FirebaseDatabase.getInstance().getReference(".info/connected");
        connectedRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                isOnline = snapshot.getValue(Boolean.class);
                if (!isOnline) {
                    // If offline, display a placeholder text
                    textView.setText(OFFLINE);
                }
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Handle network connectivity error
                Toast.makeText(getActivity(), R.string.failedDB, Toast.LENGTH_SHORT).show();
            }
        });
    }

    /*private void updateTextColor(int temperature) {

    }*/

    /*private void displayTemperatureStatus(int temperature) {
        String statusMessage;

        if (temperature >= 28) {
            statusMessage = "Hot";
        } else if (temperature >= 21) {
            statusMessage = "Ideal";
        } else {
            statusMessage = "Cold";
        }

        // Display a toast message with the temperature status
        Toast.makeText(requireContext(), "Temperature Status: " + statusMessage, Toast.LENGTH_SHORT).show();
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
    }*/
}
