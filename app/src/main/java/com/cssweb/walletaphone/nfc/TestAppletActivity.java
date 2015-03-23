package com.cssweb.walletaphone.nfc;

import android.nfc.NfcAdapter;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.cssweb.walletaphone.R;

import org.simalliance.openmobileapi.Channel;
import org.simalliance.openmobileapi.Reader;
import org.simalliance.openmobileapi.SEService;
import org.simalliance.openmobileapi.Session;

public class TestAppletActivity extends ActionBarActivity implements SEService.CallBack{

    private static final String LOG_TAG = "TestAppletActivity";

    private NfcAdapter nfcAdapter;
    private SEService seService;
    private TextView tvMsg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_applet);

        tvMsg = (TextView) findViewById(R.id.tvMsg);

        nfcAdapter = NfcAdapter.getDefaultAdapter(this);

        try {
            seService = new SEService(this, this);
        }
        catch (SecurityException e) {
            tvMsg.append("Binding not allowed, uses-permission SMARTCARD?");
        } catch (Exception e) {
            tvMsg.append("Exception: " + e.getMessage());
        }
    }

    @Override
    public void serviceConnected(SEService seService) {
        Session session = null;
        Channel channel = null;

        tvMsg.append("serviceConnected()\n");
        if(seService.isConnected() == false)
        {
            tvMsg.append("seService is not Connected");
            return;
        }

        try {
            tvMsg.append("Getting available readers...\n");

            Reader[] readers = seService.getReaders();
            if (readers.length <= 0) {
                tvMsg.append("reader is null");
                return;
            }

            for(Reader reader:readers)
            {
                Log.i(LOG_TAG, "reader.name = " + reader.getName());
                tvMsg.append("reader.name = " + reader.getName()+"\n");
            }

            tvMsg.append("Getting Session ...\n");
            session = readers[1].openSession();

            //mTextView.append("ATR: "+ByteArrayToString(session.getATR())+"\n");
            byte[] aid = {(byte) 0xA0,0x00,0x00,0x00,0x03,0x00,0x00,0x00};
            tvMsg.append("准备打开AID\n");
            channel = session.openBasicChannel(aid);
           // channel = session.openLogicalChannel(aid);
            tvMsg.append("打开AID结束\n");

            byte[] apduRequest = {(byte) 0x80, (byte) 0xca, (byte) 0x9f, 0x7f, 0x00};
            byte[] apduResponse = channel.transmit(apduRequest);

            if(apduResponse.length >= 20)
            {
               // tvMsg.append(String.format("SerialID: %02x%02x%02x%02x\n", response1[15],response1[16],response1[17],response1[18]));
            }
        } catch (Exception e) {
            tvMsg.append("Error occured: "+e.getMessage()+"\n");
        }

        if(channel != null) {
            channel.close();
        }

        if(session != null) {
            session.close();
        }


//      try {
//          mTextView.append("selectDefaultSecureElement()"+"\n");
//          mNfc.selectDefaultSecureElement(NfcAdapter.SMART_MX_ID);
//          mTextView.append("setDefaultSecureElementState()"+"\n");
//          mNfc.setDefaultSecureElementState(true);
//      } catch (Exception e) {
//          mTextView.append("ERROR: "+e.getMessage()+"\n");
//          Log.e(LOG_TAG, "ERROR: "+e.getMessage());
//      }
    }

    @Override
    protected void onDestroy()
    {
        if (seService != null && seService.isConnected()) {
            seService.shutdown();
        }
        super.onDestroy();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_test_applet, menu);
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

    /**
     *
     * @param b
     * @return
     */
    private String ByteArrayToString(byte[] b) {
        StringBuffer s = new StringBuffer();
        for (int i = 0; i < b.length; i++) {
            s.append(Integer.toHexString(0x100 + (b[i] & 0xff)).substring(1));
        }
        return s.toString();
    }

}
