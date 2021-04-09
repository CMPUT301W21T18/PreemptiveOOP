package com.example.preemptiveoop.uiwidget;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;

import com.example.preemptiveoop.R;
import com.example.preemptiveoop.trial.model.GenericTrial;
import com.example.preemptiveoop.uiwidget.model.MyLocation;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;

import java.util.ArrayList;

/**
 * The TrialLocationsDisp class is the activity class that builds and manages the UI for displaying
 * a map of trial locations.
 */
public class TrialLocationsDisp extends FragmentActivity implements OnMapReadyCallback {
    private final int PERMISSION_REQUEST_ACCESS_FINE_LOCATION = 1;
    private final int DEFAULT_ZOOM = 15;

    private GoogleMap mMap;
    private ArrayList<GenericTrial> trials;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trial_locations_disp);

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

        // get passed-in arguments
        Intent intent = getIntent();
        trials = (ArrayList<GenericTrial>) intent.getSerializableExtra(".trials");

        if (trials == null)
            throw new IllegalArgumentException("Expected '.trials' passed-in through intent.");

        getLocationPermission();
        updateMapDisplay();
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
                            LatLng latlng = new LatLng(location.getLatitude(), location.getLongitude());

                            mMap.addMarker(new MarkerOptions().position(latlng)
                                    .title("Your Location")
                                    .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE))
                            );
                            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latlng, DEFAULT_ZOOM));
                        }
                    });
        }
        catch (SecurityException e) { MyDialog.messageDialog(this, "SecurityException", e.toString()); }
    }

    private void dispTrialLocations() {
        for (GenericTrial trial : trials) {
            MyLocation location = trial.getLocation();
            if (location == null) continue;

            LatLng latlng = new LatLng(location.getLatitude(), location.getLongitude());
            if (trial.isIgnored())
                mMap.addMarker(new MarkerOptions().position(latlng)
                        .title("Ignored Trial")
                        .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_YELLOW))
                );
            else
                mMap.addMarker(new MarkerOptions().position(latlng).title("Result: " + trial.getResultStr()));
        }
    }

    public void updateMapDisplay() {
        mMap.clear();
        getDeviceLocation();
        dispTrialLocations();
    }
}
