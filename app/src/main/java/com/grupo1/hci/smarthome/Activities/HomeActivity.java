package com.grupo1.hci.smarthome.Activities;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.widget.Toast;

import com.grupo1.hci.smarthome.Model.Constants;
import com.grupo1.hci.smarthome.Model.Room;
import com.grupo1.hci.smarthome.R;

import java.util.ArrayList;

public class HomeActivity extends NavigationActivity {

    FragmentTransaction fragmentTransaction;
    Fragment fragment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(savedInstanceState != null){
            roomsArray = (ArrayList<Room>) savedInstanceState.getSerializable(Constants.ROOM_ARRAY_INTENT);
        }
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();
        setFragment();

    }

    private void setFragment() {
        if(getResources().getConfiguration().orientation ==  getResources().getConfiguration().ORIENTATION_PORTRAIT) {
            fragment = new HomeListFragment();
            fragmentTransaction.add(R.id.homeActivity_Fragmentcontainer, fragment);
            fragmentTransaction.commit();
        }else{
            Toast.makeText(getApplicationContext(), "CAMBIO", Toast.LENGTH_SHORT).show();
            fragment = new HomeTileFragment();
            fragmentTransaction.add(R.id.homeActivity_Fragmentcontainer, fragment);
            fragmentTransaction.commit();
        }
    }

    public Fragment getFragment() {
        return fragment;
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.clear();
        outState.putSerializable(Constants.ROOM_ARRAY_INTENT, roomsArray);
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        roomsArray = (ArrayList<Room>) savedInstanceState.getSerializable(Constants.ROOM_ARRAY_INTENT);
        super.onRestoreInstanceState(savedInstanceState);
    }
}
