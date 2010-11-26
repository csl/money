package com.money;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class invoice extends Activity 
{ 
	private static String DB_NAME = "money.db";
	private static int DB_VERSION = 1;
	private static final int EDIT=1;
	
	private SQLiteDatabase db;
	private SQLiteHelper dbHelper;
	private Cursor cursor;

	private static final int MENU_START = Menu.FIRST  ;
	private static final int MENU_EXIT = Menu.FIRST +1 ;
	  
	private Spinner date_year;
	private Spinner date_month;
	  
	private TextView show_date;
	private TextView show_result;
	  
	private String year, month, m_y;

   int retry = 1;

	public void onCreate(Bundle savedInstanceState)
	{
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.invoice);
	
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
	
		show_date = (TextView) findViewById(R.id.show_date);
		show_result = (TextView) findViewById(R.id.show_result);

		//add date_year spinner
		String date_year_list[] = this.getResources().getStringArray(R.array.year_list);
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, date_year_list);
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
	        	   if (retry==1)
	        	   {
	        	   updateDate();
	        	   get_gunno_result();
		        	   retry=0;
	        	   }
	        	}
	           public void onNothingSelected(AdapterView arg0) 
	           {
	           }
	        });
		date_month.setOnItemSelectedListener(new Spinner.OnItemSelectedListener(){
	           public void onItemSelected(AdapterView adapterView, View view, int position, long id)
	            {
	        	   if (retry==1)
	        	   {
	        	   updateDate();
	        	   get_gunno_result();
	        	   }
	        	   retry=1;
	        	}
	           public void onNothingSelected(AdapterView arg0) 
	           {
	           }
	        });
		
		year = date_year.getSelectedItem().toString().trim();
		month = date_month.getSelectedItem().toString().trim();
		m_y = year + "/" + month;
		
		updateDate();

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
		show_date.setText(s_d + " ~ " + e_d);
	}
	
	private int get_gunno_result()
	{
		String sp[] = new String[3];
		String he[] = new String[3];
		String t_special[] = this.getResources().getStringArray(R.array.t_special);
		String info = "";
		int vcode=-1;
		
		year = date_year.getSelectedItem().toString().trim();
		month = date_month.getSelectedItem().toString().trim();
		m_y = year + "/" + month;
		
		//fetch gunno data
		try{
    		cursor = db.query(SQLiteHelper.TB_NAME_G, null, Gunno_item.rDATE + "='" + m_y + "'", null, null, null, null);
    		cursor.moveToFirst();
    		
    		//no data
    		if (cursor.isAfterLast())
    		{
    			show_result.setText((String) this.getResources().getText(R.string.t_nodata));
    			return 0;
    		}
    		
        	while(!cursor.isAfterLast())
        	{   
        		sp[0] = cursor.getString(2);
        		sp[1] = cursor.getString(3);
        		sp[2] = cursor.getString(4);
        		he[0] = cursor.getString(5);
        		he[1] = cursor.getString(6);
        		he[2] = cursor.getString(7);
        		
         		cursor.moveToNext();
        	}
    	}catch(IllegalArgumentException e){
    		e.printStackTrace();
    		++ DB_VERSION;
    		dbHelper.onUpgrade(db, --DB_VERSION, DB_VERSION);
    	}		

    	//fetch last month
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

			if (smonth < 10)
				s_d = year + "/0" + Integer.toString(smonth);
			else
				s_d = year + "/" + Integer.toString(smonth);
			
			if (emonth < 10)
				e_d = year + "/0" + Integer.toString(emonth);
			else
				e_d = year + "/" + Integer.toString(emonth);
		}
    	
    	String ystart = s_d + "/01";
    	String yend = e_d + "/31";

    	String sp_info = (String) this.getResources().getText(R.string.t_special);
    	
    	 try{
    		cursor = db.query(SQLiteHelper.TB_NAME, null, Money_item.DATE + " BETWEEN '" +  "" + ystart + "' AND '" + yend + "'", null, null, null, null);
    		cursor.moveToFirst();
    		
        	//openOptionsDialog(Money_item.DATE + " BETWEEN '" +  "" + ystart + "' AND '" + yend + "'");

        	//no data
    		if (cursor.isAfterLast())
    		{
    			show_result.setText((String) this.getResources().getText(R.string.t_nodata));
    			return 0;
    		}
      	
        	while(!cursor.isAfterLast())
        	{   
        		
        		String sqldate = cursor.getString(7);
        		//compare special case
        		for (int i=0; i<3; i++)
        		{
        			//if yes, print 200k
        			if (sqldate.equals(sp[i]))
        			{
        				//print information
        		    	info = info + sp_info + cursor.getString(7) + "/" + cursor.getString(2) + cursor.getString(3) 
                        + "/$" + cursor.getString(6) + "\n";
        			}
        		}        		
        		
        		//compare head case
        		for (int i=0; i<3; i++)
        		{
        			int j=5;
        			while (true)
        			{
        				if (j < 0)
        				{
        					break;
        				}
        				
        				//if yes, record
                		openOptionsDialog(sqldate.substring(j,8) + " compare " + he[i].substring(j,8));
        				if (sqldate.substring(j,8).equals(he[i].substring(j,8)))
        				{
        					vcode=j;
        					j = j - 1;
        					//print information
        				}
        				else
        				{
        					break;
        				}
        			}
        		}

        		//print info
        		if (vcode != -1)
        		{
    		    	info = info + t_special[vcode] + ": " + cursor.getString(7) + "/" + cursor.getString(2) + cursor.getString(3) 
                    + "/$" + cursor.getString(6) + "\n";
        			vcode=-1;
        		}
        		//next
         		cursor.moveToNext();
        	}
    	}catch(IllegalArgumentException e){
    		e.printStackTrace();
    		++ DB_VERSION;
    		dbHelper.onUpgrade(db, --DB_VERSION, DB_VERSION);
    	}		
    	
    	//show date
    	if (info.equals(""))
    		show_result.setText((String) this.getResources().getText(R.string.t_nomatch));
    	else
    		show_result.setText(info);
    	
		return 1;	
	}
	
	public boolean onCreateOptionsMenu(Menu menu)
	{

	    super.onCreateOptionsMenu(menu);
	    
	    menu.add(0 , MENU_START, 0 ,R.string.menu_start).setIcon(R.drawable.start)
	    .setAlphabeticShortcut('S');
	    menu.add(0 , MENU_EXIT, 1 ,R.string.menu_exit).setIcon(R.drawable.exit)
	    .setAlphabeticShortcut('E');
	return true;  
	}

	  @Override
	public boolean onOptionsItemSelected(MenuItem item)
	{
		Intent intent = null;
		
	    switch (item.getItemId())
	    { 
	          case MENU_START:    
	        		intent = new Intent();
	        		intent.setClass(invoice.this, addgunno.class);

	        		startActivity(intent);
	        		invoice.this.finish();	  
	             return true;
	      
	          case MENU_EXIT:
	        		intent = new Intent();
	        		intent.setClass(invoice.this, MoneyListView.class);

	        		startActivity(intent);
	        		invoice.this.finish();	  
	        	  
	             break ;
	    }
	      return true ;
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