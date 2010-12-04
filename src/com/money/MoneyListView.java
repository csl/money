package com.money;

import java.util.ArrayList;
import java.util.HashMap;

import android.app.ListActivity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.util.Log;

public class MoneyListView extends ListActivity 
{
	private static String DB_NAME = "money.db";
	private static int DB_VERSION = 1;
	private static final int EDIT=1;
	
	private SQLiteDatabase db;
	private SQLiteHelper dbHelper;
	private Cursor cursor;
	private String memo_info="";
	private TextView message;
	
	private String acc_mess;
	private String acc_messa;
	
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

	   	try{
    		dbHelper = new SQLiteHelper(this, DB_NAME, null, DB_VERSION);
    		db = dbHelper.getWritableDatabase();
    	}
		catch(IllegalArgumentException e){
    		e.printStackTrace();
    		++ DB_VERSION;
    		dbHelper.onUpgrade(db, --DB_VERSION, DB_VERSION);
    	}
		
		message = (TextView) findViewById(R.id.message);
		
		acc_mess = (String) this.getResources().getText(R.string.account_m);
		acc_messa = (String) this.getResources().getText(R.string.account_message);

		String m_list = (String) this.getResources().getText(R.string.m_list);
		String m_account  = (String) this.getResources().getText(R.string.m_account);
		String m_inoutcome  = (String) this.getResources().getText(R.string.m_inoutcome);
		
		String c_list  = (String) this.getResources().getText(R.string.c_list);
		String c_account  = (String) this.getResources().getText(R.string.c_account);
		String c_inoutcome  = (String) this.getResources().getText(R.string.c_inoutcome);
		
		String t_list  = (String) this.getResources().getText(R.string.t_list);
		String t_account  = (String) this.getResources().getText(R.string.t_account);
		String t_gunno  = (String) this.getResources().getText(R.string.t_gunno);
		
		String s_list  = (String) this.getResources().getText(R.string.s_list);
		String s_setup  = (String) this.getResources().getText(R.string.s_setup);

		String cmemo  = (String) this.getResources().getText(R.string.cmemo);
		String t_budget  = (String) this.getResources().getText(R.string.t_budget);

		CustomerListAdapter adapter = new CustomerListAdapter(this);
		ContentListElement element;

		//Management
		adapter.addSectionHeaderItem(m_list);

		ArrayList<ListElement> elements = new ArrayList<ListElement>();
	
		element = new ContentListElement();
		element.setTitle(m_account);
		elements.add(element);
		
		element = new ContentListElement();
		element.setTitle(m_inoutcome);
		elements.add(element);
		
		adapter.addList(elements);

		//Control
		adapter.addSectionHeaderItem(c_list);

		elements = new ArrayList<ListElement>();
/*		
		element = new ContentListElement();
		element.setTitle(c_account);
		elements.add(element);
	*/	
		element = new ContentListElement();
		element.setTitle(c_inoutcome);
		elements.add(element);

		adapter.addList(elements);

		//tools
		adapter.addSectionHeaderItem(t_list);
		elements = new ArrayList<ListElement>();

		//budget
		element = new ContentListElement();
		element.setTitle(t_budget);
		elements.add(element);
		
		//account
		element = new ContentListElement();
		element.setTitle(t_account);
		elements.add(element);
		
		//gunno 
		element = new ContentListElement();
		element.setTitle(t_gunno);
		elements.add(element);

		//memo
		element = new ContentListElement();
		element.setTitle(cmemo);
		elements.add(element);
		
		adapter.addList(elements);
/*
		//system setup
		adapter.addSectionHeaderItem(s_list);

		elements = new ArrayList<ListElement>();

		element = new ContentListElement();
		element.setTitle(s_setup);
		elements.add(element);
		adapter.addList(elements);
*/
		this.setListAdapter(adapter);
		
		query_account_status();
		updateShow();
		message.setText(memo_info);

	}
	
	private void query_account_status()
	{
    	//fetch acoount's cost
    	try{
    		cursor = db.query(SQLiteHelper.TB_NAME_A, null, null, null, null, null, null);
    		cursor.moveToFirst();
    		//nodata
    		if (cursor.isAfterLast())
    		{
    			return;
    		}
        	while(!cursor.isAfterLast())
        	{   
        		memo_info = memo_info + "[" + cursor.getString(1) + "]" + acc_messa + cursor.getString(5) + "\n";
         		cursor.moveToNext();
        	}
    	}catch(IllegalArgumentException e){
    		e.printStackTrace();
    		++ DB_VERSION;
    		dbHelper.onUpgrade(db, --DB_VERSION, DB_VERSION);
    	}	    	
		
	}
	
	protected void onListItemClick(ListView l, View v, int position, long id) 
	{
		Log.d("DEBUG", "press " + position);

		if (position == 1)
		{
			Intent intent = new Intent();
			intent.setClass(MoneyListView.this,addaccount.class);
	
			startActivity(intent);
			//MoneyListView.this.finish();
		}
		else if (position==2)
		{
			Intent intent = new Intent();
			intent.setClass(MoneyListView.this,additem.class);
	
			startActivity(intent);
			//MoneyListView.this.finish();
		}
		else if (position == 4)
		{
			Intent intent = new Intent();
			intent.setClass(MoneyListView.this,query.class);
	
			startActivity(intent);
			//MoneyListView.this.finish();
		}
		else if (position == 6)
		{
			Intent intent = new Intent();
			intent.setClass(MoneyListView.this,SetBudget.class);
	
			startActivity(intent);
			//MoneyListView.this.finish();
		}
		else if (position == 7)
		{
			Intent intent = new Intent();
			intent.setClass(MoneyListView.this,ModifyAccount.class);
	
			startActivity(intent);
			//MoneyListView.this.finish();
		}
		else if (position == 8)
		{
			Intent intent = new Intent();
			intent.setClass(MoneyListView.this,invoice.class);
	
			startActivity(intent);
			//MoneyListView.this.finish();
		}
		else if (position == 9)
		{
			Intent intent = new Intent();
			intent.setClass(MoneyListView.this,addmemo.class);
	
			startActivity(intent);
			//MoneyListView.this.finish();
		}
	}
	
    private void updateShow() 
    {
    	memo_info = memo_info + "\n" + (String) this.getResources().getText(R.string.memo_title) + "\n";
    	
		//get memo information
    	try{
        	cursor = db.query(SQLiteHelper.TB_NAME_M, null, null, null, null, null, null);
        	
        	if (cursor.isAfterLast())
        	{
        		memo_info = memo_info +
        			(String) this.getResources().getText(R.string.memo_empty);
        	}
        	
        	cursor.moveToFirst();
        	while(!cursor.isAfterLast())
        	{
        		memo_info = memo_info + cursor.getString(1) + " " + cursor.getString(2) + "\n";
        		cursor.moveToNext();
        	}
    	}catch(IllegalArgumentException e){
    		e.printStackTrace();
    		++ DB_VERSION;
    		dbHelper.onUpgrade(db, --DB_VERSION, DB_VERSION);
    	}
    	
    }
}