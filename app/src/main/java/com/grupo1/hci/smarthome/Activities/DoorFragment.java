package com.grupo1.hci.smarthome.Activities;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.grupo1.hci.smarthome.Model.Blind;
import com.grupo1.hci.smarthome.Model.Constants;
import com.grupo1.hci.smarthome.Model.Door;
import com.grupo1.hci.smarthome.R;


public class DoorFragment extends Fragment {

    ToggleButton openCloseToggeleButton;
    ToggleButton lockUnlockToggleButton;
    Door door;

    public DoorFragment() {
        // Required empty public constructor
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_door, container, false);
        setView(view);
        setOnClickListener();
        loadDoorState();
        // Inflate the layout for this fragment
        return view;
    }


    private void setOnClickListener() {
        openCloseToggeleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(openCloseToggeleButton.isChecked()){
                    Toast.makeText(getActivity().getApplicationContext(), "CLOSE", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(getActivity().getApplicationContext(), "OPEN", Toast.LENGTH_SHORT).show();
                }
            }
        });
        lockUnlockToggleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(openCloseToggeleButton.isChecked()){
                    Toast.makeText(getActivity().getApplicationContext(), "LOCK", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(getActivity().getApplicationContext(), "UNLOCK", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    private void setView(View view) {

        door = (Door) ((DeviceActivity) getActivity()).getDevice();
        ((DeviceActivity)getActivity()).getSupportActionBar().setTitle(door.getName());

        openCloseToggeleButton = view.findViewById(R.id.contentDoor_OpenClose_ToogleButton);
        openCloseToggeleButton.setTextOff(getString(R.string.open));
        openCloseToggeleButton.setTextOn(getString(R.string.close));
        lockUnlockToggleButton = view.findViewById(R.id.contentDoor_LockUnlock_ToggleButton);
        lockUnlockToggleButton.setTextOff(getString(R.string.unlock));
        lockUnlockToggleButton.setTextOn(getString(R.string.lock));
    }

    private void loadDoorState() {
        if(door.isClosed()){
            openCloseToggeleButton.setChecked(true);
        }else{
            openCloseToggeleButton.setChecked(false);
        }
        if(door.isLocked()){
            lockUnlockToggleButton.setChecked(true);
        }else{
            lockUnlockToggleButton.setChecked(false);
        }
    }


}
