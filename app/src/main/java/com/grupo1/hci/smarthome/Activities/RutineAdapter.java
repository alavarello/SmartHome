package com.grupo1.hci.smarthome.Activities;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.PopupMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.grupo1.hci.smarthome.Model.APIManager;
import com.grupo1.hci.smarthome.Model.Device;
import com.grupo1.hci.smarthome.Model.Rutine;
import com.grupo1.hci.smarthome.Notifications.ApiService;
import com.grupo1.hci.smarthome.R;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by agust on 10/30/2017.
 */

public class RutineAdapter extends ArrayAdapter<Rutine> {

    ArrayList<Rutine> names;
    Context context;
    RutinesListFragment rutinesListFragment;

    public RutineAdapter(Context context, ArrayList<Rutine> names, RutinesListFragment rutineFragment) {
        super(context, 0, names);
        this.names = names;
        this.context = context;
        this.rutinesListFragment = rutineFragment;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
       final Rutine rutine = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.rutine_row_layout, parent, false);
        }
        final View finalView = convertView;
        // Lookup view for data population
        TextView deviceName = (TextView) convertView.findViewById(R.id.rutineRowLayout_nameTextView);
        Button actionButton = convertView.findViewById(R.id.rutineRowLayout_actionButton);
        actionButton.setFocusable(false);
        actionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                APIManager.getInstance(rutinesListFragment.getActivity()).executeRutine(rutinesListFragment.getActivity(),rutine);
            }
        });
        // Populate the data into the template view using the data object
        deviceName.setText(rutine.getName());
        final ImageButton overflowMenu = convertView.findViewById(R.id.rutineRowLayout_overflowMenu_ImageButton);
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
                                editDeviceMessage.setRutine(rutine);
                                editDeviceMessage.setView(finalView);
                                editDeviceMessage.show(rutinesListFragment.getActivity().getFragmentManager(), "editRoutine");
                                break;
                            case R.id.deleteElement:
                                HashMap<Rutine, Integer> hashMap = new HashMap<>();
                                hashMap.put(rutine, position);
                                rutinesListFragment.deleteRutines(hashMap);
                        }
                        return true;
                    }
                });
                popup.show();
            }
        });
        overflowMenu.setFocusable(false);
        // Return the completed view to render on screen
        return convertView;
    }

    @Nullable
    @Override
    public Rutine getItem(int position) {
        return names.get(position);
    }
}
