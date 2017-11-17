package com.grupo1.hci.smarthome.Activities;

import android.util.Log;
import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.grupo1.hci.smarthome.Model.Device;
import com.grupo1.hci.smarthome.R;

import java.util.ArrayList;

/**
 * Created by agust on 11/1/2017.
 */

public class RoomContextualMenu implements ActionMode.Callback {

    ArrayList<Device> devices = new ArrayList<>();
    RoomActivity roomActivity;
    Menu menu;

    public void addDevice(Device device) {
        this.devices.add(device);
    }

    public void setRoomActivity(RoomActivity roomActivity) {
        this.roomActivity = roomActivity;
    }

    @Override
    public boolean onCreateActionMode(ActionMode actionMode, Menu menu) {
        MenuInflater inflater = actionMode.getMenuInflater();
        this.menu = menu;
        inflater.inflate(R.menu.one_item_contextual_menu, menu);
        return true;
    }

    @Override
    public boolean onPrepareActionMode(ActionMode actionMode, Menu menu) {
        return false;
    }

    @Override
    public boolean onActionItemClicked(ActionMode actionMode, MenuItem menuItem) {

        Log.i("Menu Item click: ", devices.toString());
        for(Device d: devices) {
            ((RoomListFragment) roomActivity.getFragment()).deleteDevice(d.getId());
        }
        return true;
    }

    public void changeToSeveralItemsMenu(){
        menu.findItem(R.id.editElement).setVisible(false);
    }

    @Override
    public void onDestroyActionMode(ActionMode actionMode) {
        ((RoomListFragment)roomActivity.getFragment()).setmActionMode(null);
        ((RoomListFragment)roomActivity.getFragment()).diselectElement();

    }

}
