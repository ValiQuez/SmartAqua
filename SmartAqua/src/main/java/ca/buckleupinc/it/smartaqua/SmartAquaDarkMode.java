/*  CENG-322-0NA: Group 6
    Denis Shwaloff - N01422583
    Alvaro Rodrigo Chavez Moya - N01455107
    Paolo Adrian Quezon - N01424883
    Nicholas Dibiase - N01367109            */

package ca.buckleupinc.it.smartaqua;

import android.content.SharedPreferences;
import android.widget.CompoundButton;
import android.widget.Toast;
import android.widget.ToggleButton;

import androidx.appcompat.app.AppCompatDelegate;

public class SmartAquaDarkMode implements CompoundButton.OnCheckedChangeListener {

    private final ToggleButton darkTB;
    private final SharedPreferences settingsPreferences;
    private static final String DARK_MODE_TOGGLE_STATE_KEY = "DarkModeToggleState";

    public SmartAquaDarkMode(ToggleButton darkTB, SharedPreferences settingsPreferences) {
        this.darkTB = darkTB;
        this.settingsPreferences = settingsPreferences;
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        // Check if the state has actually changed
        if (isChecked != settingsPreferences.getBoolean(DARK_MODE_TOGGLE_STATE_KEY, false)) {
            if (isChecked) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                Toast.makeText(buttonView.getContext(), R.string.darkModeON, Toast.LENGTH_SHORT).show();
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
            }
            settingsPreferences.edit().putBoolean(DARK_MODE_TOGGLE_STATE_KEY, isChecked).apply();
        }
    }
}
