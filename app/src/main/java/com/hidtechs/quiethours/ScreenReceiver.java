package com.hidtechs.quiethours;

import android.annotation.SuppressLint;
import android.app.KeyguardManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.AudioManager;
import android.util.Log;


/**
 * Created by napster on 12/7/15.
 */
public class ScreenReceiver extends BroadcastReceiver {


    @SuppressLint("NewApi")
    @Override
    public void onReceive(Context context, Intent intent) {
        AudioManager audioManager = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
        KeyguardManager keyguardManager = (KeyguardManager) context.getSystemService(Context.KEYGUARD_SERVICE);
        SharedPreferences preferences = context.getSharedPreferences("MyFiles", Context.MODE_PRIVATE);

        if (intent.getAction().equals(Intent.ACTION_SCREEN_OFF)) {
            if (preferences.getBoolean("silentButton", false) && keyguardManager.isKeyguardLocked()) {
                audioManager.setRingerMode(AudioManager.RINGER_MODE_SILENT);
                Log.d(""," broadcast received");
            }
        } else if (intent.getAction().equals(Intent.ACTION_USER_PRESENT)) {
            audioManager.setRingerMode(AudioManager.RINGER_MODE_NORMAL);
            
        }
    }
}
