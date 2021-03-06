package com.grupo1.hci.smarthome.Activities;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;

import com.grupo1.hci.smarthome.Model.APIManager;
import com.grupo1.hci.smarthome.Model.Constants;
import com.grupo1.hci.smarthome.Model.Device;
import com.grupo1.hci.smarthome.R;

/**
 * Created by agust on 11/5/2017.
 */

public class DeleteDialogMessage extends DialogFragment {

    Device device;
    Activity activity;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the Builder class for convenient dialog construction
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage(R.string.deleteConfirmation)
                .setPositiveButton(R.string.delete, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        APIManager apiManager = APIManager.getInstance(activity);
                        apiManager.deleteDevice(device, activity, Constants.DELTE_FROM_DIALOGE);
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
    }

    public void setActivity(Activity activity) {
        this.activity = activity;
    }
}
