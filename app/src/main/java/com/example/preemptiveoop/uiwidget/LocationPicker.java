package com.example.preemptiveoop.uiwidget;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;

import com.example.preemptiveoop.R;
import com.example.preemptiveoop.uiwidget.model.MyLocation;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;

import java.io.IOException;
import java.util.List;

/**
 * The LocationPicker class is the activity class that builds and manages the UI for the user to select
 * a location.
 */
public class LocationPicker extends FragmentActivity implements OnMapReadyCallback {
    private final int PERMISSION_REQUEST_ACCESS_FINE_LOCATION = 1;
    private final int DEFAULT_ZOOM = 15;

    private GoogleMap mMap;
    private Location selectedLocation;

    private EditText etSearch;
    private Button btSearch;

    private Button btCurrLocation, btFinish;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location_picker);

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    /**
     * Manipulates the map once available. This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        selectedLocation = null;

        etSearch = findViewById(R.id.EditText_search);
        btSearch = findViewById(R.id.Button_search);

        btCurrLocation = findViewById(R.id.Button_currLocation);
        btFinish       = findViewById(R.id.Button_finish);

        getLocationPermission();
        mMap.setOnMapClickListener(this::onMapClick);

        btSearch.setOnClickListener(this::btSearchOnClick);

        btCurrLocation.setOnClickListener(this::btCurrLocationOnClick);
        btFinish.setOnClickListener(this::btFinishOnClick);

        getDeviceLocation();
    }

    /**
     * Request location permission, so that we can get the location of the device.
     * The result of the permission request is handled by a callback, onRequestPermissionsResult.
     */
    private void getLocationPermission() {
        if (ContextCompat.checkSelfPermission(this.getApplicationContext(), android.Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            return;
        }
        ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},
                PERMISSION_REQUEST_ACCESS_FINE_LOCATION);
    }

    /**
     * Handles the result of the request for location permissions.
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        // super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch(requestCode) {
            case PERMISSION_REQUEST_ACCESS_FINE_LOCATION:
                if (!(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                    MyDialog.messageDialog(this, "Permission Denied", "Please grant this app location access.");
                    finish();
                }
                break;
        }
    }

    /**
     * Get the best and most recent location of the device, which may be null in rare
     * cases when a location is not available.
     */
    private void getDeviceLocation() {
        FusedLocationProviderClient client = LocationServices.getFusedLocationProviderClient(this);

        try {
            client.getLastLocation()
                    .addOnSuccessListener(new OnSuccessListener<Location>() {
                        @Override
                        public void onSuccess(Location location) {
                            if (location == null)
                                return;
                            selectedLocation = location;
                            LatLng latlng = new LatLng(location.getLatitude(), location.getLongitude());

                            mMap.clear();
                            mMap.addMarker(new MarkerOptions().position(latlng).title("selected"));
                            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latlng, DEFAULT_ZOOM));
                        }
                    });
        }
        catch (SecurityException e) { MyDialog.messageDialog(this, "SecurityException", e.toString()); }
    }

    /**
     * Callback for OnMapClickListener, which is triggered when the map is clicked.
     */
    public void onMapClick(LatLng point) {
        selectedLocation = new Location("");

        selectedLocation.setLatitude(point.latitude);
        selectedLocation.setLongitude(point.longitude);

        mMap.clear();
        mMap.addMarker(new MarkerOptions().position(point).title("selected"));
    }

    public void btSearchOnClick(View v) {
        String searchStr = etSearch.getText().toString();

        Geocoder geocoder = new Geocoder(this);
        List<Address> results = null;

        try { results = geocoder.getFromLocationName(searchStr, 1); }
        catch (IOException e) { return; }

        if (results == null || results.isEmpty())
            return;
        Address first = results.get(0);

        selectedLocation = new Location("");

        selectedLocation.setLatitude(first.getLatitude());
        selectedLocation.setLongitude(first.getLongitude());

        LatLng latlng = new LatLng(first.getLatitude(), first.getLongitude());
        mMap.clear();
        mMap.addMarker(new MarkerOptions().position(latlng).title("selected"));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latlng, DEFAULT_ZOOM));
    }

    public void btCurrLocationOnClick(View v) {
        getDeviceLocation();
    }

    public void btFinishOnClick(View v) {
        MyLocation location = new MyLocation(selectedLocation.getLatitude(), selectedLocation.getLongitude());

        // return MyLocation object through intent
        Intent intent = new Intent();
        intent.putExtra(".location", location);
        setResult(Activity.RESULT_OK, intent);
        finish();
    }
}
