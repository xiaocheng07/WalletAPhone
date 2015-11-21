package com.cssweb.walletaphone.nfc.test;

import android.app.Activity;
import android.nfc.NfcAdapter;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.cssweb.walletaphone.R;

public class CardInfoFragment extends Fragment {
    private static final String LOG_TAG = "CardInfoFragment";
    public static int READER_FLAGS = NfcAdapter.FLAG_READER_NFC_A | NfcAdapter.FLAG_READER_SKIP_NDEF_CHECK;
    NFCCallback nfcCallback;
    Button btnGetBalance;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.nfcchangsha_cardinfo_fragment, container, false);

        btnGetBalance = (Button) view.findViewById(R.id.btnGetBalance);
        btnGetBalance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(LOG_TAG, "==========================onClick=========================");
                Snackbar.make(view, "onClick", Snackbar.LENGTH_LONG).setAction("Action", null).show();
            }
        });


        if (view != null) {
            Log.d(LOG_TAG, "==========================onCreateView=========================");


            nfcCallback = new NFCCallback();
            enableReaderMode();
        }

        return view;
    }

    private void enableReaderMode() {


        Activity activity = getActivity();


        NfcAdapter nfc = NfcAdapter.getDefaultAdapter(activity);
        if (nfc != null) {
            nfc.enableReaderMode(activity, nfcCallback, READER_FLAGS, null);

            Log.d(LOG_TAG, "==========================enableReaderMode success=========================");
        }
        else
        {
            Log.d(LOG_TAG, "==========================enableReaderMode error=========================");

        }
    }

    private void disableReaderMode() {
        Log.i(LOG_TAG, "Disabling reader mode");
        Activity activity = getActivity();
        NfcAdapter nfc = NfcAdapter.getDefaultAdapter(activity);
        if (nfc != null) {
            nfc.disableReaderMode(activity);
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        disableReaderMode();
    }

    @Override
    public void onResume() {
        super.onResume();
        enableReaderMode();
    }

}
