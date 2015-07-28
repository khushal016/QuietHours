package com.hidtechs.quiethours;

import android.annotation.SuppressLint;
import android.app.KeyguardManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.media.AudioManager;
import android.net.Uri;
import android.provider.ContactsContract;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.util.Log;

/**
 * Created by napster on 18/7/15.
 */
public class IncomingCall extends BroadcastReceiver {
    AudioManager audioManager;
    TelephonyManager telephonyManager;
    KeyguardManager keyguardManager;
    String number;
    SharedPreferences preferences;
    DBHelper dbHelper;
    @Override
    public void onReceive(final Context context, Intent intent) {

        audioManager= (AudioManager) context.getSystemService(context.AUDIO_SERVICE);
        telephonyManager= (TelephonyManager) context.getSystemService(context.TELEPHONY_SERVICE);
        keyguardManager= (KeyguardManager) context.getSystemService(context.KEYGUARD_SERVICE);
        preferences=context.getSharedPreferences("MyFiles",Context.MODE_PRIVATE);
        dbHelper = new DBHelper(context);
        PhoneStateListener callStateListener = new PhoneStateListener() {
            @SuppressLint("NewApi")
            public void onCallStateChanged(int state, String incomingNumber)
            {
                number=incomingNumber;

                // If phone ringing
                if(state==TelephonyManager.CALL_STATE_RINGING)
                {
                    Log.d("","call recieved");
                    if (preferences.getBoolean("allcontacts",false) && preferences.getBoolean("silentButton",false) && preferences.getBoolean("callsButton",false) && contactExists(context,number))
                    {
                        audioManager.setRingerMode(AudioManager.RINGER_MODE_NORMAL);
                        Log.d("","call recieved");
                    }

                   else if (preferences.getBoolean("exceptions",false) && preferences.getBoolean("silentButton",false) && preferences.getBoolean("callsButton",false) && exceptionContactExists(number))
                    {
                        audioManager.setRingerMode(AudioManager.RINGER_MODE_NORMAL);
                        Log.d("", "call recieved");
                    }
                }
            }
        };
        telephonyManager.listen(callStateListener, PhoneStateListener.LISTEN_CALL_STATE);
    }
    public boolean contactExists(Context context,String number) {
        try {
            Uri uri = Uri.withAppendedPath(ContactsContract.PhoneLookup.CONTENT_FILTER_URI, Uri.encode(number));
            Cursor cursor = context.getContentResolver().query(uri, new String[]{ContactsContract.PhoneLookup.DISPLAY_NAME}, number, null, null );

            try {
                if (cursor.moveToFirst()) {
                    return true;
                }
            } finally {
                if (cursor != null)
                    cursor.close();
            }
        } catch (IllegalArgumentException iae) {
            return false;
        }
        return false;
    }
    public boolean exceptionContactExists(String number)
    {
        String newNumber=number.replaceAll("\\s+", "");
        String newNumber2 = newNumber.substring(3);
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String selectQuery = "SELECT "+DBHelper.CONTACTS_COLUMN_PHONE+" FROM "+DBHelper.CONTACTS_TABLE_NAME+" WHERE "+DBHelper.CONTACTS_COLUMN_PHONE+"=?"+" OR "+DBHelper.CONTACTS_COLUMN_PHONE+"=?";
        try{
        Cursor cursor= db.rawQuery(selectQuery, new String[]{newNumber,newNumber2});
        try {
            if (cursor.moveToFirst()) {
                return true;
            }
        } finally {
            if (cursor != null)
                cursor.close();
        }
    } catch (IllegalArgumentException iae) {
        return false;
    }
    return false;
    }
}
