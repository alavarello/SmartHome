package com.grupo1.hci.smarthome.Activities;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.grupo1.hci.smarthome.Model.APIManager;
import com.grupo1.hci.smarthome.Model.Constants;
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
    Switch onOffToggleButton;

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
        APIManager.getInstance(getActivity()).getState(getContext(),oven,getActivity(),this);
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
        heatSpinner = view.findViewById(R.id.contentOven_Heat_Spinner);
        grillSpinner = view.findViewById(R.id.contentOven_Grill_Spinner);
        convectionSpinner = view.findViewById(R.id.contentOven_Convection_Spinner);
        temperatureEditText = view.findViewById(R.id.contentOven_Temperature_EditText);

        setSpinners(view);

        onOffToggleButton.setTextOff(getString(R.string.off));
        onOffToggleButton.setTextOn(getString(R.string.on));
        temperatureEditText.setHint(R.string.temperature);
    }

    private void setSpinners(View view) {

        String[] heatModes = getResources().getStringArray(R.array.heat_array);
        ArrayAdapter<String> heatAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, heatModes);
        Spinner heatSp = view.findViewById(R.id.contentOven_Heat_Spinner);
        heatAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        heatSp.setAdapter(heatAdapter);

        String[] grillModes = getResources().getStringArray(R.array.grill_array);
        ArrayAdapter<String> grillAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, grillModes);
        Spinner grillSp = view.findViewById(R.id.contentOven_Grill_Spinner);
        grillAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        grillSp.setAdapter(grillAdapter);

        String[] convectionModes = getResources().getStringArray(R.array.convection_array);
        ArrayAdapter<String> convectionAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, convectionModes);
        Spinner convectionSp = view.findViewById(R.id.contentOven_Convection_Spinner);
        convectionAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        convectionSp.setAdapter(convectionAdapter);

    }


    public void loadOvenState(Oven oven) {
        this.oven = oven;
        if(oven.isOn()){
            onOffToggleButton.setChecked(true);
        }else{
            onOffToggleButton.setChecked(false);
        }
        temperatureEditText.setText(String.valueOf(oven.getTemperature()));
        if(oven.getConvection().equals(Constants.OVEN_CONVECTION_ECO)){
            convectionSpinner.setSelection(Constants.OVEN_CONVECTION_ECO_POSITION);
        }
        else if(oven.getConvection().equals(Constants.OVEN_CONVECTION_NORMAL)){
            convectionSpinner.setSelection(Constants.OVEN_CONVECTION_NORMAL_POSITION);
        }else{
            convectionSpinner.setSelection(Constants.OVEN_CONVECTION_OFF_POSITION);
        }
        if(oven.getGrill().equals(Constants.OVEN_GRILL_ECO)){
            grillSpinner.setSelection(Constants.OVEN_GRILL_ECO_POSITION);
        }
        else if(oven.getGrill().equals(Constants.OVEN_GRILL_LARGE)){
            grillSpinner.setSelection(Constants.OVEN_GRILL_LARGE_POSITION);
        }else{
            grillSpinner.setSelection(Constants.OVEN_GRILL_OFF_POSITION);
        }
        if(oven.getHeat().equals(Constants.OVEN_HEAT_BOTTOM)){
            heatSpinner.setSelection(Constants.OVEN_HEAT_BOTTOM_POSITION);
        }
        else if(oven.getHeat().equals(Constants.OVEN_HEAT_TOP)){
            heatSpinner.setSelection(Constants.OVEN_HEAT_TOP_POSITION);
        }else{
            heatSpinner.setSelection(Constants.OVEN_HEAT_CONVENTIONAL_POSITION);
        }
    }



}
