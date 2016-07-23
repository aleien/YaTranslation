package io.translation.yandex.yatranslation.screens;


import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.translation.yandex.yatranslation.R;

public class CardsTaskView extends LinearLayout {

    @BindView(R.id.fragment_cards_card_rotating_frame_layout) // Size does matter
            RelativeLayout mRotatingLayout;

    @BindView(R.id.fragment_cards_true_image_view)
    ImageView mTrueImageView;

    @BindView(R.id.fragment_cards_false_image_view)
    ImageView mFalseImageView;

    public CardsTaskView(Context context) {
        super(context);
        init();
    }

    private void init() {
        inflate(getContext(), R.layout.cards_task_view, this);
        ButterKnife.bind(this);

        this.setOnTouchListener(new OnTouchListener() {
            boolean isAnimated;
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

                            if (-delta > 350) {
                                /*
                                isAnimated = true;
                                ObjectAnimator alphaAnimator = ObjectAnimator
                                        .ofFloat(frameLayout, "alpha", smallFrameLayout.getAlpha(), 0.0f)
                                        .setDuration(1000);
                                alphaAnimator.start();
                                */
                            }
                        }
                        break;
                    case MotionEvent.ACTION_UP:
                    case MotionEvent.ACTION_CANCEL:
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
                        } else {

                        }
                        /*
                        backgroundImageView.setAlpha(0.0f);
                        backgroundImageView.setTranslationX(0);
                        frameLayout.setAlpha(1.0f);
                        isAnimated = false;
                        */
                        break;
                }

                return true;
            }
        });
    }
}
