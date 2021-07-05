package com.example.quranapp.ui.showTest;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.quranapp.R;

import java.util.List;

public class ChooseQAdapter extends RecyclerView.Adapter<ChooseQAdapter.MyViewHolder> {

    private List<ChooseQuestionItem> questionList;

    public ChooseQAdapter(List<ChooseQuestionItem> questionList) {
        this.questionList = questionList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.choose_q_item, viewGroup, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, final int i) {
        ChooseQuestionItem chooseQuestionItem = questionList.get(i);

        myViewHolder.questionTextView.setText(chooseQuestionItem.getQuestion());

        myViewHolder.choose1RadioButton.setText(chooseQuestionItem.getChoose1());
        myViewHolder.choose2RadioButton.setText(chooseQuestionItem.getChoose2());
        myViewHolder.choose3RadioButton.setText(chooseQuestionItem.getChoose3());
        myViewHolder.choose4RadioButton.setText(chooseQuestionItem.getChoose4());
    }

    @Override
    public int getItemCount() {
        return questionList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        RadioButton choose1RadioButton, choose2RadioButton,
                choose3RadioButton, choose4RadioButton;

        TextView questionTextView;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            choose1RadioButton = itemView.findViewById(R.id.radioButton1);
            choose2RadioButton = itemView.findViewById(R.id.radioButton2);
            choose3RadioButton = itemView.findViewById(R.id.radioButton3);
            choose4RadioButton = itemView.findViewById(R.id.radioButton4);

            questionTextView = itemView.findViewById(R.id.question);

        }
    }
}

