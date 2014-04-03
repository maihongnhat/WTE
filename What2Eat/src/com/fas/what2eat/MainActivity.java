package com.fas.what2eat;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import com.fas.lib.MyFileLib;
import com.fas.lib.MyLogger;

import android.os.Bundle;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ContextMenu.ContextMenuInfo;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.AdapterContextMenuInfo;

public class MainActivity extends Activity{

	/* Initializing variables:
	 * dishList 	: List of dishes
	 * sa			: Array Adapter
	 * r			: Random variable used for picking up a dish in the list
	 * randomDialog : Dialog which is shown when user taps on the Random button
	 * */	 
	List<String> dishList = new ArrayList<String>();
	ArrayAdapter<String> sa;
	Random r = new Random();
    Dialog randomDialog;
    public AutoCompleteTextView actv;
    
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
			sa = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,dishList);
			lv.setAdapter(sa);
			registerForContextMenu(lv);
		
	/* EDIT FUNCTION
	 * 		Purpose	: to rename the pre-assigned dish
	 * 		Usage	: tap on edit button		 
	 */
		lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
			    final Dialog d = new Dialog(arg0.getContext());			    
			    TextView clickedView = (TextView) arg1;		
			    final String mon = clickedView.getText().toString();				
				d.setContentView(R.layout.dialog_add);
				d.setTitle(getResources().getString(R.string.dialog_edit_title));
				d.setCancelable(true);
				
				//autoComplete function for EDIT
				String[] listDishAuto = getResources().getStringArray(R.array.list_dish_auto);
				final AutoCompleteTextView et = (AutoCompleteTextView) d.findViewById(R.id.autoCompleteTextView1);
				@SuppressWarnings({ "rawtypes", "unchecked" })
				ArrayAdapter adapter_auto = new ArrayAdapter(arg0.getContext(), android.R.layout.simple_list_item_1, listDishAuto);
				et.setAdapter(adapter_auto);
				//et.setTokenizer(new AutoCompleteTextView.CommaTokenizer());
								
				final int pos = arg2; 
				//final EditText et = (EditText) d.findViewById(R.id.editText);
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
		init();
	}
	
	/* Purpose: Sua 1 mon trong danh sach
	 * PIC: LamHV
	 */
	private void editDish(Dialog d, int pos, String monEdit) {
		if(!checkEmptyString(monEdit, getResources().getString(R.string.EmptyString)))
		{
			MainActivity.this.dishList.set(pos, monEdit);
			MainActivity.this.sa.notifyDataSetChanged();
			d.dismiss();
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
	
	// Hien thi menu Delete khi press and hold tren 1 item
	@Override
	public void onCreateContextMenu(ContextMenu menu, View v, ContextMenuInfo menuInfo) {
	       
	      super.onCreateContextMenu(menu, v, menuInfo);
	      AdapterContextMenuInfo aInfo = (AdapterContextMenuInfo) menuInfo;
	       
	      String s = dishList.get(aInfo.position);
	      menu.setHeaderTitle(getResources().getString(R.string.menu_header_title) + " " + s);
	      menu.add(1, 1, 1, getResources().getString(R.string.menu_delete_title));	       
	}

	@Override
	public boolean onContextItemSelected(MenuItem item) {
		deleteDish(item);
		return true;
	}
	
	/* Purpose: Xoa 1 mon trong danh sach 
	 * PIC: LamHV
	 */
	private void deleteDish(MenuItem item){
		AdapterContextMenuInfo aInfo = (AdapterContextMenuInfo) item.getMenuInfo();
		dishList.remove(aInfo.position);
		sa.notifyDataSetChanged();
		saveFile();		
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
	
	/* Purpose: Su kien khi click vao button them mon an - hien thi dialog them
	 * PIC: LamHV
	 */
	public void addDish(View view) {
		final Dialog d = new Dialog(this);
		d.setContentView(R.layout.dialog_add);
		d.setTitle(getResources().getString(R.string.dialog_add_title));
		d.setCancelable(true);
		
		//initialize and implement the auto Complete function
		String[] listDishAuto = getResources().getStringArray(R.array.list_dish_auto);
		final AutoCompleteTextView et = (AutoCompleteTextView) d.findViewById(R.id.autoCompleteTextView1);
		@SuppressWarnings({ "rawtypes", "unchecked" })
		ArrayAdapter adapter_auto = new ArrayAdapter(this, android.R.layout.simple_list_item_1, listDishAuto);
		et.setAdapter(adapter_auto);
		//et.setTokenizer(new AutoCompleteTextView.CommaTokenizer());
				
		Button b = (Button) d.findViewById(R.id.btThem);
		b.setOnClickListener(new View.OnClickListener()	{
				@Override
				public void onClick(View v) {
					addDish(d);
				}
			});
		d.show();
	}
	
	
	/* Purpose: Them 1 mon an vao danh sach mon khi click vao nut them tren dialog
	 * PIC: Huynh Van Lam
	 */

	public void addDish(Dialog d){
		final AutoCompleteTextView et = (AutoCompleteTextView) d.findViewById(R.id.autoCompleteTextView1);

		String mon = et.getText().toString();
		if(!checkEmptyString(mon, getResources().getString(R.string.checkEmptyNoti)))
		{
			MainActivity.this.dishList.add(mon);
			MainActivity.this.sa.notifyDataSetChanged();
			saveFile();
			d.dismiss();
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
				et.setText(dishList.get(randomDishId));
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
    		et.setText(dishList.get(randomDishId));
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
		ArrayList<String> arrayList = new ArrayList<String>(dishList); 
		myIO.writeObjectToFile(arrayList, ALL_DISHES_FILE);
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
