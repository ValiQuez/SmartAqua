/*  CENG-322-0NA: Group 6
    Denis Shwaloff - N01422583
    Alvaro Rodrigo Chavez Moya - N01455107
    Paolo Adrian Quezon - N01424883
    Nicholas Dibiase - N01367109            */

package ca.buckleupinc.it.smartaqua;

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

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class SmartAquaQuality extends Fragment {

    private TextView readings_TDS;
    private TextView status_TDS;
    private Handler handler;
    private Runnable runnable;
    private DatabaseReference dbRef;
    private List<Double> tdsDataList;
    private boolean isOnline;
    private int lastDisplayedIndex = -1;
    private static final String SHARED_PREF_READINGS = "QualityDataReadings";
    private static final String TDS_DATA_REF = "ReadingsRPi/SmartAqua_Readings/tds";
    private static final String OFFLINE_REF = ".info/connected";
    private static final String OFFLINE = "Connect to Wi-Fi...";

    public SmartAquaQuality() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        handler = new Handler();
        tdsDataList = new ArrayList<>();

        runnable = new Runnable() {
            @Override
            public void run() {
                if (isOnline && !tdsDataList.isEmpty()) {
                    // Data is available from Firebase and online, show random readings
                    int randomIndex = new Random().nextInt(tdsDataList.size());
                    Double reading_ran = tdsDataList.get(randomIndex);
                    displayReading(reading_ran);
                } else {
                    // No data available from Firebase or offline, display the last cached readings in a loop
                    if (!tdsDataList.isEmpty()) {
                        lastDisplayedIndex = (lastDisplayedIndex + 1) % tdsDataList.size();
                        Double reading_ran = tdsDataList.get(lastDisplayedIndex);
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
        readDataFromDatabase();

        FloatingActionButton quality_fab = view.findViewById(R.id.SmartAquaFAB);
        quality_fab.setOnClickListener(viewFAB -> {
            SmartAquaDownloaderManager fileDownloader = new SmartAquaDownloaderManager(requireContext());
            fileDownloader.saveWQDataToFile(readings_TDS.getText().toString(), status_TDS.getText().toString());
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
                double tdsData = dataSnapshot.getValue(Double.class);
                tdsDataList.add(tdsData);
                displayReading(tdsData);

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

    private void setupNetworkConnectivityListener() {
        DatabaseReference connectedRef = FirebaseDatabase.getInstance().getReference(OFFLINE_REF);
        connectedRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                isOnline = snapshot.getValue(Boolean.class);
                if (!isOnline) {
                    // If offline, load cached data from SharedPreferences
                    readings_TDS.setText(OFFLINE);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Handle network connectivity error
                Toast.makeText(getActivity(), R.string.failedDB, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void displayReading(Double reading_ran) {
        readings_TDS.setText(String.valueOf(reading_ran));
        if (reading_ran >= 390 && reading_ran <= 460) {
            status_TDS.setText(R.string.s_good);
            status_TDS.setTextColor(Color.GREEN);
        } else {
            status_TDS.setText(R.string.s_bad);
            status_TDS.setTextColor(Color.RED);
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        handler.removeCallbacks(runnable);
    }

    @Override
    public void onResume() {
        super.onResume();

        // Fetch data from the Firebase Realtime Database
        readDataFromDatabase();

        // Start displaying random TDS data after onResume to ensure the latest data is shown
        handler.post(runnable);
    }
}