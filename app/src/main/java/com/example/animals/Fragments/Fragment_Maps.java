package com.example.animals.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import com.example.animals.PostActivity;
import com.example.animals.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Fragment_Maps extends Fragment implements OnMapReadyCallback {

    private GoogleMap mMap;
    CardView postCard;
    private ProgressBar progressBar;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_maps, container, false);
        postCard = view.findViewById(R.id.postCard);
        progressBar = view.findViewById(R.id.progressBar); // Reference the ProgressBar
        postCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(view.getContext(), PostActivity.class));
            }
        });
        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        return view;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        // Initialize Firebase
        progressBar.setVisibility(View.VISIBLE); // Show the progress bar
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference animalsRef = database.getReference("animals");

        // Retrieve latitude and longitude data from Firebase
        animalsRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                LatLngBounds.Builder builder = new LatLngBounds.Builder();
                boolean validData = false; // Flag to check if there is any valid data

                for (DataSnapshot animalSnapshot : dataSnapshot.getChildren()) {
                    // Retrieve species, latitude, and longitude from the database
                    String species = animalSnapshot.child("species").getValue(String.class);
                    Double latitude = animalSnapshot.child("latitude").getValue(Double.class);
                    Double longitude = animalSnapshot.child("longitude").getValue(Double.class);

                    if (species != null && latitude != null && longitude != null) {
                        LatLng animalLocation = new LatLng(latitude, longitude);

                        // Create a custom marker icon based on the species
                        int markerIconResourceId;
                        if (species.equals("Lion")) {
                            markerIconResourceId = R.drawable.lion; // Replace with your lion icon resource
                        } else if (species.equals("Leopard")) {
                            markerIconResourceId = R.drawable.leopard; // Replace with your leopard icon resource
                        } else if (species.equals("Cheetah")) {
                            markerIconResourceId = R.drawable.cheetah; // Replace with your cheetah icon resource
                        } else if (species.equals("Serval")) {
                            markerIconResourceId = R.drawable.serval;
                        } else if (species.equals("Caracal")) {
                            markerIconResourceId = R.drawable.caracal;
                        } else {
                            // If the species is not recognized, you can set a default marker icon
                            markerIconResourceId = R.drawable.location;
                        }

                        // Add markers to the map with the custom icon
                        MarkerOptions markerOptions = new MarkerOptions()
                                .position(animalLocation)
                                .title(species)
                                .icon(BitmapDescriptorFactory.fromResource(markerIconResourceId));

                        mMap.addMarker(markerOptions);

                        // Extend the bounds to include this marker
                        builder.include(animalLocation);
                        validData = true; // Valid data is found
                    }
                }

                if (validData) {
                    // After all valid markers have been added, calculate bounds and move camera
                    LatLngBounds bounds = builder.build();
                    mMap.animateCamera(CameraUpdateFactory.newLatLngBounds(bounds, 100)); // Adjust padding as needed
                    progressBar.setVisibility(View.GONE);

                } else {
                    progressBar.setVisibility(View.GONE);

                    // Handle the case when there is no valid data (e.g., show a message to the user)
                    Toast.makeText(getContext(), "No valid animal locations found.", Toast.LENGTH_SHORT).show();
                }
            }


            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(getContext(), "Failed to load animal locations.", Toast.LENGTH_SHORT).show();
            }
        });
    }
}