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
	private int edittextlen;
	
	private int nodata;
	private String null_b;
	private String notnumber;
	private String budget_b;

	public void onCreate(Bundle savedInstanceState)
	{
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.budget);
	 
	    //Initial data
	    nodata = 0;

	    null_b = (String) this.getResources().getText(R.string.budget_null);
	    budget_b = (String) this.getResources().getText(R.string.budget_total);
	    notnumber= (String) this.getResources().getText(R.string.budget_notnumber);
	    
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
		vmoney = (TextView) findViewById(R.id.allbudget);
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
		syear.setOnItemSelectedListener(new Spinner.OnItemSelectedListener(){
	           public void onItemSelected(AdapterView adapterView, View view, int position, long id)
	           {
	        	   update_layout(stype.getSelectedItemPosition());
	           }
	           public void onNothingSelected(AdapterView arg0) 
	           {
	           }
	        });
		
		syear.setSelection(1);
		
		//add button
		Button additem=(Button) findViewById(R.id.id_add);
		additem.setOnClickListener(new Button.OnClickListener()
	     {
	        	public void onClick(View v)
	        	{
	        		int total_m = 0;
	        		String mdata = "";
	        		String myear = syear.getSelectedItem().toString().trim();
					int type = stype.getSelectedItemPosition();
	        		
	        		//fetch EditText data
	    		    for(int i=0; i<edittextlen; i++)
	    		     {
	    		    	if (etext[i].getText().toString().equals("")) 
	    		    	{
	    		    		openOptionsDialog(null_b);
	    		    		return;
	    		    	}
	    		    	
	    		    	try {
	    		    			total_m = total_m + Integer.parseInt(etext[i].getText().toString());
	    		    		} 
	    		    	catch(Exception e) {
		    		    		openOptionsDialog(notnumber);
		    		    		return;
	    		    		}
	    		    	
	    		    	if (i==0)
	    		    	{
	    		    		mdata = mdata + etext[i].getText();
	    		    	}
	    		    	else
	    		    	{
	    		    		mdata = mdata + "," + etext[i].getText();
	    		    	}
	    		     }
	    		    
	        		//fetch data
					ContentValues values = new ContentValues();
					values.put(Budget_item.YEAR , myear);
					values.put(Budget_item.TYPE, Integer.toString(type));
					values.put(Budget_item.DATA, mdata);
					if (nodata == 1)
					{
						//SQL
				    	try{
							Long cityID = db.insert(SQLiteHelper.TB_NAME_B, Budget_item.ID, values);
				    	}
						catch(IllegalArgumentException e){
				    		e.printStackTrace();
				    		++ DB_VERSION;
				    		dbHelper.onUpgrade(db, --DB_VERSION, DB_VERSION);
				    	}
						nodata=0;
						Toast.makeText(SetBudget.this, "save success.", Toast.LENGTH_LONG).show();
					}
					else
					{
       				 String where = Budget_item.YEAR + "='" + myear + "' and " + Budget_item.TYPE + "='" + type + "'";

						//SQL
				    	try{
    						int cityID = db.update(SQLiteHelper.TB_NAME_B, values, where, null);
				    	}
						catch(IllegalArgumentException e){
				    		e.printStackTrace();
				    		++ DB_VERSION;
				    		dbHelper.onUpgrade(db, --DB_VERSION, DB_VERSION);
				    	}
						Toast.makeText(SetBudget.this, "save success.", Toast.LENGTH_LONG).show();
		        	}
		    	    vmoney.setText(budget_b + total_m);
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
		nodata=0;
		
		//clear all views
		mylayout.removeAllViews();

		//Query exist or not
		String myear = syear.getSelectedItem().toString().trim();
		String classify_list[] = this.getResources().getStringArray(R.array.classify_list);
		int total_m = 0;
	
		int type = stype.getSelectedItemPosition();		
		if (type == 0)
		{
			edittextlen = classify_list.length;
		}
		else
		{
			edittextlen=12;			
		}

		String data_i[] = new String[edittextlen];
		String mydata = null;

		//query exist
    	try{
    		cursor = db.query(SQLiteHelper.TB_NAME_B, null, 
    				Budget_item.YEAR + "='" + myear + "' and " + Budget_item.TYPE + "='" + type + "'", null, null, null, null);
    		cursor.moveToFirst();
    		
    		//no data
    		if (cursor.isAfterLast())
    		{
    			nodata=1;
    		}
    		
        	while(!cursor.isAfterLast())
        	{    
        		mydata = cursor.getString(3);
        		cursor.moveToNext();
        	}
    	}catch(IllegalArgumentException e){
    		e.printStackTrace();
    		++ DB_VERSION;
    		dbHelper.onUpgrade(db, --DB_VERSION, DB_VERSION);
    	}
    	
    	//Split
    	if (mydata != null)
    	{
    		String[] names = mydata.split(",");
    		int i = 0;
    		for(String name:names)
    		{
    			data_i[i] = name;
    	    	total_m = total_m + Integer.parseInt(data_i[i]);
    			i++;
    		}    		
    	}
		
		//openOptionsDialog(Integer.toString(position));
		if (position == 0)
		{
		    tview= new TextView[edittextlen];
			etext = new EditText[edittextlen];
		    for(int i=0; i<edittextlen; i++)
		    {
		    	tview[i] = new TextView(this);
		    	tview[i].setText(classify_list[i]);
		    	etext[i] = new EditText(this);

		    	if (nodata == 1)
		    	{
		    		etext[i].setText("0");
		    	}
		    	else
		    	{
		    		etext[i].setText(data_i[i]);
		    	}
		    	
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

		    	if (nodata == 1)
		    	{
		    		etext[i].setText("0");
		    	}
		    	else
		    	{
		    		etext[i].setText(data_i[i]);
		    	}
		    	
		    	mylayout.addView(tview[i]);
		    	mylayout.addView(etext[i]);
		     }
		}
	    vmoney.setText((String) this.getResources().getText(R.string.budget_total) + total_m);
	}
	
    //error message
    private void openOptionsDialog(String info)
	{
	    new AlertDialog.Builder(this)
	    .setTitle("message")
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