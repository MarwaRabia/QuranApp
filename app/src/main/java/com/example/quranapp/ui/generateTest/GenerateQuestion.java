package com.example.quranapp.ui.generateTest;

import android.content.Context;
import android.util.Log;


import com.example.quranapp.db.DbHandler;
import com.example.quranapp.db.Quran;
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

    public List<String> getSuraNameList() {
        ArrayList<String> suraNamesArrayList = new ArrayList<>();
        suraNamesArrayList.add("سورة الفاتحة");
        suraNamesArrayList.add("سورة البقرة");
        suraNamesArrayList.add("سورة آل عمران");
        suraNamesArrayList.add("سورة النساء");
        suraNamesArrayList.add("سورة المائدة");
        suraNamesArrayList.add("سورة الأنعام");
        suraNamesArrayList.add("سورة الأعراف");
        suraNamesArrayList.add("سورة الأنفال");
        suraNamesArrayList.add("سورة التوبة");
        suraNamesArrayList.add("سورة يونس");
        suraNamesArrayList.add("سورة هود");
        suraNamesArrayList.add("سورة يوسف");
        suraNamesArrayList.add("سورة الرعد");
        suraNamesArrayList.add("سورة إبراهيم");
        suraNamesArrayList.add("سورة الحجر");
        suraNamesArrayList.add("سورة النحل");
        suraNamesArrayList.add("سورة الإسراء");
        suraNamesArrayList.add("سورة الكهف");
        suraNamesArrayList.add("سورة مريم");
        suraNamesArrayList.add("سورة طه");
        suraNamesArrayList.add("سورة الأنبياء");
        suraNamesArrayList.add("سورة الحج");
        suraNamesArrayList.add("سورة المؤمنون");
        suraNamesArrayList.add("سورة النور");
        suraNamesArrayList.add("سورة الفرقان");
        suraNamesArrayList.add("سورة الشعراء");
        suraNamesArrayList.add("سورة النمل");
        suraNamesArrayList.add("سورة القصص");
        suraNamesArrayList.add("سورة العنكبوت");
        suraNamesArrayList.add("سورة الروم");
        suraNamesArrayList.add("سورة لقمان");
        suraNamesArrayList.add("سورة السجدة");
        suraNamesArrayList.add("سورة الأحزاب");
        suraNamesArrayList.add("سورة سبأ");
        suraNamesArrayList.add("سورة فاطر");
        suraNamesArrayList.add("سورة يس");
        suraNamesArrayList.add("سورة الصافات");
        suraNamesArrayList.add("سورة ص");
        suraNamesArrayList.add("سورة الزمر");
        suraNamesArrayList.add("سورة غافر");
        suraNamesArrayList.add("سورة فصلت");
        suraNamesArrayList.add("سورة الشوري");
        suraNamesArrayList.add("سورة الزخرف");
        suraNamesArrayList.add("سورة الدخان");
        suraNamesArrayList.add("سورة الجاثية");
        suraNamesArrayList.add("سورة الأحقاف");
        suraNamesArrayList.add("سورة محمد");
        suraNamesArrayList.add("سورة الفتح");
        suraNamesArrayList.add("سورة الحجرات");
        suraNamesArrayList.add("سورة ق");
        suraNamesArrayList.add("سورة الذاريات");
        suraNamesArrayList.add("سورة الطور");
        suraNamesArrayList.add("سورة النجم");
        suraNamesArrayList.add("سورة القمر");
        suraNamesArrayList.add("سورة الرحمن");
        suraNamesArrayList.add("سورة الواقعة");
        suraNamesArrayList.add("سورة الحديد");
        suraNamesArrayList.add("سورة المجادلة");
        suraNamesArrayList.add("سورة الحشر");
        suraNamesArrayList.add("سورة الممتحنة");
        suraNamesArrayList.add("سورة الصف");
        suraNamesArrayList.add("سورة الجمعة");
        suraNamesArrayList.add("سورة المنافقون");
        suraNamesArrayList.add("سورة التغابن");
        suraNamesArrayList.add("سورة الطلاق");
        suraNamesArrayList.add("سورة التحريم");
        suraNamesArrayList.add("سورة الملك");
        suraNamesArrayList.add("سورة القلم");
        suraNamesArrayList.add("سورة الحاقة");
        suraNamesArrayList.add("سورة المعارج");
        suraNamesArrayList.add("سورة نوح");
        suraNamesArrayList.add("سورة الجن");
        suraNamesArrayList.add("سورة المزمل");
        suraNamesArrayList.add("سورة المدثر");
        suraNamesArrayList.add("سورة القيامة");
        suraNamesArrayList.add("سورة الإنسان");
        suraNamesArrayList.add("سورة المرسلات");
        suraNamesArrayList.add("سورة النبأ");
        suraNamesArrayList.add("سورة النازعات");
        suraNamesArrayList.add("سورة عبس");
        suraNamesArrayList.add("سورة التكوير");
        suraNamesArrayList.add("سورة الانفطار");
        suraNamesArrayList.add("سورة المطففين");
        suraNamesArrayList.add("سورة الانشقاق");
        suraNamesArrayList.add("سورة البروج");
        suraNamesArrayList.add("سورة الطارق");
        suraNamesArrayList.add("سورة الأعلي");
        suraNamesArrayList.add("سورة الغاشية");
        suraNamesArrayList.add("سورة الفجر");
        suraNamesArrayList.add("سورة البلد");
        suraNamesArrayList.add("سورة الشمس");
        suraNamesArrayList.add("سورة الليل");
        suraNamesArrayList.add("سورة الضحي");
        suraNamesArrayList.add("سورة الشرح");
        suraNamesArrayList.add("سورة التين");
        suraNamesArrayList.add("سورة العلق");
        suraNamesArrayList.add("سورة القدر");
        suraNamesArrayList.add("سورة البينة");
        suraNamesArrayList.add("سورة الزلزلة");
        suraNamesArrayList.add("سورة العاديات");
        suraNamesArrayList.add("سورة القارعة");
        suraNamesArrayList.add("سورة التكاثر");
        suraNamesArrayList.add("سورة العصر");
        suraNamesArrayList.add("سورة الهمزة");
        suraNamesArrayList.add("سورة الفيل");
        suraNamesArrayList.add("سورة قريش");
        suraNamesArrayList.add("سورة الماعون");
        suraNamesArrayList.add("سورة الكوثر");
        suraNamesArrayList.add("سورة الكافرون");
        suraNamesArrayList.add("سورة النصر");
        suraNamesArrayList.add("سورة المسد");
        suraNamesArrayList.add("سورة الإخلاص");
        suraNamesArrayList.add("سورة الفلق");
        suraNamesArrayList.add("سورة الناس");

        return suraNamesArrayList;
    }

    public ArrayList<String> generateQuestionListComplete(ArrayList<String> questionsList, int numberOfAyatRequired,
                                                          int questionNum) {
        //generate questions
        ArrayList<Integer> randomList = new ArrayList<>();
        ArrayList<String> requiredQuestion = new ArrayList<>();
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


            int endQuestionKeyId = dbHandler.getKeyId(startQuestion) + numberOfAyatRequired;
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
            Log.i("generateQuestion: ", generateQuestion);

            if (randomList.size() == questionsList.size())
                break;
        }

        return requiredQuestion;
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

            List<String> suraNameList = getSuraNameList();


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

    public ArrayList<String> generateQuestionListCompleteEnd(ArrayList<String> questionsList,
                                                             int questionNum) {
        //generate questions
        ArrayList<Integer> randomList = new ArrayList<>();
        ArrayList<String> requiredQuestion = new ArrayList<>();
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

                question.append(" ......");
                requiredQuestion.add(question.toString());
                Log.i("generateQuestion: ", ayaQuestion);
            }

            if (randomList.size() == questionsList.size())
                break;
        }

        return requiredQuestion;
    }
}
