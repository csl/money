package com.money;

public class Account_item 
{
	public static final String ID = "rid";
	public static final String ACCOUNT = "raccount";
	public static final String CLASSIFY = "rclassify";
	public static final String MONEY = "rmoney";
	public static final String COMMIT = "rcommit";
	public static final String COST = "rcost";
	
	private String id;
	private String account;
	private String classify;
	private String money;
	private String commit;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
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
	public String getMoney() {
		return money;
	}
	public void setMoney(String money) {
		this.money = money;
	}
	public String getCommit() {
		return commit;
	}
	public void setGunno(String commit) {
		this.commit = commit;
	}
}
