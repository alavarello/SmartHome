package com.grupo1.hci.smarthome.Activities;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Switch;

import com.grupo1.hci.smarthome.Model.Blind;
import com.grupo1.hci.smarthome.Model.Constants;
import com.grupo1.hci.smarthome.Notifications.BlindState;
import com.grupo1.hci.smarthome.R;

public class BlindSettingsActivity extends AppCompatActivity {

    Switch notificationCloseSwitch;
    Switch notificationOpenSwitch;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_blind_settings);
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
        notificationOpenSwitch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                BlindState.setNotifyOpenClass(notificationOpenSwitch.isChecked());
            }
        });
        notificationCloseSwitch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                BlindState.setNotifyCloseClass(notificationCloseSwitch.isChecked());
            }
        });

    }

    private void setView() {
        notificationCloseSwitch = findViewById(R.id.activityBlindSettings_notificationCloseSwitch);
        notificationOpenSwitch = findViewById(R.id.activityBlindSettings_notificationOpenSwitch);

        notificationCloseSwitch.setChecked(BlindState.isNotifyCloseClass());
        notificationOpenSwitch.setChecked(BlindState.isNotifyOpenClass());

    }

}
