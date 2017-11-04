package com.grupo1.hci.smarthome.Activities;

import android.app.Activity;
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
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.SubMenu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.grupo1.hci.smarthome.Model.Constants;
import com.grupo1.hci.smarthome.Model.Room;
import com.grupo1.hci.smarthome.R;

import java.io.Serializable;
import java.util.ArrayList;

public class HomeActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    // Array of strings...
    ArrayList<Room> roomsArray = new ArrayList<>();
    View selectedElement;
    Toolbar toolbar;
    ActionMode mActionMode;
    Snackbar mySnackbar;
    private CountDownTimer deleteCountDown;

    public void setmActionMode(ActionMode mActionMode) {
        this.mActionMode = mActionMode;
    }

    ActionMode.Callback mActionModeCallback;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        ListView listView = (ListView) findViewById(R.id.contentRoom_ListView);

        //Set toolbar
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        //Add Elements to array for testing------------------
        roomsArray.add(new Room("1", "Cuarto1"));
        roomsArray.add(new Room("2", "Cuarto2"));
        roomsArray.add(new Room("3", "Cuarto3"));
        roomsArray.add(new Room("4", "Cuarto4"));
        roomsArray.add(new Room("5", "Cuarto5"));
        roomsArray.add(new Room("6", "Cuarto6"));

        //------------------------------------------------------

        //set the contextual floating menu
        mActionModeCallback = new HomeContextualMenu();
        ((HomeContextualMenu) mActionModeCallback).setHomeActivity(this);

        //set listview Adapter and onCikcListener
        ArrayAdapter rowAdapter = new HomeAdapter(this, roomsArray);
        listView.setAdapter(rowAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View view, int position, long arg3) {
                Object o = arg0.getAdapter().getItem(position);
                Room room = (Room) o;//As you are using Default String Adapter
                Toast.makeText(getApplicationContext(), room.getName() + " ShortClick", Toast.LENGTH_SHORT).show();
                //if is the same view as the selected one
                if (mActionMode == null) {
                    diselectElement();
                    Intent intent = new Intent(HomeActivity.this.getApplicationContext(), RoomActivity.class);
                    intent.putExtra(Constants.ROOM_ARRAY_INTENT, (Serializable) roomsArray);
                    intent.putExtra(Constants.ROOM_INTENT, (Serializable) room);
                    startActivity(intent);
                }
            }
        });

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> arg0, View view, int position, long id) {
                Object o = arg0.getAdapter().getItem(position);
                Room room = (Room) o;//As you are using Default String Adapter

                Toast.makeText(getApplicationContext(), room.getName() + " LongClick", Toast.LENGTH_SHORT).show();
                if (mActionMode != null) {
                    return false;
                }
                // Start the CAB using the ActionMode.Callback defined above
                ((HomeContextualMenu) mActionModeCallback).setRoom(room);
                mActionMode = HomeActivity.this.startActionMode(mActionModeCallback);
                selectedElement(view, room);

                return true;
            }
        });

        //Set up the nav menu for the rooms
         SubMenu m = navigationView.getMenu().addSubMenu("Cuartos");
         int i =0;
        for (Room r : roomsArray) {
            m.add(R.id.roomGroupNavigationalDrawer, i++ ,500, r.getName());
        }




    }

    public void deleteRoom(String roomId)
    {
        //setting the snackbar
        mySnackbar = Snackbar.make(findViewById(R.id.contentRoom_ListView), "Deleted", Snackbar.LENGTH_LONG);
        mySnackbar.setAction("Undo", new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                deleteCountDown.cancel();
            }
        });

        mySnackbar.show();
        deleteCountDown = new CountDownTimer(4000,1000) {
            @Override
            public void onTick(long l) {

            }

            @Override
            public void onFinish() {
                Toast.makeText(getApplicationContext(), "Se borro!!!!!", Toast.LENGTH_SHORT).show();
            }
        }.start();
    }

    public void selectedElement(View view, Room room) {
        view.setBackgroundColor(Color.GRAY);
        view.findViewById(R.id.rowLayout_iconImageView).setBackground(null);
        selectedElement =view;
        toolbar.setTitle(room.getName());
    }

    public void diselectElement() {
        if(selectedElement != null){
            selectedElement.setBackgroundColor(Color.TRANSPARENT);
            toolbar.setTitle(Constants.appName);
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
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();
        Intent intent = new Intent(HomeActivity.this.getApplicationContext(), RoomActivity.class);
        intent.putExtra(Constants.ROOM_INTENT, (Serializable) roomsArray.get(id));
        intent.putExtra(Constants.ROOM_ARRAY_INTENT, (Serializable) roomsArray);
        startActivity(intent);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
