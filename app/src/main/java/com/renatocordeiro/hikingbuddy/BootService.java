package com.renatocordeiro.hikingbuddy;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import static com.renatocordeiro.hikingbuddy.Utils.print;
/**
 * Created by renatosc on 4/14/18.
 */

public class BootService extends BroadcastReceiver {
    public BootService() {
    }

    public void onReceive(Context context, Intent intent) {
        print("onReceive - BroadcastReceiver");

        Boolean isHiking = PrefUtils.getCurrentHikeId() == null ? false: true;
        print("isHiking=" + isHiking.toString());
        if (isHiking) {
            ServiceManager.startStepCounter();
        }
    }
}
