/*
 * Copyright (C) 2013 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.cssweb.walletaphone.nfc.test;

import android.nfc.NfcAdapter;
import android.nfc.Tag;
import android.nfc.tech.IsoDep;

import android.util.Log;


import com.cssweb.walletaphone.nfc.common.HEX;

import java.io.IOException;

import java.util.Arrays;



public class NFCCallback implements NfcAdapter.ReaderCallback {
    private static final String TAG = "NFCCallback";

    private static final String AID = "A00000000386980701";


    private static final String INS_SELECT = "00A40400";


    private static final byte[] SW_SUCCESS = {(byte) 0x90, (byte) 0x00};



    /**
     * Callback when a new tag is discovered by the system.
     *
     * <p>Communication with the card should take place here.
     *
     * @param tag Discovered tag
     */
    @Override
    public void onTagDiscovered(Tag tag) {
        Log.i(TAG, "New tag discovered");

        IsoDep isoDep = IsoDep.get(tag);

        if (isoDep != null) {
            try {

                isoDep.connect();


                String LC = "09";
                byte[] command = HEX.HexStringToByteArray(INS_SELECT + LC + AID);
                Log.i(TAG, "请求: " + HEX.ByteArrayToHexString(command));

                byte[] result = isoDep.transceive(command);


                int resultLength = result.length;

                byte[] statusWord = {result[resultLength-2], result[resultLength-1]};
                byte[] payload = Arrays.copyOf(result, resultLength-2);

                if (Arrays.equals(SW_SUCCESS, statusWord)) {

                    String tmp = HEX.ByteArrayToHexString(result);
                    Log.i(TAG, "响应：" + tmp);
                }
            } catch (IOException e) {
                Log.e(TAG, "Error communicating with card: " + e.toString());
            }
        }
    }




}
