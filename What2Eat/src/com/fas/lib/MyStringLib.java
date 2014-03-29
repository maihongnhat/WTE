package com.fas.lib;

import java.util.ArrayList;

public class MyStringLib {

	/*
	 * Method name		: WriteFile
	 * Description		: convert String to ArrayList<String>
	 * Creator			: NhatMH
	 * Last modified by	: -
	 */
	public static ArrayList<String> getArrayListFromString(String inputString){
		ArrayList<String> outputStringList = new ArrayList<String>();
		String[] strArr = inputString.split("\n");
		
		for (String str : strArr){
			outputStringList.add(str);
		}
		
		return outputStringList;
	}
	
	/*
	 * Method name		: WriteFile
	 * Description		: convert ArrayList<String> to a multi-line String
	 * Creator			: NhatMH
	 * Last modified by	: -
	 */
	public static String getStringfromArrayList(ArrayList<String> stringArrayList){
		StringBuilder originalString = new StringBuilder();
		
		for(String str : stringArrayList){
			originalString.append(str);
			originalString.append("\n");
		}
		
		return originalString.toString();
	}

	
}
