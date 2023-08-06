/*  CENG-322-0NA: Group 6
    Denis Shwaloff - N01422583
    Alvaro Rodrigo Chavez Moya - N01455107
    Paolo Adrian Quezon - N01424883
    Nicholas Dibiase - N01367109            */

package ca.buckleupinc.it.smartaqua;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.Settings;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

public class SmartAquaLocation {

    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1;
    private LocationManager locationManager;
    private LocationListener locationListener;
    private Fragment fragment;

    private static final String PERMISSION_ACCESS_COARSE_LOCATION = Manifest.permission.ACCESS_COARSE_LOCATION;
    private static final String LOCATION_PROVIDER_NETWORK = LocationManager.NETWORK_PROVIDER;
    private static final String LOCATION_PROVIDER_GPS = LocationManager.GPS_PROVIDER;

    public SmartAquaLocation(Fragment fragment) {
        this.fragment = fragment;
    }

    public void requestLocationPermission() {
        LocationPermissionTask permissionTask = new LocationPermissionTask();
        permissionTask.execute();
    }

    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode == LOCATION_PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Location permission granted, display current location
                displayCurrentLocation();
            }
        }
    }

    private class LocationPermissionTask extends AsyncTask<Void, Void, Boolean> {
        @Override
        protected Boolean doInBackground(Void... voids) {
            Context context = fragment.getContext();
            return context != null && ActivityCompat.checkSelfPermission(context, PERMISSION_ACCESS_COARSE_LOCATION) ==
                    PackageManager.PERMISSION_GRANTED;
        }

        @Override
        protected void onPostExecute(Boolean isPermissionGranted) {
            if (isPermissionGranted) {
                // Permission already granted, display current location
                displayCurrentLocation();
            } else {
                Context context = fragment.getContext();
                if (context != null) {
                    ActivityCompat.requestPermissions(fragment.requireActivity(),
                            new String[]{PERMISSION_ACCESS_COARSE_LOCATION},
                            LOCATION_PERMISSION_REQUEST_CODE);
                }
            }
        }
    }

    private void displayCurrentLocation() {
        Context context = fragment.getContext();
        if (context == null) {
            return;
        }

        locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                // Display the current location
                double latitude = location.getLatitude();
                double longitude = location.getLongitude();
                String currentLocation = context.getString(R.string.settingscurrentLocation, latitude, longitude);
                Toast.makeText(context, currentLocation, Toast.LENGTH_SHORT).show();
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
            if (ActivityCompat.checkSelfPermission(context, PERMISSION_ACCESS_COARSE_LOCATION) ==
                    PackageManager.PERMISSION_GRANTED) {
                locationManager.requestLocationUpdates(LOCATION_PROVIDER_NETWORK, 0, 0, locationListener);
            }
        } else {
            // Location services are disabled, open settings to enable them
            Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
            context.startActivity(intent);
        }
    }

    private boolean isLocationEnabled() {
        Context context = fragment.getContext();
        if (context == null) {
            return false;
        }
        LocationManager locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        boolean isGpsEnabled = locationManager.isProviderEnabled(LOCATION_PROVIDER_GPS);
        boolean isNetworkEnabled = locationManager.isProviderEnabled(LOCATION_PROVIDER_NETWORK);
        return isGpsEnabled || isNetworkEnabled;
    }
}
