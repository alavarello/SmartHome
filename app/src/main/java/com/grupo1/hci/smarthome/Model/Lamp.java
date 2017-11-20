package com.grupo1.hci.smarthome.Model;

/**
 * Created by agust on 10/31/2017.
 */

public class Lamp implements Device {

    private String id;
    private String typeId = Constants.LAMP_ID;
    private String name;
    private boolean isOn;
    private String color;
    private int brightness;

    public Lamp(String id, String name) {
        this.id = id;
        this.name = name;
        this.isOn = Constants.STATUS_OFF;
        this.color = "0000000";
        this.brightness = 100;
    }

    public Lamp(String id, String typeId, String name, boolean status, String color, int brightness) {
        this.id = id;
        this.typeId = typeId;
        this.name = name;
        this.isOn = status;
        this.color = color;
        this.brightness = brightness;
    }

    public Lamp(){}

    public boolean isOn() {
        return isOn;
    }

    public void setOn(boolean on) {
        isOn = on;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public int getBrightness() {
        return brightness;
    }

    public void setBrightness(int brightness) {
        this.brightness = brightness;
    }

    @Override
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String getTypeId() {
        return typeId;
    }

    public void setTypeId(String typeId) {
        this.typeId = typeId;
    }

    @Override
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Lamp lamp = (Lamp) o;

        return id.equals(lamp.id);
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }
}
