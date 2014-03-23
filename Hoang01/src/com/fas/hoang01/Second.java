package com.fas.hoang01;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class Second extends Activity {
	@Override
	public void onCreate(Bundle savedInstanceState) {		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.second);
		Intent intent 	= getIntent();
		String chosen 	= intent.getExtras().getString("theChosenDish");
		TextView tv10 	= (TextView)findViewById(R.id.textView11);
				tv10.setText("Bạn hãy chọn " + chosen);
	}
}
