package com.example.quranapp.ui.generateTest;

import java.util.ArrayList;

public class QuestionAndAnswer {
    private ArrayList<String> questionList, answerList;

    public QuestionAndAnswer() {
    }

    public QuestionAndAnswer(ArrayList<String> questionList, ArrayList<String> answerList) {
        this.questionList = questionList;
        this.answerList = answerList;
    }

    public ArrayList<String> getQuestionList() {
        return questionList;
    }

    public void setQuestionList(ArrayList<String> questionList) {
        this.questionList = questionList;
    }

    public ArrayList<String> getAnswerList() {
        return answerList;
    }

    public void setAnswerList(ArrayList<String> answerList) {
        this.answerList = answerList;
    }
}
