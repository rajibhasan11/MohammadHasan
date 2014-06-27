/**
 * This TabIndicatorDrawable is an extention of ProxyDrawable
 * to use in TabIndicatorView.
 * 
 *
 * @Author : Mohammad Hasan Khan
 * @Company Name : MP Mobile Srl, Milan, Italy
 * @Copyright: 2013
 * **/
package com.heck.dynamic.tabhost;

import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.Transformation;

public class TabIndicatorDrawable extends ProxyDrawable {
	
	private Animation mAnimation;
	private Transformation mTransformation = new Transformation();
	
	public TabIndicatorDrawable(Drawable paramDrawable) {
		super(paramDrawable);
	}
	
	public void draw(Canvas paramCanvas){
		Drawable localDrawable = getProxy();
		if (localDrawable != null){
			int i = paramCanvas.save();
			Animation localAnimation = this.mAnimation;
			if (localAnimation != null){
				localAnimation.getTransformation(AnimationUtils.currentAnimationTimeMillis(), this.mTransformation);
				paramCanvas.concat(this.mTransformation.getMatrix());
			}
			localDrawable.draw(paramCanvas);
			paramCanvas.restoreToCount(i);
		}
	}
	
	public boolean hasEnded(){
		return (this.mAnimation == null) || (this.mAnimation.hasEnded());
	}
	
	public boolean hasStarted() {
		return (this.mAnimation != null) && (this.mAnimation.hasStarted());
	}
	
	public void startAnimation(Animation paramAnimation) {
		this.mAnimation = paramAnimation;
		paramAnimation.startNow();
	}
}
