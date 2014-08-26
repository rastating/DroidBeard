package com.rastating.droidbeard.ui;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.view.View;

public class CrossFader {
    private View mView1, mView2;
    private int mDuration;

    /***
     * Instantiate a new CrossFader object.
     * @param view1 the view to fade out
     * @param view2 the view to fade in
     * @param fadeDuration the duration in milliseconds for each fade to last
     */
    public CrossFader(View view1, View view2, int fadeDuration) {
        mView1 = view1;
        mView2 = view2;
        mDuration = fadeDuration;
    }

    /***
     * Start the cross-fade animation.
     */
    public void start() {
        mView2.setAlpha(0f);
        mView2.setVisibility(View.VISIBLE);
        mView1.animate()
            .alpha(0f)
            .setDuration(mDuration)
            .setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mView1.setVisibility(View.GONE);
                    mView2.animate()
                        .alpha(1f)
                        .setDuration(mDuration)
                        .setListener(null);
                }
            });
    }
}