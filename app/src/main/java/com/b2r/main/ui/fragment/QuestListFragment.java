package com.b2r.main.ui.fragment;

import android.animation.StateListAnimator;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Fragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.b2r.main.Constants;
import com.b2r.main.R;
import com.b2r.main.model.Quest;
import com.b2r.main.model.Task;
import com.b2r.main.ui.SwipeDetector;
import com.b2r.main.ui.activity.MainActivity;
import com.b2r.main.ui.adapter.QuestListAdapter;

import java.util.ArrayList;

public class QuestListFragment extends Fragment implements View.OnClickListener,
        DialogInterface.OnClickListener {

    private OnFragmentInteractionListener mListener;
    private QuestListAdapter mAdapter;
    private EditText pskInput;
    private RecyclerView questListView;
    private SwipeDetector mSwipeDetector;
    private MainActivity mActivity;

    public static QuestListFragment newInstance() {
        QuestListFragment fragment = new QuestListFragment();
        return fragment;
    }

    public QuestListFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        questListView = new RecyclerView(getActivity());
        questListView.setLayoutParams(new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        questListView.setLayoutManager(new LinearLayoutManager(mActivity));

        mActivity = (MainActivity) getActivity();
        mAdapter = new QuestListAdapter(mActivity, mActivity.getQuests(),
                R.layout.quest_item, new int[]{R.id.quest_item_head_text, R.id.quest_item_secondary_text, R.id.quest_title_image, R.id.task_list},
                R.layout.task_item, new int[]{R.id.task_item_head_text, R.id.task_item_secondary_text, R.id.map_button, R.id.hint});

        mActivity.getFloatingActionButton().setVisibility(View.VISIBLE);
        mActivity.getFloatingActionButton().setImageDrawable(getResources().getDrawable(R.drawable.key));
        mActivity.getFloatingActionButton().setSize(FloatingActionButton.SIZE_NORMAL);
        mActivity.getFloatingActionButton().setOnClickListener(this);

        mSwipeDetector = new SwipeDetector();
        return questListView;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (OnFragmentInteractionListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    public interface OnFragmentInteractionListener {
        public void onFragmentInteraction(int id, Bundle args);

        public boolean onFragmentInteraction(int id);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        questListView.setAdapter((QuestListAdapter) null);
//        questListView.addFooterView(mFooter);
    }

    @Override
    public void onStart() {
        questListView.setAdapter(mAdapter);
        questListView.setOnTouchListener(mSwipeDetector);
//        questListView.setOnChildClickListener(this);
//        questListView.setOnGroupClickListener(this);
        super.onStart();
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mActivity.getFloatingActionButton().setVisibility(View.GONE);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.floating_action_button) {
            if (!mActivity.getCurrentQuest().isEnded()) {
                AlertDialog.Builder alertDialog = new AlertDialog.Builder(getActivity());
                alertDialog.setTitle("PASSWORD");
                alertDialog.setMessage("Enter Password");
                pskInput = new EditText(getActivity());
                LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.MATCH_PARENT);
                pskInput.setLayoutParams(lp);
                alertDialog.setView(pskInput);
                alertDialog.setIcon(R.drawable.blue_key);
                alertDialog.setPositiveButton("YES", this).setNegativeButton("NO", this);
                alertDialog.show();
            }
        }
    }


    @Override
    public void onClick(DialogInterface dialog, int which) {
        ArrayList<Quest> mQuests = mActivity.getQuests();
        if (DialogInterface.BUTTON_NEGATIVE == which) {
            dialog.cancel();
        } else if (DialogInterface.BUTTON_POSITIVE == which) {
            String password = pskInput.getText().toString();
            if (password.compareTo("") != 0) {
                final Quest mQuest = mActivity.getCurrentQuest();
                ArrayList<Task> mTasks = mQuest.getTaskList();
                final int taskCount = mTasks.size();
                for (int j = 0; j < taskCount; j++) {
                    final Task mTask = mTasks.get(j);
                    if (mTask.getPskPass().equals(password)) {
                        if (mTask.getState() == Task.State.ACTIVE) {
                            updateQuest(Task.State.PASSED, mQuest, mTask, taskCount);
                        } else {
                            showToastMessage("Passed key was already putted");
                        }
                    } else if (mTask.getPsksUnlock().contains(password)) {
                        mTask.setTaskVisible(true);
                        mAdapter.notifyDataSetChanged();
                    } else if (mTask.getPskBronze().equals(password)) {
                        if (mTask.getState() == Task.State.ACTIVE) {
                            updateQuest(Task.State.BRONZE, mQuest, mTask, taskCount);
                        } else {
                            showToastMessage("Bronze key was already putted");
                        }
                    } else if (mTask.getPskSilver().equals(password)) {
                        if (mTask.getState() == Task.State.ACTIVE) {
                            updateQuest(Task.State.SILVER, mQuest, mTask, taskCount);
                        } else {
                            showToastMessage("Silver key was already putted");
                        }
                    } else if (mTask.getPskGold().equals(password)) {
                        if (mTask.getState() == Task.State.ACTIVE) {
                            updateQuest(Task.State.GOLD, mQuest, mTask, taskCount);
                        } else {
                            showToastMessage("Gold key was already putted");
                        }
                    }
                }

                if (mQuest.getProgress() >= (360 / taskCount) * taskCount) {
                    mQuest.setProgress(360);
                    mAdapter.notifyDataSetChanged();
                    mListener.onFragmentInteraction(Constants.CHANGE_FOOTER_TO_END_STATE);
                    mListener.onFragmentInteraction(Constants.STOP_TIMER, null);
                }

                if (mQuest.getPskAddTime().equals(password) && !mQuest.isTimeAdded()) {
                    mQuest.setDurationTime(mQuest.getDurationTime() + mQuest.getAddTime());
                    mQuest.setIsTimeAdded(true);
                    mListener.onFragmentInteraction(Constants.START_TIMER, null);
                }
            }
        }
    }

    private void showToastMessage(String text) {
        Toast.makeText(mActivity, text, Toast.LENGTH_SHORT).show();
    }

    private void updateQuest(Task.State state, Quest mQuest, Task mTask, int taskCount) {
        mTask.setState(state);
        switch (state) {
            case GOLD:
                mQuest.addScore(mTask.getGoldScore());
                break;
            case SILVER:
                mQuest.addScore(mTask.getSilverScore());
                break;
            case BRONZE:
                mQuest.addScore(mTask.getBronzeScore());
                break;
        }
        mActivity.getScoreView().setText(String.valueOf(mQuest.getScore()));
        mQuest.addProgress(360 / taskCount);
        mAdapter.notifyDataSetChanged();
    }


}
