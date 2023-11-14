package com.example.animals;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.animals.Classes.Animals;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class PostActivity extends AppCompatActivity {
    private String[] kenyanCats = {"Lion", "Leopard", "Cheetah", "Serval", "Caracal"};
    private EditText editTextDescription;
    private Button buttonPickLocation;
    private Spinner spinnerSpecies;
    private TextView textViewLocation;

    private double latitude = 0.0;
    private double longitude = 0.0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);
        spinnerSpecies = findViewById(R.id.spinnerSpecies);
        editTextDescription = findViewById(R.id.editTextDescription);
        buttonPickLocation = findViewById(R.id.buttonPickLocation);
        textViewLocation = findViewById(R.id.textViewLocation);

        buttonPickLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Start the LocationPickerActivity to pick a location
                Intent intent = new Intent(PostActivity.this, LocationPickerActivity.class);
                startActivityForResult(intent, 1);
            }
        });
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, kenyanCats);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerSpecies.setAdapter(adapter);

        Button buttonSave = findViewById(R.id.buttonSave);
        buttonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Get the animal data
                String selectedCat = spinnerSpecies.getSelectedItem().toString();
                String description = editTextDescription.getText().toString();
                Date currentDate = new Date();

// Define the date format pattern for "Monday 12, 2020 10:09PM"
                SimpleDateFormat dateFormat = new SimpleDateFormat("EEEE dd, yyyy hh:mma", Locale.getDefault());

// Format the current date to a string
                String formattedDate = dateFormat.format(currentDate);
                // Create an instance of the Animals class with the data
                Animals animal = new Animals(selectedCat, description, latitude, longitude, formattedDate);

                // Get a reference to your Firebase Realtime Database
                FirebaseDatabase database = FirebaseDatabase.getInstance();
                DatabaseReference animalsRef = database.getReference("animals");

                // Push the animal data to the "animals" child in the database
                animalsRef.push().setValue(animal);

                // Display a message or perform any other necessary actions
                textViewLocation.setText("Latitude: " + latitude + ", Longitude: " + longitude);
                finish();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1) {
            if (resultCode == RESULT_OK) {
                // Retrieve the selected latitude and longitude from LocationPickerActivity
                latitude = data.getDoubleExtra("latitude", 0.0);
                longitude = data.getDoubleExtra("longitude", 0.0);

                // Update the textViewLocation with the selected coordinates
                textViewLocation.setText("Latitude: " + latitude + ", Longitude: " + longitude);
            }
        }
    }
}
