package com.fas.what2eat;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.fas.lib.Dish;
import com.fas.lib.DishAdapter;
import com.fas.lib.MyFileLib;
import com.fas.lib.MyLogger;

import android.os.Bundle;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.view.ActionMode;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ContextMenu.ContextMenuInfo;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AbsListView.MultiChoiceModeListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.AdapterContextMenuInfo;
 
@SuppressLint("NewApi")
public class MainActivity extends Activity{

	/* Initializing variables:
	 * dishList 	: List of dishes
	 * sa			: Array Adapter
	 * r			: Random variable used for picking up a dish in the list
	 * randomDialog : Dialog which is shown when user taps on the Random button
	 * */	 
	ArrayList<Dish> dishList = new ArrayList<Dish>();
	DishAdapter dap;
	Random r = new Random();
    Dialog randomDialog;
    
    
    public static final String ALL_DISHES_FILENAME = "AllDishes.txt";
	public File ALL_DISHES_FILE;
	public MyFileLib myIO;
	public MyLogger myLogger;
	
	/*	onCreate method of the MainActivity
	 * 
	 * 		Create new File for saving and loading
	 * 		Load listview from the layout
	 */
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		String allDishesFilePath = getFilesDir() + "/" + ALL_DISHES_FILENAME;
			ALL_DISHES_FILE = new File(allDishesFilePath);		
			myIO = new MyFileLib();
			myIO.createFile(ALL_DISHES_FILE);
		
		initDishes();
		
		ListView lv = (ListView) findViewById(R.id.listview);
			
		dap = new DishAdapter(dishList, this);
			
		lv.setAdapter(dap);
		
		/** For contextual action mode, the choice mode should be CHOICE_MODE_MULTIPLE_MODAL */
		lv.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE_MODAL);
		
		/** Setting multichoicemode listener for the listview */
        lv.setMultiChoiceModeListener(new MultiChoiceModeListener() {
			
        	private int nr = 0;
        	
        	@Override
			public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
				return false;
			}
			
			@Override
			public void onDestroyActionMode(ActionMode mode) {
				
			}
			
			/** This will be invoked when action mode is created. In our case , it is on long clicking a menu item */
			@Override
			public boolean onCreateActionMode(ActionMode mode, Menu menu) {
				nr=0;
				getMenuInflater().inflate(R.menu.context_menu, menu);
				return true;
			}
			
			/** Invoked when an action in the action mode is clicked */
			@Override
			public boolean onActionItemClicked(ActionMode mode, MenuItem item) {				
				Toast.makeText(getBaseContext(), "Test", Toast.LENGTH_LONG).show();								
				return false;
			}
			
			@Override
			public void onItemCheckedStateChanged(ActionMode mode, int position, long id, boolean checked) {
			}
		});
			
	/* EDIT FUNCTION
	 * 		Purpose	: to rename the pre-assigned dish
	 * 		Usage	: tap on edit button		 
	 */
		lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
			    final Dialog d = new Dialog(arg0.getContext());			    
			    TextView nameView = (TextView) arg1.findViewById(R.id.name);	
			    final String mon = nameView.getText().toString();				
				d.setContentView(R.layout.dialog);
				d.setTitle(getResources().getString(R.string.dialog_edit_title));
				d.setCancelable(true);
												
				final int pos = arg2; 
				final EditText et = (EditText) d.findViewById(R.id.editText);
				et.append(mon);
				Button b = (Button) d.findViewById(R.id.btThem);
				b.setOnClickListener(new View.OnClickListener() {
					@Override
					public void onClick(View v) {							
							String monEdit = et.getText().toString();
							editDish(d,pos,monEdit);												
					}							
					});
				d.show();
			}
		});	
		
		registerForContextMenu(lv);
		init();
	}
	
	/* Purpose: Sua 1 mon trong danh sach
	 * PIC: LamHV
	 */
	private void editDish(Dialog d, int pos, String monEdit) {
		if(!checkEmptyString(monEdit, getResources().getString(R.string.EmptyString)))
		{
			Dish dis = new Dish(monEdit);
			dishList.set(pos, dis);
			dap.notifyDataSetChanged();
			d.dismiss();
			saveFile();
		}			
	}
	

	/* MENU INFLATER
	 * Purpose	: Inflate option menu when tapping on the Menu button on the Menu bar
	 */
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
//	// Hien thi menu Delete khi press and hold tren 1 item
//	@Override
//	public void onCreateContextMenu(ContextMenu menu, View v, ContextMenuInfo menuInfo) {
//	       
//	      super.onCreateContextMenu(menu, v, menuInfo);
//	      AdapterContextMenuInfo aInfo = (AdapterContextMenuInfo) menuInfo;
//	       
//	      String s = dishList.get(aInfo.position);
//	      menu.setHeaderTitle(getResources().getString(R.string.menu_header_title) + " " + s);
//	      menu.add(1, 1, 1, getResources().getString(R.string.menu_delete_title));	       
//	}

//	@Override
//	public boolean onContextItemSelected(MenuItem item) {
//		deleteDish(item);
//		return true;
//	}
	
//	/* Purpose: Xoa 1 mon trong danh sach 
//	 * PIC: LamHV
//	 */
//	private void deleteDish(MenuItem item){
//		AdapterContextMenuInfo aInfo = (AdapterContextMenuInfo) item.getMenuInfo();
//		dishList.remove(aInfo.position);
//		dap.notifyDataSetChanged();
//		saveFile();		
//	}	
		
	//Doc danh sach mon tu file - Nhat
	@SuppressWarnings("unchecked")
	private void initDishes()
	{
		if(!myIO.checkEmptyFile(ALL_DISHES_FILE)){
			
			dishList = (ArrayList<Dish>) myIO.readObjectFromFile(ALL_DISHES_FILE);
		}else{
			dishList = new ArrayList<Dish>();
		}
	}
	
	/* Purpose: Su kien khi click vao button them mon an - hien thi dialog them
	 * PIC: LamHV
	 */
	public void addDish(View view){
		EditText et = (EditText) findViewById(R.id.etDish);
		String dish = et.getText().toString();
		if(!checkEmptyString(dish, getResources().getString(R.string.checkEmptyNoti)))
		{
			Dish di = new Dish(dish);
			dishList.add(di);
			et.setText(null);
			MainActivity.this.dap.notifyDataSetChanged();
			saveFile();
		}
	}
		
	/* INITIALIZING THE RANDOM DIALOG
	 * 	Purpose	: To prepare for the random dialog before user tapping on the random button
	 * 
	 */
	public void init() {
		this.randomDialog = new Dialog(this);		
		randomDialog.setContentView(R.layout.randomdialog);
		randomDialog.setTitle(getResources().getString(R.string.dialog_random_title));
		randomDialog.setCancelable(true);
		final EditText et = (EditText) randomDialog.findViewById(R.id.chosenDish);
		et.setEnabled(false);
		
		Button buttonRandomAgain = (Button) randomDialog.findViewById(R.id.buttonRandomAgain);
		buttonRandomAgain.setOnClickListener(new View.OnClickListener()	{
			@Override
			public void onClick(View v) {
				int randomDishId = r.nextInt(dishList.size());
				et.setText(dishList.get(randomDishId).getName());
			}							
			});
	}
	
	
	/* SHOW RANDOM DIALOG
	 * 	Purpose	: Pick one dish randomly and show it on the dialog
	 */
    public void showPopup() {
    	int size = dishList.size();
    	if(size >0)	{
    		EditText et = (EditText) randomDialog.findViewById(R.id.chosenDish);
        	int randomDishId = r.nextInt(size);
    		et.setText(dishList.get(randomDishId).getName());
        	randomDialog.show();
    	}
    	
    }
    
    
    /* RANDOM DISH
     *  Purpose : Implement the show Pop method
     * 
     */
    public void randomDish(View v){
		showPopup();
    }
	
    public void saveFile(){
		myIO.writeObjectToFile(dishList, ALL_DISHES_FILE);
    }    
    
    @SuppressLint("NewApi")
	private Boolean checkEmptyString(String s, String alert){
    	if(s.isEmpty())
    	{
    		Toast.makeText(this, alert, Toast.LENGTH_SHORT).show();
    		return true;
    	}
    	return false;
    }
}
