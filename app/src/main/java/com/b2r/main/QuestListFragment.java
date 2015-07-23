package com.b2r.main;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Fragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.LinearLayout;

import com.b2r.main.adapter.QuestListAdapter;
import com.b2r.main.adapter.QuestListAdapterDB;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link QuestListFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link QuestListFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class QuestListFragment extends Fragment implements View.OnClickListener,
        DialogInterface.OnClickListener, ExpandableListView.OnChildClickListener, ExpandableListView.OnGroupClickListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;
    private QuestListAdapter mAdapter;
    private EditText pskInput;
    private ExpandableListView questListView;
    private View mFooter;
    private SwipeDetector mSwipeDetector;
    private MainActivity mActivity;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment QuestListFragment.
     */

    // TODO: Rename and change types and number of parameters
    public static QuestListFragment newInstance(String param1, String param2) {
        QuestListFragment fragment = new QuestListFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public QuestListFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        questListView = new ExpandableListView(getActivity());

        mActivity = (MainActivity)getActivity();
        mAdapter = new QuestListAdapter(mActivity, mActivity.getQuests(),
                R.layout.quest_item, new int[]{R.id.quest_item_head_text, R.id.quest_item_secondary_text, R.id.quest_title_image},
                R.layout.task_item, new int[]{R.id.task_item_head_text, R.id.task_item_secondary_text, R.id.map_button});
        mFooter = View.inflate(getActivity(), R.layout.task_list_footer, null);
        mFooter.findViewById(R.id.enter_code_button).setOnClickListener(this);
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
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        questListView.setAdapter((ExpandableListAdapter) null);
        questListView.addFooterView(mFooter);
    }

    @Override
    public void onStart() {
        questListView.setAdapter(mAdapter);
        questListView.setOnTouchListener(mSwipeDetector);
        questListView.setOnChildClickListener(this);
        questListView.setOnGroupClickListener(this);
        super.onStart();
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }


    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.enter_code_button) {
            AlertDialog.Builder alertDialog = new AlertDialog.Builder(getActivity());
            alertDialog.setTitle("PASSWORD");
            alertDialog.setMessage("Enter Password");

            pskInput = new EditText(getActivity());
            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.MATCH_PARENT);
            pskInput.setLayoutParams(lp);
            alertDialog.setView(pskInput);
            alertDialog.setIcon(R.drawable.dw_key_1_q48);
            alertDialog.setPositiveButton("YES", this).setNegativeButton("NO", this);
            alertDialog.show();
        }
    }

    @Override
    public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
        if (mSwipeDetector.swipeDetected()){
            if(mSwipeDetector.getAction() == SwipeDetector.Action.RL) {
                Bundle args = new Bundle();
                args.putInt(Constants.QUEST_POSITION,groupPosition);
                args.putInt(Constants.TASK_POSITION,childPosition);
                mListener.onFragmentInteraction(Constants.SWITCH_TO_MAP,args);
                return true;
            } if(mSwipeDetector.getAction() == SwipeDetector.Action.LR) {
                Bundle args = new Bundle();
                args.putInt(Constants.QUEST_POSITION,groupPosition);
                args.putInt(Constants.TASK_POSITION,childPosition);
                mListener.onFragmentInteraction(Constants.SWITCH_TO_TASK,args);
                return true;
            }  if(mSwipeDetector.getAction() == SwipeDetector.Action.TB) {
                return false;
            }  if(mSwipeDetector.getAction() == SwipeDetector.Action.BT) {
                return false;
            }
        }
        return false;
    }

    @Override
    public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {
        return false;
    }

    @Override
    public void onClick(DialogInterface dialog, int which) {
        ArrayList<Quest> mQuests = mActivity.getQuests();
        if (DialogInterface.BUTTON_NEGATIVE == which) {
            dialog.cancel();
        } else if (DialogInterface.BUTTON_POSITIVE == which) {
            String password = pskInput.getText().toString();
            if (password.compareTo("") != 0) {
                for (int i = 0; i < mQuests.size(); i++) {
                    final Quest mQuest = mQuests.get(i);
                    ArrayList<Task> mTasks = mQuest.getTaskList();
                    for (int j = 0; j < mTasks.size(); j++) {
                        final Task mTask = mTasks.get(j);
                        if (mTask.getPskPass().equals(password)) {
                            mTask.setState(Task.State.PASSED);
                            mAdapter.notifyDataSetChanged();
                        } else if (mTask.getPsksUnlock().contains(password)) {
                            mTask.setTaskVisible(true);
                            mAdapter.notifyDataSetChanged();
                        } else if (mTask.getPskBronze().equals(password)) {
                            mTask.setState(Task.State.BRONZE);
                            mQuest.addScore(mTask.getBronzeScore());
                            mActivity.getScoreView().setText(Integer.toString(mQuest.getScore()));
                            mQuest.addProgress(360 / mQuests.size());
                            mAdapter.notifyDataSetChanged();
                        } else if (mTask.getPskSilver().equals(password)) {
                            mTask.setState(Task.State.SILVER);
                            mQuest.addScore(mTask.getSilverScore());
                            mActivity.getScoreView().setText(Integer.toString(mQuest.getScore()));
                            mQuest.addProgress(360 / mQuests.size());
                            mAdapter.notifyDataSetChanged();
                        } else if (mTask.getPskGold().equals(password)) {
                            mTask.setState(Task.State.GOLD);
                            mQuest.addScore(mTask.getGoldScore());
                            mActivity.getScoreView().setText(Integer.toString(mQuest.getScore()));
                            mQuest.addProgress(360 / mQuests.size());
                            mAdapter.notifyDataSetChanged();
                        }
                    }
                }
            }
        }
    }


}
