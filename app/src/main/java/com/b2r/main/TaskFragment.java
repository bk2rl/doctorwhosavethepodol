package com.b2r.main;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.b2r.main.database.TaskCursorEnvalop;


public class TaskFragment extends Fragment {

    private Task mTask;

    private OnFragmentInteractionListener mListener;
    private MainActivity mActivity;
    private int mQuestPosition;
    private int mTaskPosition;


    // TODO: Rename and change types of parameters
    public static TaskFragment newInstance(int questPosition, int taskPosition) {
        TaskFragment fragment = new TaskFragment();
        Bundle args = new Bundle();
        args.putInt(Constants.QUEST_POSITION, questPosition);
        args.putInt(Constants.TASK_POSITION, taskPosition);
        fragment.setArguments(args);
        return fragment;
    }

    public TaskFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mActivity = (MainActivity)getActivity();

        if (getArguments() != null) {
            mQuestPosition = getArguments().getInt(Constants.QUEST_POSITION);
            mTaskPosition = getArguments().getInt(Constants.TASK_POSITION);
            mTask = mActivity.getQuests().get(mQuestPosition).getTaskList().get(mTaskPosition);
        }

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View convertView = inflater.inflate(R.layout.task_layout,container,false);
        switch (mTask.getState()) {
            case ACTIVE:
                convertView.setBackgroundColor(getResources().getColor(R.color.active));
                break;
            case PASSED:
                convertView.setBackgroundColor(getResources().getColor(R.color.passed));
                break;
            case BRONZE:
                convertView.setBackgroundColor(getResources().getColor(R.color.bronze));
                break;
            case SILVER:
                convertView.setBackgroundColor(getResources().getColor(R.color.silver));
                break;
            case GOLD:
                convertView.setBackgroundColor(getResources().getColor(R.color.gold));
                break;
        }
        ((TextView)convertView.findViewById(R.id.task_title)).setText(mTask.getTitle());
        ((TextView)convertView.findViewById(R.id.task_long_description)).setText(mTask.getLongDescription());
        return convertView;
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

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }




    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        public void onFragmentInteraction(int id, Bundle args);
    }


}
