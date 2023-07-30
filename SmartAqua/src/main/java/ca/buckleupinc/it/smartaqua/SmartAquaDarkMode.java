package ca.buckleupinc.it.smartaqua;

import android.content.SharedPreferences;
import android.widget.CompoundButton;
import android.widget.Toast;
import android.widget.ToggleButton;

import androidx.appcompat.app.AppCompatDelegate;

public class SmartAquaDarkMode implements CompoundButton.OnCheckedChangeListener {

    private final ToggleButton darkTB;
    private final SharedPreferences settingsPreferences;

    public SmartAquaDarkMode(ToggleButton darkTB, SharedPreferences settingsPreferences) {
        this.darkTB = darkTB;
        this.settingsPreferences = settingsPreferences;
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        // Check if the state has actually changed
        if (isChecked != settingsPreferences.getBoolean("DarkModeToggleState", false)) {
            if (isChecked) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                Toast.makeText(buttonView.getContext(), R.string.darkModeON, Toast.LENGTH_SHORT).show();
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
            }
            settingsPreferences.edit().putBoolean("DarkModeToggleState", isChecked).apply();
        }
    }
}
