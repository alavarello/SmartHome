package com.grupo1.hci.smarthome.Model;

/**
 * Created by Julian on 21/11/17.
 */

public class Refrigerator implements Device {

    private String id;
    private String name;
    private String typeId = Constants.REFRIGERATOR_ID;

    Integer freezerTemperature;
    Integer temperature;
    String mode;

    public Refrigerator(){}

    public Refrigerator(Integer freezerTemperature, Integer temperature, String mode) {
        this.freezerTemperature = freezerTemperature;
        this.temperature = temperature;
        this.mode = mode;
    }
    public Refrigerator(String id, String name) {
        this.id = id;
        this.name = name;
        this.freezerTemperature = 0;
        this.temperature = 0;
        this.mode = "default";
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String getTypeId() {
        return typeId;
    }

    public Integer getFreezerTemperature() {
        return freezerTemperature;
    }

    public void setFreezerTemperature(Integer freezerTemperature) {
        this.freezerTemperature = freezerTemperature;
    }

    public Integer getTemperature() {
        return temperature;
    }

    public void setTemperature(Integer temperature) {
        this.temperature = temperature;
    }

    public String getMode() {
        return mode;
    }

    public void setMode(String mode) {
        this.mode = mode;
    }
}
