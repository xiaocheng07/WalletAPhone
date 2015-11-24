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

public class CAPPPurchaseFragment extends Fragment {

    private static final String LOG_TAG= "CAPPPurchaseFragment";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.merchant_fragment, container, false);
    }

    public void initCAPPPurchase()
    {
        byte[] header = {(byte)0x80, (byte)0x50, (byte)0x03, (byte)0x02, (byte)0x0B};

        byte[] m = INT.toBytes(0);


        byte[] apdu = new byte[16];

        System.arraycopy(header, 0, apdu, 0, header.length);
        apdu[5] = common.keyIndex;
        System.arraycopy(m, 0, apdu, 6, m.length);
        System.arraycopy(common.terminalId, 0, apdu, 10, common.terminalId.length);

        String cmd = HEX.ByteArrayToHexString(apdu);
        Log.d(LOG_TAG, cmd);
    }

    public void updteCAPPPurchase()
    {
        byte[] header = {(byte)0x80, (byte)0xDC, (byte)0x00, (byte)0x00};

    }

    public void CAPPPurchase()
    {
        byte[] header = {(byte)0x80, (byte)0x54, (byte)0x01, (byte)0x00, (byte)0x0F};

        byte[] mac1 = new byte[4];

        byte[] apdu = new byte[20];

        System.arraycopy(header, 0, apdu, 0, header.length);
        byte[] pid = INT.toBytes(common.purchaseId);
        System.arraycopy(pid, 0, apdu, 5, pid.length);
        System.arraycopy(common.datetime, 0, apdu, 9, common.datetime.length);
        System.arraycopy(mac1, 0, apdu, 16, mac1.length);
    }
}
