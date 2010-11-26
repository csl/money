package com.money;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.BaseExpandableListAdapter;
import android.app.DatePickerDialog.OnDateSetListener;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;

public class query extends Activity 
{ 
	//GUI
	private ExpandableListView show_list;
	private QueryExpand adapter;
	
	private TextView start_date;
	private TextView end_date;
	private int mYear,mMonth,mDay;
    private String sYear,sMonth, sDay;
	private int who;
	
	static final int DATE_DIALOG_ID = 0;
	
	//database
	private static String DB_NAME = "money.db";
	private static int DB_VERSION = 1;
	private static int POSTION;
	
	private SQLiteDatabase db;
	private SQLiteHelper dbHelper;
	private Cursor cursor;
	
	public void onCreate(Bundle savedInstanceState)
	{
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.query);
	    
    	//ExpandableListView & BaseExpandableListAdapter

	    try{
    		dbHelper = new SQLiteHelper(this, DB_NAME, null, DB_VERSION);
    		db = dbHelper.getWritableDatabase();
    	}catch(IllegalArgumentException e){
    		e.printStackTrace();
    		++ DB_VERSION;
    		dbHelper.onUpgrade(db, --DB_VERSION, DB_VERSION);
    	}			        	

    	show_list = (ExpandableListView)findViewById(R.id.queryList);
    	
    	start_date = (TextView)findViewById(R.id.start_date);
    	end_date = (TextView)findViewById(R.id.end_date);

    	adapter = new QueryExpand(this);

	    Button m_start_date=(Button) findViewById(R.id.m_start_date);
	    m_start_date.setOnClickListener(new Button.OnClickListener()
	     {
	        	public void onClick(View v)
	        	{
	        		who = 1;
	        		showDialog(DATE_DIALOG_ID);
	        	}
	     });

	    Button m_end_date=(Button) findViewById(R.id.m_end_date);
	    m_end_date.setOnClickListener(new Button.OnClickListener()
	     {
	        	public void onClick(View v)
	        	{
	        		who = 2;
	        		showDialog(DATE_DIALOG_ID);
	        	}
	     });
	    
	    Button querydata=(Button) findViewById(R.id.querydata);
	    querydata.setOnClickListener(new Button.OnClickListener()
	     {
	        	public void onClick(View v)
	        	{

	        		//fetch start date
	        		String sdate = start_date.getText().toString();
	        		String edate = end_date.getText().toString();
	        		
	        		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
	        		
	        		//count 
	        		int days = 0;
	        		
	        		Date startdate = new Date(sdate);
	        		Date enddate= new Date(edate);	    
	        		
					
					days = daysOfTwoDate(startdate, enddate);
					Calendar scalendar = new GregorianCalendar(2010,1,1);
					scalendar.setTime(startdate);
			        String term=new String();
			        Date date= new Date();
			        
			        //openOptionsDialog(sdate + " " + edate + " " + days);
			        List<String> child = null;
			        int smoney = 0;
		        	//Query DATABASE
			        for (int i=0; i<=days; i++)
			        {
			        	//Query DATABASE
			        	try{
			        		if (i == 0)
			        			cursor = db.query(SQLiteHelper.TB_NAME, null, Money_item.DATE + "='" + sdate + "'", null, null, null, null);
			        		else
			        			cursor = db.query(SQLiteHelper.TB_NAME, null, Money_item.DATE + "='" + term + "'", null, null, null, null);

			        		cursor.moveToFirst();
			        		
			        		//no data
			        		if (cursor.isAfterLast())
			        		{
					        	scalendar.add(Calendar.DATE, 1);
					        	date=scalendar.getTime();
					        	term = sdf.format(date);			        			
			        			continue;
			        		}
			        		
			            	while(!cursor.isAfterLast())
			            	{    
			        			child = new ArrayList<String>();
			            		child.add(cursor.getString(1) + "/" + cursor.getString(2) + ", NT." + cursor.getString(6));
			            		smoney = smoney + Integer.parseInt(cursor.getString(7)); 
			            		//openOptionsDialog(cursor.getString(2) + "/" +cursor.getString(3) + ", NT." + cursor.getString(5));
			            		cursor.moveToNext();
			            	}
			        	}catch(IllegalArgumentException e){
			        		e.printStackTrace();
			        		++ DB_VERSION;
			        		dbHelper.onUpgrade(db, --DB_VERSION, DB_VERSION);
			        	}			        	
			        		        	
			        	//date+1
			        	try
			        	{
			        		String[] data = child.toArray(new String[child.size()]);
			        		if (i == 0)
			        			adapter.addItem(sdate, data);
			        		else
			        			adapter.addItem(term, data);
				        	}
			        	catch (Exception e)
			        	{
			        		e.printStackTrace();	       		
			        	}
			        	
			        	scalendar.add(Calendar.DATE, 1);
			        	date=scalendar.getTime();
			        	term = sdf.format(date);
						//openOptionsDialog(term);
			        }
			        
			    	String total[] = {"支出花費: " + smoney};
			    	adapter.addItem("總和", total);

			        show_list.setAdapter(adapter);
	        	}
	      }
	    );
	    
	    Button exit_b=(Button) findViewById(R.id.exit_b);
	    exit_b.setOnClickListener(new Button.OnClickListener()
	     {
	        	public void onClick(View v)
	        	{
	        		db.close();
	        		dbHelper.close();
	        		
	        		Intent intent = new Intent();
	        		intent.setClass(query.this, MoneyListView.class);

	        		startActivity(intent);
	        		query.this.finish();	  
	        	}
	      }
	    );
	}
	
	public int daysOfTwoDate(Date beginDate,Date endDate)
	{ 
        int days = 0; 
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd"); 

        Calendar beginCalendar = Calendar.getInstance(); 
        Calendar endCalendar = Calendar.getInstance(); 
         
        beginCalendar.setTime(beginDate); 
        endCalendar.setTime(endDate); 

        while(beginCalendar.before(endCalendar)){ 
            days++; 
            beginCalendar.add(Calendar.DAY_OF_MONTH, 1); 
        } 
        return days;  
	}
	
	
    private void updateDisplay() 
    {
    	if (who == 1)
    	{
    		start_date.setText(
    				sYear + "/" + sMonth + "/" + sDay);
    	}
    	else if(who == 2)
    	{
    		end_date.setText(
    				sYear + "/" + sMonth + "/" + sDay);
    	}
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
                                      int monthOfYear, int dayOfMonth) 
                {
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