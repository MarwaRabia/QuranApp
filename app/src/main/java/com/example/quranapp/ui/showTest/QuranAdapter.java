package com.example.quranapp.ui.showTest;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;


import com.example.quranapp.R;
import com.example.quranapp.db.DbHandler;
import com.example.quranapp.db.Quran;
import com.example.quranapp.ui.generateTest.QuestionAndAnswer;
import com.example.quranapp.ui.studentHome.fahrs.SuraFragment;

import java.util.List;

public class QuranAdapter extends RecyclerView.Adapter<QuranAdapter.MyViewHolder> {

    private List<QuestionAndAnswer> questionList;

    public QuranAdapter(List<QuestionAndAnswer> questionList) {
        this.questionList = questionList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.complete_q_item, viewGroup, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, final int position) {
        myViewHolder.questionTextView.setText(questionList.get(position).getQuestion());

        myViewHolder.close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                questionList.remove(position);
                notifyDataSetChanged();
            }
        });
    }

    @Override
    public int getItemCount() {
        return questionList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        TextView questionTextView;
        ImageView close;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            questionTextView = itemView.findViewById(R.id.question);
            close = itemView.findViewById(R.id.image_close);
        }
    }
}

