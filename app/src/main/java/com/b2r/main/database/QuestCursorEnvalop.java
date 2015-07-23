package com.b2r.main.database;

import android.database.Cursor;

public class QuestCursorEnvalop {

    private Cursor mCursor;

    public QuestCursorEnvalop(Cursor cursor){
        mCursor = cursor;
    }

    public int getQuestId() {
        return mCursor.getInt(mCursor.getColumnIndex(B2RDB.DB_TABLE_QUEST_COLUMN_ID));
    }

    public String getQuestTitle() {
        return mCursor.getString(mCursor.getColumnIndex(B2RDB.DB_TABLE_QUEST_COLUMN_TITLE));
    }

    public String getQuestShortDescription() {
        return mCursor.getString(mCursor.getColumnIndex(B2RDB.DB_TABLE_QUEST_COLUMN_SHORT_DESCRIPTION));
    }

    public String getQuestLongDescription() {
        return mCursor.getString(mCursor.getColumnIndex(B2RDB.DB_TABLE_QUEST_COLUMN_LONG_DESCRIPTION));
    }

    public String getQuestLogoSrc() {
        return mCursor.getString(mCursor.getColumnIndex(B2RDB.DB_TABLE_QUEST_COLUMN_QUEST_LOGO_SRC));
    }

    public int getQuestProgress() {
        return mCursor.getInt(mCursor.getColumnIndex(B2RDB.DB_TABLE_QUEST_COLUMN_PROGRESS));
    }

    public String getQuestImgSrc() {
        return mCursor.getString(mCursor.getColumnIndex(B2RDB.DB_TABLE_QUEST_COLUMN_IMG_SRC));
    }

    public String getQuestColumnMode() {
        return mCursor.getString(mCursor.getColumnIndex(B2RDB.DB_TABLE_QUEST_COLUMN_MODE));
    }

    public int getQuestDurationTime() {
        return mCursor.getInt(mCursor.getColumnIndex(B2RDB.DB_TABLE_QUEST_COLUMN_DURATION_TIME));
    }

    public int getQuestColumnExp() {
        return mCursor.getInt(mCursor.getColumnIndex(B2RDB.DB_TABLE_QUEST_COLUMN_EXP));
    }

    public boolean getQuestHasBindToMap() {
        return mCursor.getInt(mCursor.getColumnIndex(B2RDB.DB_TABLE_QUEST_COLUMN_HAS_BIND_TO_MAP)) != 0;
    }

    public boolean getQuestColumnIsFavorite() {
        return mCursor.getInt(mCursor.getColumnIndex(B2RDB.DB_TABLE_QUEST_COLUMN_IS_FAVORITE)) != 0;
    }

    public boolean getQuestColumnHasBindToAgent() {
        return mCursor.getInt(mCursor.getColumnIndex(B2RDB.DB_TABLE_QUEST_COLUMN_HAS_BIND_TO_AGENT)) != 0;
    }

    public boolean getQuestColumnDifficultyLevel() {
        return mCursor.getInt(mCursor.getColumnIndex(B2RDB.DB_TABLE_QUEST_COLUMN_DIFFICULTY_LEVEL)) != 0;
    }


}
