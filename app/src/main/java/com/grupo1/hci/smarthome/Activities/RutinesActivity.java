package com.grupo1.hci.smarthome.Activities;

import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.design.widget.Snackbar;
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
import com.grupo1.hci.smarthome.Model.Device;
import com.grupo1.hci.smarthome.Model.Room;
import com.grupo1.hci.smarthome.Model.Rutine;
import com.grupo1.hci.smarthome.R;

import java.util.ArrayList;

public class RutinesActivity extends NavigationActivity {

    // Array of strings...
    Fragment fragment;
    FragmentTransaction fragmentTransaction;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getSupportActionBar().setTitle("Rutines");
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();
        setFragment();
        setMenuWithApiCall();

    }

    private void setFragment() {
        fragment = new RutinesListFragment();
        fragmentTransaction.add(R.id.homeActivity_Fragmentcontainer, fragment);
        fragmentTransaction.commit();
    }

    public Fragment getFragment() {
        return fragment;
    }


    public void loadRutines(ArrayList<Rutine> rutineList) {
        ((RutinesListFragment)fragment).loadRutines(rutineList);
    }

    public ArrayList<View> getSelectedElements(){
        return ((RutinesListFragment)fragment).getSelectedElement();
    }
}

