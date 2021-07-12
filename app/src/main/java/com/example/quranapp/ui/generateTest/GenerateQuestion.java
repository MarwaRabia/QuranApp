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
    private ArrayList<String> easyQuestions, mediumQuestion, difficultQuestions;
    private int startKeyId, endKeyId;
    private ArrayList<QuestionAndAnswer> generateQuestionListComplete, generateQuestionListCompleteEnd;
    private ArrayList<ChooseQuestionItem> generateQuestionListChoose;

    public GenerateQuestion(DbHandler dbHandler, Context context, int startKeyId, int endKeyId) {
        this.dbHandler = dbHandler;
        this.context = context;
        this.startKeyId = startKeyId;
        this.endKeyId = endKeyId;
    }

    public void generateQuestionTest(String questionDifficulty, String questionType, int questionNum
            , ArrayList<String> questionTypeArrayList) {

        getEasyMediumDifficultQuestions(startKeyId, endKeyId);

        // generate questions
        if (questionDifficulty.equals("سهل")) {

            // case questions less thar required
            if (questionNum > easyQuestions.size()) {
                int i = 0;
                while (questionNum > easyQuestions.size()) {
                    easyQuestions.add(mediumQuestion.get(i));
                    i++;

                    if (i == mediumQuestion.size())
                        break;
                }
            }

            if (questionType.equals("أكمل")) {
                generateQuestionListComplete = generateQuestionListComplete(easyQuestions, questionNum);
            } else if (questionType.equals("اختياري (اسم السورة)")) {
                generateQuestionListChoose = generateQuestionListChoose(easyQuestions, questionNum);
            } else if (questionType.equals("أكمل نهاية الآيات")) {
                generateQuestionListCompleteEnd = generateQuestionListCompleteEnd(easyQuestions, questionNum);
            } else {
                // متنوعة
                if (questionTypeArrayList.contains("اختياري (اسم السورة)")) {
                    int completeNum = questionNum / 3;
                    int completeEndNum = questionNum / 3;
                    int chooseNum = questionNum - (completeNum + completeEndNum);
                    generateQuestionListComplete = generateQuestionListComplete(easyQuestions, completeNum);
                    generateQuestionListChoose = generateQuestionListChoose(easyQuestions, chooseNum);
                    generateQuestionListCompleteEnd = generateQuestionListCompleteEnd(easyQuestions, completeEndNum);

                } else {
                    int completeNum = questionNum / 2;
                    int completeEndNum = questionNum - completeNum;
                    generateQuestionListComplete = generateQuestionListComplete(easyQuestions, completeNum);
                    generateQuestionListCompleteEnd = generateQuestionListCompleteEnd(easyQuestions, completeEndNum);
                }
            }

        } else if (questionDifficulty.equals("متوسط")) {
            if (questionType.equals("أكمل")) {
                generateQuestionListComplete = generateQuestionListComplete(mediumQuestion, questionNum);
            } else if (questionType.equals("اختياري (اسم السورة)")) {
                generateQuestionListChoose = generateQuestionListChoose(mediumQuestion, questionNum);
            } else if (questionType.equals("أكمل نهاية الآيات")) {
                generateQuestionListCompleteEnd = generateQuestionListCompleteEnd(mediumQuestion, questionNum);
            } else {
                // متنوعة
                if (questionTypeArrayList.contains("اختياري (اسم السورة)")) {
                    int completeNum = questionNum / 3;
                    int completeEndNum = questionNum / 3;
                    int chooseNum = questionNum - (completeNum + completeEndNum);
                    generateQuestionListComplete = generateQuestionListComplete(mediumQuestion, completeNum);
                    generateQuestionListChoose = generateQuestionListChoose(mediumQuestion, chooseNum);
                    generateQuestionListCompleteEnd = generateQuestionListCompleteEnd(mediumQuestion, completeEndNum);
                } else {
                    int completeNum = questionNum / 2;
                    int completeEndNum = questionNum - completeNum;
                    generateQuestionListComplete = generateQuestionListComplete(mediumQuestion, completeNum);
                    generateQuestionListCompleteEnd = generateQuestionListCompleteEnd(mediumQuestion, completeEndNum);
                }
            }
        } else {
            // صعب
            // case questions less thar required
            if (questionNum > difficultQuestions.size()) {
                int i = 0;
                while (questionNum > difficultQuestions.size()) {
                    difficultQuestions.add(mediumQuestion.get(i));
                    i++;

                    if (i == mediumQuestion.size())
                        break;
                }
            }

            if (questionType.equals("أكمل")) {
                generateQuestionListComplete = generateQuestionListComplete(difficultQuestions, questionNum);
            } else if (questionType.equals("اختياري (اسم السورة)")) {
                generateQuestionListChoose = generateQuestionListChoose(difficultQuestions, questionNum);
            } else if (questionType.equals("أكمل نهاية الآيات")) {
                generateQuestionListCompleteEnd = generateQuestionListCompleteEnd(difficultQuestions, questionNum);
            } else {
                // متنوعة
                // متنوعة
                if (questionTypeArrayList.contains("اختياري (اسم السورة)")) {
                    int completeNum = questionNum / 3;
                    int completeEndNum = questionNum / 3;
                    int chooseNum = questionNum - (completeNum + completeEndNum);
                    generateQuestionListComplete = generateQuestionListComplete(difficultQuestions, completeNum);
                    generateQuestionListChoose = generateQuestionListChoose(difficultQuestions, chooseNum);
                    generateQuestionListCompleteEnd = generateQuestionListCompleteEnd(difficultQuestions, completeEndNum);
                } else {
                    int completeNum = questionNum / 2;
                    int completeEndNum = questionNum - completeNum;
                    generateQuestionListComplete = generateQuestionListComplete(difficultQuestions, completeNum);
                    generateQuestionListCompleteEnd = generateQuestionListCompleteEnd(difficultQuestions, completeEndNum);
                }
            }
        }

    }

    private void getEasyMediumDifficultQuestions(int startKeyId, int endKeyId) {
        easyQuestions = new ArrayList<>();
        mediumQuestion = new ArrayList<>();
        difficultQuestions = new ArrayList<>();

        for (int i = startKeyId; i <= endKeyId; i++) {
            Quran question = dbHandler.getQuranRow(String.valueOf(i));
            // every aya
            mediumQuestion.add(question.getTextEmlaey());

            // verse easy question (بداية الربع) difficult (قبل الربع ب آية)
            if (question.getVerseId() == 1) {
                easyQuestions.add(question.getTextEmlaey());

                if (i != startKeyId) {
                    Quran difficultQuestion = dbHandler.getQuranRow(String.valueOf(i - 1));
                    difficultQuestions.add(difficultQuestion.getTextEmlaey());
                }
            }

            // page easy question (بداية الصفحة) difficult (نهاية الصفحة)
            Quran previous = dbHandler.getQuranRow(String.valueOf(i - 1));
            int previousAyaPage = previous.getPage();
            int currentAyaPage = question.getPage();

            if (currentAyaPage != previousAyaPage) {
                easyQuestions.add(question.getTextEmlaey());
                difficultQuestions.add(previous.getTextEmlaey());
            }

            // sura easy question (بداية السورة) difficult (نهاية السورة)
            int previousSuraNo = previous.getSuraNo();
            int currentSuraNo = question.getSuraNo();

            if (currentSuraNo != previousSuraNo) {
                easyQuestions.add(question.getTextEmlaey());
                difficultQuestions.add(previous.getTextEmlaey());
            }
        }
    }

    public ArrayList<QuestionAndAnswer> generateQuestionListComplete(List<String> questionsList, int questionNum) {
        //generate questions
        ArrayList<Integer> randomList = new ArrayList<>();
        ArrayList<QuestionAndAnswer> questionAndAnswerList = new ArrayList<>();
        Random random = new Random();

        for (int i = 0; i < questionNum; i++) {

            int startQuestionKeyIdRandomNum = random.nextInt(questionsList.size());
            while (randomList.contains(startQuestionKeyIdRandomNum)) {
                startQuestionKeyIdRandomNum = random.nextInt(questionsList.size());
                if (randomList.size() == questionsList.size())
                    break;
            }
            randomList.add(startQuestionKeyIdRandomNum);


            String startQuestion = questionsList.get(startQuestionKeyIdRandomNum);
            int startKeyId = dbHandler.getKeyId(startQuestion);
            int numberOfAyatRequired = 3;

            while (startKeyId + numberOfAyatRequired > endKeyId) {
                startQuestionKeyIdRandomNum = random.nextInt(questionsList.size());
                while (randomList.contains(startQuestionKeyIdRandomNum)) {
                    startQuestionKeyIdRandomNum = random.nextInt(questionsList.size());
                    if (randomList.size() == questionsList.size())
                        break;
                }
                randomList.add(startQuestionKeyIdRandomNum);


                startQuestion = questionsList.get(startQuestionKeyIdRandomNum);
                startKeyId = dbHandler.getKeyId(startQuestion);

                if (randomList.size() == questionsList.size())
                    break;
            }

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

            String question = " اكتب من قوله تعالي " + " \" " + startQuestion + " \" "
                    + " إلي قوله تعالي " + " \" " + endQuestion + " \" ";

            questionAndAnswerList.add(new QuestionAndAnswer(question, answers.toString()));

            if (randomList.size() == questionsList.size())
                break;
        }
        return questionAndAnswerList;
    }

    private ArrayList<ChooseQuestionItem> generateQuestionListChoose(ArrayList<String> questionsList, int questionNum) {
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
            int suraId = dbHandler.getSuraId(ayaQuestion); // البقرة (2)

            List<Integer> randomListSura = getRandumSura(suraId);

            List<String> suraNameList = new Constant().getSuraNameList(); // البقرة (1)


            String[] splitQuestion = ayaQuestion.split("\\s+");

            if (splitQuestion.length > 9)
                ayaQuestion = splitQuestion[0] + " " + splitQuestion[1] + " " + splitQuestion[2] +
                        " " + splitQuestion[3] + " " + splitQuestion[4] + " " + splitQuestion[5] +
                        " " + splitQuestion[6] + " " + splitQuestion[7] + " " + splitQuestion[8] + " " + splitQuestion[9];

            ChooseQuestionItem chooseQuestionItem = new ChooseQuestionItem(ayaQuestion,
                    suraNameList.get(randomListSura.get(0)),
                    suraNameList.get(randomListSura.get(1)),
                    suraNameList.get(randomListSura.get(2)),
                    suraNameList.get(randomListSura.get(3)),
                    suraNameList.get(suraId -1));

            requiredQuestion.add(chooseQuestionItem);

            if (randomList.size() == questionsList.size())
                break;
        }

        return requiredQuestion;
    }

    private ArrayList<Integer> getRandumSura(int suraId) {

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

    private ArrayList<QuestionAndAnswer> generateQuestionListCompleteEnd(ArrayList<String> questionsList,
                                                                         int questionNum) {
        //generate questions
        ArrayList<Integer> randomList = new ArrayList<>();
        ArrayList<QuestionAndAnswer> questionAndAnswerList = new ArrayList<>();
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
            int ayaLength = splitQuestion.length;

            if (ayaLength > 4) {
                StringBuilder question = new StringBuilder();
                for (int j = 0; j < ayaLength - 3; j++) {
                    question.append(" ").append(splitQuestion[j]);
                }

                question.append(" ................. ");
                questionAndAnswerList.add(new QuestionAndAnswer(question.toString(), ayaQuestion));
            }

            if (randomList.size() == questionsList.size())
                break;
        }

        return questionAndAnswerList;
    }

    public List<QuestionAndAnswer> generateQuestionListCompleteEnd(ArrayList<String> questionsList) {
        //generate questions
        ArrayList<Integer> randomList = new ArrayList<>();
        List<QuestionAndAnswer> questionAndAnswerList = new ArrayList<>();
        Random random = new Random();

        while (questionAndAnswerList.size() < 4) {

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

            if (ayaLength > 4) {
                StringBuilder question = new StringBuilder();
                for (int j = 0; j < ayaLength - 3; j++) {
                    question.append(" ").append(splitQuestion[j]);
                }

                String answer = splitQuestion[ayaLength - 3] + " " + splitQuestion[ayaLength - 2] + " "
                        + splitQuestion[ayaLength - 1] + " ";

                questionAndAnswerList.add(new QuestionAndAnswer(question.toString(), answer));
            }

            if (randomList.size() == questionsList.size())
                break;
        }

        return questionAndAnswerList;
    }

    public ArrayList<QuestionAndAnswer> getGenerateQuestionListComplete() {
        return generateQuestionListComplete;
    }

    public ArrayList<QuestionAndAnswer> getGenerateQuestionListCompleteEnd() {
        return generateQuestionListCompleteEnd;
    }

    public ArrayList<ChooseQuestionItem> getGenerateQuestionListChoose() {
        return generateQuestionListChoose;
    }
}
