package com.money;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.widget.TextView;
import android.widget.Toast;
import android.database.sqlite.SQLiteDatabase;
import android.content.ContentValues;
import android.widget.Spinner;
import android.widget.Toast;
import android.widget.ArrayAdapter;

public class addaccount extends Activity 
{ 
	
	private static String DB_NAME = "money.db";
	private static int DB_VERSION = 1;
	private static int POSTION;
	
	private SQLiteDatabase db;
	private SQLiteHelper dbHelper;
	
	private EditText accname;
	private Spinner accclassify;
	private EditText money;
	private EditText commit;
	
	public void onCreate(Bundle savedInstanceState)
	{
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.addcount);
	    
    	try{
    		dbHelper = new SQLiteHelper(this, DB_NAME, null, DB_VERSION);
    		db = dbHelper.getWritableDatabase();
    	}
		catch(IllegalArgumentException e){
    		e.printStackTrace();
    		++ DB_VERSION;
    		dbHelper.onUpgrade(db, --DB_VERSION, DB_VERSION);
    	}	   
		
		accname = (EditText) findViewById(R.id.accname);
		accclassify = (Spinner) findViewById(R.id.accclassify);
		money = (EditText) findViewById(R.id.money);
		commit = (EditText) findViewById(R.id.commit);
		
		String account_classify[] = this.getResources().getStringArray(R.array.accclassify_list);
		
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, account_classify);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		accclassify.setAdapter(adapter);		
		
		
	    Button accadd=(Button) findViewById(R.id.addacc);
	    accadd.setOnClickListener(new Button.OnClickListener()
	     {
	        	public void onClick(View v)
	        	{
	        		//fetch data
					ContentValues values = new ContentValues();
					values.put(Account_item.ACCOUNT, accname.getText().toString().trim());
					values.put(Account_item.CLASSIFY, accclassify.getSelectedItem().toString().trim());
					values.put(Account_item.MONEY, money.getText().toString().trim());
					values.put(Account_item.COMMIT, commit.getText().toString().trim());
					values.put(Account_item.COST, money.getText().toString().trim());
					
					//SQL
					
			    	try{
						Long AccountID = db.insert(SQLiteHelper.TB_NAME_A, Account_item.ID, values);
			    	}
					catch(IllegalArgumentException e){
			    		e.printStackTrace();
			    		++ DB_VERSION;
			    		dbHelper.onUpgrade(db, --DB_VERSION, DB_VERSION);
			    	}
					Toast.makeText(addaccount.this, "add success.", Toast.LENGTH_LONG).show();	        		
	        	}
	      }
	    );
	    
	    Button exitb=(Button) findViewById(R.id.exitb);
	    exitb.setOnClickListener(new Button.OnClickListener()
	     {
	        	public void onClick(View v)
	        	{
	        		db.close();
	        		dbHelper.close();

	        		Intent intent = new Intent();
	        		intent.setClass(addaccount.this, MoneyListView.class);

	        		startActivity(intent);
	        		addaccount.this.finish();	  
	        	}
	      }
	    );

	    
	      
	  }
	
	
}