package com.grupo1.hci.smarthome.Notifications;


/**
 * Created by francisco on 18/11/2017.
 */

public class AlarmState extends State{

    String status;

    public String getStatus() {
        return status;
    }

    public AlarmState( String status) {
        super(5);
        this.status = status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof AlarmState)) return false;

        AlarmState that = (AlarmState) o;

        return getStatus() != null ? getStatus().equals(that.getStatus()) : that.getStatus() == null;
    }

    @Override
    public String toString() {
        return "AlarmState{" +
                "status='" + status + '\'' +
                '}';
    }

    @Override
    public int hashCode() {
        return getStatus() != null ? getStatus().hashCode() : 0;
    }
}
