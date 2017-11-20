package com.grupo1.hci.smarthome.Activities;

import android.view.ActionMode;
import android.view.View;

import com.grupo1.hci.smarthome.Model.Room;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by agust on 11/17/2017.
 */

public interface HomeFragment {

    void deleteRooms(HashMap<Room,Integer> rooms);
    void loadRomms(List<Room> rooms);
    void setmActionMode(ActionMode mActionMode);
    void diselectElements();
    void diselectElement(View view);
    void roomDeleteError(Room room);
}
