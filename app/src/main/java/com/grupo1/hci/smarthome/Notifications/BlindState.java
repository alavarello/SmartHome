package com.grupo1.hci.smarthome.Notifications;


/**
 * Created by francisco on 18/11/2017.
 */

public class BlindState extends State{

    String status;
    int level;

    public String getStatus() {
        return status;
    }


    public BlindState( String status, int level) {
        super(0);
        this.status = status;
        this.level = level;
    }

    public int getLevel() {
        return level;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof BlindState)) return false;

        BlindState that = (BlindState) o;

        if (getLevel() != that.getLevel()) return false;
        return getStatus().equals(that.getStatus());
    }

    @Override
    public int hashCode() {
        int result = getStatus().hashCode();
        result = 31 * result + getLevel();
        return result;
    }

    @Override
    public String toString() {
        return "BlindState{" +
                "status='" + status + '\'' +
                ", level=" + level +
                '}';
    }
}
