package com.grupo1.hci.smarthome.Activities;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.grupo1.hci.smarthome.Model.Constants;
import com.grupo1.hci.smarthome.Model.Device;
import com.grupo1.hci.smarthome.R;

public class DeviceActivity extends AppCompatActivity {
    Device device;
    FragmentTransaction fragmentTransaction;
    Fragment fragment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_device);

        device = (Device) getIntent().getSerializableExtra(Constants.DEVICE_INTENT);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Get a support ActionBar corresponding to this toolbar
        ActionBar ab = getSupportActionBar();
        // Enable the Up button
        ab.setDisplayHomeAsUpEnabled(true);

        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();
        setFragment();
    }

    private void setFragment() {
        Fragment newFragment;
        switch (device.getTypeId()){
            case Constants.BLIND_ID:
                newFragment = new BlindFragment();
                fragmentTransaction.add(R.id.deviceActivity_Fragmentcontainer, newFragment);
                fragmentTransaction.commit();
                break;
            case Constants.LAMP_ID:
                newFragment = new LampFragment();
                fragmentTransaction.add(R.id.deviceActivity_Fragmentcontainer, newFragment);
                fragmentTransaction.commit();
                break;
            case Constants.OVEN_ID:
                newFragment = new OvenFragment();
                fragmentTransaction.add(R.id.deviceActivity_Fragmentcontainer, newFragment);
                fragmentTransaction.commit();
                break;
            case Constants.DOOR_ID:
                newFragment = new DoorFragment();
                fragmentTransaction.add(R.id.deviceActivity_Fragmentcontainer, newFragment);
                fragmentTransaction.commit();
                break;
        }
    }

    public Device getDevice() {
        return device;
    }

    @Override
    public boolean onPrepareOptionsMenu(final Menu menu) {
        getMenuInflater().inflate(R.menu.one_item_contextual_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.deleteElement:
                DeleteDialogMessage deleteConfirmationFragment = new DeleteDialogMessage();
                deleteConfirmationFragment.setDevice(device);
                deleteConfirmationFragment.setActivity(this);
                deleteConfirmationFragment.show(getFragmentManager(), "deleteConfirmation");
                return true;

            case R.id.editElement:
               if(device.getTypeId().equals(Constants.LAMP_ID)){
                   Intent intent = new Intent(this, LampSettingsActivity.class);
                   startActivity(intent);
               }
                if(device.getTypeId().equals(Constants.BLIND_ID)){
                    Intent intent = new Intent(this, BlindSettingsActivity.class);
                    startActivity(intent);
                }
                if(device.getTypeId().equals(Constants.OVEN_ID)){
                    Intent intent = new Intent(this, OvenSettingsActivity.class);
                    startActivity(intent);
                }
                if(device.getTypeId().equals(Constants.DOOR_ID)){
                    Intent intent = new Intent(this, DoorSettingsActivity.class);
                    startActivity(intent);
                }
                return true;

            default:
                // If we got here, the user's action was not recognized.
                // Invoke the superclass to handle it.
                return super.onOptionsItemSelected(item);

        }
    }

    public void deviceDeleted() {
        Intent returnIntent = new Intent();
        returnIntent.putExtra(Constants.DEVICE_INTENT,device);
        setResult(this.RESULT_OK,returnIntent);
        finish();
    }
}