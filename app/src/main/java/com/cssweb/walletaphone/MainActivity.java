package com.cssweb.walletaphone;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.cssweb.walletaphone.broadcast.TestBroadcastActivity;
import com.cssweb.walletaphone.contentprovider.contract.AccessContract;
import com.cssweb.walletaphone.nfc.ReadNFCTagActivity;
import com.cssweb.walletaphone.nfc.ReadYktActivity;
import com.cssweb.walletaphone.nfc.TestAppletActivity;
import com.cssweb.walletaphone.nfc.WriteNFCTagActivity;
import com.cssweb.walletaphone.quote.ZxgActivity;
import com.cssweb.walletaphone.service.TestServiceActivity;
import com.cssweb.walletaphone.web.WebviewActivity;


public class MainActivity extends ActionBarActivity {
   // private SEService seService;

    private Button btnZxg;
    private EditText editUserName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editUserName = (EditText)findViewById(R.id.editUserName);
        editUserName.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    InputMethodManager imm = (InputMethodManager)v.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0);

                    Log.d("test", "test");

                    return true;
                }
                return false;

            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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

    public void btnServiceOnClick(View view)
    {
        Intent intent = new Intent(this, TestServiceActivity.class);
        startActivity(intent);
    }

    public void btnContentProviderOnClick(View view)
    {
        AccessContract accessContract = new AccessContract();
        accessContract.accessContract(this);

    }

    public void btnBroadcastOnClick(View view)
    {
        Intent intent = new Intent(this, TestBroadcastActivity.class);
        startActivity(intent);
    }

    public void btnWebviewOnClick(View view)
    {
        Intent intent = new Intent(this, WebviewActivity.class);
        startActivity(intent);
    }

    public void btnTestAppletOnClick(View view)
    {
        Intent intent = new Intent(this, TestAppletActivity.class);
        startActivity(intent);
    }

    public void btnReadNFCTagOnClick(View view)
    {
        Intent intent = new Intent(this, ReadNFCTagActivity.class);
        startActivity(intent);
    }

    public void btnWriteNFCTagOnClick(View view)
    {
        Intent intent = new Intent(this, WriteNFCTagActivity.class);
        startActivity(intent);
    }

    public void btnReadYktOnClick(View view)
    {
        Intent intent = new Intent(this, ReadYktActivity.class);
        startActivity(intent);
    }
}
