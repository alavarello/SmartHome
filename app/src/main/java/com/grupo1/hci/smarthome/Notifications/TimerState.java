package com.grupo1.hci.smarthome.Notifications;


import com.grupo1.hci.smarthome.R;

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

        if(super.isVisible){
            return ret;
        }

        if(!s.status.equals(status)){
            ret.add(super.getName() + " " + super.context.getResources().getString(R.string.timerStateStatus) + status);
        }

        if(s.interval != interval){
            ret.add(super.getName() + " " + super.context.getResources().getString(R.string.timerStateInterval) + interval);
        }
        if(s.remaining != remaining){
            ret.add(super.getName() + " " + super.context.getResources().getString(R.string.timerStateRemaining) + remaining);
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
