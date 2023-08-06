/*  CENG-322-0NA: Group 6
    Denis Shwaloff - N01422583
    Alvaro Rodrigo Chavez Moya - N01455107
    Paolo Adrian Quezon - N01424883
    Nicholas Dibiase - N01367109            */

package ca.buckleupinc.it.smartaqua;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.content.SharedPreferences;
import android.widget.ToggleButton;

public class SmartAquaLandscapeMode {

    private final Activity activity;
    private final ToggleButton toggleLockBtn;
    private final SharedPreferences settingsPreferences;
    private final static String LockToggleStateKey = "LockToggleState";

    public SmartAquaLandscapeMode(Activity activity, ToggleButton toggleLockBtn, SharedPreferences settingsPreferences) {
        this.activity = activity;
        this.toggleLockBtn = toggleLockBtn;
        this.settingsPreferences = settingsPreferences;

        // Set the initial state of the toggle button
        toggleLockBtn.setChecked(settingsPreferences.getBoolean(LockToggleStateKey, false));

        // Set the listener for the toggle button
        toggleLockBtn.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                // The toggle is enabled
                lockOrientation();
            } else {
                // The toggle is disabled
                unlockOrientation();
            }
            settingsPreferences.edit().putBoolean(LockToggleStateKey, isChecked).apply();
        });
    }

    private void lockOrientation() {
        activity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
    }

    private void unlockOrientation() {
        activity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED);
    }
}
