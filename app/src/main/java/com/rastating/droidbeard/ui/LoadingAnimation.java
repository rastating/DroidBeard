package com.rastating.droidbeard.ui;

import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;

public class LoadingAnimation extends RotateAnimation {
    public LoadingAnimation() {
        super(0.0f, 360.0f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        setInterpolator(new LinearInterpolator());
        setRepeatCount(-1);
        setDuration(1000);
    }
}