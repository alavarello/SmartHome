package com.grupo1.hci.smarthome.Activities;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Switch;
import android.widget.Toast;

import com.grupo1.hci.smarthome.Model.APIManager;
import com.grupo1.hci.smarthome.Model.Door;
import com.grupo1.hci.smarthome.R;


public class DoorFragment extends Fragment {

    Switch openCloseSwitch;
    Switch lockUnlockSwitch;
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
        APIManager.getInstance(getActivity()).getState(getContext(), door, getActivity(), this);
        // Inflate the layout for this fragment
        return view;
    }


    private void setOnClickListener() {
        openCloseSwitch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(openCloseSwitch.isChecked()){
                    Toast.makeText(getActivity().getApplicationContext(), "CLOSE", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(getActivity().getApplicationContext(), "OPEN", Toast.LENGTH_SHORT).show();
                }
            }
        });
        lockUnlockSwitch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(lockUnlockSwitch.isChecked()){
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

        openCloseSwitch = view.findViewById(R.id.contentDoor_OpenClose_Switch);
        openCloseSwitch.setTextOff(getString(R.string.open));
        openCloseSwitch.setTextOn(getString(R.string.close));
        lockUnlockSwitch = view.findViewById(R.id.contentDoor_LockUnlock_Switch);
        lockUnlockSwitch.setTextOff(getString(R.string.unlock));
        lockUnlockSwitch.setTextOn(getString(R.string.lock));
    }

    public void loadDoorState(Door door) {
        this.door = door;
        if(door.isClosed()){
            openCloseSwitch.setChecked(true);
        }else{
            openCloseSwitch.setChecked(false);
        }
        if(door.isLocked()){
            lockUnlockSwitch.setChecked(true);
        }else{
            lockUnlockSwitch.setChecked(false);
        }
    }


}
