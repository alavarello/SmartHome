package com.grupo1.hci.smarthome.Activities;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.Switch;

import com.grupo1.hci.smarthome.R;

public class OvenSettingsActivity extends AppCompatActivity {

    Switch notificationGrillSwitch;
    Switch notificationOnSwitch;
    Switch notificationOffSwitch;
    Switch notificationConvectionSwitch;
    Switch notificationHeatSwitch;
    Switch notificationTemperatureSwitch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_oven_settings);

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

        notificationOffSwitch = findViewById(R.id.activityOvenSettings_notificationOffSwitch);
        notificationOnSwitch = findViewById(R.id.activityOvenSettings_notificationOnSwitch);
        notificationConvectionSwitch = findViewById(R.id.activityOvenSettings_notificationConvectionSwitch);
        notificationGrillSwitch = findViewById(R.id.activityOvenSettings_notificationGrillSwitch);
        notificationTemperatureSwitch = findViewById(R.id.activityOvenSettings_notificationTemperatureSwitch);
        notificationHeatSwitch = findViewById(R.id.activityOvenSettings_notificationHeatSwitch);
    }
}
