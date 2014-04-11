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
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

public class MyFileLib {
	
	public MyFileLib(){
		
	}
	
	/* Purpose		: check if the target file existed (return true if "exist", return false = "not exist")
	 * PIC			: NhatMH
	 */
	public boolean checkFileExistence(File file){
		return file.exists();
	}

	/* Purpose		: createFile
	 * PIC			: NhatMH
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
	
	/* Purpose		: create an ArrayList<String> containing content of the specified file
	 * PIC			: NhatMH
	 */	
	@SuppressWarnings("finally")
	public ArrayList<String> readTextFile(File file){
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
			String logMessage = "Can't not read "+file.getAbsolutePath();
			myLogger.printLog(logMessage, ioe);
		}
		finally{
			return fileContent;
		}		
	}
	
	/* Purpose		: write a String to the specified file (Overwrite)
	 * PIC			: NhatMH
	 */	
	public void overwriteTextFile(String content, File file){
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
	
	public boolean checkEmptyFile(File file){
		boolean isEmptyFlag = true;
		long fileSize = file.length();
		if(fileSize != 0){
			isEmptyFlag = false;
		}		
		return isEmptyFlag;
	}
	
	/* Purpose		: write all Strings of an ArrayList<String> to the specified file (Overwrite)
	 * PIC			: NhatMH
	 */
	public void overwriteTextFile(ArrayList<String> strArr, File file){	
		try{
			BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(file));
			for(String str : strArr){
				bufferedWriter.write(str);
				bufferedWriter.write("\n");
				bufferedWriter.flush();
			}
			bufferedWriter.close();
		}catch(IOException ioe){
			String logMessage = "Can't not write "+file.getAbsolutePath();
			myLogger.printLog(logMessage, ioe);
		}
	} 

	/* Purpose		: Read Object from the specified file
	 * PIC			: NhatMH
	 */
	public Object readObjectFromFile(File file){
		Object obj = new Object();
		
		try{
			ObjectInputStream objectInputStream = new ObjectInputStream(new FileInputStream(file));
			obj = objectInputStream.readObject();
			objectInputStream.close();
		}catch(FileNotFoundException fnfe){
			String logMessage = "Can't not find "+file.getAbsolutePath();
			myLogger.printLog(logMessage, fnfe);
		}catch(ClassNotFoundException cnfe){
			String logMessage = "Can't not indentify the return Object from "+file.getAbsolutePath();
			myLogger.printLog(logMessage, cnfe);
		}
		catch(IOException ioe){
			String logMessage = "Can't not read "+file.getAbsolutePath();
			myLogger.printLog(logMessage, ioe);
		}
		
		return obj;
	}
	
	/* Purpose		: write an Object to the specified file
	 * PIC			: NhatMH
	 */
	public void writeObjectToFile(Object obj, File file){
		try{
			ObjectOutputStream objectOutputStream = new ObjectOutputStream(new FileOutputStream(file));
			objectOutputStream.writeObject(obj);
			objectOutputStream.flush();
			objectOutputStream.close();
		}catch(FileNotFoundException fnfe){
			String logMessage = "Can't not find "+file.getAbsolutePath();
			myLogger.printLog(logMessage, fnfe);
		}catch(IOException ioe){
			String logMessage = "Can't not write "+file.getAbsolutePath();
			myLogger.printLog(logMessage, ioe);
		}
	}
	
	
	
	private MyLogger myLogger = new MyLogger();
}
