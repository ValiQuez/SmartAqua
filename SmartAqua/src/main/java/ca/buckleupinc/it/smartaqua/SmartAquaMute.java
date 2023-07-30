package ca.buckleupinc.it.smartaqua;

import android.content.Context;
import android.content.SharedPreferences;
import android.media.AudioManager;
import android.widget.CompoundButton;
import android.widget.Toast;
import android.widget.ToggleButton;

public class SmartAquaMute implements CompoundButton.OnCheckedChangeListener {

    private final ToggleButton muteToggleBtn;
    private final SharedPreferences settingsPreferences;
    private final Context context;

    public SmartAquaMute(ToggleButton muteToggleBtn, SharedPreferences settingsPreferences, Context context) {
        this.muteToggleBtn = muteToggleBtn;
        this.settingsPreferences = settingsPreferences;
        this.context = context;
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        // Check if the state has actually changed
        if (isChecked != settingsPreferences.getBoolean("MuteToggleState", false)) {
            AudioManager aManager = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
            if (isChecked) {
                // mutes device's volume
                aManager.adjustVolume(AudioManager.ADJUST_MUTE, AudioManager.FLAG_SHOW_UI);
            } else {
                // unmutes device's volume
                aManager.adjustVolume(AudioManager.ADJUST_UNMUTE, AudioManager.FLAG_SHOW_UI);
            }
            settingsPreferences.edit().putBoolean("MuteToggleState", isChecked).apply();
        }
    }
}
