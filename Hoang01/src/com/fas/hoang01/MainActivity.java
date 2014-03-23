package com.fas.hoang01;

import java.util.Random;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.Button;


public class MainActivity extends Activity {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);		

		Button b = (Button) findViewById(R.id.button1);
		b.setOnClickListener
        (
            new View.OnClickListener()
            {
                @Override 
                public void onClick(View v){
                	String[] edit_text_value 	= new String[4];
        	  	  	EditText edit_text1 	= (EditText)findViewById(R.id.editText1);
        	        	edit_text_value[0] 	= 	edit_text1.getText().toString();
        	        EditText edit_text2 	= (EditText)findViewById(R.id.editText2);
        	        	edit_text_value[1] 	= 	edit_text2.getText().toString();
        	        EditText edit_text3 	= (EditText)findViewById(R.id.editText3);
        	        	edit_text_value[2] 	= 	edit_text3.getText().toString();
        	    	EditText edit_text4 	= (EditText)findViewById(R.id.editText4);
        	        	edit_text_value[3] 	= 	edit_text4.getText().toString();
                	
                	  Random rn 		= 	new Random();
        	          int num 			= 	rn.nextInt(4);
        	          String chosen		= edit_text_value[num].toString();
        	          //TextView tv2 			= (TextView)findViewById(R.id.textView2);
        			  //tv2.setText("Bạn hãy chọn "+chosen);
        			  
        			  Intent intent = new Intent(MainActivity.this, Second.class);
    			      intent.putExtra("theChosenDish", chosen);
    			      startActivity(intent);
    			      //Lam test
                }
            }
        );
			
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
