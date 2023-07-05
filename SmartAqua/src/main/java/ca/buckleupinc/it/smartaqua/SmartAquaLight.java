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

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SmartAquaLight#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SmartAquaLight extends Fragment {

    private Switch switchlightButton;
    private TextView notificationTextView;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public SmartAquaLight() {

    }


    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SmartAquaLight.
     */
    // TODO: Rename and change types and number of parameters
    public static SmartAquaLight newInstance(String param1, String param2) {
        SmartAquaLight fragment = new SmartAquaLight();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view;



        if(getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE){
            view = inflater.inflate(R.layout.fragment_smart_aqua_light_land, container, false);
        }
        else {
            view = inflater.inflate(R.layout.fragment_smart_aqua_light, container, false);
        }

        // Required empty public constructor
        switchlightButton = view.findViewById(R.id.switchlightButton);
        notificationTextView = view.findViewById(R.id.notificationTextView);
        notificationTextView.setText("Light is turned Off.");

        switchlightButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    notificationTextView.setText("Light is turned On.");
                } else {
                    notificationTextView.setText("Light is turned Off.");
                }
            }
        });

        return view;
    }

    public void setSwitchlightButton(Switch switchlightButton) {
        this.switchlightButton = switchlightButton;
    }
}