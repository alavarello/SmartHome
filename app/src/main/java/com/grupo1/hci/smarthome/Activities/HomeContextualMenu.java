package com.grupo1.hci.smarthome.Activities;

import android.util.Log;
import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.grupo1.hci.smarthome.Model.Room;
import com.grupo1.hci.smarthome.R;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by agust on 11/1/2017.
 */

public class HomeContextualMenu implements ActionMode.Callback {

    HashMap<Room,Integer> rooms = new HashMap<>();
    HomeActivity homeActivity;
    MenuInflater inflater;
    Menu menu;
    public void addRoom(Room room, int position) {
        this.rooms.put(room, position);
    }

    public void removeRoom(Room room) {
        this.rooms.remove(room);
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

    public void changeToOneItemsMenu(){
        menu.findItem(R.id.editElement).setVisible(true);
    }

    @Override
    public boolean onPrepareActionMode(ActionMode actionMode, Menu menu) {
        return false;
    }

    @Override
    public boolean onActionItemClicked(ActionMode actionMode, MenuItem menuItem) {

       switch (menuItem.getItemId()){
           case R.id.editElement:
               EditDialogMessage editDeviceMessage = new EditDialogMessage();
               editDeviceMessage.setRoom(rooms.keySet().iterator().next());
               editDeviceMessage.show(homeActivity.getFragmentManager(), "editDevice");
               break;
           case R.id.deleteElement:
               ((HomeFragment)homeActivity.getFragment()).deleteRooms(rooms);

               break;
       }
        return true;
    }

    @Override
    public void onDestroyActionMode(ActionMode actionMode) {
        ((HomeFragment)homeActivity.getFragment()).setmActionMode(null);
        ((HomeFragment)homeActivity.getFragment()).diselectElements();
    }

}
