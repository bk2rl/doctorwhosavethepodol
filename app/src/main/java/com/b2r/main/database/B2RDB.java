package com.b2r.main.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteCursorDriver;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.b2r.main.Constants;

public class B2RDB {
    private static final String DB_NAME = "b2rdb";
    private static final int DB_VERSION = 4;

    private static final String DB_TABLE_QUEST = "Quest";
    private static final String DB_TABLE_TASK = "Task";
    private static final String DB_TABLE_MAPMARKER = "Mapmarker";

    static final String DB_TABLE_QUEST_COLUMN_ID = "quest_ID";
    static final String DB_TABLE_QUEST_COLUMN_TITLE = "quest_title";
    static final String DB_TABLE_QUEST_COLUMN_SHORT_DESCRIPTION = "quest_short_description";
    static final String DB_TABLE_QUEST_COLUMN_LONG_DESCRIPTION = "quest_long_description";
    static final String DB_TABLE_QUEST_COLUMN_QUEST_LOGO_SRC = "quest_logo_src";
    static final String DB_TABLE_QUEST_COLUMN_PROGRESS = "quest_progress";
    static final String DB_TABLE_QUEST_COLUMN_IMG_SRC = "quest_img_src";
    static final String DB_TABLE_QUEST_COLUMN_MODE = "quest_mode";
    static final String DB_TABLE_QUEST_COLUMN_DURATION_TIME = "quest_time_duration";
    static final String DB_TABLE_QUEST_COLUMN_IS_STARTED = "quest_is_started";
    static final String DB_TABLE_QUEST_COLUMN_EXP = "quest_exp";
    static final String DB_TABLE_QUEST_COLUMN_HAS_BIND_TO_MAP = "quest_has_bind_to_map";
    static final String DB_TABLE_QUEST_COLUMN_IS_FAVORITE = "quest_is_favorite";
    static final String DB_TABLE_QUEST_COLUMN_HAS_BIND_TO_AGENT = "quest_has_bind_to_agent";
    static final String DB_TABLE_QUEST_COLUMN_DIFFICULTY_LEVEL = "quest_difficuty_level";

    static final String DB_TABLE_TASK_COLUMN_ID = "task_ID";
    static final String DB_TABLE_TASK_COLUMN_QUEST_ID = "quest_ID";
    static final String DB_TABLE_TASK_COLUMN_TITLE = "task_title";
    static final String DB_TABLE_TASK_COLUMN_SHORT_DESCRIPTION = "task_short_description";
    static final String DB_TABLE_TASK_COLUMN_LONG_DESCRIPTION = "task_long_description";
    static final String DB_TABLE_TASK_COLUMN_PSK_PASS = "task_psk_pass";
    static final String DB_TABLE_TASK_COLUMN_PSK_UNLOCK = "task_psk_unlock";
    static final String DB_TABLE_TASK_COLUMN_PSK_BRONZE = "task_psk_bronze";
    static final String DB_TABLE_TASK_COLUMN_PSK_SILVER = "task_psk_silver";
    static final String DB_TABLE_TASK_COLUMN_PSK_GOLD = "task_psk_gold";
    static final String DB_TABLE_TASK_COLUMN_IS_TASK_VISIBLE = "task_is_task_visible";
    static final String DB_TABLE_TASK_COLUMN_LATITUDE = "task_latitude";
    static final String DB_TABLE_TASK_COLUMN_LONGITUDE = "task_longitude";
    static final String DB_TABLE_TASK_COLUMN_HELP_IMG_SRC = "task_help_img_src";
    static final String DB_TABLE_TASK_COLUMN_SCORE_BRONZE = "task_score_bronze";
    static final String DB_TABLE_TASK_COLUMN_SCORE_SILVER = "task_score_silver";
    static final String DB_TABLE_TASK_COLUMN_SCORE_GOLD = "task_score_gold";
    static final String DB_TABLE_TASK_COLUMN_STATE = "task_state";
    static final String DB_TABLE_TASK_COLUMN_HAS_BIND_TO_MAP = "quest_has_bind_to_map";
    static final String DB_TABLE_TASK_COLUMN_HAS_BIND_TO_AGENT = "quest_has_bind_to_agent";
    static final String DB_TABLE_TASK_COLUMN_DIFFICULTY_LEVEL = "task_difficulty_level";

    static final String DB_TABLE_MAPMARKER_COLUMN_ID = "mapmarker_ID";
    static final String DB_TABLE_MAPMARKER_COLUMN_TASK_ID = "task_ID";

    private static final String DB_CREATE_TABLE_QUEST = "CREATE TABLE " + DB_TABLE_QUEST + " (\n " +
            DB_TABLE_QUEST_COLUMN_ID + " INTEGER PRIMARY KEY, \n " +
            DB_TABLE_QUEST_COLUMN_TITLE + " CHAR, \n " +
            DB_TABLE_QUEST_COLUMN_SHORT_DESCRIPTION + " CHAR, \n " +
            DB_TABLE_QUEST_COLUMN_LONG_DESCRIPTION + " CHAR, \n " +
            DB_TABLE_QUEST_COLUMN_QUEST_LOGO_SRC + " CHAR, \n " +
            DB_TABLE_QUEST_COLUMN_PROGRESS + " INTEGER, \n " +
            DB_TABLE_QUEST_COLUMN_IMG_SRC + " CHAR, \n " +
            DB_TABLE_QUEST_COLUMN_MODE + " CHAR, \n " +
            DB_TABLE_QUEST_COLUMN_DURATION_TIME + " INTEGER, \n " +
            DB_TABLE_QUEST_COLUMN_EXP + " INTEGER, \n " +
            DB_TABLE_QUEST_COLUMN_IS_STARTED + " BOOLEAN, \n " +
            DB_TABLE_QUEST_COLUMN_HAS_BIND_TO_MAP + " BOOLEAN, \n " +
            DB_TABLE_QUEST_COLUMN_IS_FAVORITE + " BOOLEAN, \n " +
            DB_TABLE_QUEST_COLUMN_HAS_BIND_TO_AGENT + " BOOLEAN, \n " +
            DB_TABLE_QUEST_COLUMN_DIFFICULTY_LEVEL + " CHAR);";

    private static final String DB_CREATE_TABLE_TASK = "CREATE TABLE " + DB_TABLE_TASK + " (\n " +
            DB_TABLE_TASK_COLUMN_ID + " INTEGER PRIMARY KEY, \n " +
            DB_TABLE_TASK_COLUMN_QUEST_ID + " INTEGER, \n " +
            DB_TABLE_TASK_COLUMN_TITLE + " CHAR, \n " +
            DB_TABLE_TASK_COLUMN_SHORT_DESCRIPTION + " CHAR, \n " +
            DB_TABLE_TASK_COLUMN_LONG_DESCRIPTION + " CHAR, \n " +
            DB_TABLE_TASK_COLUMN_PSK_PASS + " CHAR, \n " +
            DB_TABLE_TASK_COLUMN_PSK_UNLOCK + "  CHAR, \n " +
            DB_TABLE_TASK_COLUMN_PSK_BRONZE + " CHAR, \n " +
            DB_TABLE_TASK_COLUMN_PSK_SILVER + " CHAR, \n " +
            DB_TABLE_TASK_COLUMN_PSK_GOLD + " CHAR, \n " +
            DB_TABLE_TASK_COLUMN_IS_TASK_VISIBLE + " BOOLEAN, \n " +
            DB_TABLE_TASK_COLUMN_LATITUDE + " DOUBLE, \n " +
            DB_TABLE_TASK_COLUMN_LONGITUDE + " DOUBLE, \n " +
            DB_TABLE_TASK_COLUMN_HELP_IMG_SRC + " CHAR, \n " +
            DB_TABLE_TASK_COLUMN_SCORE_BRONZE + " INTEGER, \n " +
            DB_TABLE_TASK_COLUMN_SCORE_SILVER + " INTEGER, \n " +
            DB_TABLE_TASK_COLUMN_SCORE_GOLD + "  INTEGER, \n " +
            DB_TABLE_TASK_COLUMN_STATE + "  CHAR, \n " +
            DB_TABLE_TASK_COLUMN_HAS_BIND_TO_MAP + "  BOOLEAN, \n " +
            DB_TABLE_TASK_COLUMN_HAS_BIND_TO_AGENT + "  BOOLEAN, \n " +
            DB_TABLE_TASK_COLUMN_DIFFICULTY_LEVEL + " CHAR);\n";

    private static final String DB_CREATE_TABLE_MAPMARKER = "CREATE TABLE " + DB_TABLE_MAPMARKER + " (\n" +
            DB_TABLE_MAPMARKER_COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, \n" +
            DB_TABLE_MAPMARKER_COLUMN_TASK_ID + " INTEGER);";

    private static final String DB_DROP_TABLE_MAPMARKER = "DROP TABLE " + DB_TABLE_MAPMARKER + ";";
    private static final String DB_DROP_TABLE_QUEST = "DROP TABLE " + DB_TABLE_QUEST+ ";";
    private static final String DB_DROP_TABLE_TASK = "DROP TABLE " + DB_TABLE_TASK + ";";

    private final Context mContext;
    private DBHelper mDBHelper;
    private SQLiteDatabase mDB;
    private SQLiteCursorDriver mDriver;

    public B2RDB(Context context) {

        mContext = context;
        mDriver = null;
    }

    public void open() {
        Log.d(Constants.DEBUG, "open DB");
        mDBHelper = new DBHelper(mContext, DB_NAME, null, DB_VERSION);
        mDB = mDBHelper.getWritableDatabase();
    }

    public void close() {
        Log.d(Constants.DEBUG, "close DB");
        if (mDBHelper != null) mDBHelper.close();
    }

    public void addQuest(int questId, String title, String shortDescription, String longDescription,
                         String questLogoSrc, int progress, String imgSrc,
                         String mode, int durationTime, boolean isStarted,
                         int exp, boolean hasBindToMap, boolean isFavorite,
                         boolean hasBindToAgent, String difficultyLevel
    ) {
        ContentValues cv = new ContentValues();
        cv.put(DB_TABLE_QUEST_COLUMN_ID, questId);
        cv.put(DB_TABLE_QUEST_COLUMN_TITLE, title);
        cv.put(DB_TABLE_QUEST_COLUMN_SHORT_DESCRIPTION, shortDescription);
        cv.put(DB_TABLE_QUEST_COLUMN_LONG_DESCRIPTION, longDescription);
        cv.put(DB_TABLE_QUEST_COLUMN_QUEST_LOGO_SRC, questLogoSrc);
        cv.put(DB_TABLE_QUEST_COLUMN_PROGRESS, progress);
        cv.put(DB_TABLE_QUEST_COLUMN_IMG_SRC, imgSrc);
        cv.put(DB_TABLE_QUEST_COLUMN_MODE, mode);
        cv.put(DB_TABLE_QUEST_COLUMN_DURATION_TIME, durationTime);
        cv.put(DB_TABLE_QUEST_COLUMN_IS_STARTED, isStarted);
        cv.put(DB_TABLE_QUEST_COLUMN_EXP, exp);
        cv.put(DB_TABLE_QUEST_COLUMN_HAS_BIND_TO_MAP, hasBindToMap);
        cv.put(DB_TABLE_QUEST_COLUMN_IS_FAVORITE, isFavorite);
        cv.put(DB_TABLE_QUEST_COLUMN_HAS_BIND_TO_AGENT, hasBindToAgent);
        cv.put(DB_TABLE_QUEST_COLUMN_DIFFICULTY_LEVEL, difficultyLevel);
        mDB.insert(DB_TABLE_QUEST, null, cv);
    }

    public void addTask(int taskId, int questId, String title, String shortDescription,
                        String longDescription, String pskPass, String psksUnlock,
                        String pskBronze, String pskSilver, String pskGold,
                        String state, boolean isTaskVisible,
                        String imgSrc, String difficultyLevel,
                        boolean hasBindToMap, boolean hasBindToAgent,
                        int bronzeScore, int silverScore, int goldScore,
                        double latitude, double longitude) {
        ContentValues cv = new ContentValues();
        cv.put(DB_TABLE_TASK_COLUMN_ID, taskId);
        cv.put(DB_TABLE_TASK_COLUMN_QUEST_ID, questId);
        cv.put(DB_TABLE_TASK_COLUMN_TITLE, title);
        cv.put(DB_TABLE_TASK_COLUMN_SHORT_DESCRIPTION, shortDescription);
        cv.put(DB_TABLE_TASK_COLUMN_LONG_DESCRIPTION, longDescription);
        cv.put(DB_TABLE_TASK_COLUMN_PSK_PASS, pskPass);

        cv.put(DB_TABLE_TASK_COLUMN_PSK_UNLOCK, psksUnlock);

        cv.put(DB_TABLE_TASK_COLUMN_PSK_BRONZE, pskBronze);
        cv.put(DB_TABLE_TASK_COLUMN_PSK_SILVER, pskSilver);
        cv.put(DB_TABLE_TASK_COLUMN_PSK_GOLD, pskGold);
        cv.put(DB_TABLE_TASK_COLUMN_IS_TASK_VISIBLE, isTaskVisible);
        cv.put(DB_TABLE_TASK_COLUMN_LATITUDE, latitude);
        cv.put(DB_TABLE_TASK_COLUMN_LONGITUDE, longitude);
        cv.put(DB_TABLE_TASK_COLUMN_HELP_IMG_SRC, imgSrc);
        cv.put(DB_TABLE_TASK_COLUMN_SCORE_BRONZE, bronzeScore);
        cv.put(DB_TABLE_TASK_COLUMN_SCORE_SILVER, silverScore);
        cv.put(DB_TABLE_TASK_COLUMN_SCORE_GOLD, goldScore);
        cv.put(DB_TABLE_TASK_COLUMN_HAS_BIND_TO_MAP, hasBindToMap);
        cv.put(DB_TABLE_TASK_COLUMN_HAS_BIND_TO_AGENT, hasBindToAgent);
        cv.put(DB_TABLE_TASK_COLUMN_STATE, state);
        cv.put(DB_TABLE_TASK_COLUMN_DIFFICULTY_LEVEL, difficultyLevel);
        mDB.insert(DB_TABLE_TASK, null, cv);
    }

    public Cursor getQuests() {
        Cursor cursor = mDB.query(DB_TABLE_QUEST,
                null, null, null, null, null, null);
        return cursor;
    }

    public Cursor getTasks(Cursor questCursor) {
        return mDB.query(DB_TABLE_TASK, null, String.format("WHERE quest_id = %d", questCursor.getInt(questCursor.getColumnIndex(DB_TABLE_QUEST_COLUMN_ID))), null, null, null, null);
    }

    private class DBHelper extends SQLiteOpenHelper {

        public DBHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
            super(context, name, factory, version);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            Log.d(Constants.DEBUG, "DB create start");
            db.execSQL(DB_CREATE_TABLE_QUEST);
            db.execSQL(DB_CREATE_TABLE_TASK);
            db.execSQL(DB_CREATE_TABLE_MAPMARKER);
            Log.d(Constants.DEBUG, "DB create end");
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            Log.d(Constants.DEBUG, "DB drop start");
            db.execSQL(DB_DROP_TABLE_QUEST);
            db.execSQL(DB_DROP_TABLE_TASK);
            db.execSQL(DB_DROP_TABLE_MAPMARKER);
            Log.d(Constants.DEBUG, "DB drop end");
            onCreate(db);
        }
    }
}
