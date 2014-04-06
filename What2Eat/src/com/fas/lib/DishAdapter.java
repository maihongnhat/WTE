package com.fas.lib;

import java.util.List;

import com.fas.what2eat.*;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class DishAdapter extends ArrayAdapter<Dish> {

	private List<Dish> dishList;
	private Context context;
	
	public DishAdapter(List<Dish> dl, Context ctx){
		super(ctx,R.layout.main_row,dl);
		this.dishList = dl;
		this.context = ctx;
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		
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
	  
	    return convertView;
	}
}
