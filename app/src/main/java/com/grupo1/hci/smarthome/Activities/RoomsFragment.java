package com.grupo1.hci.smarthome.Activities;

import android.view.ActionMode;
import android.view.View;

import com.grupo1.hci.smarthome.Model.Device;
import com.grupo1.hci.smarthome.Model.Room;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by agust on 11/18/2017.
 */

public interface RoomsFragment {

    void deleteDevices(HashMap<Device, Integer> devices);
    void loadDevices(List<Device> devices);
    void setmActionMode(ActionMode mActionMode);
    void diselectElements();
    void diselectElement(View view);
    void deviceDeleteError(Device device);
    void deleteDevice(Device device);
    ArrayList<View> getSelectedElements();
}
