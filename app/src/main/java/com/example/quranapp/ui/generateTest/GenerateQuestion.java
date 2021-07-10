package com.example.quranapp.ui.generateTest;

import android.content.Context;
import android.util.Log;


import com.example.quranapp.db.DbHandler;
import com.example.quranapp.db.Quran;
import com.example.quranapp.prefs.Constant;
import com.example.quranapp.ui.showTest.ChooseQuestionItem;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class GenerateQuestion {
    private DbHandler dbHandler;
    private Context context;

    public GenerateQuestion(DbHandler dbHandler, Context context) {
        this.dbHandler = dbHandler;
        this.context = context;
    }

    public GenerateQuestion() {
    }

    public QuestionAndAnswer generateQuestionListComplete(List<String> questionsList, int numberOfAyatRequired,
                                                          int questionNum) {
        //generate questions
        ArrayList<Integer> randomList = new ArrayList<>();
        ArrayList<String> requiredQuestion = new ArrayList<>();
        ArrayList<String> requiredAnswers = new ArrayList<>();
        Random random = new Random();

        for (int i = 0; i < questionNum; i++) {

            int startQuestionKeyIdRandomNum = random.nextInt(questionsList.size());

            //
            while (randomList.contains(startQuestionKeyIdRandomNum)) {
                startQuestionKeyIdRandomNum = random.nextInt(questionsList.size());
                if (randomList.size() == questionsList.size())
                    break;
            }
            randomList.add(startQuestionKeyIdRandomNum);


            String startQuestion = questionsList.get(startQuestionKeyIdRandomNum);

            int startKeyId = dbHandler.getKeyId(startQuestion);
            int endQuestionKeyId = startKeyId + numberOfAyatRequired;

            // to get answers
            StringBuilder answers = new StringBuilder();
            for (int j = startKeyId; j <= endQuestionKeyId; j++) {
                answers.append(dbHandler.getQuranRow(String.valueOf(j)).getTextEmlaey()).append(" ");
            }


            Quran quran = dbHandler.getQuranRow(String.valueOf(endQuestionKeyId));
            String endQuestion = quran.getTextEmlaey();


            String[] splitStartQuestion = startQuestion.split("\\s+");
            String[] splitEndQuestion = endQuestion.split("\\s+");

            if (splitEndQuestion.length > 9)
                endQuestion = splitEndQuestion[splitEndQuestion.length - 5] + " " + splitEndQuestion[splitEndQuestion.length - 4] + " " + splitEndQuestion[splitEndQuestion.length - 3] +
                        " " + splitEndQuestion[splitEndQuestion.length - 2] + " " + splitEndQuestion[splitEndQuestion.length - 1];

            if (splitStartQuestion.length > 9)
                startQuestion = splitStartQuestion[0] + " " + splitStartQuestion[1] + " " + splitStartQuestion[2] +
                        " " + splitStartQuestion[3] + " " + splitStartQuestion[4] + " " + splitStartQuestion[5] +
                        " " + splitStartQuestion[6] + " " + splitStartQuestion[7] + " " + splitStartQuestion[8] + " " + splitStartQuestion[9];

            String generateQuestion = " اكتب من قوله تعالي " + " \" " + startQuestion + " \" "
                    + " إلي قوله تعالي " + " \" " + endQuestion + " \" ";

            requiredQuestion.add(generateQuestion);
            requiredAnswers.add(answers.toString());

            if (randomList.size() == questionsList.size())
                break;
        }
        return new QuestionAndAnswer(requiredQuestion, requiredAnswers);
    }

    public ArrayList<ChooseQuestionItem> generateQuestionListChoose(ArrayList<String> questionsList, int questionNum) {
        //generate questions
        ArrayList<Integer> randomList = new ArrayList<>();
        ArrayList<ChooseQuestionItem> requiredQuestion = new ArrayList<>();
        Random random = new Random();

        for (int i = 0; i < questionNum; i++) {

            // to prevent duplicate
            int startQuestionKeyIdRandomNum = random.nextInt(questionsList.size());
            while (randomList.contains(startQuestionKeyIdRandomNum)) {
                startQuestionKeyIdRandomNum = random.nextInt(questionsList.size());
                if (randomList.size() == questionsList.size())
                    break;
            }
            randomList.add(startQuestionKeyIdRandomNum);


            String ayaQuestion = questionsList.get(startQuestionKeyIdRandomNum);
            int suraId = dbHandler.getSuraId(ayaQuestion);

            List<Integer> randomListSura = getRandumSura(suraId);

            List<String> suraNameList = new Constant().getSuraNameList();


            String[] splitQuestion = ayaQuestion.split("\\s+");

            if (splitQuestion.length > 9)
                ayaQuestion = splitQuestion[0] + " " + splitQuestion[1] + " " + splitQuestion[2] +
                        " " + splitQuestion[3] + " " + splitQuestion[4] + " " + splitQuestion[5] +
                        " " + splitQuestion[6] + " " + splitQuestion[7] + " " + splitQuestion[8] + " " + splitQuestion[9];

            ChooseQuestionItem chooseQuestionItem = new ChooseQuestionItem(ayaQuestion,
                    suraNameList.get(randomListSura.get(0)),
                    suraNameList.get(randomListSura.get(1)),
                    suraNameList.get(randomListSura.get(2)),
                    suraNameList.get(randomListSura.get(3)));

            requiredQuestion.add(chooseQuestionItem);
            Log.i("generateQuestion: ", ayaQuestion);

            if (randomList.size() == questionsList.size())
                break;
        }

        return requiredQuestion;
    }

    public ArrayList<Integer> getRandumSura(int suraId) {

        ArrayList<Integer> randomListSura = new ArrayList<>();
        randomListSura.add(suraId - 1);

        if (suraId > 1) {
            randomListSura.add(suraId - 2);
        } else {
            Random random = new Random();
            int randomSuraId = random.nextInt(113);
            while (randomListSura.contains(randomSuraId)) {
                randomSuraId = random.nextInt(113);
            }
            randomListSura.add(randomSuraId);
        }

        if (suraId != 114) {
            randomListSura.add(suraId);
        } else {
            Random random = new Random();
            int randomSuraId = random.nextInt(113);
            while (randomListSura.contains(randomSuraId)) {
                randomSuraId = random.nextInt(113);
            }
            randomListSura.add(randomSuraId);
        }

        if (suraId < 113) //suraNameList.size() 113 index
        {
            randomListSura.add(suraId + 1);
        } else {
            Random random = new Random();
            int randomSuraId = random.nextInt(113);
            while (randomListSura.contains(randomSuraId)) {
                randomSuraId = random.nextInt(113);
            }
            randomListSura.add(randomSuraId);
        }

        Collections.shuffle(randomListSura);
        return randomListSura;
    }

    public QuestionAndAnswer generateQuestionListCompleteEnd(ArrayList<String> questionsList,
                                                             int questionNum) {
        //generate questions
        ArrayList<Integer> randomList = new ArrayList<>();
        ArrayList<String> requiredQuestion = new ArrayList<>();
        ArrayList<String> requiredAnswers = new ArrayList<>();
        Random random = new Random();

        for (int i = 0; i < questionNum; i++) {

            int startQuestionKeyIdRandomNum = random.nextInt(questionsList.size());
            while (randomList.contains(startQuestionKeyIdRandomNum)) {
                startQuestionKeyIdRandomNum = random.nextInt(questionsList.size());
                if (randomList.size() == questionsList.size())
                    break;
            }
            randomList.add(startQuestionKeyIdRandomNum);

            String ayaQuestion = questionsList.get(startQuestionKeyIdRandomNum);

            String[] splitQuestion = ayaQuestion.split("\\s+");

            if (splitQuestion.length > 6) {
                StringBuilder question = new StringBuilder();
                for (int j = 0; j < splitQuestion.length - 4; j++) {
                    question.append(" ").append(splitQuestion[j]);
                }

                question.append("......");
                requiredQuestion.add(question.toString());
                requiredAnswers.add(ayaQuestion);
            }

            if (randomList.size() == questionsList.size())
                break;
        }

        return new QuestionAndAnswer(requiredQuestion, requiredAnswers);
    }

    public QuestionAndAnswer generateQuestionListCompleteEnd(ArrayList<String> questionsList) {
        //generate questions
        ArrayList<Integer> randomList = new ArrayList<>();
        ArrayList<String> requiredQuestion = new ArrayList<>();
        ArrayList<String> requiredAnswers = new ArrayList<>();
        Random random = new Random();

        for (int i = 0; i < 6; i++) {

            int startQuestionKeyIdRandomNum = random.nextInt(questionsList.size());
            while (randomList.contains(startQuestionKeyIdRandomNum)) {
                startQuestionKeyIdRandomNum = random.nextInt(questionsList.size());
                if (randomList.size() == questionsList.size())
                    break;
            }
            randomList.add(startQuestionKeyIdRandomNum);

            String ayaQuestion = questionsList.get(startQuestionKeyIdRandomNum);

            String[] splitQuestion = ayaQuestion.split("\\s+");
            int ayaLength = splitQuestion.length;

            if (ayaLength > 6) {
                StringBuilder question = new StringBuilder();
                for (int j = 0; j < ayaLength - 4; j++) {
                    question.append(" ").append(splitQuestion[j]);
                }

                requiredQuestion.add(question.toString());
                requiredAnswers.add(splitQuestion[ayaLength - 4] + " " + splitQuestion[ayaLength - 3] + " " + splitQuestion[ayaLength - 2] + " "
                        + splitQuestion[ayaLength - 1] + " ");
            }

            if (randomList.size() == questionsList.size())
                break;
        }

        return new QuestionAndAnswer(requiredQuestion, requiredAnswers);
    }

}
