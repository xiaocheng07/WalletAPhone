package com.cssweb.walletaphone.wallet;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cssweb.walletaphone.R;

/**
 * Created by chenhf on 2015/8/7.
 */
public class WalletFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.wallet_fragment, container, false);
    }

}
