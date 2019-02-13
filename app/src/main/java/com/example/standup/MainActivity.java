package com.example.standup;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.CompoundButton;
import android.widget.Toast;
import android.widget.ToggleButton;

public class MainActivity extends AppCompatActivity {

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
    }
}
