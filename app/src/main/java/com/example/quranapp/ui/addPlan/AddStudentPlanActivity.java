package com.example.quranapp.ui.addPlan;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.quranapp.R;
import com.example.quranapp.db.DbHandler;
import com.example.quranapp.prefs.Constant;
import com.example.quranapp.prefs.PreferencesHelperImp;
import com.example.quranapp.ui.generateTest.GenerateQuestion;
import com.example.quranapp.ui.studentHome.StudentHomeActivity;
import com.google.android.material.textfield.TextInputEditText;
import com.google.gson.Gson;
import com.reginald.editspinner.EditSpinner;

import java.util.ArrayList;

public class AddStudentPlanActivity extends AppCompatActivity {
    private EditSpinner suraNameStartEditSpinner, ayaNameStartEditSpinner, suraNameEndEditSpinner,
            ayaNameEndEditSpinner, amountHafzTypeEditSpinner;
    private Button startHafzButton;
    private TextView planTimeTextView;
    private TextInputEditText wardCountEditText;
    private String suraIdStart, ayaIdStart, suraIdEnd, ayaIdEnd, wardCountType,
            wardCount, keyIdStart, keyIdEnd;
    private ArrayList<Integer> ayaNumStartArrayList, ayaNumEndArrayList;
    private ArrayList<String> amountHafzTypeArrayList;
    private ArrayAdapter<Integer> ayaNumStartAdapter, ayaNumEndAdapter;
    private DbHandler dbHandler;
    private int planTimeCount, verseCount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_add_plan);

        initViews();
        setUpDataSpinner();
        setListeners();
    }

    @SuppressLint("ClickableViewAccessibility")
    private void setListeners() {

        suraNameStartEditSpinner.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                suraIdStart = String.valueOf(i + 1);
                Log.i("suraNameStart: ", suraIdStart);

                // set up ayat numbers start
                ayaNumStartArrayList = dbHandler.getNumOfAyatInsideSura(String.valueOf(i + 1));

                ayaNumStartAdapter = new ArrayAdapter<>(AddStudentPlanActivity.this,
                        android.R.layout.simple_spinner_dropdown_item, ayaNumStartArrayList);
                ayaNameStartEditSpinner.setAdapter(ayaNumStartAdapter);
            }
        });

        ayaNameStartEditSpinner.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                ayaIdStart = String.valueOf(i + 1);
                Log.i("ayaNumStart: ", ayaIdStart);
            }
        });

        ayaNameStartEditSpinner.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    if (suraIdStart == null)
                        Toast.makeText(AddStudentPlanActivity.this, "Please choose the name of the sura first", Toast.LENGTH_SHORT).show();
                }
                return false;
            }
        });

        suraNameEndEditSpinner.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                suraIdEnd = String.valueOf(i + 1);
                Log.i("suraNameEnd: ", suraIdEnd);

                // set up ayat numbers end
                ayaNumEndArrayList = dbHandler.getNumOfAyatInsideSura(String.valueOf(i + 1));

                ayaNumEndAdapter = new ArrayAdapter<>(AddStudentPlanActivity.this,
                        android.R.layout.simple_spinner_dropdown_item, ayaNumEndArrayList);
                ayaNameEndEditSpinner.setAdapter(ayaNumEndAdapter);
            }
        });

        ayaNameEndEditSpinner.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                ayaIdEnd = String.valueOf(i + 1);
                Log.i("ayaNumEnd: ", ayaIdEnd);

            }
        });

        ayaNameEndEditSpinner.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    if (suraIdEnd == null)
                        Toast.makeText(AddStudentPlanActivity.this, "Please choose the name of the sura first", Toast.LENGTH_SHORT).show();
                }
                return false;
            }
        });

        amountHafzTypeEditSpinner.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_UP) {

                    wardCount = wardCountEditText.getText().toString();

                    if (suraIdStart == null || ayaIdStart == null || suraIdEnd == null
                            && ayaIdEnd == null || wardCount.isEmpty()) {
                        Toast.makeText(AddStudentPlanActivity.this, "Please fill your data first", Toast.LENGTH_SHORT).show();
                    } else {
                        ArrayAdapter<String> questionTypeAdapter = new ArrayAdapter<String>(AddStudentPlanActivity.this, android.R.layout.simple_spinner_dropdown_item, amountHafzTypeArrayList);
                        amountHafzTypeEditSpinner.setAdapter(questionTypeAdapter);
                    }
                }
                return false;
            }
        });

        amountHafzTypeEditSpinner.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                wardCountType = amountHafzTypeArrayList.get(i);

            /*    if (!planTimeTextView.getText().toString().isEmpty())
                    planTimeTextView.setText("سيستغرق الحفظ: ");
*/
                //   planTimeCount = calculatePlanTimeCount();
                //  planTimeTextView.setText("سيستغرق الحفظ: " + planTimeCount + " يوما ");
            }
        });

        startHafzButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (suraIdStart != null && ayaIdStart != null && suraIdEnd != null
                        && ayaIdEnd != null && wardCountType != null && !wardCount.isEmpty()) {

                    int idStart = dbHandler.getKeyId(suraIdStart, ayaIdStart);
                    int idEnd = dbHandler.getKeyId(suraIdEnd, ayaIdEnd);

                    if (idStart > idEnd) {
                        idStart = dbHandler.getKeyId(suraIdEnd, ayaIdEnd);
                        idEnd = dbHandler.getKeyId(suraIdStart, ayaIdStart);
                    }

                    if (idEnd - idStart < 10) {
                        Toast.makeText(AddStudentPlanActivity.this, "يجب ان تكون مقدار الحفظ أكبر من 10 أيات", Toast.LENGTH_SHORT).show();
                    } else {
                        Plan plan = new Plan(keyIdStart, keyIdEnd, wardCount, wardCountType,
                                String.valueOf(planTimeCount));
                        Gson gson = new Gson();
                        String json = gson.toJson(plan);
                        PreferencesHelperImp.getInstance().setStudentPlan(json);

                        startActivity(new Intent(AddStudentPlanActivity.this, StudentHomeActivity.class));
                        finish();
                    }

                } else {
                    Toast.makeText(AddStudentPlanActivity.this, "Please fill your data first", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }


    private void setUpDataSpinner() {
        dbHandler = new DbHandler(this);
        dbHandler.getWritableDatabase();

        // sura name start list
        ArrayAdapter<String> suraNameStartAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_dropdown_item, new Constant().getSuraNameList());

        suraNameStartEditSpinner.setAdapter(suraNameStartAdapter);

        // sura name start list
        ArrayAdapter<String> suraNameEndAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_dropdown_item, new Constant().getSuraNameList());
        suraNameEndEditSpinner.setAdapter(suraNameEndAdapter);

        // amount hafz type
        amountHafzTypeArrayList = new ArrayList<>();
        amountHafzTypeArrayList.add("صفحة");
        amountHafzTypeArrayList.add("ربع");
        amountHafzTypeArrayList.add("جزء");
    }

    private void initViews() {
        suraNameStartEditSpinner = findViewById(R.id.spinner_sura_name_start);
        suraNameEndEditSpinner = findViewById(R.id.spinner_sura_name_end);
        ayaNameStartEditSpinner = findViewById(R.id.spinner_aya_num_start);
        ayaNameEndEditSpinner = findViewById(R.id.spinner_aya_num_end);
        amountHafzTypeEditSpinner = findViewById(R.id.spinner_hafz_type);
        startHafzButton = findViewById(R.id.start_hafz_button);
        wardCountEditText = findViewById(R.id.count_edit);
        planTimeTextView = findViewById(R.id.amount_of_time);
    }

    public int calculatePlanTimeCount() {

        int idStart = dbHandler.getKeyId(suraIdStart, ayaIdStart);
        int idEnd = dbHandler.getKeyId(suraIdEnd, ayaIdEnd);

        if (idStart > idEnd) {
            keyIdStart = dbHandler.getKeyId(suraIdEnd, ayaIdEnd).toString();
            keyIdEnd = dbHandler.getKeyId(suraIdStart, ayaIdStart).toString();
        } else {
            keyIdStart = dbHandler.getKeyId(suraIdStart, ayaIdStart).toString();
            keyIdEnd = dbHandler.getKeyId(suraIdEnd, ayaIdEnd).toString();
        }

        if (wardCountType.equals("صفحة")) {
            int pageStart = dbHandler.getQuranRow(keyIdStart).getPage();
            int pageEnd = dbHandler.getQuranRow(keyIdEnd).getPage();

            int pageCount = (pageEnd - pageStart) + 1;
            return pageCount / Integer.parseInt(wardCount);
        } else if (wardCountType.equals("ربع")) {

            int startKeyId = Integer.parseInt(keyIdStart);
            int endKeyId = Integer.parseInt(keyIdEnd);

            for (int i = startKeyId; i < endKeyId; i++) {
                int verseID = dbHandler.getQuranRow(String.valueOf(i)).getVerseId();
                if (verseID == 1) {
                    verseCount++;
                }
            }
            return verseCount / Integer.parseInt(wardCount);
        } else {
            // جزء
            int jozzStart = dbHandler.getQuranRow(keyIdStart).getJozz();
            int jozzEnd = dbHandler.getQuranRow(keyIdEnd).getJozz();

            int jozzCount = (jozzEnd - jozzStart) + 1;
            return jozzCount / Integer.parseInt(wardCount);
        }
    }

}