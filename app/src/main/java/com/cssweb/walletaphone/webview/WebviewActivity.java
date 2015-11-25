package com.cssweb.walletaphone.webview;

import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.JsResult;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;

import com.cssweb.walletaphone.R;
import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;

import java.io.IOException;
import java.io.InputStream;

public class WebviewActivity extends AppCompatActivity {

    private static final String TAG = "WebviewActivity";

    private WebView webview;
    private Handler mHandler = new Handler();
    private Button button;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.webview_activity);

        webview = (WebView) findViewById(R.id.activity_main_webview);
        button = (Button) findViewById(R.id.btnClick);

        WebSettings webSettings = webview.getSettings();
        webSettings.setJavaScriptEnabled(true);

       // webview.addJavascriptInterface(new JsObject(), "injectedObject");
        webview.addJavascriptInterface(this, "injectedObject");

        webview.setWebViewClient(new WebViewClient() {
            //WebViewClient主要帮助WebView处理各种通知、请求事件的
            @Override
            public void onPageFinished(WebView view, String url) {

            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }

            //拦截请求，加载本地资源文件http://www.tuicool.com/articles/VFzY3y3
            @Override
            public WebResourceResponse shouldInterceptRequest(WebView view, String url) {
                Log.i(TAG, "shouldInterceptRequest url=" + url + ";threadInfo" + Thread.currentThread());

                WebResourceResponse response = null;

                if (url.contains("logo")) {
                    try {
                        InputStream localCopy = getAssets().open("droidyue.png");
                        response = new WebResourceResponse("image/png", "UTF-8", localCopy);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

                return response;
            }
/*
            @Override
            public WebResourceResponse shouldInterceptRequest(WebView view, WebResourceRequest request) {
                WebResourceResponse response = null;
                return response;
            }*/

        });

        webview.setWebChromeClient(new WebChromeClient() {
            //WebChromeClient主要辅助WebView处理Javascript的对话框、网站图标、网站title、加载进度等
            @Override
            public boolean onJsAlert(WebView view, String url, String message, JsResult result) {
                return super.onJsAlert(view, url, message, result);
            }



/*
            public boolean onKeyDown(int keyCode, KeyEvent event) {
                if ((keyCode == KeyEvent.KEYCODE_BACK) && webview.canGoBack()) {
                    webview.goBack();
                    return true;
                }
                return super.onKeyDown(keyCode, event);
            }
            */


        });


        webview.loadUrl("file:///android_asset/index.html");


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 方法1
                webview.loadUrl("javascript:demo('test')");

                // 方法2
                /*)
                webview.evaluateJavascript("", new ValueCallback<String>() {
                    @Override
                    public void onReceiveValue(String s) {

                    }
                });
                */
            }
        });

    }

    @JavascriptInterface
    public void demo(final String s){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                button.setText(s);
            }
        });
    }

}
