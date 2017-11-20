package com.grupo1.hci.smarthome.Notifications;

/**
 * Created by francisco on 19/11/2017.
 */

import java.io.Serializable;

public class State implements  Serializable {

    private int device;

    private boolean isStarted;

    public int getDevice(){
        return device;
    }

    public State(int device) {
        this.isStarted = false;
        this.device = device;
    }

    public void setDevice(int device) {
        this.device = device;
    }

    public void setStarted(boolean started) {
        isStarted = started;
    }

    public boolean isStarted() {

        return isStarted;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof State)) return false;

        State state = (State) o;

        return getDevice() == state.getDevice();
    }

    @Override
    public int hashCode() {
        return getDevice();
    }
}
