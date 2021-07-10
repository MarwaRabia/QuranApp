package com.example.quranapp.ui.studentHome.ward;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.quranapp.R;
import com.example.quranapp.db.DbHandler;
import com.example.quranapp.db.Quran;
import com.example.quranapp.prefs.PreferencesHelperImp;
import com.example.quranapp.ui.generateTest.GenerateQuestion;
import com.example.quranapp.ui.generateTest.QuestionAndAnswer;

import java.util.ArrayList;

public class StudentCompleteExamFragment extends Fragment {
    private final int REQ_CODE_1 = 100;
    private final int REQ_CODE_2 = 200;
    private Button submitButton;
    private ImageView speakQ1, speakQ2;
    private EditText answerQ1EditText, answerQ2EditText;
    private TextView question1TextView, question2TextView;
    private String keyIdStartWard, keyIdEndWard;
    private QuestionAndAnswer questionAndAnswer;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.student_complete_exam_fragment, container, false);

        Bundle bundle = this.getArguments();
        if (bundle != null) {
            keyIdStartWard = bundle.getString("keyIdStartWard");
            keyIdEndWard = bundle.getString("keyIdEndWard");
        }
        initViews(view);
        return view;
    }

    private void initViews(View view) {
        submitButton = view.findViewById(R.id.button_next);
        speakQ1 = view.findViewById(R.id.speak_1);
        speakQ2 = view.findViewById(R.id.speak_2);
        question1TextView = view.findViewById(R.id.question_1);
        question2TextView = view.findViewById(R.id.question_2);
        answerQ1EditText = view.findViewById(R.id.answer_edit_text_1);
        answerQ2EditText = view.findViewById(R.id.answer_edit_text_2);
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        generateQuestion();
        setListeners();

    }

    public void setAnswerQ1EditText(String answer) {
        answerQ1EditText.setText(answer);
    }

    public void setAnswerQ2EditText(String answer) {
        answerQ2EditText.setText(answer);
    }

    @SuppressLint("ClickableViewAccessibility")
    private void setListeners() {
        speakQ1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
                intent.putExtra(RecognizerIntent.EXTRA_CALLING_PACKAGE, getClass().getPackage().getName());
                intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);// spilling
                intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, "ar-EG");
                intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Need to speak");
                intent.putExtra("android.speech.extra.EXTRA_ADDITIONAL_LANGUAGES", new String[]{});

                try {
                    getActivity().startActivityForResult(intent, REQ_CODE_1);
                } catch (ActivityNotFoundException a) {
                    Log.i("Error", a.getMessage());
                    Toast.makeText(getActivity(), "Sorry your device not supported", Toast.LENGTH_SHORT).show();
                }
            }
        });

        speakQ2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
                intent.putExtra(RecognizerIntent.EXTRA_CALLING_PACKAGE, getClass().getPackage().getName());
                intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);// spilling
                intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, "ar-EG");
                intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Need to speak");
                intent.putExtra("android.speech.extra.EXTRA_ADDITIONAL_LANGUAGES", new String[]{});

                try {
                    getActivity().startActivityForResult(intent, REQ_CODE_2);
                } catch (ActivityNotFoundException a) {
                    Log.i("Error", a.getMessage());
                    Toast.makeText(getActivity(), "Sorry your device not supported", Toast.LENGTH_SHORT).show();
                }
            }
        });

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String userAnswer1 = answerQ1EditText.getText().toString().trim();
                String userAnswer2 = answerQ2EditText.getText().toString().trim();

                if (!userAnswer1.isEmpty() && !userAnswer2.isEmpty()) {
                    saveInSharedPF(userAnswer1, userAnswer2);

                    StudentCompleteEndExamFragment studentCompleteEndExamFragment = new StudentCompleteEndExamFragment();
                    Bundle bundle = new Bundle();
                    bundle.putString("keyIdStartWard", keyIdStartWard);
                    bundle.putString("keyIdEndWard", keyIdEndWard);
                    studentCompleteEndExamFragment.setArguments(bundle);

                    FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
                    fragmentTransaction.addToBackStack(null);
                    fragmentTransaction.add(R.id.fragment_container, studentCompleteEndExamFragment).commit();

                } else {
                    Toast.makeText(getActivity(), "من فضلك جاوب الاول", Toast.LENGTH_SHORT).show();
                }
            }
        });

        answerQ1EditText.setOnTouchListener(new View.OnTouchListener() {

            public boolean onTouch(View v, MotionEvent event) {
                if (answerQ1EditText.hasFocus()) {
                    v.getParent().requestDisallowInterceptTouchEvent(true);
                    switch (event.getAction() & MotionEvent.ACTION_MASK) {
                        case MotionEvent.ACTION_SCROLL:
                            v.getParent().requestDisallowInterceptTouchEvent(false);
                            return true;
                    }
                }
                return false;
            }
        });

        answerQ2EditText.setOnTouchListener(new View.OnTouchListener() {
            public boolean onTouch(View v, MotionEvent event) {
                if (answerQ2EditText.hasFocus()) {
                    v.getParent().requestDisallowInterceptTouchEvent(true);
                    switch (event.getAction() & MotionEvent.ACTION_MASK) {
                        case MotionEvent.ACTION_SCROLL:
                            v.getParent().requestDisallowInterceptTouchEvent(false);
                            return true;
                    }
                }
                return false;
            }
        });
    }

    private void saveInSharedPF(String userAnswer1, String userAnswer2) {
        ArrayList<String> userAnswersList = new ArrayList<>();
        userAnswersList.add(userAnswer1);
        userAnswersList.add(userAnswer2);
        PreferencesHelperImp.getInstance().setUserAnswersQ1_Q2(userAnswersList);

        String correctAnswer1 = questionAndAnswer.getAnswerList().get(0);
        String correctAnswer2 = questionAndAnswer.getAnswerList().get(1);
        ArrayList<String> correctAnswersList = new ArrayList<>();
        correctAnswersList.add(correctAnswer1);
        correctAnswersList.add(correctAnswer2);
        PreferencesHelperImp.getInstance().setCorrectAnswersQ1_Q2(correctAnswersList);
    }

    private void generateQuestion() {
        DbHandler dbHandler = new DbHandler(getActivity());
        dbHandler.getWritableDatabase();
        questionAndAnswer = new QuestionAndAnswer();
        GenerateQuestion generateQuestion = new GenerateQuestion(dbHandler, getActivity());

        ArrayList<String> mediumQuestion = new ArrayList<>();
        int startKeyId = Integer.parseInt(keyIdStartWard);
        int endKeyId = Integer.parseInt(keyIdEndWard) - 2;

        for (int i = startKeyId; i < endKeyId; i++) {
            Quran question = dbHandler.getQuranRow(String.valueOf(i));
            mediumQuestion.add(question.getTextEmlaey());
        }
        questionAndAnswer = generateQuestion.generateQuestionListComplete
                (mediumQuestion, 3, 2);

        question1TextView.setText(questionAndAnswer.getQuestionList().get(0));
        question2TextView.setText(questionAndAnswer.getQuestionList().get(1));
    }

}
