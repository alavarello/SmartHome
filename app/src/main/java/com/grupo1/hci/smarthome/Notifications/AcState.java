package com.grupo1.hci.smarthome.Notifications;
/**
 * Created by francisco on 18/11/2017.
 */

public class AcState  extends State {

    String status;
    int temperature;
    String mode;
    String verticalSwing;
    String horizontalSwing;
    String fanSpeed;

    public String getStatus() {
        return status;
    }

    public int getTemperature() {
        return temperature;
    }

    public String getMode() {
        return mode;
    }

    public String getVerticalSwing() {
        return verticalSwing;
    }

    public String getHorizontalSwing() {
        return horizontalSwing;
    }

    public String getFanSpeed() {
        return fanSpeed;
    }

    public AcState(String status, int temperature, String mode, String verticalSwing, String horizontalSwing, String fanSpeed) {
        super(3);
        this.status = status;
        this.temperature = temperature;
        this.mode = mode;
        this.verticalSwing = verticalSwing;
        this.horizontalSwing = horizontalSwing;
        this.fanSpeed = fanSpeed;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof AcState)) return false;

        AcState acState = (AcState) o;

        if (getTemperature() != acState.getTemperature()) return false;
        if (!getStatus().equals(acState.getStatus())) return false;
        if (!getMode().equals(acState.getMode())) return false;
        if (!getVerticalSwing().equals(acState.getVerticalSwing())) return false;
        if (!getHorizontalSwing().equals(acState.getHorizontalSwing())) return false;
        return getFanSpeed().equals(acState.getFanSpeed());
    }

    @Override
    public int hashCode() {
        int result = getStatus().hashCode();
        result = 31 * result + getTemperature();
        result = 31 * result + getMode().hashCode();
        result = 31 * result + getVerticalSwing().hashCode();
        result = 31 * result + getHorizontalSwing().hashCode();
        result = 31 * result + getFanSpeed().hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "AcState{" +
                "status='" + status + '\'' +
                ", temperature=" + temperature +
                ", mode='" + mode + '\'' +
                ", verticalSwing='" + verticalSwing + '\'' +
                ", horizontalSwing='" + horizontalSwing + '\'' +
                ", fanSpeed='" + fanSpeed + '\'' +
                '}';
    }
}
