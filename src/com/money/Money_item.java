package com.money;

public class Money_item 
{
	public static final String ID = "aid";
	public static final String CLASS = "class";
	public static final String CLASSIFY = "classify";
	public static final String ITEM = "item";
	public static final String ACCOUNT = "account";
	public static final String DATE = "date";
	public static final String MONEY = "money";
	public static final String GUNNO = "gunno";
	
	private String id;
	private String class_c;
	private String classify;
	private String account;
	private String date;
	private String money;
	private String gunno;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getClassc() {
		return class_c;
	}
	public void setClassc(String class_c) {
		this.class_c = class_c;
	}
	public String getClassify() {
		return classify;
	}
	public void setClassify(String classify) {
		this.classify = classify;
	}
	public String getAccount() {
		return account;
	}
	public void setAccount(String account) {
		this.account = account;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public String getMoney() {
		return money;
	}
	public void setMoney(String money) {
		this.money = money;
	}
	public String getGunno() {
		return gunno;
	}
	public void setGunno(String gunno) {
		this.gunno = gunno;
	}
}
