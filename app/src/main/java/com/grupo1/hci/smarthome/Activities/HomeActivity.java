package com.grupo1.hci.smarthome.Activities;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.Toolbar;
import android.view.ActionMode;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;


import com.android.volley.AuthFailureError;
import com.android.volley.Cache;
import com.android.volley.DefaultRetryPolicy;
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
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.grupo1.hci.smarthome.Model.Constants;
import com.grupo1.hci.smarthome.Model.Room;
import com.grupo1.hci.smarthome.R;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.Serializable;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class HomeActivity extends NavigationActivity {

    // Array of strings...
    View selectedElement;
    ListView listView;
    Toolbar toolbar;
    ActionMode mActionMode;
    Snackbar mySnackbar;
    private CountDownTimer deleteCountDown;

    public void setmActionMode(ActionMode mActionMode) {
        this.mActionMode = mActionMode;
    }

    ActionMode.Callback mActionModeCallback;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setView();
        setOnClickListener();

    }

    private void setOnClickListener() {
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View view, int position, long arg3) {
                Object o = arg0.getAdapter().getItem(position);
                Room room = (Room) o;//As you are using Default String Adapter
                Toast.makeText(getApplicationContext(), room.getName() + " ShortClick", Toast.LENGTH_SHORT).show();
                //if is the same view as the selected one
                if (mActionMode == null) {
                    diselectElement();
                    Intent intent = new Intent(HomeActivity.this.getApplicationContext(), RoomActivity.class);
                    intent.putExtra(Constants.ROOM_ARRAY_INTENT, (Serializable) roomsArray);
                    intent.putExtra(Constants.ROOM_INTENT, (Serializable) room);
                    startActivity(intent);
                }
            }
        });

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> arg0, View view, int position, long id) {
                Object o = arg0.getAdapter().getItem(position);
                Room room = (Room) o;//As you are using Default String Adapter

                Toast.makeText(getApplicationContext(), room.getName() + " LongClick", Toast.LENGTH_SHORT).show();
                if (mActionMode != null) {
                    return false;
                }
                // Start the CAB using the ActionMode.Callback defined above
                ((HomeContextualMenu) mActionModeCallback).setRoom(room);
                mActionMode = HomeActivity.this.startActionMode(mActionModeCallback);
                selectedElement(view, room);

                return true;
            }
        });

    }

    private void setView() {
        listView = (ListView) findViewById(R.id.contentRoom_ListView);
        //set the contextual floating menu
        mActionModeCallback = new HomeContextualMenu();
        ((HomeContextualMenu) mActionModeCallback).setHomeActivity(this);
        //set listview Adapter and onCikcListener
        //ArrayAdapter rowAdapter = new HomeAdapter(this, roomsArray);
        //listView.setAdapter(rowAdapter);
    }


    public void deleteRoom(String roomId) {
        //setting the snackbar
        mySnackbar = Snackbar.make(findViewById(R.id.contentRoom_ListView), "Deleted", Snackbar.LENGTH_LONG);
        mySnackbar.setAction("Undo", new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                deleteCountDown.cancel();
            }
        });

        mySnackbar.show();
        deleteCountDown = new CountDownTimer(4000, 1000) {
            @Override
            public void onTick(long l) {

            }

            @Override
            public void onFinish() {
                Toast.makeText(getApplicationContext(), "Se borro!!!!!", Toast.LENGTH_SHORT).show();
            }
        }.start();
    }

    public void selectedElement(View view, Room room) {
        view.setBackgroundColor(Color.GRAY);
        view.findViewById(R.id.rowLayout_iconImageView).setBackground(null);
        selectedElement = view;
        toolbar.setTitle(room.getName());
    }

    public void diselectElement() {
        if (selectedElement != null) {
            selectedElement.setBackgroundColor(Color.TRANSPARENT);
            toolbar.setTitle(Constants.appName);
        }
    }


    /**
     * @author sswinnen
     * Consulta los rooms a la API y a partir de lo que obtiene construye el ArrayAdapter de todos
     * los rooms. Falta hacer bien la construccion, la estrucutura del llamado a la API creo que funciona
     * Por ahora cuando la llamo no esta haciendo nada pero no tira error.
     */
        private void getRoomsFromAPI() {
        RequestQueue mRequestQueue;
        Cache cache = new DiskBasedCache(getCacheDir(), 1024 * 1024); // 1MB cap
        Network network = new BasicNetwork(new HurlStack());
        mRequestQueue = new RequestQueue(cache, network);
        mRequestQueue.start();

        String url = "http://10.0.3.2:8080/api/rooms";

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        Gson gson = new Gson();
                        Type listType = new TypeToken<ArrayList<Room>>() {}.getType();
                        ArrayList<Room> roomList = gson.fromJson(response, listType);
                        getRoomsFromAPI();

                        ListView listView = (ListView) findViewById(R.id.contentRoom_ListView);
                        if (listView != null) {
                            ArrayAdapter arrayAdapter = new HomeAdapter(getApplicationContext(),roomList);
                            listView.setAdapter(arrayAdapter);
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), error.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        stringRequest.setRetryPolicy(new DefaultRetryPolicy(40000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        mRequestQueue.start();

        mRequestQueue.add(stringRequest);

    }

    /**
     * @author sswinnen
     * Recibe un nombre y un meta, (si el meta es vacio pasarle ""), y gson construye el JSON
     * para mandarle a la API
     * @param roomName nombre elegido por el usuario
     * @param meta vacio en caso de no querer usarlo
     */

    private void newRoomToAPI(final String roomName, final String meta) {

        String url = "http://10.0.3.2:8080/api/rooms";
        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        StringRequest sr = new StringRequest(Request.Method.POST,url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Toast.makeText(getApplicationContext(), "Posteo bien a la API", Toast.LENGTH_SHORT).show();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), error.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("name", roomName);
                params.put("meta", meta);
                return params;
            }

        };
        queue.add(sr);
    }

    /**
     * Recibe un id de room, arma el URL y manda el delete a la API
     * @param roomId id del Room a eliminar. Si es conveniente se podria pasar el Room y desde ahi sacarle el id.
     */

    private void deleteRoomFromAPI(String roomId) {
        String url = "http://10.0.3.2:8080/api/rooms" + roomId;
        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        StringRequest sr = new StringRequest(Request.Method.DELETE,url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Toast.makeText(getApplicationContext(), "Posteo bien a la API", Toast.LENGTH_SHORT).show();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), error.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            }
        });
        queue.add(sr);
    }
}
