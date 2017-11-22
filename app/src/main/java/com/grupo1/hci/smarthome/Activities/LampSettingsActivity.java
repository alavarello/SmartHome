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
                if(notificationOnSwitch.isChecked()){
                    LampState.setNotifyTurnOnClass(true);
                }else{
                    LampState.setNotifyTurnOnClass(false);
                }
            }
        });
        notificationOffSwitch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(notificationOffSwitch.isChecked()){
                    LampState.setNotifyTurnOffClass(true);
                }else{
                    LampState.setNotifyTurnOffClass(false);
                }
            }
        });
        notificationColorSwitch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(notificationColorSwitch.isChecked()){
                    LampState.setNotifyChangeColorClass(true);
                }else{
                    LampState.setNotifyChangeColorClass(false);
                }
            }
        });
        notificationDimmerSwitch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(notificationDimmerSwitch.isChecked()){
                    LampState.setNotifyChangeBrigtnessClass(true);
                }else{
                    LampState.setNotifyChangeBrigtnessClass(false);
                }
            }
        });
    }

    private void setView() {

        notificationColorSwitch = findViewById(R.id.activityLampSettings_notificationColorSwitch);
        notificationDimmerSwitch = findViewById(R.id.activityLampSettings_notificationGrillSwitch);
        notificationOffSwitch = findViewById(R.id.activityLampSettings_notificationOffSwitch);
        notificationOnSwitch = findViewById(R.id.activityLampSettings_notificationOnSwitch);

        if(isDefault){
            setDefalutChecks();
        }else{

        }

    }



    private void setDefalutChecks() {
        if(LampState.isNotifyTurnOnClass()){
            notificationOnSwitch.setChecked(true);
        }else{
            notificationOnSwitch.setChecked(false);
        }
        if(LampState.isNotifyTurnOffClass()){
            notificationOffSwitch.setChecked(true);
        }else{
            notificationOffSwitch.setChecked(false);
        }
        if(LampState.isNotifyChangeBrigtnessClass()){
            notificationDimmerSwitch.setChecked(true);
        }else{
            notificationDimmerSwitch.setChecked(false);
        }
        if(LampState.isNotifyChangeColorClass()){
            notificationColorSwitch.setChecked(true);
        }else{
            notificationColorSwitch.setChecked(false);
        }
    }
}
