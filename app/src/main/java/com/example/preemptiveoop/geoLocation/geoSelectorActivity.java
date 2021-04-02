package com.example.preemptiveoop.geoLocation;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;

import com.example.preemptiveoop.R;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import java.util.ArrayList;
import java.util.List;


public class geoSelectorActivity extends AppCompatActivity {


    private boolean permissionOK = false;
    private GoogleMap gMap;
    private FusedLocationProviderClient locationProviderClient;
    private TextView searchGeo;
    LatLng selectedLocation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_geo_selector);

        searchGeo = findViewById(R.id.search_geo);
        getPermission();

    }

    /**
     * get and ask permission to use user's location
     */
    private void getPermission() {
        String[] permissions = {Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_FINE_LOCATION};

        if (ContextCompat.checkSelfPermission(this.getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION)
                == getPackageManager().PERMISSION_GRANTED) {
            if (ContextCompat.checkSelfPermission(this.getApplicationContext(), Manifest.permission.ACCESS_COARSE_LOCATION)
                    == getPackageManager().PERMISSION_GRANTED) {
                permissionOK = true;
                initMap();
            }
        } else {
            ActivityCompat.requestPermissions(this, permissions, 1);
        }
    }

    /**
     * check if user grant the permistion request.
     * @param requestCode
     * @param permissions
     * @param grantResults
     */

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        permissionOK = false;

        switch (requestCode) {
            case 1:
                if (grantResults.length > 0) {

                    for (int i = 0; i < grantResults.length; i++) {
                        if (grantResults[i] != getPackageManager().PERMISSION_GRANTED) {
                            permissionOK = false;
                            return;
                        }
                    }
                    permissionOK = true;
                    initMap();

                }

        }
    }

    /**
     * initialize map
     */
    private void initMap() {
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);

        mapFragment.getMapAsync(new OnMapReadyCallback() {
            @SuppressLint("MissingPermission")
            @Override
            public void onMapReady(GoogleMap googleMap) {
                gMap = googleMap;

                if (permissionOK) {
                    getCurrentLocation();
                    gMap.setMyLocationEnabled(true);
                }


            }
        });
    }


    /**
     * get current location
     */
    private void getCurrentLocation() {
        locationProviderClient = LocationServices.getFusedLocationProviderClient(this);

        try {
            if (ActivityCompat.checkSelfPermission(this,
                    Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                    && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    return;
                }
            final Task location = locationProviderClient.getLastLocation();
            location.addOnCompleteListener(new OnCompleteListener() {
                @Override
                public void onComplete(@NonNull Task task) {
                    if(task.isSuccessful()){
                        Location currLocation = (Location) task.getResult();
                        moveFocus(new LatLng(currLocation.getLatitude(),currLocation.getLongitude()));
                        selectedLocation = new LatLng(currLocation.getLatitude(),currLocation.getLongitude());
                    }
                }
            });

        }catch(Exception e){
            System.out.println(e.getMessage());
        }

    }


    /**
     * onClick listener for search button
     * @param view
     */
    public void searchLocation(View view){
        goToThePlace();
    }




    /**
     * locate user input place
     */
    private void goToThePlace(){

        String inputStr = searchGeo.getText().toString();

        Geocoder geocoder = new Geocoder(this);
        List<Address> list = new ArrayList<>();
        try{
            list = geocoder.getFromLocationName(inputStr,1);
            if(list.size()!= 0){
                Address address = list.get(0);
                moveFocus(new LatLng(address.getLatitude(),address.getLongitude()));
                selectedLocation = new LatLng(address.getLatitude(),address.getLongitude());
            }

        }catch (Exception e){
            System.out.println(e.getMessage());
        }

    }


    /**
     * move focus to the specific place, and mark the place
     * @param latLng
     */
    private void moveFocus(LatLng latLng){
        gMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng,15f));
        MarkerOptions options = new MarkerOptions()
                .position(latLng);
        gMap.addMarker(options);
    }


    /**OnClick listener, indicate user input is finished.
     * get ready for jump to other activity
     * @param view
     */
    public void locationSelectOK(View view){
        Intent i = new Intent();
        i.putExtra("lat",selectedLocation.latitude);
        i.putExtra("lng",selectedLocation.longitude);



        /*
        return ------------- not finish -> modify HERE
        */

        System.out.println("========================="+selectedLocation.latitude);
        System.out.println("========================="+selectedLocation.longitude);

    }
}