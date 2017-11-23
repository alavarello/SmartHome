package com.grupo1.hci.smarthome.Activities;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.grupo1.hci.smarthome.Model.APIManager;
import com.grupo1.hci.smarthome.Model.Blind;
import com.grupo1.hci.smarthome.Model.Constants;
import com.grupo1.hci.smarthome.Model.Room;
import com.grupo1.hci.smarthome.R;

import java.io.Serializable;
import java.util.ArrayList;

public class SettingsActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    EditText appNameEditText;
    Spinner timeNotificationSpinner;
    ListView defaultNotificationView;
    String lamp;
    String oven;
    String door;
    String blind;
    Toolbar toolbar;
    NavigationView navigationView;
    DrawerLayout drawer;
    ArrayList<Room> roomsArray = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        setNavView();
        setView();
        setOnClickListener();

    }

    public void setRoomsArray(ArrayList<Room> roomsArray){
        this.roomsArray = roomsArray;
    }

    private void setNavView() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        drawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);
        setMenuWithApiCall();
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
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item,list ) {
            @NonNull
            @Override
            public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
                View view = super.getView(position,convertView,parent);
                ViewGroup.LayoutParams params = view.getLayoutParams();
                params.height = 200;
                view.setLayoutParams(params);
                return view;
            }
        };
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

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if(id == R.id.settings){
            Intent intent = new Intent(getApplicationContext(), SettingsActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivity(intent);
        }else if(id == R.id.rutines){
            Intent intent = new Intent(getApplicationContext(), RutinesActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivity(intent);
        }else{
            if(!getClass().equals(RoomActivity.class)){
                Intent intent = new Intent(getApplicationContext(), RoomActivity.class);
                intent.putExtra(Constants.ROOM_INTENT, (Serializable) roomsArray.get(id));
                intent.putExtra(Constants.ROOM_ARRAY_INTENT, (Serializable) roomsArray);
                startActivity(intent);
            }
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void setMenuWithApiCall(){
        APIManager.getInstance(this).getRoomsForMenu(this,  navigationView.getMenu());
    }
}
