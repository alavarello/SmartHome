package com.grupo1.hci.smarthome.Model;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.widget.ArrayAdapter;
import android.widget.ListView;
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
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.grupo1.hci.smarthome.Activities.HomeActivity;
import com.grupo1.hci.smarthome.Activities.HomeAdapter;
import com.grupo1.hci.smarthome.Activities.RoomsAdapter;
import com.grupo1.hci.smarthome.R;

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
        String url = "http://10.0.2.2:8080/api/rooms";
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

                            /**
                             * TODO
                             * Armar el adapter
                             */
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

    private void getDevicesForRoom(String roomID, final Activity activity) {

        String url = "http://10.0.2.2:8080/api/rooms/" + roomID + "/devices";
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, new JSONObject(),
                new Response.Listener<JSONObject>()
                {
                    @Override
                    public void onResponse(JSONObject response) {
                        try{
                            Gson gson = new GsonBuilder().create();
                            Type listType = new TypeToken<ArrayList<Device>>() {
                            }.getType();
                            String jsonFragment = response.getString("devices");
                            ArrayList<Device> deviceList = gson.fromJson(jsonFragment, listType);

                            /**
                             * TODO
                             * Lo mismo que en rooms, falta armar el adapter pero deviceList ya tiene
                             * los devices del room
                             */
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
        String url = "http://10.0.2.2:8080/api/rooms";
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
     * @param roomId id del Room a eliminar. Si es conveniente se podria pasar el Room y desde ahi sacarle el id.
     */

    private void deleteRoom(String roomId, final Activity activity) {

        String url = "http://10.0.2.2:8080/api/rooms/" + roomId;
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
                            Toast.makeText(activity,error.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    }
                });
        queue.add(request);
    }

    public void getRoutines(final Activity activity) {
        String url = "http://10.0.2.2:8080/api/routines";
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
                            ArrayList<Rutine> routineList = gson.fromJson(jsonFragment, listType);

                            /**
                             * TODO
                             * Armar el adapter
                             */
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
}
