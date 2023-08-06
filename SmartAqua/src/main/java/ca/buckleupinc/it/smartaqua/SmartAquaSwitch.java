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
    private SharedPreferences switchPref;

    public SmartAquaSwitch(){}

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

        switchPref = getActivity().getSharedPreferences("SwitchPref", Context.MODE_PRIVATE);

        boolean switchAirState = switchPref.getBoolean("SwitchAirState", false);
        boolean switchBubbleState = switchPref.getBoolean("SwitchBubbleState", false);
        switchAir.setChecked(switchAirState);
        switchBubble.setChecked(switchBubbleState);

        // Restore TextView states
        boolean statusAirState = switchPref.getBoolean("StatusAirState", false);
        boolean statusBubbleState = switchPref.getBoolean("StatusBubbleState", false);
        setStatusText(statusAir, statusAirState);
        setStatusText(statusBubble, statusBubbleState);

        switchAir.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                setStatusText(statusAir, true);
                Snackbar snackbar = Snackbar.make(view, R.string.snackbarAirOn, Snackbar.LENGTH_SHORT);
                snackbar.show();
            } else {
                setStatusText(statusAir, false);
                Snackbar snackbar = Snackbar.make(view, R.string.snackbarAirOff, Snackbar.LENGTH_SHORT);
                snackbar.show();
            }
            switchPref.edit().putBoolean("SwitchAirState", isChecked).apply();
        });

        switchBubble.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                setStatusText(statusBubble, true);
                Snackbar snackbar = Snackbar.make(view, R.string.snackbarBubbleOn, Snackbar.LENGTH_SHORT);
                snackbar.show();
            } else {
                setStatusText(statusBubble, false);
                Snackbar snackbar = Snackbar.make(view, R.string.snackbarBubbleOff, Snackbar.LENGTH_SHORT);
                snackbar.show();
            }
            switchPref.edit().putBoolean("SwitchBubbleState", isChecked).apply();
        });

        return view;
    }

    private void setStatusText(TextView textView, boolean isOn) {
        if (isOn) {
            textView.setText(R.string.ON);
            textView.setTextColor(Color.GREEN);
        } else {
            textView.setText(R.string.OFF);
            textView.setTextColor(Color.RED);
        }
        switchPref.edit().putBoolean(getStatusKey(textView), isOn).apply();
    }

    private String getStatusKey(TextView textView) {
        if (textView == statusAir) {
            return "StatusAirState";
        } else if (textView == statusBubble) {
            return "StatusBubbleState";
        }
        return "";
    }
}