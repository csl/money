package com.money;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.content.ContentValues;
import android.widget.Spinner;
import android.widget.Toast;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;

public class SetBudget extends Activity 
{
	private static String DB_NAME = "money.db";
	private static int DB_VERSION = 1;
	private static final int EDIT=1;
	
	private SQLiteDatabase db;
	private SQLiteHelper dbHelper;
	private Cursor cursor;

	//layout
	private Spinner stype;
	private Spinner syear;
	private TextView vmoney;
	
	//mylayout
	private LinearLayout mylayout;
	private TextView tview[];
	private EditText etext[];
	int edittextlen;

	public void onCreate(Bundle savedInstanceState)
	{
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.budget);
	 
    	try{
    		dbHelper = new SQLiteHelper(this, DB_NAME, null, DB_VERSION);
    		db = dbHelper.getWritableDatabase();
    	}
		catch(IllegalArgumentException e){
    		e.printStackTrace();
    		++ DB_VERSION;
    		dbHelper.onUpgrade(db, --DB_VERSION, DB_VERSION);
    	}
		
		stype = (Spinner) findViewById(R.id.stype);
		syear = (Spinner) findViewById(R.id.syear);
		vmoney = (TextView) findViewById(R.id.money);
		mylayout = (LinearLayout)findViewById(R.id.mylayout);

		//add date_year spinner
		String type_list[] = this.getResources().getStringArray(R.array.budget_type);
		ArrayAdapter<String> type_adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, type_list);
		type_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		stype.setAdapter(type_adapter);
		
		
		stype.setOnItemSelectedListener(new Spinner.OnItemSelectedListener(){
	           public void onItemSelected(AdapterView adapterView, View view, int position, long id)
	           {
	        	   update_layout(position);
	           }
	           public void onNothingSelected(AdapterView arg0) 
	           {
	           }
	        });
		
		
		//add date_year spinner
		String date_year_list[] = this.getResources().getStringArray(R.array.year_list);
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, date_year_list);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		syear.setAdapter(adapter);
		
		syear.setSelection(1);
		
		//add button
		Button additem=(Button) findViewById(R.id.id_add);
		additem.setOnClickListener(new Button.OnClickListener()
	     {
	        	public void onClick(View v)
	        	{
	        		/*
	        		//fetch data
					ContentValues values = new ContentValues();
					values.put(Memo_item.DATE, date_d.getText().toString().trim());
					values.put(Memo_item.TEXT, text.getText().toString().trim());
					
					//SQL
					
			    	try{
						Long cityID = db.insert(SQLiteHelper.TB_NAME_M, Memo_item.ID, values);
			    	}
					catch(IllegalArgumentException e){
			    		e.printStackTrace();
			    		++ DB_VERSION;
			    		dbHelper.onUpgrade(db, --DB_VERSION, DB_VERSION);
			    	}
					Toast.makeText(SetBudget.this, "add success.", Toast.LENGTH_LONG).show();
					updateShow();*/
	        	}
	      }
	    );
		

		//exit button
	    Button exit_b=(Button) findViewById(R.id.exit_b);
	    exit_b.setOnClickListener(new Button.OnClickListener()
	     {
	        	public void onClick(View v)
	        	{
	        		db.close();
	        		dbHelper.close();
	        		
	        		Intent intent = new Intent();
	        		intent.setClass(SetBudget.this, MoneyListView.class);

	        		startActivity(intent);
	        		SetBudget.this.finish();	  
	        	}
	      }
	    );

	}
	
	private void update_layout(int position)
	{
		//openOptionsDialog(Integer.toString(position));
		
		if (position == 0)
		{
		    tview= new TextView[12];
			etext = new EditText[12];
		    for(int i=0;i<12;i++)
		    {
		    	tview[i] = new TextView(this);
		    	tview[i].setText(Integer.toString(i+1));
		    	etext[i] = new EditText(this);
		    	etext[i].setText("0");
		    	mylayout.addView(tview[i]);
		    	mylayout.addView(etext[i]);
		     }
		}
		else 
		{
			edittextlen = 12;
		    tview= new TextView[edittextlen];
			etext = new EditText[edittextlen];
		    for(int i=0;i<12;i++)
		    {
		    	tview[i] = new TextView(this);
		    	tview[i].setText(Integer.toString(i+1));
		    	etext[i] = new EditText(this);
		    	etext[i].setText("0");
		    	mylayout.addView(tview[i]);
		    	mylayout.addView(etext[i]);
		     }
		}
	}
            
    //error message
    private void openOptionsDialog(String info)
	{
	    new AlertDialog.Builder(this)
	    .setTitle("Inquire")
	    .setMessage(info)
	    .setPositiveButton("OK",
	        new DialogInterface.OnClickListener()
	        {
	         public void onClick(DialogInterface dialoginterface, int i)
	         {
	         }
	         }
	        )
	    .show();
	}
}