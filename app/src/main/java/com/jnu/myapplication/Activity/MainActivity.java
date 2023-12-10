package com.jnu.myapplication.Activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.PopupMenu;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentResultListener;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.jnu.myapplication.Fragment.TasksFragment;
import com.jnu.myapplication.Fragment.DailyTasksFragment;
import com.jnu.myapplication.Fragment.HomeFragment;
import com.jnu.myapplication.Fragment.RewardFragment;
import com.jnu.myapplication.R;
import com.jnu.myapplication.data.DataDailyTasks;
import com.jnu.myapplication.data.DataGeneralTasks;
import com.jnu.myapplication.data.DataRewardTasks;
import com.jnu.myapplication.data.DataWeeklyTasks;
import com.jnu.myapplication.data.Tasks;

import java.util.ArrayList;
import java.util.Collections;

public class MainActivity extends AppCompatActivity {
    private int tabposition;
    private int myMenu;
    private BottomNavigationView btmNavView;
    // 添加任务管理器，带有返回结果
    private ActivityResultLauncher<Intent> addTasksLauncher;
    private ActivityResultLauncher<Intent> addRewardLauncher;
    // 排序管理器，带有返回结果
    private ActivityResultLauncher<Intent> SortLauncher;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bottom_navegation);
        // 第一次加载，显示页面
        if (savedInstanceState == null) {
            Fragment tasksFragment = new TasksFragment();
            loadTasksFragment(tasksFragment);
        }
        // 注册添加任务管理器
        addTasksLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if(result.getResultCode() == Activity.RESULT_OK){
                        Intent data = result.getData();
                        String tasks_type = data.getStringExtra("task_type");
                        if("daily".equals(tasks_type)) //每日
                        {
                            ArrayList<Tasks> daily_tasks = new DataDailyTasks().LoadTasks(this);
                            int score = Integer.parseInt(data.getStringExtra("score"));
                            String title = data.getStringExtra("title");
                            daily_tasks.add(new Tasks(title,score));
                            new DataDailyTasks().SaveTasks(this,daily_tasks);
                            loadTasksFragment(new TasksFragment());
                        } else if ("weekly".equals(tasks_type)) { //每周
                            ArrayList<Tasks> weekly_tasks = new DataWeeklyTasks().LoadTasks(this);
                            int score = Integer.parseInt(data.getStringExtra("score"));
                            String title = data.getStringExtra("title");
                            weekly_tasks.add(new Tasks(title,score));
                            new DataWeeklyTasks().SaveTasks(this,weekly_tasks);
                            loadTasksFragment(new TasksFragment(1));
                        } else if ("regular".equals(tasks_type)) { //普通
                            ArrayList<Tasks> general_tasks = new DataGeneralTasks().LoadTasks(this);
                            int score = Integer.parseInt(data.getStringExtra("score"));
                            String title = data.getStringExtra("title");
                            general_tasks.add(new Tasks(title,score));
                            new DataGeneralTasks().SaveTasks(this,general_tasks);
                            loadTasksFragment(new TasksFragment(2));
                        }
                    } else if (result.getResultCode() == Activity.RESULT_CANCELED) {

                    }
                }
        );
        // 处理新建奖励
        addRewardLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if(result.getResultCode() == Activity.RESULT_OK){
                        Intent data = result.getData();
                        ArrayList<Tasks> reward_tasks = new DataRewardTasks().LoadTasks(this);
                        int score = Integer.parseInt(data.getStringExtra("reward_score"));
                        String title = data.getStringExtra("reward_title");
                        String tags = data.getStringExtra("reward_tags");
                        reward_tasks.add(new Tasks(title,-score));
                        new DataRewardTasks().SaveTasks(this,reward_tasks);
                        loadTasksFragment(new RewardFragment());
                    } else if (result.getResultCode() == Activity.RESULT_CANCELED) {

                    }
                }
        );

        // 注册排序管理器
        SortLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if(result.getResultCode() == Activity.RESULT_OK){

                    } else if (result.getResultCode() == Activity.RESULT_CANCELED) {

                    }
                }
        );
        // 获取底部导航栏
        btmNavView = findViewById(R.id.bottom_navigation_menu);
        btmNavView.setOnItemSelectedListener(item -> {
            // 处理底部导航栏项的选择
            if (item.getItemId() == R.id.navigation_tasks) {
                // 点击“任务”，加载TasksFragment
                Fragment tasksFragment = new TasksFragment();//任务
                loadTasksFragment(tasksFragment);
                myMenu = 0;
                return true;
            }
            if (item.getItemId() == R.id.navigation_reward) {
                // 点击“奖励”，加载RewardFragment
                Fragment rewardFragment = new RewardFragment();
                loadTasksFragment(rewardFragment);
                myMenu = 1;
                return true;
            }
            if (item.getItemId() == R.id.navigation_home) {
                // 点击“首页”，加载HomeFragment
                Fragment homeFragment = new HomeFragment();
                loadTasksFragment(homeFragment);
                return true;
            }
            return false;
        });
        // 获取FragmentManager
        FragmentManager fragmentManager = getSupportFragmentManager();

        // 注册FragmentResultListener
        fragmentManager.setFragmentResultListener("tabposition", this,
                new FragmentResultListener() {
                    @Override
                    public void onFragmentResult(@NonNull String requestKey, @NonNull Bundle result) {
                        // 获取从Fragment传来的数据
                        int position = result.getInt("position", tabposition);
                        tabposition = position;
                    }
                });
    }

    // 加载Fragment的方法
    private void loadTasksFragment(Fragment fragment) {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, fragment)
                .commit();
    }
    @Override
    // 创建选项菜单
    public boolean onCreateOptionsMenu(Menu menu) {
        // 通过 getMenuInflater() 方法获取一个 MenuInflater 对象，
        // 用于将 XML 文件解析并填充到 Menu 对象中
        getMenuInflater().inflate(R.menu.add_button, menu);
        // 返回父类的 onCreateOptionsMenu 方法的结果，
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    //点击添加按钮
    public boolean onOptionsItemSelected(MenuItem item){
        if(item.getItemId() == R.id.tasks_msg && myMenu == 0)
        {
            // 创建一个 PopupMenu
            PopupMenu popupMenu = new PopupMenu(this, findViewById(R.id.tasks_msg));
            // 在菜单中添加选项
            popupMenu.getMenu().add("新建任务");
            popupMenu.getMenu().add("排序");
            popupMenu.getMenu().add("全部删除");
            // 设置菜单项点击事件
            popupMenu.setOnMenuItemClickListener(menuItem -> {
                // 处理菜单项点击事件
                switch (menuItem.getTitle().toString()) {
                    case "新建任务":
                        // 处理选项一点击事件
                        // 创建一个新的意图Intent以启动AddTasksActivity
                        Intent add_task_intent = new Intent(this, AddTasksActivity.class);
                        // 使用addTasksLauncher启动指定的Intent
                        addTasksLauncher.launch(add_task_intent);
                        return true;
                    case "排序":
                        // 处理选项三点击事件
                        // 排序每日任务
                        if(tabposition == 0)
                        {
                            ArrayList<Tasks> dailytask = new DataDailyTasks().LoadTasks(this);
                            Collections.sort(dailytask);
                            new DataDailyTasks().SaveTasks(this,dailytask);
                            loadTasksFragment(new TasksFragment(0));
                        }
                        // 排序每周任务
                        if(tabposition == 1)
                        {
                            // 排序每周任务
                            ArrayList<Tasks> weeklytask = new DataWeeklyTasks().LoadTasks(this);
                            Collections.sort(weeklytask);
                            new DataWeeklyTasks().SaveTasks(this,weeklytask);
                            loadTasksFragment(new TasksFragment(1));
                        }
                        // 排序普通任务
                        if(tabposition == 2)
                        {
                            ArrayList<Tasks> generaltask = new DataGeneralTasks().LoadTasks(this);
                            Collections.sort(generaltask);
                            new DataGeneralTasks().SaveTasks(this,generaltask);
                            loadTasksFragment(new TasksFragment(2));
                        }
                        Toast.makeText(this, "已排序", Toast.LENGTH_SHORT).show();
                        break;
                    case "全部删除":
                        // 处理选项三点击事件
                        // 删除每日任务
                        if(tabposition == 0)
                        {
                            ArrayList<Tasks> daily_tasks = new ArrayList<>();
                            new DataDailyTasks().SaveTasks(this, daily_tasks);
                            loadTasksFragment(new TasksFragment(0));
                        }
                        // 删除每周任务
                        if(tabposition == 1)
                        {
                            // 排序每周任务
                            ArrayList<Tasks> weekly_tasks = new ArrayList<>();
                            new DataWeeklyTasks().SaveTasks(this, weekly_tasks);
                            loadTasksFragment(new TasksFragment(1));
                        }
                        // 删除普通任务
                        if(tabposition == 2)
                        {
                            ArrayList<Tasks> general_tasks = new ArrayList<>();
                            new DataGeneralTasks().SaveTasks(this, general_tasks);
                            loadTasksFragment(new TasksFragment(2));
                        }
                        Toast.makeText(this, "已全部删除", Toast.LENGTH_SHORT).show();
                        return true;
                }
                return false;
            });
            // 显示 PopupMenu
            popupMenu.show();
            return true;
        }
        if(item.getItemId() == R.id.tasks_msg && myMenu == 1)
        {
            // 创建一个 PopupMenu
            PopupMenu popupMenu = new PopupMenu(this, findViewById(R.id.tasks_msg));
            // 在菜单中添加选项
            popupMenu.getMenu().add("新建奖励");
            popupMenu.getMenu().add("排序");
            popupMenu.getMenu().add("全部删除");
            // 设置菜单项点击事件
            popupMenu.setOnMenuItemClickListener(menuItem -> {
                // 处理菜单项点击事件
                switch (menuItem.getTitle().toString()) {
                    case "新建奖励":
                        // 处理选项一点击事件
                        Intent add_reward_intent = new Intent(this, AddRewardActivity.class);
                        // 使用 addTasksLauncher 启动指定的 Intent
                        addRewardLauncher.launch(add_reward_intent);
                        return true;
                    case "排序":
                        ArrayList<Tasks> reward_tasks_sort= new DataRewardTasks().LoadTasks(this);
                        Collections.sort(reward_tasks_sort);
                        new DataRewardTasks().SaveTasks(this,reward_tasks_sort);
                        Toast.makeText(this, "已排序", Toast.LENGTH_SHORT).show();
                        break;
                    case "全部删除":
                        ArrayList<Tasks> reward_tasks = new ArrayList<>();
                        new DataRewardTasks().SaveTasks(this,reward_tasks);
                        loadTasksFragment(new RewardFragment());
                        Toast.makeText(this, "已全部删除", Toast.LENGTH_SHORT).show();
                        return true;
                }
                return false;
            });
            // 显示 PopupMenu
            popupMenu.show();
            return true;
        }
//        else
//        {
//            // 创建一个 PopupMenu
//            PopupMenu popupMenu = new PopupMenu(this, findViewById(R.id.tasks_msg));
//            // 在菜单中添加选项
//            popupMenu.getMenu().add("新建奖励");
//            popupMenu.getMenu().add("排序");
//            popupMenu.getMenu().add("全部删除");
//            // 设置菜单项点击事件
//            popupMenu.setOnMenuItemClickListener(menuItem -> {
//                // 处理菜单项点击事件
//                switch (menuItem.getTitle().toString()) {
//                    case "新建奖励":
//                        Toast.makeText(this, "该界面不支持此操作", Toast.LENGTH_SHORT).show();
//                        return true;
//                    case "排序":
//                        Toast.makeText(this, "该界面不支持此操作", Toast.LENGTH_SHORT).show();
//                        break;
//                    case "全部删除":
//                        Toast.makeText(this, "该界面不支持此操作", Toast.LENGTH_SHORT).show();
//                        return true;
//                }
//                return false;
//            });
//        }
        return super.onOptionsItemSelected(item);
    }
}
