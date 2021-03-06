package com.grupo1.hci.smarthome.Activities;

import android.os.CountDownTimer;
import android.support.v4.app.Fragment;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
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
        apiManager = APIManager.getInstance(getActivity());
        apiManager.getState(getContext(), refri, getActivity(), this);
        setOnClickListener();
        // Inflate the layout for this fragment
        return view;
    }

    private void setView(View view) {

        refri = (Refrigerator) ((SuportDeviceActivity) getActivity()).getDevice();
        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle(refri.getName());

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
                Integer refriTemp;
                try{
                     refriTemp = Integer.parseInt(textView.getText().toString());
                }catch (Exception e){
                    refri_temp.setText(String.valueOf(refri.getTemperature()));
                    return false;
                }
                if(refriTemp > 8 || refriTemp < 2) {
                    refri_temp.requestFocus();
                    refri_temp.setError(getResources().getText(R.string.refriTemperatureMaxMinValue));
                    refri_temp.setText(String.valueOf(refri.getTemperature()));
                    CountDownTimer cdt = new CountDownTimer(4000, 1000) {
                        @Override
                        public void onTick(long l) {

                        }

                        @Override
                        public void onFinish() {
                            refri_temp.setError(null);
                        }
                    };
                    cdt.start();
                    return false;
                }
                apiManager.setRefriTemp(getActivity(), refri, textView.getText().toString(), refri_temp);
                return true;
            }
        });
        freezer_temp.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                Integer freezerTemp;
                try{
                 freezerTemp = Integer.parseInt(textView.getText().toString());
                }catch (Exception e){
                    freezer_temp.setText(String.valueOf(refri.getFreezerTemperature()));
                    return false;
                }
                if(freezerTemp > -8 || freezerTemp < -20){
                    freezer_temp.setError(getResources().getText(R.string.freezerTemperatureMinMaxValue));
                    freezer_temp.setText(String.valueOf(refri.getFreezerTemperature()));
                    CountDownTimer cdt = new CountDownTimer(4000, 1000) {
                        @Override
                        public void onTick(long l) {

                        }

                        @Override
                        public void onFinish() {
                            freezer_temp.setError(null);
                        }
                    };
                    cdt.start();
                }
                apiManager.setFreezerTemp(getActivity(), refri, textView.getText().toString(), freezer_temp);
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
                    apiManager.setRefriMode(getActivity(), refri, Constants.REFRIGERATOR_MODE_DEFAULT, refri_mode);
                } else if (s.equals(party)) {
                    apiManager.setRefriMode(getActivity(), refri, Constants.REFRIGERATOR_MODE_PARTY, refri_mode);
                } else if (s.equals(vacation)) {
                    apiManager.setRefriMode(getActivity(), refri, Constants.REFRIGERATOR_MODE_VACATION, refri_mode);
                }

            }


        });


    }

    public void loadRefriState(Refrigerator refri) {
        this.refri = refri;

        refri_temp.setText(String.valueOf(refri.getTemperature()));
        freezer_temp.setText(String.valueOf(refri.getFreezerTemperature()));

        if(refri.getMode().equals(Constants.REFRIGERATOR_MODE_DEFAULT)){
            refri_mode.setSelection(0);
        }
        else if(refri.getMode().equals(Constants.REFRIGERATOR_MODE_VACATION)){
            refri_mode.setSelection(1);
        }else if(refri.getMode().equals(Constants.REFRIGERATOR_MODE_PARTY)){
            refri_mode.setSelection(2);
        }

    }




}
