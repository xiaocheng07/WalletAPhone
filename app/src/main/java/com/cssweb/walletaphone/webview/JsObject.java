package com.cssweb.walletaphone.webview;

import android.app.Activity;
import android.webkit.JavascriptInterface;

/**
 * Created by chenh on 2015/11/25.
 */
public class JsObject {
    Activity mActivity;

    public JsObject(Activity activity)
    {
        mActivity = activity;
    }

    @JavascriptInterface
    public void demo(final String s){

    }
}
