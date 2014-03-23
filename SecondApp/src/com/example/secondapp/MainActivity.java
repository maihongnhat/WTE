package com.example.secondapp;

import java.io.File;
import java.util.ArrayList;

import com.example.myutils.MyIO;
import com.example.myutils.MyUtils;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		MainActivityLayout = new LinearLayout(this);
		
		String allDishesFilePath = getFilesDir() + "/" + ALL_DISHES_FILENAME;
		ALL_DISHES_FILE = new File(allDishesFilePath);		
		System.out.println(ALL_DISHES_FILE.getPath());
		myIO = new MyIO(ALL_DISHES_FILE);
		loadFile();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	public void loadFile(){
		EditText edit_dishes = (EditText) findViewById(R.id.edit_dishes);
		String dishesString = MyUtils.restoreStringfromArrayList(myIO.readFile());
		System.out.println("Content of targetFile:");
		System.out.println(dishesString);
		edit_dishes.setText(dishesString);
	}
	
	public void chooseRandomDish(View view){
		
		EditText edit_dishes = (EditText) findViewById(R.id.edit_dishes);
		EditText edit_todaydish = (EditText) findViewById(R.id.edit_todaydish);
		
		String dishesString = edit_dishes.getText().toString();
		ArrayList<String> dishesArr = MyUtils.getArrayListFromString(dishesString);
		int randomIndex = MyUtils.generateRandomValue(dishesArr.size());
		
		edit_todaydish.setText(dishesArr.get(randomIndex));		
		myIO.writeFile(dishesString);		
		
	}
	
	

	private LinearLayout MainActivityLayout;
	private final String ALL_DISHES_FILENAME = "AllDishes.txt";
	private File ALL_DISHES_FILE;
	public static MyIO myIO;
}
