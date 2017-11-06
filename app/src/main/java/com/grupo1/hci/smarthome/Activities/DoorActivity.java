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
import android.widget.Toast;
import android.widget.ToggleButton;

import com.grupo1.hci.smarthome.Model.Blind;
import com.grupo1.hci.smarthome.Model.Constants;
import com.grupo1.hci.smarthome.Model.Door;
import com.grupo1.hci.smarthome.R;

public class DoorActivity extends AppCompatActivity {

    Door door;
    ToggleButton openCloseToggeleButton;
    ToggleButton lockUnlockToggleButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_door);
        setView();
        setOnClickListener();
        setDoorState();
    }

    private void setOnClickListener() {
        openCloseToggeleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(openCloseToggeleButton.isChecked()){
                    Toast.makeText(getApplicationContext(), "CLOSE", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(getApplicationContext(), "OPEN", Toast.LENGTH_SHORT).show();
                }
            }
        });
        lockUnlockToggleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(openCloseToggeleButton.isChecked()){
                    Toast.makeText(getApplicationContext(), "LOCK", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(getApplicationContext(), "UNLOCK", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void setView() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        door = (Door) getIntent().getSerializableExtra(Constants.DEVICE_INTENT);
        getSupportActionBar().setTitle(door.getName());

        // Get a support ActionBar corresponding to this toolbar
        ActionBar ab = getSupportActionBar();
        // Enable the Up button
        ab.setDisplayHomeAsUpEnabled(true);

        openCloseToggeleButton = findViewById(R.id.contentDoor_OpenClose_ToogleButton);
        openCloseToggeleButton.setTextOff(getString(R.string.open));
        openCloseToggeleButton.setTextOn(getString(R.string.close));
        lockUnlockToggleButton = findViewById(R.id.contentDoor_LockUnlock_ToggleButton);
        lockUnlockToggleButton.setTextOff(getString(R.string.unlock));
        lockUnlockToggleButton.setTextOn(getString(R.string.lock));

    }

    private void setDoorState() {
        if(door.isClosed()){
            openCloseToggeleButton.setChecked(true);
        }else{
            openCloseToggeleButton.setChecked(false);
        }
        if(door.isLocked()){
            lockUnlockToggleButton.setChecked(true);
        }else{
            lockUnlockToggleButton.setChecked(false);
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
