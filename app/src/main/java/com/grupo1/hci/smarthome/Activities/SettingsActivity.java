package com.grupo1.hci.smarthome.Activities;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.grupo1.hci.smarthome.Model.Blind;
import com.grupo1.hci.smarthome.Model.Constants;
import com.grupo1.hci.smarthome.R;

import java.io.Serializable;
import java.util.ArrayList;

public class SettingsActivity extends AppCompatActivity {

    EditText appNameEditText;
    Spinner timeNotificationSpinner;
    ListView defaultNotificationView;
    String lamp;
    String oven;
    String door;
    String blind;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        setView();
        setOnClickListener();

    }

    private void setOnClickListener() {
        appNameEditText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                String text = textView.getText().toString().trim();
                if(!text.equals("")){
                    Constants.appName =text;
                }
                else{
                    textView.setError(getResources().getString(R.string.blackErrorMessage));
                }
                return false;
            }
        });

    }

    private void setView() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Get a support ActionBar corresponding to this toolbar
        ActionBar ab = getSupportActionBar();
        // Enable the Up button
        ab.setDisplayHomeAsUpEnabled(true);

        //--------------
        appNameEditText = findViewById(R.id.settingsContent_AppName_editText);
        appNameEditText.setText(Constants.appName);
        timeNotificationSpinner = findViewById(R.id.contentSettings_timeNotificationSpinner);
        defaultNotificationView = findViewById(R.id.contentSettings_defaultSettingsListView);
        setTimeSpinner();
        setDefaultSettingsButton();

    }

    private void setDefaultSettingsButton() {
        Resources res = getResources();
        oven = res.getString(R.string.oven);
        lamp = res.getString(R.string.lamp);
        door = res.getString(R.string.door);
        blind = res.getString(R.string.blind);

        String[] list = getResources().getStringArray(R.array.defaultDeviceNotification_array);
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item,list );
        defaultNotificationView.setAdapter(dataAdapter);
        defaultNotificationView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String s = adapterView.getItemAtPosition(i).toString();
                if(s.equals(oven)){
                    Intent intent = new Intent(SettingsActivity.this, OvenSettingsActivity.class);
                    startActivity(intent);
                }else if(s.equals(lamp)){
                    Intent intent = new Intent(SettingsActivity.this, LampSettingsActivity.class);
                    startActivity(intent);
                }else if(s.equals(door)){
                    Intent intent = new Intent(SettingsActivity.this, DoorSettingsActivity.class);
                    startActivity(intent);
                }else if(s.equals(blind)){
                    Intent intent = new Intent(SettingsActivity.this, BlindSettingsActivity.class);
                    startActivity(intent);
                }

            }
        });
    }

    private void setTimeSpinner() {

        String[] list = getResources().getStringArray(R.array.timeNotification_array);
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item,list );
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        timeNotificationSpinner.setAdapter(dataAdapter);
        timeNotificationSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                Toast.makeText(getApplicationContext(),adapterView.getSelectedItem().toString(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }


    @Override
    public boolean onPrepareOptionsMenu(final Menu menu) {
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
                return super.onOptionsItemSelected(item);
    }

}
