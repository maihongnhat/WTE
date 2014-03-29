package com.fas.lib;

public class MyLogger {
	
	/*
	 * Method name		: printLog
	 * Description		: print out a formatted message (for debugging)
	 * Creator			: NhatMH
	 * Last modified by	: -
	 */
	public void printLog(String message){
		StringBuilder log = new StringBuilder();		
		String className;
		String methodName;
		
		//get class name
		Class<?> enclosingClass = getClass().getEnclosingClass();
		if (enclosingClass != null) {
			className = enclosingClass.getName();
		} else {
			className = getClass().getName();
		}
		
		// get method name
		Exception ex = new Exception();
		methodName = ex.getStackTrace()[1].getMethodName();
		
		// print the message
		log.append(className);
		log.append(".");
		log.append(methodName);
		
		System.out.println(log.toString()+" - "+message);
	}

	/*
	 * Method name		: printLog
	 * Description			: print out a formatted message to help find out the exception quickly (for debugging)
	 * Creator			: NhatMH
	 * Last modified by	: -
	 */
	public void printLog(String message, Exception exception){
		StringBuilder log = new StringBuilder();		
		String className;
		String methodName;
		
		//get class name
		Class<?> enclosingClass = getClass().getEnclosingClass();
		if (enclosingClass != null) {
			className = enclosingClass.getName();
		} else {
			className = getClass().getName();
		}
		
		// get method name
		methodName = exception.getStackTrace()[1].getMethodName();
		
		// print the message
		log.append(className);
		log.append(".");
		log.append(methodName);
		
		System.out.println(log.toString()+" - "+message);
	}

}
