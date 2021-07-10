package com.example.quranapp.ui.splash;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.example.quranapp.R;
import com.example.quranapp.prefs.Constant;
import com.example.quranapp.prefs.PreferencesHelper;
import com.example.quranapp.prefs.PreferencesHelperImp;
import com.example.quranapp.ui.addPlan.AddStudentPlanActivity;
import com.example.quranapp.ui.sheikhHome.SheikhHomeActivity;
import com.example.quranapp.ui.start.StartActivity;
import com.example.quranapp.ui.studentHome.StudentHomeActivity;


public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(2000);
                    checkIfUserHasAccountAndPlan();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();

    }

    private void checkIfUserHasAccountAndPlan() {
        String accountType = PreferencesHelperImp.getInstance().getAccountType();
        if (accountType != null) {
            if (accountType.equals(new Constant().SHEIKH)) {
                startActivity(new Intent(SplashActivity.this, SheikhHomeActivity.class));
                finish();
            } else {
                //student
                String studentPlan = PreferencesHelperImp.getInstance().getStudentPlan();
                if (studentPlan != null) {
                    startActivity(new Intent(SplashActivity.this, StudentHomeActivity.class));
                    finish();
                } else {
                    startActivity(new Intent(SplashActivity.this, AddStudentPlanActivity.class));
                    finish();
                }
            }

        } else {
            // first time
            startActivity(new Intent(SplashActivity.this, StartActivity.class));
            finish();
        }
    }
}