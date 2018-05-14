package com.suikajy.jsdemo;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.Toast;

/**
 * @author zjy
 * @date 2018/2/2
 */

public class MainActivity extends AppCompatActivity {

    //压制警告注解:
    @SuppressLint("AddJavascriptInterface")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btnCallJs = (Button) findViewById(R.id.btn_calljs);

        final WebView mWv = (WebView) findViewById(R.id.wv);

        WebSettings settings = mWv.getSettings();
        settings.setDefaultTextEncodingName("UTF-8");
        settings.setJavaScriptEnabled(true);

        mWv.loadUrl("file:///android_asset/index.html");
//        mWv.postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                mWv.loadUrl("JavaScript:ios()");
//            }
//        },500);

        //添加js的接口.mAndroid是随便写的,代表了当前这个Activity.
        mWv.addJavascriptInterface(this, "mAndroid");

        //实现Android调用js里的函数
        btnCallJs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //执行JavaScript里面的changeDivContent()函数.
                mWv.loadUrl("JavaScript:changeDivContent()");
            }
        });
    }

    //java可以根据字节码进行反射.
    //一旦添加JavascriptInterface注解后,就不能在js里面执行反射的功能了.
    //供js调用的方法,必须添加JavascriptInterface注解.
    @JavascriptInterface
    public void showToast() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(MainActivity.this, "我是不带参数的Toast!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @JavascriptInterface
    public void showToastWithParams(final String msg) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(MainActivity.this, msg, Toast.LENGTH_SHORT).show();
            }
        });
    }

}
