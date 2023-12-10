package com.jnu.myapplication.Fragment;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.jnu.myapplication.Activity.GitHubActivity;
import com.jnu.myapplication.R;

public class HomeFragment extends Fragment {



    public HomeFragment() {
        // Required empty public constructor
    }

    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
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
        View rootView = inflater.inflate(R.layout.fragment_home, container, false);

        // 找到按钮
        Button button_sign_in = rootView.findViewById(R.id.sign_in);
        // 设置按钮点击事件监听器
        button_sign_in.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 在此处执行按钮点击后的操作
                AlertDialog.Builder builder1 = new AlertDialog.Builder(getActivity());
                builder1.setMessage("作者没钱租服务器");
                builder1.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // 处理确定按钮点击事件的逻辑
                    }
                });
                builder1.create().show();
            }
        });

        // 找到按钮
        Button button_sign_up = rootView.findViewById(R.id.sign_up);
        // 设置按钮点击事件监听器
        button_sign_up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 在此处执行按钮点击后的操作
                AlertDialog.Builder builder1 = new AlertDialog.Builder(getActivity());
                builder1.setMessage("作者真的没钱租服务器啦");
                builder1.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // 处理确定按钮点击事件的逻辑
                    }
                });
                builder1.create().show();
            }
        });

        // 找到布局中的视图组件(github)
        TextView helpTextView = rootView.findViewById(R.id.helpTextView);
        // 设置github点击事件监听器
        helpTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), GitHubActivity.class);
                startActivity(intent);
            }
        });

        // 找到布局中的视图组件(支持作者)
        TextView photoTextView = rootView.findViewById(R.id.buyTeaTextView);
        // 设置支持作者点击事件监听器
        photoTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 创建AlertDialog.Builder对象
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

                // 设置对话框标题和内容
                builder.setTitle("谢谢支持~");
                builder.setMessage("o(*￣▽￣*)ブ❤");

                // 添加确定按钮
                builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // 这里可以添加确定按钮点击后的操作
                        dialog.dismiss(); // 关闭对话框
                    }
                });

                // 创建并显示AlertDialog
                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });

        // 找到布局中的视图组件(更新)
        TextView update = rootView.findViewById(R.id.checkUpdateTextView);
        // 设置更新点击事件监听器
        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 创建AlertDialog.Builder对象
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

                // 设置对话框标题和内容
                builder.setTitle("检查更新");
                builder.setMessage("作者说已经不会更新了T^T");

                // 添加确定按钮
                builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // 这里可以添加确定按钮点击后的操作
                        dialog.dismiss(); // 关闭对话框
                    }
                });

                // 创建并显示AlertDialog
                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });

        return rootView;
    }
}