package com.grupo1.hci.smarthome.Model;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
import android.util.Log;
import android.view.Menu;
import android.view.SubMenu;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Cache;
import com.android.volley.Network;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.BasicNetwork;
import com.android.volley.toolbox.DiskBasedCache;
import com.android.volley.toolbox.HttpHeaderParser;
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
import com.grupo1.hci.smarthome.Activities.RefrigeratorFragment;
import com.grupo1.hci.smarthome.Activities.RoomActivity;
import com.grupo1.hci.smarthome.Activities.RoomListFragment;
import com.grupo1.hci.smarthome.Activities.RoomsAdapter;
import com.grupo1.hci.smarthome.Activities.RoomsFragment;
import com.grupo1.hci.smarthome.Activities.RutinesActivity;
import com.grupo1.hci.smarthome.Activities.SettingsActivity;
import com.grupo1.hci.smarthome.Activities.SuportDeviceActivity;
import com.grupo1.hci.smarthome.R;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
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

    public void getRooms(final Activity activity, SwipeRefreshLayout swipeRefreshLayout) {
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
                            String jsonFragment = response.getString("rooms");
                            ArrayList<Room> roomList = gson.fromJson(jsonFragment, listType);
                            ((HomeActivity)activity).loadRooms(roomList);
                            ((NavigationActivity)activity).setRoomsArray(roomList);
                            ((NavigationActivity)activity).setMenu();
                        } catch (Exception exception) {
                            Toast.makeText(activity, exception.getLocalizedMessage(), Toast.LENGTH_LONG).show();
                        }
                        if(swipeRefreshLayout != null){
                            swipeRefreshLayout.setRefreshing(false);
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if(swipeRefreshLayout != null){
                    swipeRefreshLayout.setRefreshing(false);
                }
                Toast.makeText(activity, error.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        queue.add(request);

    }

    public void getRoomsForMenu(final Activity activity, Menu menu) {
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
                            String jsonFragment = response.getString("rooms");
                            ArrayList<Room> roomList = gson.fromJson(jsonFragment, listType);
                            menu.removeItem(0);
                           SubMenu m = menu.addSubMenu(R.string.rooms);
                            int i =0;
                            for (Room r : roomList) {
                                m.add(R.id.roomGroupNavigationalDrawer, i++ ,500, r.getName());
                            }
                            if(activity instanceof NavigationActivity){
                                ((NavigationActivity)activity).setRoomsArray(roomList);
                            }else{
                                ((SettingsActivity)activity).setRoomsArray(roomList);
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

    public void getDevicesForRoom(String roomID, final Activity activity, SwipeRefreshLayout swipeRefreshLayout) {

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
                            Type refrigeratorType = new TypeToken<Refrigerator>() {
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
                                else if(typeId.equals(Constants.REFRIGERATOR_ID)){
                                    Refrigerator r = gson.fromJson(j.toString(), refrigeratorType);
                                    deviceList.add(r);
                                }
                            }
                           // String jsonFragment = response.getString("devices");
                          //  ArrayList<Device> deviceList = gson.fromJson(jsonFragment, listType);
                          ((RoomActivity)activity).loadDevices(deviceList);
                            Toast.makeText(activity, response.toString(), Toast.LENGTH_SHORT).show();
                        } catch (Exception exception) {
                            Toast.makeText(activity, exception.getLocalizedMessage(), Toast.LENGTH_LONG).show();
                        }
                        if(swipeRefreshLayout != null){
                            swipeRefreshLayout.setRefreshing(false);
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if(swipeRefreshLayout != null){
                    swipeRefreshLayout.setRefreshing(false);
                }
                Toast.makeText(activity, error.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        queue.add(request);

    }



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
                            ((SuportDeviceActivity)activity).deviceDeleted();
                        }
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

    public void getRoutines(final Activity activity, SwipeRefreshLayout swipeRefreshLayout) {
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
                        if(swipeRefreshLayout != null){
                            swipeRefreshLayout.setRefreshing(false);
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if(swipeRefreshLayout != null){
                    swipeRefreshLayout.setRefreshing(false);
                }
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
                switchView.setChecked( actionName.equals("turnOn"));
                Toast.makeText(activity, error.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        queue.add(request);
    }

    /**
     * ACTIONS with parameters
     */


    public void actionToApi( String deviceId, String actionName, String param, RevertError re) {
        String url =  Constants.PORT_CONECTIVITY+"/api/devices/" + deviceId + "/" + actionName;
        JSONObject params = new JSONObject();
        try {
            params.put("0", param);
        } catch (JSONException e) {
        }
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.PUT, url, params,
                new Response.Listener<JSONObject>()
                {
                    @Override
                    public void onResponse(JSONObject response) {
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                re.revert();
            }

        }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("Content-Type", "application/json");
                return params;
            }
        };

        queue.add(request);
    }

    public void lampColorChange(final Activity activity, Device device, String color) {
        actionToApi(device.getId(),"changeColor", color, new RevertError());
    }

    public void changeLampBrightness(final Activity activity, Device device, Integer brightness, final SeekBar dimmer) {
        RevertError re = new RevertError();
        re.setDimmer(dimmer, ((Lamp)device).getBrightness());
        actionToApi(device.getId(),"changeBrightness", brightness.toString(), re);
    }

    public void setOvenTemperature(final Activity activity, Device device, Integer temperature,final EditText temperatureEditText) {
        RevertError re = new RevertError();
        re.setEditText(temperatureEditText,String.valueOf(temperature) );
        actionToApi(device.getId(),"setTemperature", temperature.toString(), re);
    }

    public void setOvenHeat(final Activity activity, Device device, String heat, Spinner spinner) {
        RevertError re = new RevertError();
        Oven oven = (Oven) device;
        if(oven.getHeat().equals(Constants.OVEN_HEAT_BOTTOM)){
            re.setSpinner(spinner, Constants.OVEN_HEAT_BOTTOM_POSITION);
        }
        else if(oven.getHeat().equals(Constants.OVEN_HEAT_TOP)){
            re.setSpinner(spinner, Constants.OVEN_HEAT_TOP_POSITION);
        }else{
            re.setSpinner(spinner, Constants.OVEN_HEAT_CONVENTIONAL_POSITION);
        }
        actionToApi(device.getId(),"setHeat", heat, re);
    }


    public void setOvenGrill(final Activity activity, Device device, String grill,Spinner spinner) {
        RevertError re = new RevertError();
        Oven oven = (Oven) device;
        if(oven.getGrill().equals(Constants.OVEN_GRILL_ECO)){
            re.setSpinner(spinner, Constants.OVEN_GRILL_ECO_POSITION);
        }
        else if(oven.getGrill().equals(Constants.OVEN_GRILL_LARGE)){
            re.setSpinner(spinner, Constants.OVEN_GRILL_LARGE_POSITION);
        }else{
            re.setSpinner(spinner, Constants.OVEN_GRILL_OFF_POSITION);
        }
        actionToApi(device.getId(),"setGrill", grill, re);
    }

    public void setOvenConvection(final Activity activity, Device device, String convection, Spinner spinner) {
        RevertError re = new RevertError();
        Oven oven = (Oven) device;
        if(oven.getConvection().equals(Constants.OVEN_CONVECTION_ECO)){
            re.setSpinner(spinner, Constants.OVEN_CONVECTION_ECO_POSITION);
        }
        else if(oven.getConvection().equals(Constants.OVEN_CONVECTION_NORMAL)){
            re.setSpinner(spinner, Constants.OVEN_CONVECTION_NORMAL_POSITION);
        }else{
            re.setSpinner(spinner, Constants.OVEN_CONVECTION_OFF_POSITION);
        }
        actionToApi(device.getId(),"setConvection", convection, re);
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
                                case Constants.REFRIGERATOR_ID:
                                    Refrigerator r = (Refrigerator) device;
                                    r.setFreezerTemperature(jsonObject.getInt("freezerTemperature"));
                                    r.setTemperature(jsonObject.getInt("temperature"));
                                    r.setMode(jsonObject.getString("mode"));
                                    ((RefrigeratorFragment)fragment).loadRefriState(r);
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

    public void executeRutine(final Activity activity, Rutine rutine) {
        String routineId = rutine.getId();
        cache = new DiskBasedCache(activity.getCacheDir(), 1024 * 1024); // 1MB cap
        String url =  Constants.PORT_CONECTIVITY+"/api/routines/" + routineId + "/execute";
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.PUT, url, new JSONObject(),
                new Response.Listener<JSONObject>()
                {
                    @Override
                    public void onResponse(JSONObject response) {
                        Toast.makeText(activity, activity.getString(R.string.rutine)+ " " +rutine.getName() + " " + activity.getString(R.string.rutine_execute), Toast.LENGTH_SHORT).show();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
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

    public void setRefriTemp(FragmentActivity activity, Device device, String temperature, EditText refriEditText) {
        RevertError re = new RevertError();
        re.setEditText(refriEditText, temperature);
        actionToApi(device.getId(),"setTemperature", temperature,re);
    }

    public void setFreezerTemp(FragmentActivity activity, Device device, String freezerTemperature, EditText refriEditText) {
        RevertError re = new RevertError();
        re.setEditText(refriEditText, freezerTemperature);
        actionToApi(device.getId(),"setFreezerTemperature", freezerTemperature, re);
    }

    public void setRefriMode(FragmentActivity activity, Device device, String refriMode, Spinner refriSpinner) {
        RevertError re = new RevertError();
        if(refriMode.equals(Constants.REFRIGERATOR_MODE_DEFAULT)){
            re.setSpinner(refriSpinner, 0);
        }
        else if(refriMode.equals(Constants.REFRIGERATOR_MODE_VACATION)){
            re.setSpinner(refriSpinner, 1);
        }else if(refriMode.equals(Constants.REFRIGERATOR_MODE_PARTY)){
            re.setSpinner(refriSpinner, 2);
        }
        actionToApi(device.getId(),"setMode", refriMode, re);
    }

    public void changeName(Activity activity, Object object, String newName, final String oldName, final TextView editText, final ActionBar actionBar) {
        String url = "";

        JSONObject params = new JSONObject();
        try {
            params.put("name", newName);
        } catch (JSONException e) {}
        if(object instanceof Room) {
            url =  Constants.PORT_CONECTIVITY+"/api/rooms/" + ((Room)(object)).getId();
        } else if(object instanceof Device) {
            url =  Constants.PORT_CONECTIVITY+"/api/devices/" + ((Device)(object)).getId();
            try {
                params.put("typeId",((Device) object).getTypeId());
            } catch (JSONException e) {}
        } else if(object instanceof Rutine) {
            url =  Constants.PORT_CONECTIVITY+"/api/routines/" + ((Rutine)(object)).getId();
            try {
                Gson gson = new Gson();
                String actions = gson.toJson(((Rutine) object).getActions());
                params.put("actions",actions);
                params.put("meta","{}");
            } catch (JSONException e) {}
        }
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.PUT, url, params,
                new Response.Listener<JSONObject>()
                {
                    @Override
                    public void onResponse(JSONObject response) {
                       if(object.getClass().equals(Room.class)){
                            ((Room)object).setName(newName);
                        }
                        else if(object.getClass().equals(Rutine.class)){
                            ((Rutine)object).setName(newName);
                        }
                        else{
                           ((Device)object).setName(newName);
                        }
                        ((NavigationActivity)activity).setMenuWithApiCall();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {}
        }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("Content-Type", "application/json");
                return params;
            }
        };

        queue.add(request);
    }
}