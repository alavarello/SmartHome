package com.grupo1.hci.smarthome.Notifications;


import java.util.ArrayList;

/**
 * Created by francisco on 18/11/2017.
 */

public class OvenState extends State{

    String  status;
    int temperature;
    String heat;
    String grill;
    String convection;

    private static boolean notifyTurnOnClass = true;
    private static boolean notifyTurnOffClass = true;
    private static boolean notifyTemperatureClass = true;
    private static boolean notifyHeatClass = true;
    private static boolean notifyGrillClass = true;
    private static boolean notifyConvectionClass = true;


    public ArrayList<String> getDifferences(State a){

        OvenState s = (OvenState) a;

        ArrayList<String> ret = new ArrayList<>();

        if(super.isVisible){
            return ret;
        }

        if(!s.heat.equals(heat)){
            if(notifyHeatClass)ret.add(super.getName() + " Heat has changed to : " + heat);
        }
        if(!s.grill.equals(grill)){
            if(notifyGrillClass)ret.add(super.getName() + " Grill has changed to : " + grill);
        }
        if(!s.convection.equals(convection)){
            if(notifyConvectionClass)ret.add(super.getName() + " Convection has changed to : " + convection);
        }
        if(!s.status.equals(status)){
            if(status.equals("on")){
                if(notifyTurnOnClass)ret.add(super.getName() + " has turned on");
            }else{
                if(notifyTurnOffClass)ret.add(super.getName() + " has turned off");
            }
        }
        if(s.temperature != temperature){
            if(notifyTemperatureClass)ret.add(super.getName() + " Temperature has changed to : " + temperature);
        }

        return ret;

    }

    public static void setNotifyTurnOnClass(boolean notifyTurnOnClass) {
        OvenState.notifyTurnOnClass = notifyTurnOnClass;
    }

    public static void setNotifyTurnOffClass(boolean notifyTurnOffClass) {
        OvenState.notifyTurnOffClass = notifyTurnOffClass;
    }

    public static void setNotifyTemperatureClass(boolean notifyTemperatureClass) {
        OvenState.notifyTemperatureClass = notifyTemperatureClass;
    }

    public static void setNotifyHeatClass(boolean notifyHeatClass) {
        OvenState.notifyHeatClass = notifyHeatClass;
    }

    public static void setNotifyGrillClass(boolean notifyGrillClass) {
        OvenState.notifyGrillClass = notifyGrillClass;
    }

    public static void setNotifyConvectionClass(boolean notifyConvectionClass) {
        OvenState.notifyConvectionClass = notifyConvectionClass;
    }


    public OvenState(String status, int temperature, String heat, String grill, String convection) {
        super(2);
        this.status = status;
        this.temperature = temperature;
        this.heat = heat;
        this.grill = grill;
        this.convection = convection;
    }


    public String getStatus() {
        return status;
    }

    public int getTemperature() {
        return temperature;
    }

    public String getHeat() {
        return heat;
    }

    public String getGrill() {
        return grill;
    }

    public String getConvection() {
        return convection;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof OvenState)) return false;

        OvenState ovenState = (OvenState) o;

        if (getTemperature() != ovenState.getTemperature()) return false;
        if (!getStatus().equals(ovenState.getStatus())) return false;
        if (!getHeat().equals(ovenState.getHeat())) return false;
        if (!getGrill().equals(ovenState.getGrill())) return false;
        return getConvection().equals(ovenState.getConvection());
    }

    @Override
    public int hashCode() {
        int result = getStatus().hashCode();
        result = 31 * result + getTemperature();
        result = 31 * result + getHeat().hashCode();
        result = 31 * result + getGrill().hashCode();
        result = 31 * result + getConvection().hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "OvenState{" +
                "status='" + status + '\'' +
                ", temperature=" + temperature +
                ", heat='" + heat + '\'' +
                ", grill='" + grill + '\'' +
                ", convection='" + convection + '\'' +
                '}';
    }

    public static boolean isNotifyTurnOnClass() {
        return notifyTurnOnClass;
    }

    public static boolean isNotifyTurnOffClass() {
        return notifyTurnOffClass;
    }

    public static boolean isNotifyTemperatureClass() {
        return notifyTemperatureClass;
    }

    public static boolean isNotifyHeatClass() {
        return notifyHeatClass;
    }

    public static boolean isNotifyGrillClass() {
        return notifyGrillClass;
    }

    public static boolean isNotifyConvectionClass() {
        return notifyConvectionClass;
    }


}
