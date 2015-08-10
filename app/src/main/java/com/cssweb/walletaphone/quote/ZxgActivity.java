package com.cssweb.walletaphone.quote;

import android.app.Activity;
import android.app.Service;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.cssweb.walletaphone.R;
import com.cssweb.walletaphone.service.MyService;


public class ZxgActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zxg);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_zxg, menu);
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
        Intent intent = new Intent(this, MyService.class);
        startService(intent);
    }
    public void btnStopServiceOnClick(View view)
    {
        Intent intent = new Intent(this, MyService.class);

        stopService(intent);
    }

    public void btnBindServiceOnClick(View view)
    {
        Intent intent = new Intent(this, MyService.class);
        bindService(intent, conn, Service.BIND_AUTO_CREATE);
    }

    public void btnUnbindServiceOnClick(View view)
    {
        unbindService(conn);
    }

    private ServiceConnection conn = new ServiceConnection() {
                 @Override
                 public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
                         // TODO Auto-generated method stub
                         Log.i("MyService", "连接成功！");
                     }

                         @Override
                 public void onServiceDisconnected(ComponentName componentName) {
                         // TODO Auto-generated method stub
                         Log.i("MyService", "断开连接！");
                     }
             };
}
