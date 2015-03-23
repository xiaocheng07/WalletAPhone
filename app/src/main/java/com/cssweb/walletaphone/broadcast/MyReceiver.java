package com.cssweb.walletaphone.broadcast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

public class MyReceiver extends BroadcastReceiver {

    public static final String MSG_ACTION = "com.cssweb.walletaphone.broadcast.custom_msg";
    public static final String BUNDLE = "bundle";

    public MyReceiver() {
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        // 自定义广播
        if (intent.getAction().equals(MSG_ACTION))
        {
            Bundle bundle = intent.getBundleExtra(BUNDLE);
            if (bundle != null)
            {
                String value = bundle.getString("key");
                Log.d("broadcast", "Key's value =" + value);
            }
        }
    }
}
