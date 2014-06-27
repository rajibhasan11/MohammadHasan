/**
 * This DynamicTabView is an extention of FrameLayout
 * to use programmatically add and remove tabs dynamically.
 * Here are the main magic of dynamicity.
 * 
 *
 * @Author : Mohammad Hasan Khan
 * @Milan, Italy
 * @Copyright: 2013
 * **/
package com.heck.dynamic.tabhost;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.commons.lang3.text.WordUtils;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import com.hasan.dynamictab.widget.R;

public class DynamicTabView extends FrameLayout {

	protected final Context mContext;
	
	protected TabIndicatorView tabIndicatorView;
	protected LinearLayout tabContainer;
	protected int mCurTabIndex = 0;
	protected int tabCount = 0;
	private List<SingleTabView> mTabs = new ArrayList<SingleTabView>();
	
	// Using
	public DynamicTabView(Context context) {
		super(context);
		this.mContext = context;
		init();
	}
	
	public DynamicTabView(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.mContext = context;
		init();
	}
	
	private void init() {
		try {
			LayoutInflater.from(mContext).inflate(R.layout.dynamic_tab_layout, this, true);
			findViews();
			setBg();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void setBg() {
		setBackgroundColor(HackyColorParser.parseColor("9a9b9c"));
	}

	private void findViews() {
		try {
			this.tabContainer = (LinearLayout) findViewById(R.id.tabContainer);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void invalidsateTabIndicatorView(int tabCount) {
		this.tabIndicatorView.invalidate();
		this.tabIndicatorView.setTabCount(tabCount);
		LinearLayout.LayoutParams iParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
		this.tabIndicatorView.setLayoutParams(iParams);
	}
	
	private void createIndicatorOnTop(int tabCount) {
		try {
			this.tabIndicatorView = new TabIndicatorView(mContext, tabCount);
			this.tabIndicatorView.setId(R.id.tivIndictor);
			LinearLayout tiContainer = (LinearLayout) findViewById(R.id.tiContainer);
			tiContainer.addView(this.tabIndicatorView);
			LinearLayout.LayoutParams iParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
			this.tabIndicatorView.setLayoutParams(iParams);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	// Add tab methods
	private void addTab(SingleTabView tab) {
		try {
			if(tab != null) {
				LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(90, LinearLayout.LayoutParams.MATCH_PARENT);
				params.setMargins(0, 0, 2, 0);
				tab.setPadding(3, 0, 1, 0);
				tab.setLayoutParams(params);
				log("Tab added at position: " + mCurTabIndex);
				this.tabContainer.addView(tab);
				// Set listener
				tab.setSelected(false);
				tab.setTag(mCurTabIndex+"");
				tab.setOnTouchListener(tabTouchListener);
				
				mCurTabIndex++;
				mTabs.add(tab);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void addHomeTab(String tabName) {
		try {
			addTab(tabName);
		} catch (NumberFormatException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	// To use from activity
	public void addTab(String tabName) {
		try {
			if(tabName != null) {
				log(mCurTabIndex+"");
				SingleTabView tab = new SingleTabView(mContext);
				if(tab != null) {
					tab.setText(tabName);
					tab.setId(mCurTabIndex);
					if(mCurTabIndex == 0) {
						addHomeTab("Home");
					} else {
						addTab(tab);
					}
				}
			}
		} catch (NumberFormatException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	void log(String paramString){
    	Log.e("", "TabWidget DynamicTabView: "+ paramString);
    }
	
	private void addTab(ConcurrentHashMap<String, String> tabMap) {
		try {
			String tabName = tabMap.get("name");
			if(tabName != null) {
				SingleTabView tab = new SingleTabView(mContext);
				if(tab != null) {
					tab.setId(mCurTabIndex);
					if(mCurTabIndex == 0 || tabName.equalsIgnoreCase("home")) {
						addHomeTab("f015");
					} else {
						tab.setText(WordUtils.capitalize(tabName));
						addTab(tab);
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@SuppressWarnings("unchecked")
	public List<SingleTabView> addTabs(List<Object> tabs) {
		try {
			if(tabs != null) {
				if(tabs.size() > 0) {
					boolean tabViewWillRecreate = false;
					tabCount = this.tabContainer.getChildCount();
					log("Tab count before remove: " + tabCount);
					if(tabCount > 0){
						this.tabContainer.removeAllViews();
						tabViewWillRecreate = true;
						mCurTabIndex = 0;
						mTabs = new ArrayList<SingleTabView>();
					}
					for (Object tab : tabs) {
						if(tab instanceof String) {
							addTab((String) tab);
						} else if(tab instanceof ConcurrentHashMap<?, ?>) {
							addTab((ConcurrentHashMap<String, String>) tab);
						} else {
							addTab((SingleTabView) tab);
						}
					}
					tabCount = this.tabContainer.getChildCount();
					log("Tab count: " + tabCount);
					if(tabViewWillRecreate) {
						invalidsateTabIndicatorView(tabCount);
					} else {
						createIndicatorOnTop(tabCount);
					}
					// setTabListeners();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return mTabs;
	}
	
	@SuppressWarnings("unused")
	private void setTabListeners() {
		try {
			log("Tab count: " + tabCount);
			for (int i = 0; i < tabCount; i++) {
				SingleTabView tab = (SingleTabView) findViewById(i);
				tab.setSelected(false);
				tab.setTag(i+"");
				tab.setOnTouchListener(tabTouchListener);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	View.OnTouchListener tabTouchListener = new View.OnTouchListener() {
		@Override
		public boolean onTouch(View v, MotionEvent event) {
			Log.e("", "ONTOUCH");
			try {
				int intId = Integer.parseInt((String) v.getTag());
				tabIndicatorView.setTabByIndex(intId);
				((SingleTabView)v).setSelected(true);
				for (int i = 0; i < tabCount; i++) {
					if(i != intId) {
						((SingleTabView) tabContainer.getChildAt(i)).setSelected(false);
					}
				}
			} catch (NumberFormatException e) {
				e.printStackTrace();
			}
			return false;
		}
	};
	
	// Remove methods
	public void removeTabs(int from, int to) {
		try {
			if(tabCount > 0 && to > from) {
				int loopLen = to - from;
				if(loopLen > 0) {
					for (int i = 1; i < loopLen; i++) {
						removeTab(i);
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void removeTab(int tabIndex) {
		try {
			if(tabCount > 0 && tabIndex > 0) {
				this.tabContainer.removeViewAt(tabIndex);
				mCurTabIndex--;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void removeTab(View tab) {
		try {
			if(tabCount > 0) {
				this.tabContainer.removeView(tab);
				mCurTabIndex--;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
