package com.grupo1.hci.smarthome.Activities;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.grupo1.hci.smarthome.Model.APIManager;
import com.grupo1.hci.smarthome.Model.Constants;
import com.grupo1.hci.smarthome.Model.Lamp;
import com.grupo1.hci.smarthome.R;


public class LampFragment extends Fragment {

    Switch onOffSwitch;
    Lamp lamp;
    SeekBar seekBar;
    APIManager apiManager;
    Button greenButton;
    Button redButton;
    Button blueButton;
    Button yellowButton;
    TextView orange;
    TextView blue;
    TextView red;
    TextView green;
    TextView selected;


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
        onOffSwitch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(onOffSwitch.isChecked()){
                    apiManager.deviceOnOff(getActivity(), lamp,"turnOn", onOffSwitch);
                }else{
                    apiManager.deviceOnOff(getActivity(), lamp,"turnOff", onOffSwitch);
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
        yellowButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                apiManager.lampColorChange(getActivity(),lamp, Constants.COLOR_YELLOW, orange, selected);
                selected= orange;
                orange.setBackgroundColor(Color.GRAY);
            }
        });
        redButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                apiManager.lampColorChange(getActivity(),lamp, Constants.COLOR_RED, red, selected);
                selected= red;
                red.setBackgroundColor(Color.GRAY);
            }
        });
        greenButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                apiManager.lampColorChange(getActivity(),lamp, Constants.COLOR_GREEN, green, selected);
                selected= green;
                blue.setBackgroundColor(Color.GRAY);
            }
        });
        blueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                apiManager.lampColorChange(getActivity(),lamp, Constants.COLOR_BLUE, blue, selected);
                selected= blue;
                blue.setBackgroundColor(Color.GRAY);

            }
        });

    }

    private void setView(View view) {

        lamp = (Lamp) ((SuportDeviceActivity) getActivity()).getDevice();
        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle(lamp.getName());

        onOffSwitch = view.findViewById(R.id.contentLamp_OnOff_switch);
        seekBar = view.findViewById(R.id.contentLamp_dimmerSeekBar);
        onOffSwitch.setTextOff(getString(R.string.off));
        onOffSwitch.setTextOn(getString(R.string.on));
        greenButton = view.findViewById(R.id.contentLamp_greenButton);
        yellowButton = view.findViewById(R.id.contentLamp_yellowButton);
        blueButton = view.findViewById(R.id.contentLamp_blueButton);
        redButton = view.findViewById(R.id.contentLamp_redButton);
        orange = view.findViewById(R.id.orange_color);
        blue = view.findViewById(R.id.blue_color);
        red = view.findViewById(R.id.red_color);
        green = view.findViewById(R.id.green_color);

    }

    public void loadLampState(Lamp lamp) {
        this.lamp = lamp;
        if(lamp.isOn()){
            onOffSwitch.setChecked(true);
        }else{
            onOffSwitch.setChecked(false);
        }
        seekBar.setProgress(lamp.getBrightness());

        if(lamp.getColor().equals(Constants.COLOR_BLUE)){
            selected= blue;
            blue.setBackgroundColor(Color.GRAY);
        }else if(lamp.getColor().equals(Constants.COLOR_GREEN)){
            green.setBackgroundColor(Color.GRAY);
            selected= green;
        }else if(lamp.getColor().equals(Constants.COLOR_RED)){
            red.setBackgroundColor(Color.GRAY);
            selected= red;
        }else {
            orange.setBackgroundColor(Color.GRAY);
            selected= orange;
        }

    }


}