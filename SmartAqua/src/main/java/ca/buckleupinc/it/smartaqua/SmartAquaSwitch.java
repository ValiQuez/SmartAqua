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

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Switch;
import android.widget.TextView;

import com.google.android.material.snackbar.Snackbar;

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

        view = inflater.inflate(R.layout.fragment_smart_aqua_switch, container, false);

        Switch switchAir = view.findViewById(R.id.SmartAquaSwitchAir);
        Switch switchBubble = view.findViewById(R.id.SmartAquaSwitchBubble);
        statusAir = view.findViewById(R.id.SmartAquaSwitchStatusAir);
        statusBubble = view.findViewById(R.id.SmartAquaSwitchStatusBubble);
        statusAir.setText(R.string.OFF);
        statusBubble.setText(R.string.OFF);

        switchAir.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                statusAir.setText(R.string.ON);
                statusAir.setTextColor(Color.GREEN);
                Snackbar snackbar = Snackbar.make(view, R.string.snackbarAirOn, Snackbar.LENGTH_SHORT);
                snackbar.show();
            } else {
                statusAir.setText(R.string.OFF);
                statusAir.setTextColor(Color.RED);
                Snackbar snackbar = Snackbar.make(view, R.string.snackbarAirOff, Snackbar.LENGTH_SHORT);
                snackbar.show();
            }
        });

        switchBubble.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                statusBubble.setText(R.string.ON);
                statusBubble.setTextColor(Color.GREEN);
                Snackbar snackbar = Snackbar.make(view, R.string.snackbarBubbleOn, Snackbar.LENGTH_SHORT);
                snackbar.show();
            } else {
                statusBubble.setText(R.string.OFF);
                statusBubble.setTextColor(Color.RED);
                Snackbar snackbar = Snackbar.make(view, R.string.snackbarBubbleOff, Snackbar.LENGTH_SHORT);
                snackbar.show();
            }
        });

        return view;
    }
}