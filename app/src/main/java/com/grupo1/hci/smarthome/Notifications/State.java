package com.grupo1.hci.smarthome.Notifications;

/**
 * Created by francisco on 19/11/2017.
 */

import android.util.Log;

import java.io.Serializable;
import java.util.ArrayList;

public class State implements  Serializable {

    private static int notificationChannelCounter = 0;

    private int device;

    private boolean isStarted;

    private String name;

    private int notificationChannel;

    public int getDevice(){
        return device;
    }

    public State(int device) {
        this.isStarted = false;
        this.device = device;
        this.notificationChannel = notificationChannelCounter++;
    }

    public int getNotificationChannel() {
        return notificationChannel;
    }

    public void setNotificationChannel(int notificationChannel) {
        this.notificationChannel = notificationChannel;
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

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
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


    public ArrayList<String> getDifferences(State s) {
        return new ArrayList<>();
    }

    public void setSameNotifications(State sameNotifications) {
    }

    protected boolean isNotifiable(Boolean instance, boolean classNot){
        if(instance!= null){
            return instance;
        }else{
            return classNot;
        }
    }
}
