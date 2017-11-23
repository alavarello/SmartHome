package com.grupo1.hci.smarthome.Model;

import android.graphics.Color;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;

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

    TextView newTextView ;
    TextView oldTextView ;




    public  void revert(){
        if(spinner != null){
            spinner.setSelection(spinnerSelected);
        }
        else if(dimmer != null){
            dimmer.setProgress(dimmerValue);
        }else if(editText != null){
            editText.setText(editTextValue);
        }else if(newTextView != null){
            newTextView.setBackgroundColor(Color.TRANSPARENT);
            oldTextView.setBackgroundColor(Color.GRAY);
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

    public void setTextView(TextView newTextView, TextView oldTextView){
        this.newTextView = newTextView;
        this.oldTextView = oldTextView;
    }
}
