package com.grupo1.hci.smarthome.Activities;

import android.content.Intent;
import android.graphics.Color;
import android.os.CountDownTimer;
import android.support.design.widget.Snackbar;
import android.os.Bundle;
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
    ArrayList<Device> deviceArray = new ArrayList<>();
    View selectedElement;
    Room room;
    ListView listView;
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
        loadDevices();
        setView();
        setOnClickListener();


    }

    private void loadDevices() {
        deviceArray = APIManager.getRoomDevices("1");
    }

    private void setOnClickListener() {
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View view, int position, long id) {

                Object o = arg0.getAdapter().getItem(position);
                Device device = (Device) o;//As you are using Default String Adapter
                Toast.makeText(getApplicationContext(), device.getName(), Toast.LENGTH_SHORT).show();

                //if is the same view as the selected one
                if(mActionMode == null){
                    diselectElement();
                    startDeviceActivity(device);
                }
            }
        });
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> arg0, View view, int position, long id) {

                Object o = arg0.getAdapter().getItem(position);
                Device device = (Device) o;//As you are using Default String Adapter
                Toast.makeText(getApplicationContext(), device.getName(), Toast.LENGTH_SHORT).show();
                if (mActionMode != null) {
                    return false;
                }
                // Start the CAB using the ActionMode.Callback defined above
                ((RoomContextualMenu) mActionModeCallback).setDevice(device);
                mActionMode = RoomActivity.this.startActionMode(mActionModeCallback);
                selectedElement(view, device);
                return true;
            }
        });

    }

    private void setView() {
        listView = (ListView) findViewById(R.id.contentRoom_ListView);

        //set the contextual floating menu
        mActionModeCallback = new RoomContextualMenu();
        ((RoomContextualMenu) mActionModeCallback).setRoomActivity(this);

        //set listview Adapter and onCikcListener
        final ArrayAdapter rowAdapter = new RoomsAdapter(this, deviceArray);
        listView.setAdapter(rowAdapter);
    }

    private void startDeviceActivity(Device device) {
        Intent intent;
        intent = new Intent(getApplicationContext(), DeviceActivity.class);
        intent.putExtra(Constants.DEVICE_INTENT, (Serializable) device);
        startActivity(intent);
    }

    public void selectedElement(View view, Device device) {
        view.setBackgroundColor(Color.GRAY);
        view.findViewById(R.id.rowLayout_iconImageView).setBackground(null);
        selectedElement = view;
        toolbar.setTitle(device.getName());
    }

    public void diselectElement() {
        if(selectedElement != null){
            selectedElement.setBackgroundColor(Color.TRANSPARENT);
            toolbar.setTitle(Constants.appName);
        }
    }

    public void deleteDevice(String roomId)
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
}

