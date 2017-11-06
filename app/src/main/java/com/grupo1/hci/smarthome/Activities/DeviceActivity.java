package com.grupo1.hci.smarthome.Activities;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.grupo1.hci.smarthome.Model.Blind;
import com.grupo1.hci.smarthome.Model.Constants;
import com.grupo1.hci.smarthome.Model.Device;
import com.grupo1.hci.smarthome.R;

public class DeviceActivity extends AppCompatActivity {

    Device device;
   FragmentTransaction fragmentTransaction;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_blind);

        device = (Device) getIntent().getSerializableExtra(Constants.DEVICE_INTENT);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Get a support ActionBar corresponding to this toolbar
        ActionBar ab = getSupportActionBar();
        // Enable the Up button
        ab.setDisplayHomeAsUpEnabled(true);

        FragmentManager fragmentManager = getFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();
        setFragment();
    }

    private void setFragment() {
        Fragment newFragment;
        switch (device.getTypeId()){
            case Constants.BLIND_ID:
                newFragment = new BlindFragment();
                fragmentTransaction.add(R.id.deviceFragment, newFragment);
                fragmentTransaction.commit();
                break;
            case Constants.LAMP_ID:
                newFragment = new BlindFragment();
                fragmentTransaction.add(R.id.deviceFragment, newFragment);
                fragmentTransaction.commit();
                break;
            case Constants.OVEN_ID:
                newFragment = new BlindFragment();
                fragmentTransaction.add(R.id.deviceFragment, newFragment);
                fragmentTransaction.commit();
                break;
            case Constants.DOOR_ID:
                newFragment = new BlindFragment();
                fragmentTransaction.add(R.id.deviceFragment, newFragment);
                fragmentTransaction.commit();
                break;
        }
    }

    public Device getDevice() {
        return device;
    }

    @Override
    public boolean onPrepareOptionsMenu(final Menu menu) {
        getMenuInflater().inflate(R.menu.contextual_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.deleteElement:
                DeleteDialogMessage deleteConfirmationFragment = new DeleteDialogMessage();
                deleteConfirmationFragment.setRoomId("id");
                deleteConfirmationFragment.show(getFragmentManager(), "deleteConfirmation");
                return true;

            case R.id.editElement:
                Toast.makeText(getApplicationContext(), "click", Toast.LENGTH_SHORT).show();
                return true;

            default:
                // If we got here, the user's action was not recognized.
                // Invoke the superclass to handle it.
                return super.onOptionsItemSelected(item);

        }
    }

}
