package com.grupo1.hci.smarthome.Notifications;


import android.app.Activity;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.IBinder;
import android.support.annotation.NonNull;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.grupo1.hci.smarthome.Model.Constants;
import com.grupo1.hci.smarthome.Model.Device;
import com.grupo1.hci.smarthome.Model.Room;

import org.json.JSONArray;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

/**
 * Created by francisco on 18/11/2017.
 */

/*

 */

public class ApiService extends Service {


    public static long interval = 1000;
    private static final String SHARED_PREFS_NAME = "MY_SHARED_PREF";

  private final static String   typeBlind = Constants.BLIND_ID;
  private final static String   typeLamp = Constants.LAMP_ID;
  private final static String   typeOven = Constants.OVEN_ID;
  private final static String   typeAc = "li6cbv5sdlatti0j";
  private final static String   typeDoor = Constants.DOOR_ID;
  private final static String   typeAlarm= "mxztsyjzsrq7iaqc";
  private final static String   typeTimer= "ofglvd9gqX8yfl3l";
  private final static String   typeRefrigerator= Constants.REFRIGERATOR_ID;




    RequestQueue  queue ;

    Intent mServiceIntent ;

    static ArrayList<DeviceNotifications> devices  =  new ArrayList<>();

    static ArrayList<DeviceState> status = new ArrayList<>();

    static Set<DeviceState> statusAdd = new HashSet<>();

    static Set<DeviceState> statusRemove = new HashSet<>();

    static Set<DeviceNotifications> devicesAdd  = new HashSet<>();

    static Set<DeviceNotifications> devicesRemove  = new HashSet<>();

    public static DeviceState getDevice(String id){

        for(DeviceState d : status){
            if(d.deviceId.equals(id)){
                return d;
            }
        }

        return null;
    }

    private void updateArrays()
    {
        status.removeAll(statusRemove);
        status.addAll(statusAdd);

        devices.removeAll(devicesRemove);
        devices.addAll(devicesAdd);

        statusRemove.clear();
        statusAdd.clear();

        devicesAdd.clear();
        devicesRemove.clear();

        saveArray();
    }
    private void checkForDeleted(ArrayList<DeviceNotifications> dev){

        for(DeviceNotifications d1 : devices) {

         if (!dev.contains(d1)){

             Log.d("borrar" , "va a borrar" + d1.getDeviceId());

             DeviceState st = getDevice(d1.getDeviceId());

             int channelId =  st.s.getNotificationChannel();


             devicesRemove.add(d1);
             statusRemove.add(st);

             sendNotification(getApplicationContext() , d1.name + " has been deleted" , channelId  , d1.name , d1.getDeviceId());
         }
     }
    }

    public  void updateDevices(ArrayList<DeviceNotifications> dev){
        State s ;
        checkForDeleted(dev);
        for(DeviceNotifications d : dev ){
            if(!devices.contains(d) && !devicesAdd.contains(d)){
                devicesAdd.add(d);
                switch(d.typeId) {
                    case typeBlind:
                        s = new BlindState("", 0);
                        break;
                    case typeLamp:
                        s = new LampState("", "", 0);
                        break;
                    case typeOven:
                        s = new OvenState("", 0, "", "", "");
                        break;
                    case typeAc:
                        s = new AcState("", 0, "", "", "", "");
                        break;
                    case typeDoor:
                        s = new DoorState("", "");
                        break;
                    case typeAlarm:
                        s = new AlarmState("");
                        break;
                    case typeTimer:
                        s = new TimerState("", 0, 0);
                        break;
                    case typeRefrigerator:
                        s = new RefrigeratorState(0, 0, "");
                        break;
                    default:  s = new DoorState("", "");
                }
               s.setContext(this);
                s.setName(d.name);
                sendNotification(getApplicationContext() ,  d.name + " has been added" , s.getNotificationChannel() , d.name, d.getDeviceId());
                //status.add(new DeviceState(s,d.id));
                statusAdd.add(new DeviceState(s,d.id));
            }
        }
    }

    public static void updateVisibility(String idDevice , boolean visibility){
        for(DeviceState d : status){
            if(d.deviceId.equals(idDevice)){
                d.s.isVisible = visibility;
            }
        }
    }


    @Override
    public int onStartCommand(Intent intent, int flags, int startId){

        queue = Volley.newRequestQueue(getApplicationContext());
        mServiceIntent  = new Intent(getApplicationContext(), PingService.class);

        Gson g = new Gson();

        List<String> l = getArray();
        if(l.get(0) != null ){
            Type deviceType = new TypeToken<ArrayList<DeviceNotifications>>() {
            }.getType();
            Type statusType = new TypeToken<ArrayList<DeviceState>>() {
            }.getType();

            devices =g.fromJson(l.get(0) ,deviceType );
            status = g.fromJson(l.get(1) , statusType);

            for(DeviceState d : status){
                d.s.isVisible = false;
            }

        }

        loadNotifications();

        Thread thread = new Thread() {
            @Override
            public void run() {
                try {
                    while(true) {
                        sleep(5000);
                        checkDevicesState(getApplicationContext());
                        getAllDevices(getApplicationContext());
                        updateArrays();
                        saveNotifications();
                        Log.d("status es " ,g.toJson(status));
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };

        thread.start();

        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        // STOP YOUR TASKS
        //  saveArray();
        super.onDestroy();
    }


    public void  checkDevicesState(Context context){
        if(status == null) return;

        for( DeviceState d : status) {
            getMyState(context, d);
        }
    }



    @Override
    public IBinder onBind(Intent intent){
        return null;
    }

    public  void getMyState(final Context context , final DeviceState deviceState) {

        //Toast.makeText(context, idDevice, Toast.LENGTH_SHORT).show();
        // prueba con una lampara
        // String url = "http://10.0.2.2:8080/api/rooms";
        //String url =  "http://10.0.2.2:8080/api/devices/" + deviceState.deviceId + "/getState";
        String url =  Constants.PORT_CONECTIVITY+"/api/devices/" + deviceState.deviceId + "/getState";


        StringRequest sr = new StringRequest(Request.Method.PUT,url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                Gson g = new Gson();


                ApiResponse r = g.fromJson(response,ApiResponse.class );


                State s;


                try {

                    switch (deviceState.s.getDevice()) {

                        case 0:
                            s = g.fromJson(r.getResult(), BlindState.class);
                            break;
                        case 1:
                            s = g.fromJson(r.getResult(), LampState.class);
                            break;
                        case 2:
                            s = g.fromJson(r.getResult(), OvenState.class);
                            break;
                        case 3:
                            s = g.fromJson(r.getResult(), AcState.class);
                            break;
                        case 4:
                            s = g.fromJson(r.getResult(), DoorState.class);
                            break;
                        case 5:
                            s = g.fromJson(r.getResult(), AlarmState.class);
                            break;
                        case 6:
                            s = g.fromJson(r.getResult(), TimerState.class);
                            break;
                        case 7:
                            s = g.fromJson(r.getResult(), RefrigeratorState.class);
                            break;
                        default:
                            Log.d("fue default!!" , "------");
                            s = g.fromJson(r.getResult(), LampState.class);


                    }
                }catch (Exception e){
                    return;
                }

                s.setDevice(deviceState.s.getDevice());
                s.setName(deviceState.s.getName());
                s.setNotificationChannel(deviceState.s.getNotificationChannel());
                s.setContext(getApplicationContext());
                s.isVisible = deviceState.s.isVisible;


                if( deviceState.s.equals(s) || !deviceState.s.isStarted()){

                    deviceState.s = s;
                    deviceState.s.setStarted(true);
                    return;
                }


                ArrayList<String> messages = s.getDifferences(deviceState.s);


                Log.d("messages es " , g.toJson(messages));

                deviceState.s = s;
                deviceState.s.setStarted(true);

                for(String m : messages) {
                    sendNotification(context,    m , deviceState.s.getNotificationChannel() , deviceState.s.getName(), deviceState.deviceId);
                }





                //Toast.makeText(getApplicationContext(), response, Toast.LENGTH_SHORT).show();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //sendNotification(context , "error" );

                //Log.e("error" , error.toString());
                //Toast.makeText(context, error.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        queue.add(sr);
    }


    public void sendNotificationTwo(Context context , String message , int channelId , String deviceName , String deviceId , String roomId, String roomName, String typeId){
        mServiceIntent  = new Intent(getApplicationContext(), PingService.class);

        mServiceIntent.putExtra(CommonConstants.EXTRA_MESSAGE, message);//l.getStatus().toString());
        mServiceIntent.setAction(CommonConstants.ACTION_PING);

        mServiceIntent.putExtra("channelId" , channelId);
        mServiceIntent.putExtra("deviceName" , deviceName);
        mServiceIntent.putExtra("deviceId" , deviceId);
        mServiceIntent.putExtra("roomId" , roomId);
        mServiceIntent.putExtra("roomName" , roomName);
        mServiceIntent.putExtra("typeId" , typeId);

        Log.d("Send Notification Two:" , roomId + "-" + deviceId);


        int milliseconds = (5 * 1000);

        mServiceIntent.putExtra(CommonConstants.EXTRA_TIMER, milliseconds);

        // Launches IntentService "PingService" to set timer.
        context.startService(mServiceIntent );
    }


    private void sendNotification(Context context , String message , int channelId , String deviceName , String deviceId){
       // mServiceIntent.putExtra("encabezado", "ayaya");

        getRoomOfDevice(context , message ,channelId , deviceName ,deviceId);

    }

    public  void getAllDevices(final Context context) {


         //String url = "http://10.0.2.2:8080/api/devices";
        String url =  Constants.PORT_CONECTIVITY+"/api/devices";
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, new JSONObject(),
                new Response.Listener<JSONObject>()
                {
                    @Override
                    public void onResponse(JSONObject response) {
                        try{
                            Gson gson = new GsonBuilder().create();
                            Type listType = new TypeToken<ArrayList<DeviceNotifications>>() {
                            }.getType();
                            String jsonFragment = response.getString("devices");
                            ArrayList<DeviceNotifications> deviceList = gson.fromJson(jsonFragment, listType);

                            updateDevices(deviceList);


                        } catch (Exception exception) {

                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Log.e("notification" , "error in notifications");

               // sendNotification(context , "error" , 100 , "ERROR");

            }
        });

        queue.add(request);

    }

    public void saveNotifications(){





    }

    public void loadNotifications(){




    }

    public boolean saveArray() {

        Gson g = new Gson();

        String dev = g.toJson(devices , devices.getClass());
        String stat = g.toJson(status , status.getClass());

        SharedPreferences sp = this.getSharedPreferences(SHARED_PREFS_NAME, Activity.MODE_PRIVATE);
        SharedPreferences.Editor mEdit1 = sp.edit();
        mEdit1.putString("devices", dev);
        mEdit1.putString("status", stat);

        SaverClass s = new SaverClass();
        s.fillIn();

        String saver = g.toJson(s , SaverClass.class);

        if( saver == null ){
            Log.e("saver se guarda" , "omo null");
        }

        mEdit1.putString("saver" , saver);

        return mEdit1.commit();

    }

    public List<String> getArray() {

        Gson g = new Gson();


        SharedPreferences sp = this.getSharedPreferences(SHARED_PREFS_NAME, Activity.MODE_PRIVATE);
        String dev = sp.getString("devices" , null);
        String stat = sp.getString("status" , null);
        String saver = sp.getString("saver" , null);
        if(saver != null)
        {
            SaverClass s = g.fromJson(saver , SaverClass.class );
            s.load();

        }


        List<String> l = new LinkedList<>();
        l.add(0 , dev);
        l.add(1,stat);
        return l;
    }

    private void checkDevicesInRoom( Room r , Context context , String message , int channelId , String deviceName , String deviceId){

        Log.d("device id y room id" , r.getId() + "-" + deviceId);

        String url = Constants.PORT_CONECTIVITY+"/api/rooms/" + r.getId() + "/devices";
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, new JSONObject(),
                new Response.Listener<JSONObject>()
                {
                    @Override
                    public void onResponse(JSONObject response) {
                        try{
                            Gson gson = new GsonBuilder().create();
                            Type listType = new TypeToken<ArrayList<Device>>() {
                            }.getType();
                            JSONArray deviceList = response.getJSONArray("devices");

                            JSONObject aux;

                            for(int i = 0 ; i < deviceList.length() ; i++ ){
                               aux = deviceList.getJSONObject(i);

                              String auxId =  aux.getString("id");
                              String typeId = aux.getString("typeId");

                               if(auxId.equals(deviceId)){
                                   sendNotificationTwo(context , message , channelId , deviceName , deviceId , r.getId() , r.getName() , typeId);
                               }


                            }

                        } catch (Exception exception) {
                            Log.e("checkDevicesInRoom" , "error en API");
                            // Toast.makeText(activity, exception.getLocalizedMessage(), Toast.LENGTH_LONG).show();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
            }
        });

        queue.add(request);
    }


    public void getRoomOfDevice( Context context , String message , int channelId , String deviceName , String deviceId) {
        String url = Constants.PORT_CONECTIVITY+"/api/rooms";
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, new JSONObject(),
                new Response.Listener<JSONObject>()
                {
                    @Override
                    public void onResponse(JSONObject response) {
                        try{
                            Gson gson = new GsonBuilder().create();
                            Type listType = new TypeToken<ArrayList<Room>>() {
                            }.getType();
                            String jsonFragment = response.getString("rooms");
                            ArrayList<Room> roomList = gson.fromJson(jsonFragment, listType);

                            for( Room  r : roomList){
                                    checkDevicesInRoom(r ,context , message , channelId , deviceName , deviceId );
                            }

                        } catch (Exception exception) {
                            Log.e("getRoomDevice" , "error en API");
                           // Toast.makeText(activity, exception.getLocalizedMessage(), Toast.LENGTH_LONG).show();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
            }
        });

        queue.add(request);

    }


}
