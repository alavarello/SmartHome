package com.grupo1.hci.smarthome.Model;

import java.util.ArrayList;

/**
 * Created by agust on 11/5/2017.
 */

public class APIManager {


    public static  ArrayList<Room> getRooms(){
        ArrayList<Room> roomsArray = new ArrayList<>();
        roomsArray.add(new Room("1", "Cuarto1"));
        roomsArray.add(new Room("2", "Cuarto2"));
        roomsArray.add(new Room("3", "Cuarto3"));
        roomsArray.add(new Room("4", "Cuarto4"));
        roomsArray.add(new Room("5", "Cuarto5"));
        roomsArray.add(new Room("6", "Cuarto6"));
        return roomsArray;
    }

    public static ArrayList<Device> getRoomDevices(String roomId){
        ArrayList<Device> deviceArray = new ArrayList<>();
        if(roomId.equals("3")){
            deviceArray.add(new Lamp("1","Lamp3"));
            deviceArray.add(new Lamp("2","Lamp4"));
            return deviceArray;
        }
        //Add Elements to array for testing------------------
        deviceArray.add(new Lamp("1","Lamp1"));
        deviceArray.add(new Lamp("2","Lamp2"));
        deviceArray.add(new Blind("3","Blind1"));
        deviceArray.add(new Door("4","Door1"));
        deviceArray.add(new Oven("5","Oven1"));
        //------------------------------------------------------4
        return deviceArray;
    }

    public static ArrayList<Rutine> getRutines(){

        ArrayList<Rutine> rutinesArray = new ArrayList<>();
        return rutinesArray;
    }

}


