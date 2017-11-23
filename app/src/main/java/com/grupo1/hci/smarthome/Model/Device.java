package com.grupo1.hci.smarthome.Model;

import java.io.Serializable;

/**
 * Created by agust on 10/31/2017.
 */

public interface Device extends Serializable {

    public String getId();
    public String getTypeId();
    public String getName();
    public void setName(String name);
}
