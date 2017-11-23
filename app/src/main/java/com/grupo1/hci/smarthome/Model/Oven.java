package com.grupo1.hci.smarthome.Model;

/**
 * Created by agust on 11/2/2017.
 */

public class Oven implements Device {

    public boolean isOn;
    public int temperature;
    public String grill;
    public String convection;
    public String heat;
    private String id;
    private String typeId = Constants.OVEN_ID;
    private String name;

    public Oven(String id, String name) {
        this.id = id;
        this.name = name;
        this.isOn = Constants.STATUS_OFF;
        this.temperature = Constants.OVEN_START_TEMPERATURE;
        this.heat = Constants.OVEN_HEAT_CONVENTIONAL;
        this.grill = Constants.OVEN_GRILL_OFF;
        this.convection = Constants.OVEN_CONVECTION_OFF;
    }

    public Oven(boolean isOn, int temperature, String grill, String convection, String heat, String id, String typeId, String name) {
        this.isOn = isOn;
        this.temperature = temperature;
        this.grill = grill;
        this.convection = convection;
        this.heat = heat;
        this.id = id;
        this.typeId = typeId;
        this.name = name;
    }

    public Oven(){}

    @Override
    public void setName(String name) {
        this.name = name;
    }

    public boolean isOn() {
        return isOn;
    }

    public void setOn(boolean on) {
        isOn = on;
    }

    public int getTemperature() {
        return temperature;
    }

    public void setTemperature(int temperature) {
        this.temperature = temperature;
    }

    public String getGrill() {
        return grill;
    }

    public void setGrill(String grill) {
        this.grill = grill;
    }

    public String getConvection() {
        return convection;
    }

    public void setConvection(String convection) {
        this.convection = convection;
    }

    public String getHeat() {
        return heat;
    }

    public void setHeat(String heat) {
        this.heat = heat;
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public String getTypeId() {
        return typeId;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Oven oven = (Oven) o;

        return id.equals(oven.id);
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }
}
