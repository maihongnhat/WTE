package com.example.myutils;

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

public class MyIO {
	public MyIO(File file){
		try{
			targetFile = file;
			if(!targetFile.exists()){
				targetFile.createNewFile();
			}
			bufferedReader = new BufferedReader(new FileReader(targetFile));
		}catch(FileNotFoundException fnfe){
			message = "MyIO's constructor: Can't not find "+file.getAbsolutePath();
			System.out.println(message);
		}
		catch(IOException ioe){
			message = "MyIO's constructor: Can't not initialize Reader and Writer "+file.getAbsolutePath();
			System.out.println(message);
		}
	}
	
	public boolean checkFileExistence(File file){
		return file.exists();
	}
	
	public void createFile(){
		try{
			targetFile.createNewFile();
		}catch(IOException ioe){
			message = "MyIO's createFile(): Can't not create "+targetFile.getAbsolutePath();
			System.out.println(message);
		}
	}
	
	public ArrayList<String> readFile(){		
		ArrayList<String> fileContent =  new ArrayList<String>();		
		String line;
		if(targetFile.exists()){
			try{
				while( (line = bufferedReader.readLine()) != null )
					fileContent.add(line);					
			}catch(IOException ioe){
				message = "MyIO's writefile: Can't not read "+targetFile.getAbsolutePath();
				System.out.println(message);
			}
		}else{
			createFile();
		}
		return fileContent;
	}
	
	public void writeFile(String content){
		if(!checkFileExistence(targetFile)){
			System.out.println("Our FILE does not exist. File path: "+targetFile.getAbsolutePath());
			return;
		}		
		try{
			bufferedWriter = new BufferedWriter(new FileWriter(targetFile));
			bufferedWriter.write(content);
			bufferedWriter.flush();
			bufferedWriter.close();
		}catch(NullPointerException npe){
			npe.printStackTrace();
		}
		catch(IOException ioe){
			message = "MyIO's writefile: Can't not find "+targetFile.getAbsolutePath();
			System.out.println(message);
		}
		System.out.println("Write string to "+targetFile.getAbsolutePath()+" successfully.");
	}
	
	public void writeFile(ArrayList<String> strArr){		
		try{
			bufferedWriter = new BufferedWriter(new FileWriter(targetFile));
			for(String str : strArr){
				bufferedWriter.write(str);
				bufferedWriter.flush();
			}
			bufferedWriter.close();
		}catch(IOException ioe){
			message = "MyIO's writefile: Can't not find "+targetFile.getAbsolutePath();
			System.out.println(message);
		}
	} 
	
	
	public void closeStreams(){
		try{
			fileInputStream.close();
			fileOutputStream.close();
		}catch(IOException ioe){
			System.out.println("closeAll: Can not close Input & Output streams.");
		}
	}
	
	private File targetFile;
	private FileInputStream fileInputStream;
	private BufferedReader bufferedReader;
	private BufferedWriter bufferedWriter;
	private FileOutputStream fileOutputStream;
	public String message;
}
