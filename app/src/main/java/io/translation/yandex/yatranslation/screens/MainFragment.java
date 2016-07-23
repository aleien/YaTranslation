package io.translation.yandex.yatranslation.screens;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.translation.yandex.yatranslation.MainActivity;
import io.translation.yandex.yatranslation.R;
import io.translation.yandex.yatranslation.base.BaseFragment;
import io.translation.yandex.yatranslation.base.BaseTaskFragment;
import io.translation.yandex.yatranslation.model.Word;
import io.translation.yandex.yatranslation.views.MatchPairView;


public class MainFragment extends BaseFragment {

    @BindView(R.id.fragment_main_recycler_view)
    RecyclerView mRecyclerView;

    @BindView(R.id.fragment_main_toolbar)
    Toolbar mToolbar;

    private List<String> testTasks;
    private Set<Word> dummyWords = new HashSet<>();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.main_fragment, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mToolbar.setTitle(R.string.fragment_main_toolbar_title);

        testTasks = new ArrayList<>();
        testTasks.add("Базовый");
        testTasks.add("Поиск пары");

        dummyWords.add(Word.getDummyWord());
        dummyWords.add(new Word("ТЕСТ", "TEST", 0));
        dummyWords.add(new Word("ТЕСТ", "TEST", 0));
        dummyWords.add(new Word("ТЕСТ", "TEST", 0));

        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mRecyclerView.setAdapter(new TaskAdapter(testTasks));
    }

    BaseTaskFragment getSetupTaskFragment(int taskId) {
        BaseTaskFragment taskFragment = new BaseTaskFragment();
        taskFragment.setName(testTasks.get(taskId));
        switch (taskId) {
            case 1:
                taskFragment.setTask(new MatchPairView(getContext(),
                        dummyWords));
                break;
        }
        return taskFragment;
    }

    class TaskHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.fragment_main_task_item_button)
        Button mItemButton;

        public TaskHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            mItemButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    BaseTaskFragment taskFragment = getSetupTaskFragment(getAdapterPosition());
                    getFragmentManager()
                            .beginTransaction()
                            // TODO: Вынести в отдельный класс и сделать статиком
                            .replace(R.id.main_fragment_container, taskFragment) // TODO: Быть осторожным
                            .addToBackStack(String.valueOf(getAdapterPosition()))
                            .commit();
                }
            });
        }

        public void bind(String text) {
            mItemButton.setText(text);
        }
    }

    private class TaskAdapter extends RecyclerView.Adapter<TaskHolder> {
        private List<String> mItemsList;

        public TaskAdapter(List<String> itemsList) {
            mItemsList = itemsList;
        }

        @Override
        public TaskHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
            View view = layoutInflater.inflate(R.layout.main_fragment_task_item, parent, false);
            return new TaskHolder(view);
        }

        @Override
        public void onBindViewHolder(TaskHolder holder, int position) {
            holder.bind(mItemsList.get(position));
        }

        @Override
        public int getItemCount() {
            return mItemsList.size();
        }
    }
}