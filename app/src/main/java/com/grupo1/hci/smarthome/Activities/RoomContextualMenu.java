package com.grupo1.hci.smarthome.Activities;

import android.util.Log;
import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.grupo1.hci.smarthome.Model.Device;
import com.grupo1.hci.smarthome.Model.Room;
import com.grupo1.hci.smarthome.R;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by agust on 11/1/2017.
 */

public class RoomContextualMenu implements ActionMode.Callback {

    HashMap<Device, Integer> devices = new HashMap<>();
    RoomActivity roomActivity;
    Menu menu;

    public void addDevice(Device device, int position) {
        this.devices.put(device, position);
    }

    public void removeDevice(Device device) {
        this.devices.remove(device);
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

    public void changeToOneItemsMenu(){
        menu.findItem(R.id.editElement).setVisible(true);
    }

    @Override
    public boolean onPrepareActionMode(ActionMode actionMode, Menu menu) {
        return false;
    }

    @Override
    public boolean onActionItemClicked(ActionMode actionMode, MenuItem menuItem) {

        switch (menuItem.getItemId()) {
            case R.id.editElement:
                EditDialogMessage editDeviceMessage = new EditDialogMessage();
                editDeviceMessage.setDevice(devices.keySet().iterator().next());
                editDeviceMessage.setView(roomActivity.getSelectedElements().get(0));
                editDeviceMessage.show(roomActivity.getFragmentManager(), "editDevice");
                break;
            case R.id.deleteElement:
                ((RoomsFragment) roomActivity.getFragment()).deleteDevices(devices);
                break;
        }
        return true;
    }

    public void changeToSeveralItemsMenu(){
        menu.findItem(R.id.editElement).setVisible(false);
    }

    @Override
    public void onDestroyActionMode(ActionMode actionMode) {
        ((RoomListFragment)roomActivity.getFragment()).setmActionMode(null);
        ((RoomListFragment)roomActivity.getFragment()).diselectElements();

    }

}
