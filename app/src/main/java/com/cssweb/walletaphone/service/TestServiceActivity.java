package com.cssweb.walletaphone.service;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.cssweb.walletaphone.R;
import com.cssweb.walletaphone.service.aidl.IMyAidlInterface;
import com.cssweb.walletaphone.service.aidl.MyRemoteService;

public class TestServiceActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_service);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_test, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void btnStartServiceOnClick(View view)
    {
        Intent intent = new Intent(this, MyRemoteService.class);
        startService(intent);

        bindService(intent, conn, Context.BIND_AUTO_CREATE);
    }

    public void btnStopServiceOnClick(View view)
    {

    }

    private ServiceConnection conn = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            IMyAidlInterface remoteService = IMyAidlInterface.Stub.asInterface(service);

            try {
                String result = remoteService.getName("chenhf");
                Log.d("TestServiceActivity" , "result is " + result);

            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            Log.i("MyRemoteService", "onServiceDisconnected");
        }
    };
}
