package com.cssweb.walletaphone.nfc.test;


import android.app.PendingIntent;
import android.content.Intent;
import android.content.IntentFilter;
import android.nfc.FormatException;
import android.nfc.NdefMessage;
import android.nfc.NdefRecord;
import android.nfc.NfcAdapter;
import android.nfc.Tag;
import android.nfc.tech.Ndef;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.cssweb.walletaphone.R;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

public class NFCChangShaActivity extends AppCompatActivity {
   // private NfcAdapter nfcAdapter;
    //private PendingIntent pendingIntent;
  //  IntentFilter writeTagFilters[];
//    boolean writeMode;
//    Tag mytag;

 //   private EditText etMsg;
     List<String> titleList = new ArrayList<String>();

    private ViewPager mViewPager;
    ArrayList<Fragment> pageViews = new ArrayList<Fragment>();
    CardInfoFragment cardInfoFragment;
    PurchaseFragment purchaseFragment;
    ChargeFragment chargeFragment;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.nfcchangsha_activity);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

/*
        LayoutInflater layoutInFlater = LayoutInflater.from(this);
        pageViews.add(layoutInFlater.inflate(R.layout.nfcchangsha_cardinfo_fragment, null));
        pageViews.add(layoutInFlater.inflate(R.layout.nfcchangsha_purchase_fragment, null));
        pageViews.add(layoutInFlater.inflate(R.layout.nfcchangsha_charge_fragment, null));
        */
        cardInfoFragment = new CardInfoFragment();
        purchaseFragment = new PurchaseFragment();
        chargeFragment = new ChargeFragment();
        pageViews.add(cardInfoFragment);
        pageViews.add(purchaseFragment);
        pageViews.add(chargeFragment);

        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());



        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(viewPagerAdapter);



        titleList.add("卡信息");
        titleList.add("消费");
        titleList.add("充值");

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);
        //tabLayout.setTabsFromPagerAdapter(viewPagerAdapter);


        /*

        nfcAdapter = NfcAdapter.getDefaultAdapter(this);
        pendingIntent = PendingIntent.getActivity(this, 0, new Intent(this, getClass()).addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP), 0);

        IntentFilter tagDetected = new IntentFilter(NfcAdapter.ACTION_TAG_DISCOVERED);
        tagDetected.addCategory(Intent.CATEGORY_DEFAULT);

        writeTagFilters = new IntentFilter[] { tagDetected };
        */

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_write_nfctag, menu);
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

    class ViewPagerAdapter extends FragmentStatePagerAdapter
    {

        public ViewPagerAdapter(FragmentManager fm)
        {
            super(fm);


        }

        @Override
        public int getCount() {
            return pageViews.size();
        }



        @Override
        public Fragment getItem(int position) {
            return pageViews.get(position);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return titleList.get(position);//页卡标题
        }
/*
 @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position)
        {
            container.addView(pageViews.get(position));//添加页卡

            return pageViews.get(position);
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView(pageViews.get(position));//删除页卡
        }*/


    }
/*
    public void btnWriteOnClick(View view)
    {
        try {
            if(mytag == null){
                Toast.makeText(this, "检测到tag ", Toast.LENGTH_LONG ).show();
            }else{
                write(etMsg.getText().toString(), mytag);

                Toast.makeText(this, "写入tag ", Toast.LENGTH_LONG ).show();
            }
        } catch (IOException e) {
            Toast.makeText(this, "写tag失败", Toast.LENGTH_LONG ).show();
            e.printStackTrace();
        } catch (FormatException e) {
            Toast.makeText(this, "写tag失败" , Toast.LENGTH_LONG ).show();
            e.printStackTrace();
        }
    }
    */


/*
    private void write(String text, Tag tag) throws IOException, FormatException {
        // Get an instance of Ndef for the tag.
        Ndef ndef = Ndef.get(tag);
        // Enable I/O
        ndef.connect();

        // Write the message
        NdefRecord[] records = { createRecord(text) };
        NdefMessage message = new NdefMessage(records);
        ndef.writeNdefMessage(message);

        // Close the connection
        ndef.close();
    }



    private NdefRecord createRecord(String text) throws UnsupportedEncodingException {
        String lang       = "en";
        byte[] textBytes  = text.getBytes();
        byte[] langBytes  = lang.getBytes("US-ASCII");
        int    langLength = langBytes.length;
        int    textLength = textBytes.length;
        byte[] payload    = new byte[1 + langLength + textLength];

        // set status byte (see NDEF spec for actual bits)
        payload[0] = (byte) langLength;

        // copy langbytes and textbytes into payload
        System.arraycopy(langBytes, 0, payload, 1,              langLength);
        System.arraycopy(textBytes, 0, payload, 1 + langLength, textLength);

        NdefRecord recordNFC = new NdefRecord(NdefRecord.TNF_WELL_KNOWN,  NdefRecord.RTD_TEXT,  new byte[0], payload);

        return recordNFC;
    }

    @Override
    protected void onNewIntent(Intent intent){

        if(NfcAdapter.ACTION_TAG_DISCOVERED.equals(intent.getAction())){
            mytag = intent.getParcelableExtra(NfcAdapter.EXTRA_TAG);
            Toast.makeText(this, "检测到tag " + mytag.toString(), Toast.LENGTH_LONG).show();
        }

    }

    @Override
    public void onPause(){
        super.onPause();
        WriteModeOff();
    }

    @Override
    public void onResume(){
        super.onResume();
        WriteModeOn();
    }

    private void WriteModeOn(){
        writeMode = true;
        nfcAdapter.enableForegroundDispatch(this, pendingIntent, writeTagFilters, null);
    }

    private void WriteModeOff(){
        writeMode = false;
        nfcAdapter.disableForegroundDispatch(this);
    }
    */

}
