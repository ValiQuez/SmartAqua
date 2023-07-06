/*  CENG-322-0NA: Group 6
    Denis Shwaloff - N01422583
    Alvaro Rodrigo Chavez Moya - N01455107
    Paolo Adrian Quezon - N01424883
    Nicholas Dibiase - N01367109            */

package ca.buckleupinc.it.smartaqua;

import android.content.res.Configuration;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;

import org.w3c.dom.Text;


public class SmartAquaLight extends Fragment {

    private Switch switchlightButton;
    private TextView notificationTextView;


    public SmartAquaLight() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view;
        view = inflater.inflate(R.layout.fragment_smart_aqua_light, container, false);

        // Required empty public constructor
        switchlightButton = view.findViewById(R.id.switchlightButton);
        notificationTextView = view.findViewById(R.id.notificationTextView);
        notificationTextView.setText(R.string.poplightoff);

        switchlightButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    notificationTextView.setText(R.string.poplighton);
                } else {
                    notificationTextView.setText(R.string.poplightoff);
                }
            }
        });

        return view;
    }

    public void setSwitchlightButton(Switch switchlightButton) {
        this.switchlightButton = switchlightButton;
    }
}