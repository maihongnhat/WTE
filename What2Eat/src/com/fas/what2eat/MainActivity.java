package com.fas.what2eat;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import com.fas.lib.MyFileLib;
import com.fas.lib.MyLogger;

import android.os.Bundle;
import android.app.ActionBar.LayoutParams;
import android.app.Activity;
import android.app.Dialog;
import android.content.pm.LabeledIntent;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ContextMenu.ContextMenuInfo;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.AdapterContextMenuInfo;

public class MainActivity extends Activity{

	//Danh sach cac mon
	List<String> dishList = new ArrayList<String>();
	ArrayAdapter<String> sa;
	Random r = new Random();
	
    LinearLayout layoutOfPopup;
    PopupWindow popupMessage;
    Button popupButton, insidePopupButton;
    TextView popupText;
    Dialog randomDialog;
	
    public static final String ALL_DISHES_FILENAME = "AllDishes.txt";
	public File ALL_DISHES_FILE;
	public MyFileLib myIO;
	public MyLogger myLogger;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		MyLogger myLogger = new MyLogger();
    	String allDishesFilePath = getFilesDir() + "/" + ALL_DISHES_FILENAME;
		ALL_DISHES_FILE = new File(allDishesFilePath);		
		myIO = new MyFileLib();
		myIO.createFile(ALL_DISHES_FILE);
		
		//Them dishList vao listview - Nhat
		initDishes();
		
		ListView lv = (ListView) findViewById(R.id.listview);
		sa = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,dishList);
		lv.setAdapter(sa);
		registerForContextMenu(lv);
		
		
		// Edit 1 mon trong list = cach click len mon do
		lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				TextView clickedView = (TextView) arg1;
//				Toast.makeText(MainActivity.this, "Item with id ["+arg3+"] - Position ["+arg2+"] -  ["+clickedView.getText()+"]", Toast.LENGTH_SHORT).show();
				
			    final Dialog de = new Dialog(arg0.getContext());
				
				de.setContentView(R.layout.dialog);
				de.setTitle("Edit Dish");
				de.setCancelable(true);
				final EditText et = (EditText) de.findViewById(R.id.editText);
				final int pos = arg2; 
				et.setText(clickedView.getText());
				Button b = (Button) de.findViewById(R.id.btThem);
				b.setOnClickListener(new View.OnClickListener()
					{

						@Override
						public void onClick(View v) {
							// TODO Auto-generated method stub
							String mon = et.getText().toString();																				
							MainActivity.this.dishList.set(pos, mon);
							MainActivity.this.sa.notifyDataSetChanged();
							de.dismiss();														
						}							
					});
				de.show();
			}
		});
		
		init();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	// Hien thi menu Delete khi press and hold tren 1 item
	@Override
	public void onCreateContextMenu(ContextMenu menu, View v, ContextMenuInfo menuInfo) {
	       
	      super.onCreateContextMenu(menu, v, menuInfo);
	      AdapterContextMenuInfo aInfo = (AdapterContextMenuInfo) menuInfo;
	       
	      String s = dishList.get(aInfo.position);
	   
	      menu.setHeaderTitle("Options for " + s);
	      menu.add(1, 1, 1, "Delete");
	       
	  }
	
	// Xoa mon 
	@Override
	public boolean onContextItemSelected(MenuItem item)
	{
//		int itemId = item.getItemId();
		AdapterContextMenuInfo aInfo = (AdapterContextMenuInfo) item.getMenuInfo();
		dishList.remove(aInfo.position);
		sa.notifyDataSetChanged();
		saveFile();
		return true;
		
	}
		
	//Doc danh sach mon tu file - Nhat
	@SuppressWarnings("unchecked")
	private void initDishes()
	{
		if(!myIO.checkEmptyFile(ALL_DISHES_FILE)){
			dishList = (ArrayList<String>) myIO.readObjectFromFile(ALL_DISHES_FILE);
		}else{
			dishList = new ArrayList<String>();
		}
	}
	
	// Them mon
	public void addDish(View view)
	{
		final Dialog d = new Dialog(this);
		d.setContentView(R.layout.dialog);
		d.setTitle("Add Dish");
		d.setCancelable(true);
		final EditText et = (EditText) d.findViewById(R.id.editText);
		Button b = (Button) d.findViewById(R.id.btThem);
		b.setOnClickListener(new View.OnClickListener()
			{
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					String mon = et.getText().toString();
					MainActivity.this.dishList.add(mon);
					MainActivity.this.sa.notifyDataSetChanged();
					saveFile();
					d.dismiss();										
				}					
			});
		d.show();
	}


	public void init() {
		this.randomDialog = new Dialog(this);		
		randomDialog.setContentView(R.layout.randomdialog);
		randomDialog.setTitle("Hom nay an...");
		randomDialog.setCancelable(true);
		final EditText et = (EditText) randomDialog.findViewById(R.id.chosenDish);
		et.setEnabled(false);
		
		Button buttonRandomAgain = (Button) randomDialog.findViewById(R.id.buttonRandomAgain);
		buttonRandomAgain.setOnClickListener(new View.OnClickListener()
			{

				@Override
				public void onClick(View v) {
					int randomDishId = r.nextInt(dishList.size());
					et.setText(dishList.get(randomDishId));
				}							
			});
    }
 
    public void showPopup() {
    	EditText et = (EditText) randomDialog.findViewById(R.id.chosenDish);
    	int randomDishId = r.nextInt(dishList.size());
		et.setText(dishList.get(randomDishId));
    	randomDialog.show();
    }
	
    public void saveFile(){
		ArrayList<String> arrayList = new ArrayList<String>(dishList); 
		myIO.writeObjectToFile(arrayList, ALL_DISHES_FILE);
    }
    
    public void randomDish(View v){
		showPopup();
    }
}
