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
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnCreateContextMenuListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.view.ContextMenu.ContextMenuInfo;  
import android.widget.Toast;
import android.widget.ArrayAdapter;

public class AccountDel extends Activity 
{
	private static String DB_NAME = "money.db";
	private static int DB_VERSION = 1;
	private static final int EDIT=1;
	
	private SQLiteDatabase db;
	private SQLiteHelper dbHelper;
	private Cursor cursor;
	
	private ListView lv_show;
	private String account_list[];
	private ArrayList<HashMap<String, String>> accountlist;

	protected static final int Menu_Item1=Menu.FIRST;

	public void onCreate(Bundle savedInstanceState)
	{
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.account);
	
	    //OPEN Database
    	try{
    		dbHelper = new SQLiteHelper(this, DB_NAME, null, DB_VERSION);
    		db = dbHelper.getWritableDatabase();
    	}
		catch(IllegalArgumentException e){
    		e.printStackTrace();
    		++ DB_VERSION;
    		dbHelper.onUpgrade(db, --DB_VERSION, DB_VERSION);
    	}
		
		lv_show=(ListView)findViewById(R.id.lv_show); 
		account_list = this.getResources().getStringArray(R.array.m_account_l);
		accountlist = new ArrayList<HashMap<String, String>>();

		//edit button
		Button additem=(Button) findViewById(R.id.id_edit);
		additem.setOnClickListener(new Button.OnClickListener()
	     {
	        	public void onClick(View v)
	        	{
	    			Intent intent = new Intent();
	    			intent.setClass(AccountDel.this, ModifyAccount.class);
	    	
	    			startActivity(intent);
	        		
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
	        		intent.setClass(AccountDel.this, MoneyListView.class);

	        		startActivity(intent);
	        		AccountDel.this.finish();	  
	        	}
	      }
	    );

        updateShow();
        lv_show.setOnItemClickListener(new OnItemClickListener() 
	    {  
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,  
                    long arg3) {
            	
             	HashMap<String, String> id =  accountlist.get(arg2);
            	
    	    	try{
    				int cityID = db.delete(SQLiteHelper.TB_NAME_A, Account_item.ID + " = \"" + id.get("ItemText")  + "\"", null);
    	    	}
    			catch(IllegalArgumentException e){
    	    		e.printStackTrace();
    	    		++ DB_VERSION;
    	    		dbHelper.onUpgrade(db, --DB_VERSION, DB_VERSION);
    	    	}
    			Toast.makeText(AccountDel.this, "delete success.", Toast.LENGTH_LONG).show();
    			updateShow();			

            	
            }  
        });
        
        //long click
       lv_show.setOnCreateContextMenuListener(new OnCreateContextMenuListener() {  
            
            public void onCreateContextMenu(ContextMenu menu, View v,ContextMenuInfo menuInfo) {  
                menu.setHeaderTitle("Account");     
                menu.add(0, 0, 0,  account_list[0]);  
                menu.add(0, 1, 0,  account_list[1]);     
            }  
        });   
      
	  }
	
    public boolean onContextItemSelected(MenuItem item) 
    {  
        //item.getItemId()   
    	 if (item.getItemId() == 0)
    	 {
    		 
    	 }
    	 else if (item.getItemId() == 1)
         {
        	
         }

        return super.onContextItemSelected(item);  
     }  
	
    private void updateShow() 
    {
    	accountlist.clear();
		//get memo information
    	try{
        	cursor = db.query(SQLiteHelper.TB_NAME_A, null, null, null, null, null, null);
        	
        	if (cursor.isAfterLast())
        	{
        		HashMap<String, String> map = new HashMap<String, String>();  
        		map.put("ItemTitle", "No data");  
        		map.put("ItemText", "No data");  
        		accountlist.add(map); 
        	}
        	
        	cursor.moveToFirst();
        	while(!cursor.isAfterLast())
        	{
        		HashMap<String, String> map = new HashMap<String, String>();  
        		map.put("ItemTitle", cursor.getString(1));  
        		map.put("ItemText", cursor.getString(0));  
        		accountlist.add(map);  
        		cursor.moveToNext();
        	}
    	}catch(IllegalArgumentException e){
    		e.printStackTrace();
    		++ DB_VERSION;
    		dbHelper.onUpgrade(db, --DB_VERSION, DB_VERSION);
    	}
    	
    	//put in
        SimpleAdapter m = new SimpleAdapter(this,
                accountlist,
                R.layout.account_listview,
                new String[] {"ItemTitle", "ItemText"}, 
                new int[] {R.id.ItemTitle,R.id.ItemText});
        
        lv_show.setAdapter(m);
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