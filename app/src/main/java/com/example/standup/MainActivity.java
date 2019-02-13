package com.example.standup;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

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
                    // deliver the notification
                    deliverNotification(MainActivity.this);
                    // Set the toast message for the "on" case
                    toastMessage = getString(R.string.stand_up_on);
                }else {
                    //Cancel notification if the alarm is turned off
                    mNotificationManager.cancelAll();

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
                getString(R.string.stand_up_notication), NotificationManager.IMPORTANCE_HIGH);
        notificationChannel.enableLights(true);
        notificationChannel.setLightColor(Color.RED);
        notificationChannel.enableVibration(true);
        notificationChannel.setDescription(getString(R.string.notification_description));
        mNotificationManager.createNotificationChannel(notificationChannel);
        }
    }

    /**
     * deliver notification method
     * @param context activity context
     */
    private void deliverNotification(Context context){
        // Set up the pending intent that is delivered
        Intent contentIntent = new Intent(context, MainActivity.class);
        PendingIntent contentPendingIntent = PendingIntent.getActivity(context,
                NOTIFICATION_ID,contentIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        // Build the notification with all of the parameters
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context,PRIMARY_CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_stand_up)
                .setContentTitle(getString(R.string.stand_up_alert))
                .setContentText(getString(R.string.notification_text))
                .setContentIntent(contentPendingIntent)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setAutoCancel(true)
                .setDefaults(NotificationCompat.DEFAULT_ALL);

        mNotificationManager.notify(NOTIFICATION_ID,builder.build());
    }
}
