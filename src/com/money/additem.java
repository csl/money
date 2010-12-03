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
import android.app.AlertDialog.Builder;

public class additem extends Activity 
{
	private static String DB_NAME = "money.db";
	private static int DB_VERSION = 1;
	private static final int EDIT=1;
	
	private SQLiteDatabase db;
	private SQLiteHelper dbHelper;
	private Cursor cursor;
	
	private Spinner ispinner;
	private Spinner classify;
	private EditText subclass;
	private Spinner account;
	private TextView date_d;
	private EditText money;
	private EditText gunno;

    static final int DATE_DIALOG_ID = 0;
    private int mYear,mMonth,mDay;
    
    private String sYear,sMonth, sDay;
    
    private String classify_list[];
    private String money_message;
    private int edittextlen;
    private String type_m, money_m;

    private AlertDialog.Builder builder;

	public void onCreate(Bundle savedInstanceState)
	{
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.additem);
	 
    	try{
    		dbHelper = new SQLiteHelper(this, DB_NAME, null, DB_VERSION);
    		db = dbHelper.getWritableDatabase();
    	}
		catch(IllegalArgumentException e){
    		e.printStackTrace();
    		++ DB_VERSION;
    		dbHelper.onUpgrade(db, --DB_VERSION, DB_VERSION);
    	}

		classify_list = this.getResources().getStringArray(R.array.classify_list);
		money_message = (String) this.getResources().getText(R.string.m_message1);
		edittextlen = classify_list.length;
		
		builder = new AlertDialog.Builder(this);
		
		ispinner = (Spinner) findViewById(R.id.ispin);
		classify = (Spinner) findViewById(R.id.classify);
		subclass = (EditText) findViewById(R.id.subclass);
		account = (Spinner) findViewById(R.id.account);
		date_d = (TextView) findViewById(R.id.show_date);
		money = (EditText) findViewById(R.id.money);
		gunno = (EditText) findViewById(R.id.gunno);

		//add ispinner spinner
		String ispinner_list[] = this.getResources().getStringArray(R.array.ispinner_list);
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, ispinner_list);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		ispinner.setAdapter(adapter);

		//add classify spinner
		String classify_list[] = this.getResources().getStringArray(R.array.classify_list);
		adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, classify_list);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		classify.setAdapter(adapter);

		//get account information	
		String col[] = {Account_item.ACCOUNT};
		List<String> account_c = new ArrayList<String>();
		
    	try{
        	cursor = db.query(SQLiteHelper.TB_NAME_A, col, null, null, null, null, null);
        	
        	cursor.moveToFirst();
        	while(!cursor.isAfterLast())
        	{
        		account_c.add(cursor.getString(0));
        		cursor.moveToNext();
        	}
    	}catch(IllegalArgumentException e){
    		e.printStackTrace();
    		++ DB_VERSION;
    		dbHelper.onUpgrade(db, --DB_VERSION, DB_VERSION);
    	}			        	

    	//add account spinner
		ArrayAdapter<String> adapter_a = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, account_c);
		adapter_a.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		account.setAdapter(adapter_a);
		
    	//cal button
		Button cal=(Button) findViewById(R.id.cal);
		cal.setOnClickListener(new Button.OnClickListener()
	     {
	        	public void onClick(View v)
	        	{
		     		Intent intent = new Intent();
		       		intent.setClass(additem.this, calc.class);
		       		startActivityForResult(intent, EDIT);
	        		//startActivity(intent);
	        	}
	      }
	    );

		//get date
    	Date today = Calendar.getInstance().getTime();
    	SimpleDateFormat df = new SimpleDateFormat("yyyy/MM/dd");
    	date_d.setText(df.format(today));

    	//date button
		Button mdate=(Button) findViewById(R.id.mdate);
		mdate.setOnClickListener(new Button.OnClickListener()
	     {
	        	public void onClick(View v)
	        	{
	        		showDialog(DATE_DIALOG_ID);
	        	}
	      }
	    );

		//add button
		Button additem=(Button) findViewById(R.id.add);
		additem.setOnClickListener(new Button.OnClickListener()
	     {
	        	public void onClick(View v)
	        	{
	        		String mydata = null;
	        		int nodata=0;
	        		money_m = "0";
	        		type_m = "0";
	        		
	        		//splict date
	        		String format = date_d.getText().toString();
            		String[] names = format.split("/");
            		int i=0;
            		for(String name:names)
            		{
            			if (i ==0)
            				sYear = name;
            			else if (i==1)
            				sMonth = name;
            			else if (i==2)
            				sDay = name;            				
            			i++;
            		}

            		//check: sYear,sMonth
	            	try{
	            		cursor = db.query(SQLiteHelper.TB_NAME_B, null, 
	            				Budget_item.YEAR + "='" + sYear + "'", null, null, null, null);
	            		cursor.moveToFirst();
	            		
	            		//no data
	            		if (cursor.isAfterLast())
	            		{
	            			nodata = 1;
	            		}
	            		
	            		//if yes, has two data
	                	while(!cursor.isAfterLast())
	                	{
	                		String type = cursor.getString(2);
	                		if (type.equals("0"))
	                		{
	                			int type_id = classify.getSelectedItemPosition();
		                		mydata = cursor.getString(3);
	                    		names = mydata.split(",");
	                    		i=0;
	                    		for(String name:names)
	                    		{
	                    			if (i == type_id)
	                    			{
	                    				type_m = name;
	                    				break;
	                    			}
	                    			i++;
	                    		}
	                		}
	                		else
	                		{
	                			int month_id = Integer.parseInt(sMonth);
		                		mydata = cursor.getString(3);
	                    		names = mydata.split(",");
	                    		i=0;
	                    		for(String name:names)
	                    		{
	                    			if (i == month_id-1)
	                    			{
	                    				money_m = name;
	                    				break;
	                    			}
	                    			i++;
	                    		}	                			
	                		}
	                		
	                		cursor.moveToNext();	                		
                    	}
	            	}catch(IllegalArgumentException e){
	            		e.printStackTrace();
	            		++ DB_VERSION;
	            		dbHelper.onUpgrade(db, --DB_VERSION, DB_VERSION);
	            	}       		
	        		
	            	//check
	            	if (nodata == 0)
	            	{
	            		//fetch data
	            		int budget_type = Integer.parseInt(type_m);
	            		int budget_month = Integer.parseInt(money_m);

	            		//zero int = null
	            		int over_budget=0;
	            		int cost = 0;
	            		
	            		if (budget_type == 0 && budget_month == 0)
	            		{
    	            		add_itemtoDB();
	            			return;
	            		}
	            		
	            		if (budget_type != 0)
	            		{
	            			cost = query_type_totalm();
	            			//over budget
	            			if (cost > budget_type) 
	            				over_budget = 1;
	            		}
	            		
	            		if (budget_month != 0)
	            		{
	            			cost = query_month_totalm();
		            		openOptionsDialog(budget_month + " " + cost);
	            			
	            			//over budget
	            			if (cost > budget_month) 
	            				over_budget = 1;
	            		}

	            		
	            		if (over_budget == 1)
	            		{
		                    builder.setMessage(money_message);
		                    builder.setCancelable(false);
		                   
		                    builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
		                        public void onClick(DialogInterface dialog, int id) 
		                        {
		    	            		add_itemtoDB();
		                        }
		                    });
		                   
		                    builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
		                        public void onClick(DialogInterface dialog, int id) 
		                        {
		                        	return;
		                        }
		                    });
		                   
		                    AlertDialog alert = builder.create();
		                    alert.show();
	            		}
	            		else
	            		{
	            			add_itemtoDB();
	            		}	
	            		
	            	}
	            	else
	            	{
	            		add_itemtoDB();
	            	}
				
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
	        		intent.setClass(additem.this, MoneyListView.class);

	        		startActivity(intent);
	        		additem.this.finish();	  
	        	}
	      }
	    );
	  }
	
    private int query_type_totalm()
    {
    	/*
    	int cost=0;
    	int endyear = Integer.parseInt(sYear) + 1;
    	String ystart = sYear + "/01/01";
    	String yend = sYear + "/12/31";

    	try{
    		//cursor = db.query(SQLiteHelper.TB_NAME_B, null, Money_item.DATE + " BETWEEN '" +  "" + ystart + "' AND '" + yend + "'", null, null, null, null);
    		cursor.moveToFirst();
    		
        	openOptionsDialog(Money_item.DATE + " BETWEEN '" +  "" + ystart + "' AND '" + yend + "'");

        	//no data
    		if (cursor.isAfterLast())
    		{
    			return 0;
    		}
      	
        	while(!cursor.isAfterLast())
        	{   
        		String money = cursor.getString(6);
        		cost = cost + Integer.parseInt(money);
         		cursor.moveToNext();
        	}
    	}catch(IllegalArgumentException e){
    		e.printStackTrace();
    		++ DB_VERSION;
    		dbHelper.onUpgrade(db, --DB_VERSION, DB_VERSION);
    	}	            		

    	return cost;
		*/
    	return 0;
    }

	
    private int query_month_totalm()
    {
    	int cost=0;
    	String ystart = sYear + "/" + sMonth + "/01";
    	String yend = sYear + "/" + sMonth + "/31";

    	try{
    		cursor = db.query(SQLiteHelper.TB_NAME, null, Money_item.DATE + " BETWEEN '" +  "" + ystart + "' AND '" + yend + "'", null, null, null, null);
    		cursor.moveToFirst();
    		
        	//no data
    		if (cursor.isAfterLast())
    		{
    			return 0;
    		}
      	
        	while(!cursor.isAfterLast())
        	{   
        		String money = cursor.getString(6);
        		cost = cost + Integer.parseInt(money);
         		cursor.moveToNext();
        	}
    	}catch(IllegalArgumentException e){
    		e.printStackTrace();
    		++ DB_VERSION;
    		dbHelper.onUpgrade(db, --DB_VERSION, DB_VERSION);
    	}	            		

    	
    	return cost;
    }


    private void updateDisplay() 
    {
    	date_d.setText(
    				sYear + "/" + sMonth + "/" + sDay);
    }
    
    @Override
    protected Dialog onCreateDialog(int id) 
    {
    	Calendar c=Calendar.getInstance(Locale.TAIWAN);
    	mYear=c.get(Calendar.YEAR);
    	mMonth=c.get(Calendar.MONTH);
    	mDay = c.get(Calendar.DATE);
    	
        switch (id) {
        case DATE_DIALOG_ID:
            return new DatePickerDialog(this,
                        mDateSetListener,
                        mYear, mMonth, mDay);
        }
        return null;
    }
	
    // the callback received when the user "sets" the date in the dialog
    private DatePickerDialog.OnDateSetListener mDateSetListener =
            new DatePickerDialog.OnDateSetListener() 
    		{
 
                public void onDateSet(DatePicker view, int year, 
                                      int monthOfYear, int dayOfMonth) {

                	sYear = String.valueOf(year);
                    
                    if (monthOfYear + 1 < 10)
                    	sMonth = "0" + String.valueOf(monthOfYear + 1);
                    else
                    	sMonth = String.valueOf(monthOfYear + 1);

                    if (dayOfMonth < 10)
                        sDay = "0" + String.valueOf(dayOfMonth);
                    else
                        sDay = String.valueOf(dayOfMonth);
                    
                    updateDisplay();
                }
            };	
            
    private void add_itemtoDB()
    {
    	String amoney = "0";
    	String where = Account_item.ACCOUNT + "='" + account.getSelectedItem().toString().trim()+ "'";
    	
    	//fetch acoount's cost
    	try{
    		cursor = db.query(SQLiteHelper.TB_NAME_A, null, where, null, null, null, null);
    		cursor.moveToFirst();
    		
       		amoney = cursor.getString(5);
    	}catch(IllegalArgumentException e){
    		e.printStackTrace();
    		++ DB_VERSION;
    		dbHelper.onUpgrade(db, --DB_VERSION, DB_VERSION);
    	}	    	
    	
    	int cost_money = Integer.parseInt(amoney) - Integer.parseInt(money.getText().toString().trim());
		ContentValues avalues = new ContentValues();
		avalues.put(Account_item.COST, Integer.toString(cost_money));

		//write back to acoount's cost
    	try{
			int cityID = db.update(SQLiteHelper.TB_NAME_A, avalues, where, null);
    	}
		catch(IllegalArgumentException e){
    		e.printStackTrace();
    		++ DB_VERSION;
    		dbHelper.onUpgrade(db, --DB_VERSION, DB_VERSION);
    	}
    	
		//fetch data
		ContentValues values = new ContentValues();
		values.put(Money_item.CLASS, ispinner.getSelectedItem().toString().trim());
		values.put(Money_item.CLASSIFY, classify.getSelectedItem().toString().trim());
		values.put(Money_item.ITEM, subclass.getText().toString().trim());
		values.put(Money_item.ACCOUNT, account.getSelectedItem().toString().trim());
		values.put(Money_item.DATE, date_d.getText().toString().trim());
		values.put(Money_item.MONEY, money.getText().toString().trim());
		values.put(Money_item.GUNNO, gunno.getText().toString().trim());
		
		//write
    	try{
			Long cityID = db.insert(SQLiteHelper.TB_NAME, Money_item.ID, values);
    	}
		catch(IllegalArgumentException e){
    		e.printStackTrace();
    		++ DB_VERSION;
    		dbHelper.onUpgrade(db, --DB_VERSION, DB_VERSION);
    	}
		
		Toast.makeText(additem.this, "add success.", Toast.LENGTH_LONG).show();            	
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
	
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    super.onActivityResult(requestCode, resultCode, data);

    switch(requestCode){
    	case EDIT:
    		money.setText(data.getExtras().getString("money_cal"));
    }
    }
	
}