package com.example.animals.Adapter;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.graphics.pdf.PdfDocument;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.animals.Classes.Animals;

import com.example.animals.R;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

public class AnimalAdapter extends FirebaseRecyclerAdapter<Animals, AnimalAdapter.AnimalsViewHolder> {
    private static final int CALL_PERMISSION_REQUEST_CODE = 123; // You can choose any unique number

    public AnimalAdapter(@NonNull FirebaseRecyclerOptions<Animals> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull AnimalsViewHolder holder, int position, @NonNull Animals model) {
        holder.bind(model);
        String key = getRef(position).getKey();

    }

    @NonNull
    @Override
    public AnimalsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.animals, parent, false);
        return new AnimalsViewHolder(view);
    }

    public static class AnimalsViewHolder extends RecyclerView.ViewHolder {
        private TextView textViewSpecies;
        private TextView textViewDescription;
        private TextView textViewTime;
        ImageView avatar;

        public AnimalsViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewSpecies = itemView.findViewById(R.id.textViewSpecies);
            textViewDescription = itemView.findViewById(R.id.textViewDescription);
            textViewTime = itemView.findViewById(R.id.textViewTime);
            avatar = itemView.findViewById(R.id.avatar);
        }

        public void bind(Animals animal) {
            textViewSpecies.setText("Species: " + animal.getSpecies());
            textViewDescription.setText("Description: " + animal.getDescription());
            textViewTime.setText("Time: " + animal.getTime());
            int drawableResource = R.drawable.avatar; // Default icon resource
            String species = animal.getSpecies();
            if (species.equals("Lion")) {
                drawableResource = R.drawable.lion;
            } else if (species.equals("Leopard")) {
                drawableResource = R.drawable.leopard;
            } else if (species.equals("Cheetah")) {
                drawableResource = R.drawable.cheetah;
            } else if (species.equals("Serval")) {
                drawableResource = R.drawable.serval;
            } else if (species.equals("Caracal")) {
                drawableResource = R.drawable.caracal;
            }
            Drawable drawable = itemView.getContext().getResources().getDrawable(drawableResource);
            avatar.setImageDrawable(drawable);
        }
    }


}

