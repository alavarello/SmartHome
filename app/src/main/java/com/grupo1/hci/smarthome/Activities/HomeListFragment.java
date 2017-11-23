package com.grupo1.hci.smarthome.Activities;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ListFragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.Toolbar;
import android.view.ActionMode;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.grupo1.hci.smarthome.Model.APIManager;
import com.grupo1.hci.smarthome.Model.Constants;
import com.grupo1.hci.smarthome.Model.Device;
import com.grupo1.hci.smarthome.Model.Room;
import com.grupo1.hci.smarthome.R;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by agust on 11/15/2017.
 */

public class HomeListFragment extends ListFragment implements AdapterView.OnItemClickListener, AdapterView.OnItemLongClickListener, HomeFragment, SwipeRefreshLayout.OnRefreshListener {

    ArrayList<Room> roomsArray = new ArrayList<>();
    ArrayList<View> selectedElement = new ArrayList<>();
    Toolbar toolbar;
    ActionMode mActionMode;
    Snackbar mySnackbar;
    private CountDownTimer deleteCountDown;
    ActionMode.Callback mActionModeCallback;
    ArrayAdapter rowAdapter;
    TextView emptyTextView;
    View view;
    APIManager apiManager;
    SwipeRefreshLayout swipeRefreshLayout;


    public void setmActionMode(ActionMode mActionMode) {
        this.mActionMode = mActionMode;
    }

    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_list, container, false);
        toolbar = ((NavigationActivity) getActivity()).getToolbar();
        return view;
    }

    private void setOnClickListeners() {

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        apiManager = APIManager.getInstance(getActivity());
        apiManager.getRooms(getActivity(), null);
        if(getResources().getConfiguration().orientation ==  getResources().getConfiguration().ORIENTATION_PORTRAIT){
            setView(view);
            setOnClickListeners();
            getListView().setOnItemClickListener(this);
            getListView().setOnItemLongClickListener(this);
        }

    }

    private void setView(View view) {
        //set the contextual floating menu
        mActionModeCallback = new HomeContextualMenu();
        ((HomeContextualMenu) mActionModeCallback).setHomeActivity((HomeActivity) getActivity());
        //set listview Adapter and onCikcListener
        rowAdapter = new HomeAdapter((HomeActivity) getActivity(), roomsArray, (HomeFragment) this,(HomeActivity) getActivity());
        setListAdapter(rowAdapter);
       swipeRefreshLayout = view.findViewById(R.id.fragmentList_refreshLayout);
       swipeRefreshLayout.setOnRefreshListener(this);

    }

    public void deleteRooms(final HashMap<Room, Integer> rooms) {
        //setting the snackbar
        mySnackbar = Snackbar.make(getActivity().findViewById(R.id.homeActivity_Fragmentcontainer), "Deleted", Snackbar.LENGTH_LONG);
        mySnackbar.setAction("Undo", new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                for(Room r: rooms.keySet()){
                    if(rooms.get(r) < roomsArray.size()){
                        roomsArray.add(rooms.get(r), r);
                    }else{
                        roomsArray.add(r);
                    }
                }
                loadRomms(roomsArray);
                deleteCountDown.cancel();
            }
        });

        mySnackbar.show();
        roomsArray.removeAll(rooms.keySet());
        loadRomms(roomsArray);
        diselectElements();
        if(mActionMode != null){
            mActionMode.finish();
        }
        deleteCountDown = new CountDownTimer(4000, 1000) {
            @Override
            public void onTick(long l) {

            }

            @Override
            public void onFinish() {

                APIManager apiManager = APIManager.getInstance(getActivity());
                for(Room r: rooms.keySet()){
                    apiManager.deleteRoom(r, getActivity());
                }
            }
        }.start();
    }

    @Override
    public void loadRomms(List<Room> rooms) {
        roomsArray = (ArrayList<Room>) rooms;
        rowAdapter.clear();
        rowAdapter.addAll(rooms);
        rowAdapter.notifyDataSetChanged();
//        emptyTextView = getActivity().findViewById(R.id.fragmentList_emptyListTextView);
//        if(roomsArray.isEmpty()){
//            emptyTextView.setVisibility(View.VISIBLE);
//        }else{
//            emptyTextView.setVisibility(View.GONE);
//        }
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
        selectedElement.clear();
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

    @Override
    public void roomDeleteError(Room room) {
        roomsArray.add(room);
        loadRomms(roomsArray);
    }

    @Override
    public ArrayList<View> getSelectedElements() {
        return selectedElement;
    }


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
                ((HomeContextualMenu) mActionModeCallback).removeRoom(room);
            }else{
                selectedElement(view, room);
                ((HomeContextualMenu)mActionModeCallback).changeToSeveralItemsMenu();
                ((HomeContextualMenu) mActionModeCallback).addRoom(room,i);
            }

        }
    }

    @Override
    public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
        Object o = adapterView.getAdapter().getItem(i);
        Room room = (Room) o;//As you are using Default String Adapter

        Toast.makeText(getActivity().getApplicationContext(), room.getName() + " LongClick", Toast.LENGTH_SHORT).show();
        selectedElement(view, room);
        if (mActionMode != null) {
            if (selectedElement.contains(view)) {
                diselectElement(view);
                ((HomeContextualMenu) mActionModeCallback).removeRoom(room);
            } else {
                ((HomeContextualMenu) mActionModeCallback).changeToSeveralItemsMenu();
                ((HomeContextualMenu) mActionModeCallback).addRoom(room, i);
                selectedElement(view, room);
            }
            return false;
        }
        // Start the CAB using the ActionMode.Callback defined above
        ((HomeContextualMenu) mActionModeCallback).addRoom(room, i);
        mActionMode = getActivity().startActionMode(mActionModeCallback);
        return true;
    }


    @Override
    public void onRefresh() {
        apiManager.getRooms(getActivity(), swipeRefreshLayout);
    }
}