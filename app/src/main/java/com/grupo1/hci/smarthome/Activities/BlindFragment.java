package com.grupo1.hci.smarthome.Activities;

import android.support.v4.app.Fragment;
import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.grupo1.hci.smarthome.Model.APIManager;
import com.grupo1.hci.smarthome.Model.Blind;
import com.grupo1.hci.smarthome.Model.Constants;
import com.grupo1.hci.smarthome.R;


public class BlindFragment extends Fragment {

    Switch openCloseToggleSwitch;
    APIManager apiManager;
    Blind blind;

    public BlindFragment() {
        // Required empty public constructor
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_blind, container, false);
        setView(view);
        setOnClickListener();
        apiManager = APIManager.getInstance(getActivity());
        apiManager.getState(getContext(), blind, getActivity(), this);
        // Inflate the layout for this fragment
        return view;
    }


    private void setOnClickListener() {
        openCloseToggleSwitch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Switch onOffToogleButton = (Switch) view;
                if(onOffToogleButton.isChecked()){
                   apiManager.blindDown(getActivity(), blind, openCloseToggleSwitch);

                }else{
                    apiManager.blindUp(getActivity(), blind,openCloseToggleSwitch);

                }
            }
        });

    }

    private void setView(View view) {

        blind = (Blind) ((DeviceActivity) getActivity()).getDevice();
        ((DeviceActivity)getActivity()).getSupportActionBar().setTitle(blind.getName());

        openCloseToggleSwitch = view.findViewById(R.id.contentBlind_OnOff_ToggleButton);
        openCloseToggleSwitch.setTextOff(getString(R.string.close));
        openCloseToggleSwitch.setTextOn(getString(R.string.open));

    }

    public void loadBlindState(Blind blind){
        this.blind = blind;
        String status = blind.getStatus();
        if(status.equals(Constants.BLIND_STATE_OPENED)){
            openCloseToggleSwitch.setChecked(false);

        }else if(status.equals(Constants.BLIND_STATE_CLOSED)){
            openCloseToggleSwitch.setChecked(true);

        }else if(status.equals(Constants.BLIND_STATE_CLOSING)){
            openCloseToggleSwitch.setChecked(true);

        }else if(status.equals(Constants.BLIND_STATE_OPENING)){
            openCloseToggleSwitch.setChecked(false);

        }
    }


}
