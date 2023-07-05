/*  CENG-322-0NA: Group 6
    Denis Shwaloff - N01422583
    Alvaro Rodrigo Chavez Moya - N01455107
    Paolo Adrian Quezon - N01424883
    Nicholas Dibiase - N01367109            */

package ca.buckleupinc.it.smartaqua;

import android.content.res.Configuration;
import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.Random;

public class SmartAquaQuality extends Fragment {

    private TextView readings_TDS;
    private TextView status_TDS;
    private Handler handler;
    private Runnable runnable;

    public SmartAquaQuality() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        handler = new Handler();
        runnable = new Runnable() {
            @Override
            public void run() {
                int reading_ran = new Random().nextInt(201)+300;
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

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view;

        if(getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE){
            view = inflater.inflate(R.layout.fragment_smart_aqua_quality_land, container, false);
        }
        else {
            view = inflater.inflate(R.layout.fragment_smart_aqua_quality, container, false);
        }

        readings_TDS = view.findViewById(R.id.SmartAquaWaterReadings);
        status_TDS = view.findViewById(R.id.SmartAquaWStatus);

        return view;
    }

    public void onResume(){
        super.onResume();
        handler.post(runnable);
    }

    public void onPause(){
        super.onPause();
        handler.removeCallbacks(runnable);
    }
}