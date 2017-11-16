package com.grupo1.hci.smarthome.Activities;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ListFragment;
import android.support.v7.widget.Toolbar;
import android.view.ActionMode;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.grupo1.hci.smarthome.Model.Constants;
import com.grupo1.hci.smarthome.Model.Room;
import com.grupo1.hci.smarthome.R;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by agust on 11/15/2017.
 */

public class HomeListFragment extends ListFragment {

    ArrayList<Room> roomsArray = new ArrayList<>();
    View selectedElement;
    ListView listView;
    Toolbar toolbar;
    ActionMode mActionMode;
    Snackbar mySnackbar;
    private CountDownTimer deleteCountDown;
    ActionMode.Callback mActionModeCallback;

    public void setmActionMode(ActionMode mActionMode) {
        this.mActionMode = mActionMode;
    }

    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list, container, false);
        roomsArray = ((NavigationActivity)getActivity()).getRoomsArray();
        setView(view);
        setOnClickListener();
        return view;
    }


    private void setView(View view) {
        listView = (ListView) view.findViewById(R.id.contentRoom_ListView);
        //set the contextual floating menu
        mActionModeCallback = new HomeContextualMenu();
        ((HomeContextualMenu) mActionModeCallback).setHomeActivity((HomeActivity) getActivity());
        //set listview Adapter and onCikcListener
        ArrayAdapter rowAdapter = new HomeAdapter((HomeActivity)getActivity(), roomsArray);
        listView.setAdapter(rowAdapter);
    }

    private void setOnClickListener() {
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View view, int position, long arg3) {
                Object o = arg0.getAdapter().getItem(position);
                Room room = (Room) o;//As you are using Default String Adapter
                Toast.makeText(getActivity().getApplicationContext(), room.getName() + " ShortClick", Toast.LENGTH_SHORT).show();
                //if is the same view as the selected one
                if (mActionMode == null) {
                    diselectElement();
                    Intent intent = new Intent(getActivity().getApplicationContext(), RoomActivity.class);
                    intent.putExtra(Constants.ROOM_ARRAY_INTENT, (Serializable) roomsArray);
                    intent.putExtra(Constants.ROOM_INTENT, (Serializable) room);
                    startActivity(intent);
                }
            }
        });

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> arg0, View view, int position, long id) {
                Object o = arg0.getAdapter().getItem(position);
                Room room = (Room) o;//As you are using Default String Adapter

                Toast.makeText(getActivity().getApplicationContext(), room.getName() + " LongClick", Toast.LENGTH_SHORT).show();
                if (mActionMode != null) {
                    return false;
                }
                // Start the CAB using the ActionMode.Callback defined above
                ((HomeContextualMenu) mActionModeCallback).setRoom(room);
                mActionMode = getActivity().startActionMode(mActionModeCallback);
                selectedElement(view, room);

                return true;
            }
        });

    }



    public void deleteRoom(String roomId)
    {
        //setting the snackbar
        mySnackbar = Snackbar.make(getActivity().findViewById(R.id.contentRoom_ListView), "Deleted", Snackbar.LENGTH_LONG);
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
                Toast.makeText(getActivity().getApplicationContext(), "Se borro!!!!!", Toast.LENGTH_SHORT).show();
            }
        }.start();
    }

    public void selectedElement(View view, Room room) {
        view.setBackgroundColor(Color.GRAY);
        view.findViewById(R.id.rowLayout_iconImageView).setBackground(null);
        selectedElement =view;
        toolbar.setTitle(room.getName());
    }

    public void diselectElement() {
        if(selectedElement != null){
            selectedElement.setBackgroundColor(Color.TRANSPARENT);
            toolbar.setTitle(Constants.appName);
        }
    }

}