package com.example.quranapp.ui.start;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.quranapp.R;
import com.example.quranapp.prefs.Constant;
import com.example.quranapp.prefs.PreferencesHelperImp;
import com.example.quranapp.ui.addPlan.AddStudentPlanActivity;
import com.example.quranapp.ui.sheikhHome.SheikhHomeActivity;
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
                    PreferencesHelperImp.getInstance().setAccountType(Constant.SHEIKH);
                    startActivity(new Intent(StartActivity.this, SheikhHomeActivity.class));
                    finish();
                } else {
                    //student
                    PreferencesHelperImp.getInstance().setAccountType(Constant.STUDENT);
                    startActivity(new Intent(StartActivity.this, AddStudentPlanActivity.class));
                    finish();
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