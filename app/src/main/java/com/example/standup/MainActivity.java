package com.example.standup;

import androidx.appcompat.app.AppCompatActivity;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.CompoundButton;
import android.widget.Toast;
import android.widget.ToggleButton;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    private NotificationManager mNotificationManager;
    private static final int NOTIFICATION_ID = 0;
    private static final String PRIMARY_CHANNEL_ID = "primary_notification_channel";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Toggle switch
        ToggleButton alarmToggle = findViewById(R.id.alarmToggle);

        alarmToggle.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                String toastMessage;
                if(isChecked){
                    // Set the toast message for the "on" case
                    toastMessage = getString(R.string.stand_up_on);
                }else {
                    // set the toast message for the "off" case
                    toastMessage = getString(R.string.stand_up_off);
                }
                // show message to say the alarm is turned on or off
                Toast.makeText(MainActivity.this,toastMessage,Toast.LENGTH_SHORT).show();
            }
        });
        // instantiate notification manager
        mNotificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

    }

    /**
     * Creates a Notification channel, for OREO and higher.
     */
    public void createNotificationChannel(){

        // Create a notification manager object.
        mNotificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

       // notification channels are only available in Oreo or higher
        // therefore a check SDK version
        if(android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O){
            // Create a notification with all the parameters
        NotificationChannel notificationChannel = new NotificationChannel(PRIMARY_CHANNEL_ID,
                "Stand up notification", NotificationManager.IMPORTANCE_HIGH);
        notificationChannel.enableLights(true);
        notificationChannel.setLightColor(Color.RED);
        notificationChannel.enableVibration(true);
        notificationChannel.setDescription("Notifies every 15 minutes to stand up and walk");
        mNotificationManager.createNotificationChannel(notificationChannel);
        }
    }

    /**
     * deliver notification method
     * @param context activity context
     */
    private void deliverNotification(Context context){
        Intent contentIntent = new Intent(context, MainActivity.class);
        PendingIntent contentPendingIntent = PendingIntent.getActivity(context,
                NOTIFICATION_ID,contentIntent, PendingIntent.FLAG_UPDATE_CURRENT);
    }
}
