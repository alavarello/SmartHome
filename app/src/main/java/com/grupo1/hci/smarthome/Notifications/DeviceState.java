package com.grupo1.hci.smarthome.Notifications;

import java.io.Serializable;
/**
 * Created by francisco on 19/11/2017.
 */

public class DeviceState implements Serializable  {

    State s;
    String deviceId;

    public DeviceState(State s, String deviceId) {
        this.s = s;
        this.deviceId = deviceId;
    }


}
