package com.grupo1.hci.smarthome.Activities;

import android.view.ActionMode;
import android.view.View;

/**
 * Created by agust on 11/18/2017.
 */

public interface RoomsFragment {

    void deleteDevice(String roomID);
    void setmActionMode(ActionMode mActionMode);
    void diselectElements();
    void diselectElement(View view);
}
