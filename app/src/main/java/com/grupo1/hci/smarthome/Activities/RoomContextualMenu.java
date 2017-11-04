package com.grupo1.hci.smarthome.Activities;

import android.util.Log;
import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.grupo1.hci.smarthome.Model.Device;
import com.grupo1.hci.smarthome.Model.Room;
import com.grupo1.hci.smarthome.R;

/**
 * Created by agust on 11/1/2017.
 */

public class RoomContextualMenu implements ActionMode.Callback {

    Device device;
    RoomActivity roomActivity;


    public void setDevice(Device device) {
        this.device = device;
    }

    public void setRoomActivity(RoomActivity roomActivity) {
        this.roomActivity = roomActivity;
    }

    @Override
    public boolean onCreateActionMode(ActionMode actionMode, Menu menu) {
        MenuInflater inflater = actionMode.getMenuInflater();
        inflater.inflate(R.menu.contextual_menu, menu);
        return true;
    }

    @Override
    public boolean onPrepareActionMode(ActionMode actionMode, Menu menu) {
        return false;
    }

    @Override
    public boolean onActionItemClicked(ActionMode actionMode, MenuItem menuItem) {

        Log.i("Menu Item click: ", device.getName());
        roomActivity.deleteDevice(device.getId());
        return true;
    }

    @Override
    public void onDestroyActionMode(ActionMode actionMode) {
      roomActivity.setmActionMode(null);
      roomActivity.diselectElement();

    }

}
