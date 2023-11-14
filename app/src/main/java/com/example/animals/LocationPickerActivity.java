package com.example.animals;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.Manifest;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;

public class LocationPickerActivity extends AppCompatActivity implements OnMapReadyCallback {

    private GoogleMap googleMap;
    private LatLng selectedLocation;
    private Marker selectedMarker;
    private FusedLocationProviderClient fusedLocationProviderClient;
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location_picker);
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    LOCATION_PERMISSION_REQUEST_CODE);
        } else {
            // Permissions are already granted; proceed with map setup
            setupMap();
        }
        Button buttonDone = findViewById(R.id.buttonDone);
        buttonDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (selectedLocation != null) {
                    // Return the selected location to the calling activity (PostActivity)
                    Intent resultIntent = new Intent();
                    resultIntent.putExtra("latitude", selectedLocation.latitude);
                    resultIntent.putExtra("longitude", selectedLocation.longitude);
                    setResult(RESULT_OK, resultIntent);
                }
                finish();
            }
        });

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        if (mapFragment != null) {
            mapFragment.getMapAsync(this);
        }
    }

    private void setupMap() {
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        if (mapFragment != null) {
            mapFragment.getMapAsync(new OnMapReadyCallback() {
                @Override
                public void onMapReady(GoogleMap googleMap) {
                    // Enable the "My Location" button on the map
                    if (ActivityCompat.checkSelfPermission(LocationPickerActivity.this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(LocationPickerActivity.this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

                        return;
                    }
                    googleMap.setMyLocationEnabled(true);
                    // Other map setup code here
                }
            });
        }
    }


    public void onMapReady(GoogleMap map) {
        googleMap = map;

        googleMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {
                // Store the selected location
                selectedLocation = latLng;

                // Remove the previous marker if it exists
                if (selectedMarker != null) {
                    selectedMarker.remove();
                }

                // Add a marker to the selected location
                selectedMarker = googleMap.addMarker(new MarkerOptions()
                        .position(selectedLocation)
                        .title("Selected Location")
                        .icon(BitmapDescriptorFactory.fromResource(R.drawable.location))); // Replace "your_custom_marker_icon" with your marker image resource

                // You can customize the marker properties (e.g., icon, title) as needed
            }
        });
    }

    public void onBackPressed() {
        // Handle the back button press to return the selected location to the calling activity (PostActivity)
        if (selectedLocation != null) {
            Intent resultIntent = new Intent();
            resultIntent.putExtra("latitude", selectedLocation.latitude);
            resultIntent.putExtra("longitude", selectedLocation.longitude);
            setResult(RESULT_OK, resultIntent);
        }
        super.onBackPressed();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == LOCATION_PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission granted; proceed with map setup
                setupMap();
            } else {
                // Permission denied; handle this case as needed (e.g., show a message)
            }
        }
    }

}
