package com.grupo1.hci.smarthome.Notifications;

import java.io.Serializable;

/**
 * Created by agust on 10/31/2017.
 */

public class DeviceNotifications implements Serializable {

    String id;
    String typeId;
    String name;

    public String getDeviceId() {
        return id;
    }

    public String getTypeId() {
        return typeId;
    }

    public DeviceNotifications(String deviceId , String type , String name) {
        this.id = deviceId;
        this.typeId = type;
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof DeviceNotifications)) return false;

        DeviceNotifications device = (DeviceNotifications) o;

        if (!id.equals(device.id)) return false;
        return getTypeId().equals(device.getTypeId());
    }

    @Override
    public int hashCode() {
        int result = id.hashCode();
        result = 31 * result + getTypeId().hashCode();
        return result;
    }
}
