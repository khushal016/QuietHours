package com.hidtechs.quiethours;

import android.app.IntentService;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.IBinder;

/**
 * Created by napster on 22/7/15.
 */
public class ScreenService extends Service {

    BroadcastReceiver sReceiver;
    IntentFilter filter;

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        filter = new IntentFilter(Intent.ACTION_SCREEN_ON);
        filter.addAction(Intent.ACTION_SCREEN_OFF);
        filter.addAction(Intent.ACTION_USER_PRESENT);
        sReceiver = new ScreenReceiver();
        registerReceiver(sReceiver,filter);
        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unregisterReceiver(sReceiver);
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
