package com.grupo1.hci.smarthome.Activities;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.Switch;

import com.grupo1.hci.smarthome.R;

public class DoorSettingsActivity extends AppCompatActivity {

    Switch notificationOpenCloseSwitch;
    Switch notificationLockUnlockSwitch;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_door_settings);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Get a support ActionBar corresponding to this toolbar
        ActionBar ab = getSupportActionBar();
        // Enable the Up button
        ab.setDisplayHomeAsUpEnabled(true);
        setView();
        setOnSwitchListeners();
    }

    private void setOnSwitchListeners() {

    }

    private void setView() {

        notificationLockUnlockSwitch = findViewById(R.id.activityBlindSettings_notificationLockUnlockSwitch);
        notificationOpenCloseSwitch = findViewById(R.id.activityBlindSettings_notificationOpenCloseSwitch);
    }
}
