/*  CENG-322-0NA: Group 6
    Denis Shwaloff - N01422583
    Alvaro Rodrigo Chavez Moya - N01455107
    Paolo Adrian Quezon - N01424883
    Nicholas Dibiase - N01367109            */

package ca.buckleupinc.it.smartaqua;

import android.os.Bundle;
import android.os.Handler;
import android.content.Intent;
import android.content.Context;
import android.content.SharedPreferences;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

public class SmartAquaSplash extends AppCompatActivity {
    private static final int SPLASH_DELAY = 3000; // Splash screen delay in milliseconds

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_splash);

        SharedPreferences sharedPref = getSharedPreferences("UserPrefSettings", Context.MODE_PRIVATE);

        boolean darkModeEnabled = sharedPref.getBoolean("DarkModeToggleState", false);
        if (darkModeEnabled) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        }

        // Delay the execution of the next activity
        new Handler().postDelayed(() -> {
            // Start the main activity after the splash delay
            Intent intent = new Intent(SmartAquaSplash.this, MainActivity.class);
            startActivity(intent);
            finish();
        }, SPLASH_DELAY);
    }
}