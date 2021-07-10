package com.example.quranapp.ui.studentHome.ward;

import android.graphics.Color;
import android.os.Bundle;
import android.text.Html;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.quranapp.R;
import com.example.quranapp.prefs.Constant;
import com.example.quranapp.prefs.PreferencesHelperImp;
import com.example.quranapp.ui.addPlan.Plan;
import com.example.quranapp.ui.showTest.QuranAdapter;
import com.example.quranapp.ui.showTest.ShowTestActivity;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.regex.Pattern;

public class ExamSolutionFragment extends Fragment {
    private TextView scoreTextView, congratsTextView;
    private RecyclerView answersRecyclerView;
    private Button finish;
    private int studentGrade;
    private ExamSolutionAdapter examSolutionAdapter;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.exam_solution_fragment, container, false);
        initViews(view);
        return view;
    }

    private void onBackPressed(View view) {
        view.setFocusableInTouchMode(true);
        view.requestFocus();
        view.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_BACK) {
                    // no action
                    return true;
                }
                return false;
            }
        });
    }

    private void initViews(View view) {
        scoreTextView = view.findViewById(R.id.total_score);
        congratsTextView = view.findViewById(R.id.congrats);
        answersRecyclerView = view.findViewById(R.id.answers_recycler);
        finish = view.findViewById(R.id.button_next_again);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        setUpRecyclerView();
        onBackPressed(view);
        setUpStudentGrade();
        setUpListeners();
    }

    private void setUpListeners() {
        finish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (studentGrade >= 3) {
                    // success
                    int newWardIdStart = Integer.parseInt(PreferencesHelperImp.getInstance().getWardEndId()) + 1;
                    PreferencesHelperImp.getInstance().setNewWardStartId(String.valueOf(newWardIdStart));
                }
                FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.fragment_container, new WardFragment()).commit();

                Constant constant = new Constant();
                PreferencesHelperImp.getInstance().removeKey(constant.LIST_Q3_4_5_6);
                PreferencesHelperImp.getInstance().removeKey(constant.LIST_USER_ANSWERS_Q1_Q2);
                PreferencesHelperImp.getInstance().removeKey(constant.LIST_USER_ANSWERS_Q3_Q4_Q5_Q6);
                PreferencesHelperImp.getInstance().removeKey(constant.LIST_CORRECT_ANSWERS_Q1_Q2);
                PreferencesHelperImp.getInstance().removeKey(constant.LIST_CORRECT_ANSWERS_Q3_Q4_Q5_Q6);
            }
        });
    }

    private void setUpStudentGrade() {
        studentGrade = examSolutionAdapter.getStudentGrade();
        scoreTextView.setText("(" + studentGrade + "/6)");
        if (studentGrade >= 3) {
            congratsTextView.setVisibility(View.VISIBLE);
            congratsTextView.setText("لقد اجتزت الاختبار بنجاح.\n" + getMotivationString());
            finish.setText("الورد التالي");
        } else {
            congratsTextView.setVisibility(View.GONE);
            finish.setText("احفظ وردك مرة أخري ثم حاول مجددا");
        }
    }

    private void setUpRecyclerView() {
        LinearLayoutManager layoutManager
                = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        answersRecyclerView.setLayoutManager(layoutManager); // set LayoutManager to RecyclerView

        examSolutionAdapter = new ExamSolutionAdapter(getActivity());
        answersRecyclerView.setAdapter(examSolutionAdapter);

    }

    private String getMotivationString() {
        List<String> motivationList = new ArrayList<>();
        motivationList.add("هنيئاً لك حفظ كتاب الله الكريم، هنيئاً لك هذا الأجرالعظيم والثواب الجزيل");
        motivationList.add("يا مَن جعلت كتاب الله رفيقاً لك في دربك وأضأت بنور حروفه ظلام قلبك.. هنيئاً لك فهذا والله الفوز الحقيقي");
        motivationList.add("هنيئاً لك فقد استعملك الله لحفظ كتابه في الأرض");
        motivationList.add("القرآن يشفع وينفع ويرفع فرفعك الله به ونفعك به وجعله لك شفيعا");
        motivationList.add("أسعدك الله يا حافظ القرآن “ما أروعك بالقرآن” أفخر بإرادتك وشموخك");
        motivationList.add("إنّ ما يصنع منك ناجحاً هو ما يدور في داخلك حول ذاتك فخاطب ضميرك وأرغمه على النجاح ويا له من نجاح عندما تنهي حفظ كتاب ربك");
        motivationList.add("أسأل الله يزينك بزينة القرآن ويشرفك بشرف القرآن ويدخلك الجنة بشفاعة القرآن");
        motivationList.add("حفظ القرآن وتعلمه خير من الدنيا وما فيها");
        motivationList.add("حفظ القرآن سبب لحياة القلب ونور العقل");
        motivationList.add("اسأل الله أن يجعلك من حفظة كتابه");
        motivationList.add("من أراد أن يحدثه الله عز وجل فليقرأ القرآن الكريم");
        motivationList.add("جعلك الله ممن يلبس والديه تاجا في الجنة");
        motivationList.add("إن هذا القرآن لا يمنح كنوزه إلا لمن يُقبل عليه");
        motivationList.add("خيركم من تعلم القرآن وعلمه");

        return motivationList.get(new Random().nextInt(motivationList.size()));
    }
}
