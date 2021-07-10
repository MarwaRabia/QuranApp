package com.example.quranapp.ui.studentHome.ward;

import android.content.Context;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.quranapp.R;
import com.example.quranapp.prefs.PreferencesHelperImp;

import java.util.ArrayList;
import java.util.List;

public class ExamSolutionAdapter extends RecyclerView.Adapter<ExamSolutionAdapter.MyViewHolder> {
    private String userAnswerQ1, userAnswerQ2, userAnswerQ3, userAnswerQ4, userAnswerQ5, userAnswerQ6;
    private String question3, question4, question5, question6;
    private String correctAnswerQ1, correctAnswerQ2, correctAnswerQ3, correctAnswerQ4, correctAnswerQ5, correctAnswerQ6;
    private int studentGrade;

    private List<ExamSolution> examSolutionList;
    private Context context;

    public ExamSolutionAdapter(Context context) {
        this.context = context;
        examSolutionList = examCorrection();
    }

    public int getStudentGrade() {
        return studentGrade;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.solution_item, viewGroup, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, final int position) {
        ExamSolution examSolution = examSolutionList.get(position);

        myViewHolder.questionNameTextView.setText(examSolution.getQuestionName());
        myViewHolder.questionScoreTextView.setText(examSolution.getScore());
        if (examSolution.getScore().equals("(1/1)")) {
            myViewHolder.questionScoreTextView.setTextColor(context.getResources().getColor(R.color.verdigris));
        } else {
            myViewHolder.questionScoreTextView.setTextColor(context.getResources().getColor(R.color.red));
        }
        myViewHolder.answerTextView.setText(Html.fromHtml(examSolution.getAnswer()));
    }

    @Override
    public int getItemCount() {
        return examSolutionList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        TextView questionNameTextView, questionScoreTextView, answerTextView;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            questionNameTextView = itemView.findViewById(R.id.question_name);
            questionScoreTextView = itemView.findViewById(R.id.question_score);
            answerTextView = itemView.findViewById(R.id.answer);
        }
    }

    private List<ExamSolution> examCorrection() {
        examSolutionList = new ArrayList<>();
        // question 3 4 5 6
        List<String> listQ3_4_5_6 = PreferencesHelperImp.getInstance().getListQ3_4_5_6();
        question3 = listQ3_4_5_6.get(0);
        question4 = listQ3_4_5_6.get(1);
        question5 = listQ3_4_5_6.get(2);
        question6 = listQ3_4_5_6.get(3);

        // user answers
        userAnswerQ1 = PreferencesHelperImp.getInstance().getUserAnswerQ1_Q2().get(0);
        userAnswerQ2 = PreferencesHelperImp.getInstance().getUserAnswerQ1_Q2().get(1);

        userAnswerQ3 = PreferencesHelperImp.getInstance().getUserAnswersQ3_4_5_6().get(0);
        userAnswerQ3 = userAnswerQ3.replaceAll("\\s+", "")
                .replace("أ", "ا").replace("آ", "ا").replace("إ", "ا");

        userAnswerQ4 = PreferencesHelperImp.getInstance().getUserAnswersQ3_4_5_6().get(1);
        userAnswerQ4 = userAnswerQ4.replaceAll("\\s+", "")
                .replace("أ", "ا").replace("آ", "ا").replace("إ", "ا");

        userAnswerQ5 = PreferencesHelperImp.getInstance().getUserAnswersQ3_4_5_6().get(2);
        userAnswerQ5 = userAnswerQ5.replaceAll("\\s+", "")
                .replace("أ", "ا").replace("آ", "ا").replace("إ", "ا");

        userAnswerQ6 = PreferencesHelperImp.getInstance().getUserAnswersQ3_4_5_6().get(3);
        userAnswerQ6 = userAnswerQ6.replaceAll("\\s+", "")
                .replace("أ", "ا").replace("آ", "ا").replace("إ", "ا");

        //correct answers
        correctAnswerQ1 = PreferencesHelperImp.getInstance().getCorrectAnswerQ1_Q2().get(0);
        correctAnswerQ2 = PreferencesHelperImp.getInstance().getCorrectAnswerQ1_Q2().get(1);

        correctAnswerQ3 = PreferencesHelperImp.getInstance().getCorrectAnswersQ3_4_5_6().get(0);
        correctAnswerQ3 = correctAnswerQ3.replaceAll("\\s+", "")
                .replace("أ", "ا").replace("آ", "ا").replace("إ", "ا");

        correctAnswerQ4 = PreferencesHelperImp.getInstance().getCorrectAnswersQ3_4_5_6().get(1);
        correctAnswerQ4 = correctAnswerQ4.replaceAll("\\s+", "")
                .replace("أ", "ا").replace("آ", "ا").replace("إ", "ا");

        correctAnswerQ5 = PreferencesHelperImp.getInstance().getCorrectAnswersQ3_4_5_6().get(2);
        correctAnswerQ5 = correctAnswerQ5.replaceAll("\\s+", "")
                .replace("أ", "ا").replace("آ", "ا").replace("إ", "ا");

        correctAnswerQ6 = PreferencesHelperImp.getInstance().getCorrectAnswersQ3_4_5_6().get(3);
        correctAnswerQ6 = correctAnswerQ6.replaceAll("\\s+", "")
                .replace("أ", "ا").replace("آ", "ا").replace("إ", "ا");

        Log.i("data_aya", userAnswerQ1 + "\n" + userAnswerQ2 + "\n"
                + userAnswerQ3 + "\n" + userAnswerQ4 + "\n" + userAnswerQ5 + "\n" + userAnswerQ6);
        Log.i("data_aya_correct", "\n" + correctAnswerQ1 + "\n" + correctAnswerQ2 + "\n"
                + correctAnswerQ3 + "\n" + correctAnswerQ4 + "\n" + correctAnswerQ5 + "\n" + correctAnswerQ6);


        //compare here
        // question 1
        String[] userAnswerQ1Split = userAnswerQ1.split("\\s+");
        String[] correctAnswerQ1Split = correctAnswerQ1.split("\\s+");
        ArrayList<Integer> errorAnswer1Index = new ArrayList<>();
        String question1Score = null;
        for (int i = 0; i < correctAnswerQ1Split.length; i++) {
            try {
                String userAnswerText = userAnswerQ1Split[i].replace("أ", "ا")
                        .replace("آ", "ا").replace("إ", "ا");

                String correctAnswerText = correctAnswerQ1Split[i].replace("أ", "ا")
                        .replace("آ", "ا").replace("إ", "ا");

                if (!correctAnswerText.equals(userAnswerText))
                    errorAnswer1Index.add(i);
            } catch (ArrayIndexOutOfBoundsException e) {
                errorAnswer1Index.add(i);
            }
        }

        StringBuilder answer1 = new StringBuilder();
        for (int i = 0; i < correctAnswerQ1Split.length; i++) {
            if (errorAnswer1Index.contains(i)) {
                String text = correctAnswerQ1Split[i];
                text = "<font color='#F30202'>" + text + "</font>";
                answer1.append(" ").append(text);
            } else {
                String text = correctAnswerQ1Split[i];
                text = "<font color='#FF000000'>" + text + "</font>";
                answer1.append(" ").append(text);
            }
        }

        if (correctAnswerQ1Split.length / 2 >= errorAnswer1Index.size()) {
            question1Score = "(1/1)";
            studentGrade++;
        } else {
            question1Score = "(0/1)";
        }
        examSolutionList.add(new ExamSolution(answer1.toString(), "السؤال الأول: ", question1Score));

        // question 2
        String[] userAnswerQ2Split = userAnswerQ2.split("\\s+");
        String[] correctAnswerQ2Split = correctAnswerQ2.split("\\s+");
        ArrayList<Integer> errorAnswer2Index = new ArrayList<>();
        String question2Score = null;
        for (int i = 0; i < correctAnswerQ2Split.length; i++) {
            try {
                String userAnswerText = userAnswerQ2Split[i].replace("أ", "ا")
                        .replace("آ", "ا").replace("إ", "ا");

                String correctAnswerText = correctAnswerQ2Split[i].replace("أ", "ا")
                        .replace("آ", "ا").replace("إ", "ا");

                if (!correctAnswerText.equals(userAnswerText))
                    errorAnswer2Index.add(i);
            } catch (ArrayIndexOutOfBoundsException e) {
                errorAnswer2Index.add(i);
            }
        }

        StringBuilder answer2 = new StringBuilder();
        for (int i = 0; i < correctAnswerQ2Split.length; i++) {
            if (errorAnswer2Index.contains(i)) {
                String text = correctAnswerQ2Split[i];
                text = "<font color='#F30202'>" + text + "</font>";
                answer2.append(" ").append(text);
            } else {
                String text = correctAnswerQ2Split[i];
                text = "<font color='#FF000000'>" + text + "</font>";
                answer2.append(" ").append(text);
            }
        }

        if (correctAnswerQ2Split.length / 2 >= errorAnswer2Index.size()) {
            question2Score = "(1/1)";
            studentGrade++;
        } else {
            question2Score = "(0/1)";
        }
        examSolutionList.add(new ExamSolution(answer2.toString(), "السؤال الثاني: ", question2Score));

        // question 3
        //F30202 red
        //FF000000 bkack
        String answer3 = PreferencesHelperImp.getInstance().getCorrectAnswersQ3_4_5_6().get(0);
        if (userAnswerQ3.equals(correctAnswerQ3)) {
            answer3 = "<font color='#FF000000'>" + question3 + " " + answer3 + "</font>";
            examSolutionList.add(new ExamSolution(answer3, "السؤال الثالث: ", "(1/1)"));
            studentGrade++;
        } else {
            correctAnswerQ3 = " " + PreferencesHelperImp.getInstance().getCorrectAnswersQ3_4_5_6().get(0);
            answer3 = "<font color='#FF000000'>" + question3 + "</font>"
                    + "<font color='#F30202'>" + correctAnswerQ3 + "</font>";
            examSolutionList.add(new ExamSolution(answer3, "السؤال الثالث: ", "(0/1)"));
        }

        // question 4
        //F30202 red
        //FF000000 bkack
        String answer4 = PreferencesHelperImp.getInstance().getCorrectAnswersQ3_4_5_6().get(1);
        if (userAnswerQ4.equals(correctAnswerQ4)) {
            answer4 = "<font color='#FF000000'>" + question4 + " " + answer4 + "</font>";
            examSolutionList.add(new ExamSolution(answer4, "السؤال الرابع: ", "(1/1)"));
            studentGrade++;
        } else {
            correctAnswerQ4 = " " + PreferencesHelperImp.getInstance().getCorrectAnswersQ3_4_5_6().get(1);
            answer4 = "<font color='#FF000000'>" + question4 + "</font>"
                    + "<font color='#F30202'>" + correctAnswerQ4 + "</font>";
            examSolutionList.add(new ExamSolution(answer4, "السؤال الرابع: ", "(0/1)"));
        }

        // question 5
        //F30202 red
        //FF000000 bkack
        String answer5 = PreferencesHelperImp.getInstance().getCorrectAnswersQ3_4_5_6().get(2);
        if (userAnswerQ5.equals(correctAnswerQ5)) {
            answer5 = "<font color='#FF000000'>" + question5 + " " + answer5 + "</font>";
            examSolutionList.add(new ExamSolution(answer5, "السؤال الخامس: ", "(1/1)"));
            studentGrade++;
        } else {
            correctAnswerQ5 = " " + PreferencesHelperImp.getInstance().getCorrectAnswersQ3_4_5_6().get(2);
            answer5 = "<font color='#FF000000'>" + question5 + "</font>"
                    + "<font color='#F30202'>" + correctAnswerQ5 + "</font>";
            examSolutionList.add(new ExamSolution(answer5, "السؤال الخامس: ", "(0/1)"));
        }

        // question 6
        //F30202 red
        //FF000000 bkack
        String answer6 = PreferencesHelperImp.getInstance().getCorrectAnswersQ3_4_5_6().get(3);
        if (userAnswerQ6.equals(correctAnswerQ6)) {
            answer6 = "<font color='#FF000000'>" + question6 + " " + answer6 + "</font>";
            examSolutionList.add(new ExamSolution(answer6, "السؤال السادس: ", "(1/1)"));
            studentGrade++;
        } else {
            correctAnswerQ6 = " " + PreferencesHelperImp.getInstance().getCorrectAnswersQ3_4_5_6().get(3);
            answer6 = "<font color='#FF000000'>" + question6 + "</font>"
                    + "<font color='#F30202'>" + correctAnswerQ6 + "</font>";
            examSolutionList.add(new ExamSolution(answer6, "السؤال السادس: ", "(0/1)"));
        }
        return examSolutionList;
    }

}

