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

public class PurchaseFragment extends Fragment {
    private static final String LOG_TAG = "PurchaseFragment";


    int money = 100;
    int purchaseId = 1;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.nfcchangsha_purchase_fragment, container, false);
    }

    public void initPurchase()
    {
        byte[] header = {(byte)0x80, 0x50, (byte)0xA2, 0x00, 0x0B};



        byte[] m = INT.toBytes(money);


        byte[] apdu = new byte[16];
        System.arraycopy(header, 0, apdu, 0, header.length);
        System.arraycopy(m, 0, apdu, 5, m.length);
        System.arraycopy(TestData.test_terminalId, 0, apdu, 9, TestData.test_terminalId.length);

        String cmd = HEX.ByteArrayToHexString(apdu);
        Log.d(LOG_TAG, cmd);
    }

    public void purchase()
    {
        byte[] header = {(byte)0x80, (byte)0x30, (byte)0x00, (byte)0x00, (byte)0x0f};

        byte[] pid = INT.toBytes(purchaseId);
        //2015-11-24 10:10:10

        byte[] mac1 = new byte[4];

        byte[] apdu = new byte[20];

        System.arraycopy(header, 0, apdu, 0, header.length);
        System.arraycopy(pid, 0, apdu, 5, pid.length);
        System.arraycopy(TestData.test_datetime, 0, apdu, 9, TestData.test_datetime.length);
        System.arraycopy(mac1, 0, apdu, 16, mac1.length);

    }
}
