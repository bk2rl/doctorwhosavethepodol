package com.b2r.main;


import android.app.Activity;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import java.util.ArrayList;
import java.util.GregorianCalendar;

/**
 * A simple {@link Fragment} subclass.
 */
public class ComicsFragment extends Fragment {


    private OnFragmentInteractionListener mListener;
    private MainActivity mActivity;

    public ComicsFragment() {
        // Required empty public constructor
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mActivity = ((MainActivity) getActivity());
        View returnView = inflater.inflate(R.layout.comics,container,false);
        Button button = (Button) returnView.findViewById(R.id.start_button);
        if (!mActivity.getCurrentQuest().isStarted()){
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mActivity.getCurrentQuest().setIsStarted(true);
                    mActivity.getCurrentQuest().setStartTime(GregorianCalendar.getInstance().getTimeInMillis());
                    mListener.onFragmentInteraction(Constants.START_TIMER, null);
                    mListener.onFragmentInteraction(Constants.SWITCH_TO_LIST,null);
                }
            });
        } else {
            button.setVisibility(View.GONE);
        }
        return returnView;
    }


}
