package com.example.quranapp.prefs;

import com.example.quranapp.ui.addPlan.Plan;

public interface PreferencesHelper {

    String  getStudentPlan();
    void setStudentPlan(String plan);

    String getAccountType();
    void setAccountType(String accountType);

    void removeAllValues();

}
