package com.cssweb.walletaphone;



import android.app.PendingIntent;
import android.content.Intent;
import android.nfc.NdefMessage;
import android.nfc.NfcAdapter;
import android.os.Parcelable;

import android.os.Bundle;

import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.cssweb.walletaphone.broadcast.TestBroadcastActivity;
import com.cssweb.walletaphone.contentprovider.contract.AccessContract;
import com.cssweb.walletaphone.fortune.FortuneFragment;
import com.cssweb.walletaphone.merchant.MerchantFragment;
import com.cssweb.walletaphone.nfc.ReadNFCTagActivity;
import com.cssweb.walletaphone.nfc.ReadYktActivity;
import com.cssweb.walletaphone.nfc.TestAppletActivity;
import com.cssweb.walletaphone.nfc.WriteNFCTagActivity;
import com.cssweb.walletaphone.service.TestServiceActivity;
import com.cssweb.walletaphone.wallet.WalletFragment;
import com.cssweb.walletaphone.webview.WebviewActivity;
import com.squareup.okhttp.OkHttpClient;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {
   private static final String LOG_TAG = "main";

   // private final OkHttpClient client = new OkHttpClient();

  //  private EditText editUserName;

   // private NfcAdapter nfcAdapter = null;
  ///  private PendingIntent pendingIntent = null;



    private RelativeLayout rlWallet, rlMerchant, rlFortune;
    private ImageView ivWallet, ivMerchant, ivFortune;
    private TextView tvWallet, tvMerchant, tvFortune;
    private Fragment fmWallet, fmMerchant, fmFortune;
    private Fragment currentFragment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);

        initTab();


      //  tvLog = (TextView) findViewById(R.id.tvLog);
       // R.style.The
/*
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
*/
     //   checkNFC();
        //  initNFC();
    }

    private void initTab() {
        rlWallet = (RelativeLayout) findViewById(R.id.rlWallet);
        rlMerchant = (RelativeLayout) findViewById(R.id.rlMerchant);
        rlFortune = (RelativeLayout) findViewById(R.id.rlFortune);

        rlWallet.setOnClickListener(this);
        rlMerchant.setOnClickListener(this);
        rlFortune.setOnClickListener(this);

        ivWallet = (ImageView) findViewById(R.id.ivWallet);
        ivMerchant = (ImageView) findViewById(R.id.ivMerchant);
        ivFortune = (ImageView) findViewById(R.id.ivFortune);

        tvWallet = (TextView) findViewById(R.id.tvWallet);
        tvMerchant = (TextView) findViewById(R.id.tvMerchant);
        tvFortune = (TextView) findViewById(R.id.tvFortune);

       fmWallet = new WalletFragment();
        fmMerchant = new MerchantFragment();
        fmFortune = new FortuneFragment();
       // fmWallet = getSupportFragmentManager().findFragmentById(R.id.WalletFragment);
       // fmMerchant = getSupportFragmentManager().findFragmentById(R.id.MerchantFragment);
       // fmFortune = getSupportFragmentManager().findFragmentById(R.id.FortuneFragment);

        getSupportFragmentManager().beginTransaction().add(R.id.ContentFrameLayout, fmWallet).commit();
        currentFragment = fmWallet;

        ivWallet.setImageResource(R.drawable.btn_wallet_press);
      //  tvWallet.setTextColor(getResources().getColor(R.color.bottomtab_press));

        ivMerchant.setImageResource(R.drawable.btn_merchant_nor);
       // tvMerchant.setTextColor(getResources().getColor(R.color.bottomtab_normal));

        ivFortune.setImageResource(R.drawable.btn_fortune_nor);
   //     tvFortune.setTextColor(getResources().getColor(R.color.bottomtab_normal));
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.rlWallet:
                switchTab(1);
                break;
            case R.id.rlMerchant:
                switchTab(2);
                break;
            case R.id.rlFortune:
                switchTab(3);
                break;
            default:
                break;
        }
    }

    private void switchTab(int tabId)
    {

        if (tabId == 1)
        {
            if (currentFragment == fmWallet)
                return;

            if (!fmWallet.isAdded())
            {
                getSupportFragmentManager().beginTransaction().hide(currentFragment).add(R.id.ContentFrameLayout, fmWallet).commit();
            }
            else
            {
                getSupportFragmentManager().beginTransaction().hide(currentFragment).show(fmWallet).commit();
            }

            currentFragment = fmWallet;

            ivWallet.setImageResource(R.drawable.btn_wallet_press);
            //tvWallet.setTextColor(getResources().getColor(R.color.bottomtab_press));

            ivMerchant.setImageResource(R.drawable.btn_merchant_nor);
            //tvMerchant.setTextColor(getResources().getColor(R.color.bottomtab_normal));

            ivFortune.setImageResource(R.drawable.btn_fortune_nor);
            //tvFortune.setTextColor(getResources().getColor(R.color.bottomtab_normal));
        }
        else if(tabId == 2)
        {
            if (currentFragment == fmMerchant)
                return;

            if (!fmMerchant.isAdded())
            {
                getSupportFragmentManager().beginTransaction().hide(currentFragment).add(R.id.ContentFrameLayout, fmMerchant).commit();
            }
            else
            {
                getSupportFragmentManager().beginTransaction().hide(currentFragment).show(fmMerchant).commit();
            }

            currentFragment = fmMerchant;

            ivWallet.setImageResource(R.drawable.btn_wallet_nor);
            //tvWallet.setTextColor(getResources().getColor(R.color.bottomtab_normal));

            ivMerchant.setImageResource(R.drawable.btn_merchant_press);
            //tvMerchant.setTextColor(getResources().getColor(R.color.bottomtab_press));

            ivFortune.setImageResource(R.drawable.btn_fortune_nor);
            //tvFortune.setTextColor(getResources().getColor(R.color.bottomtab_normal));
        }
        else if(tabId == 3)
        {
            if (currentFragment == fmFortune)
                return;

            if (!fmFortune.isAdded())
            {
                getSupportFragmentManager().beginTransaction().hide(currentFragment).add(R.id.ContentFrameLayout, fmFortune).commit();
            }
            else
            {
                getSupportFragmentManager().beginTransaction().hide(currentFragment).show(fmFortune).commit();
            }

            currentFragment = fmFortune;

            ivWallet.setImageResource(R.drawable.btn_wallet_nor);
            //tvWallet.setTextColor(getResources().getColor(R.color.bottomtab_normal));

            ivMerchant.setImageResource(R.drawable.btn_merchant_nor);
            //tvMerchant.setTextColor(getResources().getColor(R.color.bottomtab_normal));

            ivFortune.setImageResource(R.drawable.btn_fortune_press);
           // tvFortune.setTextColor(getResources().getColor(R.color.bottomtab_press));
        }
        else
        {
            return;
        }
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

/*
    @Override
    protected void onResume()
    {
        super.onResume();

        // enableForegroundDispatch();


        if ( nfcAdapter.ACTION_NDEF_DISCOVERED.equals(getIntent().getAction()) )
        {
            Log.d("nfc", "ACTION_NDEF_DISCOVERED");
            resolveIntent(getIntent());
        }
        else if(nfcAdapter.ACTION_TECH_DISCOVERED.equals(getIntent().getAction()))
        {

            Log.d("nfc", "ACTION_TECH_DISCOVERED");
            resolveIntent(getIntent());
        }
        else if(nfcAdapter.ACTION_TAG_DISCOVERED.equals(getIntent().getAction()))
        {
            Log.d("nfc", "ACTION_TAG_DISCOVERED");
            resolveIntent(getIntent());
        }
    }


    private void resolveIntent(Intent intent)
    {
        Log.d("nfc", "resolveIntent");
        Parcelable[] rawMsgs = intent.getParcelableArrayExtra(NfcAdapter.EXTRA_NDEF_MESSAGES);
        Log.d("nfc", "msg count is " + rawMsgs.length);

        NdefMessage msg = (NdefMessage) rawMsgs[0];
        String s = new String(msg.getRecords()[0].getPayload());
        Log.d("nfc", "...............nfc content is " + s);

        Toast.makeText(this, "检测到tag " + s, Toast.LENGTH_LONG).show();
    }

    @Override
    protected void onNewIntent(Intent intent)
    {
        Log.d("nfc", "onNewIntent...................................");
        setIntent(intent);
    }

    @Override
    protected void onPause()
    {
        super.onPause();
        disableForegroundDispatch();
    }
*/
    /**
     *

    private void enableForegroundDispatch()
    {
        if (nfcAdapter != null)
        {
            nfcAdapter.enableForegroundDispatch(this, pendingIntent, null, null);
        }
    }


    private void disableForegroundDispatch()
    {
        if (nfcAdapter != null)
        {
            nfcAdapter.disableForegroundDispatch(this);
        }
    }


    private boolean checkNFC()
    {
        boolean ret;

        nfcAdapter = NfcAdapter.getDefaultAdapter(this);
        if (nfcAdapter == null) {
            Log.d("nfc", "nfcAdapter is null");
            return false;
        }
        Log.d("nfc", "nfcAdapter is ok");

        ret = nfcAdapter.isEnabled();
        if (ret)
        {
            Log.d("nfc", "nfc is enabled");
        }
        else
        {
            Log.d("nfc", "nfc isn't enabled");
        }

        return true;
    }



    private void initNFC()
    {
        pendingIntent = PendingIntent.getActivity(this, 0, new Intent(this, getClass()).addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP), 0);
    }
    */
    public void btnDownloadFile(View view)
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Dialog");
        builder.setMessage("少数派客户端");
        builder.setPositiveButton("OK", null);
        builder.setNegativeButton("Cancel", null);
        builder.show();

        /*
        Request request = new Request.Builder()
              //  .url("http://192.168.1.9:8080/resume/myself.html")
                .url("http://192.168.1.9:8080/resume/我的照片.jpg")
                .build();

        client.newCall(request).enqueue(new Callback() {


            @Override
            public void onFailure(Request request, IOException e) {
                Log.i("http onFailure。。。。。", e.toString());
            }

            @Override
            public void onResponse(Response response) {
                if (!response.isSuccessful())
                {
                   Log.i("http。。。。。。。。。。。。。。", "downlowd error");

                    return;
                }


                try {
                    byte[] content = response.body().bytes();
                    Log.i("http。。。。。。。。。。。。。", "内容大小" + content.length);

                    File fileDir = getFilesDir();

                    File file = new File(fileDir, "我的照片.jpg");
                    file.createNewFile();

                    FileOutputStream out = new FileOutputStream(file.getAbsolutePath());
                    out.write(content);
                    out.flush();
                    out.close();


                    Log.i("http。。。。。。。。。。。。。。。。。", "downlowd success");

                } catch (IOException e) {
                    e.printStackTrace();

                    Log.i("http。。。。。。。。。。。。。。。。", "downlowd exception");
                }

                //  System.out.println(response.body().string());
            }
        });
        */
    }

}
