package com.grupo1.hci.smarthome.Activities;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Switch;

import com.grupo1.hci.smarthome.Model.Constants;
import com.grupo1.hci.smarthome.Model.Lamp;
import com.grupo1.hci.smarthome.Notifications.ApiService;
import com.grupo1.hci.smarthome.Notifications.DeviceState;
import com.grupo1.hci.smarthome.Notifications.LampState;
import com.grupo1.hci.smarthome.R;

public class LampSettingsActivity extends AppCompatActivity {

    Switch notificationColorSwitch;
    Switch notificationOnSwitch;
    Switch notificationOffSwitch;
    Switch notificationDimmerSwitch;
    Lamp lamp;
    LampState lampState;
    DeviceState s ;
    boolean isDefault = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lamp_settings);
        lamp = (Lamp) getIntent().getSerializableExtra(Constants.DEVICE_INTENT);
        if(lamp == null){
            isDefault = true;
        }else{
            s = ApiService.getDevice(lamp.getId());
        }
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        // Get a support ActionBar corresponding to this toolbar
        ActionBar ab = getSupportActionBar();
        // Enable the Up button
        ab.setDisplayHomeAsUpEnabled(true);
        setView();
        if(isDefault){
            setOnDefaultSwitchListeners();
        }else{

        }
    }


    private void setOnDefaultSwitchListeners() {
        notificationOnSwitch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LampState.setNotifyTurnOnClass(notificationOnSwitch.isChecked());

            }
        });
        notificationOffSwitch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LampState.setNotifyTurnOffClass(notificationOffSwitch.isChecked());

            }
        });
        notificationColorSwitch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LampState.setNotifyChangeColorClass(notificationColorSwitch.isChecked());

            }
        });
        notificationDimmerSwitch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LampState.setNotifyChangeBrigtnessClass(notificationDimmerSwitch.isChecked());

            }
        });
    }

    private void setView() {

        notificationColorSwitch = findViewById(R.id.activityLampSettings_notificationColorSwitch);
        notificationDimmerSwitch = findViewById(R.id.activityLampSettings_notificationGrillSwitch);
        notificationOffSwitch = findViewById(R.id.activityLampSettings_notificationOffSwitch);
        notificationOnSwitch = findViewById(R.id.activityLampSettings_notificationOnSwitch);

        notificationOnSwitch.setChecked(LampState.isNotifyTurnOnClass());
        notificationOffSwitch.setChecked(LampState.isNotifyTurnOffClass());
        notificationDimmerSwitch.setChecked(LampState.isNotifyChangeBrigtnessClass());
        notificationColorSwitch.setChecked(LampState.isNotifyChangeColorClass());

    }

}
