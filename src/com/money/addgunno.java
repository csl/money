package com.money;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.content.ContentValues;
import android.widget.Spinner;
import android.widget.Toast;
import android.widget.ArrayAdapter;

public class addgunno extends Activity 
{
	private static String DB_NAME = "money.db";
	private static int DB_VERSION = 1;
	private static final int EDIT=1;
	
	private SQLiteDatabase db;
	private SQLiteHelper dbHelper;
	private Cursor cursor;

	private Spinner date_year;
	private Spinner date_month;
	
	private TextView show_date;
	
	private EditText specialone;
	private EditText specialtwo;
	private EditText specialthree;
	private EditText hone;
	private EditText htwo;
	private EditText hthree;

    static final int DATE_DIALOG_ID = 0;
    private int nofind;
    private String year, month, m_y;
    

	public void onCreate(Bundle savedInstanceState)
	{
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.addgunno);
	 
    	try{
    		dbHelper = new SQLiteHelper(this, DB_NAME, null, DB_VERSION);
    		db = dbHelper.getWritableDatabase();
    	}
		catch(IllegalArgumentException e){
    		e.printStackTrace();
    		++ DB_VERSION;
    		dbHelper.onUpgrade(db, --DB_VERSION, DB_VERSION);
    	}

		date_year = (Spinner) findViewById(R.id.date_year);
		date_month = (Spinner) findViewById(R.id.date_month);
	
		specialone = (EditText) findViewById(R.id.specialone);
		specialtwo = (EditText) findViewById(R.id.specialtwo);
		specialthree = (EditText) findViewById(R.id.specialthree);
		hone = (EditText) findViewById(R.id.hone);
		htwo = (EditText) findViewById(R.id.htwo);
		hthree = (EditText) findViewById(R.id.hthree);
		
		show_date = (TextView) findViewById(R.id.title);
		
		//add date_year spinner
		String ispinner_list[] = this.getResources().getStringArray(R.array.year_list);
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, ispinner_list);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		date_year.setAdapter(adapter);
		
		date_year.setSelection(1);
		
		//add date_month spinner
		String date_month_list[] = this.getResources().getStringArray(R.array.month_list);
		ArrayAdapter<String> iadapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, date_month_list);
		iadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		date_month.setAdapter(iadapter);

		date_month.setSelection(5);

		date_year.setOnItemSelectedListener(new Spinner.OnItemSelectedListener(){
	           public void onItemSelected(AdapterView adapterView, View view, int position, long id)
	            {
	        	   updateDate();
	        	   nofind = update_gunno();
	        	}
	           public void onNothingSelected(AdapterView arg0) 
	           {
	           }
	        });

		date_month.setOnItemSelectedListener(new Spinner.OnItemSelectedListener(){
           public void onItemSelected(AdapterView adapterView, View view, int position, long id)
            {
        	   updateDate();
        	   nofind = update_gunno();
        	}
           public void onNothingSelected(AdapterView arg0) 
           {
           }
        });

		//save
		Button save=(Button) findViewById(R.id.save);
		save.setOnClickListener(new Button.OnClickListener()
	     {
	        	public void onClick(View v)
	        	{
	        		year = date_year.getSelectedItem().toString().trim();
	        		month = date_month.getSelectedItem().toString().trim();
	        		
	        		m_y = year + "/" + month;

	        		String so = specialone.getText().toString().trim();
	        		String st =specialtwo.getText().toString().trim();
	        		String stt =specialthree.getText().toString().trim();
	        		String ho =hone.getText().toString().trim();
	        		String ht = htwo.getText().toString().trim();
	        		String htt =hthree.getText().toString().trim();
	        		
	        		if (so == "" || st == "" || stt == "" || 
	        				ho == "" || ht == "" || htt == "")
	        		{
	        			Toast.makeText(addgunno.this, "empty", Toast.LENGTH_LONG).show();
	        		}
	        		else
	        		{
    	        		//fetch data
    					ContentValues values = new ContentValues();
    					values.put(Gunno_item.rDATE, m_y);
    					values.put(Gunno_item.SPECIALONE, so);
    					values.put(Gunno_item.SPECIALTWO, st);
    					values.put(Gunno_item.SPECIALTHREE, stt);
    					values.put(Gunno_item.HONE, ho);
    					values.put(Gunno_item.HTWO, ht);
    					values.put(Gunno_item.HTHREE, htt);
	        			
	        			//find it
	        			if (nofind == 0)
	        			{	        				
	    					//SQL
	    			    	try{
	    						Long cityID = db.insert(SQLiteHelper.TB_NAME_G, Gunno_item.ID, values);
	    			    	}
	    					catch(IllegalArgumentException e){
	    			    		e.printStackTrace();
	    			    		++ DB_VERSION;
	    			    		dbHelper.onUpgrade(db, --DB_VERSION, DB_VERSION);
	    			    	}
	    					Toast.makeText(addgunno.this, "add success.", Toast.LENGTH_LONG).show();
	        			}
	        			else
	        			{
	        				 String where = Gunno_item.rDATE + "='" + m_y + "'";
	        				 
	    					//SQL
	    			    	try{
	    						int cityID = db.update(SQLiteHelper.TB_NAME_G, values, where, null);
	    			    	}
	    					catch(IllegalArgumentException e){
	    			    		e.printStackTrace();
	    			    		++ DB_VERSION;
	    			    		dbHelper.onUpgrade(db, --DB_VERSION, DB_VERSION);
	    			    	}
	    					Toast.makeText(addgunno.this, "update success.", Toast.LENGTH_LONG).show();
	        			}
	        		}
	        	}
	      }
	    );
		
		
		//exit_b
		Button cal=(Button) findViewById(R.id.exit_b);
		cal.setOnClickListener(new Button.OnClickListener()
	     {
	        	public void onClick(View v)
	        	{
	        		db.close();
	        		dbHelper.close();
	        		
		     		Intent intent = new Intent();
		       		intent.setClass(addgunno.this, invoice.class);
		       		startActivityForResult(intent, EDIT);
	        		//startActivity(intent);
	        	}
	      }
	    );

		year = date_year.getSelectedItem().toString().trim();
		month = date_month.getSelectedItem().toString().trim();
		m_y = year + "/" + month;
		
		updateDate();
		nofind = update_gunno();
		
	  }

	private int update_gunno()
	{
		year = date_year.getSelectedItem().toString().trim();
		month = date_month.getSelectedItem().toString().trim();
		
		String m_y = year + "/" + month;

    	try{
    		cursor = db.query(SQLiteHelper.TB_NAME_G, null, Gunno_item.rDATE + "='" + m_y + "'", null, null, null, null);
    		cursor.moveToFirst();
    		
    		//no data
    		if (cursor.isAfterLast())
    		{
    			return 0;
    		}
    		
        	while(!cursor.isAfterLast())
        	{    
        		specialone.setText(cursor.getString(2));
        		specialtwo.setText(cursor.getString(3));
        		specialthree.setText(cursor.getString(4));
        		hone.setText(cursor.getString(5));
        		htwo.setText(cursor.getString(6));
        		hthree.setText(cursor.getString(7));
        		
        		cursor.moveToNext();
        	}
    	}catch(IllegalArgumentException e){
    		e.printStackTrace();
    		++ DB_VERSION;
    		dbHelper.onUpgrade(db, --DB_VERSION, DB_VERSION);
    	}			        	
		
		return 1;
	}
	
	private void updateDate()
	{
		year = date_year.getSelectedItem().toString().trim();
		month = date_month.getSelectedItem().toString().trim();
		
		String s_d, e_d;
		
		if (month.equals("1"))
		{
			int myear = Integer.valueOf(year) - 1;
			s_d = myear + "/" + "11";
			e_d = myear + "/" + "12";
		}
		else
		{
			int smonth = Integer.valueOf(month) - 2;
			int emonth = Integer.valueOf(month) - 1;
			s_d = year + "/" + Integer.toString(smonth);
			e_d = year + "/" + Integer.toString(emonth);
		}
		show_date.setText(s_d + " ~ " + e_d + " " + (String) this.getResources().getText(R.string.t_gunno_m));
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