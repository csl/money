package com.money;

public class Memo_item 
{
	public static final String ID = "rid";
	public static final String DATE = "rdate";
	public static final String TEXT = "rtext";
	
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
