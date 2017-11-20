package com.grupo1.hci.smarthome.Notifications;


/**
 * Created by francisco on 18/11/2017.
 */

public class OvenState extends State{

    String  status;
    int temperature;
    String heat;
    String grill;
    String convection;

    public OvenState( String status, int temperature, String heat, String grill, String convection) {
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
}
