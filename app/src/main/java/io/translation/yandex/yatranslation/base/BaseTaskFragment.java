package io.translation.yandex.yatranslation.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.OnClick;
import io.translation.yandex.yatranslation.R;

// Получает на вход вьюху, что запихивает в свой контейнер
public class BaseTaskFragment extends BaseFragment {

    @BindView(R.id.fragment_task_name)
    TextView taskName;
    @BindView(R.id.fragment_task_base_container)
    FrameLayout mContainer;

    String mTaskName;
    View mTaskView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.base_task_fragment, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        taskName.setText(mTaskName);
        mContainer.addView(mTaskView);

    }

    @OnClick(R.id.fragment_task_toolbar_close_image_view)
    public void onCloseClick() {
        getFragmentManager().popBackStack();
    }

    // Не getInstance(...), но пойдёт
    public void setTask(String taskName, View taskView) {
        mTaskName = taskName;
        mTaskView = taskView;
    }
}
