package com.example.quranapp.ui.sheikhHome;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.quranapp.R;
import com.example.quranapp.prefs.Constant;
import com.example.quranapp.prefs.PreferencesHelperImp;
import com.example.quranapp.ui.generateTest.GenerateTestActivity;
import com.example.quranapp.ui.start.StartActivity;
import com.example.quranapp.ui.studentHome.StudentHomeActivity;

public class SheikhHomeActivity extends AppCompatActivity {
    private Button generateTestButton;
    private TextView changeAccountTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sheikh_home);

        generateTestButton = findViewById(R.id.button_generate_test);
        generateTestButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(SheikhHomeActivity.this, GenerateTestActivity.class));
            }
        });

        changeAccountTextView = findViewById(R.id.change_account_text);
        changeAccountTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PreferencesHelperImp.getInstance().removeKey(new Constant().ACCOUNT_TYPE);
                startActivity(new Intent(SheikhHomeActivity.this, StartActivity.class));
                finish();
            }
        });
    }
}