package com.example.quranapp.ui.showTest;

import android.os.Parcel;
import android.os.Parcelable;

public class ChooseQuestionItem implements Parcelable {

    private String question, choose1, choose2, choose3, choose4;

    public ChooseQuestionItem() {
    }

    public ChooseQuestionItem(Parcel in) {
        question = in.readString();
        choose1 = in.readString();
        choose2 = in.readString();
        choose3 = in.readString();
        choose4 = in.readString();
    }

    public ChooseQuestionItem(String question, String choose1, String choose2, String choose3, String choose4) {
        this.question = question;
        this.choose1 = choose1;
        this.choose2 = choose2;
        this.choose3 = choose3;
        this.choose4 = choose4;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getChoose1() {
        return choose1;
    }

    public void setChoose1(String choose1) {
        this.choose1 = choose1;
    }

    public String getChoose2() {
        return choose2;
    }

    public void setChoose2(String choose2) {
        this.choose2 = choose2;
    }

    public String getChoose3() {
        return choose3;
    }

    public void setChoose3(String choose3) {
        this.choose3 = choose3;
    }

    public String getChoose4() {
        return choose4;
    }

    public void setChoose4(String choose4) {
        this.choose4 = choose4;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(question);
        dest.writeString(choose1);
        dest.writeString(choose2);
        dest.writeString(choose3);
        dest.writeString(choose4);
    }

    public static final Creator<ChooseQuestionItem> CREATOR = new Creator<ChooseQuestionItem>()
    {
        public ChooseQuestionItem createFromParcel(Parcel in)
        {
            return new ChooseQuestionItem(in);
        }
        public ChooseQuestionItem[] newArray(int size)
        {
            return new ChooseQuestionItem[size];
        }
    };
}
