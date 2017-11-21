package com.grupo1.hci.smarthome.Model;

/**
 * Created by Julian on 21/11/17.
 */

public class Refrigerator implements Device {

    private String id;
    private String name;
    private String typeId;

    Integer freezerTemperature;
    Integer temperature;
    String mode;

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

    public Integer getFreezerTemperature() {
        return freezerTemperature;
    }

    public Integer getTemperature() {
        return temperature;
    }

    public String getMode() {
        return mode;
    }

    public void setFreezerTemperature(Integer freezerTemperature) {
        this.freezerTemperature = freezerTemperature;
    }

    public void setTemperature(Integer temperature) {
        this.temperature = temperature;
    }

    public void setMode(String mode) {
        this.mode = mode;
    }

    @Override
    public String getId() {
        return null;
    }

    @Override
    public String getTypeId() {
        return null;
    }

    @Override
    public String getName() {
        return null;
    }
}
