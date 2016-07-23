package io.translation.yandex.yatranslation.screens;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import butterknife.BindView;
import io.translation.yandex.yatranslation.R;


public class BaseTaskFragment extends BaseFragment {

    @BindView(R.id.fragment_task_base_container)
    FrameLayout mContainer;
    View mView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.base_task_fragment, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (mContainer != null) {
            mContainer.removeAllViews();
            mContainer.addView(mView);
        }
    }

    public void setTask(View view) {
        mView = view;
        if (mContainer != null) {
            mContainer.removeAllViews();
            mContainer.addView(view);
        }
    }
}
