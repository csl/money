package com.money;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase.CursorFactory;

public class SQLiteHelper extends SQLiteOpenHelper 
{
	public static final String TB_NAME = "money_table";
	public static final String TB_NAME_A = "account_table";
	public static final String TB_NAME_B = "budget_table";
	public static final String TB_NAME_G = "gunno_table";
	public static final String TB_NAME_M = "memo_table";

	public SQLiteHelper(Context context, String name, CursorFactory factory,
			int version) {
		super(context, name, factory, version);
	}
	
	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL("CREATE TABLE IF NOT EXISTS " +
				TB_NAME + "(" +
				Money_item.ID + " integer primary key," +
				Money_item.CLASS + " varchar," + 
				Money_item.CLASSIFY + " varchar,"+
				Money_item.ITEM + " varchar,"+
				Money_item.ACCOUNT + " varchar,"+
				Money_item.DATE + " varchar,"+
				Money_item.MONEY + " varchar,"+
				Money_item.GUNNO + " varchar"+
				");");
		
		db.execSQL("CREATE TABLE IF NOT EXISTS " +
				TB_NAME_A + "(" +
				Account_item.ID + " integer primary key," +
				Account_item.ACCOUNT + " varchar,"+
				Account_item.CLASSIFY + " varchar,"+
				Account_item.MONEY + " varchar,"+
				Account_item.COMMIT + " varchar,"+
				Account_item.COST + " varchar"+
				");");

		db.execSQL("CREATE TABLE IF NOT EXISTS " +
				TB_NAME_B + "(" +
				Budget_item.ID + " integer primary key," +
				Budget_item.YEAR + " varchar,"+
				Budget_item.TYPE + " varchar,"+
				Budget_item.DATA + " varchar"+
				");");
		
		db.execSQL("CREATE TABLE IF NOT EXISTS " +
				TB_NAME_G + "(" +
				Gunno_item.ID + " integer primary key," +
				Gunno_item.rDATE + " varchar,"+
				Gunno_item.SPECIALONE + " varchar,"+
				Gunno_item.SPECIALTWO + " varchar,"+
				Gunno_item.SPECIALTHREE + " varchar,"+
				Gunno_item.HONE + " varchar,"+
				Gunno_item.HTWO + " varchar,"+
				Gunno_item.HTHREE + " varchar"+
				");");
		
		db.execSQL("CREATE TABLE IF NOT EXISTS " +
				TB_NAME_M + "(" +
				Memo_item.ID + " integer primary key," +
				Memo_item.DATE + " varchar,"+
				Memo_item.TEXT + " varchar"+
				");");

		}
	
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		db.execSQL("DROP TABLE IF EXISTS " + TB_NAME);
		onCreate(db);
	}
	
	public void updateColumn(SQLiteDatabase db, String oldColumn, String newColumn, String typeColumn){
		try{
			db.execSQL("ALTER TABLE " +
					TB_NAME + " CHANGE " +
					oldColumn + " "+ newColumn +
					" " + typeColumn
			);
		}catch(Exception e){
			e.printStackTrace();
		}
	}

}
