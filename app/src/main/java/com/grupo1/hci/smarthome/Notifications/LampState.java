package com.grupo1.hci.smarthome.Notifications;

import android.util.Log;

import com.grupo1.hci.smarthome.Model.Lamp;
import com.grupo1.hci.smarthome.R;

import java.util.ArrayList;

/**
 * Created by francisco on 18/11/2017.
 */

public class LampState extends State {

    private String color;
    private String status;
    private int brightness;

    private static boolean notifyTurnOnClass = true;
    private static boolean notifyTurnOffClass = true;
    private static boolean notifyChangeBrigtnessClass = true;
    private static boolean notifyChangeColorClass = true;


    public ArrayList<String> getDifferences(State a){

        LampState s = (LampState) a;
        ArrayList<String> ret = new ArrayList<>();

        if(super.isVisible){
            return ret;
        }

        if(!s.color.equals(color)){
            if(notifyChangeColorClass)ret.add(super.getName() + " " + super.context.getResources().getString(R.string.lampStateColor));
        }
        if(!s.status.equals(status)){
            if(status.equals("on")){

               if(notifyTurnOnClass) ret.add(super.getName() + " " + super.context.getResources().getString(R.string.lampStateOn));
            }else{
               if(notifyTurnOffClass) ret.add(super.getName() + " " + super.context.getResources().getString(R.string.lampStateOff));
            }

        }
        if(s.brightness != brightness){
           if(notifyChangeBrigtnessClass) ret.add(super.getName() + " " + super.context.getResources().getString(R.string.lampStateBrightness) + " " + brightness);
        }

        return ret;

    }

    public LampState( String color, String status, int brightness) {
        super(1);
        this.color = color;
        this.status = status;
        this.brightness = brightness;
    }

    public static void setNotifyTurnOnClass(boolean notifyTurnOnClass) {
        LampState.notifyTurnOnClass = notifyTurnOnClass;
    }


    public static void setNotifyTurnOffClass(boolean notifyTurnOffClass) {
        LampState.notifyTurnOffClass = notifyTurnOffClass;
    }

    public static void setNotifyChangeBrigtnessClass(boolean notifyChangeBrigtnessClass) {
        LampState.notifyChangeBrigtnessClass = notifyChangeBrigtnessClass;
    }


    public static void setNotifyChangeColorClass(boolean notifyChangeColorClass) {
        LampState.notifyChangeColorClass = notifyChangeColorClass;
    }


    public String getColor() {
        return color;
    }

    public String getStatus() {
        return status;
    }

    public int getBrightness() {
        return brightness;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof LampState)) return false;

        LampState lampState = (LampState) o;

        if (getBrightness() != lampState.getBrightness()) return false;
        if (!getColor().equals(lampState.getColor())) return false;
        return getStatus().equals(lampState.getStatus());
    }

    @Override
    public int hashCode() {
        int result = getColor().hashCode();
        result = 31 * result + getStatus().hashCode();
        result = 31 * result + getBrightness();
        return result;
    }

    @Override
    public String toString() {
        return "LampState{" +
                "color='" + color + '\'' +
                ", status='" + status + '\'' +
                ", brightness=" + brightness +
                '}';
    }

    public static boolean isNotifyTurnOnClass() {
        return notifyTurnOnClass;
    }

    public static boolean isNotifyTurnOffClass() {
        return notifyTurnOffClass;
    }

    public static boolean isNotifyChangeBrigtnessClass() {
        return notifyChangeBrigtnessClass;
    }

    public static boolean isNotifyChangeColorClass() {
        return notifyChangeColorClass;
    }


}
