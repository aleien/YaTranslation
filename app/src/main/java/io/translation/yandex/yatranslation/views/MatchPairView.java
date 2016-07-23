package io.translation.yandex.yatranslation.views;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.translation.yandex.yatranslation.R;
import io.translation.yandex.yatranslation.model.Word;

/**
 * Created by aleien on 23.07.16.
 */

public class MatchPairView extends LinearLayout {
    RecyclerView wordsRecycler;
    RecyclerView translationsRecycler;

    List<Word> wordList;

    public MatchPairView(Context context) {
        super(context);
    }

    public MatchPairView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    private void init() {
        setOrientation(HORIZONTAL);
        wordsRecycler = new RecyclerView(getContext());
        translationsRecycler = new RecyclerView(getContext());

        wordsRecycler.setLayoutManager(new LinearLayoutManager(getContext()));
        translationsRecycler.setLayoutManager(new LinearLayoutManager(getContext()));

        wordsRecycler.setAdapter(new WordsAdapter());


    }
}
