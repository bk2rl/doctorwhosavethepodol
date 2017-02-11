package com.b2r.main.ui.adapter;

import android.content.Context;
import android.content.res.Resources;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.b2r.main.model.NavigationDrawerMenuItem;
import com.b2r.main.R;
import com.b2r.main.model.Task;

import java.util.ArrayList;


public class NavigationDrawerListAdapter extends BaseExpandableListAdapter {

    private final Context mContext;
    private final ArrayList<NavigationDrawerMenuItem> mNavigationDrawerMenuItems;
    private final ArrayList<Task> mTasks;
    private final Resources mResources;

    private final int mGroupLayoutId;
    private final int[] mGroupViewIds;

    private final int mChildLayout;
    private final int[] mChildLayoutIds;


    public NavigationDrawerListAdapter(Context context, ArrayList<NavigationDrawerMenuItem> navigationDrawerMenuItems, ArrayList<Task> tasks, int groupLayout, int[] groupTo, int childLayout, int[] childTo) {
        mContext = context;
        mTasks = tasks;
        mNavigationDrawerMenuItems = navigationDrawerMenuItems;
        mResources = mContext.getResources();
        mGroupLayoutId = groupLayout;
        mGroupViewIds = groupTo;
        mChildLayout = childLayout;
        mChildLayoutIds = childTo;
    }

    @Override
    public int getGroupCount() {
        return mNavigationDrawerMenuItems.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return groupPosition == 0 ? mTasks.size() : 0;
    }

    @Override
    public Object getGroup(int groupPosition) {
        return mNavigationDrawerMenuItems.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return groupPosition == 0 ? mTasks.get(childPosition) : null;
    }

    @Override
    public long getGroupId(int groupPosition) {
        return mNavigationDrawerMenuItems.get(groupPosition).hashCode();
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return groupPosition == 0 ? mTasks.get(childPosition).hashCode() : 0;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        NavigationDrawerMenuItem mNavigationDrawerMenuItem = mNavigationDrawerMenuItems.get(groupPosition);
        ViewGroupHolder viewHolder = new ViewGroupHolder();

        if (convertView == null) {
            convertView = View.inflate(mContext, mGroupLayoutId, null);
            viewHolder.title = ((TextView) convertView.findViewById(mGroupViewIds[0]));
            viewHolder.imageView = ((ImageView) convertView.findViewById(mGroupViewIds[1]));
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewGroupHolder) convertView.getTag();
        }

        viewHolder.title.setText(mNavigationDrawerMenuItem.getTitle());
        viewHolder.imageView.setImageResource(mNavigationDrawerMenuItem.getDrawableResource());

//        convertView.setBackgroundColor(mResources.getColor(R.color.active));

//        if (mTask.isCurrent()){
//            ((ExpandableListView) parent).expandGroup(groupPosition);
//        }

        return convertView;
    }

    @Override
    public View getChildView(final int groupPosition, final int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {

        if (groupPosition == 0) {
            ViewChildHolder holder;
            Task mTask = mTasks.get(childPosition);

            if ((convertView == null) || (convertView.getId() == R.id.row_null)) {
                convertView = View.inflate(mContext, mChildLayout, null);
                holder = new ViewChildHolder();
                holder.title = ((TextView) convertView.findViewById(mChildLayoutIds[0]));
                holder.pass = ((TextView) convertView.findViewById(mChildLayoutIds[1]));
                convertView.setTag(holder);
            } else {
                holder = (ViewChildHolder) convertView.getTag();
            }
            holder.title.setText(mTask.getTitle());
            StringBuffer stringBuffer = new StringBuffer();

            if (!mTask.getPskBronze().isEmpty()) {
                stringBuffer.append(mTask.getPskBronze());
                stringBuffer.append(" | ");
            }

            if (!mTask.getPskSilver().isEmpty()) {
                stringBuffer.append(mTask.getPskSilver());
                stringBuffer.append(" | ");
            }

            if (!mTask.getPskGold().isEmpty()) {
                stringBuffer.append(mTask.getPskGold());
                stringBuffer.append(" | ");
            }

            if (!mTask.getPskPass().isEmpty()) {
                stringBuffer.append(mTask.getPskPass());
            }

            holder.pass.setText(stringBuffer.toString());

            //change the background color of task in case of state

            switch (mTask.getState()) {
//                    case ACTIVE:
//                        convertView.setBackgroundColor(mResources.getColor(R.color.active));
//                        break;
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

            return convertView;
        } else return null;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

    private class ViewGroupHolder {
        public TextView title;
        public ImageView imageView;
    }

    private class ViewChildHolder {
        public TextView title;
        public TextView pass;
    }
}
