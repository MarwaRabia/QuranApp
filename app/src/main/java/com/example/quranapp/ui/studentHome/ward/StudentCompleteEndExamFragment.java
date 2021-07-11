package com.example.quranapp.ui.studentHome.ward;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
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
import java.util.List;

public class StudentCompleteEndExamFragment extends Fragment {
    private Button submitButton;
    private EditText answerQ1EditText, answerQ2EditText, answerQ3EditText, answerQ4EditText;
    private TextView question1TextView, question2TextView, question3TextView, question4TextView;
    String keyIdStartWard, keyIdEndWard;
    private List<QuestionAndAnswer> questionAndAnswerList;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.complete_end_student_q, container, false);

        Bundle bundle = this.getArguments();
        if (bundle != null) {
            keyIdStartWard = bundle.getString("keyIdStartWard");
            keyIdEndWard = bundle.getString("keyIdEndWard");
        }
        initViews(view);
        return view;
    }

    private void initViews(View view) {
        submitButton = view.findViewById(R.id.button_submit);

        answerQ1EditText = view.findViewById(R.id.answer_edit_text_1);
        answerQ2EditText = view.findViewById(R.id.answer_edit_text_2);
        answerQ3EditText = view.findViewById(R.id.answer_edit_text_3);
        answerQ4EditText = view.findViewById(R.id.answer_edit_text_4);

        question1TextView = view.findViewById(R.id.question_1);
        question2TextView = view.findViewById(R.id.question_2);
        question3TextView = view.findViewById(R.id.question_3);
        question4TextView = view.findViewById(R.id.question_4);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        generateQuestion();
        setListeners();
    }

    private void setListeners() {
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String answer1 = answerQ1EditText.getText().toString().trim();
                String answer2 = answerQ2EditText.getText().toString().trim();
                String answer3 = answerQ3EditText.getText().toString().trim();
                String answer4 = answerQ4EditText.getText().toString().trim();

                if (!answer1.isEmpty() && !answer2.isEmpty() && !answer3.isEmpty() && !answer4.isEmpty()) {
                    saveInSharedPF(answer1, answer2, answer3, answer4);

                    FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
                    fragmentTransaction.addToBackStack(null);
                    fragmentTransaction.add(R.id.fragment_container, new ExamSolutionFragment()).commit();

                } else {
                    Toast.makeText(getActivity(), "من فضلك جاوب الاول", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void saveInSharedPF(String answer1, String answer2, String answer3, String answer4) {

        String q3 = questionAndAnswerList.get(0).getQuestion();
        String q4 = questionAndAnswerList.get(1).getQuestion();
        String q5 = questionAndAnswerList.get(2).getQuestion();
        String q6 = questionAndAnswerList.get(3).getQuestion();
        ArrayList<String> questionsList = new ArrayList<>();
        questionsList.add(q3);
        questionsList.add(q4);
        questionsList.add(q5);
        questionsList.add(q6);
        PreferencesHelperImp.getInstance().setListQ3_4_5_6(questionsList);

        List<String> userAnswersQ3_4_5_6 = new ArrayList<>();
        userAnswersQ3_4_5_6.add(answer1);
        userAnswersQ3_4_5_6.add(answer2);
        userAnswersQ3_4_5_6.add(answer3);
        userAnswersQ3_4_5_6.add(answer4);
        PreferencesHelperImp.getInstance().setUserAnswersQ3_4_5_6(userAnswersQ3_4_5_6);

        String correctAnswer3 = questionAndAnswerList.get(0).getAnswer();
        String correctAnswer4 = questionAndAnswerList.get(1).getAnswer();
        String correctAnswer5 = questionAndAnswerList.get(2).getAnswer();
        String correctAnswer6 = questionAndAnswerList.get(3).getAnswer();
        ArrayList<String> correctAnswersList = new ArrayList<>();
        correctAnswersList.add(correctAnswer3);
        correctAnswersList.add(correctAnswer4);
        correctAnswersList.add(correctAnswer5);
        correctAnswersList.add(correctAnswer6);
        PreferencesHelperImp.getInstance().setCorrectAnswersQ3_4_5_6(correctAnswersList);
    }

    private void generateQuestion() {
        DbHandler dbHandler = new DbHandler(getActivity());
        dbHandler.getWritableDatabase();
        GenerateQuestion generateQuestion = new GenerateQuestion(dbHandler, getActivity());

        ArrayList<String> mediumQuestion = new ArrayList<>();
        int startKeyId = Integer.parseInt(keyIdStartWard);
        int endKeyId = Integer.parseInt(keyIdEndWard);

        for (int i = startKeyId; i < endKeyId; i++) {
            Quran question = dbHandler.getQuranRow(String.valueOf(i));
            mediumQuestion.add(question.getTextEmlaey());
        }
        questionAndAnswerList = generateQuestion.generateQuestionListCompleteEnd(mediumQuestion);
        question1TextView.setText(questionAndAnswerList.get(0).getQuestion() + ".....");
        question2TextView.setText(questionAndAnswerList.get(1).getQuestion() + ".....");
        question3TextView.setText(questionAndAnswerList.get(2).getQuestion() + ".....");
        question4TextView.setText(questionAndAnswerList.get(3).getQuestion() + ".....");
    }

}

