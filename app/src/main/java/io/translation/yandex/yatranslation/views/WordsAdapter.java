package io.translation.yandex.yatranslation.views;

import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import io.translation.yandex.yatranslation.model.Word;

/**
 * Created by aleien on 23.07.16.
 */

public class WordsAdapter extends RecyclerView.Adapter {
    List<Word> words = new ArrayList<>();

    public void setWordsList(List<Word> words) {
        if ()
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return words.size();
    }

    public static class WordViewHolder {


    }
}
