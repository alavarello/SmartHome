package com.grupo1.hci.smarthome.Model;

/**
 * Created by agust on 10/31/2017.
 */

public class Blind implements Device {

    private String id;
    private String typeId = Constants.BLIND_ID;
    private String name;
    private String status;

    public Blind(String id, String name, String status) {
        this.id = id;
        this.name = name;
        this.status = status;
    }

    public Blind(String id, String name) {
        this.id = id;
        this.name = name;
        this.status = Constants.BLIND_STATE_OPENED;
    }


    public Blind(){}

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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Blind blind = (Blind) o;

        return id.equals(blind.id);
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }
}
