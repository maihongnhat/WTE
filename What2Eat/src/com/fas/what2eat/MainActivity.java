package com.fas.what2eat;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.view.ActionMode;
import android.view.Menu;
import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.app.FragmentTransaction;

public class MainActivity extends FragmentActivity implements ActionBar.TabListener {

	public ViewPager viewPager;
	public MyFragmentPagerAdapter myAdapter;
	public static ActionBar actionBar;
	
	public static String outputPath;
	
	public int lastSelectedFragmentId = 0;
	
	
	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);

		setContentView(R.layout.activity_main);
		
		outputPath = getFilesDir().getAbsolutePath();
		
		myAdapter = new MyFragmentPagerAdapter(getSupportFragmentManager());
		viewPager = (ViewPager) findViewById(R.id.pager);
		viewPager.setAdapter(myAdapter);
	
		actionBar = getActionBar();
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
		actionBar.setDisplayShowTitleEnabled(false);
		actionBar.setDisplayShowHomeEnabled(false);
		actionBar.setDisplayUseLogoEnabled(false);
		
		
		Tab tabBreakfast = actionBar.newTab();
		tabBreakfast.setText(getResources().getString(R.string.page_title1));
		tabBreakfast.setTabListener(this);
		
		Tab tabLunch = actionBar.newTab();
		tabLunch.setText(getResources().getString(R.string.page_title2));
		tabLunch.setTabListener(this);
		
		Tab tabDinner = actionBar.newTab();
		tabDinner.setText(getResources().getString(R.string.page_title3));
		tabDinner.setTabListener(this);
		
		actionBar.addTab(tabBreakfast);
		actionBar.addTab(tabLunch);
		actionBar.addTab(tabDinner);
		
		
		
		
		viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
			
			@Override
			public void onPageSelected(int arg0) {
				actionBar.setSelectedNavigationItem(arg0);
				FragmentMeal fragmentMeal = (FragmentMeal) myAdapter.getItem(lastSelectedFragmentId);
				ActionMode actionMode = fragmentMeal.getActionMode();
				if(actionMode != null){
					actionMode.finish();
				}				
				DishAdapter da = (DishAdapter) fragmentMeal.lv.getAdapter();
				da.clearSelection();
				lastSelectedFragmentId = arg0;
			}
			
			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {
				
			}
			
			@Override
			public void onPageScrollStateChanged(int arg0) {
				
			}
		});
		
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public void onTabReselected(Tab tab, FragmentTransaction ft) {
		
	}

	@Override
	public void onTabSelected(Tab tab, FragmentTransaction ft) {
		viewPager.setCurrentItem(tab.getPosition());	
	}

	@Override
	public void onTabUnselected(Tab tab, FragmentTransaction ft) {
		
	}


}
