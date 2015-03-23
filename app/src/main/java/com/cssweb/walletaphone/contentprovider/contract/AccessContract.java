package com.cssweb.walletaphone.contentprovider.contract;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.provider.ContactsContract;
import android.util.Log;

/**
 * Created by chenhf on 2015/3/18.
 */
public class AccessContract {


    public void accessContract(Context ctx) {
        ContentResolver resolver = ctx.getContentResolver();

        Cursor cursor = resolver.query(ContactsContract.Contacts.CONTENT_URI, null, null, null, null);

        if (cursor.moveToFirst()) {
            int idColumn = cursor.getColumnIndex(ContactsContract.Contacts._ID);
            int displayNameColumn = cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME);

            do {
                //获取联系人ID
                String contactId = cursor.getString(idColumn);
                //获取联系人姓名
                String disPlayName = cursor.getString(displayNameColumn);
                //Toast.makeText(MainActivity.this, "联系人姓名：" + disPlayName, Toast.LENGTH_LONG).show();
                int phoneCount = cursor.getInt(cursor.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER));
                if (phoneCount > 0) {
                    Cursor phonesCursor = resolver.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null,
                            ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = " + contactId, null, null);
                    if (phonesCursor.moveToFirst()) {
                        do {
                            String phoneNumber = phonesCursor.getString(phonesCursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                            //Toast.makeText(MainActivity.this, "联系人电话：" + phoneNumber, Toast.LENGTH_LONG).show();
                            Log.d("test", "phoneNumber is " + phoneNumber);
                        }
                        while (phonesCursor.moveToNext());
                    }
                }
            }
            while (cursor.moveToNext());

        }
    }

}
