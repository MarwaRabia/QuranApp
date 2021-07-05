package com.example.quranapp.ui.studentHome.fahrs;

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;


import com.example.quranapp.R;
import com.example.quranapp.db.DbHandler;
import com.example.quranapp.db.Quran;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class SuraFragment extends Fragment {

    private ViewPager viewPager;
    private QuranPagerAdapter adapter;
    private List<String> myImageList;
    private int pageEnd, pageStart;
    private DbHandler dbHandler;
    private static final String TAG = "SuraFragment";

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        CoordinatorLayout coordinatorLayout = getActivity().findViewById(R.id.coordinator_layout);
        coordinatorLayout.setVisibility(View.GONE);

        viewPager = view.findViewById(R.id.view_pager);
        viewPager.setRotationY(180); //rotate the viewpager


        Bundle bundle = this.getArguments();
        if (bundle != null) {
            pageStart = bundle.getInt("page_start");
            pageEnd = bundle.getInt("page_end");
        }


        getDataFromAssetsFolder();
    }

    private void getDataFromAssetsFolder() {
        try {
            myImageList = Arrays.asList(getResources().getAssets().list("quran_pages"));
            myImageList = myImageList.subList(pageStart - 1, pageEnd);

            adapter = new QuranPagerAdapter(myImageList, getActivity());
            viewPager.setAdapter(adapter);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.all_pages_in_sura_fragment, container, false);

        view.setFocusableInTouchMode(true);
        view.requestFocus();
        view.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_BACK) {
                    CoordinatorLayout coordinatorLayout = getActivity().findViewById(R.id.coordinator_layout);
                    coordinatorLayout.setVisibility(View.VISIBLE);
                    getActivity().getSupportFragmentManager().popBackStack();
                    return true;
                }
                return false;
            }
        });
        return view;
    }

}
