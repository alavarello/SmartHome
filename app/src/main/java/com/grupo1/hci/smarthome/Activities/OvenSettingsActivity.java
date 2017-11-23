package com.grupo1.hci.smarthome.Activities;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Switch;

import com.grupo1.hci.smarthome.Notifications.OvenState;
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
        notificationOnSwitch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                OvenState.setNotifyTurnOnClass(notificationOnSwitch.isChecked());
            }
        });
        notificationOffSwitch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                OvenState.setNotifyTurnOffClass(notificationOffSwitch.isChecked());
            }
        });
        notificationConvectionSwitch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                OvenState.setNotifyConvectionClass(notificationConvectionSwitch.isChecked());
            }
        });
        notificationGrillSwitch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                OvenState.setNotifyGrillClass(notificationGrillSwitch.isChecked());
            }
        });
        notificationTemperatureSwitch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                OvenState.setNotifyTemperatureClass(notificationTemperatureSwitch.isChecked());
            }
        });
        notificationHeatSwitch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                OvenState.setNotifyHeatClass(notificationHeatSwitch.isChecked());
            }
        });
    }

    private void setView() {

        notificationOffSwitch = findViewById(R.id.activityOvenSettings_notificationOffSwitch);
        notificationOnSwitch = findViewById(R.id.activityOvenSettings_notificationOnSwitch);
        notificationConvectionSwitch = findViewById(R.id.activityOvenSettings_notificationConvectionSwitch);
        notificationGrillSwitch = findViewById(R.id.activityOvenSettings_notificationGrillSwitch);
        notificationTemperatureSwitch = findViewById(R.id.activityOvenSettings_notificationTemperatureSwitch);
        notificationHeatSwitch = findViewById(R.id.activityOvenSettings_notificationHeatSwitch);

        notificationOnSwitch.setChecked(OvenState.isNotifyTurnOnClass());
        notificationOffSwitch.setChecked(OvenState.isNotifyTurnOffClass());
        notificationConvectionSwitch.setChecked(OvenState.isNotifyConvectionClass());
        notificationHeatSwitch.setChecked(OvenState.isNotifyHeatClass());
        notificationTemperatureSwitch.setChecked(OvenState.isNotifyTemperatureClass());
        notificationGrillSwitch.setChecked(OvenState.isNotifyGrillClass());
    }
}
