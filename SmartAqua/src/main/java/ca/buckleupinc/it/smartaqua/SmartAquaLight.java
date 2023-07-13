/*  CENG-322-0NA: Group 6
    Denis Shwaloff - N01422583
    Alvaro Rodrigo Chavez Moya - N01455107
    Paolo Adrian Quezon - N01424883
    Nicholas Dibiase - N01367109            */

package ca.buckleupinc.it.smartaqua;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

public class SmartAquaLight extends Fragment {

    private static final String CHANNEL_ID = "LightNotificationChannel";
    private static final int NOTIFICATION_ID = 1;

    @SuppressLint("UseSwitchCompatOrMaterialCode")
    private Switch switchlightButton;
    private TextView notificationTextView;
    private NotificationManager notificationManager;
    private ImageView lightBulbImage;

    public SmartAquaLight() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_smart_aqua_light, container, false);


        switchlightButton = view.findViewById(R.id.switchlightButton);
        notificationTextView = view.findViewById(R.id.notificationTextView);
        notificationTextView.setText(R.string.poplightoff);
        lightBulbImage = view.findViewById(R.id.SmartAqualightbulbOff);

        switchlightButton.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                notificationTextView.setText(R.string.notificationlighton);
                lightBulbImage.setImageResource(R.drawable.aqua_smart_light_bulb_on);
                sendNotification(getString(R.string.notificationlighton));
            } else {
                notificationTextView.setText(R.string.notificationlightoff);
                lightBulbImage.setImageResource(R.drawable.aqua_smart_light_bulb_off);
                sendNotification(getString(R.string.notificationlightoff));
            }
        });
        notificationManager = (NotificationManager) requireActivity().getSystemService(Context.NOTIFICATION_SERVICE);

        return view;
    }

    private void sendNotification(String message) {
        // Create a notification channel
        NotificationChannel channel = new NotificationChannel(CHANNEL_ID, getString(R.string.lightnotification), NotificationManager.IMPORTANCE_DEFAULT);
        notificationManager.createNotificationChannel(channel);

// Creates a notification
        Notification.Builder builder = new Notification.Builder(requireActivity(), CHANNEL_ID)
                .setSmallIcon(R.drawable.notification)
                .setContentTitle(getString(R.string.lightstatus))
                .setContentText(message)
                .setAutoCancel(true);

        // Shows the notification
        notificationManager.notify(NOTIFICATION_ID, builder.build());
    }
}

