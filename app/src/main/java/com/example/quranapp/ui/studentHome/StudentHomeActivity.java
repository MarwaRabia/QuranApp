package com.example.quranapp.ui.studentHome;

import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;


import com.example.quranapp.R;
import com.example.quranapp.ui.studentHome.fahrs.FahrsFragment;
import com.example.quranapp.ui.studentHome.previousWards.PreviousWardsFragment;
import com.example.quranapp.ui.studentHome.settings.SettingsFragment;
import com.example.quranapp.ui.studentHome.ward.StudentCompleteExamFragment;
import com.example.quranapp.ui.studentHome.ward.WardFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.List;

public class StudentHomeActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {
    private final int REQ_CODE_1 = 100;
    private final int REQ_CODE_2 = 200;
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
        this.onNavigationItemSelected(menu.findItem(R.id.ward));
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
                if (!(currentFragment instanceof PreviousWardsFragment)) {
                    fragment = new PreviousWardsFragment();
                }
                break;
            case R.id.settings:
                if (!(currentFragment instanceof SettingsFragment)) {
                    fragment = new SettingsFragment();
                }
                break;
        }


        if (fragment != null) {
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.fragment_container, fragment).commit();
        }
        return true;
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case REQ_CODE_1:
                if (resultCode == RESULT_OK && null != data) {
                    ArrayList<String> result = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    String answerQ1 = result.get(0);

                    StudentCompleteExamFragment myFragment = (StudentCompleteExamFragment) getSupportFragmentManager().
                            findFragmentByTag("StudentCompleteExamFragment");
                    if (myFragment != null && myFragment.isVisible()) {
                        // add your code here
                        myFragment.setAnswerQ1EditText(answerQ1);
                    }
                }
                break;

            case REQ_CODE_2:
                if (resultCode == RESULT_OK && null != data) {
                    ArrayList<String> result = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    String answerQ2 = result.get(0);

                    StudentCompleteExamFragment myFragment = (StudentCompleteExamFragment) getSupportFragmentManager().
                            findFragmentByTag("StudentCompleteExamFragment");
                    if (myFragment != null && myFragment.isVisible()) {
                        // add your code here
                        myFragment.setAnswerQ2EditText(answerQ2);
                    }
                }
                break;

        }


    }
}