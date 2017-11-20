package com.grupo1.hci.smarthome.Notifications;


/**
 * Created by francisco on 18/11/2017.
 */

public class DoorState  extends State {

    String status;
    String lock;

    public String getStatus() {
        return status;
    }

    public String getLock() {
        return lock;
    }

    public DoorState( String status, String lock) {
        super(4);
        this.status = status;
        this.lock = lock;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof DoorState)) return false;

        DoorState doorState = (DoorState) o;

        if (!getStatus().equals(doorState.getStatus())) return false;
        return getLock().equals(doorState.getLock());
    }

    @Override
    public int hashCode() {
        int result = getStatus().hashCode();
        result = 31 * result + getLock().hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "DoorState{" +
                "status='" + status + '\'' +
                ", lock='" + lock + '\'' +
                '}';
    }
}
