/*  CENG-322-0NA: Group 6
    Denis Shwaloff - N01422583
    Alvaro Rodrigo Chavez Moya - N01455107
    Paolo Adrian Quezon - N01424883
    Nicholas Dibiase - N01367109            */

package ca.buckleupinc.it.smartaqua;

import android.content.Context;
import android.content.SharedPreferences;
import android.media.AudioManager;
import android.widget.CompoundButton;
import android.widget.ToggleButton;

public class SmartAquaMute implements CompoundButton.OnCheckedChangeListener {

    private final ToggleButton muteToggleBtn;
    private final SharedPreferences settingsPreferences;
    private final Context context;
    private final static String MuteToggleStateKey = "MuteToggleState";

    public SmartAquaMute(ToggleButton muteToggleBtn, SharedPreferences settingsPreferences, Context context) {
        this.muteToggleBtn = muteToggleBtn;
        this.settingsPreferences = settingsPreferences;
        this.context = context;
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        // Check if the state has changed
        if (isChecked != settingsPreferences.getBoolean(MuteToggleStateKey, false)) {
            AudioManager aManager = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
            if (isChecked) {
                // mutes device's volume
                aManager.adjustVolume(AudioManager.ADJUST_MUTE, AudioManager.FLAG_SHOW_UI);
            } else {
                // unmutes device's volume
                aManager.adjustVolume(AudioManager.ADJUST_UNMUTE, AudioManager.FLAG_SHOW_UI);
            }
            settingsPreferences.edit().putBoolean(MuteToggleStateKey, isChecked).apply();
        }
    }
}
