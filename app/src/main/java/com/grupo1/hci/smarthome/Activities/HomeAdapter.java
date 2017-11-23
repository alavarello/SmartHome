package com.grupo1.hci.smarthome.Activities;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.PopupMenu;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.grupo1.hci.smarthome.Model.Room;
import com.grupo1.hci.smarthome.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;

/**
 * Created by agust on 10/30/2017.
 */

public class HomeAdapter extends ArrayAdapter<Room> {

    ArrayList<Room> names;
    Context context;
    HomeFragment homeFragment;
    HomeActivity homeActivity;

    public HomeAdapter(Context context, ArrayList<Room> names, HomeFragment homeFragment, HomeActivity homeActivity) {
        super(context, 0, names);
        this.context = context;
        this.names = names;
        this.homeFragment = homeFragment;
        this.homeActivity = homeActivity;
    }


    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
       final Room room = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.row_layout, parent, false);
        }
        final  View finalView = convertView;
        // Lookup view for data population
        TextView roomName = (TextView) convertView.findViewById(R.id.rowLayout_nameTextView);
        ImageButton overflowMenu = convertView.findViewById(R.id.rowLayout_overflowMenu_imageButton);
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
                                EditDialogMessage editDeviceMessage = new EditDialogMessage();
                                editDeviceMessage.setRoom(room);
                                editDeviceMessage.setView(finalView);
                                editDeviceMessage.show(homeActivity.getFragmentManager(), "editDevice");
                                break;
                            case R.id.deleteElement:
                                HashMap<Room,Integer> hashMap = new HashMap();
                                hashMap.put(room, position);
                                homeFragment.deleteRooms(hashMap);
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
        roomName.setText(room.getName());
        roomName.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        // Return the completed view to render on screen
        return convertView;
    }

    @Nullable
    @Override
    public Room getItem(int position) {
        return names.get(position);
    }

}
