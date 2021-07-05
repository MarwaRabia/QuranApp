package com.example.quranapp.db;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.quranapp.R;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class DbHandler extends SQLiteOpenHelper {
    private static final int DB_VERSION = 1;
    private static final String DB_NAME = "quran.db";
    private static final String TABLE_NAME = "quran";
    private static final String KEY_ID = "id";
    private static final String JOZZ = "jozz";
    private static final String SURA = "sura_no";
    private static final String AYA = "aya_no";
    private static final String SURA_NAME_EN = "sura_name_en";
    private static final String SURA_NAME_AR = "sura_name_ar";
    private static final String VERSE_ID = "verseID";
    private static final String PAGE = "page";
    private static final String LINE = "line";
    private static final String TEXT = "aya_text";
    private static final String TEXT_EMLAEY = "aya_text_emlaey";
    private Context context;

    private int id, suraNo, ayaNo, verseId, jozz, page, line;
    private String textEmlaey, suraNameAr, suraNameEn;

    public DbHandler(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CRATE_TABLE = "CREATE TABLE " + TABLE_NAME + "("
                + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + JOZZ + " INTEGER," + SURA + " INTEGER,"
                + SURA_NAME_EN + " TEXT," + SURA_NAME_AR + " TEXT,"
                + PAGE + " INTEGER," + LINE + " INTEGER,"
                + TEXT_EMLAEY + " TEXT," + VERSE_ID + " INTEGER,"
                + AYA + " INTEGER," + TEXT + " TEXT" + ")";
        db.execSQL(CRATE_TABLE);

        // Inserting
        Log.i("Insert: ", "Inserting ..");
        try {
            long insertCount = insertFromFile(R.raw.quran, db);
            Log.i("Insert: ", "Rows loaded from file= " + insertCount);
        } catch (IOException e) {
            Log.i("Insert: ", e.toString());
            e.printStackTrace();
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);

        // Create tables again
        onCreate(db);
    }

    private long insertFromFile(int resourceId, SQLiteDatabase db) throws IOException {
        // Reseting Counter
        long result = 0;

        // Open the resource
        InputStream insertsStream = context.getResources().openRawResource(resourceId);
        BufferedReader reader = new BufferedReader(new InputStreamReader(insertsStream));


        ArrayList<String> databaseList = new ArrayList<>();
        String line;
        while ((line = reader.readLine()) != null) {
            databaseList.add(line);
        }


        for (int i = 0; i < databaseList.size(); i++) {
            db.execSQL("INSERT INTO " + TABLE_NAME + databaseList.get(i));
            result = result + 1;
        }

        reader.close();

        // returning number of inserted rows
        return result;
    }

    public ArrayList<Integer> getNumOfAyatInsideSura(String suraId) {
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<Integer> arrayList = new ArrayList<>();

        Cursor cursor = db.query(TABLE_NAME,
                null,
                SURA + "=?",
                new String[]{suraId},
                null,
                null,
                null);

        while (cursor.moveToNext()) {
            int aya = cursor.getInt(cursor.getColumnIndex(AYA));
            arrayList.add(aya);
        }
        cursor.close();
        return arrayList;
    }

    public Quran getQuranRow(String keyId) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery("select * from " + TABLE_NAME + " where " + KEY_ID + "='" + keyId + "'", null);

        while (cursor.moveToNext()) {
            textEmlaey = cursor.getString(cursor.getColumnIndex(TEXT_EMLAEY));
            suraNameEn = cursor.getString(cursor.getColumnIndex(SURA_NAME_EN));
            suraNameAr = cursor.getString(cursor.getColumnIndex(SURA_NAME_AR));

            suraNo = cursor.getInt(cursor.getColumnIndex(SURA));
            ayaNo = cursor.getInt(cursor.getColumnIndex(AYA));
            verseId = cursor.getInt(cursor.getColumnIndex(VERSE_ID));
            jozz = cursor.getInt(cursor.getColumnIndex(JOZZ));
            line = cursor.getInt(cursor.getColumnIndex(LINE));
            page = cursor.getInt(cursor.getColumnIndex(PAGE));
        }
        Quran quran = new Quran(Integer.parseInt(keyId), suraNo, ayaNo, verseId, jozz,
                page, line, textEmlaey, suraNameAr, suraNameEn);
        cursor.close();
        return quran;
    }

    public List<Quran> getAllAyatInsideSura(String suraId) {
        SQLiteDatabase db = this.getReadableDatabase();
        List<Quran> rowsList = new ArrayList<>();

        Cursor cursor = db.rawQuery("select * from " + TABLE_NAME + " where " + SURA + "='" + suraId + "'", null);

        while (cursor.moveToNext()) {
            textEmlaey = cursor.getString(cursor.getColumnIndex(TEXT_EMLAEY));
            suraNameEn = cursor.getString(cursor.getColumnIndex(SURA_NAME_EN));
            suraNameAr = cursor.getString(cursor.getColumnIndex(SURA_NAME_AR));

            suraNo = cursor.getInt(cursor.getColumnIndex(SURA));
            id = cursor.getInt(cursor.getColumnIndex(KEY_ID));
            ayaNo = cursor.getInt(cursor.getColumnIndex(AYA));
            verseId = cursor.getInt(cursor.getColumnIndex(VERSE_ID));
            jozz = cursor.getInt(cursor.getColumnIndex(JOZZ));
            line = cursor.getInt(cursor.getColumnIndex(LINE));
            page = cursor.getInt(cursor.getColumnIndex(PAGE));

            Quran quran = new Quran();

            quran.setPage(page);
            quran.setVerseId(verseId);
            quran.setTextEmlaey(textEmlaey);
            quran.setJozz(jozz);
            quran.setSuraNo(suraNo);
            quran.setAyaNo(ayaNo);
            quran.setSuraNameAr(suraNameAr);
            quran.setSuraNameEn(suraNameEn);
            quran.setLine(line);
            quran.setId(id);

            rowsList.add(quran);
        }

        cursor.close();
        return rowsList;
    }

    public List<Quran> getAllAyatInsidePage(String pageString) {
        SQLiteDatabase db = this.getReadableDatabase();
        List<Quran> rowsList = new ArrayList<>();

        Cursor cursor = db.rawQuery("select * from " + TABLE_NAME + " where " + PAGE + "='" + pageString + "'", null);

        while (cursor.moveToNext()) {
            textEmlaey = cursor.getString(cursor.getColumnIndex(TEXT_EMLAEY));
            suraNameEn = cursor.getString(cursor.getColumnIndex(SURA_NAME_EN));
            suraNameAr = cursor.getString(cursor.getColumnIndex(SURA_NAME_AR));

            suraNo = cursor.getInt(cursor.getColumnIndex(SURA));
            id = cursor.getInt(cursor.getColumnIndex(KEY_ID));
            ayaNo = cursor.getInt(cursor.getColumnIndex(AYA));
            verseId = cursor.getInt(cursor.getColumnIndex(VERSE_ID));
            jozz = cursor.getInt(cursor.getColumnIndex(JOZZ));
            line = cursor.getInt(cursor.getColumnIndex(LINE));
            page = cursor.getInt(cursor.getColumnIndex(PAGE));

            Quran quran = new Quran();

            quran.setPage(page);
            quran.setVerseId(verseId);
            quran.setTextEmlaey(textEmlaey);
            quran.setJozz(jozz);
            quran.setSuraNo(suraNo);
            quran.setAyaNo(ayaNo);
            quran.setSuraNameAr(suraNameAr);
            quran.setSuraNameEn(suraNameEn);
            quran.setLine(line);
            quran.setId(id);

            rowsList.add(quran);
        }

        cursor.close();
        return rowsList;
    }

    public Integer getKeyId(String suraId, String ayaId) {
        SQLiteDatabase db = this.getReadableDatabase();
        int keyId = 0;

        Cursor cursor = db.query(TABLE_NAME,
                new String[]{},
                SURA + " IN (" + suraId + ")" + " AND " + AYA + " IN (" + ayaId + ")"
                , null, null, null, null);

        while (cursor.moveToNext()) {
            keyId = cursor.getInt(cursor.getColumnIndex(KEY_ID));
        }
        cursor.close();
        return keyId;
    }

    public Integer getKeyId(String ayaText) {
        SQLiteDatabase db = this.getReadableDatabase();
        int keyId = 0;

        Cursor cursor = db.rawQuery("select * from " + TABLE_NAME + " where " + TEXT_EMLAEY + "='" + ayaText + "'", null);

        while (cursor.moveToNext()) {
            keyId = cursor.getInt(cursor.getColumnIndex(KEY_ID));
        }
        cursor.close();
        return keyId;
    }

    public Integer getSuraId(String ayaText) {
        SQLiteDatabase db = this.getReadableDatabase();
        int suraId = 0;

        Cursor cursor = db.rawQuery("select * from " + TABLE_NAME + " where " + TEXT_EMLAEY + "='" + ayaText + "'", null);

        while (cursor.moveToNext()) {
            suraId = cursor.getInt(cursor.getColumnIndex(SURA));
        }
        cursor.close();
        return suraId;
    }

}
