package com.b2r.main.database;

import android.database.Cursor;

public class TaskCursorEnvalop {

    private Cursor mCursor;

    public TaskCursorEnvalop(Cursor mCursor) {
        this.mCursor = mCursor;
    }

    public int getTaskId() {
        return mCursor.getInt(mCursor.getColumnIndex(B2RDB.DB_TABLE_TASK_COLUMN_ID));
    }

    public int getQuestId() {
        return mCursor.getInt(mCursor.getColumnIndex(B2RDB.DB_TABLE_TASK_COLUMN_QUEST_ID));
    }

    public String getTaskTitle() {
        return mCursor.getString(mCursor.getColumnIndex(B2RDB.DB_TABLE_TASK_COLUMN_TITLE));
    }

    public String getTaskShortDescription() {
        return mCursor.getString(mCursor.getColumnIndex(B2RDB.DB_TABLE_TASK_COLUMN_SHORT_DESCRIPTION));
    }

    public String getTaskLongDescription() {
        return mCursor.getString(mCursor.getColumnIndex(B2RDB.DB_TABLE_TASK_COLUMN_LONG_DESCRIPTION));
    }

    public String getTaskPskPass() {
        return mCursor.getString(mCursor.getColumnIndex(B2RDB.DB_TABLE_TASK_COLUMN_PSK_PASS));
    }

    public String getTaskPskUnlock() {
        return mCursor.getString(mCursor.getColumnIndex(B2RDB.DB_TABLE_TASK_COLUMN_PSK_UNLOCK));
    }

    public String getTaskPskBronze() {
        return mCursor.getString(mCursor.getColumnIndex(B2RDB.DB_TABLE_TASK_COLUMN_PSK_BRONZE));
    }

    public String getTaskPskSilver() {
        return mCursor.getString(mCursor.getColumnIndex(B2RDB.DB_TABLE_TASK_COLUMN_PSK_SILVER));
    }

    public String getTaskPskGold() {
        return mCursor.getString(mCursor.getColumnIndex(B2RDB.DB_TABLE_TASK_COLUMN_PSK_GOLD));
    }

    public boolean getTaskIsTaskVisible() {
        return mCursor.getInt(mCursor.getColumnIndex(B2RDB.DB_TABLE_TASK_COLUMN_IS_TASK_VISIBLE)) != 0;
    }

    public double getTaskLatitude() {
        return mCursor.getDouble(mCursor.getColumnIndex(B2RDB.DB_TABLE_TASK_COLUMN_LATITUDE));
    }

    public double getTaskLongitude() {
        return mCursor.getDouble(mCursor.getColumnIndex(B2RDB.DB_TABLE_TASK_COLUMN_LATITUDE));
    }

    public String getTaskImgSrc() {
        return mCursor.getString(mCursor.getColumnIndex(B2RDB.DB_TABLE_TASK_COLUMN_HELP_IMG_SRC));
    }

    public int getTaskScoreBronze() {
        return mCursor.getInt(mCursor.getColumnIndex(B2RDB.DB_TABLE_TASK_COLUMN_SCORE_BRONZE));
    }

    public int getTaskScoreSilver() {
        return mCursor.getInt(mCursor.getColumnIndex(B2RDB.DB_TABLE_TASK_COLUMN_SCORE_SILVER));
    }

    public int getTaskScoreGold() {
        return mCursor.getInt(mCursor.getColumnIndex(B2RDB.DB_TABLE_TASK_COLUMN_SCORE_GOLD));
    }

    public String getTaskState() {
        return mCursor.getString(mCursor.getColumnIndex(B2RDB.DB_TABLE_TASK_COLUMN_STATE));
    }

    public boolean getTaskHasBindToMap() {
        return mCursor.getInt(mCursor.getColumnIndex(B2RDB.DB_TABLE_TASK_COLUMN_HAS_BIND_TO_MAP)) != 0;
    }

    public boolean getTaskHasBindToAgent() {
        return mCursor.getInt(mCursor.getColumnIndex(B2RDB.DB_TABLE_TASK_COLUMN_HAS_BIND_TO_AGENT)) != 0;
    }

    public String getTaskDifficultyLevel() {
        return mCursor.getString(mCursor.getColumnIndex(B2RDB.DB_TABLE_TASK_COLUMN_DIFFICULTY_LEVEL));
    }

}
