package com.grupo1.hci.smarthome.Notifications;


import com.grupo1.hci.smarthome.Model.Blind;
import com.grupo1.hci.smarthome.R;

import java.util.ArrayList;

/**
 * Created by francisco on 18/11/2017.
 */

public class BlindState extends State{

    String status;
    int level;

    private static boolean notifyOpenClass = true;
    private static boolean notifyCloseClass = true;



    public static boolean isNotifyOpenClass() {
        return notifyOpenClass;
    }

    public static void setNotifyOpenClass(boolean notifyOpenClass) {
        BlindState.notifyOpenClass = notifyOpenClass;
    }

    public static boolean isNotifyCloseClass() {
        return notifyCloseClass;
    }

    public static void setNotifyCloseClass(boolean notifyCloseClass) {
        BlindState.notifyCloseClass = notifyCloseClass;
    }


    public ArrayList<String> getDifferences(State a){

        BlindState s = (BlindState) a;

        ArrayList<String> ret = new ArrayList<>();

        if(super.isVisible){
            return ret;
        }

        if(!s.status.equals(status)){
            if(status.equals("opened")){

                if(notifyOpenClass)ret.add(super.getName() + " " + super.context.getResources().getString(R.string.blindStateOpen));
            }else{
                if(notifyCloseClass)ret.add(super.getName() + " " + super.context.getResources().getString(R.string.blindStateClose));
            }

        }

        if(s.level != level){
            if(level == 100) {
              if(notifyOpenClass)  ret.add(super.getName() + " " + super.context.getResources().getString(R.string.blindStateOpened));
            }else if(level == 0) {
                if (notifyCloseClass)
                    ret.add(super.getName() + " " + super.context.getResources().getString(R.string.blindStateClosed));
            }
            if(level == 100 && s.equals("opened")) {
              if(notifyOpenClass)  ret.add(super.getName() + "has been opened");
            }else if(level == 100  && s.equals("closed")){
              if(notifyCloseClass)  ret.add(super.getName() + "has been closed");
            }
        }

        return ret;

    }

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
