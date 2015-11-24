package com.cssweb.walletaphone.nfc.test;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cssweb.walletaphone.R;
import com.cssweb.walletaphone.nfc.common.HEX;
import com.cssweb.walletaphone.nfc.common.INT;

public class ChargeFragment extends Fragment {
    private static final String LOG_TAG = "ChargeFragment";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.nfcchangsha_charge_fragment, container, false);
    }

    public void initCharge()
    {
        byte[] header = {(byte)0x80, 0x50, (byte)0xA1, 0x00};

        byte keyIndex = 0x00;
        int money = 100;
        byte[] m = INT.toBytes(money);
        byte[] terminalId = {0x01, 0x02, 0x03, 0x04, 0x05, 0x06};

        byte[] apdu = new byte[16];
        System.arraycopy(header, 0, apdu, 0, header.length);
        apdu[4] = 0x0B;
        System.arraycopy(m, 0, apdu, 5, m.length);
        System.arraycopy(terminalId, 0, apdu, 9, terminalId.length);

        String cmd = HEX.ByteArrayToHexString(apdu);
        Log.d(LOG_TAG, cmd);
    }

    public void charge()
    {
        byte[] header = {(byte)0x80, (byte)0x32, 0x00, 0x00, 0x0B};

        //2015-11-24 10:10:10
        byte[] datetime = {0x14, 0x0f, 0x0b, 0x18, 0x0a, 0x0a, 0x0a};

        byte[] mac2 = new byte[4];

        byte[] apdu = new byte[16];

        System.arraycopy(header, 0, apdu, 0, header.length);
        System.arraycopy(datetime, 0, apdu, 5, datetime.length);
        System.arraycopy(mac2, 0, apdu, 12, mac2.length);



    }
}
