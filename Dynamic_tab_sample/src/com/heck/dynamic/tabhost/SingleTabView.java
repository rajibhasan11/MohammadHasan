/**
 * This SingleTabView is an extention of TextView
 * to use in xml or programmatically draw as tab for 
 * DynamicTabView
 * 
 *
 * @Author : Mohammad Hasan Khan
 * @Company Name : MP Mobile Srl, Milan, Italy
 * @Copyright: 2013
 * **/
package com.heck.dynamic.tabhost;


import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.Gravity;
import android.widget.LinearLayout;
import android.widget.TextView;


public class SingleTabView extends TextView {
	
	protected boolean isFocused;
	protected final Context mContext;
	
	// Using
	public SingleTabView(Context context) {
		super(context);
		mContext = context;
		init();
	}
	
	public SingleTabView(Context context, AttributeSet attrs) {
		super(context, attrs);
		mContext = context;
		init();
	}
	
    public SingleTabView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        mContext = context;
        init();
    }

	@Override
    protected void onFocusChanged(boolean focused, int direction, Rect previouslyFocusedRect) {
    	if(focused) {
    		isFocused = true;
    		super.onFocusChanged(isFocused, direction, previouslyFocusedRect);
    	}
    }

    @Override
    public void onWindowFocusChanged(boolean focused) {
        if(focused)
            super.onWindowFocusChanged(focused);
    }

    @Override
    public boolean isFocused() {
        return isFocused;
    }

	private void init() {
		try {
			// setTextAppearance(mContext, R.style.main_tabs);
			LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
			params.setMargins(1, 0, 1, 0);
			setLayoutParams(params);
			setTextSize(13);
			setSingleLine(true);
			setTypeface(null, Typeface.BOLD);
			setNullBg();
			setEllipsize(TextUtils.TruncateAt.MARQUEE);
			setFadingEdgeLength(1);
			setGravity(Gravity.CENTER);
			setTextColor(new ColorStateList (
			          new int [][] {
					      new int [] {android.R.attr.state_pressed},
					      new int [] {}
					   },
					   new int [] {
					      HackyColorParser.parseColor("0021ff"),
					      HackyColorParser.parseColor("ffffff")
					   }
				));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	
	@SuppressWarnings("deprecation")
	private void setNullBg() {
		setBackgroundDrawable(null);
	}

}
