package com.grupo1.hci.smarthome.Notifications;

/**
 * Created by francisco on 18/11/2017.
 */

public class LampState extends State {

    private String color;
    private String status;
    private int brightness;

    public LampState( String color, String status, int brightness) {
        super(1);
        this.color = color;
        this.status = status;
        this.brightness = brightness;
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
}
