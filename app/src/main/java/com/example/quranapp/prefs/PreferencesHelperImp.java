package com.example.quranapp.prefs;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;


public class PreferencesHelperImp implements PreferencesHelper {

    SharedPreferences preferences;
    Context context;
    private static PreferencesHelperImp instance;
    private Constant constant;

    public static PreferencesHelperImp getInstance() {
        if (instance == null) {
            instance = new PreferencesHelperImp();
        }
        return instance;
    }

    private PreferencesHelperImp() {
        this.context = ContextApplication.getInstance();
        constant = new Constant();
        preferences = context.getSharedPreferences(constant.SharedPreferencesName, Context.MODE_PRIVATE);
    }

    @Override
    public void setAccountType(String accountType) {
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(constant.ACCOUNT_TYPE, accountType);
        editor.apply();
    }

    @Override
    public String getAccountType() {
        return preferences.getString(new Constant().ACCOUNT_TYPE, null);
    }

    @Override
    public void setWardEndId(String wardEndId) {
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(constant.WARD_END, wardEndId);
        editor.apply();
    }

    @Override
    public String getWardEndId() {
        return preferences.getString(constant.WARD_END, null);
    }

    @Override
    public void setNewWardStartId(String newWardStartId) {
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(constant.NEW_WARD_START, newWardStartId);
        editor.apply();
    }

    @Override
    public String getNewWardStartId() {
        return preferences.getString(constant.NEW_WARD_START, null);
    }

    @Override
    public void setStudentPlan(String plan) {
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(constant.PLAN, plan);
        editor.apply();
    }

    @Override
    public String getStudentPlan() {
        return preferences.getString(constant.PLAN, null);
    }

    @Override
    public void setUserAnswersQ1_Q2(List<String> answersQ1_q2) {
        SharedPreferences.Editor editor = preferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(answersQ1_q2);
        editor.putString(constant.LIST_USER_ANSWERS_Q1_Q2, json);
        editor.apply();
    }

    @Override
    public List<String> getUserAnswerQ1_Q2() {
        Gson gson = new Gson();
        String json = preferences.getString(constant.LIST_USER_ANSWERS_Q1_Q2, null);

        Type type = new TypeToken<ArrayList<String>>() {
        }.getType();
        return gson.fromJson(json, type);
    }

    @Override
    public void setListQ3_4_5_6(List<String> q3_4_5_6) {
        SharedPreferences.Editor editor = preferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(q3_4_5_6);
        editor.putString(constant.LIST_Q3_4_5_6, json);
        editor.apply();
    }

    @Override
    public List<String> getListQ3_4_5_6() {
        Gson gson = new Gson();
        String json = preferences.getString(constant.LIST_Q3_4_5_6, null);

        Type type = new TypeToken<ArrayList<String>>() {
        }.getType();
        return gson.fromJson(json, type);
    }

    @Override
    public void setUserAnswersQ3_4_5_6(List<String> answersQ3_4_5_6) {
        SharedPreferences.Editor editor = preferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(answersQ3_4_5_6);
        editor.putString(constant.LIST_USER_ANSWERS_Q3_Q4_Q5_Q6, json);
        editor.apply();
    }

    @Override
    public List<String> getUserAnswersQ3_4_5_6() {
        Gson gson = new Gson();
        String json = preferences.getString(constant.LIST_USER_ANSWERS_Q3_Q4_Q5_Q6, null);
        Type type = new TypeToken<ArrayList<String>>() {
        }.getType();
        return gson.fromJson(json, type);
    }

    @Override
    public void setCorrectAnswersQ1_Q2(List<String> answersQ1_q2) {
        SharedPreferences.Editor editor = preferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(answersQ1_q2);
        editor.putString(constant.LIST_CORRECT_ANSWERS_Q1_Q2, json);
        editor.apply();
    }

    @Override
    public List<String> getCorrectAnswerQ1_Q2() {
        Gson gson = new Gson();
        String json = preferences.getString(constant.LIST_CORRECT_ANSWERS_Q1_Q2, null);

        Type type = new TypeToken<ArrayList<String>>() {
        }.getType();
        return gson.fromJson(json, type);
    }

    @Override
    public void setCorrectAnswersQ3_4_5_6(List<String> answersQ3_4_5_6) {
        SharedPreferences.Editor editor = preferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(answersQ3_4_5_6);
        editor.putString(constant.LIST_CORRECT_ANSWERS_Q3_Q4_Q5_Q6, json);
        editor.apply();
    }

    @Override
    public List<String> getCorrectAnswersQ3_4_5_6() {
        Gson gson = new Gson();
        String json = preferences.getString(constant.LIST_CORRECT_ANSWERS_Q3_Q4_Q5_Q6, null);
        Type type = new TypeToken<ArrayList<String>>() {
        }.getType();
        return gson.fromJson(json, type);

    }

    @Override
    public void removeKey(String key) {
        SharedPreferences.Editor editor = preferences.edit();
        editor.remove(key);
        editor.apply();
    }

    @Override
    public void removeAllValues() {
        SharedPreferences.Editor editor = preferences.edit();
        editor.clear();
        editor.apply();
    }
}
