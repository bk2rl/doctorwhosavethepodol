package com.b2r.main.adapter;

import android.content.Context;
import android.content.res.Resources;
import android.database.Cursor;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.SimpleCursorTreeAdapter;
import android.widget.SimpleExpandableListAdapter;
import android.widget.TextView;

import com.b2r.main.Quest;
import com.b2r.main.R;
import com.b2r.main.Task;
import com.b2r.main.database.B2RDB;
import com.b2r.main.database.QuestCursorEnvalop;
import com.b2r.main.database.TaskCursorEnvalop;
import com.b2r.main.view.QuestTitleImageView;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


public class QuestListAdapter extends BaseExpandableListAdapter {

    private final Context mContext;
    private final ArrayList<Quest> mQuests;
    private final Resources mResources;

    private final int mGroupLayoutId;
    private final int[] mGroupViewIds;

    private final int mChildLayout;
    private final int[] mChildLayoutIds;


    public QuestListAdapter(Context context, ArrayList<Quest> questList, int groupLayout, int[] groupTo, int childLayout, int[] childTo) {
        mContext = context;
        mQuests = questList;
        mResources = mContext.getResources();
        mGroupLayoutId = groupLayout;
        mGroupViewIds = groupTo;
        mChildLayout = childLayout;
        mChildLayoutIds = childTo;
    }

    @Override
    public int getGroupCount() {
        return mQuests.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return mQuests.get(groupPosition).getTaskList().size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return mQuests.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return mQuests.get(groupPosition).getTaskList().get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return mQuests.get(groupPosition).hashCode();
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return mQuests.get(groupPosition).getTaskList().get(childPosition).hashCode();
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        Quest mQuest = mQuests.get(groupPosition);
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

        viewHolder.titleView.setText(mQuest.getTitle());
        viewHolder.aboutQuest.setText(mQuest.getShortDescription());
        viewHolder.questTitleImageView.setProgress(mQuest.getProgress());

        convertView.setBackgroundColor(mResources.getColor(R.color.active));
        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {

        ViewChildHolder holder;
        Task mTask = mQuests.get(groupPosition).getTaskList().get(childPosition);

        if (mTask.isTaskVisible()) {
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
            holder.title.setText(mTask.getTitle());
            holder.shortDescription.setText(mTask.getShortDescription());

            //change the background color of task in case of state

            switch (mTask.getState()) {
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

            if (!mTask.isHasBindToMap()) holder.mapIcon.setVisibility(View.GONE);
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
