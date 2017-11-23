package com.grupo1.hci.smarthome.Activities;

import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ListFragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.Toolbar;
import android.view.ActionMode;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.TextView;
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

import static android.app.Activity.RESULT_OK;

/**
 * Created by agust on 11/4/2017.
 */

public class RoomListFragment extends ListFragment implements AdapterView.OnItemClickListener, AdapterView.OnItemLongClickListener, SwipeRefreshLayout.OnRefreshListener,RoomsFragment {

    // Array of strings...
    ArrayList<Device> deviceArray = new ArrayList<>();
    ArrayList<View> selectedElement = new ArrayList<>();
    Room room;
    ActionMode mActionMode;
    Snackbar mySnackbar;
    Toolbar toolbar;
    private CountDownTimer deleteCountDown;
    ArrayAdapter rowAdapter;
    View view;
    boolean isLargeScreen;
    SwipeRefreshLayout swipeRefreshLayout;
    APIManager apiManager;

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
        view = inflater.inflate(R.layout.fragment_room, container, false);
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        room = ((RoomActivity) getActivity()).getRoom();
        apiManager = APIManager.getInstance(getActivity());
        apiManager.getDevicesForRoom(room.getId(), getActivity(), null);
        toolbar = ((NavigationActivity) getActivity()).getToolbar();
        setView();
        getListView().setOnItemLongClickListener(this);
        getListView().setOnItemClickListener(this);
        isLargeScreen = ((getResources().getConfiguration().screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK) == Configuration.SCREENLAYOUT_SIZE_XLARGE);
    }

    public void changeRoom(Room room) {
        this.room = room;
        setView();
    }



    private void setView() {
        toolbar.setTitle(room.getName());
        //set the contextual floating menu
        mActionModeCallback = new RoomContextualMenu();
        ((RoomContextualMenu) mActionModeCallback).setRoomActivity((RoomActivity) getActivity());
        //set listview Adapter and onCikcListener
        rowAdapter = new RoomsAdapter((RoomActivity) getActivity(), deviceArray, (RoomsFragment) this, (RoomActivity) getActivity());
        setListAdapter(rowAdapter);
        swipeRefreshLayout = view.findViewById(R.id.framentRoom_refreshLayout);
        swipeRefreshLayout.setOnRefreshListener(this);

    }


    public void startDeviceActivity(Device device) {
        Intent intent;
        intent = new Intent(getActivity().getApplicationContext(), DeviceActivity.class);
        intent.putExtra(Constants.DEVICE_INTENT, (Serializable) device);
        startActivityForResult(intent, 148);
    }

    public void selectedElement(View view, Device device) {
        view.setBackgroundColor(Color.GRAY);
        view.findViewById(R.id.rowLayout_iconImageView).setBackground(null);
        selectedElement.add(view);
        toolbar.setTitle(device.getName());
    }

    public void diselectElements() {
        for (View v : selectedElement) {
            v.setBackgroundColor(Color.TRANSPARENT);
        }
        toolbar.setTitle(room.getName());
        selectedElement.clear();
    }

    public void diselectElement(View view) {
        view.setBackgroundColor(Color.TRANSPARENT);
        selectedElement.remove(view);
        if (selectedElement.size() == 1) {
            ((RoomContextualMenu) mActionModeCallback).changeToOneItemsMenu();
        }
        if (selectedElement.isEmpty()) {
            mActionMode.finish();
        }
    }

    @Override
    public void deviceDeleteError(Device device) {
        deviceArray.add(device);
        loadDevices(deviceArray);
    }

    @Override
    public void deleteDevice(Device device) {
        deviceArray.remove(device);
        loadDevices(deviceArray);

    }

    @Override
    public ArrayList<View> getSelectedElements() {
        return selectedElement;
    }

    public void deleteDevices(final HashMap<Device, Integer> devices) {
        //setting the snackbar
        mySnackbar = Snackbar.make(getActivity().findViewById(R.id.homeActivity_Fragmentcontainer), R.string.deleted, Snackbar.LENGTH_LONG);
        mySnackbar.setAction(R.string.undo, new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                for(Device d: devices.keySet()){
                    if(devices.get(d) < deviceArray.size()){
                        deviceArray.add(devices.get(d), d);
                    }else{
                        deviceArray.add(d);
                    }
                }
                loadDevices(deviceArray);
                deleteCountDown.cancel();
            }
        });
        deviceArray.removeAll(devices.keySet());
        loadDevices(deviceArray);
        diselectElements();
        mySnackbar.show();
        if(mActionMode != null){
            mActionMode.finish();
        }
        deleteCountDown = new CountDownTimer(4000, 1000) {
            @Override
            public void onTick(long l) {

            }

            @Override
            public void onFinish() {
                APIManager apiManager = APIManager.getInstance(getActivity());
                for(Device d: devices.keySet()){
                    apiManager.deleteDevice(d, getActivity(), Constants.DELTE__FROM_OVERFLOW);
                }
            }
        }.start();
    }

    @Override
    public void loadDevices(List<Device> devices) {
        deviceArray = (ArrayList<Device>) devices;
        rowAdapter.clear();
        rowAdapter.addAll(devices);
        rowAdapter.notifyDataSetChanged();
        if(deviceArray.isEmpty()) {
            Toast.makeText(getActivity(), getResources().getText(R.string.noDevices), Toast.LENGTH_SHORT).show();
        }
    }


    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        Object o = adapterView.getAdapter().getItem(i);
        Device device = (Device) o;//As you are using Default String Adapter
        //if is the same view as the selected one
        if (mActionMode == null) {
            diselectElements();
            if(isLargeScreen){
                ((RoomActivity)getActivity()).changeDeviceFragment(device);
                return;
            }
            startDeviceActivity(device);
        } else {
            if (selectedElement.contains(view)) {
                diselectElement(view);
                ((RoomContextualMenu) mActionModeCallback).removeDevice(device);
            } else {
                ((RoomContextualMenu) mActionModeCallback).changeToSeveralItemsMenu();
                ((RoomContextualMenu) mActionModeCallback).addDevice(device, i);
                selectedElement(view, device);
            }
        }
    }

    @Override
    public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {


        Object o = adapterView.getAdapter().getItem(i);
        Device device = (Device) o;//As you are using Default String Adapter
        selectedElement(view, device);
        if (mActionMode != null) {
            if (selectedElement.contains(view)) {
                diselectElement(view);
                ((RoomContextualMenu) mActionModeCallback).removeDevice(device);
            } else {
                ((RoomContextualMenu) mActionModeCallback).changeToSeveralItemsMenu();
                ((RoomContextualMenu) mActionModeCallback).addDevice(device, i);
                selectedElement(view, device);
            }
            return false;
        }
        // Start the CAB using the ActionMode.Callback defined above
        ((RoomContextualMenu) mActionModeCallback).addDevice(device, i);
        mActionMode = getActivity().startActionMode(mActionModeCallback);

        return true;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 148) {
            // Make sure the request was successful
            if (resultCode == RESULT_OK) {
                Device device = (Device) data.getSerializableExtra(Constants.DEVICE_INTENT);
               deviceArray.remove(device);
                loadDevices(deviceArray);
            }
        }
    }

    @Override
    public void onRefresh() {
        apiManager.getDevicesForRoom(room.getId(), getActivity(), swipeRefreshLayout);
    }
}
