package com.b2r.main.ui.adapter;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.view.animation.FastOutSlowInInterpolator;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.TextView;

import com.b2r.main.Constants;
import com.b2r.main.R;
import com.b2r.main.model.Task;
import com.b2r.main.ui.activity.MainActivity;
import com.bumptech.glide.Glide;

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
        final ViewTaskHolder holder = (ViewTaskHolder) viewHolder;
        if (mTask.isTaskVisible()) {

//            holder.rootView.setLayoutParams(defaultLayoutParams);

            holder.title.setText(mTask.getTitle());
            holder.shortDescription.setText(mTask.getShortDescription());
            holder.longDescription.setText(mTask.getLongDescription());

            //change the background color of task_in in case of state

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

//            holder.longDescription.setVisibility(View.VISIBLE);
            holder.rootView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                @Override
                public void onGlobalLayout() {
//                    holder.longDescription.setVisibility(View.GONE);
                    holder.fullHeight = holder.rootView.getMeasuredHeight();
                    holder.smallHeight = holder.fullHeight - holder.longDescription.getMeasuredHeight();
                    new LayoutParamsAdapter(holder.rootView).setHeight(holder.smallHeight);
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                        holder.rootView.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                    } else {
                        holder.rootView.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                    }
                }
            });

            holder.rootView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    if (!holder.isOpened) {

                        ObjectAnimator heightAnimator = ObjectAnimator.ofInt(new LayoutParamsAdapter(holder.rootView),
                                "height", holder.smallHeight, holder.fullHeight);
                        ObjectAnimator alphaAnimator = ObjectAnimator.ofFloat(holder.longDescription,View.ALPHA,0,1);
                        AnimatorSet animatorSet = new AnimatorSet();
                        animatorSet.playTogether(heightAnimator,alphaAnimator);
                        animatorSet.setDuration(300);
                        animatorSet.setInterpolator(new FastOutSlowInInterpolator());
                        animatorSet.addListener(new Animator.AnimatorListener() {
                            @Override
                            public void onAnimationStart(Animator animator) {

                            }

                            @Override
                            public void onAnimationEnd(Animator animator) {
                                holder.isOpened = true;
//                                holder.longDescription.setVisibility(View.VISIBLE);
                            }

                            @Override
                            public void onAnimationCancel(Animator animator) {

                            }

                            @Override
                            public void onAnimationRepeat(Animator animator) {

                            }
                        });
                        animatorSet.start();
//                    Bundle args = new Bundle();
//                    args.putInt(Constants.QUEST_POSITION, mQuestNumber);
//                    args.putInt(Constants.TASK_POSITION, position);
//                    mActivity.onFragmentInteraction(Constants.SWITCH_TO_TASK, args);
                    } else {
                        ObjectAnimator heightAnimator = ObjectAnimator.ofInt(new LayoutParamsAdapter(holder.rootView),
                                "height", holder.fullHeight, holder.smallHeight);
                        ObjectAnimator alphaAnimator = ObjectAnimator.ofFloat(holder.longDescription,View.ALPHA,1,0);
                        AnimatorSet animatorSet = new AnimatorSet();
                        animatorSet.playTogether(heightAnimator,alphaAnimator);
                        animatorSet.setDuration(300);
                        animatorSet.setInterpolator(new FastOutSlowInInterpolator());
                        animatorSet.addListener(new Animator.AnimatorListener() {
                            @Override
                            public void onAnimationStart(Animator animator) {

                            }

                            @Override
                            public void onAnimationEnd(Animator animator) {
//                                holder.longDescription.setVisibility(View.GONE);
                                holder.isOpened = false;

                            }

                            @Override
                            public void onAnimationCancel(Animator animator) {

                            }

                            @Override
                            public void onAnimationRepeat(Animator animator) {

                            }
                        });
                        animatorSet.start();
                    }
                }
            });

            Glide.with(mActivity).load(Uri.parse("file:///android_asset/help/" + mTask.getImgSrc())).asBitmap().into(holder.hint);

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
        public TextView longDescription;
        public View mapIcon;
        public ImageView hint;
        public boolean isOpened;
        public int smallHeight;
        public int fullHeight;

        public ViewTaskHolder(View rootView) {
            super(rootView);
            this.rootView = rootView;
            this.shortDescription = ((TextView) rootView.findViewById(mChildLayoutIds[1]));
            this.title = ((TextView) rootView.findViewById(mChildLayoutIds[0]));
            this.mapIcon = rootView.findViewById(mChildLayoutIds[2]);
            this.hint = ((ImageView) rootView.findViewById(mChildLayoutIds[3]));
            this.longDescription = (TextView) rootView.findViewById(R.id.task_long_description);
            isOpened = false;
        }
    }

    private class LayoutParamsAdapter extends View {
//        public int width;
//        public int height;

        private ViewGroup.LayoutParams layoutParams;
        private View view;

        public LayoutParamsAdapter(View view) {
            super(view.getContext());
            this.view = view;
            this.layoutParams = view.getLayoutParams();
        }

        public void setWidth(int width) {
            layoutParams.width = width;
            view.setLayoutParams(layoutParams);
        }

        public void setHeight(int height) {
            layoutParams.height = height;
            view.setLayoutParams(layoutParams);
        }

    }

}
