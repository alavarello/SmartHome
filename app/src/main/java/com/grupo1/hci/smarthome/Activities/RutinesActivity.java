package com.grupo1.hci.smarthome.Activities;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.design.widget.Snackbar;
import android.view.ActionMode;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.grupo1.hci.smarthome.Model.APIManager;
import com.grupo1.hci.smarthome.Model.Constants;
import com.grupo1.hci.smarthome.Model.Device;
import com.grupo1.hci.smarthome.Model.Room;
import com.grupo1.hci.smarthome.Model.Rutine;
import com.grupo1.hci.smarthome.R;

import java.io.Serializable;
import java.util.ArrayList;

public class RutinesActivity extends NavigationActivity {

    // Array of strings...
    ArrayList<Rutine> rutineArray = new ArrayList<>();
    View selectedElement;
    ListView listView;
    ActionMode mActionMode;
    Snackbar mySnackbar;
    private CountDownTimer deleteCountDown;

    public void setmActionMode(ActionMode mActionMode) {
        this.mActionMode = mActionMode;
    }

    ActionMode.Callback mActionModeCallback;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        loadRutines();
        setView();
        setOnClickListener();


    }

    private void loadRutines() {
        rutineArray = APIManager.getRutines();
    }

    private void setOnClickListener() {
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View view, int position, long id) {

                Object o = arg0.getAdapter().getItem(position);
                Device device = (Device) o;//As you are using Default String Adapter
                Toast.makeText(getApplicationContext(), device.getName(), Toast.LENGTH_SHORT).show();

                //if is the same view as the selected one
                if(mActionMode == null){
                    diselectElement();
                }
            }
        });
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> arg0, View view, int position, long id) {

                Object o = arg0.getAdapter().getItem(position);
                Rutine rutine = (Rutine) o;//As you are using Default String Adapter
                Toast.makeText(getApplicationContext(), rutine.toString(), Toast.LENGTH_SHORT).show();
                if (mActionMode != null) {
                    return false;
                }
                // Start the CAB using the ActionMode.Callback defined above
                ((RutineContextualMenu) mActionModeCallback).setRutine(rutine);
                mActionMode = RutinesActivity.this.startActionMode(mActionModeCallback);
                selectedElement(view, rutine);
                return true;
            }
        });

    }

    private void setView() {
        listView = (ListView) findViewById(R.id.contentRoom_ListView);

        //set the contextual floating menu
        mActionModeCallback = new RutineContextualMenu();
        ((RutineContextualMenu) mActionModeCallback).setRutineActivity(this);

        //set listview Adapter and onCikcListener
        final ArrayAdapter rowAdapter = new RutineAdapter(this, rutineArray);
        listView.setAdapter(rowAdapter);
    }

    public void selectedElement(View view,Rutine rutine) {
        view.setBackgroundColor(Color.GRAY);
        view.findViewById(R.id.rowLayout_iconImageView).setBackground(null);
        selectedElement = view;
        toolbar.setTitle(rutine.toString());
    }

    public void diselectElement() {
        if(selectedElement != null){
            selectedElement.setBackgroundColor(Color.TRANSPARENT);
            toolbar.setTitle(R.string.title_activity_rutines);
        }
    }

    public void deleteRutine(String rutineId)
    {
        //setting the snackbar
        mySnackbar = Snackbar.make(findViewById(R.id.contentRoom_ListView), "Deleted", Snackbar.LENGTH_LONG);
        mySnackbar.setAction("Undo", new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                deleteCountDown.cancel();
            }
        });

        mySnackbar.show();
        deleteCountDown = new CountDownTimer(4000,1000) {
            @Override
            public void onTick(long l) {

            }

            @Override
            public void onFinish() {
                Toast.makeText(getApplicationContext(), "Se borro!!!!!", Toast.LENGTH_SHORT).show();
            }
        }.start();
    }

}

