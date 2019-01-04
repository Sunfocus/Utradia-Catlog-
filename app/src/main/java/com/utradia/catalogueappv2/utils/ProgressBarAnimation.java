package com.utradia.catalogueappv2.utils;

import android.view.animation.Animation;
import android.view.animation.Transformation;
import android.widget.ProgressBar;

public class ProgressBarAnimation extends Animation {
    private ProgressBar progressBar;
    private float from;
    private float  to;

    public ProgressBarAnimation() {
    }

    public void setAnimation(ProgressBar progressBar, float from, float to) {
        this.progressBar = progressBar;
        this.from = from;
        this.to = to;
    }

    @Override
    protected void applyTransformation(float interpolatedTime, Transformation t) {
        super.applyTransformation(interpolatedTime, t);
        float value = (from * 100) / to;
        progressBar.setProgress((int) value);
    }

}