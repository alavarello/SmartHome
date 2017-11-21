package com.grupo1.hci.smarthome.Notifications;


import java.sql.Time;
import java.util.ArrayList;

/**
 * Created by francisco on 18/11/2017.
 */

public class TimerState extends State{

    String status;

    int interval;
    int remaining;

    public ArrayList<String> getDifferences(State a){

        TimerState s = (TimerState) a;

        ArrayList<String> ret = new ArrayList<>();

        if(!s.status.equals(status)){
            ret.add(super.getName() + " Status has changed to : " + status);
        }

        if(s.interval != interval){
            ret.add(super.getName() + " Interval has changed to : " + interval);
        }
        if(s.remaining != remaining){
            ret.add(super.getName() + " Remaining has changed to : " + remaining);
        }

        return ret;

    }

    public TimerState( String status, int interval, int remaining) {
        super(6);
        this.status = status;
        this.interval = interval;
        this.remaining = remaining;
    }

    public String getStatus() {
        return status;
    }

    public int getInterval() {
        return interval;
    }

    public int getRemaining() {
        return remaining;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TimerState)) return false;

        TimerState that = (TimerState) o;

        if (getInterval() != that.getInterval()) return false;
        if (getRemaining() != that.getRemaining()) return false;
        return getStatus().equals(that.getStatus());
    }

    @Override
    public int hashCode() {
        int result = getStatus().hashCode();
        result = 31 * result + getInterval();
        result = 31 * result + getRemaining();
        return result;
    }

    @Override
    public String toString() {
        return "TimerState{" +
                "status='" + status + '\'' +
                ", interval=" + interval +
                ", remaining=" + remaining +
                '}';
    }
}
