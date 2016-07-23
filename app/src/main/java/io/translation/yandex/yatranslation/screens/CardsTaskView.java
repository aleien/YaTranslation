package io.translation.yandex.yatranslation.screens;


import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Set;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.translation.yandex.yatranslation.R;
import io.translation.yandex.yatranslation.model.SlovoModel;
import io.translation.yandex.yatranslation.model.Word;

public class CardsTaskView extends LinearLayout {

    @BindView(R.id.fragment_cards_card_rotating_frame_layout) // Size does matter
            RelativeLayout mRotatingLayout;
    @BindView(R.id.fragment_cards_true_image_view)
    ImageView mTrueImageView;
    @BindView(R.id.fragment_cards_false_image_view)
    ImageView mFalseImageView;
    @BindView(R.id.fragment_cards_card_word_text_view)
    TextView mWordTextView;
    @BindView(R.id.fragment_cards_card_help_text_view)
    TextView mHelpTextView;
    @BindView(R.id.fragment_cards_card_progress_text_view)
    TextView mProgressTextView;
    private SlovoModel mSlovoModel;
    private int mPosition;
    private ArrayList<Word> mWordArrayList;
    private Word mWord;

    public CardsTaskView(Context context) {
        super(context);
        mSlovoModel = new SlovoModel();
        init();
    }

    private void init() {
        inflate(getContext(), R.layout.cards_task_view, this);
        ButterKnife.bind(this);

        // Наша постоянная рубрика "Я у мамы говнокодер"
        Set<Word> wordSet = mSlovoModel.getWords();
        mWordArrayList = new ArrayList<>();
        mPosition = 0;

        for (Word word : wordSet) {
            mWordArrayList.add(word);
            Collections.shuffle(mWordArrayList);
        }

        // Вынести в отдельный метод
        mWord = mWordArrayList.get(mPosition++);
        mWordTextView.setText(mWord.getEnglish());
        mProgressTextView.setText(String.valueOf(mWord.getLevelOfKnowledge()));
        mHelpTextView.setText(mWord.getRussian());

        mWordTextView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mHelpTextView.getVisibility() == INVISIBLE) {
                    mHelpTextView.setVisibility(VISIBLE);
                    ObjectAnimator
                            .ofFloat(mHelpTextView, "alpha", 0.0f, 1.0f)
                            .setDuration(500)
                            .start();
                } else {
                    ObjectAnimator
                            .ofFloat(mHelpTextView, "alpha", 1.0f, 0.0f)
                            .setDuration(500)
                            .start();
                    mHelpTextView.setVisibility(INVISIBLE);
                }
            }
        });

        this.setOnTouchListener(new OnTouchListener() {
            boolean isAnimated, isNextWord;
            float x, x0 = 0;

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        x0 = event.getX();
                        isAnimated = false;
                        break;
                    case MotionEvent.ACTION_MOVE:
                        if (!isAnimated) {
                            x = event.getX();
                            float delta = x - x0;
                            mRotatingLayout.setRotation((delta) / 20);
                            mRotatingLayout.setTranslationX(delta);
                            mTrueImageView.setAlpha(delta / 80);
                            mFalseImageView.setAlpha(-delta / 80);

                            if (-delta > 200) { // FALSE (свайп влево)
                                if (!isNextWord) {
                                    isNextWord = true;

                                    mSlovoModel.knowledgeDecrease(mWord);
                                    mWord = mWordArrayList.get(mPosition++);
                                    mHelpTextView.setVisibility(INVISIBLE);
                                    mWordTextView.setText(mWord.getEnglish());
                                    mProgressTextView.setText(String.valueOf(mWord.getLevelOfKnowledge()));
                                    mHelpTextView.setText(mWord.getRussian());
                                }
                            }

                            if (delta > 200) {
                                if (!isNextWord) { // TODO: Опасно!
                                    isNextWord = true;

                                    mHelpTextView.setVisibility(INVISIBLE);
                                    mSlovoModel.knowledgeIncrease(mWord);
                                    mWord = mWordArrayList.get(mPosition++);
                                    mHelpTextView.setVisibility(INVISIBLE);
                                    mWordTextView.setText(mWord.getEnglish());
                                    mProgressTextView.setText(String.valueOf(mWord.getLevelOfKnowledge()));
                                    mHelpTextView.setText(mWord.getRussian());
                                }
                            }
                        }
                        break;
                    case MotionEvent.ACTION_UP:
                    case MotionEvent.ACTION_CANCEL:
                        isNextWord = false;
                        if (!isAnimated) {
                            ObjectAnimator rotatingAnimator = ObjectAnimator
                                    .ofFloat(mRotatingLayout, "rotation", mRotatingLayout.getRotation(), 0.0f)
                                    .setDuration(500);

                            ObjectAnimator translationAnimator = ObjectAnimator
                                    .ofFloat(mRotatingLayout, "translationX", mRotatingLayout.getTranslationX(), 0.0f)
                                    .setDuration(500);

                            ObjectAnimator trueAlphaAnimator = ObjectAnimator
                                    .ofFloat(mTrueImageView, "alpha", mTrueImageView.getAlpha(), 0.0f)
                                    .setDuration(500);

                            ObjectAnimator falseAlphaAnimator = ObjectAnimator
                                    .ofFloat(mFalseImageView, "alpha", mFalseImageView.getAlpha(), 0.0f)
                                    .setDuration(500);

                            AnimatorSet animatorSet = new AnimatorSet();
                            animatorSet.play(translationAnimator)
                                    .with(rotatingAnimator)
                                    .with(trueAlphaAnimator)
                                    .with(falseAlphaAnimator);
                            animatorSet.start();
                        }
                        break;
                }

                return true;
            }
        });
    }
}
