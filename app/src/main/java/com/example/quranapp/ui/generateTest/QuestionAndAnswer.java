package com.example.quranapp.ui.generateTest;

import android.os.Parcel;
import android.os.Parcelable;

public class QuestionAndAnswer implements Parcelable {

    private String question, answer;


    public QuestionAndAnswer(Parcel in) {
        question = in.readString();
        answer = in.readString();
    }

    public QuestionAndAnswer(String question, String answer) {
        this.question = question;
        this.answer = answer;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(question);
        dest.writeString(answer);
    }

    public static final Creator<QuestionAndAnswer> CREATOR = new Creator<QuestionAndAnswer>() {
        public QuestionAndAnswer createFromParcel(Parcel in) {
            return new QuestionAndAnswer(in);
        }

        public QuestionAndAnswer[] newArray(int size) {
            return new QuestionAndAnswer[size];
        }
    };
}