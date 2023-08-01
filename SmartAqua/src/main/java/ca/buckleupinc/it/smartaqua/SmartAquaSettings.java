/*  CENG-322-0NA: Group 6
    Denis Shwaloff - N01422583
    Alvaro Rodrigo Chavez Moya - N01455107
    Paolo Adrian Quezon - N01424883
    Nicholas Dibiase - N01367109            */

package ca.buckleupinc.it.smartaqua;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;
import android.widget.ToggleButton;

public class SmartAquaSettings extends Fragment {

    public SmartAquaSettings() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_smart_aqua_settings, container, false);

        SharedPreferences settingsPreferences = getActivity().getSharedPreferences("UserPrefSettings", Context.MODE_PRIVATE);

        //=====LOCK LANDSCAPE MODE=====
        ToggleButton toggleLockBtn = view.findViewById(R.id.SmartAquaPortraitLockToggleBtn);
        SmartAquaLandscapeMode landscapeModeHandler = new SmartAquaLandscapeMode(getActivity(), toggleLockBtn, settingsPreferences);

        //=====DARK MODE=====
        ToggleButton darkTB = view.findViewById(R.id.SmartAquaDarkModeToggleBtn);
        SmartAquaDarkMode darkModeHandler = new SmartAquaDarkMode(darkTB, settingsPreferences);
        darkTB.setOnCheckedChangeListener(darkModeHandler);

        //=====LOCATION USER PERMISSION REQUEST=====
        Button btn = view.findViewById(R.id.SmartAquaUsrPermBtn);
        SmartAquaLocation smartAquaLocation = new SmartAquaLocation(this);
        btn.setOnClickListener(v -> smartAquaLocation.requestLocationPermission());

        //=====MUTE APP=====
        ToggleButton muteToggleBtn = view.findViewById(R.id.SmartAquaMuteToggleBtn);
        SmartAquaMute muteHandler = new SmartAquaMute(muteToggleBtn, settingsPreferences, getActivity());
        muteToggleBtn.setOnCheckedChangeListener(muteHandler);

        //=====RESET SETTINGS=====
        Button resetBTN = view.findViewById(R.id.SmartAquaResetBtn);
        resetBTN.setOnClickListener(v -> {
            toggleLockBtn.setChecked(false);
            darkTB.setChecked(false);
            muteToggleBtn.setChecked(false);

            settingsPreferences.edit().clear().apply();

            Toast.makeText(getActivity(), R.string.reset_snackbar, Toast.LENGTH_SHORT).show();
        });

        toggleLockBtn.setChecked(settingsPreferences.getBoolean("LockToggleState", false));
        darkTB.setChecked(settingsPreferences.getBoolean("DarkModeToggleState", false));
        muteToggleBtn.setChecked(settingsPreferences.getBoolean("MuteToggleState", false));

        return view;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        // Pass the permission results to the SmartAquaLocation class
        SmartAquaLocation smartAquaLocation = new SmartAquaLocation(this);
        smartAquaLocation.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }
}
