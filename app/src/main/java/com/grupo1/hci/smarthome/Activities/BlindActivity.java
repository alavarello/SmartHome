package com.grupo1.hci.smarthome.Activities;

import android.app.DialogFragment;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
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
import com.grupo1.hci.smarthome.Model.Lamp;
import com.grupo1.hci.smarthome.R;

public class BlindActivity extends AppCompatActivity {

    Blind blind;
    ToggleButton openCloseToggleButton;
    TextView stateTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_blind);

        setView();
        setOnClickListener();
        loadBlindState();
    }

    private void setOnClickListener() {
        openCloseToggleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ToggleButton onOffToogleButton = (ToggleButton) view;
                if(onOffToogleButton.isChecked()){
                    Toast.makeText(getApplicationContext(), "CERRADO", Toast.LENGTH_SHORT).show();
                    stateTextView.setText(Constants.BLIND_STATE_CLOSING);
                }else{
                    Toast.makeText(getApplicationContext(), "ABIERTO", Toast.LENGTH_SHORT).show();
                    stateTextView.setText(Constants.BLIND_STATE_OPENING);
                }
            }
        });

    }

    private void setView() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        blind = (Blind) getIntent().getSerializableExtra(Constants.DEVICE_INTENT);
        getSupportActionBar().setTitle(blind.getName());

        // Get a support ActionBar corresponding to this toolbar
        ActionBar ab = getSupportActionBar();
        // Enable the Up button
        ab.setDisplayHomeAsUpEnabled(true);

        openCloseToggleButton = findViewById(R.id.contentBlind_OnOff_ToggleButton);
        openCloseToggleButton.setTextOff(getString(R.string.close));
        openCloseToggleButton.setTextOn(getString(R.string.open));
        stateTextView = findViewById(R.id.contentBlind_State_TextView);
    }

    private void loadBlindState(){
        String status = blind.getStatus();
        if(status.equals(Constants.BLIND_STATE_OPENED)){
            openCloseToggleButton.setChecked(true);
            stateTextView.setText(R.string.opened);
        }else if(status.equals(Constants.BLIND_STATE_CLOSED)){
            openCloseToggleButton.setChecked(false);
            stateTextView.setText(R.string.closed);
        }else if(status.equals(Constants.BLIND_STATE_CLOSING)){
            openCloseToggleButton.setChecked(false);
            stateTextView.setText(R.string.closing);
        }else if(status.equals(Constants.BLIND_STATE_OPENING)){
            openCloseToggleButton.setChecked(true);
            stateTextView.setText(R.string.opening);
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
