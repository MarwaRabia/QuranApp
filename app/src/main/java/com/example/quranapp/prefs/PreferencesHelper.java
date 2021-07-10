package com.example.quranapp.prefs;


import java.util.List;

public interface PreferencesHelper {

    void setStudentPlan(String plan);

    void setAccountType(String accountType);

    void setWardEndId(String wardEndId);

    void setNewWardStartId(String newWardStartId);

    void setUserAnswersQ1_Q2(List<String> answersQ1_q2);

    void setListQ3_4_5_6(List<String> q3_4_5_6);

    void setUserAnswersQ3_4_5_6(List<String> answersQ3_4_5_6);

    void setCorrectAnswersQ1_Q2(List<String> answersQ1_q2);

    void setCorrectAnswersQ3_4_5_6(List<String> answersQ3_4_5_6);

    // get methods
    String getStudentPlan();

    String getAccountType();

    String getWardEndId();

    String getNewWardStartId();

    List<String> getUserAnswerQ1_Q2();

    List<String> getListQ3_4_5_6();

    List<String> getUserAnswersQ3_4_5_6();

    List<String> getCorrectAnswerQ1_Q2();

    List<String> getCorrectAnswersQ3_4_5_6();

    // remove
    void removeAllValues();

    void removeKey(String key);

}
