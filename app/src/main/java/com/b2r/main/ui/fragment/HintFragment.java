package com.b2r.main.ui.fragment;


import android.app.Activity;
import android.app.Fragment;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.b2r.main.Constants;
import com.b2r.main.ui.activity.MainActivity;
import com.b2r.main.R;
import com.b2r.main.ui.adapter.ImageViewPageAdapter;

import java.util.ArrayList;
import java.util.GregorianCalendar;

public class HintFragment extends Fragment {


    private OnFragmentInteractionListener mListener;
    private MainActivity mActivity;
    private ArrayList<Drawable> hints;
    private ViewPager hintPager;
    private TabLayout tabLayout;

    public HintFragment() {
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
        View returnView = inflater.inflate(R.layout.hint,container,false);

        mActivity.getSupportActionBar().hide();

        hints = new ArrayList<Drawable>();
        hints.add(mActivity.getResources().getDrawable(R.drawable.dw_uc_comics_01));
        hints.add(mActivity.getResources().getDrawable(R.drawable.dw_uc_comics_02));
        hints.add(mActivity.getResources().getDrawable(R.drawable.dw_uc_comics_03));

        hintPager = (ViewPager) returnView.findViewById(R.id.hint_pager);
        tabLayout = (TabLayout) returnView.findViewById(R.id.tabDots);
        tabLayout.setupWithViewPager(hintPager);
        hintPager.setAdapter(new ImageViewPageAdapter(getActivity(), hints));

        Button button = (Button) returnView.findViewById(R.id.start_button);
        if (!mActivity.getCurrentQuest().isStarted()){
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mActivity.getCurrentQuest().setQuestStarted(true);
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

    @Override
    public void onDestroyView() {
        mActivity.getSupportActionBar().show();
        super.onDestroyView();
    }
}
