package com.renatocordeiro.hikingbuddy;

import android.content.Context;
import android.util.Log;

/**
 * Created by renatosc on 4/13/18.
 */

public class Utils {
    public static String TAG = "Hiking Buddy";
    private static Context mContext;

    public static boolean isPrintEnabled = true;

    public static void setContext(Context ctx){
        mContext = ctx;
    }
    public static Context getContext(){
        return mContext;
    }

    public static void print(Object value) {
        if (isPrintEnabled) {
            if (value == null) {
                Log.d(TAG, "null");
                return ;
            }
            String text = value.toString();
            Log.d(TAG, text);
        }
    }

    public static int getAndroidAPIVersion() {
        return android.os.Build.VERSION.SDK_INT;
    }

}
