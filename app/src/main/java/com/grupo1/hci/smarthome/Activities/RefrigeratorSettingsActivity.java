package com.grupo1.hci.smarthome.Activities;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Switch;

import com.grupo1.hci.smarthome.Model.Constants;
import com.grupo1.hci.smarthome.Model.Lamp;
import com.grupo1.hci.smarthome.Model.Refrigerator;
import com.grupo1.hci.smarthome.Notifications.ApiService;
import com.grupo1.hci.smarthome.Notifications.DeviceState;
import com.grupo1.hci.smarthome.Notifications.LampState;
import com.grupo1.hci.smarthome.Notifications.RefrigeratorState;
import com.grupo1.hci.smarthome.R;

public class RefrigeratorSettingsActivity extends AppCompatActivity {

    Switch notificationTemperatureSwitch;
    Switch notificationFreezerTemperatureSwitch;
    Switch notificationModeSwitch;
    Refrigerator refrigerator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_refri_settings);
        refrigerator = (Refrigerator) getIntent().getSerializableExtra(Constants.DEVICE_INTENT);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        // Get a support ActionBar corresponding to this toolbar
        ActionBar ab = getSupportActionBar();
        // Enable the Up button
        ab.setDisplayHomeAsUpEnabled(true);
        setView();
        setOnDefaultSwitchListeners();
    }


    private void setOnDefaultSwitchListeners() {
        notificationModeSwitch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RefrigeratorState.setNotifyModeClass(notificationModeSwitch.isChecked());

            }
        });
        notificationFreezerTemperatureSwitch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RefrigeratorState.setNotifyFreezerTemperatureClass(notificationFreezerTemperatureSwitch.isChecked());

            }
        });
        notificationTemperatureSwitch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RefrigeratorState.setNotifyTemperatureClass(notificationTemperatureSwitch.isChecked());

            }
        });
    }

    private void setView() {

        notificationModeSwitch = findViewById(R.id.activityRefriSettings_notificationModeSwitch);
        notificationFreezerTemperatureSwitch = findViewById(R.id.activityRefriSettings_notificationsFreezerTempSwitch);
        notificationTemperatureSwitch = findViewById(R.id.activityRefriSettings_notificationTemperatureSwitch);

        notificationTemperatureSwitch.setChecked(RefrigeratorState.isNotifyTemperatureClass());
        notificationFreezerTemperatureSwitch.setChecked(RefrigeratorState.isNotifyFreezerTemperatureClass());
        notificationModeSwitch.setChecked(RefrigeratorState.isNotifyModeClass());

    }

}
