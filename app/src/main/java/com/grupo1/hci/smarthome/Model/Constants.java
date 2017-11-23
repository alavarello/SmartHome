package com.grupo1.hci.smarthome.Model;

import android.content.Context;

import com.grupo1.hci.smarthome.R;

/**
 * Created by agust on 10/31/2017.
 */

public class Constants {

    public static final int DELTE_FROM_DIALOGE = 1;
    public static final int DELTE__FROM_OVERFLOW = 0;
    public static Context context;
    public static String appName;
    public static String PORT_CONECTIVITY = "http://192.168.43.9:8080";



    //Devices ID -------------------------------------------
    public static final String LAMP_ID = "go46xmbqeomjrsjr";
    public static final String OVEN_ID = "im77xxyulpegfmv8";
    public static final String DOOR_ID = "lsf78ly0eqrjbz91";
    public static final String BLIND_ID = "eu0v2xgprrhhg41g";
    public static final String REFRIGERATOR_ID = "rnizejqr2di0okho";

    //Blind ---------------------------------------------------
    public static final String BLIND_STATE_OPENED = "opened";
    public static final String BLIND_STATE_CLOSING = "closing";
    public static final String BLIND_STATE_CLOSED = "closed";
    public static final String BLIND_STATE_OPENING = "opening";

    //Refrigerator------------------------------------------
    public static final String REFRIGERATOR_MODE_DEFAULT = "default";
    public static final String REFRIGERATOR_MODE_PARTY = "party";
    public static final String REFRIGERATOR_MODE_VACATION = "vacation";
    //Oven --------------------------------------------------
    public static final String OVEN_HEAT_CONVENTIONAL = "conventional";
    public static final String OVEN_HEAT_BOTTOM = "bottom";
    public static final String OVEN_HEAT_TOP = "top";
    public static final String OVEN_GRILL_OFF = "off";
    public static final String OVEN_GRILL_LARGE = "large";
    public static final String OVEN_GRILL_ECO = "eco";
    public static final String OVEN_CONVECTION_OFF = "off";
    public static final String OVEN_CONVECTION_NORMAL = "normal";
    public static final String OVEN_CONVECTION_ECO = "eco";
    public static final int OVEN_START_TEMPERATURE = 90;
    public static final int OVEN_CONVECTION_ECO_POSITION = 1;
    public static final int OVEN_CONVECTION_NORMAL_POSITION = 0;
    public static final int OVEN_CONVECTION_OFF_POSITION = 2;
    public static final int OVEN_GRILL_OFF_POSITION = 2;
    public static final int OVEN_GRILL_ECO_POSITION = 1;
    public static final int OVEN_GRILL_LARGE_POSITION = 0;
    public static final int OVEN_HEAT_CONVENTIONAL_POSITION = 0;
    public static final int OVEN_HEAT_BOTTOM_POSITION = 1;
    public static final int OVEN_HEAT_TOP_POSITION = 2;

    //Door --------------------------------
    public static final boolean DOOR_CLOSED = true;
    public static final boolean DOOR_OPENED = false;
    public static final boolean DOOR_LOCKED = true;
    public static final boolean DOOR_UNLOCKED = false;

    //ON OFF -------------------------------
    public static final boolean STATUS_OFF = false;
    public static final boolean STATUS_ON = true;

    //LAMP----------------------------------
    public static final String COLOR_RED = "ff0000";
    public static final String COLOR_BLUE = "#0033cc";
    public static final String COLOR_GREEN = "#00cc00";
    public static final String COLOR_YELLOW = "ffff00";

    //Intents ----------------------------------
    public static String ROOM_INTENT = "room";
    public static String DEVICE_INTENT = "device";
    public static String ROOM_ARRAY_INTENT = "roomArray";

    public static void setAppName(){
        if(context != null && appName == null){
            appName = context.getResources().getString(R.string.app_name) ;
        }
    }
}
