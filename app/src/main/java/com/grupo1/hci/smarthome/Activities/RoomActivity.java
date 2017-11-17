package com.grupo1.hci.smarthome.Activities;

import android.content.Intent;
import android.graphics.Color;
import android.os.CountDownTimer;
import android.support.design.widget.Snackbar;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.ActionMode;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.grupo1.hci.smarthome.Model.APIManager;
import com.grupo1.hci.smarthome.Model.Constants;
import com.grupo1.hci.smarthome.Model.Device;
import com.grupo1.hci.smarthome.Model.Room;
import com.grupo1.hci.smarthome.R;

import java.io.Serializable;
import java.util.ArrayList;

public class RoomActivity extends NavigationActivity {

    // Array of strings...
    Room room;
    Fragment fragment;
    FragmentTransaction fragmentTransaction;
    public Room getRoom() {
        return room;
    }

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
        }
        //Set toolbar content
        getSupportActionBar().setTitle(room.getName());

        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();
        setFragment();

    }

    private void setFragment() {
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
        super.onRestoreInstanceState(savedInstanceState);
    }



}

