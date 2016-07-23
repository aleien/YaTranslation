package io.translation.yandex.yatranslation.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by aleien on 23.07.16.
 */

public class TranslationResponse {
    @SerializedName("def")
    public List<Definition> definition;
    @SerializedName("head")
    Object head;

    public static class Definition {
        @SerializedName("text")
        public String word;

        @SerializedName("pos")
        public String partOfSpeech;

        @SerializedName("tr")
        public List<Translation> translations;
    }

    public static class Translation {
        @SerializedName("text")
        public String word;

        @SerializedName("pos")
        public String partOfSpeech;

        @SerializedName("syn")
        public List<Synonym> synonyms;

        @SerializedName("mean")
        public List<TranslatedString> meanings;

        @SerializedName("ex")
        public List<Example> examples;
    }

    public static class Synonym {
        @SerializedName("text")
        String word;
        @SerializedName("pos")
        String partOfSpeech;
    }

    private static class Example {
        @SerializedName("text")
        public String word;

        @SerializedName("tr")
        public List<TranslatedString> exampleTranslations;
    }

    private static class TranslatedString {
        @SerializedName("text")
        String text;
    }


}
