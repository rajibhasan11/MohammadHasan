/**
 * This TabIndicatorView is an extention of View
 * to use in xml or programmatically draw tabs for 
 * navigation.
 * 
 *
 * @Author : Mohammad Hasan Khan
 * @Milan, Italy
 * @Copyright: 2013
 * **/
package com.heck.dynamic.tabhost;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.animation.TranslateAnimation;


public class TabIndicatorView extends View {
	
	private int mAnimTime = 280;
    private int mCurIndex = 0;
    private int mIndexCount = 1;
    private int mIndicatorWidth = 0;
    private float mIndicatorHeight = 45;
    private float mMarginLeft = 3;
    private float mMarginMiddle = 3;
    private float mMarginRight = 3;
    private Drawable mIndicatorDrawable;
    private TabIndicatorDrawable mDrawable;
    protected final Context mContext;
    protected int mIndicator;

    public TabIndicatorView(Context paramContext){
	   super(paramContext);
       mContext = paramContext;
    }
    
    // Using this constructor
    public TabIndicatorView(Context paramContext, int tabCount){
 	   super(paramContext);
       mContext = paramContext;
       mIndexCount = tabCount;
       mIndicatorDrawable = getLocalDrawable();
     }

    public TabIndicatorView(Context paramContext, AttributeSet paramAttributeSet) {
	   super(paramContext, paramAttributeSet);
	   mContext = paramContext;
    }

    private Drawable getLocalDrawable() {
    	GradientDrawable gd = new GradientDrawable();
    	try {
			gd.setColor(HackyColorParser.parseColor("a4cf76"));
			gd.setCornerRadius(4);
			gd.setStroke(2, HackyColorParser.parseColor("a4cf76"));
		} catch (Exception e) {
			e.printStackTrace();
		}
    	return gd;
    }

    private TabIndicatorDrawable getDrawable(){
    	if (this.mDrawable == null){
    		int i = getWidth();
            int j = getHeight();
            log("W: " + i + " H: " + j);
            if ((i == 0) || (j == 0)){
            	return null;
            }
            int k = 0;
            if(mIndexCount > 0) {
            	k = (int)(i - this.mMarginLeft - (-1 + this.mIndexCount) * this.mMarginMiddle - this.mMarginRight) / this.mIndexCount;
            	if(k != 0) {
                	this.mIndicatorWidth = k;
                	
                	if(mIndexCount == 1 || mIndexCount == 2) {
                	    this.mIndicatorWidth = 90;
                	}
            		
                    int m = (int)((j - this.mIndicatorHeight) / 2.0F);
                    int n = (int)this.mMarginLeft;
                    int i1 = (int)(this.mMarginLeft + this.mIndicatorWidth);
                    int i2 = j - m;
                    mIndicatorDrawable.setBounds(n, m, i1, i2);
                    this.mDrawable = new TabIndicatorDrawable(mIndicatorDrawable);
                }
            }
        }
    	return this.mDrawable;
    }
    
    private void log(String paramString){
    	Log.e("", "TabWidget TabIndicatorView: " + paramString);
    }
    
    protected void onDraw(Canvas paramCanvas){
    	super.onDraw(paramCanvas);
        TabIndicatorDrawable iDrawable = getDrawable();
        if(iDrawable != null) {
        	iDrawable.draw(paramCanvas);
            if(!iDrawable.hasEnded())
            	invalidate();
        } else {
        	log("TabIndicatorDrawable is NULL");
        }
    }
    
    public void setCurrentTabIndex(int tabIndex) {
    	this.mCurIndex = tabIndex;
    }
    
    public void setTabCount(int tabCount) {
    	this.mIndexCount = tabCount;
    	this.mDrawable = getDrawable();
    }
	
	public boolean setTabByIndex(int paramInt) {
		log("paramInt: " + paramInt + " mCurIndex: " + mCurIndex);
		if (paramInt == this.mCurIndex) {
			return false;
		}
		try {
			int i = (int)(this.mCurIndex * (this.mIndicatorWidth + this.mMarginMiddle));
			int j = (int)(paramInt * (this.mIndicatorWidth + this.mMarginMiddle));
			TranslateAnimation transAnim = new TranslateAnimation(i, j, 0.0F, 0.0F);
			transAnim.setDuration(mAnimTime);
			transAnim.initialize(10, 10, 10, 10);
			this.mDrawable.startAnimation(transAnim);
			this.mCurIndex = paramInt;
		} catch (Exception e) {
			e.printStackTrace();
		}
        return true;
    }
  
}
