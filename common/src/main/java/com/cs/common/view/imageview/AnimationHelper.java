package com.cs.common.view.imageview;

import android.view.View;
import android.view.animation.OvershootInterpolator;
import android.view.animation.ScaleAnimation;

public class AnimationHelper {
	public static void scaleViewAnimation(View view){
		ScaleAnimation scale = new ScaleAnimation(0.5f, 1, 0.5f, 1, ScaleAnimation.RELATIVE_TO_SELF, .5f, ScaleAnimation.RELATIVE_TO_SELF, .5f);
        scale.setDuration(300);
        scale.setInterpolator(new OvershootInterpolator());
        view.startAnimation(scale);
	}
}
