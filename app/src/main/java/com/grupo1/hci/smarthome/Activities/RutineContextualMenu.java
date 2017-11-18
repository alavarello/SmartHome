package com.grupo1.hci.smarthome.Activities;

import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.grupo1.hci.smarthome.Model.Rutine;
import com.grupo1.hci.smarthome.R;

import java.util.ArrayList;

/**
 * Created by agust on 11/1/2017.
 */

public class RutineContextualMenu implements ActionMode.Callback {

    ArrayList<Rutine> rutines = new ArrayList<>();
    RutinesActivity rutineActivity;
    Menu menu;



    public void addRutine(Rutine rutine) {
        this.rutines.add(rutine);
    }

    public void setRutineActivity(RutinesActivity roomActivity) {
        this.rutineActivity = roomActivity;
    }
    public void changeToSeveralItemsMenu(){
        menu.findItem(R.id.editElement).setVisible(false);
    }

    public void changeToOneItemsMenu(){
        menu.findItem(R.id.editElement).setVisible(true);
    }

    @Override
    public boolean onCreateActionMode(ActionMode actionMode, Menu menu) {
        MenuInflater inflater = actionMode.getMenuInflater();
        this.menu =menu;
        inflater.inflate(R.menu.one_item_contextual_menu, menu);
        return true;
    }

    @Override
    public boolean onPrepareActionMode(ActionMode actionMode, Menu menu) {
        return false;
    }

    @Override
    public boolean onActionItemClicked(ActionMode actionMode, MenuItem menuItem) {
        for (Rutine r:rutines){
            ((RutinesListFragment)rutineActivity.getFragment()).deleteRutine(r.toString());
        }
        return true;
    }

    @Override
    public void onDestroyActionMode(ActionMode actionMode) {
        ((RutinesListFragment)rutineActivity.getFragment()).setmActionMode(null);
        ((RutinesListFragment)rutineActivity.getFragment()).diselectElement();

    }

}
