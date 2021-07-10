package com.example.quranapp.ui.studentHome.fahrs;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.quranapp.R;
import com.example.quranapp.prefs.Constant;
import com.example.quranapp.ui.showTest.QuranAdapter;
import com.example.quranapp.ui.generateTest.GenerateQuestion;


public class FahrsFragment extends Fragment {
    private QuranAdapter quranAdapter;
    private RecyclerView allQuranSuraRecyclerView;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        setUpRecyclerView(view);
    }

    private void setUpRecyclerView(View view) {
        allQuranSuraRecyclerView = view.findViewById(R.id.quran_sura_recycler);

        LinearLayoutManager layoutManager
                = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        allQuranSuraRecyclerView.setLayoutManager(layoutManager); // set LayoutManager to RecyclerView

        GenerateQuestion generateQuestion = new GenerateQuestion();
        quranAdapter = new QuranAdapter(new Constant().getSuraNameList());
        quranAdapter.setParentId(R.layout.fahrs_fragment, getActivity());
        allQuranSuraRecyclerView.setAdapter(quranAdapter);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fahrs_fragment, container, false);
    }
}
