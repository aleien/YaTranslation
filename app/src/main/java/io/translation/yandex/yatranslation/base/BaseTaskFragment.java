package io.translation.yandex.yatranslation.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import butterknife.BindView;
import io.translation.yandex.yatranslation.R;


public class BaseTaskFragment extends BaseFragment {

    @BindView(R.id.fragment_task_name)
    TextView taskName;
    @BindView(R.id.fragment_task_base_container)
    FrameLayout mContainer;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.base_task_fragment, container, false);
    }

    public void setTaskName(String taskName) {
        this.taskName.setText(taskName);
    }

    public void setTask(View view) {
        if (mContainer != null) {
            mContainer.removeAllViews();
            mContainer.addView(view);
        }
    }
}
