package com.grupo1.hci.smarthome.Model;

/**
 * Created by agust on 11/2/2017.
 */

public class Door implements Device {

    private String id;
    private String typeId = Constants.DOOR_ID;
    private String name;
    private boolean isClosed;
    private boolean isLocked;

    public Door(String id, String name) {
        this.id = id;
        this.name = name;
        this.isClosed = Constants.DOOR_CLOSED;
        this.isLocked = Constants.DOOR_UNLOCKED;
    }

    public Door(String id, String name, boolean isClosed, boolean isLocked) {

        this.id = id;
        this.name = name;
        this.isClosed = isClosed;
        this.isLocked = isLocked;
    }

    public Door(){}

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

    public void setName(String name) {
        this.name = name;
    }

    public boolean isClosed() {
        return isClosed;
    }

    public void setClosed(boolean closed) {
        isClosed = closed;
    }

    public boolean isLocked() {
        return isLocked;
    }

    public void setLocked(boolean locked) {
        isLocked = locked;
    }
}
