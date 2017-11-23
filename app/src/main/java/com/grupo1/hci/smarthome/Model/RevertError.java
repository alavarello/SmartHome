package com.grupo1.hci.smarthome.Model;

import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.Spinner;

/**
 * Created by agust on 11/23/2017.
 */

public class RevertError {

    Spinner spinner;
    Integer spinnerSelected;

    SeekBar dimmer;
    Integer dimmerValue;

    EditText editText;
    String editTextValue;




    public  void revert(){
        if(spinner != null){
            spinner.setSelection(spinnerSelected);
        }
        else if(dimmer != null){
            dimmer.setProgress(dimmerValue);
        }else if(editText != null){
            editText.setText(editTextValue);
        }
    }

    public void setSpinner(Spinner spinner, Integer spinerSelected){
        this.spinner = spinner;
        this.spinnerSelected = spinerSelected;
    }

    public void setDimmer(SeekBar dimmer, Integer dimmerValue){
        this.dimmer = dimmer;
        this.dimmerValue = dimmerValue;
    }

    public void setEditText(EditText editText, String editTextValue){
        this.editText = editText;
        this.editTextValue = editTextValue;
    }
}
