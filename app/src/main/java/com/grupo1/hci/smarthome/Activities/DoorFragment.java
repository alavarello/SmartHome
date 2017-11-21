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
    APIManager apiManager;

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
        apiManager = APIManager.getInstance(getActivity());
        apiManager.getState(getContext(), door, getActivity(), this);
        // Inflate the layout for this fragment
        return view;
    }


    private void setOnClickListener() {
        openCloseSwitch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(openCloseSwitch.isChecked()){
                    apiManager.openDoor(getActivity(),door,openCloseSwitch);
                }else{
                    apiManager.closeDoor(getActivity(),door,openCloseSwitch);
                }
            }
        });
        lockUnlockSwitch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(lockUnlockSwitch.isChecked()){
                    apiManager.lockDoor(getActivity(),door,lockUnlockSwitch);
                }else{
                    apiManager.unlockDoor(getActivity(),door,lockUnlockSwitch);
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
            openCloseSwitch.setChecked(false);
        }else{
            openCloseSwitch.setChecked(true);
        }
        if(door.isLocked()){
            lockUnlockSwitch.setChecked(true);
        }else{
            lockUnlockSwitch.setChecked(false);
        }
    }


}
