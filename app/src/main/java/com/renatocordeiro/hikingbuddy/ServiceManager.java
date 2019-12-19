package com.renatocordeiro.hikingbuddy;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.util.Log;

/**
 * Created by renatosc on 4/14/18.
 */

public class ServiceManager {

    private static String TAG = Utils.TAG;


    // -------------------------------
    // -- Step Sensor

    public static boolean hasStepSensor() {
        Log.d(TAG, "hasStepSensor - began");

        // Check that the device supports the step counter and detector sensors
        PackageManager packageManager = Utils.getContext().getPackageManager();

        int currentApiVersion = Utils.getAndroidAPIVersion();
        Boolean isKitKatMininum = currentApiVersion >= android.os.Build.VERSION_CODES.KITKAT;
        //Boolean hasStepCounter = packageManager.hasSystemFeature(PackageManager.FEATURE_SENSOR_STEP_COUNTER); // we are using step detector to keep a daily track of steps
        Boolean hasStepDetector = packageManager.hasSystemFeature(PackageManager.FEATURE_SENSOR_STEP_DETECTOR);
        Log.d(TAG,"isKitKatMininum=" + isKitKatMininum);
        Log.d(TAG,"hasStepDetector=" + hasStepDetector);
        Boolean hasSensor = currentApiVersion >= android.os.Build.VERSION_CODES.KITKAT
                //&& packageManager.hasSystemFeature(PackageManager.FEATURE_SENSOR_STEP_COUNTER);
                && packageManager.hasSystemFeature(PackageManager.FEATURE_SENSOR_STEP_DETECTOR);

        Log.d(TAG, "hasStepSensor - ended");

        return hasSensor;
    }

    public static void startStepCounter(){
        Intent intent = new Intent(Utils.getContext(), StepCounterService.class);
        Utils.getContext().startService(intent);
    }

    public static void stopStepCounter(){
        Intent intent = new Intent(Utils.getContext(), StepCounterService.class);
        Utils.getContext().stopService(intent);
    }


}
