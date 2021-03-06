package com.grupo1.hci.smarthome.Notifications;


import com.grupo1.hci.smarthome.Model.Door;
import com.grupo1.hci.smarthome.R;

import java.util.ArrayList;

/**
 * Created by francisco on 18/11/2017.
 */

public class DoorState  extends State {

    String status;
    String lock;

    private static boolean notifyLockUnlockClass = true;
    private static boolean notifyOpenCloseClass = true;


    public static boolean isNotifyLockUnlockClass() {
        return notifyLockUnlockClass;
    }

    public static void setNotifyLockUnlockClass(boolean notifyLockUnlockClass) {
        DoorState.notifyLockUnlockClass = notifyLockUnlockClass;
    }

    public static boolean isNotifyOpenCloseClass() {
        return notifyOpenCloseClass;
    }

    public static void setNotifyOpenCloseClass(boolean notifyOpenCloseClass) {
        DoorState.notifyOpenCloseClass = notifyOpenCloseClass;
    }

    public ArrayList<String> getDifferences(State a){

        DoorState s = (DoorState) a;

        ArrayList<String> ret = new ArrayList<>();

        if(super.isVisible){
            return ret;
        }

        if(!s.status.equals(status) && notifyOpenCloseClass){
            if(status.equals("opened")){
                ret.add(super.getName() + " " + super.context.getResources().getString(R.string.doorStateOpened));
            }else{
                ret.add(super.getName() + " " + super.context.getResources().getString(R.string.doorStateClosed));
            }

        }
        if(!s.lock.equals(lock) && notifyLockUnlockClass){
            if(lock.equals("locked")){
                ret.add(super.getName() + " " + super.context.getResources().getString(R.string.doorStateLocked));
            }else{
                ret.add(super.getName() + " " + super.context.getResources().getString(R.string.doorStateUnlocked));
            }
        }

        return ret;

    }

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
