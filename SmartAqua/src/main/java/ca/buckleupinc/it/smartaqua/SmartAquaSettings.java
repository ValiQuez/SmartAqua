/*  CENG-322-0NA: Group 6
    Denis Shwaloff - N01422583
    Alvaro Rodrigo Chavez Moya - N01455107
    Paolo Adrian Quezon - N01424883
    Nicholas Dibiase - N01367109            */

package ca.buckleupinc.it.smartaqua;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.annotation.NonNull;
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

public class SmartAquaSettings extends Fragment {

    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1;
    private LocationManager locationManager;
    private LocationListener locationListener;

    public SmartAquaSettings() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view;

        view = inflater.inflate(R.layout.fragment_smart_aqua_settings, container, false);

        SharedPreferences settingsPreferences = getActivity().getSharedPreferences("UserPrefSettings",Context.MODE_PRIVATE);

        //=====LOCK LANDSCAPE MODE=====
        ToggleButton toggleLockBtn = view.findViewById(R.id.SmartAquaPortraitLockToggleBtn);
        SmartAquaLandscapeMode landscapeModeHandler = new SmartAquaLandscapeMode(getActivity(), toggleLockBtn, settingsPreferences);

        //=====DARK MODE=====
        ToggleButton darkTB = view.findViewById(R.id.SmartAquaDarkModeToggleBtn);
        SmartAquaDarkMode darkModeHandler = new SmartAquaDarkMode(darkTB, settingsPreferences);
        darkTB.setOnCheckedChangeListener(darkModeHandler);

        //=====LOCATION USER PERMISSION REQUEST=====
        Button btn = view.findViewById(R.id.SmartAquaUsrPermBtn);
        btn.setOnClickListener(view1 -> {
            LocationPermissionTask permissionTask = new LocationPermissionTask();
            permissionTask.execute();
        });

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

    private class LocationPermissionTask extends AsyncTask<Void, Void, Boolean> {
        @Override
        protected Boolean doInBackground(Void... voids) {
            return ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION)
                    == PackageManager.PERMISSION_GRANTED;
        }
        @Override
        protected void onPostExecute(Boolean isPermissionGranted) {
            if (isPermissionGranted) {
                // Permission already granted, display current location
                displayCurrentLocation();
            } else {
                ActivityCompat.requestPermissions(getActivity(),
                        new String[]{Manifest.permission.ACCESS_COARSE_LOCATION},
                        LOCATION_PERMISSION_REQUEST_CODE);
            }
        }
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
            }  // Location permission denied, do nothing

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