package ca.buckleupinc.it.smartaqua;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.content.SharedPreferences;
import android.widget.ToggleButton;

public class SmartAquaLandscapeMode {

    private final Activity activity;
    private final ToggleButton toggleLockBtn;
    private final SharedPreferences settingsPreferences;

    public SmartAquaLandscapeMode(Activity activity, ToggleButton toggleLockBtn, SharedPreferences settingsPreferences) {
        this.activity = activity;
        this.toggleLockBtn = toggleLockBtn;
        this.settingsPreferences = settingsPreferences;

        // Set the initial state of the toggle button
        toggleLockBtn.setChecked(settingsPreferences.getBoolean("LockToggleState", false));

        // Set the listener for the toggle button
        toggleLockBtn.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                // The toggle is enabled
                lockOrientation();
            } else {
                // The toggle is disabled
                unlockOrientation();
            }
            settingsPreferences.edit().putBoolean("LockToggleState", isChecked).apply();
        });
    }

    private void lockOrientation() {
        activity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
    }

    private void unlockOrientation() {
        activity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED);
    }
}
