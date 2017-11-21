package com.grupo1.hci.smarthome.Model;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Cache;
import com.android.volley.Network;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.BasicNetwork;
import com.android.volley.toolbox.DiskBasedCache;
import com.android.volley.toolbox.HurlStack;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.grupo1.hci.smarthome.Activities.BlindFragment;
import com.grupo1.hci.smarthome.Activities.DeviceActivity;
import com.grupo1.hci.smarthome.Activities.DoorFragment;
import com.grupo1.hci.smarthome.Activities.HomeActivity;
import com.grupo1.hci.smarthome.Activities.HomeAdapter;
import com.grupo1.hci.smarthome.Activities.HomeFragment;
import com.grupo1.hci.smarthome.Activities.HomeListFragment;
import com.grupo1.hci.smarthome.Activities.LampFragment;
import com.grupo1.hci.smarthome.Activities.NavigationActivity;
import com.grupo1.hci.smarthome.Activities.OvenFragment;
import com.grupo1.hci.smarthome.Activities.RoomActivity;
import com.grupo1.hci.smarthome.Activities.RoomListFragment;
import com.grupo1.hci.smarthome.Activities.RoomsAdapter;
import com.grupo1.hci.smarthome.Activities.RoomsFragment;
import com.grupo1.hci.smarthome.Activities.RutinesActivity;


import org.json.JSONArray;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by agust on 11/5/2017.
 */

public class APIManager {
    private static APIManager instance = null;
    private RequestQueue queue;
    private Cache cache;
    private Network network;

    private APIManager(Activity activity) {
        cache = new DiskBasedCache(activity.getCacheDir(), 1024 * 1024); // 1MB cap
        network = new BasicNetwork(new HurlStack());
        queue = new RequestQueue(cache, network);
        queue.start();
    }

    public static APIManager getInstance(Activity activity) {
        if(instance == null) {
            return new APIManager(activity);
        }
        return instance;
    }

    public void getRooms(final Activity activity) {
        cache = new DiskBasedCache(activity.getCacheDir(), 1024 * 1024); // 1MB cap
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
                            /**
                             * Del string que devuelve la consulta selecciono to.do lo que esta
                             * dentro de rooms, sino no lo desseializa bien.
                             */
                            String jsonFragment = response.getString("rooms");
                            /**
                             * Este array ya tiene todos los objetos que busco de la API, solo
                             * falta el adapter
                             */
                            ArrayList<Room> roomList = gson.fromJson(jsonFragment, listType);
                            ((HomeActivity)activity).loadRooms(roomList);
                            ((NavigationActivity)activity).setRoomsArray(roomList);
                            ((NavigationActivity)activity).setMenu();
                        } catch (Exception exception) {
                            Toast.makeText(activity, exception.getLocalizedMessage(), Toast.LENGTH_LONG).show();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(activity, error.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        queue.add(request);

    }

    public void getDevicesForRoom(String roomID, final Activity activity) {

        String url =  Constants.PORT_CONECTIVITY+"/api/rooms/" + roomID + "/devices";
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, new JSONObject(),
                new Response.Listener<JSONObject>()
                {
                    @Override
                    public void onResponse(JSONObject response) {
                        try{
                            Gson gson = new GsonBuilder().create();
                            Type listType = new TypeToken<ArrayList<Device>>() {
                            }.getType();
                            Type doorType = new TypeToken<Door>() {
                            }.getType();
                            Type lampType = new TypeToken<Lamp>() {
                            }.getType();
                            Type blindType = new TypeToken<Blind>() {
                            }.getType();
                            Type ovenType = new TypeToken<Oven>() {
                            }.getType();
                            JSONArray jArray = response.getJSONArray("devices");
                            JSONObject j;
                            ArrayList<Device> deviceList = new ArrayList<>();
                            for(int i =0; i<jArray.length(); i++){
                                j = jArray.getJSONObject(i);
                                String typeId = j.get("typeId").toString();
                                if(typeId.equals(Constants.LAMP_ID)){
                                    Lamp l = gson.fromJson(j.toString(), lampType);
                                    deviceList.add(l);
                                }
                                else if(typeId.equals(Constants.DOOR_ID)){
                                    Door d = gson.fromJson(j.toString(), doorType);
                                    deviceList.add(d);
                                }
                                else if(typeId.equals(Constants.BLIND_ID)){
                                    Blind b = gson.fromJson(j.toString(), blindType);
                                    deviceList.add(b);
                                }
                                else if(typeId.equals(Constants.OVEN_ID)){
                                    Oven o = gson.fromJson(j.toString(), ovenType);
                                    deviceList.add(o);
                                }
                            }
                           // String jsonFragment = response.getString("devices");
                          //  ArrayList<Device> deviceList = gson.fromJson(jsonFragment, listType);
                          ((RoomActivity)activity).loadDevices(deviceList);
                            Toast.makeText(activity, response.toString(), Toast.LENGTH_SHORT).show();
                        } catch (Exception exception) {
                            Toast.makeText(activity, exception.getLocalizedMessage(), Toast.LENGTH_LONG).show();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(activity, error.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        queue.add(request);

    }

    public void addDeviceToRoom() {

    }



    /**
     * @author sswinnen
     * Recibe un nombre y un meta, (si el meta es vacio pasarle ""), y gson construye el JSON
     * para mandarle a la API
     * @param roomName nombre elegido por el usuario
     * @param meta vacio en caso de no querer usarlo
     */

    private void newRoomToAPI(final String roomName, final String meta, final Activity activity) {
        cache = new DiskBasedCache(activity.getCacheDir(), 1024 * 1024); // 1MB cap
        String url =  Constants.PORT_CONECTIVITY+"/api/rooms";
        Map<String, String> jsonParams = new HashMap<>();
        jsonParams.put("name",roomName);
        jsonParams.put("meta", "{}");
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, url, new JSONObject(jsonParams),
                new Response.Listener<JSONObject>()
                {
                    @Override
                    public void onResponse(JSONObject response)
                    {
                        Toast.makeText(activity,response.toString(), Toast.LENGTH_LONG).show();
                    }
                },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error)
                    {
                        if (null != error.networkResponse)
                        {
                            Toast.makeText(activity,error.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    }
                });
        queue.add(request);
    }

    /**
     * Recibe un id de room, arma el URL y manda el delete a la API
     * @param room id del Room a eliminar. Si es conveniente se podria pasar el Room y desde ahi sacarle el id.
     */

    public void deleteRoom(final Room room, final Activity activity) {

        String url =  Constants.PORT_CONECTIVITY+"/api/rooms/" + room.getId();
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.DELETE, url, new JSONObject(),
                new Response.Listener<JSONObject>()
                {
                    @Override
                    public void onResponse(JSONObject response)
                    {
                        Toast.makeText(activity,response.toString(), Toast.LENGTH_LONG).show();
                    }
                },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error)
                    {
                        if (null != error.networkResponse)
                        {
                            ((HomeFragment)((RoomActivity)activity).getFragment()).roomDeleteError(room);
                            Toast.makeText(activity,error.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    }
                });
        queue.add(request);
    }

    public void deleteRutine(final Rutine rutine, final Activity activity) {

        String url =  Constants.PORT_CONECTIVITY+"/api/routines/" + rutine.getId();
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.DELETE, url, new JSONObject(),
                new Response.Listener<JSONObject>()
                {
                    @Override
                    public void onResponse(JSONObject response)
                    {
                        Toast.makeText(activity,response.toString(), Toast.LENGTH_LONG).show();
                    }
                },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error)
                    {
                        if (null != error.networkResponse)
                        {
                           //TODO
                            Toast.makeText(activity,error.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    }
                });
        queue.add(request);
    }

    public void deleteDevice(final Device device, final Activity activity, final int from) {

        String url =  Constants.PORT_CONECTIVITY+"/api/devices/" + device.getId();
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.DELETE, url, new JSONObject(),
                new Response.Listener<JSONObject>()
                {
                    @Override
                    public void onResponse(JSONObject response)
                    {
                        if(from == Constants.DELTE_FROM_DIALOGE){
                            ((DeviceActivity)activity).deviceDeleted();
                        }
                        Toast.makeText(activity,response.toString(), Toast.LENGTH_LONG).show();
                    }
                },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error)
                    {
                        if (null != error.networkResponse)
                        {
                            Toast.makeText(activity,error.getMessage(), Toast.LENGTH_LONG).show();
                            if(from == Constants.DELTE__FROM_OVERFLOW){
                                ((RoomsFragment)((RoomActivity)activity).getFragment()).deviceDeleteError(device);
                            }
                        }
                    }
                });
        queue.add(request);
    }

    public void getRoutines(final Activity activity) {
        String url =  Constants.PORT_CONECTIVITY+"/api/routines";
        cache = new DiskBasedCache(activity.getCacheDir(), 1024 * 1024); // 1MB cap
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, new JSONObject(),
                new Response.Listener<JSONObject>()
                {
                    @Override
                    public void onResponse(JSONObject response) {
                        try{
                            Gson gson = new GsonBuilder().create();
                            Type listType = new TypeToken<ArrayList<Rutine>>() {
                            }.getType();
                            String jsonFragment = response.getString("routines");

                            ArrayList<Rutine> rutinesList = gson.fromJson(jsonFragment, listType);
                            ((RutinesActivity)activity).loadRutines(rutinesList);

                        } catch (Exception exception) {
                            Toast.makeText(activity, exception.getLocalizedMessage(), Toast.LENGTH_LONG).show();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(activity, error.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        queue.add(request);

    }

    /**
     *
     * @param activity activity que lo llama
     * @param device objeto que se quiere prender o apagar, deberia servir para cualquier device
     * @param actionName tiene que ser "turnOn" o "turnOff"  para que luego sea concatenado a la accion"
     */
    public void deviceOnOff(final Activity activity, Device device, final String actionName, final Switch switchView) {
        String deviceId = device.getId();
        cache = new DiskBasedCache(activity.getCacheDir(), 1024 * 1024); // 1MB cap
        String url =  Constants.PORT_CONECTIVITY+"/api/devices/" + deviceId + "/" + actionName;
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.PUT, url, new JSONObject(),
                new Response.Listener<JSONObject>()
                {
                    @Override
                    public void onResponse(JSONObject response) {
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if(actionName.equals("turnOff")){
                    switchView.setChecked(false);
                }else {
                    switchView.setChecked(true);
                }
                Toast.makeText(activity, error.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        queue.add(request);
    }


    public void lampColorChange(final Activity activity, Device device, String color) {
        String deviceId = device.getId();
        cache = new DiskBasedCache(activity.getCacheDir(), 1024 * 1024); // 1MB cap
        String url =  Constants.PORT_CONECTIVITY+"/api/devices/" + deviceId + "/changeColor";
        Map<String, String> jsonParams = new HashMap<>();
        jsonParams.put("color",color);
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.PUT, url, new JSONObject(jsonParams),
                new Response.Listener<JSONObject>()
                {
                    @Override
                    public void onResponse(JSONObject response) {
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(activity, error.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        queue.add(request);
    }

    public void changeLampBrightness(final Activity activity, Device device, Integer brightness, final SeekBar dimmer) {
        String deviceId = device.getId();
        cache = new DiskBasedCache(activity.getCacheDir(), 1024 * 1024); // 1MB cap
        String url =  Constants.PORT_CONECTIVITY+"/api/devices/" + deviceId + "/changeBrightness";
        Map<String, Integer> jsonParams = new HashMap<>();
        jsonParams.put("brightness",brightness);
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.PUT, url, new JSONObject(jsonParams),
                new Response.Listener<JSONObject>()
                {
                    @Override
                    public void onResponse(JSONObject response) {
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(activity, error.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        queue.add(request);
    }

    public void setOvenTemperature(final Activity activity, Device device, Integer temperature,final EditText temperatureEditText) {
        String deviceId = device.getId();
        final String oldTemperature = temperatureEditText.getText().toString();
        cache = new DiskBasedCache(activity.getCacheDir(), 1024 * 1024); // 1MB cap
        String url =  Constants.PORT_CONECTIVITY+"/api/devices/" + deviceId + "/setTemperature";
        Map<String, Integer> jsonParams = new HashMap<>();
        jsonParams.put("temperature",temperature);
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.PUT, url, new JSONObject(jsonParams),
                new Response.Listener<JSONObject>()
                {
                    @Override
                    public void onResponse(JSONObject response) {
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(activity, error.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        queue.add(request);
    }


    /**
     *
     * @param heat solo soporta  "conventional", "bottom", "top"
     */
    public void setOvenHeat(final Activity activity, Device device, String heat, Spinner spinner) {
        String deviceId = device.getId();
        cache = new DiskBasedCache(activity.getCacheDir(), 1024 * 1024); // 1MB cap
        String url =  Constants.PORT_CONECTIVITY+"/api/devices/" + deviceId + "/setHeat";
        Map<String, String> jsonParams = new HashMap<>();
        jsonParams.put("heat",heat);
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.PUT, url, new JSONObject(jsonParams),
                new Response.Listener<JSONObject>()
                {
                    @Override
                    public void onResponse(JSONObject response) {
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(activity, error.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        queue.add(request);
    }

    /**
     * @param grill solo soporta  "large", "eco", "off"
     */
    public void setOvenGrill(final Activity activity, Device device, String grill,Spinner spinner) {
        String deviceId = device.getId();
        cache = new DiskBasedCache(activity.getCacheDir(), 1024 * 1024); // 1MB cap
        String url =  Constants.PORT_CONECTIVITY+"/api/devices/" + deviceId + "/setGrill";
        Map<String, String> jsonParams = new HashMap<>();
        jsonParams.put("grill",grill);
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.PUT, url, new JSONObject(jsonParams),
                new Response.Listener<JSONObject>()
                {
                    @Override
                    public void onResponse(JSONObject response) {
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(activity, error.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        queue.add(request);
    }

    /**
     * convection soporta "normal" "eco" u "off"
     */
    public void setOvenConvection(final Activity activity, Device device, String convection, Spinner spinner) {
        String deviceId = device.getId();
        cache = new DiskBasedCache(activity.getCacheDir(), 1024 * 1024); // 1MB cap
        String url =  Constants.PORT_CONECTIVITY+"/api/devices/" + deviceId + "/setConvection";
        Map<String, String> jsonParams = new HashMap<>();
        jsonParams.put("convection",convection);
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.PUT, url, new JSONObject(jsonParams),
                new Response.Listener<JSONObject>()
                {
                    @Override
                    public void onResponse(JSONObject response) {
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(activity, error.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        queue.add(request);
    }

    public  void getState(final Context context , final Device device, final Activity activity, final Fragment fragment) {

        String url =  Constants.PORT_CONECTIVITY+"/api/devices/" + device.getId() + "/getState";
        cache = new DiskBasedCache(activity.getCacheDir(), 1024 * 1024); // 1MB cap
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.PUT, url, new JSONObject(),
                new Response.Listener<JSONObject>()
                {
                    @Override
                    public void onResponse(JSONObject response) {
                        try{
                            Gson gson = new GsonBuilder().create();
                            Type listType = new TypeToken<ArrayList<Rutine>>() {
                            }.getType();
                            JSONObject jsonObject = response.getJSONObject("result");
                            switch (device.getTypeId()){
                                case Constants.LAMP_ID:
                                    Lamp l = (Lamp) device;
                                    l.setBrightness(jsonObject.getInt("brightness"));
                                    l.setColor(jsonObject.getString("color"));
                                    if(jsonObject.getString("status").equals("off")){
                                        l.setOn(false);
                                    }else{
                                        l.setOn(true);
                                    }
                                    ((LampFragment)fragment).loadLampState(l);
                                    break;
                                case Constants.BLIND_ID:
                                    Blind b = (Blind) device;
                                   b.setStatus(jsonObject.getString("status"));
                                    ((BlindFragment)fragment).loadBlindState(b);
                                   break;
                                case Constants.DOOR_ID:
                                    Door d = (Door) device;
                                    if(jsonObject.getString("status").equals("opened")){
                                        d.setClosed(Constants.DOOR_OPENED);
                                    }else{
                                        d.setClosed(Constants.DOOR_CLOSED);
                                    }
                                    if(jsonObject.getString("lock").equals("unlocked")){
                                        d.setLocked(Constants.DOOR_UNLOCKED);
                                    }else{
                                        d.setLocked(Constants.DOOR_LOCKED);
                                    }
                                    ((DoorFragment)fragment).loadDoorState(d);
                                    break;
                                case Constants.OVEN_ID:
                                    Oven o = (Oven) device;
                                    if(jsonObject.getString("status").equals("off")){
                                        o.setOn(false);
                                    }else{
                                        o.setOn(true);
                                    }
                                    o.setTemperature(jsonObject.getInt("temperature"));
                                    o.setConvection(jsonObject.getString("convection"));
                                    o.setGrill(jsonObject.getString("grill"));
                                    o.setHeat(jsonObject.getString("heat"));
                                    ((OvenFragment)fragment).loadOvenState(o);
                                    break;
                            }

                        } catch (Exception exception) {
                            Toast.makeText(activity, exception.getLocalizedMessage(), Toast.LENGTH_LONG).show();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(activity, error.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        queue.add(request);

    }

    public void openDoor(final Activity activity, Device device,final Switch switchView) {
        String deviceId = device.getId();
        cache = new DiskBasedCache(activity.getCacheDir(), 1024 * 1024); // 1MB cap
        String url =  Constants.PORT_CONECTIVITY+"/api/devices/" + deviceId + "/open";
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.PUT, url, new JSONObject(),
                new Response.Listener<JSONObject>()
                {
                    @Override
                    public void onResponse(JSONObject response) {
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                switchView.setChecked(false);
                Toast.makeText(activity, error.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        queue.add(request);
    }

    public void closeDoor(final Activity activity, Device device, final Switch switchView) {
        String deviceId = device.getId();
        cache = new DiskBasedCache(activity.getCacheDir(), 1024 * 1024); // 1MB cap
        String url =  Constants.PORT_CONECTIVITY+"/api/devices/" + deviceId + "/close";
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.PUT, url, new JSONObject(),
                new Response.Listener<JSONObject>()
                {
                    @Override
                    public void onResponse(JSONObject response) {
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                switchView.setChecked(true);
                Toast.makeText(activity, error.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        queue.add(request);
    }

    public void lockDoor(final Activity activity, Device device, final Switch switchView) {
        String deviceId = device.getId();
        cache = new DiskBasedCache(activity.getCacheDir(), 1024 * 1024); // 1MB cap
        String url =  Constants.PORT_CONECTIVITY+"/api/devices/" + deviceId + "/lock";
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.PUT, url, new JSONObject(),
                new Response.Listener<JSONObject>()
                {
                    @Override
                    public void onResponse(JSONObject response) {
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                switchView.setChecked(true);
                Toast.makeText(activity, error.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        queue.add(request);
    }

    public void unlockDoor(final Activity activity, Device device, final Switch switchView) {
        String deviceId = device.getId();
        cache = new DiskBasedCache(activity.getCacheDir(), 1024 * 1024); // 1MB cap
        String url =  Constants.PORT_CONECTIVITY+"/api/devices/" + deviceId + "/unlock";
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.PUT, url, new JSONObject(),
                new Response.Listener<JSONObject>()
                {
                    @Override
                    public void onResponse(JSONObject response) {
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                switchView.setChecked(false);
                Toast.makeText(activity, error.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        queue.add(request);
    }

    public void blindUp(final Activity activity, Device device, final Switch switchView) {
        String deviceId = device.getId();
        cache = new DiskBasedCache(activity.getCacheDir(), 1024 * 1024); // 1MB cap
        String url =  Constants.PORT_CONECTIVITY+"/api/devices/" + deviceId + "/up";
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.PUT, url, new JSONObject(),
                new Response.Listener<JSONObject>()
                {
                    @Override
                    public void onResponse(JSONObject response) {
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                switchView.setChecked(false);
                Toast.makeText(activity, error.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        queue.add(request);
    }

    public void blindDown(final Activity activity, Device device, final Switch switchView) {
        String deviceId = device.getId();
        cache = new DiskBasedCache(activity.getCacheDir(), 1024 * 1024); // 1MB cap
        String url =  Constants.PORT_CONECTIVITY+"/api/devices/" + deviceId + "/down";
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.PUT, url, new JSONObject(),
                new Response.Listener<JSONObject>()
                {
                    @Override
                    public void onResponse(JSONObject response) {
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                switchView.setChecked(true);
                Toast.makeText(activity, error.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        queue.add(request);
    }
}