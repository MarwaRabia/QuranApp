package com.example.quranapp.ui.studentHome.ward;

public class ExamSolution {
    private String answer, questionName, score;

    public ExamSolution() {
    }

    public ExamSolution(String answer, String questionName, String score) {
        this.answer = answer;
        this.questionName = questionName;
        this.score = score;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public String getQuestionName() {
        return questionName;
    }

    public void setQuestionName(String questionName) {
        this.questionName = questionName;
    }

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }
}
