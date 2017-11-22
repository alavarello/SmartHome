package com.grupo1.hci.smarthome.Model;

import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by agust on 11/5/2017.
 */

public class Rutine {

    String name;
    String id;
    ArrayList<Action> actions;

    public Rutine(String name, String id, ArrayList<Action> actions) {
        this.name = name;
        this.id = id;
        this.actions = actions;
    }

    public ArrayList<Action> getActions() {
        return actions;
    }

    public void setActions(ArrayList<Action> actions) {
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
