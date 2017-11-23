package com.grupo1.hci.smarthome.Activities;

import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Color;
import android.os.CountDownTimer;
import android.support.design.widget.Snackbar;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.DisplayMetrics;
import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.grupo1.hci.smarthome.Model.APIManager;
import com.grupo1.hci.smarthome.Model.Constants;
import com.grupo1.hci.smarthome.Model.Device;
import com.grupo1.hci.smarthome.Model.Room;
import com.grupo1.hci.smarthome.R;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class RoomActivity extends NavigationActivity implements SuportDeviceActivity{

    // Array of strings...
    Room room;
    Fragment fragment;
    FragmentTransaction fragmentTransaction;
    APIManager apiManager;
    public Room getRoom() {
        return room;
    }
    Device device;
    Menu menu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // recovering the instance state
        if (savedInstanceState != null) {
            room = (Room) savedInstanceState.getSerializable(Constants.ROOM_INTENT);
            roomsArray = (ArrayList<Room>) savedInstanceState.getSerializable(Constants.ROOM_ARRAY_INTENT);
        }else{
            room = (Room) getIntent().getSerializableExtra(Constants.ROOM_INTENT);
            roomsArray = (ArrayList<Room>) getIntent().getSerializableExtra(Constants.ROOM_ARRAY_INTENT);
            apiManager = APIManager.getInstance(this);
            apiManager.getDevicesForRoom(room.getId(), this);
        }
        setMenu();
        if(((getResources().getConfiguration().screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK) == Configuration.SCREENLAYOUT_SIZE_XLARGE)){
            setViewForXLARGE();
        }
        //Set toolbar content
        getSupportActionBar().setTitle(room.getName());
        setFragment();

    }

    private void setViewForXLARGE() {
        LinearLayout listFragment = findViewById(R.id.homeActivity_Fragmentcontainer);
        LinearLayout deviceFragment = findViewById(R.id.homeActivity_FragmentDevicecontainer);
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int height = displayMetrics.heightPixels;
        int width = displayMetrics.widthPixels;
        listFragment.getLayoutParams().width = (int)(width*0.5);
        deviceFragment.getLayoutParams().width = (int)(width*0.5);

    }

    private void setFragment() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();
        fragment = new RoomListFragment();
        fragmentTransaction.add(R.id.homeActivity_Fragmentcontainer, fragment);
        fragmentTransaction.commit();

    }

    public Fragment getFragment() {
        return fragment;
    }


    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.clear();
        outState.putSerializable(Constants.ROOM_INTENT, room);
        outState.putSerializable(Constants.ROOM_ARRAY_INTENT, roomsArray);
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        room = (Room) savedInstanceState.getSerializable(Constants.ROOM_INTENT);
        roomsArray = (ArrayList<Room>) savedInstanceState.getSerializable(Constants.ROOM_ARRAY_INTENT);
        setMenu();
        super.onRestoreInstanceState(savedInstanceState);
    }

    public void loadDevices(List<Device> devices){
        ((RoomsFragment)fragment).loadDevices(devices);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if(((getResources().getConfiguration().screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK) == Configuration.SCREENLAYOUT_SIZE_XLARGE)){
            MenuInflater inflater = getMenuInflater();
            inflater.inflate(R.menu.one_item_contextual_menu, menu);
            this.menu = menu;
            menu.getItem(0).setVisible(false);
            menu.getItem(1).setVisible(false);
            return true;
        }else{
            return super.onCreateOptionsMenu(menu);
        }
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
                    intent.putExtra(Constants.DEVICE_INTENT, device);
                    startActivity(intent);
                }
                if(device.getTypeId().equals(Constants.BLIND_ID)){
                    Intent intent = new Intent(this, BlindSettingsActivity.class);
                    intent.putExtra(Constants.DEVICE_INTENT, device);
                    startActivity(intent);
                }
                if(device.getTypeId().equals(Constants.OVEN_ID)){
                    Intent intent = new Intent(this, OvenSettingsActivity.class);
                    intent.putExtra(Constants.DEVICE_INTENT, device);
                    startActivity(intent);
                }
                if(device.getTypeId().equals(Constants.DOOR_ID)){
                    Intent intent = new Intent(this, DoorSettingsActivity.class);
                    intent.putExtra(Constants.DEVICE_INTENT, device);
                    startActivity(intent);
                }
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public ArrayList<View> getSelectedElements(){
        return ((RoomsFragment)fragment).getSelectedElements();
    }

    public void changeDeviceFragment(Device device) {
        this.device = device;
        Fragment newFragment = null;
        switch (device.getTypeId()){
            case Constants.BLIND_ID:
                newFragment = new BlindFragment();
                break;
            case Constants.LAMP_ID:
                newFragment = new LampFragment();
                break;
            case Constants.OVEN_ID:
                newFragment = new OvenFragment();
                break;
            case Constants.DOOR_ID:
                newFragment = new DoorFragment();
                break;
            case Constants.REFRIGERATOR_ID:
                newFragment = new RefrigeratorFragment();
                break;
        }
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.homeActivity_FragmentDevicecontainer, newFragment);
        fragmentTransaction.commit();
        menu.getItem(0).setVisible(true);
        menu.getItem(1).setVisible(true);

    }

    @Override
    public Device getDevice() {
        return device;
    }

    @Override
    public void deviceDeleted() {
        ((RoomsFragment)fragment).deleteDevice(device);
    }
}

