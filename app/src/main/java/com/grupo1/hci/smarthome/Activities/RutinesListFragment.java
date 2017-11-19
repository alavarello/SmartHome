package com.grupo1.hci.smarthome.Activities;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ListFragment;
import android.support.v7.widget.Toolbar;
import android.view.ActionMode;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.grupo1.hci.smarthome.Model.APIManager;
import com.grupo1.hci.smarthome.Model.Constants;
import com.grupo1.hci.smarthome.Model.Device;
import com.grupo1.hci.smarthome.Model.Room;
import com.grupo1.hci.smarthome.Model.Rutine;
import com.grupo1.hci.smarthome.R;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by agust on 11/4/2017.
 */

public class RutinesListFragment extends ListFragment implements AdapterView.OnItemClickListener, AdapterView.OnItemLongClickListener{

    // Array of strings...
    ArrayList<Rutine> rutineArray = new ArrayList<>();
    ArrayList<View> selectedElement = new ArrayList<>();
    TextView emptyTextView;
    ActionMode mActionMode;
    Snackbar mySnackbar;
    private CountDownTimer deleteCountDown;
    Toolbar toolbar;
    View view;

    ActionMode.Callback mActionModeCallback;
    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_list, container, false);
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        toolbar = ((NavigationActivity)getActivity()).getToolbar();
        loadRutines();
        setView();


    }

    private void loadRutines() {
        emptyTextView = getActivity().findViewById(R.id.fragmentList_emptyListTextView);
        rutineArray = APIManager.getRutines();
        if(rutineArray.isEmpty()){
            emptyTextView.setVisibility(View.VISIBLE);
        }else{
            emptyTextView.setVisibility(View.GONE);
        }
    }


    private void setView() {
        //set the contextual floating menu
        mActionModeCallback = new RutineContextualMenu();
        ((RutineContextualMenu) mActionModeCallback).setRutineActivity((RutinesActivity) getActivity());
        //set listview Adapter and onCikcListener
        final ArrayAdapter rowAdapter = new RutineAdapter((RutinesActivity)getActivity(), rutineArray);
        setListAdapter(rowAdapter);
        getListView().setVisibility(View.VISIBLE);
        view.findViewById(R.id.gridView).setVisibility(View.GONE);
        getListView().setOnItemClickListener(this);
        getListView().setOnItemLongClickListener(this);
    }

    public void selectedElement(View view,Rutine rutine) {
        view.setBackgroundColor(Color.GRAY);
        view.findViewById(R.id.rowLayout_iconImageView).setBackground(null);
        selectedElement.add(view);
        toolbar.setTitle(rutine.toString());
    }

    public void diselectElements() {
        for(View v: selectedElement){
            v.setBackgroundColor(Color.TRANSPARENT);
        }
            toolbar.setTitle(R.string.title_activity_rutines);
    }

    public void diselectElement(View view) {
        view.setBackgroundColor(Color.TRANSPARENT);
        selectedElement.remove(view);
        if(selectedElement.size() == 1){
            ((RutineContextualMenu)mActionModeCallback).changeToOneItemsMenu();
        }
        if(selectedElement.isEmpty()){
            mActionMode.finish();
        }
    }

    public void deleteRutines(List<Rutine> rutines)
    {
        //setting the snackbar
        mySnackbar = Snackbar.make(getActivity().findViewById(R.id.homeActivity_Fragmentcontainer), "Deleted", Snackbar.LENGTH_LONG);
        mySnackbar.setAction("Undo", new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                deleteCountDown.cancel();
            }
        });

        mySnackbar.show();
        deleteCountDown = new CountDownTimer(4000,1000) {
            @Override
            public void onTick(long l) {

            }

            @Override
            public void onFinish() {
                Toast.makeText(getActivity().getApplicationContext(), "Se borro!!!!!", Toast.LENGTH_SHORT).show();
            }
        }.start();
    }

    @Override
    public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {

        Object o = adapterView.getAdapter().getItem(i);
        Rutine rutine = (Rutine) o;//As you are using Default String Adapter
        Toast.makeText(getActivity().getApplicationContext(), rutine.toString(), Toast.LENGTH_SHORT).show();
        selectedElement(view, rutine);
        if (mActionMode != null) {
            ((RutineContextualMenu) mActionModeCallback).changeToSeveralItemsMenu();
            return false;
        }
        // Start the CAB using the ActionMode.Callback defined above
        ((RutineContextualMenu) mActionModeCallback).addRutine(rutine);
        mActionMode = getActivity().startActionMode(mActionModeCallback);

        return true;
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

        Object o = adapterView.getAdapter().getItem(i);
        Rutine rutine = (Rutine) o;//As you are using Default String Adapter
        Toast.makeText(getActivity().getApplicationContext(), rutine.toString(), Toast.LENGTH_SHORT).show();

        //if is the same view as the selected one
        if(mActionMode == null){
            diselectElements();
        }else{
            ((RoomContextualMenu)mActionModeCallback).changeToSeveralItemsMenu();
            selectedElement(view, rutine);
        }
    }

    public void setmActionMode(ActionMode mActionMode) {
        this.mActionMode = mActionMode;
    }
}
