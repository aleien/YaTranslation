package io.translation.yandex.yatranslation.model;


import java.io.Serializable;

public class Word implements Serializable {
    private String mRussian;
    private String mEnglish;
    private int mLevelOfKnowledge;

    public Word(String russian, String english, int levelOfKnowledge) {
        mRussian = russian;
        mEnglish = english;
        mLevelOfKnowledge = levelOfKnowledge;
    }

    public String getRussian() {
        return mRussian;
    }

    public String getEnglish() {
        return mEnglish;
    }

    public boolean isLearned() {
        return (mLevelOfKnowledge >= 10);
    }

    public int getLevelOfKnowledge() {
        return mLevelOfKnowledge;
    }
}
