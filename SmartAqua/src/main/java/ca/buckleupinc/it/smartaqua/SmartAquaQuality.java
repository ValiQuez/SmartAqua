/*  CENG-322-0NA: Group 6
    Denis Shwaloff - N01422583
    Alvaro Rodrigo Chavez Moya - N01455107
    Paolo Adrian Quezon - N01424883
    Nicholas Dibiase - N01367109            */

package ca.buckleupinc.it.smartaqua;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Random;

public class SmartAquaQuality extends Fragment {

    private TextView readings_TDS;
    private TextView status_TDS;
    private Handler handler;
    private Runnable runnable;
    private DatabaseReference dbRef;
    private List<Integer> tdsDataList;
    private boolean isOnline;
    private int lastDisplayedIndex = -1;
    private SharedPreferences sharedPreferences;
    private static final String SHARED_PREF_KEY = "SmartAquaQualityData";
    private static final String SHARED_PREF_READINGS = "QualityDataReadings";
    private static final String TDS_DATA_REF = "TDS_DATA";
    private static final String OFFLINE_REF = ".info/connected";
    private static final String COMMA = ",";
    private static final String EMPTY = "";
    private static final String TDS_LIST = "tdsDataList";

    public SmartAquaQuality() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        handler = new Handler();
        tdsDataList = new ArrayList<>();
        sharedPreferences = requireContext().getSharedPreferences(SHARED_PREF_KEY, Context.MODE_PRIVATE);
        loadDataFromSharedPreferences();

        runnable = new Runnable() {
            @Override
            public void run() {
                if (isOnline && !tdsDataList.isEmpty()) {
                    // Data is available from Firebase and online, show random readings
                    int randomIndex = new Random().nextInt(tdsDataList.size());
                    int reading_ran = tdsDataList.get(randomIndex);
                    displayReading(reading_ran);
                } else {
                    // No data available from Firebase or offline, display the last cached readings in a loop
                    if (!tdsDataList.isEmpty()) {
                        lastDisplayedIndex = (lastDisplayedIndex + 1) % tdsDataList.size();
                        int reading_ran = tdsDataList.get(lastDisplayedIndex);
                        displayReading(reading_ran);
                    }
                }

                handler.postDelayed(this, 5000);
            }
        };
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_smart_aqua_quality, container, false);

        readings_TDS = view.findViewById(R.id.SmartAquaWaterReadings);
        status_TDS = view.findViewById(R.id.SmartAquaWStatus);

        Button dbButton = view.findViewById(R.id.SmartAquaWQButton);

        dbButton.setOnClickListener(view1 -> {
            String reading_TDS_str = readings_TDS.getText().toString();
            String status_TDS_str = status_TDS.getText().toString();

            SmartAquaQualityData qualityData = new SmartAquaQualityData(reading_TDS_str, status_TDS_str);
            dbRef = FirebaseDatabase.getInstance().getReference(SHARED_PREF_READINGS);
            dbRef.child(reading_TDS_str).setValue(qualityData);
            Toast.makeText(getActivity(), R.string.save_data, Toast.LENGTH_SHORT).show();
        });

        setupNetworkConnectivityListener();
        loadDataFromSharedPreferences();
        readDataFromDatabase();

        FloatingActionButton quality_fab = view.findViewById(R.id.SmartAquaFAB);
        quality_fab.setOnClickListener(viewFAB -> {
            String reading_TDS_str = readings_TDS.getText().toString();
            String status_TDS_str = status_TDS.getText().toString();

            // Save the data to a text file
            saveDataToFile(reading_TDS_str, status_TDS_str);

            // Show a toast message to indicate the download
            Toast.makeText(getActivity(), R.string.wq_download_message, Toast.LENGTH_SHORT).show();
        });

        return view;
    }

    private void readDataFromDatabase() {
        DatabaseReference tdsDataRef = FirebaseDatabase.getInstance().getReference(TDS_DATA_REF);

        tdsDataRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                tdsDataList.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    int tdsData = snapshot.getValue(Integer.class);
                    tdsDataList.add(tdsData);
                }
                // Save data to SharedPreferences for offline caching
                saveDataToSharedPreferences();
                // Start displaying random TDS data
                handler.post(runnable);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Handle any errors that occur during data reading
                Toast.makeText(getActivity(), R.string.failedDB, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void saveDataToSharedPreferences() {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        StringBuilder dataStringBuilder = new StringBuilder();

        for (int tdsData : tdsDataList) {
            dataStringBuilder.append(tdsData).append(COMMA);
        }

        editor.putString(TDS_LIST, dataStringBuilder.toString());
        editor.apply();
    }

    private void loadDataFromSharedPreferences() {
        String dataString = sharedPreferences.getString(TDS_LIST, EMPTY);
        String[] dataValues = dataString.split(COMMA);

        tdsDataList.clear();
        for (String value : dataValues) {
            if (!value.isEmpty()) {
                tdsDataList.add(Integer.parseInt(value));
            }
        }
    }

    private void setupNetworkConnectivityListener() {
        DatabaseReference connectedRef = FirebaseDatabase.getInstance().getReference(OFFLINE_REF);
        connectedRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                isOnline = snapshot.getValue(Boolean.class);
                if (!isOnline) {
                    // If offline, load cached data from SharedPreferences
                    loadDataFromSharedPreferences();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Handle network connectivity error
            }
        });
    }

    private void displayReading(int reading_ran) {
        readings_TDS.setText(String.valueOf(reading_ran));
        if (reading_ran >= 390 && reading_ran <= 460) {
            status_TDS.setText(R.string.s_good);
            status_TDS.setTextColor(Color.GREEN);
        } else {
            status_TDS.setText(R.string.s_bad);
            status_TDS.setTextColor(Color.RED);
        }
    }

    private void saveDataToFile(String reading, String status) {
        String folderName = "SmartAquaQualityReadings";
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(new Date());

        // Save JSON data
        String jsonFileName = "SmartAquaReading_" + timeStamp + ".json";
        File folder = new File(getActivity().getExternalFilesDir(null), folderName);
        if (!folder.exists()) {
            folder.mkdirs();
        }

        File jsonFile = new File(folder, jsonFileName);

        try {
            FileOutputStream jsonFileOutputStream = new FileOutputStream(jsonFile);
            OutputStreamWriter jsonOutputStreamWriter = new OutputStreamWriter(jsonFileOutputStream);

            JSONObject dataObject = new JSONObject();
            dataObject.put("Reading", reading);
            dataObject.put("Status", status);
            dataObject.put("DateTime", timeStamp); // Include date and time in JSON

            jsonOutputStreamWriter.write(dataObject.toString());

            jsonOutputStreamWriter.close();
            jsonFileOutputStream.close();
        } catch (IOException | JSONException e) {
            e.printStackTrace();
            Toast.makeText(getActivity(), R.string.wq_json_error, Toast.LENGTH_SHORT).show();
        }

        // Save text data
        String textFileName = "SmartAquaReading_" + timeStamp + ".txt";
        File textFile = new File(folder, textFileName);

        try {
            FileOutputStream textFileOutputStream = new FileOutputStream(textFile);
            OutputStreamWriter textOutputStreamWriter = new OutputStreamWriter(textFileOutputStream);

            textOutputStreamWriter.write("Reading: " + reading + "\n");
            textOutputStreamWriter.write("Status: " + status + "\n");
            textOutputStreamWriter.write("DateTime: " + timeStamp + "\n"); // Include date and time in text

            textOutputStreamWriter.close();
            textFileOutputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(getActivity(), R.string.wq_txt_error, Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        handler.removeCallbacks(runnable);
        saveDataToSharedPreferences();
    }
    @Override
    public void onResume() {
        super.onResume();

        // Load cached data from SharedPreferences when the app resumes
        loadDataFromSharedPreferences();

        // Fetch data from the Firebase Realtime Database
        readDataFromDatabase();

        // Start displaying random TDS data after onResume to ensure the latest data is shown
        handler.post(runnable);
    }
}