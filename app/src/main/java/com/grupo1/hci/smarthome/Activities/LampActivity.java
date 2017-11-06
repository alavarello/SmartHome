package com.grupo1.hci.smarthome.Activities;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.SeekBar;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.grupo1.hci.smarthome.Model.Constants;
import com.grupo1.hci.smarthome.Model.Lamp;
import com.grupo1.hci.smarthome.R;

public class LampActivity extends AppCompatActivity {

    Lamp lamp;
    ToggleButton onOffToggleButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lamp);


        setView();
        setOnClickListener();
        setLampState();

    }

    private void setOnClickListener() {
        onOffToggleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(onOffToggleButton.isChecked()){
                    Toast.makeText(getApplicationContext(), "OFF", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(getApplicationContext(), "ON", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void setView() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        lamp = (Lamp) getIntent().getSerializableExtra(Constants.DEVICE_INTENT);
        getSupportActionBar().setTitle(lamp.getName());

        // Get a support ActionBar corresponding to this toolbar
        ActionBar ab = getSupportActionBar();
        // Enable the Up button
        ab.setDisplayHomeAsUpEnabled(true);

        onOffToggleButton = findViewById(R.id.contentLamp_OnOff_toogleButton);
        onOffToggleButton.setTextOff(getString(R.string.off));
        onOffToggleButton.setTextOn(getString(R.string.on));
    }

    private void setLampState() {
        if(lamp.isOn()){
            onOffToggleButton.setChecked(true);
        }else{
            onOffToggleButton.setChecked(false);
        }

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
