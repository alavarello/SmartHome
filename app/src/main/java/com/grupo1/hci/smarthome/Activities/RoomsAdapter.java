package com.grupo1.hci.smarthome.Activities;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.grupo1.hci.smarthome.Model.Device;
import com.grupo1.hci.smarthome.Model.Room;
import com.grupo1.hci.smarthome.R;

import java.util.ArrayList;

/**
 * Created by agust on 10/30/2017.
 */

public class RoomsAdapter extends ArrayAdapter<Device> {

    ArrayList<Device> names;
    public RoomsAdapter(Context context, ArrayList<Device> names) {
        super(context, 0, names);
        this.names = names;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
       Device device = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.row_layout, parent, false);
        }
        // Lookup view for data population
        TextView deviceName = (TextView) convertView.findViewById(R.id.rowLayout_nameTextView);
        // Populate the data into the template view using the data object
        deviceName.setText(device.getName());
        // Return the completed view to render on screen
        return convertView;
    }

    @Nullable
    @Override
    public Device getItem(int position) {
        return names.get(position);
    }
}
