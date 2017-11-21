package com.grupo1.hci.smarthome.Activities;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SeekBar;
import android.widget.Switch;
import android.widget.Toast;

import com.grupo1.hci.smarthome.Model.APIManager;
import com.grupo1.hci.smarthome.Model.Lamp;
import com.grupo1.hci.smarthome.R;


public class LampFragment extends Fragment {

    Switch onOffToggleButton;
    Lamp lamp;
    SeekBar seekBar;

    public LampFragment() {
        // Required empty public constructor
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_lamp, container, false);
        setView(view);
        setOnClickListener();
        APIManager apiManager = APIManager.getInstance(getActivity());
        apiManager.getState(getContext(), lamp, getActivity(), this);
        // Inflate the layout for this fragment
        return view;
    }


    private void setOnClickListener() {
        onOffToggleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(onOffToggleButton.isChecked()){
                    Toast.makeText(getActivity().getApplicationContext(), "OFF", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(getActivity().getApplicationContext(), "ON", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void setView(View view) {

        lamp = (Lamp) ((DeviceActivity) getActivity()).getDevice();
        ((DeviceActivity)getActivity()).getSupportActionBar().setTitle(lamp.getName());

        onOffToggleButton = view.findViewById(R.id.contentLamp_OnOff_switch);
        seekBar = view.findViewById(R.id.contentLamp_dimmerSeekBar);
        onOffToggleButton.setTextOff(getString(R.string.off));
        onOffToggleButton.setTextOn(getString(R.string.on));
    }

    public void loadLampState(Lamp lamp) {
        this.lamp = lamp;
       if(lamp.isOn()){
            onOffToggleButton.setChecked(true);
        }else{
            onOffToggleButton.setChecked(false);
        }
        seekBar.setProgress(lamp.getBrightness());
       //TODO color of the lamp

    }


}
