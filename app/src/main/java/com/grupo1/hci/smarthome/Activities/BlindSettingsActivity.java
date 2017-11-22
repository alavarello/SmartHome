package com.grupo1.hci.smarthome.Activities;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.Switch;

import com.grupo1.hci.smarthome.Model.Blind;
import com.grupo1.hci.smarthome.Model.Constants;
import com.grupo1.hci.smarthome.Notifications.BlindState;
import com.grupo1.hci.smarthome.R;

public class BlindSettingsActivity extends AppCompatActivity {

    Switch notificationCloseSwitch;
    Switch notificationOpenSwitch;
    Blind blind;
    BlindState blindState;
    boolean isDefault = false;


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
        blind = (Blind) getIntent().getSerializableExtra(Constants.DEVICE_INTENT);
        if(blind == null){
            isDefault = true;
            //TODO blindState
        }
        setView();
        setOnSwitchListeners();
    }

    private void setOnSwitchListeners() {


    }

    private void setView() {
        notificationCloseSwitch = findViewById(R.id.activityBlindSettings_notificationCloseSwitch);
        notificationOpenSwitch = findViewById(R.id.activityBlindSettings_notificationOpenSwitch);

        if(isDefault){
            setDefaultState();
        }else{
            setPersonalizeState();
        }
    }

    private void setPersonalizeState() {
       //if(blindState.)
    }

    private void setDefaultState() {

    }
}
