package com.grupo1.hci.smarthome.Notifications;

import com.grupo1.hci.smarthome.Model.Lamp;

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

    private Boolean notifyTurnOn;
    private Boolean notifyTurnOff;
    private Boolean notifyChangeBrigtness;
    private Boolean notifyChangeColor;


    private boolean isNotifiable(Boolean instance, boolean classNot){
        if(instance!= null){
            return instance;
        }else{
            return classNot;
        }
    }

    public boolean getRealNotifyTurnOn(){
        return isNotifiable(notifyTurnOn , notifyTurnOnClass);
    }

    public boolean getRealNotifyTurnOff(){
        return isNotifiable(notifyTurnOff , notifyTurnOffClass);
    }

    public boolean getRealNotifyChangeBrightness(){
        return isNotifiable( notifyChangeBrigtness , notifyChangeBrigtnessClass);
    }

    public boolean getRealNotifyChangeColor(){
        return isNotifiable(notifyChangeColor, notifyChangeColorClass);
    }


    public ArrayList<String> getDifferences(State a){

        LampState s = (LampState) a;
        ArrayList<String> ret = new ArrayList<>();

        if(!s.color.equals(color)){
            if(isNotifiable(notifyChangeColor , notifyChangeColorClass))ret.add(super.getName() + " Color has changed to : " + color);
        }
        if(!s.status.equals(status)){
            if(status.equals("on")){

               if(isNotifiable(notifyTurnOn , notifyTurnOnClass)) ret.add(super.getName() + " has turned on");
            }else{
               if(isNotifiable(notifyTurnOff , notifyTurnOffClass)) ret.add(super.getName() + " has turned off");
            }

        }
        if(s.brightness != brightness){
           if(isNotifiable(notifyChangeBrigtness , notifyChangeBrigtnessClass)) ret.add(super.getName() + " Brightness has changed to : " + brightness);
        }

        return ret;

    }

    public LampState( String color, String status, int brightness) {
        super(1);
        this.color = color;
        this.status = status;
        this.brightness = brightness;
        this.notifyChangeBrigtness= null;
        this.notifyChangeColor= null;
        this.notifyTurnOff= null;
        this.notifyTurnOn= null;
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

    public void setNotifyTurnOn(boolean notifyTurnOn) {
        this.notifyTurnOn = notifyTurnOn;
    }

    public void setNotifyTurnOff(boolean notifyTurnOff) {
        this.notifyTurnOff = notifyTurnOff;
    }

    public void setNotifyChangeBrigtness(boolean notifyChangeBrigtness) {
        this.notifyChangeBrigtness = notifyChangeBrigtness;
    }

    public void setNotifyChangeColor(boolean notifyChangeColor) {
        this.notifyChangeColor = notifyChangeColor;
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

    public Boolean getNotifyTurnOn() {
        return notifyTurnOn;
    }

    public Boolean getNotifyTurnOff() {
        return notifyTurnOff;
    }

    public Boolean getNotifyChangeBrigtness() {
        return notifyChangeBrigtness;
    }

    public Boolean getNotifyChangeColor() {
        return notifyChangeColor;
    }
}
