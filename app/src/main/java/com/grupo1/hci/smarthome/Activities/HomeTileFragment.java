package com.grupo1.hci.smarthome.Activities;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.view.ActionMode;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.Toast;

import com.grupo1.hci.smarthome.Model.Constants;
import com.grupo1.hci.smarthome.Model.Room;
import com.grupo1.hci.smarthome.R;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by agust on 11/15/2017.
 */

public class HomeTileFragment extends Fragment implements HomeFragment  {

    ArrayList<Room> roomsArray = new ArrayList<>();
    ArrayList<View> selectedElement = new ArrayList<>();
    Toolbar toolbar;
    ActionMode mActionMode;
    GridView gridView;
    Snackbar mySnackbar;
    private CountDownTimer deleteCountDown;
    ActionMode.Callback mActionModeCallback;

    public void setmActionMode(ActionMode mActionMode) {
        this.mActionMode = mActionMode;
    }


    @Override
    public void diselectElement(View view) {
        view.setBackgroundColor(Color.TRANSPARENT);
        selectedElement.remove(view);
        if(selectedElement.size() == 1){
            ((HomeContextualMenu)mActionModeCallback).changeToOneItemsMenu();
        }
        if(selectedElement.isEmpty()){
            mActionMode.finish();
        }
    }

    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list, container, false);
        roomsArray = ((NavigationActivity) getActivity()).getRoomsArray();
        toolbar = ((NavigationActivity) getActivity()).getToolbar();
        gridView = view.findViewById(R.id.gridView);
        setView(view);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Object o = adapterView.getAdapter().getItem(i);
                Room room = (Room) o;//As you are using Default String Adapter
                Toast.makeText(getActivity().getApplicationContext(), room.getName() + " ShortClick", Toast.LENGTH_SHORT).show();
                //if is the same view as the selected one
                if (mActionMode == null) {
                    diselectElements();
                    Intent intent = new Intent(getActivity().getApplicationContext(), RoomActivity.class);
                    intent.putExtra(Constants.ROOM_ARRAY_INTENT, (Serializable) roomsArray);
                    intent.putExtra(Constants.ROOM_INTENT, (Serializable) room);
                    startActivity(intent);
                }else{
                    if(selectedElement.contains(view)){
                        diselectElement(view);
                    }else{
                        selectedElement(view, room);
                        ((HomeContextualMenu)mActionModeCallback).changeToSeveralItemsMenu();
                    }
                }
            }
        });
        gridView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                Object o = adapterView.getAdapter().getItem(i);
                Room room = (Room) o;//As you are using Default String Adapter

                Toast.makeText(getActivity().getApplicationContext(), room.getName() + " LongClick", Toast.LENGTH_SHORT).show();
                selectedElement(view, room);
                if (mActionMode != null) {
                    //selected severla items
                    ((HomeContextualMenu)mActionModeCallback).changeToSeveralItemsMenu();
                    return false;
                }
                // Start the CAB using the ActionMode.Callback defined above
                ((HomeContextualMenu) mActionModeCallback).addRoom(room);
                mActionMode = getActivity().startActionMode(mActionModeCallback);
                return true;
            }
        });
    }

    private void setView(View view) {
        //set the contextual floating menu
        mActionModeCallback = new HomeContextualMenu();
        ((HomeContextualMenu) mActionModeCallback).setHomeActivity((HomeActivity) getActivity());
        //set listview Adapter and onCikcListener
        ArrayAdapter rowAdapter = new HomeAdapter((HomeActivity) getActivity(), roomsArray, (HomeFragment) this);
        gridView.setAdapter(rowAdapter);
    }

    public void deleteRoom(String roomId) {
        //setting the snackbar
        mySnackbar = Snackbar.make(getActivity().findViewById(R.id.homeActivity_Fragmentcontainer), "Deleted", Snackbar.LENGTH_LONG);
        mySnackbar.setAction("Undo", new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                deleteCountDown.cancel();
            }
        });

        mySnackbar.show();
        deleteCountDown = new CountDownTimer(4000, 1000) {
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
        selectedElement.add(view);
        toolbar.setTitle(room.getName());
    }

    public void diselectElements() {
       for(View v: selectedElement){
            v.setBackgroundColor(Color.TRANSPARENT);
        }
        toolbar.setTitle(Constants.appName);
    }
}