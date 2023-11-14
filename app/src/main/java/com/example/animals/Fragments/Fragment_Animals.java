package com.example.animals.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.animals.Adapter.AnimalAdapter;
import com.example.animals.Classes.Animals;
import com.example.animals.R;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Fragment_Animals extends Fragment {

    private RecyclerView recyclerView;
    private DatabaseReference databaseRef;
    private FirebaseAuth mAuth;
    AnimalAdapter animalAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_animals, container, false);
        mAuth = FirebaseAuth.getInstance();
        recyclerView = view.findViewById(R.id.recyclerAnimals);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);

        // Set up Firebase database reference
        databaseRef = FirebaseDatabase.getInstance().getReference().child("animals");
        FirebaseRecyclerOptions<Animals> options = new FirebaseRecyclerOptions.Builder<Animals>()
                .setQuery(databaseRef, Animals.class)
                .build();
        animalAdapter = new AnimalAdapter(options);
        recyclerView.setAdapter(animalAdapter);

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        animalAdapter.startListening();
    }

    @Override
    public void onStop() {
        super.onStop();
        animalAdapter.stopListening();
    }
}