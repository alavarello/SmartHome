package com.grupo1.hci.smarthome.Activities;

import android.app.FragmentManager;
import android.support.v7.app.ActionBar;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.text.InputFilter;
import android.view.Gravity;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.grupo1.hci.smarthome.Model.APIManager;
import com.grupo1.hci.smarthome.Model.Device;
import com.grupo1.hci.smarthome.Model.Room;
import com.grupo1.hci.smarthome.Model.Rutine;
import com.grupo1.hci.smarthome.R;

import java.util.regex.Pattern;

/**
 * Created by agust on 11/5/2017.
 */

public class EditDialogMessage extends DialogFragment {

    Device device;
    Room room;
    Rutine rutine;
    Boolean isRutine = false;
    Boolean isDevice = false;
    Boolean isRoom = false;
    View view;
    ActionBar ab;
    AlertDialog alertDialog;
    EditText input;


    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the Builder class for convenient dialog construction
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        input = new EditText(getActivity().getApplicationContext());
        input.setHeight(150);
        input.setWidth(340);
        input.setGravity(Gravity.LEFT);
        input.setImeOptions(EditorInfo.IME_ACTION_DONE);
        input.setTextColor(Color.BLACK);
        InputFilter[] filterArray = new InputFilter[1];
        filterArray[0] = new InputFilter.LengthFilter(15);
        input.setFilters(filterArray);
        builder.setView(input);
        builder.setMessage("EDIT")
                .setPositiveButton("EDIT", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        String newName = input.getText().toString().trim();
                        APIManager apiManager = APIManager.getInstance(getActivity());
                        if(!Pattern.matches("^[\\w ]+$", newName) && newName.length() < 3 ){
                            Toast.makeText(getActivity(), "The text blbla", Toast.LENGTH_LONG).show();
                            return;
                        }
                        if(!Pattern.matches("^[\\w ]+$", newName)){
                            Toast.makeText(getActivity(), "The text blbla2", Toast.LENGTH_LONG).show();
                            return;
                        }
                        if(newName.length() < 3 ){
                            Toast.makeText(getActivity(), "The text blbla3", Toast.LENGTH_LONG).show();
                            return;
                        }
                        if(isDevice){
                            if(view != null){
                                TextView tv = view.findViewById(R.id.rowLayout_nameTextView);
                                tv.setText(input.getText().toString());
                                //TODO diselect element
                                apiManager.changeName(device, newName,device.getName(), tv, null);
                            }else{
                                ab.setTitle(input.getText().toString());
                                apiManager.changeName(device, newName, device.getName(),null, ab);
                            }
                            return;
                        }
                        if(isRoom){
                            TextView tv = view.findViewById(R.id.rowLayout_nameTextView);
                            tv.setText(input.getText().toString());
                            apiManager.changeName(room, newName,room.getName(), tv, null);
                            //TODO diselect element
                            return;
                        }
                        if(isRutine){
                            TextView tv = view.findViewById(R.id.rutineRowLayout_nameTextView);
                            tv.setText(input.getText().toString());
                            apiManager.changeName(rutine, newName,rutine.getName(), tv, null);
                            //TODO diselect element
                            return;
                        }

                    }})
                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                    }
                });
        setCancelable(false);
        alertDialog = builder.create();
        // Create the AlertDialog object and return it
        return alertDialog;
    }

    public void setDevice(Device device) {
        this.device = device;
        this.isRoom = false;
        this.isDevice = true;
        this.isRutine = false;
    }

    public void setRoom(Room room) {
        this.room = room;
        this.isRoom = true;
        this.isDevice = false;
        this.isRutine = false;
    }

    public void setRutine(Rutine rutine) {
        this.rutine = rutine;
        this.isRoom = false;
        this.isDevice = false;
        this.isRutine = true;
    }

    public void setView(View view) {
        this.view = view;
    }

    public void setAb(ActionBar ab) {
        this.ab = ab;
    }



}
