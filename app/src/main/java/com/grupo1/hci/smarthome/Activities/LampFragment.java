package com.grupo1.hci.smarthome.Activities;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.Switch;
import android.widget.Toast;

import com.grupo1.hci.smarthome.Model.APIManager;
import com.grupo1.hci.smarthome.Model.Constants;
import com.grupo1.hci.smarthome.Model.Lamp;
import com.grupo1.hci.smarthome.R;


public class LampFragment extends Fragment {

    Switch onOffToggleButton;
    Lamp lamp;
    SeekBar seekBar;
    APIManager apiManager;
    Button greenButton;
    Button redButton;
    Button blueButton;
    Button yellowButton;

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
        apiManager = APIManager.getInstance(getActivity());
        apiManager.getState(getContext(), lamp, getActivity(), this);
        // Inflate the layout for this fragment
        return view;
    }


    private void setOnClickListener() {
        onOffToggleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(onOffToggleButton.isChecked()){
                    apiManager.deviceOnOff(getActivity(), lamp,"turnOff", onOffToggleButton);
                }else{
                    apiManager.deviceOnOff(getActivity(), lamp,"turnOn", onOffToggleButton);
                }
            }
        });
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                apiManager.changeLampBrightness(getActivity(),lamp,i,seekBar);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        //TODO add final fragment to apimanager changeColor
        yellowButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                apiManager.lampColorChange(getActivity(),lamp, Constants.COLOR_YELLOW);
            }
        });
        redButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                apiManager.lampColorChange(getActivity(),lamp, Constants.COLOR_RED);
            }
        });
        greenButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                apiManager.lampColorChange(getActivity(),lamp, Constants.COLOR_GREEN);
            }
        });
        blueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                apiManager.lampColorChange(getActivity(),lamp, Constants.COLOR_BLUE);
            }
        });
        
    }

    private void setView(View view) {

        lamp = (Lamp) ((SuportDeviceActivity) getActivity()).getDevice();
        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle(lamp.getName());

        onOffToggleButton = view.findViewById(R.id.contentLamp_OnOff_switch);
        seekBar = view.findViewById(R.id.contentLamp_dimmerSeekBar);
        onOffToggleButton.setTextOff(getString(R.string.off));
        onOffToggleButton.setTextOn(getString(R.string.on));
        greenButton = view.findViewById(R.id.contentLamp_greenButton);
        yellowButton = view.findViewById(R.id.contentLamp_yellowButton);
        blueButton = view.findViewById(R.id.contentLamp_blueButton);
        redButton = view.findViewById(R.id.contentLamp_redButton);

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
