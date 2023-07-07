/*  CENG-322-0NA: Group 6
    Denis Shwaloff - N01422583
    Alvaro Rodrigo Chavez Moya - N01455107
    Paolo Adrian Quezon - N01424883
    Nicholas Dibiase - N01367109            */
package ca.buckleupinc.it.smartaqua;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.media.AudioManager;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ToggleButton;

import com.google.android.material.snackbar.Snackbar;
import android.Manifest;

public class SmartAquaSettings extends Fragment {

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
                //Toast.makeText(getActivity(),R.string.on,Toast.LENGTH_SHORT).show();
            } else {
                // The toggle is disabled
                getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED);
            }
        });

        //=====LOCATION USER PERMISSION REQUEST=====
        Button btn = view.findViewById(R.id.SmartAquaUsrPermBtn);
        btn.setOnClickListener(view1 -> {

            if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle(getResources().getString(R.string.settingsPermissionTitle));
                builder.setMessage(getResources().getString(R.string.settingsPermissionBody));
                builder.setPositiveButton(R.string.settingsPermissionGrant, (dialogInterface, i) -> {

                    //Toast.makeText(getActivity(),R.string.settingsAccessGranted, Toast.LENGTH_SHORT).show();
                    Snackbar grantedBar = Snackbar.make(view1, R.string.settingsAccessGranted, Snackbar.LENGTH_SHORT);
                    grantedBar.show();
                    Uri link = Uri.parse("http://maps.google.com/maps");
                    Intent mapIntent = new Intent(Intent.ACTION_VIEW, link);
                    mapIntent.setPackage("com.google.android.apps.maps");
                    startActivity(mapIntent);
                });

                builder.setNegativeButton(R.string.settingsPermissionDeny, (dialogInterface, i) -> {
                    //Toast.makeText(getActivity(),R.string.settingsAccessDenied, Toast.LENGTH_SHORT).show();
                    Snackbar deniedBar = Snackbar.make(view1, R.string.settingsAccessDenied, Snackbar.LENGTH_SHORT);
                    deniedBar.show();
                });

                AlertDialog alert = builder.create();
                alert.show();
            }
        });

        //=====MUTE APP=====
        AudioManager aManager = (AudioManager)getActivity().getSystemService(Context.AUDIO_SERVICE);
        ToggleButton muteToggleBtn = view.findViewById(R.id.SmartAquaMuteToggleBtn);
        muteToggleBtn.setOnClickListener(v -> {
            if(muteToggleBtn.isChecked()){
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

        return view;
    }


}