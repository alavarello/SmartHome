package com.grupo1.hci.smarthome.Activities;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.grupo1.hci.smarthome.Model.Blind;
import com.grupo1.hci.smarthome.Model.Constants;
import com.grupo1.hci.smarthome.R;


public class BlindFragment extends Fragment {

    ToggleButton openCloseToggleButton;
    TextView stateTextView;
    Blind blind;

    public BlindFragment() {
        // Required empty public constructor
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        setView();
        setOnClickListener();
        loadBlindState();
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_blind, container, false);
    }


    private void setOnClickListener() {
        openCloseToggleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ToggleButton onOffToogleButton = (ToggleButton) view;
                if(onOffToogleButton.isChecked()){
                    Toast.makeText(getActivity().getApplicationContext(), "CERRADO", Toast.LENGTH_SHORT).show();
                    stateTextView.setText(Constants.BLIND_STATE_CLOSING);
                }else{
                    Toast.makeText(getActivity().getApplicationContext(), "ABIERTO", Toast.LENGTH_SHORT).show();
                    stateTextView.setText(Constants.BLIND_STATE_OPENING);
                }
            }
        });

    }

    private void setView() {

        blind = (Blind) ((DeviceActivity) getActivity()).getDevice();
        ((DeviceActivity)getActivity()).getSupportActionBar().setTitle(blind.getName());

        openCloseToggleButton = getActivity().findViewById(R.id.contentBlind_OnOff_ToggleButton);
        openCloseToggleButton.setTextOff(getString(R.string.close));
        openCloseToggleButton.setTextOn(getString(R.string.open));
        stateTextView = getActivity().findViewById(R.id.contentBlind_State_TextView);
    }

    private void loadBlindState(){
        String status = blind.getStatus();
        if(status.equals(Constants.BLIND_STATE_OPENED)){
            openCloseToggleButton.setChecked(true);
            stateTextView.setText(R.string.opened);
        }else if(status.equals(Constants.BLIND_STATE_CLOSED)){
            openCloseToggleButton.setChecked(false);
            stateTextView.setText(R.string.closed);
        }else if(status.equals(Constants.BLIND_STATE_CLOSING)){
            openCloseToggleButton.setChecked(false);
            stateTextView.setText(R.string.closing);
        }else if(status.equals(Constants.BLIND_STATE_OPENING)){
            openCloseToggleButton.setChecked(true);
            stateTextView.setText(R.string.opening);
        }
    }


}
