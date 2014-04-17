package com.fas.what2eat;

import java.io.File;
import java.util.ArrayList;
import java.util.Random;

import com.fas.lib.MyFileLib;

import android.os.Bundle;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.support.v4.app.Fragment;
import android.view.ActionMode;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AbsListView.MultiChoiceModeListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
 
@SuppressLint("NewApi")  
public class FragmentMeal extends Fragment{

	/* Initializing variables:
	 * dishList 	: List of dishes
	 * sa			: Array Adapter
	 * r			: Random variable used for picking up a dish in the list
	 * randomDialog : Dialog which is shown when user taps on the Random button
	 */	 
	private ArrayList<Dish> dishList = new ArrayList<Dish>();
	private DishAdapter dap;
	private Dialog randomDialog;
	
	private Random r = new Random();
	private int lastRandomValue = -1;        
    private String outputFileName;
	private File outputFile;
	private MyFileLib myIO;
	private Activity currentActivity;
	private int layoutId;
	public ListView lv;
	private ActionMode actionMode;
		
	
	
	 @Override
	 public void onAttach(Activity activity) {
	        super.onAttach(activity);
	        currentActivity = activity;
	 }
	
	/*	onCreate method of the MainActivity
	 * 
	 * 		Create new File for saving and loading
	 * 		Load listview from the layout
	 */
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		View viewMeal = inflater.inflate(layoutId, container, false);
		
		String allDishesFilePath = MainActivity.outputPath + "/" + outputFileName;
			outputFile = new File(allDishesFilePath);		
			myIO = new MyFileLib();
			myIO.createFile(outputFile);
		
		initDishes();
		
		lv = (ListView) viewMeal.findViewById(R.id.listview);
		
		dap = new DishAdapter(dishList, currentActivity);
			
		lv.setAdapter(dap);
		
		/** For contextual action mode, the choice mode should be CHOICE_MODE_MULTIPLE_MODAL */
		lv.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE_MODAL);
		
		/** Setting multichoicemode listener for the listview */
        lv.setMultiChoiceModeListener(new MultiChoiceModeListener() {
			
            
            @Override
            public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
                return false;
            }
             
            @Override
            public void onDestroyActionMode(ActionMode mode) {
                 dap.clearSelection();
            }
             
            @Override
            public boolean onCreateActionMode(ActionMode mode, Menu menu) {
                MenuInflater inflater = currentActivity.getMenuInflater();
                inflater.inflate(R.menu.context_menu, menu);
                return true;
            }
             
            @Override
            public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
                switch (item.getItemId()) {
                 
                    case R.id.delete:
                    	dap.removeSelectedDishes();
                        dap.clearSelection();
                        saveFile();
                        mode.finish();
                        
                }
                return true;
            }
             
            @Override
            public void onItemCheckedStateChanged(ActionMode mode, int position,
                    long id, boolean checked) {
                 if (checked) {
                        dap.setNewSelection(position, checked);                   
                    } else {
                        dap.removeSelection(position);                
                    }                 
                 actionMode = mode;
                    mode.setTitle(dap.getSelectedCount() + " selected");
                 
            }
		});
        
//        lv.setOnFocusChangeListener(new View.OnFocusChangeListener() {
//			
//			@Override
//			public void onFocusChange(View v, boolean hasFocus) {
//				if (hasFocus == false){
//					actionMode.finish();
//				}
//				
//			}
//		});
			
	/* EDIT FUNCTION
	 * 		Purpose	: to rename the pre-assigned dish
	 * 		Usage	: tap on a dish in listview		 
	 */
		lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> view, View arg1, int pos, long arg3) {
				TextView nameView = (TextView) arg1.findViewById(R.id.name);	
			    final String mon = nameView.getText().toString();	
			    editDish(view, pos, mon);
			}
		});	
		
		registerForContextMenu(lv);
		initDialogRandom();	
		
		Button buttonAdd = (Button) viewMeal.findViewById(R.id.buttonAdd);
		buttonAdd.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				addDish(v);
			}
		});
		
		Button buttonRandom = (Button) viewMeal.findViewById(R.id.buttonRandom);
		buttonRandom.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				showDialogRandom();		
			}
		});
		
		return viewMeal;
	}
	
	/* Purpose: Sua 1 mon trong danh sach
	 * PIC: LamHV
	 */
	private void editDish(AdapterView<?> view, final int pos, String dish) {
		
			final Dialog dialogEdit = new Dialog(view.getContext());			    			
			dialogEdit.setContentView(R.layout.dialog_edit);
			dialogEdit.setTitle(getResources().getString(R.string.dialog_edit_title));
			dialogEdit.setCancelable(true);
			final EditText et = (EditText) dialogEdit.findViewById(R.id.editText);
			et.append(dish);
			Button buttonEdit = (Button) dialogEdit.findViewById(R.id.buttonEdit);
			buttonEdit.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {							
						String monEdit = et.getText().toString();
						if(!checkEmptyString(monEdit, getResources().getString(R.string.EmptyString)))
						{
							dap.editDish(pos, monEdit);
							dialogEdit.dismiss();
							saveFile();			
						}
				}							
				});
			dialogEdit.show();
			
					
	}
	

	/* MENU INFLATER
	 * Purpose	: Inflate option menu when tapping on the Menu button on the Menu bar
	 */
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		currentActivity.getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
			
	//Doc danh sach mon tu file - Nhat
	@SuppressWarnings("unchecked")
	private void initDishes()
	{
		if(!myIO.checkEmptyFile(outputFile)){			
			dishList = (ArrayList<Dish>) myIO.readObjectFromFile(outputFile);
		}else{
			dishList = new ArrayList<Dish>();
		}
	}
	
	/* Purpose: Su kien khi click vao button them mon an - hien thi dialog them
	 * PIC: LamHV
	 */
	public void addDish(View view){
		EditText et = (EditText) this.getView().findViewById(R.id.etDish);
		String dish = et.getText().toString();
		if(!checkEmptyString(dish, getResources().getString(R.string.checkEmptyNoti)))
		{
			dap.addDish(dish);
			et.setText(null);
			saveFile();
		}
	}
		
	/* INITIALIZING THE RANDOM DIALOG
	 * 	Purpose	: To prepare for the random dialog before user tapping on the random button
	 * 
	 */
	public void initDialogRandom() {
		this.randomDialog = new Dialog(currentActivity);		
		randomDialog.setContentView(R.layout.dialog_random);
		randomDialog.setTitle(getResources().getString(R.string.dialog_random_title));
		randomDialog.setCancelable(true);
		final EditText et = (EditText) randomDialog.findViewById(R.id.chosenDish);
		et.setEnabled(false);
		
		Button buttonRandomAgain = (Button) randomDialog.findViewById(R.id.buttonRandomAgain);
		buttonRandomAgain.setOnClickListener(new View.OnClickListener()	{
			@Override
			public void onClick(View v) {
				int randomDishId = generateRandomValue(dishList.size());
				et.setText(dishList.get(randomDishId).getName());
			}							
			});
	}
	
	
	/* SHOW RANDOM DIALOG
	 * 	Purpose	: Pick one dish randomly and show it on the dialog
	 */
    public void showDialogRandom() {
    	int size = dishList.size();
    	if(size > 0)	{
    		EditText et = (EditText) randomDialog.findViewById(R.id.chosenDish);
        	int randomDishId = generateRandomValue(size);
    		et.setText(dishList.get(randomDishId).getName());
        	randomDialog.show();
    	}
    	
    }
	
    public void saveFile(){
		myIO.writeObjectToFile(dishList, outputFile);
    }    
    
    @SuppressLint("NewApi")
	private Boolean checkEmptyString(String s, String alert){
    	if(s.isEmpty())
    	{
    		Toast.makeText(currentActivity, alert, Toast.LENGTH_SHORT).show();
    		return true;
    	}
    	return false;
    }
    
    private int generateRandomValue(int range){
    	int returnValue;
    	if(dishList.size() == 1){
    		returnValue = 0;
    	}else{
	    	do{
	    		returnValue = r.nextInt(range);
	    	}while(returnValue == lastRandomValue);
    	}
    	lastRandomValue = returnValue;
    	return returnValue;
    }
    
    public void setLayoutId(int layoutId) {
		this.layoutId = layoutId;
	}
    
    public void setOutputFileName(String outputFileName) {
		this.outputFileName = outputFileName;
	}
    
    public ActionMode getActionMode() {
		return actionMode;
	}

}
