package com.example.quranapp.ui.generateTest;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;


import com.example.quranapp.R;
import com.example.quranapp.db.DbHandler;
import com.example.quranapp.db.Quran;
import com.example.quranapp.prefs.Constant;
import com.example.quranapp.ui.showTest.ChooseQuestionItem;
import com.example.quranapp.ui.showTest.ShowTestActivity;
import com.reginald.editspinner.EditSpinner;

import java.util.ArrayList;

public class GenerateTestActivity extends AppCompatActivity {

    private EditSpinner suraNameStartEditSpinner, ayaNameStartEditSpinner, suraNameEndEditSpinner,
            ayaNameEndEditSpinner, questionTypeEditSpinner,
            questionDifficultyEditSpinner, questionNumEditSpinner;

    private String suraIdStart, ayaIdStart, suraIdEnd, ayaIdEnd,
            questionType, questionDifficulty;
    private int questionNum;

    private Button showTest;
    private ArrayList<String> questionTypeArrayList, questionDifficultyArrayList, questionTimeArrayList;
    private ArrayList<Integer> ayaNumStartArrayList, ayaNumEndArrayList, questionNumArrayList;
    private ArrayAdapter<Integer> ayaNumStartAdapter, ayaNumEndAdapter;
    private GenerateQuestion generateQuestion;
    private DbHandler dbHandler;

    private ArrayList<String> generateQuestionListComplete, generateQuestionListCompleteEnd;
    private ArrayList<ChooseQuestionItem> generateQuestionListChoose;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_generate_test);

        initViews();
        createInstance();
        setUpDataSpinner();
        setListeners();
    }

    private void initViews() {
        suraNameStartEditSpinner = findViewById(R.id.spinner_sura_name_start);
        suraNameEndEditSpinner = findViewById(R.id.spinner_sura_name_end);
        ayaNameStartEditSpinner = findViewById(R.id.spinner_aya_num_start);
        ayaNameEndEditSpinner = findViewById(R.id.spinner_aya_num_end);
        questionTypeEditSpinner = findViewById(R.id.spinner_q_type);
        questionDifficultyEditSpinner = findViewById(R.id.spinner_q_difficulty);
        questionNumEditSpinner = findViewById(R.id.spinner_q_num);
        showTest = findViewById(R.id.button_generate_test);
    }

    private void createInstance() {
        dbHandler = new DbHandler(GenerateTestActivity.this);
        dbHandler.getWritableDatabase();

        generateQuestion = new GenerateQuestion(dbHandler, GenerateTestActivity.this);
    }

    private void setUpDataSpinner() {

        // sura name start list
        ArrayAdapter<String> suraNameStartAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_dropdown_item, new Constant().getSuraNameList());

        suraNameStartEditSpinner.setAdapter(suraNameStartAdapter);

        // sura name start list
        ArrayAdapter<String> suraNameEndAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_dropdown_item, new Constant().getSuraNameList());
        suraNameEndEditSpinner.setAdapter(suraNameEndAdapter);

        // question type
        questionTypeArrayList = new ArrayList<>();
        questionTypeArrayList.add("أكمل");
        questionTypeArrayList.add("اختياري (اسم السورة)");
        questionTypeArrayList.add("أكمل نهاية الآيات");
        questionTypeArrayList.add("متنوعة");
        ArrayAdapter<String> questionTypeAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, questionTypeArrayList);
        questionTypeEditSpinner.setAdapter(questionTypeAdapter);

        // question difficulty
        questionDifficultyArrayList = new ArrayList<>();
        questionDifficultyArrayList.add("سهل");
        questionDifficultyArrayList.add("متوسط");
        questionDifficultyArrayList.add("متشابهات");
        ArrayAdapter<String> questionDifficultyAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, questionDifficultyArrayList);
        questionDifficultyEditSpinner.setAdapter(questionDifficultyAdapter);

        // question numbers
        questionNumArrayList = new ArrayList<>();
        for (int i = 1; i <= 100; i++) {
            questionNumArrayList.add(i);
        }
        ArrayAdapter<Integer> questionNumAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, questionNumArrayList);
        questionNumEditSpinner.setAdapter(questionNumAdapter);
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

                ayaNumStartAdapter = new ArrayAdapter<>(GenerateTestActivity.this,
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
                        Toast.makeText(GenerateTestActivity.this, "Please choose the name of the sura first", Toast.LENGTH_SHORT).show();
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

                ayaNumEndAdapter = new ArrayAdapter<>(GenerateTestActivity.this,
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
                        Toast.makeText(GenerateTestActivity.this, "Please choose the name of the sura first", Toast.LENGTH_SHORT).show();
                }
                return false;
            }
        });

        questionTypeEditSpinner.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                questionType = questionTypeArrayList.get(i).toString();
                Log.i("questionType: ", questionType);
            }
        });

        questionDifficultyEditSpinner.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                questionDifficulty = questionDifficultyArrayList.get(i).toString();
                Log.i("questionDifficulty: ", questionDifficulty);
            }
        });


        questionNumEditSpinner.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                questionNum = questionNumArrayList.get(i);
                Log.i("questionNum: ", " " + questionNum);
            }
        });

        showTest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (suraIdStart != null && ayaIdStart != null && suraIdEnd != null
                        && ayaIdEnd != null && questionType != null && questionDifficulty != null
                        && questionNum != 0) {
                    generateQuestionTest();

                    Intent intent = new Intent(GenerateTestActivity.this, ShowTestActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putParcelableArrayList("generateQuestionListChoose", generateQuestionListChoose);
                    intent.putExtras(bundle);

                    intent.putExtra("generateQuestionListComplete", generateQuestionListComplete);
                    intent.putExtra("generateQuestionListCompleteEnd", generateQuestionListCompleteEnd);
                    startActivity(intent);
                } else {
                    Toast.makeText(GenerateTestActivity.this, "Please fill all data first", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }

    private void generateQuestionTest() {

        // get id for start and end questions
        int startKeyId = dbHandler.getKeyId(suraIdStart, ayaIdStart);
        Log.i("startKeyId:", "id: = " + startKeyId);

        int endKeyId = dbHandler.getKeyId(suraIdEnd, ayaIdEnd);
        Log.i("endKeyId:", "id: = " + endKeyId);


        // get questions upon type
        ArrayList<String> easyQuestions = new ArrayList<>();
        ArrayList<String> mediumQuestion = new ArrayList<>();

        for (int i = startKeyId; i < endKeyId; i++) {
            Quran question = dbHandler.getQuranRow(String.valueOf(i));
            if (question.getVerseId() == 1) {
                easyQuestions.add(question.getTextEmlaey());
            }
            mediumQuestion.add(question.getTextEmlaey());
        }

        // generate questions
        if (questionDifficulty.equals("سهل")) {

            if (questionType.equals("أكمل")) {
                generateQuestionListComplete = generateQuestion.generateQuestionListComplete
                        (easyQuestions, 3, questionNum).getQuestionList();
            } else if (questionType.equals("اختياري (اسم السورة)")) {
                generateQuestionListChoose = generateQuestion.generateQuestionListChoose(easyQuestions, questionNum);
            } else if (questionType.equals("أكمل نهاية الآيات")) {
                generateQuestionListCompleteEnd = generateQuestion.generateQuestionListCompleteEnd
                        (easyQuestions, questionNum).getQuestionList();
            } else {
                generateQuestionListComplete = generateQuestion.generateQuestionListComplete
                        (easyQuestions, 3, questionNum).getQuestionList();
                generateQuestionListChoose = generateQuestion.generateQuestionListChoose(easyQuestions, questionNum);
                generateQuestionListCompleteEnd = generateQuestion.generateQuestionListCompleteEnd
                        (easyQuestions, questionNum).getQuestionList();
            }

        } else if (questionDifficulty.equals("متوسط")) {
            if (questionType.equals("أكمل")) {
                generateQuestionListComplete = generateQuestion.generateQuestionListComplete
                        (mediumQuestion.subList(0, mediumQuestion.size() - 3), 3, questionNum).getQuestionList();
            } else if (questionType.equals("اختياري (اسم السورة)")) {
                generateQuestionListChoose = generateQuestion.generateQuestionListChoose(mediumQuestion, questionNum);
            } else if (questionType.equals("أكمل نهاية الآيات")) {
                generateQuestionListCompleteEnd = generateQuestion.generateQuestionListCompleteEnd
                        (mediumQuestion, questionNum).getQuestionList();
            } else {
                generateQuestionListComplete = generateQuestion.generateQuestionListComplete
                        (mediumQuestion.subList(0, mediumQuestion.size() - 3), 3, questionNum).getQuestionList();
                generateQuestionListChoose = generateQuestion.generateQuestionListChoose(mediumQuestion, questionNum);
                generateQuestionListCompleteEnd = generateQuestion.generateQuestionListCompleteEnd
                        (mediumQuestion, questionNum).getQuestionList();
            }

        } else {
            // متشابهات
        }

    }
}