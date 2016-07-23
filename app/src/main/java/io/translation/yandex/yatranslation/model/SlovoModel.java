package io.translation.yandex.yatranslation.model;


import android.content.Context;
import android.util.Log;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Set;

public class SlovoModel {
    private static final String NAME_OF_WORDS_FILE = "wordsList";
    private static Context sContext;

    private Set<Word> mWordSet = null;

    public static void init(Context context) {
        sContext = context.getApplicationContext();
    }

    public Set<Word> getWords() {
        if (mWordSet == null) {
            loadWords(NAME_OF_WORDS_FILE, sContext);
        }
        return mWordSet;
    }

    public void addWord(Word word) {
        mWordSet.add(word);
        try {
            saveWords(mWordSet, NAME_OF_WORDS_FILE, sContext);
        } catch (IOException e) {
            Log.e(getClass().getName(), "IOException in addWord!", e);
        }
    }

    private void saveWords(Set<Word> wordSet, String nameOfFile, Context context) throws IOException {
        FileOutputStream fos = null;
        ObjectOutputStream oos = null;
        try {
            fos = context.openFileOutput(nameOfFile, Context.MODE_PRIVATE);
            oos = new ObjectOutputStream(fos);
            oos.writeObject(wordSet);
        } finally {
            if (fos != null) {
                fos.close();
            }
            if (oos != null) {
                oos.close();
            }
        }
    }

    private Set<Word> loadWords(String nameOfFile, Context context) {
        FileInputStream fis;
        ObjectInputStream ois;
        try {
            fis = context.openFileInput(nameOfFile);
            ois = new ObjectInputStream(fis);
            Set<Word> theSet = (Set<Word>) ois.readObject();
            ois.close();
            return theSet;
        } catch (IOException ioe) {
            Log.e("loadAll", nameOfFile + "" + ioe.getMessage());
            File file = new File(context.getFilesDir().getAbsolutePath() + "/" + nameOfFile);
            file.delete();
            return null;
        } catch (ClassNotFoundException e) { // TODO: Понять и перепилить
            return null;
        }
    }
}
