package com.example.eat;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ContextMenu.ContextMenuInfo;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.AdapterContextMenuInfo;

public class MainActivity extends Activity {

	List<String> monList = new ArrayList<String>();
	ArrayAdapter<String> sa;
	Random r = new Random();
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
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
				Toast.makeText(MainActivity.this, "Item with id ["+arg3+"] - Position ["+arg2+"] -  ["+clickedView.getText()+"]", Toast.LENGTH_SHORT).show();
				
			}
		});
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
		monList.add("Com");
		monList.add("Pho");
		monList.add("Banh My");
	}
	//Test branch
	public void addMon(View view)
	{
		final Dialog d = new Dialog(this);
		d.setContentView(R.layout.dialog);
		d.setTitle("Thêm Món");
		d.setCancelable(true);
		final EditText et = (EditText) d.findViewById(R.id.editText);
		Button b = (Button) d.findViewById(R.id.btThem);
		b.setOnClickListener(new View.OnClickListener()
			{

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					String mon = et.getText().toString();
					MainActivity.this.monList.add(mon);
					MainActivity.this.sa.notifyDataSetChanged();
					d.dismiss();
					
					
				}
					
			});
		d.show();
				
		
	}

    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        
        return true;
    }
    
    public void openChonActivity(View view)
    {
//    	Intent intent = new Intent(this, ChonActivity.class);
//    	startActivity(intent);
    	String mon = monList.get(r.nextInt(monList.size()));
    	Toast t = Toast.makeText(MainActivity.this, "Hom nay an "+mon, Toast.LENGTH_LONG);
    	t.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
    	t.show();
    	

    	
    }    
    
}
