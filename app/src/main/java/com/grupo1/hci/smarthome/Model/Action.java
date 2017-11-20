package com.grupo1.hci.smarthome.Model;

import java.util.ArrayList;

/**
 * Created by Santi on 11/18/2017.
 */

public class Action {
    String deviceId;
    String actionName;
    ArrayList<String> params;
    String meta;

    public Action(String deviceId, String actionName, ArrayList<String> params, String meta) {
        this.deviceId = deviceId;
        this.actionName = actionName;
        this.params = params;
        this.meta = meta;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public String getActionName() {
        return actionName;
    }

    public void setActionName(String actionName) {
        this.actionName = actionName;
    }

    public ArrayList<String> getParams() {
        return params;
    }

    public void setParams(ArrayList<String> params) {
        this.params = params;
    }

    public String getMeta() {
        return meta;
    }

    public void setMeta(String meta) {
        this.meta = meta;
    }
}