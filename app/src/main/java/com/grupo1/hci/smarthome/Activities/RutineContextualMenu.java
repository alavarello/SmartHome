package com.grupo1.hci.smarthome.Activities;

import android.util.Log;
import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.grupo1.hci.smarthome.Model.Device;
import com.grupo1.hci.smarthome.Model.Rutine;
import com.grupo1.hci.smarthome.R;

/**
 * Created by agust on 11/1/2017.
 */

public class RutineContextualMenu implements ActionMode.Callback {

    Rutine rutine;
    RutinesActivity rutineActivity;


    public void setRutine(Rutine rutine) {
        this.rutine = rutine;
    }

    public void setRutineActivity(RutinesActivity roomActivity) {
        this.rutineActivity = roomActivity;
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

        rutineActivity.deleteRutine(rutine.toString());
        return true;
    }

    @Override
    public void onDestroyActionMode(ActionMode actionMode) {
      rutineActivity.setmActionMode(null);
      rutineActivity.diselectElement();

    }

}
