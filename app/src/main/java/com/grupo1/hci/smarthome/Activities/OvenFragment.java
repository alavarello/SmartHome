package com.grupo1.hci.smarthome.Activities;

import android.content.Context;
import android.content.res.Resources;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
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

    Oven oven;
    Spinner heatSpinner;
    Spinner grillSpinner;
    Spinner convectionSpinner;
    EditText temperatureEditText;
    Switch onOffSwitch;
    APIManager apiManager;

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
        apiManager = APIManager.getInstance(getActivity());
        apiManager.getState(getContext(),oven,getActivity(),this);
        // Inflate the layout for this fragment
        return view;
    }


    private void setOnClickListener() {
        Resources res = getResources();
        final String normal = res.getString(R.string.oven_normal);
        final String eco = res.getString(R.string.oven_eco);
        final String off = res.getString(R.string.oven_off);
        final String bottom = res.getString(R.string.oven_bottom);
        final String top = res.getString(R.string.oven_top);
        final String large = res.getString(R.string.oven_large);
        final String conventional = res.getString(R.string.oven_conventional);

        onOffSwitch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(onOffSwitch.isChecked()){
                    apiManager.deviceOnOff(getActivity(),oven,"turnOn", onOffSwitch );
                }else{
                    apiManager.deviceOnOff(getActivity(),oven,"turnOff", onOffSwitch );
                }
            }
        });
        temperatureEditText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                apiManager.setOvenTemperature(getActivity(), oven,Integer.parseInt(textView.getText().toString()), temperatureEditText);
                return false;
            }
        });
        convectionSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String s = (String) adapterView.getItemAtPosition(i);
                if(s.equals(normal)){
                    apiManager.setOvenConvection(getActivity(),oven,Constants.OVEN_CONVECTION_NORMAL, convectionSpinner);
                }else if(s.equals(eco)){
                    apiManager.setOvenConvection(getActivity(),oven,Constants.OVEN_CONVECTION_ECO, convectionSpinner);
                }else if(s.equals(off)){
                    apiManager.setOvenConvection(getActivity(),oven,Constants.OVEN_CONVECTION_OFF, convectionSpinner);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        heatSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String s = (String) adapterView.getItemAtPosition(i);
                if(s.equals(conventional)){
                    apiManager.setOvenHeat(getActivity(),oven,Constants.OVEN_HEAT_CONVENTIONAL, heatSpinner);
                }else if(s.equals(bottom)){
                    apiManager.setOvenHeat(getActivity(),oven,Constants.OVEN_HEAT_BOTTOM, heatSpinner);
                }else if(s.equals(top)){
                    apiManager.setOvenHeat(getActivity(),oven,Constants.OVEN_HEAT_TOP, heatSpinner);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        grillSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String s = (String) adapterView.getItemAtPosition(i);
                if(s.equals(eco)){
                    apiManager.setOvenGrill(getActivity(),oven,Constants.OVEN_GRILL_ECO, grillSpinner);
                }else if(s.equals(large)){
                    apiManager.setOvenGrill(getActivity(),oven,Constants.OVEN_GRILL_LARGE, grillSpinner);
                }else if(s.equals(off)){
                    apiManager.setOvenGrill(getActivity(),oven,Constants.OVEN_GRILL_OFF, grillSpinner);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

    }

    private void setView(View view) {

        oven = (Oven) ((SuportDeviceActivity) getActivity()).getDevice();
        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle(oven.getName());

        onOffSwitch = view.findViewById(R.id.contentOven_Onoff_ToogleButton);
        heatSpinner = view.findViewById(R.id.contentOven_Heat_Spinner);
        grillSpinner = view.findViewById(R.id.contentOven_Grill_Spinner);
        convectionSpinner = view.findViewById(R.id.contentOven_Convection_Spinner);
        temperatureEditText = view.findViewById(R.id.contentOven_Temperature_EditText);

        setSpinners(view);

        onOffSwitch.setTextOff(getString(R.string.off));
        onOffSwitch.setTextOn(getString(R.string.on));
        temperatureEditText.setHint(R.string.temperature);
    }

    private void setSpinners(View view) {

        String[] heatModes = getResources().getStringArray(R.array.heat_array);
        ArrayAdapter<String> heatAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, heatModes);
        heatAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        heatSpinner.setAdapter(heatAdapter);

        String[] grillModes = getResources().getStringArray(R.array.grill_array);
        ArrayAdapter<String> grillAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, grillModes);
        grillAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        grillSpinner.setAdapter(grillAdapter);

        String[] convectionModes = getResources().getStringArray(R.array.convection_array);
        ArrayAdapter<String> convectionAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, convectionModes);
        convectionAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        convectionSpinner.setAdapter(convectionAdapter);

    }


    public void loadOvenState(Oven oven) {
        this.oven = oven;
        if(oven.isOn()){
            onOffSwitch.setChecked(true);
        }else{
            onOffSwitch.setChecked(false);
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
