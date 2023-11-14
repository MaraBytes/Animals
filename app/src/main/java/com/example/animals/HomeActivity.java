package com.example.animals;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;


import com.example.animals.Fragments.Fragment_Animals;
import com.example.animals.Fragments.Fragment_Maps;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;

public class HomeActivity extends AppCompatActivity {
    private FragmentManager fragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        fragmentManager = getSupportFragmentManager();
        displayFragment(new Fragment_Maps());

        // Handle navigation between fr agments
        BottomNavigationView bottomNavigationView = findViewById(R.id.navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.Home:
                    displayFragment(new Fragment_Maps());
                    return true;
                case R.id.History:
                    displayFragment(new Fragment_Animals());
                    return true;
                default:
                    return false;
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.menu_logout) {
            // Handle logout action
            // For example, sign out the user and navigate to the login screen
            FirebaseAuth.getInstance().signOut();
            startActivity(new Intent(HomeActivity.this, MainActivity.class));
            HomeActivity.this.finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    // Display the specified fragment
    private void displayFragment(Fragment fragment) {
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.fragment_container, fragment);
        transaction.commit();
    }

}