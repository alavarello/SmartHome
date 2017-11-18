package com.grupo1.hci.smarthome.Model;

/**
 * Created by agust on 11/5/2017.
 */

public class Rutine {

    String name;
    String id;

    public Rutine(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public Rutine(){}

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
