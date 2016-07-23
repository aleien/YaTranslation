package io.translation.yandex.yatranslation.screens;


import android.content.Context;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.translation.yandex.yatranslation.R;

public class CardsTaskView extends LinearLayout {

    @BindView(R.id.fragment_cards_card_rotating_frame_layout) // Size does matter
            FrameLayout mRotatingLayout;

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
                            mRotatingLayout.setRotation((delta) / 10);
                            mRotatingLayout.setTranslationX(delta);
                            //backgroundImageView.setAlpha(-delta / 600);
                            if (-delta < 200) {
                                //backgroundImageView.setTranslationX(-delta / 10 - (delta + 200) / 4);
                            } else {
                                //backgroundImageView.setTranslationX(-delta / 10 + (delta + 200) / 4);
                            }

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
