package com.renatocordeiro.hikingbuddy;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import static com.renatocordeiro.hikingbuddy.Utils.print;


/**
 * Created by renatosc on 4/13/18.
 */

public class HikingDB extends SQLiteOpenHelper {

    private static HikingDB instance;

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "hikingbuddy";
    private static final String TABLE = "hikes";
    private static final String COL_ID = "id";
    private static final String COL_NAME = "name";
    private static final String COL_DATE = "date";
    private static final String COL_STEP_COUNT = "step_count";


    private static final String STM_CREATE_TABLE = "CREATE TABLE "
            + TABLE
            + "(" + COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + COL_NAME + " TEXT, "
            + COL_DATE + " INTEGER, "
            + COL_STEP_COUNT + " INTEGER)";

    private static final String STM_REMOVE_ALL_ENTRIES = "DELETE FROM " + TABLE;

    public static Integer lastStepCountValue = 0;

    public HikingDB() {
        super(Utils.getContext(), DATABASE_NAME, null, DATABASE_VERSION);
    }


    public static HikingDB getInstance() {
        if (instance != null) {
            return instance;
        }
        instance = new HikingDB();
        return instance;
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(STM_CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE);
        this.onCreate(db);
    }


    // -----------------------------
    // ---- Public functions -------

    public Long newHike(String name, Long startEpoch) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COL_NAME, name);
        values.put(COL_DATE, startEpoch);
        values.put(COL_STEP_COUNT, 0);
        try {
        long row = db.insert(TABLE, null, values);
        if (row != -1) {
            String query = "SELECT ROWID from " + TABLE + " order by ROWID DESC limit 1";
            Cursor c = db.rawQuery(query, null);
            if (c != null && c.moveToFirst()) {
                return c.getLong(0); // returning the new added id on
            }
        }
        db.close();

        } catch( Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    // updates the database by increasing the step number by +1 for today date
    public Integer increaseStepCountForHiking() {

        Long hikeId = PrefUtils.getCurrentHikeId();
        print("hikeId=");
        print(hikeId);
        if (hikeId == null) {
            Log.d(Utils.TAG, "No active hike. Not saving anything in database");
            return null;
        }


        String selectQuery = "SELECT " + COL_STEP_COUNT + " FROM " +  TABLE + " WHERE " + COL_ID + " = '" + hikeId + "'";
        Integer currentStepCounts = null;
        // Retrieving the steps count for today date (if already exist any)
        try {
            SQLiteDatabase db = this.getReadableDatabase();
            Cursor c = db.rawQuery(selectQuery, null);
            if (c.moveToFirst()) {
                currentStepCounts = c.getInt((c.getColumnIndex(COL_STEP_COUNT)));
            }
            db.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (currentStepCounts == null) {
            print("No current steps count found on database for active hike");
            return null;
        }

        // Updating the database step count
        try {
            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put(COL_STEP_COUNT, ++currentStepCounts);
            int row = db.update(TABLE, values, COL_ID + " = '" + hikeId + "'", null);
            if(row == -1) {
                print("Update failed");
                return null;
            }
            db.close();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

        lastStepCountValue = currentStepCounts;

        HikeModel hike = HikeModel.getCurrentInstance();
        if (hike == null){
            print("No current hike found. Not able to update the steps count");
        } else {
            hike.setStepCount(currentStepCounts);
        }

        return currentStepCounts;
    }

    public void removeAllEntries(){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL(STM_REMOVE_ALL_ENTRIES);
    }

}
