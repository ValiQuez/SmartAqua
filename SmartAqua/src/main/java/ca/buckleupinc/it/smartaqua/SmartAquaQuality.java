/*  CENG-322-0NA: Group 6
    Denis Shwaloff - N01422583
    Alvaro Rodrigo Chavez Moya - N01455107
    Paolo Adrian Quezon - N01424883
    Nicholas Dibiase - N01367109            */

package ca.buckleupinc.it.smartaqua;

import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

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
    private List<Integer> tdsDataList;

    public SmartAquaQuality() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        handler = new Handler();
        runnable = new Runnable() {
            @Override
            public void run() {
                int randomIndex = new Random().nextInt(tdsDataList.size());
                int reading_ran = tdsDataList.get(randomIndex);
                readings_TDS.setText(String.valueOf(reading_ran));
                //int reading_ran = new Random().nextInt(201)+300;
                readings_TDS.setText(String.valueOf(reading_ran));

                if (reading_ran >= 390 && reading_ran <= 460) {
                    status_TDS.setText(R.string.s_good);
                    status_TDS.setTextColor(Color.GREEN);
                } else {
                    status_TDS.setText(R.string.s_bad);
                    status_TDS.setTextColor(Color.RED);
                }

                handler.postDelayed(this,5000);
            }
        };
        tdsDataList = new ArrayList<>();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view;

        view = inflater.inflate(R.layout.fragment_smart_aqua_quality, container, false);

        readings_TDS = view.findViewById(R.id.SmartAquaWaterReadings);
        status_TDS = view.findViewById(R.id.SmartAquaWStatus);

        Button dbButton = view.findViewById(R.id.SmartAquaWQButton);

        dbButton.setOnClickListener(view1 ->{
            String reading_TDS_str = readings_TDS.getText().toString();
            String status_TDS_str = status_TDS.getText().toString();

            SmartAquaQualityData qualityData = new SmartAquaQualityData(reading_TDS_str, status_TDS_str);
            dbRef = FirebaseDatabase.getInstance().getReference("QualityDataReadings");
            dbRef.child(reading_TDS_str).setValue(qualityData);
            Toast.makeText(getActivity(), R.string.save_data, Toast.LENGTH_SHORT).show();
        });

        readDataFromDatabase();

        return view;
    }

    private void readDataFromDatabase() {
        DatabaseReference tdsDataRef = FirebaseDatabase.getInstance().getReference("TDS_DATA");

        tdsDataRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    int tdsData = snapshot.getValue(Integer.class);
                    tdsDataList.add(tdsData);
                }
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



    public void onPause(){
        super.onPause();
        handler.removeCallbacks(runnable);
    }
}