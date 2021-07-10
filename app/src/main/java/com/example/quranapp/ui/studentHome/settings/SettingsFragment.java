package com.example.quranapp.ui.studentHome.settings;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.quranapp.R;
import com.example.quranapp.db.DbHandler;
import com.example.quranapp.db.Quran;
import com.example.quranapp.prefs.PreferencesHelperImp;
import com.example.quranapp.ui.addPlan.AddStudentPlanActivity;
import com.example.quranapp.ui.addPlan.Plan;
import com.example.quranapp.ui.start.StartActivity;
import com.google.gson.Gson;

public class SettingsFragment extends Fragment {
    private TextView suraStartTextView, suraEndTextView, pageStartTextView,
            pageEndTextView, wardDailyTextView;

    private Button changePlanButton, changeAccountTypeButton;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.settings_fragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initViews(view);
        setPlanData();
        setListeners();
    }

    private void setListeners() {
        changePlanButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // to update plan
                startActivity(new Intent(getActivity(), AddStudentPlanActivity.class));
                getActivity().finish();
            }
        });

        changeAccountTypeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // to change Account type
                PreferencesHelperImp.getInstance().removeAllValues();
                startActivity(new Intent(getActivity(), StartActivity.class));
                getActivity().finish();
            }
        });
    }

    private void setPlanData() {
        // data
        String json = PreferencesHelperImp.getInstance().getStudentPlan();
        Gson gson = new Gson();
        Plan plan = gson.fromJson(json, Plan.class);

        DbHandler dbHandler = new DbHandler(getActivity());
        dbHandler.getWritableDatabase();
        Quran startAya = dbHandler.getQuranRow(plan.getKeyIdStart());
        Quran endAya = dbHandler.getQuranRow(plan.getKeyIdEnd());

        suraStartTextView.setText("من سورة " + startAya.getSuraNameAr() + "- آية " + startAya.getAyaNo());
        suraEndTextView.setText("إلي سورة " + endAya.getSuraNameAr() + "- آية " + endAya.getAyaNo());

        pageStartTextView.setText("صفحة " + startAya.getPage());
        pageEndTextView.setText("صفحة " + endAya.getPage());

        wardDailyTextView.setText("ورد الحفظ اليومي: " + plan.getWardCount() + " " + plan.getWardCountType());

    }

    private void initViews(View view) {
        suraStartTextView = view.findViewById(R.id.sura_name_aya_start);
        suraEndTextView = view.findViewById(R.id.sura_name_aya_end);
        pageStartTextView = view.findViewById(R.id.page_no_start);
        pageEndTextView = view.findViewById(R.id.page_no_end);
        wardDailyTextView = view.findViewById(R.id.daily_ward);
        changePlanButton = view.findViewById(R.id.change_plan_button);
        changeAccountTypeButton = view.findViewById(R.id.change_account_type_button);
    }
}
