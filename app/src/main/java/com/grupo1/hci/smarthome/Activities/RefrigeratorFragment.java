package com.grupo1.hci.smarthome.Activities;

import android.support.v4.app.Fragment;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.grupo1.hci.smarthome.Model.APIManager;
import com.grupo1.hci.smarthome.Model.Constants;
import com.grupo1.hci.smarthome.Model.Lamp;
import com.grupo1.hci.smarthome.Model.Oven;
import com.grupo1.hci.smarthome.Model.Refrigerator;
import com.grupo1.hci.smarthome.R;

/**
 * Created by Julian on 21/11/17.
 */

public class RefrigeratorFragment extends Fragment {

    Refrigerator refri;
    Spinner refri_mode;
    EditText refri_temp;
    EditText freezer_temp;
    APIManager apiManager;

    public RefrigeratorFragment() {}

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_refrigerator, container, false);
        setView(view);
        setOnClickListener();
        apiManager = APIManager.getInstance(getActivity());
        apiManager.getState(getContext(), refri, getActivity(), this);
        // Inflate the layout for this fragment
        return view;
    }

    private void setView(View view) {

        refri = (Refrigerator) ((DeviceActivity) getActivity()).getDevice();
        ((DeviceActivity)getActivity()).getSupportActionBar().setTitle(refri.getName());

        refri_mode = view.findViewById(R.id.refri_mode);
        refri_temp = view.findViewById(R.id.refri_temp);
        freezer_temp = view.findViewById(R.id.freezer_temp);

        setSpinners(view);

    }

    private void setSpinners(View view) {

        String[] refriModes = getResources().getStringArray(R.array.refri_mode);
        ArrayAdapter<String> refriAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, refriModes);
        refriAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        refri_mode.setAdapter(refriAdapter);


    }

    private void setOnClickListener() {
        Resources res = getResources();

        final String default_ = res.getString(R.string.refri_def);
        final String party = res.getString(R.string.refri_party);
        final String vacation = res.getString(R.string.refri_vacation);

        refri_temp.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                apiManager.setRefriTemp(getActivity(), refri, i, refri_temp);
                return false;
            }
        });
        freezer_temp.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                apiManager.setFreezerTemp(getActivity(), refri, i, freezer_temp);
                return false;
            }
        });

        refri_mode.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }

            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String s = (String) adapterView.getItemAtPosition(i);
                if (s.equals(default_)) {
                    apiManager.setRefriMode(getActivity(), refri, "default", refri_mode);
                } else if (s.equals(party)) {
                    apiManager.setRefriMode(getActivity(), refri, "party", refri_mode);
                } else if (s.equals(vacation)) {
                    apiManager.setRefriMode(getActivity(), refri, "vacation", refri_mode);
                }

            }


        });


    }

    public void loadRefriState(Refrigerator refri) {
        this.refri = refri;

        refri_temp.setText(String.valueOf(refri.getTemperature()));
        freezer_temp.setText(String.valueOf(refri.getFreezerTemperature()));

        if(refri.getMode().equals("Default")){
            refri_mode.setSelection(0);
        }
        else if(refri.getMode().equals("Vacation")){
            refri_mode.setSelection(1);
        }else{
            refri_mode.setSelection(2);
        }

    }


}
