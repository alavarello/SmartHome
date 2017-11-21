package com.grupo1.hci.smarthome.Notifications;


import com.google.gson.JsonElement;



/**
 * Created by francisco on 18/11/2017.
 */

public class ApiResponse {

    JsonElement result;

    public JsonElement getResult(){
        return result;
    }

    @Override
    public String toString() {
        return result.toString();
    }
}
