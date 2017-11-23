package com.grupo1.hci.smarthome.Notifications;

import com.grupo1.hci.smarthome.Model.Door;

/**
 * Created by francisco on 22/11/2017.
 */

public class SaverClass {

    //LAMP

    private long  interval;

    private  boolean LampNotifyTurnOnClass ;
    private  boolean LampNotifyTurnOffClass;
    private boolean LampNotifyChangeBrigtnessClass ;
    private  boolean LampNotifyChangeColorClass;

    //BLIND

    private boolean BlindNotifyOpenClass ;
    private boolean BlindNotifyCloseClass ;

    //DOOR

    private boolean DoorNotifyLockUnlockClass ;
    private  boolean DoorNotifyOpenCloseClass ;

    //OVEN

    private boolean OvenNotifyTurnOnClass ;
    private boolean OvenNotifyTurnOffClass ;
    private boolean OvenNotifyTemperatureClass ;
    private boolean OvenNotifyHeatClass;
    private boolean OvenNotifyGrillClass ;
    private boolean OvenNotifyConvectionClass ;


    //REFRIGERATOR

    private boolean RefrigeratorNotifyFreezerTemperatureClass ;
    private boolean RefrigeratorNotifyTemperatureClass ;
    private boolean RefrigeratorNotifyModeClass ;

    public void load(){

        ApiService.interval = interval;


        LampState.setNotifyTurnOnClass(LampNotifyTurnOnClass);
        LampState.setNotifyTurnOffClass(LampNotifyTurnOffClass);
        LampState.setNotifyChangeBrigtnessClass(LampNotifyChangeBrigtnessClass);
        LampState.setNotifyChangeColorClass(LampNotifyChangeColorClass);

        //BLIND

        BlindState.setNotifyOpenClass(BlindNotifyOpenClass );
        BlindState.setNotifyCloseClass(BlindNotifyCloseClass);

        //DOOR

        DoorState.setNotifyLockUnlockClass(DoorNotifyLockUnlockClass);
        DoorState.setNotifyOpenCloseClass(DoorNotifyOpenCloseClass);

        //OVEN

        OvenState.setNotifyTurnOnClass(OvenNotifyTurnOnClass);
        OvenState.setNotifyTurnOffClass(OvenNotifyTurnOffClass);
        OvenState.setNotifyTemperatureClass(OvenNotifyTemperatureClass);
        OvenState.setNotifyHeatClass(OvenNotifyHeatClass);
        OvenState.setNotifyGrillClass( OvenNotifyGrillClass);
        OvenState.setNotifyConvectionClass(OvenNotifyConvectionClass);


        //REFRIGERATOR

        RefrigeratorState.setNotifyFreezerTemperatureClass(RefrigeratorNotifyFreezerTemperatureClass);
        RefrigeratorState.setNotifyTemperatureClass(RefrigeratorNotifyTemperatureClass);
        RefrigeratorState.setNotifyModeClass(RefrigeratorNotifyModeClass);


    }


    public void fillIn(){

        interval = ApiService.interval;

        //LAMP

       LampNotifyTurnOnClass  = LampState.isNotifyTurnOnClass();
       LampNotifyTurnOffClass = LampState.isNotifyTurnOffClass();
       LampNotifyChangeBrigtnessClass = LampState.isNotifyChangeBrigtnessClass();
       LampNotifyChangeColorClass = LampState.isNotifyChangeColorClass();

        //BLIND

       BlindNotifyOpenClass  = BlindState.isNotifyOpenClass();
       BlindNotifyCloseClass = BlindState.isNotifyCloseClass();

        //DOOR

       DoorNotifyLockUnlockClass = DoorState.isNotifyLockUnlockClass();
       DoorNotifyOpenCloseClass = DoorState.isNotifyOpenCloseClass();

        //OVEN

       OvenNotifyTurnOnClass  = OvenState.isNotifyTurnOnClass();
       OvenNotifyTurnOffClass = OvenState.isNotifyTurnOffClass();
       OvenNotifyTemperatureClass = OvenState.isNotifyTemperatureClass();
       OvenNotifyHeatClass = OvenState.isNotifyHeatClass();
       OvenNotifyGrillClass  = OvenState.isNotifyGrillClass();
       OvenNotifyConvectionClass = OvenState.isNotifyConvectionClass();


        //REFRIGERATOR

        RefrigeratorNotifyFreezerTemperatureClass = RefrigeratorState.isNotifyFreezerTemperatureClass();
        RefrigeratorNotifyTemperatureClass = RefrigeratorState.isNotifyTemperatureClass();
        RefrigeratorNotifyModeClass = RefrigeratorState.isNotifyModeClass();

    }


}
