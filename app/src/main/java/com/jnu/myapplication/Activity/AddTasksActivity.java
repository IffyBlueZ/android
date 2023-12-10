package com.jnu.myapplication.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.jnu.myapplication.R;

public class AddTasksActivity extends AppCompatActivity {
    private BottomNavigationView btmNavView;
    private String task_Type;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_tasks);

        RadioGroup radioGroupFrequency = findViewById(R.id.add_tasks_details_Group_Frequency);

        radioGroupFrequency.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int checkedId) {
                if(checkedId == R.id.add_tasks_details_Button_Daily) {
                    // 每日
                    task_Type = "daily";
                }
                if(checkedId == R.id.add_tasks_details_Button_Weekly) {
                    // 每周
                   task_Type = "weekly";
                }
                if(checkedId == R.id.add_tasks_details_Button_Regular) {
                    // 普通
                    task_Type = "regular";
                }
            }
        });


        // 在布局中找到ID为button_item_details_ok的Button控件
        Button buttonOk = findViewById(R.id.add_tasks_details_ok);
        // 为Button添加点击事件监听器
        buttonOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 创建一个新的Intent对象
                Intent intent = new Intent();

                // 在当前布局中找到ID为add_tasks_details_edit_Title的EditText控件
                EditText editTasksTitle = findViewById(R.id.add_tasks_details_edit_Title);

                // 将EditText中的文本内容作为"title"键对应的值放入Intent中
                intent.putExtra("title", editTasksTitle.getText().toString());

                // 获得点数
                EditText editScoreTitle = findViewById(R.id.add_tasks_details_edit_Score);
                intent.putExtra("score", editScoreTitle.getText().toString());

                // 标签
                EditText editTagsTitle = findViewById(R.id.add_tasks_details_edit_Tags);
                intent.putExtra("tags", editTagsTitle.getText().toString());

                // 任务类型
                intent.putExtra("task_type", task_Type);

                // 设置返回结果为RESULT_OK，并将带有数据的Intent传递回前一个Activity
                setResult(Activity.RESULT_OK, intent);
                // 结束当前的BookDetailsActivity，并返回到前一个Activity
                AddTasksActivity.this.finish();
            }
        });

        // 在布局中找到ID为button_item_details_Cancel的Button控件
        Button buttonCancel = findViewById(R.id.add_tasks_details_cancel);
        // 为Button添加点击事件监听器
        buttonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 结束当前的BookDetailsActivity，并返回到前一个Activity
                AddTasksActivity.this.finish();
            }
        });
    }
}