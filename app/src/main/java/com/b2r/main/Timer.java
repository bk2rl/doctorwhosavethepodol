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
            final long timeLeft = mDurationTime - (currentTime - mStartTime);
            final long seconds = TimeUnit.MILLISECONDS.toSeconds(timeLeft) % 60;
            final long minutes = TimeUnit.MILLISECONDS.toMinutes(timeLeft) % 60;
            final long hours = TimeUnit.MILLISECONDS.toHours(timeLeft) % 24;
            mActivity.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    mTimeView.setText(String.format("%d:%02d:%02d", hours, minutes, seconds));
                }
            });
            if (timeLeft < 0) {
                mActivity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mTimeView.setText(String.format("%d:%02d:%02d", 0, 0, 0));
                    }
                });
                break;
            }
        }
    }
}
