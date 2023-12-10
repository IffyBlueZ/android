package com.jnu.myapplication.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.jnu.myapplication.R;

public class AddRewardActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_reward);

        Button buttonOk = findViewById(R.id.add_reward_button_ok);
        // 为Button添加点击事件监听器
        buttonOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 创建一个新的Intent对象
                Intent intent = new Intent();

                // 标题
                EditText editRewardTitle = findViewById(R.id.add_reward_titleEditText);
                intent.putExtra("reward_title", editRewardTitle.getText().toString());

                // 消耗点数
                EditText editScoreTitle = findViewById(R.id.reward_score_edit_text);
                intent.putExtra("reward_score", editScoreTitle.getText().toString());

                // 标签
                EditText editTagsTitle = findViewById(R.id.reward_tagsEditText);
                intent.putExtra("reward_tags", editTagsTitle.getText().toString());

                // 设置返回结果为RESULT_OK，并将带有数据的Intent传递回前一个Activity
                setResult(Activity.RESULT_OK, intent);
                // 结束当前的BookDetailsActivity，并返回到前一个Activity
                AddRewardActivity.this.finish();
            }
        });
        // 取消按钮实现
        Button buttonCancel = findViewById(R.id.add_reward_button_cancel);
        buttonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 结束当前的BookDetailsActivity，并返回到前一个Activity
                AddRewardActivity.this.finish();
            }
        });
    }
}