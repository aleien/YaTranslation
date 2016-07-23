package io.translation.yandex.yatranslation.views;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.translation.yandex.yatranslation.R;
import io.translation.yandex.yatranslation.model.Word;

/**
 * Created by aleien on 23.07.16.
 */

public class WordsAdapter extends RecyclerView.Adapter {
    List<Word> words = new ArrayList<>();
    Context context;
    Boolean translation;

    public WordsAdapter(Context context, Set<Word> words, boolean translation) {
        this.context = context.getApplicationContext();
        this.translation = translation;
        setWordsList(words);
    }


    public void setWordsList(Set<Word> words) {
        long seed = System.nanoTime();

        this.words.clear();
        this.words.addAll(words);
        Collections.shuffle(this.words, new Random(seed));
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(context).inflate(R.layout.main_fragment_task_item, parent, false);
        return new WordViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        WordViewHolder wordHolder = (WordViewHolder) holder;
        if (translation) {
            wordHolder.setWord(this.words.get(position).getEnglish());
        } else {
            wordHolder.setWord(this.words.get(position).getRussian());
        }
    }

    @Override
    public int getItemCount() {
        return words.size();
    }

    public static class WordViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.fragment_main_task_item_button)
        Button button;

        public WordViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void setWord(String word) {
            button.setText(word);
        }


    }
}
