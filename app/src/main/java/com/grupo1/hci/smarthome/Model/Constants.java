package com.grupo1.hci.smarthome.Model;

/**
 * Created by agust on 10/31/2017.
 */

public class Constants {

    //Devices ID -------------------------------------------
    public static final String LAMP_ID = "go46xmbqeomjrsjr";
    public static final String OVEN_ID = "im77xxyulpegfmv8";
    public static final String DOOR_ID = "lsf78ly0eqrjbz91";
    public static final String BLIND_ID = "eu0v2xgprrhhg41g";

    //Blind ---------------------------------------------------
    public static final String BLIND_STATE_OPENED = "opened";
    public static final String BLIND_STATE_CLOSING = "closing";
    public static final String BLIND_STATE_CLOSED = "closed";
    public static final String BLIND_STATE_OPENING = "opening";

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

    //Door --------------------------------
    public static final boolean DOOR_CLOSED = true;
    public static final boolean DOOR_OPENED = false;
    public static final boolean DOOR_LOCKED = true;
    public static final boolean DOOR_UNLOCKED = false;

    //ON OFF -------------------------------
    public static final boolean STATUS_OFF = false;
    public static final boolean STATUS_ON = true;

    public static final String appName = "SMART HOME";

    //Intents ----------------------------------
    public static String ROOM_INTENT = "room";
    public static String DEVICE_INTENT = "device";
    public static String ROOM_ARRAY_INTENT = "roomArray";


}
