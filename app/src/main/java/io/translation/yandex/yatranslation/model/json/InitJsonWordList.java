package io.translation.yandex.yatranslation.model.json;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class InitJsonWordList {
    @SerializedName("en")
    private List<String> mEnList;
    @SerializedName("ru")
    private List<String> mRuList;

    public List<String> getEnList() {
        return mEnList;
    }

    public List<String> getRuList() {
        return mRuList;
    }
}