package com.fas.lib;

import java.util.ArrayList;
import java.util.Random;

public class MyStringLib {
	
	public static ArrayList<String> getArrayListFromString(String inputString){
		ArrayList<String> outputStringList = new ArrayList<String>();
		String[] strArr = inputString.split("\n");
		
		for (String str : strArr){
			outputStringList.add(str);
		}
		
		return outputStringList;
	}
	
	public static String getStringfromArrayList(ArrayList<String> stringArrayList){
		StringBuilder originalString = new StringBuilder();
		
		for(String str : stringArrayList){
			originalString.append(str);
			originalString.append("\n");
		}
		
		return originalString.toString();
	}

	
}
