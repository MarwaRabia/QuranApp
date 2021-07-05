package com.example.quranapp.ui.addPlan;

public class Plan {
    String keyIdStart, keyIdEnd, wardCount, wardCountType, planTimeCount;

    public Plan(String keyIdStart, String keyIdEnd, String wardCount, String wardCountType, String planTimeCount) {
        this.keyIdStart = keyIdStart;
        this.keyIdEnd = keyIdEnd;
        this.wardCount = wardCount;
        this.wardCountType = wardCountType;
        this.planTimeCount = planTimeCount;
    }

    public String getKeyIdStart() {
        return keyIdStart;
    }

    public void setKeyIdStart(String keyIdStart) {
        this.keyIdStart = keyIdStart;
    }

    public String getKeyIdEnd() {
        return keyIdEnd;
    }

    public void setKeyIdEnd(String keyIdEnd) {
        this.keyIdEnd = keyIdEnd;
    }

    public String getWardCount() {
        return wardCount;
    }

    public void setWardCount(String wardCount) {
        this.wardCount = wardCount;
    }

    public String getWardCountType() {
        return wardCountType;
    }

    public void setWardCountType(String wardCountType) {
        this.wardCountType = wardCountType;
    }

    public String getPlanTimeCount() {
        return planTimeCount;
    }

    public void setPlanTimeCount(String planTimeCount) {
        this.planTimeCount = planTimeCount;
    }
}
