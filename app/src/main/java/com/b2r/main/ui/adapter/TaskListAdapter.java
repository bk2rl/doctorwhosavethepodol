package com.b2r.main.ui.adapter;

import android.content.res.Resources;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.b2r.main.Constants;
import com.b2r.main.R;
import com.b2r.main.model.Task;
import com.b2r.main.ui.activity.MainActivity;

import java.util.ArrayList;


public class TaskListAdapter extends RecyclerView.Adapter {

    private final MainActivity mActivity;
    private final Resources mResources;

    private final int mQuestNumber;
    private final ArrayList<Task> mTaskLists;
    private final int mChildLayout;
    private final int[] mChildLayoutIds;


    public TaskListAdapter(MainActivity activity, int questNumber, ArrayList<Task> taskList, int childLayout, int[] childTo) {
        mActivity = activity;
        mResources = mActivity.getResources();
        mQuestNumber = questNumber;
        mTaskLists = taskList;
        mChildLayout = childLayout;
        mChildLayoutIds = childTo;
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewTaskHolder(LayoutInflater.from(mActivity).inflate(mChildLayout, parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, final int position) {
        Task mTask = mTaskLists.get(position);
        ViewTaskHolder holder = (ViewTaskHolder) viewHolder;
        if (mTask.isTaskVisible()) {

//            holder.rootView.setLayoutParams(defaultLayoutParams);

            holder.title.setText(mTask.getTitle());
            holder.shortDescription.setText(mTask.getShortDescription());

            //change the background color of task in case of state

            switch (mTask.getState()) {
                case ACTIVE:
                    holder.rootView.setBackgroundColor(mResources.getColor(R.color.active));
                    break;
                case PASSED:
                    holder.rootView.setBackgroundColor(mResources.getColor(R.color.passed));
                    break;
                case BRONZE:
                    holder.rootView.setBackgroundColor(mResources.getColor(R.color.bronze));
                    break;
                case SILVER:
                    holder.rootView.setBackgroundColor(mResources.getColor(R.color.silver));
                    break;
                case GOLD:
                    holder.rootView.setBackgroundColor(mResources.getColor(R.color.gold));
                    break;
            }

            if (!mTask.isHasBindToMap()) holder.mapIcon.setVisibility(View.GONE);
            else {
                holder.mapIcon.setVisibility(View.VISIBLE);
                holder.mapIcon.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Bundle args = new Bundle();
                        args.putInt(Constants.QUEST_POSITION, mQuestNumber);
                        args.putInt(Constants.TASK_POSITION, position);
                        mActivity.onFragmentInteraction(Constants.SWITCH_TO_MAP, args);
                    }
                });
            }

            holder.rootView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Bundle args = new Bundle();
                    args.putInt(Constants.QUEST_POSITION, mQuestNumber);
                    args.putInt(Constants.TASK_POSITION, position);
                    mActivity.onFragmentInteraction(Constants.SWITCH_TO_TASK, args);
                }
            });
        } else {
            holder.rootView.setLayoutParams(new ViewGroup.LayoutParams(0, 0));
            holder.rootView.setVisibility(View.GONE);
        }

    }

    @Override
    public int getItemCount() {
        return mTaskLists.size();
    }

    private class ViewTaskHolder extends RecyclerView.ViewHolder {
        public View rootView;
        public TextView title;
        public TextView shortDescription;
        public View mapIcon;

        public ViewTaskHolder(View rootView) {
            super(rootView);
            this.rootView = rootView;
            this.shortDescription = ((TextView) rootView.findViewById(mChildLayoutIds[1]));
            this.title = ((TextView) rootView.findViewById(mChildLayoutIds[0]));
            this.mapIcon = rootView.findViewById(mChildLayoutIds[2]);
        }
    }

}
