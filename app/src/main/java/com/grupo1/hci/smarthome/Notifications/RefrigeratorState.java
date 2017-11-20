package com.grupo1.hci.smarthome.Notifications;


/**
 * Created by francisco on 18/11/2017.
 */

public class RefrigeratorState extends State {
    int freezerTemperature;
    int temperature;
    String mode;

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
