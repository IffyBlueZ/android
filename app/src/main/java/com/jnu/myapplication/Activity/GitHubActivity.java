package com.jnu.myapplication.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.jnu.myapplication.R;

public class GitHubActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_github);

        // 找到 WebView 控件
        WebView webView = findViewById(R.id.web_view);

        // 获取 WebView 的设置
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);

        // 加载网页
        webView.loadUrl("https://github.com/IffyBlueZ/android");

        // 设置 WebViewClient 来处理页面加载
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                // 在 WebView 中加载网页，而不是通过系统浏览器打开
                return false;
            }
        });
    }
}