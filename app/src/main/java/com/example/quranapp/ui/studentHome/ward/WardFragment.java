package com.example.quranapp.ui.studentHome.ward;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.quranapp.R;
import com.example.quranapp.db.DbHandler;
import com.example.quranapp.db.Quran;
import com.example.quranapp.prefs.PreferencesHelperImp;
import com.example.quranapp.ui.addPlan.Plan;
import com.example.quranapp.ui.studentHome.fahrs.SuraFragment;
import com.google.gson.Gson;

import java.util.List;

public class WardFragment extends Fragment {
    private String ayaStart, ayaEnd, suraNameStart, suraNameEnd, ayaNoStart, ayaNoEnd,
            pageStart, pageEnd, keyIdStartWard, keyIdEndWard;
    private TextView ayaStartTextView, ayaEndTextView, pageNoEndTextView, pageNoStartTextView,
            suraNameStartTextView, suraNameEndTextView;
    private Button hafzWardButton, hafzDoneButton;
    private Quran quranEnd, quranStart;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        setListeners();
    }

    private void setListeners() {
        hafzWardButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SuraFragment suraFragment = new SuraFragment();
                Bundle bundle = new Bundle();
                bundle.putInt("page_start", Integer.parseInt(pageStart));
                bundle.putInt("page_end", Integer.parseInt(pageEnd));
                suraFragment.setArguments(bundle);

                FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.add(R.id.fragment_container, suraFragment).commit();

            }
        });

        hafzDoneButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                StudentCompleteExamFragment studentCompleteExamFragment = new StudentCompleteExamFragment();
                Bundle bundle = new Bundle();
                bundle.putString("keyIdStartWard", keyIdStartWard);
                bundle.putString("keyIdEndWard", keyIdEndWard);
                studentCompleteExamFragment.setArguments(bundle);

                FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.add(R.id.fragment_container, studentCompleteExamFragment, "StudentCompleteExamFragment").commit();

            }
        });
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.ward_fragment, container, false);
        initViews(view);
        calculateWardHafzData();
        return view;
    }

    private void initViews(View view) {
        ayaStartTextView = view.findViewById(R.id.aya_start_text);
        ayaEndTextView = view.findViewById(R.id.aya_end_text);
        pageNoStartTextView = view.findViewById(R.id.page_no_start);
        pageNoEndTextView = view.findViewById(R.id.page_no_end);
        suraNameStartTextView = view.findViewById(R.id.sura_name_aya_start);
        suraNameEndTextView = view.findViewById(R.id.sura_name_aya_end);
        hafzWardButton = view.findViewById(R.id.button_hafz_ward);
        hafzDoneButton = view.findViewById(R.id.button_hafz_done);
    }

    private void calculateWardHafzData() {
        DbHandler dbHandler = new DbHandler(getActivity());
        dbHandler.getWritableDatabase();

        String json = PreferencesHelperImp.getInstance().getStudentPlan();
        Gson gson = new Gson();
        Plan plan = gson.fromJson(json, Plan.class);

        int wardCount = Integer.parseInt(plan.getWardCount());
        String wardCountType = plan.getWardCountType();
        String keyIdEnd = plan.getKeyIdEnd();

        String newWardStartId = PreferencesHelperImp.getInstance().getNewWardStartId();
        if (newWardStartId != null) {
            keyIdStartWard = newWardStartId;
        } else {
            keyIdStartWard = plan.getKeyIdStart();
        }
        quranStart = dbHandler.getQuranRow(keyIdStartWard);

        ayaStart = quranStart.getTextEmlaey();
        pageStart = String.valueOf(quranStart.getPage());
        ayaNoStart = String.valueOf(quranStart.getAyaNo());
        suraNameStart = quranStart.getSuraNameAr();

        // ayaEnd
        if (wardCountType.equals("صفحة")) {
            int pageEndInt = Integer.parseInt(pageStart) + wardCount - 1;

            int allWardEnd = dbHandler.getQuranRow(keyIdEnd).getPage();
            if (pageEndInt > allWardEnd) {
                pageEndInt = allWardEnd;
            }

            List<Quran> quranList = dbHandler.getAllAyatInsidePage(String.valueOf(pageEndInt));

            quranEnd = quranList.get(quranList.size() - 1);

        } else if (wardCountType.equals("ربع")) {
            int verseCount = 0;
            int idStart = Integer.parseInt(keyIdStartWard);
            int idEnd = Integer.parseInt(keyIdEnd);

            for (int i = idStart; i < idEnd; i++) {
                Quran quran = dbHandler.getQuranRow(String.valueOf(i));
                int verseID = quran.getVerseId();
                if (verseID == 1) {
                    verseCount++;
                }

                if (wardCount == verseCount) {
                    quranEnd = dbHandler.getQuranRow(String.valueOf(i - 1));
                    break;
                }
            }

            if (quranEnd == null) {
                //wardCount > verseCount (عدد الارباع كبير أكبر من مقدار الحفظ)
                quranEnd = dbHandler.getQuranRow(keyIdEnd);
            }
        } else {
            //حزء
            int jozzStart = quranStart.getJozz();
            int jozzCount = 0;

            int idStart = Integer.parseInt(keyIdStartWard);
            int idEnd = Integer.parseInt(keyIdEnd);

            for (int i = idStart; i < idEnd; i++) {
                Quran quran = dbHandler.getQuranRow(String.valueOf(i));
                int jozz = quran.getJozz();
                if (jozzStart != jozz) {
                    jozzCount++;
                    jozzStart = jozz;
                }

                if (wardCount == jozzCount) {
                    quranEnd = dbHandler.getQuranRow(String.valueOf(i - 1));
                    break;
                }
            }

            if (quranEnd == null) {
                //wardCount > jozzCount (عدد الاجزاء كبير أكبر من مقدار الحفظ)
                quranEnd = dbHandler.getQuranRow(keyIdEnd);
            }
        }

        keyIdEndWard = String.valueOf(quranEnd.getId());
        PreferencesHelperImp.getInstance().setWardEndId(keyIdEndWard);
        pageEnd = String.valueOf(quranEnd.getPage());
        ayaEnd = quranEnd.getTextEmlaey();
        ayaNoEnd = String.valueOf(quranEnd.getAyaNo());
        suraNameEnd = quranEnd.getSuraNameAr();

        String[] splitStartQuestion = ayaStart.split("\\s+");
        String[] splitEndQuestion = ayaEnd.split("\\s+");

        if (splitEndQuestion.length > 3)
            ayaEnd = splitEndQuestion[splitEndQuestion.length - 3] + " " + splitEndQuestion[splitEndQuestion.length - 2] +
                    " " + splitEndQuestion[splitEndQuestion.length - 1];

        if (splitStartQuestion.length > 5)
            ayaStart = splitStartQuestion[0] + " " + splitStartQuestion[1] + " " + splitStartQuestion[2] +
                    " " + splitStartQuestion[3] + " " + splitStartQuestion[4] + " " + splitStartQuestion[5];

        ayaStartTextView.setText("\" " + ayaStart + "\"");
        ayaEndTextView.setText("\" " + ayaEnd + "\"");
        pageNoStartTextView.setText("صفحة " + pageStart);
        pageNoEndTextView.setText("صفحة " + pageEnd);
        suraNameStartTextView.setText("سورة " + suraNameStart + "- آية " + ayaNoStart);
        suraNameEndTextView.setText("سورة " + suraNameEnd + "- آية " + ayaNoEnd);
    }
}
