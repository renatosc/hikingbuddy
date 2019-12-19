package com.renatocordeiro.hikingbuddy;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;
/**
 * Created by renatosc on 4/13/18.
 */

public class PrefUtils {

    private static final String PREF_HIKE_ID = "pref_hike_id";
    private static final String PREF_HIKE_START_TIME = "pref_hike_start_time";
    private static final String PREF_HIKE_NAME = "pref_hike_name";
    private static final String PREF_HIKE_STEP_COUNT = "pref_hike_step_count";



    public static void setCurrentHikeId(final Long id) {
        if (Utils.getContext() != null) {
            SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(Utils.getContext());
            sp.edit().putLong(PREF_HIKE_ID, id).commit();
        }
    }

    public static Long getCurrentHikeId() {
        if (Utils.getContext() != null) {
            SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(Utils.getContext());
            Long id = sp.getLong(PREF_HIKE_ID, -1);
            if (id == -1) {
                return null;
            }
            return id;
        }
        return null;
    }

    public static void setCurrentHikeStartTime(final Long epoch) {
        if (Utils.getContext() != null) {
            SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(Utils.getContext());
            sp.edit().putLong(PREF_HIKE_START_TIME, epoch).commit();
        }
    }

    public static Long getCurrentHikeStartTime() {
        if (Utils.getContext() != null) {
            SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(Utils.getContext());
            Long epoch = sp.getLong(PREF_HIKE_START_TIME, -1);
            if (epoch == -1) {
                return null;
            }
            return epoch;
        }
        return null;
    }

    public static void setCurrentHikeName(final String name) {
        if (Utils.getContext() != null) {
            SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(Utils.getContext());
            sp.edit().putString(PREF_HIKE_NAME, name).commit();
        }
    }

    public static String getCurrentHikeName() {
        if (Utils.getContext() != null) {
            SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(Utils.getContext());
            String name = sp.getString(PREF_HIKE_NAME, "-1");
            if (name == "-1") {
                return null;
            }
            return name;
        }
        return null;
    }

    public static void setCurrentHikeStepCount(final Integer stepCount) {
        if (Utils.getContext() != null) {
            SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(Utils.getContext());
            sp.edit().putInt(PREF_HIKE_STEP_COUNT, stepCount).commit();
        }
    }

    public static Integer getCurrentHikeStepCount() {
        if (Utils.getContext() != null) {
            SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(Utils.getContext());
            Integer stepCount = sp.getInt(PREF_HIKE_STEP_COUNT, -1);
            if (stepCount == -1) {
                return null;
            }
            return stepCount;
        }
        return null;
    }


    public static void clearHikeInfo(){
        if (Utils.getContext() != null) {
            SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(Utils.getContext());
            sp.edit().remove(PREF_HIKE_START_TIME).remove(PREF_HIKE_ID).remove(PREF_HIKE_NAME).commit();
        }
    }
}
