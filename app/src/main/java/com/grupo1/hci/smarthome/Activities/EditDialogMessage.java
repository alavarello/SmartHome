package com.grupo1.hci.smarthome.Activities;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.grupo1.hci.smarthome.Model.Device;
import com.grupo1.hci.smarthome.Model.Room;
import com.grupo1.hci.smarthome.Model.Rutine;
import com.grupo1.hci.smarthome.R;

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

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the Builder class for convenient dialog construction
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        final EditText input = new EditText(getActivity().getApplicationContext());
        input.setHeight(150);
        input.setWidth(340);
        input.setGravity(Gravity.LEFT);
        input.setImeOptions(EditorInfo.IME_ACTION_DONE);
        input.setTextColor(Color.BLACK);
        builder.setView(input);
        builder.setMessage("EDIT")
                .setPositiveButton("EDIT", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        if(isDevice){
                            Toast.makeText(getActivity().getApplicationContext(), "Se edito el disp "+ device.getName() , Toast.LENGTH_SHORT).show();
                            return;
                        }
                        if(isRoom){
                            Toast.makeText(getActivity().getApplicationContext(), "Se edito el cuarto "+ room.getName() , Toast.LENGTH_SHORT).show();
                            return;
                        }
                        if(isRutine){
                            Toast.makeText(getActivity().getApplicationContext(), "Se edito la rutina "+ rutine.getName() , Toast.LENGTH_SHORT).show();

                            return;
                        }

                    }
                })
                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                    }
                });
        // Create the AlertDialog object and return it
        return builder.create();
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
}
