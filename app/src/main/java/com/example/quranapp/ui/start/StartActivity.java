package com.example.quranapp.ui.start;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.quranapp.R;
import com.example.quranapp.prefs.Constant;
import com.example.quranapp.prefs.PreferencesHelperImp;
import com.example.quranapp.ui.addPlan.AddStudentPlanActivity;
import com.example.quranapp.ui.sheikhHome.SheikhHomeActivity;
import com.example.quranapp.ui.splash.SplashActivity;
import com.example.quranapp.ui.studentHome.StudentHomeActivity;
import com.google.android.material.card.MaterialCardView;

public class StartActivity extends AppCompatActivity {
    private MaterialCardView sheikhCardView, studentCardView;
    private Button startButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        initViews();
        setListeners();

    }

    private void setListeners() {

        sheikhCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!sheikhCardView.isChecked())
                    studentCardView.setChecked(false);

                sheikhCardView.toggle();
            }
        });

        studentCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!studentCardView.isChecked())
                    sheikhCardView.setChecked(false);

                studentCardView.toggle();
            }
        });

        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (sheikhCardView.isChecked()) {
                    startActivity(new Intent(StartActivity.this, SheikhHomeActivity.class));
                    finish();
                    PreferencesHelperImp.getInstance().setAccountType(new Constant().SHEIKH);
                } else if (studentCardView.isChecked()) {
                    //student
                    PreferencesHelperImp.getInstance().setAccountType(new Constant().STUDENT);

                    String studentPlan = PreferencesHelperImp.getInstance().getStudentPlan();
                    if (studentPlan != null) {
                        startActivity(new Intent(StartActivity.this, StudentHomeActivity.class));
                        finish();
                    } else {
                        startActivity(new Intent(StartActivity.this, AddStudentPlanActivity.class));
                        finish();
                    }

                } else {
                    Toast.makeText(StartActivity.this, "من فضلك اختار أولا ", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void initViews() {
        sheikhCardView = findViewById(R.id.card_view_sheikh);
        studentCardView = findViewById(R.id.card_view_student);
        startButton = findViewById(R.id.button_start);
    }
}