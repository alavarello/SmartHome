package com.grupo1.hci.smarthome.Notifications;

import java.util.ArrayList;

/**
 * Created by francisco on 18/11/2017.
 */

public class AcState  extends State {

    String status;
    int temperature;
    String mode;
    String verticalSwing;
    String horizontalSwing;
    String fanSpeed;

    public String getStatus() {
        return status;
    }

    public int getTemperature() {
        return temperature;
    }

    public String getMode() {
        return mode;
    }

    public String getVerticalSwing() {
        return verticalSwing;
    }

    public String getHorizontalSwing() {
        return horizontalSwing;
    }

    public String getFanSpeed() {
        return fanSpeed;
    }

    public AcState(String status, int temperature, String mode, String verticalSwing, String horizontalSwing, String fanSpeed) {
        super(3);
        this.status = status;
        this.temperature = temperature;
        this.mode = mode;
        this.verticalSwing = verticalSwing;
        this.horizontalSwing = horizontalSwing;
        this.fanSpeed = fanSpeed;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof AcState)) return false;

        AcState acState = (AcState) o;

        if (getTemperature() != acState.getTemperature()) return false;
        if (!getStatus().equals(acState.getStatus())) return false;
        if (!getMode().equals(acState.getMode())) return false;
        if (!getVerticalSwing().equals(acState.getVerticalSwing())) return false;
        if (!getHorizontalSwing().equals(acState.getHorizontalSwing())) return false;
        return getFanSpeed().equals(acState.getFanSpeed());
    }

    @Override
    public int hashCode() {
        int result = getStatus().hashCode();
        result = 31 * result + getTemperature();
        result = 31 * result + getMode().hashCode();
        result = 31 * result + getVerticalSwing().hashCode();
        result = 31 * result + getHorizontalSwing().hashCode();
        result = 31 * result + getFanSpeed().hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "AcState{" +
                "status='" + status + '\'' +
                ", temperature=" + temperature +
                ", mode='" + mode + '\'' +
                ", verticalSwing='" + verticalSwing + '\'' +
                ", horizontalSwing='" + horizontalSwing + '\'' +
                ", fanSpeed='" + fanSpeed + '\'' +
                '}';
    }


    public ArrayList<String> getDifferences( State a){

        AcState s = (AcState) a;

        ArrayList<String> ret = new ArrayList<>();

        if(!s.fanSpeed.equals(fanSpeed)){
            ret.add(super.getName() + " Fan speed has changed to : " + fanSpeed);
        }
        if(!s.status.equals(status)){
            if(status.equals("on")){
                ret.add(super.getName() + " has turned on");
            }else {

                ret.add(super.getName() + " has turned off");
            }
        }
        if(!s.mode.equals(mode)){
            ret.add(super.getName() + " Mode has changed to : " + mode);
        }
        if(!s.verticalSwing.equals(verticalSwing)){
            ret.add(super.getName() + " Vertical Swing  has changed to : " + verticalSwing);
        }
        if(!s.horizontalSwing.equals(horizontalSwing)){
            ret.add(super.getName() + " Horizontal Swing has changed to : " + horizontalSwing);
        }
        if(s.temperature != temperature){
            ret.add(super.getName() + " Temperature has changed to : " + mode);
        }

        return ret;

    }
}
