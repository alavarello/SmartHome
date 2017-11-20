package com.grupo1.hci.smarthome.Activities;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuItem;
import android.view.SubMenu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.grupo1.hci.smarthome.Model.APIManager;
import com.grupo1.hci.smarthome.Model.Blind;
import com.grupo1.hci.smarthome.Model.Constants;
import com.grupo1.hci.smarthome.Model.Device;
import com.grupo1.hci.smarthome.Model.Door;
import com.grupo1.hci.smarthome.Model.Lamp;
import com.grupo1.hci.smarthome.Model.Oven;
import com.grupo1.hci.smarthome.Model.Room;
import com.grupo1.hci.smarthome.R;

import java.io.Serializable;
import java.util.ArrayList;

public class NavigationActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    ArrayList<Room> roomsArray = new ArrayList<>();
    Toolbar toolbar;
    NavigationView navigationView;
    DrawerLayout drawer;
    APIManager apiManager;

    public Toolbar getToolbar() {
        return toolbar;
    }

    public ArrayList<Room> getRoomsArray() {
        return roomsArray;
    }

    public void setRoomsArray(ArrayList<Room> roomsArray){
        this.roomsArray = roomsArray;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation);
        apiManager = APIManager.getInstance(this);
        setView();
        setMenu();

    }



    private void setView() {
        //Set toolbar content
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


       drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

    }

    private void setMenu() {
        //Set up the nav menu for the rooms
        SubMenu m = navigationView.getMenu().addSubMenu("Cuartos");
        int i =0;
        for (Room r : roomsArray) {
            m.add(R.id.roomGroupNavigationalDrawer, i++ ,500, r.getName());
        }

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
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();
        if(id == R.id.settings){
            Intent intent = new Intent(NavigationActivity.this.getApplicationContext(), SettingsActivity.class);
            intent.setFlags(intent.getFlags() | Intent.FLAG_ACTIVITY_NO_HISTORY);
            startActivity(intent);
        }else if(id == R.id.rutines){
            Intent intent = new Intent(NavigationActivity.this.getApplicationContext(), RutinesActivity.class);
            intent.setFlags(intent.getFlags() | Intent.FLAG_ACTIVITY_NO_HISTORY);
            startActivity(intent);
        }else{
            if(!getClass().equals(RoomActivity.class)){
                Intent intent = new Intent(NavigationActivity.this.getApplicationContext(), RoomActivity.class);
                intent.putExtra(Constants.ROOM_INTENT, (Serializable) roomsArray.get(id));
                intent.putExtra(Constants.ROOM_ARRAY_INTENT, (Serializable) roomsArray);
                startActivity(intent);
            }else{
                ((RoomListFragment)((RoomActivity)this).getFragment()).changeRoom(roomsArray.get(id));
            }

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


}

