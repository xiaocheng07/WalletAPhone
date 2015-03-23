package com.cssweb.walletaphone.service.aidl;

import android.app.IntentService;
import android.app.Service;
import android.content.Intent;
import android.content.Context;
import android.os.IBinder;
import android.os.RemoteException;

public class MyRemoteService extends Service {

    @Override
    public IBinder onBind(Intent intent) {
        return new MyRemoteServiceImpl();
    }

    @Override
    public void onDestroy()
    {
        super.onDestroy();
    }

    private class MyRemoteServiceImpl extends IMyAidlInterface.Stub
    {

        @Override
        public String getName(String param) throws RemoteException {
            return "hello " + param;
        }
    }
}
