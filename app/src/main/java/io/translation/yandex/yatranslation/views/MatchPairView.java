package io.translation.yandex.yatranslation.views;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.translation.yandex.yatranslation.R;
import io.translation.yandex.yatranslation.model.Word;

/**
 * Created by aleien on 23.07.16.
 */

public class MatchPairView extends LinearLayout {
    private Set<Word> words = new HashSet<>();
    RecyclerView wordsRecycler;
    RecyclerView translationsRecycler;

    public MatchPairView(Context context, Set<Word> words) {
        super(context);
        this.words = words;
        init();
    }

    public MatchPairView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    private void init() {
        setOrientation(HORIZONTAL);
        LinearLayout.LayoutParams linearLayoutParams
                = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT);
        linearLayoutParams.weight = 1;
        wordsRecycler = new RecyclerView(getContext());
        wordsRecycler.setLayoutParams(linearLayoutParams);

        translationsRecycler = new RecyclerView(getContext());
        translationsRecycler.setLayoutParams(linearLayoutParams);

        wordsRecycler.setLayoutManager(new LinearLayoutManager(getContext()));
        translationsRecycler.setLayoutManager(new LinearLayoutManager(getContext()));

        WordsAdapter wordsAdapter = new WordsAdapter(getContext(), words, true);
        WordsAdapter translateAdapter = new WordsAdapter(getContext(), words, false);

        wordsRecycler.setAdapter(wordsAdapter);
        translationsRecycler.setAdapter(translateAdapter);
        
        addView(wordsRecycler);
        addView(translationsRecycler);



    }
}
