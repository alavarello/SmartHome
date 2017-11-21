package com.grupo1.hci.smarthome.Activities;

import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.grupo1.hci.smarthome.Model.Device;
import com.grupo1.hci.smarthome.Model.Rutine;
import com.grupo1.hci.smarthome.R;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by agust on 11/1/2017.
 */

public class RutineContextualMenu implements ActionMode.Callback {

    HashMap<Rutine,Integer> rutines = new HashMap<>();
    RutinesActivity rutineActivity;
    Menu menu;



    public void addRutine(Rutine rutine, Integer position) {
        this.rutines.put(rutine, position);
    }

    public void removeRutine(Rutine rutine) {
        this.rutines.remove(rutine);
    }

    public void setRutineActivity(RutinesActivity roomActivity) {
        this.rutineActivity = roomActivity;
    }
    @Override
    public boolean onCreateActionMode(ActionMode actionMode, Menu menu) {
        MenuInflater inflater = actionMode.getMenuInflater();
        this.menu =menu;
        inflater.inflate(R.menu.contextual_menu, menu);
        return true;
    }

    @Override
    public boolean onPrepareActionMode(ActionMode actionMode, Menu menu) {
        return false;
    }

    @Override
    public boolean onActionItemClicked(ActionMode actionMode, MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case R.id.deleteElement:
                ((RutinesListFragment) rutineActivity.getFragment()).deleteRutines(rutines);
                break;
        }
        return true;
    }

    @Override
    public void onDestroyActionMode(ActionMode actionMode) {
        ((RutinesListFragment)rutineActivity.getFragment()).setmActionMode(null);
        ((RutinesListFragment)rutineActivity.getFragment()).diselectElements();

    }

}
