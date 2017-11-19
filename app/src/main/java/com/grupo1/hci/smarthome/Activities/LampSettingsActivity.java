package com.grupo1.hci.smarthome.Activities;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.Switch;

import com.grupo1.hci.smarthome.R;

public class LampSettingsActivity extends AppCompatActivity {

    Switch notificationColorSwitch;
    Switch notificationOnSwitch;
    Switch notificationOffSwitch;
    Switch notificationDimmerSwitch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lamp_settings);

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

        notificationColorSwitch = findViewById(R.id.activityLampSettings_notificationColorSwitch);
        notificationDimmerSwitch = findViewById(R.id.activityLampSettings_notificationGrillSwitch);
        notificationOffSwitch = findViewById(R.id.activityLampSettings_notificationOffSwitch);
        notificationOnSwitch = findViewById(R.id.activityLampSettings_notificationOnSwitch);
    }
}
