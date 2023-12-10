package com.jnu.myapplication.Fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import android.view.LayoutInflater;

import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.jnu.myapplication.R;

import com.jnu.myapplication.data.DataScore;

public class TasksFragment extends Fragment {
    private static int score;
    public int defaultTab = 0;
    private Fragment daily_tasks =  new DailyTasksFragment();
    private Fragment weekly_tasks = new WeeklyTasksFragment();
    private Fragment general_tasks = new GeneralTasksFragment();
    private String []tabHeaderStrings = {"每日任务","每周任务","普通任务"};
    public TasksFragment() {
        // Required empty public constructor
    }
    public TasksFragment(int i) {
        // Required empty public constructor
        defaultTab = i;
    }
    public static TasksFragment newInstance(int defaultTab) {
        TasksFragment fragment = new TasksFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_tasks_bottom, container, false);
        ViewPager2 viewPager = root.findViewById(R.id.button_view_pager);
        TabLayout tabLayout = root.findViewById(R.id.tab_layout);
        TextView textView = root.findViewById(R.id.textView);

        score = new DataScore().loadScore(this.getContext());
        textView.setText(String.valueOf(score));

        getParentFragmentManager().setFragmentResultListener("updateScore", this, (requestKey, result) -> {
            score = new DataScore().loadScore(this.getContext());
            int updatedScore = score;
            if (result.containsKey("dailyScore")) {
                updatedScore += result.getInt("dailyScore");
            }
            if (result.containsKey("weeklyScore")) {
                updatedScore += result.getInt("weeklyScore");
            }
            if (result.containsKey("generalScore")) {
                updatedScore += result.getInt("generalScore");
            }
            // 更新 TextView 的显示
            textView.setText(String.valueOf(updatedScore));
            new DataScore().saveScore(this.getContext(),updatedScore);
            if (getActivity() != null) {
                int all_score = new DataScore().loadScore(this.getContext());
                Bundle bundle = new Bundle();
                bundle.putInt("allScore", all_score);
                getParentFragmentManager().setFragmentResult("AllScore", bundle);
            }
        });
        // 找到按钮
        Button buttonZero = root.findViewById(R.id.button_delete_score);
        // 设置按钮点击事件监听器
        buttonZero.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 在此处执行按钮点击后的操作
                Toast.makeText(getContext(), "已清除", Toast.LENGTH_SHORT).show();
                textView.setText(String.valueOf(0));
                new DataScore().saveScore(getContext(),0);
            }
        });

        // 创建适配器
        FragmentAdapter fragmentAdapter = new FragmentAdapter(getActivity().getSupportFragmentManager(), getLifecycle());
        viewPager.setAdapter(fragmentAdapter);
        // 将TabLayout和ViewPager2进行关联
        new TabLayoutMediator(tabLayout, viewPager,
                (tab, position) -> tab.setText(tabHeaderStrings[position])
        ).attach();
        // 获取默认标签页
        viewPager.setCurrentItem(defaultTab);
        viewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                TabLayout.Tab tab = tabLayout.getTabAt(position);
                if (tab != null) {
                    if (getActivity() != null) {
                        Bundle bundle = new Bundle();
                        bundle.putInt("position", position);
                        getParentFragmentManager().setFragmentResult("tabposition", bundle);
                    }
                    // 在这里你可以对当前标签页进行操作
                }
            }
        });
        return root;
    }
    public class FragmentAdapter extends FragmentStateAdapter {
        private static final int NUM_TABS = 3;
        public FragmentAdapter(@NonNull FragmentManager fragmentManager, @NonNull Lifecycle lifecycle) {
            super(fragmentManager, lifecycle);
        }

        @NonNull
        @Override
        public Fragment createFragment(int position) {
            // 根据位置返回对应的Fragment实例
            switch (position) {
                case 0:
                    return new DailyTasksFragment();
                case 1:
                    return new WeeklyTasksFragment();
                case 2:
                    return new GeneralTasksFragment();
                default:
                    return null;
            }
        }

        @Override
        public int getItemCount() {
            return NUM_TABS;
        }
    }
    private void loadTasksFragment(Fragment fragment) {
        getChildFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, fragment)
                .commit();
    }
}