package com.b2r.main;

import android.widget.TextView;

import java.util.GregorianCalendar;
import java.util.concurrent.TimeUnit;

public class Timer extends Thread {

    MainActivity mActivity;
    long mStartTime;
    long mDurationTime;
    TextView mTimeView;

    public Timer(MainActivity activity, long startTime, long durationTime, TextView timeView) {
        mActivity = activity;
        mStartTime = startTime;
        mDurationTime = durationTime;
        mTimeView = timeView;
    }

    @Override
    public void run() {
        while(true) {
            final long currentTime = GregorianCalendar.getInstance().getTimeInMillis();
            final long timeLeft = mDurationTime - currentTime - mStartTime;
            final long seconds = TimeUnit.MILLISECONDS.toSeconds(timeLeft);
            final long minutes = TimeUnit.MILLISECONDS.toMinutes(timeLeft);
            final long hours = TimeUnit.MILLISECONDS.toHours(timeLeft);
            mActivity.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    mTimeView.setText(String.format("%d:%d:%d", hours, minutes, seconds));
                }
            });
        }
    }
}
