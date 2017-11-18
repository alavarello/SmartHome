package com.grupo1.hci.smarthome.Activities;

import android.view.ActionMode;

/**
 * Created by agust on 11/18/2017.
 */

public interface RoomsFragment {

    void deleteDevice(String roomID);
    void setmActionMode(ActionMode mActionMode);
    void diselectElement();
}
