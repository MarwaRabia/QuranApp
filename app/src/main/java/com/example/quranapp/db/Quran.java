package com.example.quranapp.db;

public class Quran {

    private int id, suraNo, ayaNo, verseId, jozz, page, line;
    private String textEmlaey, suraNameAr, suraNameEn;

    public Quran() {
    }

    public Quran(int id, int suraNo, int ayaNo, int verseId, int jozz,
                 int page, int line, String textEmlaey, String suraNameAr, String suraNameEn) {
        this.id = id;
        this.suraNo = suraNo;
        this.ayaNo = ayaNo;
        this.verseId = verseId;
        this.jozz = jozz;
        this.page = page;
        this.line = line;
        this.textEmlaey = textEmlaey;
        this.suraNameAr = suraNameAr;
        this.suraNameEn = suraNameEn;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getSuraNo() {
        return suraNo;
    }

    public void setSuraNo(int suraNo) {
        this.suraNo = suraNo;
    }

    public int getAyaNo() {
        return ayaNo;
    }

    public void setAyaNo(int ayaNo) {
        this.ayaNo = ayaNo;
    }

    public int getVerseId() {
        return verseId;
    }

    public void setVerseId(int verseId) {
        this.verseId = verseId;
    }

    public int getJozz() {
        return jozz;
    }

    public void setJozz(int jozz) {
        this.jozz = jozz;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getLine() {
        return line;
    }

    public void setLine(int line) {
        this.line = line;
    }

    public String getTextEmlaey() {
        return textEmlaey;
    }

    public void setTextEmlaey(String textEmlaey) {
        this.textEmlaey = textEmlaey;
    }

    public String getSuraNameAr() {
        return suraNameAr;
    }

    public void setSuraNameAr(String suraNameAr) {
        this.suraNameAr = suraNameAr;
    }

    public String getSuraNameEn() {
        return suraNameEn;
    }

    public void setSuraNameEn(String suraNameEn) {
        this.suraNameEn = suraNameEn;
    }
}
