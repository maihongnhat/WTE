package com.example.eat;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.os.Bundle;
import android.app.Activity;
import android.app.Dialog;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

public class MonActivity extends Activity {

	List<String> monList = new ArrayList<String>();
	ArrayAdapter<String> sa;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_mon);
		
		initMon();
		
		ListView lv = (ListView) findViewById(R.id.listView1);
		
		sa = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,monList);
		
		lv.setAdapter(sa);
		
		registerForContextMenu(lv);
		
		lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				TextView clickedView = (TextView) arg1;
				Toast.makeText(MonActivity.this, "Item with id ["+arg3+"] - Position ["+arg2+"] -  ["+clickedView.getText()+"]", Toast.LENGTH_SHORT).show();
				
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.mon, menu);
		return true;
	}
	
	@Override
	public void onCreateContextMenu(ContextMenu menu, View v, ContextMenuInfo menuInfo) {
	       
	      super.onCreateContextMenu(menu, v, menuInfo);
	      AdapterContextMenuInfo aInfo = (AdapterContextMenuInfo) menuInfo;
	       
	      String s = monList.get(aInfo.position);
	   
	      menu.setHeaderTitle("Options for " + s);
	      menu.add(1, 1, 1, "Delete");
	       
	  }
	
	@Override
	public boolean onContextItemSelected(MenuItem item)
	{
		int itemId = item.getItemId();
		AdapterContextMenuInfo aInfo = (AdapterContextMenuInfo) item.getMenuInfo();
		monList.remove(aInfo.position);
		sa.notifyDataSetChanged();
		return true;
		
	}
	
	private void initMon()
	{
		monList.add("Demo");
		monList.add("Demo1");
		monList.add("Demo2");
	}
	
	public void addMon(View view)
	{
		final Dialog d = new Dialog(this);
		d.setContentView(R.layout.dialog);
		d.setTitle("Thêm món");
		d.setCancelable(true);
		final EditText et = (EditText) d.findViewById(R.id.editText);
		Button b = (Button) d.findViewById(R.id.btThem);
		b.setOnClickListener(new View.OnClickListener()
			{

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					String mon = et.getText().toString();
					MonActivity.this.monList.add(mon);
					MonActivity.this.sa.notifyDataSetChanged();
					d.dismiss();
					
					
				}
					
			});
		d.show();
				
		
	}

}
