package com.grupo1.hci.smarthome.Activities;

import com.grupo1.hci.smarthome.Model.Device;

/**
 * Created by agust on 11/21/2017.
 */

public interface SuportDeviceActivity {

    public Device getDevice();

    public void deviceDeleted();
}
