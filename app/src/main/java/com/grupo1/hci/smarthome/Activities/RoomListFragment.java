package com.grupo1.hci.smarthome.Activities;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ListFragment;
import android.support.v7.widget.Toolbar;
import android.view.ActionMode;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.grupo1.hci.smarthome.Model.APIManager;
import com.grupo1.hci.smarthome.Model.Constants;
import com.grupo1.hci.smarthome.Model.Device;
import com.grupo1.hci.smarthome.Model.Room;
import com.grupo1.hci.smarthome.R;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by agust on 11/4/2017.
 */

public class RoomListFragment extends ListFragment implements AdapterView.OnItemClickListener, AdapterView.OnItemLongClickListener{

     // Array of strings...
    ArrayList<Device> deviceArray = new ArrayList<>();
    ArrayList<View> selectedElement = new ArrayList<>();
    Room room;
    ActionMode mActionMode;
    Snackbar mySnackbar;
    TextView emptyTextView;
    Toolbar toolbar;
    private CountDownTimer deleteCountDown;
    ArrayAdapter rowAdapter;
    View view;

    public void setDeviceArray(ArrayList<Device> deviceArray) {
        this.deviceArray = deviceArray;
    }

    public void setmActionMode(ActionMode mActionMode) {
        this.mActionMode = mActionMode;
    }

    ActionMode.Callback mActionModeCallback;

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_list, container, false);
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        room = ((RoomActivity)getActivity()).getRoom();
        toolbar = ((NavigationActivity)getActivity()).getToolbar();
        setView();
        getListView().setOnItemLongClickListener(this);
        getListView().setOnItemClickListener(this);

    }

    public void changeRoom(Room room){
        this.room = room;
        loadDevices(room.getId());
        setView();
    }


    public void loadDevices(String roomID) {
        deviceArray = APIManager.getRoomDevices(roomID);
        rowAdapter.clear();
        rowAdapter.addAll(deviceArray);
        rowAdapter.notifyDataSetChanged();
        emptyTextView = getActivity().findViewById(R.id.fragmentList_emptyListTextView);
        if(deviceArray.isEmpty()){
            emptyTextView.setVisibility(View.VISIBLE);
        }else{
            emptyTextView.setVisibility(View.GONE);
        }
    }


    private void setView() {
        toolbar.setTitle(room.getName());
        //set the contextual floating menu
        mActionModeCallback = new RoomContextualMenu();
        ((RoomContextualMenu) mActionModeCallback).setRoomActivity((RoomActivity)getActivity());
        //set listview Adapter and onCikcListener
        getListView().setVisibility(View.VISIBLE);
        view.findViewById(R.id.gridView).setVisibility(View.GONE);
        rowAdapter = new RoomsAdapter((RoomActivity)getActivity(), deviceArray);
        setListAdapter(rowAdapter);
        loadDevices(room.getId());

    }

    private void startDeviceActivity(Device device) {
        Intent intent;
        intent = new Intent(getActivity().getApplicationContext(), DeviceActivity.class);
        intent.putExtra(Constants.DEVICE_INTENT, (Serializable) device);
        startActivity(intent);
    }

    public void selectedElement(View view, Device device) {
        view.setBackgroundColor(Color.GRAY);
        view.findViewById(R.id.rowLayout_iconImageView).setBackground(null);
        selectedElement.add(view);
        toolbar.setTitle(device.getName());
    }

    public void diselectElement() {
        for(View v: selectedElement){
            v.setBackgroundColor(Color.TRANSPARENT);
        }
        toolbar.setTitle(room.getName());
    }

    public void deleteDevice(String roomId)
    {
        //setting the snackbar
        mySnackbar = Snackbar.make(getActivity().findViewById(R.id.homeActivity_Fragmentcontainer), "Deleted", Snackbar.LENGTH_LONG);
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
                Toast.makeText(getActivity().getApplicationContext(), "Se borro!!!!!", Toast.LENGTH_SHORT).show();
            }
        }.start();
    }


    public void deleteRoom(String roomId)
    {
        //setting the snackbar
        mySnackbar = Snackbar.make(getActivity().findViewById(R.id.homeActivity_Fragmentcontainer), "Deleted", Snackbar.LENGTH_LONG);
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
                Toast.makeText(getActivity().getApplicationContext(), "Se borro!!!!!", Toast.LENGTH_SHORT).show();
            }
        }.start();
    }

    public void selectedElement(View view, Room room) {
        view.setBackgroundColor(Color.GRAY);
        view.findViewById(R.id.rowLayout_iconImageView).setBackground(null);
        selectedElement.add(view);
        toolbar.setTitle(room.getName());
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        Object o = adapterView.getAdapter().getItem(i);
        Device device = (Device) o;//As you are using Default String Adapter
        Toast.makeText(getActivity().getApplicationContext(), device.getName(), Toast.LENGTH_SHORT).show();

        //if is the same view as the selected one
        if(mActionMode == null){
            diselectElement();
            startDeviceActivity(device);
        }else{
            ((RoomContextualMenu)mActionModeCallback).changeToSeveralItemsMenu();
            selectedElement(view, device);
        }
    }

    @Override
    public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {

        Object o = adapterView.getAdapter().getItem(i);
        Device device = (Device) o;//As you are using Default String Adapter
        Toast.makeText(getActivity().getApplicationContext(), device.getName(), Toast.LENGTH_SHORT).show();
        selectedElement(view, device);
        if (mActionMode != null) {
            ((RoomContextualMenu)mActionModeCallback).changeToSeveralItemsMenu();
            return false;
        }
        // Start the CAB using the ActionMode.Callback defined above
        ((RoomContextualMenu) mActionModeCallback).addDevice(device);
        mActionMode = getActivity().startActionMode(mActionModeCallback);

        return true;
    }
}
