/*  CENG-322-0NA: Group 6
    Denis Shwaloff - N01422583
    Alvaro Rodrigo Chavez Moya - N01455107
    Paolo Adrian Quezon - N01424883
    Nicholas Dibiase - N01367109            */

package ca.buckleupinc.it.smartaqua;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.media.AudioManager;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.provider.Settings;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.google.android.material.snackbar.Snackbar;

public class SmartAquaSettings extends Fragment {

    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1;

    private LocationManager locationManager;
    private LocationListener locationListener;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view;

        view = inflater.inflate(R.layout.fragment_smart_aqua_settings, container, false);

        //=====LOCK LANDSCAPE MODE=====
        ToggleButton toggleLockBtn = view.findViewById(R.id.SmartAquaPortraitLockToggleBtn);
        int currentOrientation = getResources().getConfiguration().orientation;
        toggleLockBtn.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                // The toggle is enabled
                if (currentOrientation == Configuration.ORIENTATION_LANDSCAPE) {
                    getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_USER_PORTRAIT);
                }
                getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_USER_PORTRAIT);
            } else {
                // The toggle is disabled
                getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED);
            }
        });

        //=====DARK MODE=====
        ToggleButton darkTB = view.findViewById(R.id.SmartAquaDarkModeToggleBtn);
        darkTB.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if(isChecked){
                Snackbar ON_SnackBar = Snackbar.make(view, R.string.darkModeON, Snackbar.LENGTH_SHORT);
                ON_SnackBar.show();
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
            }
            else {
                Snackbar OFF_SnackBar = Snackbar.make(view, R.string.darkModeOFF, Snackbar.LENGTH_SHORT);
                OFF_SnackBar.show();
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
            }
        });

        //=====LOCATION USER PERMISSION REQUEST=====
        Button btn = view.findViewById(R.id.SmartAquaUsrPermBtn);
        btn.setOnClickListener(view1 -> requestLocationPermission());

        //=====MUTE APP=====
        AudioManager aManager = (AudioManager)getActivity().getSystemService(Context.AUDIO_SERVICE);
        ToggleButton muteToggleBtn = view.findViewById(R.id.SmartAquaMuteToggleBtn);
        muteToggleBtn.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if(isChecked){
                //mutes device's volume
                aManager.adjustVolume(AudioManager.ADJUST_MUTE, AudioManager.FLAG_SHOW_UI);

                //Snackbar snackbar = Snackbar.make(view, "App has been muted", Snackbar.LENGTH_SHORT);
                //snackbar.show();
            }
            else{
                //unmutes device's volume
                aManager.adjustVolume(AudioManager.ADJUST_UNMUTE, AudioManager.FLAG_SHOW_UI);

                //Snackbar snackbar = Snackbar.make(view, "App has been unmuted", Snackbar.LENGTH_SHORT);
                //snackbar.show();
            }
        });

        Button resetBTN = view.findViewById(R.id.SmartAquaResetBtn);
        resetBTN.setOnClickListener(v -> {
            toggleLockBtn.setChecked(false);
            darkTB.setChecked(false);
            muteToggleBtn.setChecked(false);
            Snackbar resetSnackbar = Snackbar.make(view, R.string.reset_snackbar, Snackbar.LENGTH_SHORT);
            resetSnackbar.show();
        });

        return view;
    }
    private void requestLocationPermission() {
        if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(),
                    Manifest.permission.ACCESS_COARSE_LOCATION)) {
                showPermissionExplanationDialog();
            } else {
                ActivityCompat.requestPermissions(getActivity(),
                        new String[]{Manifest.permission.ACCESS_COARSE_LOCATION},
                        LOCATION_PERMISSION_REQUEST_CODE);
            }
        } else {
            // Permission already granted, displays current location
            displayCurrentLocation();
        }
    }

    private void showPermissionExplanationDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(getResources().getString(R.string.settingsPermissionTitle));
        builder.setMessage(getResources().getString(R.string.settingsPermissionBody));
        builder.setIcon(R.drawable.location_icon);
        builder.setPositiveButton(R.string.settingsPermissionGrant, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                // Request location permission
                ActivityCompat.requestPermissions(getActivity(),
                        new String[]{Manifest.permission.ACCESS_COARSE_LOCATION},
                        LOCATION_PERMISSION_REQUEST_CODE);
            }
        });
        builder.setNegativeButton(R.string.settingsPermissionDeny, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                // Permission denied, do nothing
            }
        });
        AlertDialog alert = builder.create();
        alert.show();
    }

    private void displayCurrentLocation() {
        locationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
        locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                // Display the current location
                double latitude = location.getLatitude();
                double longitude = location.getLongitude();
                String currentLocation = getString(R.string.settingscurrentLocation, latitude, longitude);
                Toast.makeText(getActivity(), currentLocation, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {
            }

            @Override
            public void onProviderEnabled(String provider) {
            }

            @Override
            public void onProviderDisabled(String provider) {
            }
        };

        // Check if location services are enabled
        if (isLocationEnabled()) {
            // Request location updates
            if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION)
                    == PackageManager.PERMISSION_GRANTED) {
                locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, locationListener);
            }
        } else {
            // Location services are disabled, open settings to enable them
            Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
            startActivity(intent);
        }
    }

    private boolean isLocationEnabled() {
        LocationManager locationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
        boolean isGpsEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        boolean isNetworkEnabled = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
        return isGpsEnabled || isNetworkEnabled;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == LOCATION_PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Location permission granted, display current location
                displayCurrentLocation();
            } else {
                // Location permission denied, do nothing
            }
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (locationManager != null && locationListener != null) {
            locationManager.removeUpdates(locationListener);
        }
    }


}