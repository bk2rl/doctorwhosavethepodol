package com.b2r.main.ui.view;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.content.Context;
import android.content.res.TypedArray;
import android.os.Build;
import android.support.v4.view.animation.FastOutSlowInInterpolator;
import android.support.v7.widget.CardView;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.TextView;

import com.b2r.main.R;

public class HintImageView extends ImageView {
    private final int duration = 375;
    private final int startDelay = 3000;
    private String hint;
    private HintOnClickListener onClickListener;
    private CardView hintView;
    private ObjectAnimator hintAnimator;
    private Animator.AnimatorListener hintAnimatorListener;

    public HintImageView(Context context) {
        super(context);
    }

    public HintImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        setCustomAttributes(context, attrs);
        onClickListener = new HintOnClickListener();
        this.setOnClickListener(onClickListener);
    }

    public HintImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setCustomAttributes(context, attrs);
        onClickListener = new HintOnClickListener();
        this.setOnClickListener(onClickListener);
    }

    private void setCustomAttributes(Context context, AttributeSet attrs) {
        TypedArray customAttributes = null;
        try {
            customAttributes = context.obtainStyledAttributes(attrs, R.styleable.HintImageView);
            hint = customAttributes.getString(R.styleable.HintImageView_hint);
        } finally {
            if (customAttributes != null) {
                customAttributes.recycle();
            }
        }
    }

    public String getHint() {
        return hint;
    }

    public HintImageView setHint(String hint) {
        this.hint = hint;
        return this;
    }

    public void createHint(final ViewGroup rootView) {

        hintView = new CardView(getContext());

        //move hintView to their coordinates
        hintView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                final int[] hintInWindowLocation = new int[2];
                final int[] rootInWindowLocation = new int[2];
                final int[] imageViewInWindowLocation = new int[2];
                final int[] rootCoordinates = new int[4];
                final int[] hintCoordinates = new int[4];
                final int[] imageViewCoordinates = new int[4];


                rootView.getLocationInWindow(rootInWindowLocation);
                hintView.getLocationInWindow(hintInWindowLocation);
                HintImageView.this.getLocationInWindow(imageViewInWindowLocation);

                rootCoordinates[0] = rootInWindowLocation[0];
                rootCoordinates[1] = rootInWindowLocation[1];
                rootCoordinates[2] = rootCoordinates[0] + rootView.getMeasuredWidth();
                rootCoordinates[3] = rootCoordinates[1] + rootView.getMeasuredHeight();

                hintCoordinates[0] = hintInWindowLocation[0];
                hintCoordinates[1] = hintInWindowLocation[1];
                hintCoordinates[2] = hintCoordinates[0] + hintView.getMeasuredWidth();
                hintCoordinates[3] = hintCoordinates[1] + hintView.getMeasuredHeight();

                imageViewCoordinates[0] = imageViewInWindowLocation[0];
                imageViewCoordinates[1] = imageViewInWindowLocation[1];
                imageViewCoordinates[2] = imageViewCoordinates[0] + HintImageView.this.getMeasuredWidth();
                imageViewCoordinates[3] = imageViewCoordinates[1] + HintImageView.this.getMeasuredHeight();

                if ((imageViewCoordinates[3]-rootCoordinates[0]) > hintView.getMeasuredWidth()) {
                    hintView.setX(imageViewCoordinates[2] - (hintCoordinates[2] - hintCoordinates[0]));
                } else {
                    hintView.setX(imageViewCoordinates[0]);
                }

                hintView.setY(imageViewCoordinates[3]);



                if (hintAnimator == null) {
                    createAnimator(rootView);
                }
                hintAnimator.start();

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                    hintView.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                } else {
                    hintView.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                }
            }
        });

        //create view
        hintView.setCardElevation(24);
        hintView.setAlpha((float) 0.9);
        hintView.setLayoutParams(new ViewGroup.MarginLayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        View hintLayout = View.inflate(getContext(), R.layout.hint, null);
        ((TextView) hintLayout.findViewById(R.id.hint_text)).setText(HintImageView.this.hint);
        hintLayout.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                rootView.removeView(hintView);
            }
        });
        hintView.addView(hintLayout);

        //creating Animation listener
        hintAnimatorListener = new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {

            }

            @Override
            public void onAnimationEnd(Animator animator) {
                rootView.removeView(hintView);
            }

            @Override
            public void onAnimationCancel(Animator animator) {

            }

            @Override
            public void onAnimationRepeat(Animator animator) {

            }
        };

        //attach view to root
        rootView.addView(hintView);
    }

    private void createAnimator(final ViewGroup rootView) {
        hintAnimator = ObjectAnimator.ofFloat(hintView, View.ALPHA, 1, 0);
        hintAnimator.setStartDelay(startDelay);
        hintAnimator.setDuration(duration);
        hintAnimator.setInterpolator(new FastOutSlowInInterpolator());
        hintAnimator.addListener(hintAnimatorListener);
    }

    protected class HintOnClickListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            if (getContext() instanceof Activity) {
                final ViewGroup rootView = (ViewGroup) ((Activity) getContext()).getWindow().getDecorView().getRootView();
                if (hintView == null) {
                    ((HintImageView) view).createHint(rootView);
                } else {
                    if (hintView.getParent() == null) {
                        rootView.addView(hintView);
                    }
                    if (hintAnimator != null) {
                        hintView.setAlpha((float) 0.9);
                        if (hintAnimator.isRunning()) {
                            resetAnimation(hintAnimator);
                        }
                        hintAnimator.start();
                    }
                }
            }
        }
    }

    private void resetAnimation(ObjectAnimator animator) {
        hintAnimator.removeListener(hintAnimatorListener);
        hintAnimator.cancel();
        animator.setDuration(0);
        animator.reverse();
        animator.start();
        animator.setStartDelay(startDelay);
        animator.setDuration(duration);
        hintAnimator.addListener(hintAnimatorListener);
    }
}

