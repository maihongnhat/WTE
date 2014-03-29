package com.fas.lib;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class MyFileLib {
	
	public MyFileLib(){
		
	}
	
	/*
	 * Method name		: checkFileExistence
	 * Description		: check if the target file existed (return true if "exist", return false = "not exist")
	 * Creator			: NhatMH
	 * Last modified by	: -
	 */
	public boolean checkFileExistence(File file){
		return file.exists();
	}

	/*
	 * Method name		: createFile
	 * Description		: create new file
	 * Creator			: NhatMH
	 * Last modified by	: -
	 */	
	public void createFile(File file){
		try{
			if(!checkFileExistence(file)){
				file.createNewFile();
			}
		}catch(IOException ioe){
			String logMessage = "Can't not create "+file.getAbsolutePath();
			myLogger.printLog(logMessage, ioe);
		}
	}
	
	/*
	 * Method name		: readFile
	 * Description		: create an ArrayList<String> containing content of the specified file
	 * Creator			: NhatMH
	 * Last modified by	: -
	 */	
	@SuppressWarnings("finally")
	public ArrayList<String> readFile(File file){
		ArrayList<String> fileContent =  new ArrayList<String>();
		String line;
		
		try{
			BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
			while( (line = bufferedReader.readLine()) != null ){
				fileContent.add(line);
			}
			bufferedReader.close();
		}catch(FileNotFoundException fnfe){
			String logMessage = "Can't not find "+file.getAbsolutePath();
			myLogger.printLog(logMessage, fnfe);
		}
		catch(IOException ioe){
			String logMessage = "Can't not initialize Reader for "+file.getAbsolutePath();
			myLogger.printLog(logMessage, ioe);
		}
		finally{
			return fileContent;
		}		
	}
	
	/*
	 * Method name		: WriteFile
	 * Description		: write a String to the specified file (Overwrite)
	 * Creator			: NhatMH
	 * Last modified by	: -
	 */	
	public void overwriteFile(String content, File file){
		try{
			BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(file));
			bufferedWriter.write(content);
			bufferedWriter.flush();
			bufferedWriter.close();
		}catch(FileNotFoundException fnfe){
			String logMessage = "Can't not find "+file.getAbsolutePath();
			myLogger.printLog(logMessage, fnfe);
		}
		catch(IOException ioe){
			String logMessage = "Can't not write "+file.getAbsolutePath();
			myLogger.printLog(logMessage, ioe);
		}
	}
	
	/*
	 * Method name		: WriteFile
	 * Description		: write all Strings of an ArrayList<String> to the specified file (Overwrite)
	 * Creator			: NhatMH
	 * Last modified by	: -
	 */
	public void overwriteFile(ArrayList<String> strArr, File file){	
		try{
			BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(file));
			for(String str : strArr){
				bufferedWriter.write(str);
				bufferedWriter.flush();
			}
			bufferedWriter.close();
		}catch(IOException ioe){
			String logMessage = "Can't not write "+file.getAbsolutePath();
			myLogger.printLog(logMessage, ioe);
		}
	} 

	private MyLogger myLogger = new MyLogger();
}
