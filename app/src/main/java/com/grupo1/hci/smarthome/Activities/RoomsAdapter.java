package com.grupo1.hci.smarthome.Activities;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.Nullable;
import android.support.v7.widget.PopupMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.grupo1.hci.smarthome.Model.Constants;
import com.grupo1.hci.smarthome.Model.Device;
import com.grupo1.hci.smarthome.Model.Room;
import com.grupo1.hci.smarthome.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;

/**
 * Created by agust on 10/30/2017.
 */

public class RoomsAdapter extends ArrayAdapter<Device> {

    ArrayList<Device> names;
    Context context;
    RoomsFragment roomsFragment;
    RoomActivity roomActivity;
    public RoomsAdapter(Context context, ArrayList<Device> names, RoomsFragment roomsFragment, RoomActivity roomActivity) {
        super(context, 0, names);
        this.names = names;
        this.context = context;
        this.roomsFragment = roomsFragment;
        this.roomActivity = roomActivity;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
       final Device device = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.row_layout, parent, false);
        }
        // Lookup view for data population
        TextView deviceName = (TextView) convertView.findViewById(R.id.rowLayout_nameTextView);
        final ImageButton overflowMenu = convertView.findViewById(R.id.rowLayout_overflowMenu_imageButton);
        overflowMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PopupMenu popup = new PopupMenu(context, view);
                popup.inflate(R.menu.one_item_contextual_menu);
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.editElement:
                                Toast.makeText(context, "Se edito!!!!!", Toast.LENGTH_SHORT).show();
                                EditDialogMessage editDeviceMessage = new EditDialogMessage();
                                editDeviceMessage.setDevice(device);
                                editDeviceMessage.show(roomActivity.getFragmentManager(), "editDevice");
                                break;
                            case R.id.deleteElement:
                                Toast.makeText(context, "Se borro!!!!!" +device.getId(), Toast.LENGTH_SHORT).show();
                                HashMap<Device,Integer> hashMap = new HashMap<>();
                                hashMap.put(device, position);

                                roomsFragment.deleteDevices(hashMap);
                                break;
                        }
                        return true;
                    }
                });
                popup.show();
            }
        });
        overflowMenu.setFocusable(false);
        // Populate the data into the template view using the data object
        deviceName.setText(device.getName());

        ImageView imageView = convertView.findViewById(R.id.rowLayout_iconImageView);
        switch (device.getTypeId()){
            case Constants.LAMP_ID:
                imageView.setImageResource(R.drawable.ic_lamp);
                break;
            case Constants.DOOR_ID:
                imageView.setImageResource(R.drawable.ic_door);
                break;
            case Constants.BLIND_ID:
                //TODO icon blind
                imageView.setImageResource(R.drawable.ic_lamp);
                break;
            case Constants.OVEN_ID:
                imageView.setImageResource(R.drawable.ic_oven);
                break;

        }
        // Return the completed view to render on screen
        return convertView;
    }



    @Nullable
    @Override
    public Device getItem(int position) {
        return names.get(position);
    }
}
