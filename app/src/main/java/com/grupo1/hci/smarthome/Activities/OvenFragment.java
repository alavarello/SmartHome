package com.grupo1.hci.smarthome.Activities;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.grupo1.hci.smarthome.Model.Oven;
import com.grupo1.hci.smarthome.R;


public class OvenFragment extends Fragment {

    ToggleButton openCloseToggleButton;
    TextView stateTextView;
    Oven oven;
    Spinner heatSpinner;
    Spinner grillSpinner;
    Spinner convectionSpinner;
    EditText temperatureEditText;
    ToggleButton onOffToggleButton;

    public OvenFragment() {
        // Required empty public constructor
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_oven, container, false);
        setView(view);
        setOnClickListener();
        loadOvenState();
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
        temperatureEditText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                Toast.makeText(getActivity().getApplicationContext(), textView.getText(), Toast.LENGTH_SHORT).show();
                return false;
            }
        });
    }

    private void setView(View view) {

        oven = (Oven) ((DeviceActivity) getActivity()).getDevice();
        ((DeviceActivity)getActivity()).getSupportActionBar().setTitle(oven.getName());

        onOffToggleButton = view.findViewById(R.id.contentOven_Onoff_ToogleButton);
        heatSpinner = view.findViewById(R.id.contentDoor_Heat_Spinner);
        grillSpinner = view.findViewById(R.id.contentDoor_Grill_Spinner);
        convectionSpinner = view.findViewById(R.id.contentDoor_Convection_Spinner);
        temperatureEditText = view.findViewById(R.id.contentDoor_Temperature_EditText);

        onOffToggleButton.setTextOff(getString(R.string.off));
        onOffToggleButton.setTextOn(getString(R.string.on));
        temperatureEditText.setHint(R.string.temperature);
    }

    private void loadOvenState() {
        if(oven.isOn()){
            onOffToggleButton.setChecked(true);
        }else{
            onOffToggleButton.setChecked(false);
        }
        temperatureEditText.setText(String.valueOf(oven.getTemperature()));
    }



}
