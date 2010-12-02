package com.money;

public class Budget_item 
{
	public static final String ID = "bid";
	public static final String YEAR = "byear";
	public static final String TYPE = "btype";
	public static final String DATA = "bdata";
	
	private String id;
	private String date;
	private String type;
	private String data;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
}
