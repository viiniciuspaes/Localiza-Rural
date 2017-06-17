package brbr.ufrpe.httpwww.localizarural;

import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;  //will be needeed in the future
import android.widget.Toast; // will be needed maybe


import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapsMain extends FragmentActivity {

    private GoogleMap ruralMap; // Might be null if Google Play services APK is not available.
    public static final float defaltZoom= 15;
    Locations local = new Locations();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_maps_main);
        setUpMapIfNeeded();
        onMapReady(ruralMap); // setting the stating position on the scream
    }

    private void setUpMapIfNeeded() {
        // Do a null check to confirm that we have not already instantiated the map.
        if (ruralMap == null) {
            // Try to obtain the map from the SupportMapFragment.
            ruralMap = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map))
                    .getMap();
            // Check if we were successful in obtaining the map.
            if (ruralMap != null) {
                setUpMap();
            }
        }
    }
    @Override
    protected void onResume() {
        super.onResume();
        setUpMapIfNeeded();
        resumeState();
    }
    protected void onStop(){
        super.onStop();
        MapStateManeger mgr = new MapStateManeger((this));
        mgr.saveMapState(ruralMap);
    }
    public void onMapReady(GoogleMap map) {
        map.setMyLocationEnabled(true);
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(Locations.rural, defaltZoom));  // updating the camera
        map.getUiSettings();
        Markers.addMark(map);


        map.addMarker(new MarkerOptions()
                .title("UFRPE")
                .snippet("Universidade Federal Rural De Pernambuco")
                .position(Locations.rural));
    }
    private void setUpMap() {
        ruralMap.addMarker(new MarkerOptions().position(new LatLng(0, 0)).title("Marker"));  // creates a marker
    }
    private void hideSoftKeyboard(View v) {    // hide keyboard after use
        InputMethodManager imm = (InputMethodManager)getSystemService(INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
    }
    public void resumeState(){
        MapStateManeger mgr = new MapStateManeger((this));
        CameraPosition position= mgr.getSavedCameraPosition();
        if (position != null) {
            CameraUpdate update = CameraUpdateFactory.newCameraPosition(position);
            ruralMap.moveCamera(update);
        }
    }
// nao lembro pra que isso serve
    public void setVisible(){

    }
    public void geoLocate(View v){    // in construction && ask the user for a location name or cordinate and then generates de marker, still need to update de camera
        hideSoftKeyboard(v);

       // EditText et =(EditText) findViewById(R.id.editText1);
       // String location = et.getText().toString();

       // Toast.makeText(this,location,Toast.LENGTH_LONG).show();
    }
    private void goToLocation(LatLng position){    // nome auto explicativo
        CameraUpdate uptade= CameraUpdateFactory.newLatLng(position);
        ruralMap.moveCamera(uptade);
    }
    }