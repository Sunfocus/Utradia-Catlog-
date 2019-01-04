package com.utradia.catalogueappv2.utils;

import android.app.Activity;
import android.content.Context;

import androidx.annotation.RequiresApi;
import androidx.core.content.ContextCompat;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.utradia.catalogueappv2.R;


/**
 * Display ProgressBar over any Activity in your application
 */

public class ProgressBarHandler {

    private ImageView mLoader;
    private RelativeLayout rl;
    public ProgressBarHandler() {
    }

    public void setContext(Context context) {
        ViewGroup layout = (ViewGroup) ((Activity) context).findViewById(android.R.id.content).getRootView();

        RelativeLayout.LayoutParams imgparams = new
                RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        mLoader = new ImageView(context, null);
        mLoader.setImageDrawable(ContextCompat.getDrawable(context,R.drawable.ic_loader));
        mLoader.startAnimation(
                AnimationUtils.loadAnimation(context, R.anim.rotate) );
        mLoader.setLayoutParams(imgparams);

        RelativeLayout.LayoutParams params = new
                RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT);

        rl = new RelativeLayout(context);

        rl.setGravity(Gravity.CENTER);
        rl.addView(mLoader);
        rl.setOnClickListener(v -> { });

        layout.addView(rl, params);
        hideProgress();
    }

    public void showProgress() {
        rl.setClickable(true);
        rl.setVisibility(View.VISIBLE);
        mLoader.setVisibility(View.VISIBLE);
    }

    public void hideProgress() {
        rl.setClickable(false);
        rl.setVisibility(View.GONE);
        mLoader.setVisibility(View.INVISIBLE);
    }
}