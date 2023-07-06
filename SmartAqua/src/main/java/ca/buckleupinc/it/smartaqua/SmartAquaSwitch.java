/*  CENG-322-0NA: Group 6
    Denis Shwaloff - N01422583
    Alvaro Rodrigo Chavez Moya - N01455107
    Paolo Adrian Quezon - N01424883
    Nicholas Dibiase - N01367109            */

package ca.buckleupinc.it.smartaqua;

import android.content.res.Configuration;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Switch;
import android.widget.TextView;

public class SmartAquaSwitch extends Fragment {

    private TextView statusAir;
    private TextView statusBubble;

    public SmartAquaSwitch(){

    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view;

        if(getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE){
            view = inflater.inflate(R.layout.fragment_smart_aqua_switch, container, false);
        }
        else {
            view = inflater.inflate(R.layout.fragment_smart_aqua_switch, container, false);
        }

        Switch switchAir = view.findViewById(R.id.SmartAquaSwitchAir);
        Switch switchBubble = view.findViewById(R.id.SmartAquaSwitchBubble);
        statusAir = view.findViewById(R.id.SmartAquaSwitchStatusAir);
        statusBubble = view.findViewById(R.id.SmartAquaSwitchStatusBubble);
        statusAir.setText(R.string.off);
        statusBubble.setText(R.string.off);

        switchAir.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                statusAir.setText(R.string.on);
                statusAir.setTextColor(Color.GREEN);
            } else {
                statusAir.setText(R.string.off);
                statusAir.setTextColor(Color.RED);
            }
        });

        switchBubble.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                statusBubble.setText(R.string.on);
                statusBubble.setTextColor(Color.GREEN);
            } else {
                statusBubble.setText(R.string.off);
                statusBubble.setTextColor(Color.RED);
            }
        });

        return view;
    }
}