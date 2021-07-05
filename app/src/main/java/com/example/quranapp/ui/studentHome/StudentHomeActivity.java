package com.example.quranapp.ui.studentHome;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;


import com.example.quranapp.R;
import com.example.quranapp.ui.studentHome.fahrs.FahrsFragment;
import com.example.quranapp.ui.studentHome.ward.WardFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.List;

public class StudentHomeActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {

    private static final String TAG = "HomeActivity";
    private BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        CoordinatorLayout coordinatorLayout = findViewById(R.id.coordinator_layout);
        coordinatorLayout.setVisibility(View.VISIBLE);

        initViews();
    }

    private void initViews() {
        bottomNavigationView = findViewById(R.id.bottomNavigationView);

        bottomNavigationView.setOnNavigationItemSelectedListener(this);
        Menu menu = bottomNavigationView.getMenu();
        this.onNavigationItemSelected(menu.findItem(R.id.fahrs));
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        Fragment currentFragment = getSupportFragmentManager().findFragmentById(R.id.fragment_container);
        Fragment fragment = null;

        switch (item.getItemId()) {
            case R.id.ward:
                if (!(currentFragment instanceof WardFragment)) {
                    fragment = new WardFragment();
                }
                break;

            case R.id.fahrs:
                if (!(currentFragment instanceof FahrsFragment)) {
                    fragment = new FahrsFragment();
                }
                break;

            case R.id.previous_ward:
                Toast.makeText(StudentHomeActivity.this, "previous_ward", Toast.LENGTH_SHORT).show();
                break;
            case R.id.settings:
                Toast.makeText(StudentHomeActivity.this, "settings", Toast.LENGTH_SHORT).show();
                break;
        }


        if (fragment != null) {
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.fragment_container, fragment).commit();
        }

        /*FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();

        switch (item.getItemId()) {
            case R.id.ward:
                 fragmentTransaction = getSupportFragmentManager().beginTransaction();
                fragmentTransaction.add(R.id.fragment_container, new WardFragment()).commit();
                break;
            case R.id.previous_ward:
                Toast.makeText(StudentHomeActivity.this, "previous_ward", Toast.LENGTH_SHORT).show();
                break;
            case R.id.fahrs:
                fragmentTransaction = getSupportFragmentManager().beginTransaction();
                fragmentTransaction.add(R.id.fragment_container, new FahrsFragment()).commit();
                break;
            case R.id.settings:
                Toast.makeText(StudentHomeActivity.this, "settings", Toast.LENGTH_SHORT).show();
                break;
        }*/
        return true;
    }


}