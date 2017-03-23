package com.b2r.main.ui.adapter;

import android.content.Context;
import android.content.res.Resources;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.b2r.main.R;
import com.b2r.main.model.Quest;
import com.b2r.main.ui.activity.MainActivity;
import com.b2r.main.ui.view.QuestTitleImageView;

import java.util.ArrayList;


public class QuestListAdapter extends RecyclerView.Adapter {

    private final MainActivity mActivity;
    private final ArrayList<Quest> mQuests;
    private final Resources mResources;

    private final int mGroupLayoutId;
    private final int[] mGroupViewIds;

    private final int mChildLayout;
    private final int[] mChildLayoutIds;


    public QuestListAdapter(MainActivity activity, ArrayList<Quest> questList, int groupLayout, int[] groupTo, int childLayout, int[] childTo) {
        mActivity = activity;
        mQuests = questList;
        mResources = mActivity.getResources();
        mGroupLayoutId = groupLayout;
        mGroupViewIds = groupTo;
        mChildLayout = childLayout;
        mChildLayoutIds = childTo;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewQuestHolder(LayoutInflater.from(mActivity).inflate(mGroupLayoutId, parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        Quest mQuest = mQuests.get(position);

        ((ViewQuestHolder) holder).titleView.setText(mQuest.getTitle());
        ((ViewQuestHolder) holder).aboutQuest.setText(mQuest.getShortDescription());
        ((ViewQuestHolder) holder).questTitleImageView.setProgress(mQuest.getProgress());

        ((ViewQuestHolder) holder).taskRecyclerView.setAdapter(new TaskListAdapter(mActivity,position,mQuest.getTaskList(),mChildLayout,mChildLayoutIds,((ViewQuestHolder) holder).taskRecyclerView));
    }


    @Override
    public int getItemCount() {
        return mQuests.size();
    }

    private class ViewQuestHolder extends RecyclerView.ViewHolder {
        private final RecyclerView taskRecyclerView;
        public final View rootView;
        public final TextView titleView;
        public final TextView aboutQuest;
        public final QuestTitleImageView questTitleImageView;

        public ViewQuestHolder(View rootView) {
            super(rootView);
            this.rootView = rootView;
            this.titleView = ((TextView) rootView.findViewById(mGroupViewIds[0]));
            this.aboutQuest = ((TextView) rootView.findViewById(mGroupViewIds[1]));
            this.questTitleImageView = (QuestTitleImageView) rootView.findViewById(mGroupViewIds[2]);
            this.taskRecyclerView = (RecyclerView) rootView.findViewById(mGroupViewIds[3]);
            this.taskRecyclerView.setLayoutManager(new LinearLayoutManager(mActivity));
        }
    }
}
