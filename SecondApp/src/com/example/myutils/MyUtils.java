package com.example.myutils;

import java.util.ArrayList;
import java.util.Random;

public class MyUtils {
	
	public static ArrayList<String> getArrayListFromString(String inputString){
		ArrayList<String> outputStringList = new ArrayList<String>();
		String[] strArr = inputString.split("\n");
		
		for (String str : strArr){
			System.out.println(str);
			outputStringList.add(str);
		}
		
		return outputStringList;
	}
	
	public static String restoreStringfromArrayList(ArrayList<String> stringArrayList){
		StringBuilder originalString = new StringBuilder();
		
		for(String str : stringArrayList){
			originalString.append(str);
			originalString.append("\n");
		}
		
		return originalString.toString();
	}
	
	public static int generateRandomValue(int total){
		return R.nextInt(total);
	}
	
	
	public static void showValue(Object obj){
		String classType = obj.getClass().toString();
		System.out.println("DEBUG - "+classType);		
	}
	
	public static Random R = new Random();
}
