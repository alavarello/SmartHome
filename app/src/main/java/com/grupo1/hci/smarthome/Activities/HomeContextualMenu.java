package com.grupo1.hci.smarthome.Activities;

import android.util.Log;
import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.grupo1.hci.smarthome.Model.Room;
import com.grupo1.hci.smarthome.R;

import java.util.ArrayList;

/**
 * Created by agust on 11/1/2017.
 */

public class HomeContextualMenu implements ActionMode.Callback {

    ArrayList<Room> rooms = new ArrayList<>();
    HomeActivity homeActivity;
    MenuInflater inflater;
    Menu menu;
    public void addRoom(Room room) {
        this.rooms.add(room);
    }

    public void setHomeActivity(HomeActivity homeActivity) {
        this.homeActivity = homeActivity;
    }

    @Override
    public boolean onCreateActionMode(ActionMode actionMode, Menu menu) {
        inflater = actionMode.getMenuInflater();
        this.menu = menu;
        inflater.inflate(R.menu.one_item_contextual_menu, menu);
        return true;
    }

    public void changeToSeveralItemsMenu(){
        menu.findItem(R.id.editElement).setVisible(false);
    }

    @Override
    public boolean onPrepareActionMode(ActionMode actionMode, Menu menu) {
        return false;
    }

    @Override
    public boolean onActionItemClicked(ActionMode actionMode, MenuItem menuItem) {

        Log.i("Menu Item click: ", rooms.toString());
        for (Room r:rooms){
            ((HomeFragment)homeActivity.getFragment()).deleteRoom(r.getId());
        }
        return true;
    }

    @Override
    public void onDestroyActionMode(ActionMode actionMode) {
        ((HomeFragment)homeActivity.getFragment()).setmActionMode(null);
        ((HomeFragment)homeActivity.getFragment()).diselectElement();
    }

}
