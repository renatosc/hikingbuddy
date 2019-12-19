package com.renatocordeiro.hikingbuddy;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.IBinder;
import android.os.PowerManager;
import android.util.Log;

/**
 * Created by renatosc on 4/13/18.
 */

public class StepCounterService extends Service implements SensorEventListener {
    private String TAG  = "Hiking Buddy";
    SensorManager mSensorManager;
    Sensor mStepDetectorSensor;

    PowerManager.WakeLock mWakeLock;



    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(TAG, "On StepCounterService Created");
        mSensorManager = (SensorManager) this.getSystemService(Context.SENSOR_SERVICE);
        if(mSensorManager.getDefaultSensor(Sensor.TYPE_STEP_DETECTOR) != null)
        {
            mStepDetectorSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_STEP_DETECTOR);
            mSensorManager.registerListener(this, mStepDetectorSensor, SensorManager.SENSOR_DELAY_NORMAL);


            // getting lock to not allow the device sleep
            try {
                // https://developer.android.com/reference/android/os/PowerManager.html
                PowerManager powerManager = (PowerManager) getSystemService(Context.POWER_SERVICE);
                mWakeLock = powerManager.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK, TAG);
                mWakeLock.acquire();
                Log.d(TAG,"Acquired WakeLock with success!");
            } catch (Exception e) {
                Log.d(TAG,"Failed to acquire WakeLock. App could stop counting if device enters in sleep mode!");
            }
        }

    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        return Service.START_STICKY;
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        Log.d(TAG, "on onSensorChanged");
        HikeModel currHike = new HikeModel();
        if (currHike != null){
            currHike.increaseStepCount(1);
        }

    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    @Override
    public IBinder onBind(Intent intent) {

        return null;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mSensorManager.unregisterListener(this);

        try {
            if (mWakeLock != null) {
                mWakeLock.release();
                Log.d(TAG,"Released WakeLock with success.");
            } else {
                Log.d(TAG,"No WakeLock found to be released.");
            }
        } catch (Exception e) {
            Log.d(TAG,"Failed to release WakeLock.!");
        }
    }
}