package com.b2r.main;

import android.widget.TextView;

import java.util.GregorianCalendar;
import java.util.concurrent.TimeUnit;

public class Timer extends Thread {

    private MainActivity mActivity;
    private long mStartTime;
    private long mDurationTime;
    private TextView mTimeView;
    private long prevSec;

    public Timer(MainActivity activity, long startTime, long durationTime, TextView timeView) {
        mActivity = activity;
        mStartTime = startTime;
        mDurationTime = durationTime;
        mTimeView = timeView;
        prevSec = TimeUnit.MILLISECONDS.toSeconds(mDurationTime - (GregorianCalendar.getInstance().getTimeInMillis() - mStartTime)) % 60;
    }

    @Override
    public void run() {
        if (!mActivity.getCurrentQuest().isEnded()) {
            while (true) {
                if (isInterrupted()) {
                    return;
                } else {
                    final long currentTime = GregorianCalendar.getInstance().getTimeInMillis();
                    final long timeLeft = mDurationTime - (currentTime - mStartTime);
                    final long seconds = TimeUnit.MILLISECONDS.toSeconds(timeLeft) % 60;
                    final long minutes = TimeUnit.MILLISECONDS.toMinutes(timeLeft) % 60;
                    final long hours = TimeUnit.MILLISECONDS.toHours(timeLeft) % 24;
                    if (timeLeft < 0) {
                        mActivity.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                mTimeView.setText(String.format("%d:%02d:%02d", 0, 0, 0));
                            }
                        });
                        if (!mActivity.getCurrentQuest().isEnded()) {
                            boolean mReturn = false;
                            while (!mReturn) {
                                mReturn = mActivity.onFragmentInteraction(Constants.CHANGE_FOOTER_TO_END_STATE);
                            }
                        }
                        return;
                    } else {
                        if (prevSec != seconds) {
                            mActivity.runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    mTimeView.setText(String.format("%d:%02d:%02d", hours, minutes, seconds));
                                }
                            });
                            prevSec = seconds;
                        }
                    }
                }
            }
        }
    }
}
