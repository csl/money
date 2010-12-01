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

public class addmemo extends Activity 
{
	private static String DB_NAME = "money.db";
	private static int DB_VERSION = 1;
	private static final int EDIT=1;
	
	private SQLiteDatabase db;
	private SQLiteHelper dbHelper;
	private Cursor cursor;
	
	private TextView date_d;
	private EditText text;
	private ListView lv_show;
	
	ArrayList<HashMap<String, String>> memolist;

    static final int DATE_DIALOG_ID = 0;
    private int mYear,mMonth,mDay;
    
    private String sYear,sMonth, sDay;

	public void onCreate(Bundle savedInstanceState)
	{
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.memo);
	 
    	try{
    		dbHelper = new SQLiteHelper(this, DB_NAME, null, DB_VERSION);
    		db = dbHelper.getWritableDatabase();
    	}
		catch(IllegalArgumentException e){
    		e.printStackTrace();
    		++ DB_VERSION;
    		dbHelper.onUpgrade(db, --DB_VERSION, DB_VERSION);
    	}
		
		date_d = (TextView) findViewById(R.id.date_d);
		text = (EditText) findViewById(R.id.text);
		lv_show=(ListView)findViewById(R.id.lv_show);
		
		memolist = new ArrayList<HashMap<String, String>>();

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
		Button additem=(Button) findViewById(R.id.id_add);
		additem.setOnClickListener(new Button.OnClickListener()
	     {
	        	public void onClick(View v)
	        	{
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
					Toast.makeText(addmemo.this, "add success.", Toast.LENGTH_LONG).show();
					updateShow();
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
	        		intent.setClass(addmemo.this, MoneyListView.class);

	        		startActivity(intent);
	        		addmemo.this.finish();	  
	        	}
	      }
	    );

        updateShow();
        lv_show.setOnItemClickListener(new OnItemClickListener() 
	    {  
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,  
                    long arg3) {
            	
            	HashMap<String, String> id =  memolist.get(arg2);
            	
		    	try{
					int cityID = db.delete(SQLiteHelper.TB_NAME_M, Memo_item.ID + " = \"" + id.get("ItemText")  + "\"", null);
		    	}
				catch(IllegalArgumentException e){
		    		e.printStackTrace();
		    		++ DB_VERSION;
		    		dbHelper.onUpgrade(db, --DB_VERSION, DB_VERSION);
		    	}
				Toast.makeText(addmemo.this, "delete success.", Toast.LENGTH_LONG).show();
				updateShow();			
            }  
        });  

	  }
	
	
    private void updateShow() 
    {
    	memolist.clear();
		//get memo information
    	try{
        	cursor = db.query(SQLiteHelper.TB_NAME_M, null, null, null, null, null, null);
        	
        	if (cursor.isAfterLast())
        	{
        		HashMap<String, String> map = new HashMap<String, String>();  
        		map.put("ItemTitle", "No data");  
        		map.put("ItemText", "No data");  
        		memolist.add(map); 
        	}
        	
        	cursor.moveToFirst();
        	while(!cursor.isAfterLast())
        	{
        		HashMap<String, String> map = new HashMap<String, String>();  
        		map.put("ItemTitle", cursor.getString(1) + " " + cursor.getString(2));  
        		map.put("ItemText", cursor.getString(0));  
        		memolist.add(map);  
        		cursor.moveToNext();
        	}
    	}catch(IllegalArgumentException e){
    		e.printStackTrace();
    		++ DB_VERSION;
    		dbHelper.onUpgrade(db, --DB_VERSION, DB_VERSION);
    	}
    	
    	//put in
        SimpleAdapter m = new SimpleAdapter(this,
                memolist,
                R.layout.memo_listview,
                new String[] {"ItemTitle", "ItemText"}, 
                new int[] {R.id.ItemTitle,R.id.ItemText});
        
        lv_show.setAdapter(m);
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