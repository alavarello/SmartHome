package com.grupo1.hci.smarthome.Model;

import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by agust on 11/5/2017.
 */

public class Rutine {

    String name;
    String id;
    ArrayList<JSONObject> actions;

    public Rutine(String name, String id, ArrayList<JSONObject> actions) {
        this.name = name;
        this.id = id;
        this.actions = actions;
    }

    public ArrayList<JSONObject> getActions() {
        return actions;
    }

    public void setActions(ArrayList<JSONObject> actions) {
        this.actions = actions;
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
