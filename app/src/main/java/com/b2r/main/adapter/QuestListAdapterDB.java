package com.b2r.main.adapter;

import android.content.Context;
import android.content.res.Resources;
import android.database.Cursor;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SimpleCursorTreeAdapter;
import android.widget.TextView;

import com.b2r.main.Task;
import com.b2r.main.database.B2RDB;
import com.b2r.main.R;
import com.b2r.main.database.QuestCursorEnvalop;
import com.b2r.main.database.TaskCursorEnvalop;
import com.b2r.main.view.QuestTitleImageView;


public class QuestListAdapterDB extends SimpleCursorTreeAdapter {

    private final Context mContext;
    private final B2RDB mDB;
    private final Resources mResources;

    private final int mGroupLayoutId;
    private final int[] mGroupViewIds;

    private final int mChildLayout;
    private final int[] mChildLayoutIds;


    public QuestListAdapterDB(Context context, B2RDB db, Cursor cursor, int groupLayout, int[] groupTo, int childLayout, int[] childTo) {
        super(context, cursor, groupLayout, null, groupTo, childLayout, null, childTo);
        mContext = context;
        mDB = db;
        mResources = mContext.getResources();
        mGroupLayoutId = groupLayout;
        mGroupViewIds = groupTo;
        mChildLayout = childLayout;
        mChildLayoutIds = childTo;
    }

    @Override
    protected Cursor getChildrenCursor(Cursor groupCursor) {
        return mDB.getTasks(groupCursor);
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        QuestCursorEnvalop mQuestCursor = new QuestCursorEnvalop(getCursor());
        ViewGroupHolder viewHolder = new ViewGroupHolder();

        if (convertView == null) {
            convertView = View.inflate(mContext, mGroupLayoutId, null);
            viewHolder.titleView = ((TextView) convertView.findViewById(mGroupViewIds[0]));
            viewHolder.aboutQuest = ((TextView) convertView.findViewById(mGroupViewIds[1]));
            viewHolder.questTitleImageView = (QuestTitleImageView) convertView.findViewById(mGroupViewIds[2]);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewGroupHolder)convertView.getTag();
        }

        viewHolder.titleView.setText(mQuestCursor.getQuestTitle());
        viewHolder.aboutQuest.setText(mQuestCursor.getQuestShortDescription());
        viewHolder.questTitleImageView.setProgress(mQuestCursor.getQuestProgress());

        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {

        ViewChildHolder holder;
        TaskCursorEnvalop mTaskCursor = new TaskCursorEnvalop(getChildrenCursor(getCursor()));

        if (mTaskCursor.getTaskIsTaskVisible()) {
            if ((convertView == null) || (convertView.getId() == R.id.row_null)) {
                convertView = View.inflate(mContext, mChildLayout, null);
                holder = new ViewChildHolder();
                holder.shortDescription = ((TextView) convertView.findViewById(mChildLayoutIds[1]));
                holder.title = ((TextView) convertView.findViewById(mChildLayoutIds[0]));
                holder.mapIcon = convertView.findViewById(mChildLayoutIds[2]);
                convertView.setTag(holder);
            } else {
                holder = (ViewChildHolder) convertView.getTag();
            }
            holder.title.setText(mTaskCursor.getTaskTitle());
            holder.shortDescription.setText(mTaskCursor.getTaskShortDescription());

            //change the background color of task in case of state

            switch (Task.State.valueOf(mTaskCursor.getTaskState())) {
                case ACTIVE:
                    convertView.setBackgroundColor(mResources.getColor(R.color.active));
                    break;
                case PASSED:
                    convertView.setBackgroundColor(mResources.getColor(R.color.passed));
                    break;
                case BRONZE:
                    convertView.setBackgroundColor(mResources.getColor(R.color.bronze));
                    break;
                case SILVER:
                    convertView.setBackgroundColor(mResources.getColor(R.color.silver));
                    break;
                case GOLD:
                    convertView.setBackgroundColor(mResources.getColor(R.color.gold));
                    break;
            }

            if (mTaskCursor.getTaskHasBindToMap()) holder.mapIcon.setVisibility(View.GONE);
            else holder.mapIcon.setVisibility(View.VISIBLE);
        } else {
            convertView = View.inflate(mContext, R.layout.row_null, null);
        }
        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

    private class ViewGroupHolder {
        public TextView titleView;
        public TextView aboutQuest;
        public QuestTitleImageView questTitleImageView;
    }

    private class ViewChildHolder {
        public TextView title;
        public TextView shortDescription;
        public View mapIcon;
    }
}
