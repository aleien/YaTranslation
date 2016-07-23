package io.translation.yandex.yatranslation.model;


import java.io.Serializable;

public class Word implements Serializable {
    private String mRussian;
    private String mEnglish;
    private int mCountOfShow;

    public Word(String russian, String english) {
        mRussian = russian;
        mEnglish = english;
    }


    public String getRussian() {
        return mRussian;
    }

    public String getEnglish() {
        return mEnglish;
    }

    public int getCountOfShow() {
        return mCountOfShow;
    }

    public void setCountOfShow(int countOfShow) {
        mCountOfShow = countOfShow;
    }
}
