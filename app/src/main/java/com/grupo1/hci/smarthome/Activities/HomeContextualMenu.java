package com.grupo1.hci.smarthome.Activities;

import android.content.Context;
import android.util.Log;
import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.grupo1.hci.smarthome.Model.Room;
import com.grupo1.hci.smarthome.R;

/**
 * Created by agust on 11/1/2017.
 */

public class HomeContextualMenu implements ActionMode.Callback {

    Room room;
    HomeActivity homeActivity;


    public void setRoom(Room room) {
        this.room = room;
    }

    public void setHomeActivity(HomeActivity homeActivity) {
        this.homeActivity = homeActivity;
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

        Log.i("Menu Item click: ", room.getName());
        homeActivity.deleteRoom(room.getId());
        return true;
    }

    @Override
    public void onDestroyActionMode(ActionMode actionMode) {
        homeActivity.setmActionMode(null);
        homeActivity.diselectElement();
    }

}
