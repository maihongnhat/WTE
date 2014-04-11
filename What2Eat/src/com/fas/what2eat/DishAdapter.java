package com.fas.what2eat;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import com.fas.what2eat.*;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ListView;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.TextView;

public class DishAdapter extends ArrayAdapter<Dish> {

	private ArrayList<Dish> dishList;
	private Context context;
	private HashMap<Integer, Boolean> mSelection = new HashMap<Integer, Boolean>();
	private ArrayList<Dish> removeList = new ArrayList<Dish>();
	
	public DishAdapter(ArrayList<Dish> dl, Context ctx){
		super(ctx,R.layout.main_row,dl);
		this.dishList = dl;
		this.context = ctx;
	}
	
	@Override
	public View getView(final int position, View convertView, final ViewGroup parent) {
		
	    // First let's verify the convertView is not null
	    if (convertView == null) {
	        // This a new view we inflate the new layout
	        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	        convertView = inflater.inflate(R.layout.main_row, parent, false);
	    }
	        // Now we can fill the layout with the right values
	        TextView tv = (TextView) convertView.findViewById(R.id.name);
	        Dish d = dishList.get(position);
	        tv.setText(d.getName());
	        
	        
	    CheckBox chb = (CheckBox) convertView.findViewById(R.id.chk);
	    chb.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				ListView lv = (ListView) parent;
				lv.setItemChecked(position, isChecked);	
			}
		});
	    
	    
	    
        if (mSelection.get(position) != null) {
            
            chb.setChecked(true);
            removeList.add(dishList.get(position));
//            	convertView.setBackgroundColor(Color.BLUE);// this is a selected position so make it red
        }else
        {
        	chb.setChecked(false);
        	removeList.remove(dishList.get(position));
        }
        return convertView;
	}
	
	public ArrayList<Dish> getRemoveList()
	{
		return removeList;
	}
	

	public void setNewSelection(int position, boolean value) {
        mSelection.put(position, value);
        notifyDataSetChanged();
    }

    public boolean isPositionChecked(int position) {
        Boolean result = mSelection.get(position);
        return result == null ? false : result;
    }

    public Set<Integer> getCurrentCheckedPosition() {
        return mSelection.keySet();
    }

    public int getSelectedCount()
    {
    	return mSelection.size();
    }

    public void removeSelection(int position) {
        mSelection.remove(position);
        notifyDataSetChanged();
    }

    public void clearSelection() {
        mSelection = new HashMap<Integer, Boolean>();
        notifyDataSetChanged();
    }
    
    public void addDish(String d){
    	Dish di = new Dish(d);
		dishList.add(di);
		notifyDataSetChanged();
    }
    
    /* Purpose: Sua 1 mon trong danh sach
	 * PIC: LamHV
	 */
    
	public void editDish(int pos, String dish) {
			Dish d = new Dish(dish);
			dishList.set(pos, d);
			notifyDataSetChanged();
	}

	/* Purpose: Xoa mon trong danh sach
	 * PIC: LamHV
	 */
	public void removeSelectedDishes() {
    	dishList.removeAll(removeList);
    	notifyDataSetChanged();
		
	}
}
