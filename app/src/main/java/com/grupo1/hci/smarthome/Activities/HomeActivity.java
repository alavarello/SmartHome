package com.grupo1.hci.smarthome.Activities;

import com.grupo1.hci.smarthome.Notifications.ApiService;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.widget.Toast;


import com.grupo1.hci.smarthome.Model.APIManager;
import com.grupo1.hci.smarthome.Model.Constants;
import com.grupo1.hci.smarthome.Model.Room;
import com.grupo1.hci.smarthome.R;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.Serializable;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HomeActivity extends NavigationActivity {


    Fragment fragment;
    APIManager apiManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Constants.context = getApplicationContext();
        Constants.setAppName();
        if(savedInstanceState != null){
            roomsArray = (ArrayList<Room>) savedInstanceState.getSerializable(Constants.ROOM_ARRAY_INTENT);
        }else{
//            apiManager = APIManager.getInstance(this);
//            apiManager.getRooms(this);
        }
        setFragment();
       getSupportActionBar().setTitle(Constants.appName);

      // Intent serviceIntent = new Intent(getApplicationContext() , ApiService.class );startService(serviceIntent);

    }

    private void setFragment() {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        if(getResources().getConfiguration().orientation ==  getResources().getConfiguration().ORIENTATION_PORTRAIT) {
            fragment = new HomeListFragment();
            fragmentTransaction.replace(R.id.homeActivity_Fragmentcontainer, fragment);
            fragmentTransaction.commit();
        }else{
            Toast.makeText(getApplicationContext(), "CAMBIO", Toast.LENGTH_SHORT).show();
            fragment = new HomeTileFragment();
            fragmentTransaction.replace(R.id.homeActivity_Fragmentcontainer, fragment);
            fragmentTransaction.commit();
        }
    }

    public Fragment getFragment() {
        return fragment;
    }

    public void loadRooms(List<Room> rooms){
        ((HomeFragment)fragment).loadRomms(rooms);
    };

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.clear();
        outState.putSerializable(Constants.ROOM_ARRAY_INTENT, roomsArray);
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        roomsArray = (ArrayList<Room>) savedInstanceState.getSerializable(Constants.ROOM_ARRAY_INTENT);
        ((HomeFragment)fragment).loadRomms(roomsArray);
        setMenu();
        super.onRestoreInstanceState(savedInstanceState);
    }
}
