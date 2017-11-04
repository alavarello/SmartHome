package com.grupo1.hci.smarthome.Model;

import java.io.Serializable;

/**
 * Created by agust on 10/31/2017.
 */

public class Room implements Serializable{

    String id;
    String Name;

    public Room(String id, String name) {
        this.id = id;
        Name = name;
    }

    public Room(){}

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }
}
