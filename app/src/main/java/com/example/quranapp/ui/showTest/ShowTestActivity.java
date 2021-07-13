package com.example.quranapp.ui.showTest;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.quranapp.R;
import com.example.quranapp.ui.generateTest.QuestionAndAnswer;
import com.example.quranapp.ui.sheikhHome.SheikhHomeActivity;
import com.independentsoft.office.word.AbsolutePositionTabAlignment;
import com.independentsoft.office.word.Run;
import com.independentsoft.office.word.WordDocument;
import com.itextpdf.text.Document;
import com.itextpdf.text.Font;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class ShowTestActivity extends AppCompatActivity {
    private RecyclerView completeQRecyclerView, endOfAyaCompleteRecyclerView, chooseQRecyclerView;
    private TextView completeQTextView, endOfAyaCompleteTextView, chooseQTextView;

    private List<QuestionAndAnswer> generateListComplete, generateListCompleteEnd;
    private List<ChooseQuestionItem> generateQuestionListChoose;

    private ChooseQAdapter chooseQAdapter;
    private QuranAdapter quranAdapter;
    private Button downloadPdfButton, downloadWordButton;
    private static final int STORAGE_CODE_PDF = 100;
    private static final int STORAGE_CODE_WORD = 200;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_test);

        Bundle bundle = getIntent().getExtras();
        generateQuestionListChoose = bundle.getParcelableArrayList("generateQuestionListChoose");
        generateListComplete = bundle.getParcelableArrayList("generateQuestionListComplete");
        generateListCompleteEnd = bundle.getParcelableArrayList("generateQuestionListCompleteEnd");

        initViews();
        setUpRecyclerView();
        setListeners();
    }

    private void initViews() {
        chooseQRecyclerView = findViewById(R.id.choose_q_recycler);
        completeQRecyclerView = findViewById(R.id.complete_q_recycler);
        endOfAyaCompleteRecyclerView = findViewById(R.id.end_of_aya_complete_q_recycler);
        chooseQTextView = findViewById(R.id.choose_q);
        completeQTextView = findViewById(R.id.complete_q);
        endOfAyaCompleteTextView = findViewById(R.id.end_of_aya_complete_q);
        downloadPdfButton = findViewById(R.id.button_download_test_as_pdf);
        downloadWordButton = findViewById(R.id.button_download_test_as_word);
    }

    private void setUpRecyclerView() {

        if (generateListComplete != null) {
            LinearLayoutManager layoutManager
                    = new LinearLayoutManager(ShowTestActivity.this, LinearLayoutManager.VERTICAL, false);
            completeQRecyclerView.setLayoutManager(layoutManager); // set LayoutManager to RecyclerView

            quranAdapter = new QuranAdapter(generateListComplete);
            completeQRecyclerView.setAdapter(quranAdapter);

            completeQRecyclerView.setVisibility(View.VISIBLE);
            completeQTextView.setVisibility(View.VISIBLE);
        } else {
            completeQTextView.setVisibility(View.GONE);
            completeQRecyclerView.setVisibility(View.GONE);
        }

        if (generateListCompleteEnd != null) {

            LinearLayoutManager layoutManager
                    = new LinearLayoutManager(ShowTestActivity.this, LinearLayoutManager.VERTICAL, false);
            endOfAyaCompleteRecyclerView.setLayoutManager(layoutManager); // set LayoutManager to RecyclerView

            quranAdapter = new QuranAdapter(generateListCompleteEnd);
            endOfAyaCompleteRecyclerView.setAdapter(quranAdapter);

            endOfAyaCompleteTextView.setVisibility(View.VISIBLE);
            endOfAyaCompleteRecyclerView.setVisibility(View.VISIBLE);
        } else {
            endOfAyaCompleteTextView.setVisibility(View.GONE);
            endOfAyaCompleteRecyclerView.setVisibility(View.GONE);
        }

        if (generateQuestionListChoose != null) {
            LinearLayoutManager layoutManager
                    = new LinearLayoutManager(ShowTestActivity.this, LinearLayoutManager.VERTICAL, false);
            chooseQRecyclerView.setLayoutManager(layoutManager); // set LayoutManager to RecyclerView

            chooseQAdapter = new ChooseQAdapter(generateQuestionListChoose);
            chooseQRecyclerView.setAdapter(chooseQAdapter);

            chooseQTextView.setVisibility(View.VISIBLE);
            chooseQRecyclerView.setVisibility(View.VISIBLE);
        } else {
            chooseQTextView.setVisibility(View.GONE);
            chooseQRecyclerView.setVisibility(View.GONE);
        }

    }

    private void setListeners() {
        downloadPdfButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                StringBuilder dataExamQuestion = generateQuestionsAsStringBuilder();
                StringBuilder dataExamAnswer = generateAnswersAsStringBuilder();

                if (Build.VERSION.SDK_INT > Build.VERSION_CODES.M) {
                    if (checkSelfPermission(android.Manifest.permission.WRITE_EXTERNAL_STORAGE) ==
                            (PackageManager.PERMISSION_DENIED)) {
                        String[] permissions = {android.Manifest.permission.WRITE_EXTERNAL_STORAGE};
                        requestPermissions(permissions, STORAGE_CODE_PDF);
                    } else {
                        downloadQuestionsPDFpdf(dataExamQuestion.toString());
                        downloadAnswersPDFpdf(dataExamAnswer.toString());
                    }
                } else {
                    downloadQuestionsPDFpdf(dataExamQuestion.toString());
                    downloadAnswersPDFpdf(dataExamAnswer.toString());
                }
            }
        });

        downloadWordButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StringBuilder dataExamQuestion = generateQuestionsAsStringBuilder();
                StringBuilder dataExamAnswer = generateAnswersAsStringBuilder();

                if (Build.VERSION.SDK_INT > Build.VERSION_CODES.M) {
                    if (checkSelfPermission(android.Manifest.permission.WRITE_EXTERNAL_STORAGE) ==
                            (PackageManager.PERMISSION_DENIED)) {
                        String[] permissions = {android.Manifest.permission.WRITE_EXTERNAL_STORAGE};
                        requestPermissions(permissions, STORAGE_CODE_WORD);
                    } else {
                        downloadQuestionsWord(dataExamQuestion.toString());
                        downloadAnswersWord(dataExamAnswer.toString());
                    }
                } else {
                    downloadQuestionsWord(dataExamQuestion.toString());
                    downloadAnswersWord(dataExamAnswer.toString());
                }
            }
        });
    }

    private StringBuilder generateAnswersAsStringBuilder() {
        StringBuilder dataExam = new StringBuilder();
        dataExam.append("\n");
        dataExam.append("                              إجابة الاختبار                               ");
        dataExam.append("\n");


        if (generateListComplete != null) {
            dataExam.append("أكمل هذه الآيات:  ");
            dataExam.append("\n");
            for (int i = 0; i < generateListComplete.size(); i++) {
                dataExam.append(convertToArabic(i + 1)).append(".  ")
                        .append(generateListComplete.get(i).getAnswer())
                        .append("\n");
            }
            dataExam.append("________________________________\n");
        }

        if (generateListCompleteEnd != null) {
            dataExam.append("أكمل نهاية الأيات : ");
            dataExam.append("\n");
            for (int i = 0; i < generateListCompleteEnd.size(); i++) {
                dataExam.append(convertToArabic(i + 1)).append(".  ")
                        .append(generateListCompleteEnd.get(i).getAnswer())
                        .append("\n");
            }
            dataExam.append("________________________________\n");
        }

        if (generateQuestionListChoose != null) {
            dataExam.append("اختر اسم السورة:  ");
            dataExam.append("\n");
            for (int i = 0; i < generateQuestionListChoose.size(); i++) {
                dataExam.append(convertToArabic(i + 1)).append(".  ")
                        .append(generateQuestionListChoose.get(i).getCorrectAnswer())
                        .append("\n");
            }
            dataExam.append("________________________________\n");
        }

        return dataExam;
    }

    private StringBuilder generateQuestionsAsStringBuilder() {
        StringBuilder dataExam = new StringBuilder();
        dataExam.append("\n");
        dataExam.append("                              اختبار حفظ القرآن                              ");
        dataExam.append("\n");


        if (generateListComplete != null) {
            dataExam.append("أكمل هذه الآيات:  ");
            dataExam.append("\n");
            for (int i = 0; i < generateListComplete.size(); i++) {
                dataExam.append(convertToArabic(i + 1)).append(".  ")
                        .append(generateListComplete.get(i).getQuestion())
                        .append("\n________________________________\n");
            }
        }

        if (generateListCompleteEnd != null) {
            dataExam.append("أكمل نهاية الأيات : ");
            dataExam.append("\n");
            for (int i = 0; i < generateListCompleteEnd.size(); i++) {
                dataExam.append(convertToArabic(i + 1)).append(".  ")
                        .append(generateListCompleteEnd.get(i).getQuestion())
                        .append("\n________________________________\n");
            }
        }

        if (generateQuestionListChoose != null) {
            dataExam.append("اختر اسم السورة:  ");
            dataExam.append("\n");
            for (int i = 0; i < generateQuestionListChoose.size(); i++) {
                ChooseQuestionItem item = generateQuestionListChoose.get(i);
                String choose = convertToArabic(i + 1) + ". \" " + item.getQuestion() + " \" " + "\n ١. " + item.getChoose1()
                        + "\n ٢.  " + item.getChoose2() + "\n ٣.  " + item.getChoose3()
                        + "\n ٤.  " + item.getChoose4();

                dataExam.append(choose).append("\n________________________________\n");
            }
        }

        return dataExam;
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void downloadQuestionsPDFpdf(String data) {
        String mfilePath1 = Environment.getExternalStorageDirectory().getPath() + "/quran.exams.pdf/";
        File file = new File(mfilePath1);
        if (!file.exists()) {
            file.mkdirs();
        }
        String q_filename = "Test Questions_" + UUID.randomUUID().toString().substring(0, 4);

        String targetPdf = mfilePath1 + q_filename + ".pdf";
        File mfilePath = new File(targetPdf);

        try {
            // document
            Document document = new Document(PageSize.A4, 0f, 0f, 0f, 0f);
            document.setMargins(22, 22, 22, 22);
            //writer
            PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(mfilePath));
            writer.setInitialLeading(24.5f);
            writer.setRunDirection(PdfWriter.RUN_DIRECTION_RTL);
            // step 4
            document.open();
            // step 5
            //try adding new table
            PdfPTable table = new PdfPTable(1);
            table.setRunDirection(PdfWriter.RUN_DIRECTION_RTL);
            Font f = new Font(BaseFont.createFont("assets/fonts/trado.otf", BaseFont.IDENTITY_H, BaseFont.EMBEDDED));

            f.setSize(26.0f);
            PdfPCell cell = new PdfPCell(new Paragraph(data.replaceAll("\n", "\n\n"), f));
            cell.setPaddingBottom(8);
            cell.setBorder(Rectangle.NO_BORDER);
            table.addCell(cell);
            document.add(table);
            // step 6
            document.close();
            Toast.makeText(this, q_filename + ".pdf\n is saved to \n" + mfilePath, Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
            Log.e("PDF", "savepdf: " + e.getMessage());
        }
    }

    private void downloadAnswersPDFpdf(String data) {
        String mfilePath1 = Environment.getExternalStorageDirectory().getPath() + "/quran.exams.pdf/";
        File file = new File(mfilePath1);
        if (!file.exists()) {
            file.mkdirs();
        }
        String q_filename = "Test Answers_" + UUID.randomUUID().toString().substring(0, 4);

        String targetPdf = mfilePath1 + q_filename + ".pdf";
        File mfilePath = new File(targetPdf);

        try {
            // document
            Document document = new Document(PageSize.A4, 0f, 0f, 0f, 0f);
            document.setMargins(22, 22, 22, 22);
            //writer
            PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(mfilePath));
            writer.setInitialLeading(24.5f);
            writer.setRunDirection(PdfWriter.RUN_DIRECTION_RTL);
            // step 4
            document.open();
            // step 5
            //try adding new table
            PdfPTable table = new PdfPTable(1);
            table.setRunDirection(PdfWriter.RUN_DIRECTION_RTL);
            Font f = new Font(BaseFont.createFont("assets/fonts/trado.otf", BaseFont.IDENTITY_H, BaseFont.EMBEDDED));

            f.setSize(26.0f);
            PdfPCell cell = new PdfPCell(new Paragraph(data.replaceAll("\n", "\n\n"), f));
            cell.setPaddingBottom(8);
            cell.setBorder(Rectangle.NO_BORDER);
            table.addCell(cell);
            document.add(table);
            // step 6
            document.close();
            Toast.makeText(this, q_filename + ".pdf\n is saved to \n" + mfilePath, Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
            Log.e("PDF", "savepdf: " + e.getMessage());
        }

    }

    // creat  word file
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private void downloadQuestionsWord(String lines) {
        String filePath = getExternalPath("Test Questions");
        try {
            WordDocument doc = new WordDocument();
            Run run = new Run(lines);
            run.setFontSize(52);
            run.addAbsolutePositionTab(AbsolutePositionTabAlignment.RIGHT);

            com.independentsoft.office.word.Paragraph paragraph = new com.independentsoft.office.word.Paragraph();
            paragraph.add(run);
            doc.getBody().add(paragraph);

            doc.save(filePath, true);

            Toast.makeText(ShowTestActivity.this, "file \n is saved to \n" + filePath, Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            Toast.makeText(ShowTestActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
            Log.d("Word", "saveWord: " + e.getMessage());
        }
    }

    private void downloadAnswersWord(String data) {
        String filePath = getExternalPath("Test Answers");
        try {
            WordDocument doc = new WordDocument();
            Run run = new Run(data);
            run.setFontSize(52);

            com.independentsoft.office.word.Paragraph paragraph = new com.independentsoft.office.word.Paragraph();
            paragraph.add(run);

            doc.getBody().add(paragraph);

            doc.save(filePath, true);

            Toast.makeText(ShowTestActivity.this, "file \n is saved to \n" + filePath, Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            Toast.makeText(ShowTestActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
            Log.d("Word", "saveWord: " + e.getMessage());
        }

    }

    // git path of word file
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private String getExternalPath(String fileName) {
        String mfilePath1 = Environment.getExternalStorageDirectory().getPath() + "/quran.exams.word/";
        File file = new File(mfilePath1);
        if (!file.exists()) {
            file.mkdirs();
        }
        String mfilename = fileName + UUID.randomUUID().toString().substring(0, 4);
        String targetPdf = mfilePath1 + mfilename + ".docx";
        File mfilePath = new File(targetPdf);
        return mfilePath.getPath();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case STORAGE_CODE_PDF:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    downloadPdfButton.performClick();
                } else {
                    Toast.makeText(this, "permession denied ....", Toast.LENGTH_SHORT).show();
                }
                break;

            case STORAGE_CODE_WORD:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    downloadWordButton.performClick();
                } else {
                    Toast.makeText(this, "permession denied ....", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

    public String convertToArabic(int value) {
        String newValue = (((((((((((value + "")
                .replaceAll("1", "١")).replaceAll("2", "٢"))
                .replaceAll("3", "٣")).replaceAll("4", "٤"))
                .replaceAll("5", "٥")).replaceAll("6", "٦"))
                .replaceAll("7", "٧")).replaceAll("8", "٨"))
                .replaceAll("9", "٩")).replaceAll("0", "٠"));
        return newValue;
    }
}