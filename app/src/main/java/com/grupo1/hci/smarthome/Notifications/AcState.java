package com.grupo1.hci.smarthome.Notifications;

import android.content.Context;

import com.grupo1.hci.smarthome.R;

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
        super(3 );
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

        if(super.isVisible){
            return ret;
        }

        if(!s.fanSpeed.equals(fanSpeed)){
            ret.add(super.getName() + " " + super.context.getResources().getString(R.string.acStateFan) + fanSpeed);
        }
        if(!s.status.equals(status)){
            if(status.equals("on")){
                ret.add(super.getName() + " " + super.context.getResources().getString(R.string.acStateTurnOn));
            }else {

                ret.add(super.getName() + " " +super.context.getResources().getString(R.string.acStateTurnOff));
            }
        }
        if(!s.mode.equals(mode)){
            ret.add(super.getName() + " " + super.context.getResources().getString(R.string.acStateMode) + mode);
        }
        if(!s.verticalSwing.equals(verticalSwing)){
            ret.add(super.getName() + " " + super.context.getResources().getString(R.string.acStateVerticalSwing) + verticalSwing);
        }
        if(!s.horizontalSwing.equals(horizontalSwing)){
            ret.add(super.getName() + " " + super.context.getResources().getString(R.string.acStateHorizontalSwing) + horizontalSwing);
        }
        if(s.temperature != temperature){
            ret.add(super.getName() + " " + super.context.getResources().getString(R.string.acTemperature) + mode);
        }

        return ret;

    }
}
