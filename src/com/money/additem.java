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
	        		//fetch data
					ContentValues values = new ContentValues();
					values.put(Money_item.CLASS, ispinner.getSelectedItem().toString().trim());
					values.put(Money_item.CLASSIFY, classify.getSelectedItem().toString().trim());
					values.put(Money_item.ITEM, subclass.getText().toString().trim());
					values.put(Money_item.ACCOUNT, account.getSelectedItem().toString().trim());
					values.put(Money_item.DATE, date_d.getText().toString().trim());
					values.put(Money_item.MONEY, money.getText().toString().trim());
					values.put(Money_item.GUNNO, gunno.getText().toString().trim());
					
					//SQL
					
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
	
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    super.onActivityResult(requestCode, resultCode, data);

    switch(requestCode){
    	case EDIT:
    		money.setText(data.getExtras().getString("money_cal"));
    }
    }
	
}