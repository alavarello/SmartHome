package com.grupo1.hci.smarthome.Activities;

import android.view.ActionMode;

/**
 * Created by agust on 11/17/2017.
 */

interface HomeFragment {

    void deleteRoom(String roomID);
    void setmActionMode(ActionMode mActionMode);
    void diselectElement();
}
