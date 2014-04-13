package com.fas.what2eat;

import java.util.ArrayList;
import java.util.List;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class MyFragmentPagerAdapter extends FragmentPagerAdapter {

	public List<Fragment> fragmentList;
	public FragmentMeal fragmentBreakfast;
	public FragmentMeal fragmentLunch;
	public FragmentMeal fragmentDinner;
	
	MyFragmentPagerAdapter(FragmentManager fragmentManager){
		super(fragmentManager);
	
		fragmentBreakfast = new FragmentMeal();
		fragmentBreakfast.setLayoutId(R.layout.fragment_meal);
		fragmentBreakfast.setOutputFileName("Breakfast");
		
		fragmentLunch = new FragmentMeal();
		fragmentLunch.setLayoutId(R.layout.fragment_meal);
		fragmentLunch.setOutputFileName("Lunch");
		
		fragmentDinner = new FragmentMeal();
		fragmentDinner.setLayoutId(R.layout.fragment_meal);
		fragmentDinner.setOutputFileName("Dinner");
		
		fragmentList = new ArrayList<Fragment>();
		fragmentList.add(fragmentBreakfast);
		fragmentList.add(fragmentLunch);
		fragmentList.add(fragmentDinner);
	}
	
	@Override
	public Fragment getItem(int position) {
		return fragmentList.get(position);
	}

	@Override
	public int getCount() {
		return fragmentList.size();
	}	

}
