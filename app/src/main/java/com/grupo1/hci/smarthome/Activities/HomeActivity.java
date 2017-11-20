package com.grupo1.hci.smarthome.Activities;

//import com.grupo1.hci.smarthome.Notifications.ApiService;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.Toolbar;
import android.view.ActionMode;
import android.view.MenuInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.widget.Toast;


import com.android.volley.AuthFailureError;
import com.android.volley.Cache;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Network;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.BasicNetwork;
import com.android.volley.toolbox.DiskBasedCache;
import com.android.volley.toolbox.HurlStack;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
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


    FragmentTransaction fragmentTransaction;
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
            apiManager = APIManager.getInstance(this);
            apiManager.getRooms(this);
        }
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();
        setFragment();
       getSupportActionBar().setTitle(Constants.appName);

//        Intent serviceIntent = new Intent(getApplicationContext() , ApiService.class );
//        startService(serviceIntent);

    }

    private void setFragment() {
        if(getResources().getConfiguration().orientation ==  getResources().getConfiguration().ORIENTATION_PORTRAIT) {
            //fragmentTransaction.remove(fragment);
            fragment = new HomeListFragment();
            fragmentTransaction.add(R.id.homeActivity_Fragmentcontainer, fragment);
            fragmentTransaction.commit();
        }else{
            Toast.makeText(getApplicationContext(), "CAMBIO", Toast.LENGTH_SHORT).show();
           // fragmentTransaction.remove(fragment);
            fragment = new HomeTileFragment();
            fragmentTransaction.add(R.id.homeActivity_Fragmentcontainer, fragment);
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
        super.onRestoreInstanceState(savedInstanceState);
    }
}
