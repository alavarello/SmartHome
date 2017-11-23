package com.grupo1.hci.smarthome.Notifications;


import android.util.Log;

import com.grupo1.hci.smarthome.R;

import java.util.ArrayList;

/**
 * Created by francisco on 18/11/2017.
 */

public class RefrigeratorState extends State {
    int freezerTemperature;
    int temperature;
    String mode;

    private static boolean notifyFreezerTemperatureClass = true;
    private static boolean notifyTemperatureClass = true;
    private static  boolean notifyModeClass = true;



    public static boolean isNotifyFreezerTemperatureClass() {
        return notifyFreezerTemperatureClass;
    }

    public static void setNotifyFreezerTemperatureClass(boolean notifyFreezerTemperatureClass) {
        RefrigeratorState.notifyFreezerTemperatureClass = notifyFreezerTemperatureClass;
    }

    public static boolean isNotifyTemperatureClass() {
        return notifyTemperatureClass;
    }

    public static void setNotifyTemperatureClass(boolean notifyTemperatureClass) {
        RefrigeratorState.notifyTemperatureClass = notifyTemperatureClass;
    }

    public static boolean isNotifyModeClass() {
        return notifyModeClass;
    }

    public static void setNotifyModeClass(boolean notifyModeClass) {
        RefrigeratorState.notifyModeClass = notifyModeClass;
    }

    public ArrayList<String> getDifferences(State a){

        RefrigeratorState s = (RefrigeratorState) a;
        ArrayList<String> ret = new ArrayList<>();

        if(super.isVisible){
            return ret;
        }

        if(!s.mode.equals(mode)){
            Log.d("DENTRO MODE", "DENTRO DE MODE");
           if(notifyModeClass)
           {
               if(mode.equals("party")) {
                   ret.add(super.getName() + " " + super.context.getResources().getString(R.string.refriStateMode) + " " + super.context.getResources().getString(R.string.refri_party));

               }
               else if(mode.equals("vacation")) {
                   ret.add(super.getName() + " " + super.context.getResources().getString(R.string.refriStateMode) + " " + super.context.getResources().getString(R.string.refri_vacation));

               }
               else
               {
                   ret.add(super.getName() + " " + super.context.getResources().getString(R.string.refriStateMode) + " " + super.context.getResources().getString(R.string.refri_def));

               }
               return ret;
           }
        }

        if(s.temperature != temperature){
            if(notifyTemperatureClass)
                ret.add(super.getName() + " " + super.context.getResources().getString(R.string.refriStateTemperature) + " " + temperature);
        }
        if(s.freezerTemperature != freezerTemperature){
            Log.d("DENTRO FREEZER TEMP", "DENTRO DE FREEZER TEMP");
           if(notifyFreezerTemperatureClass)
               ret.add(super.getName() + " " + super.context.getResources().getString(R.string.refriStateFreezerTemp) + " " + freezerTemperature);
        }

        return ret;

    }

    public RefrigeratorState( int freezerTemperature, int temperature, String mode) {
        super(7);
        this.freezerTemperature = freezerTemperature;
        this.temperature = temperature;
        this.mode = mode;
    }

    public int getFreezerTemperature() {
        return freezerTemperature;
    }

    public int getTemperature() {
        return temperature;
    }

    public String getMode() {
        return mode;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof RefrigeratorState)) return false;

        RefrigeratorState that = (RefrigeratorState) o;

        if (getFreezerTemperature() != that.getFreezerTemperature()) return false;
        if (getTemperature() != that.getTemperature()) return false;
        return getMode().equals(that.getMode());
    }

    @Override
    public int hashCode() {
        int result = getFreezerTemperature();
        result = 31 * result + getTemperature();
        result = 31 * result + getMode().hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "RefrigeratorState{" +
                "freezerTemperature=" + freezerTemperature +
                ", temperature=" + temperature +
                ", mode='" + mode + '\'' +
                '}';
    }
}
