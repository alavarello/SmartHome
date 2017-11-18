package com.grupo1.hci.smarthome.Activities;

import android.view.ActionMode;
import android.view.View;

/**
 * Created by agust on 11/17/2017.
 */

interface HomeFragment {

    void deleteRoom(String roomID);
    void setmActionMode(ActionMode mActionMode);
    void diselectElements();
    void diselectElement(View view);

    //TODO add in diselectElement: if it's the last element in the array, the contextual menu should disappear
}
