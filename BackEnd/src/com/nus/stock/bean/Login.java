package com.nus.stock.bean;

import java.io.Serializable;


public class Login implements Serializable{
	private static final long serialVersionUID = 6809000426953229426L;
	private String userName;
	private String password;
	
	
	public Login() {
		super();
	}
	
	public Login(String userName, String password, String ipAdress,
			String powerCode) {
		super();
		this.userName = userName;
		this.password = password;
	}
	
	public String getUserName() {
		return userName;
	}
	
	public void setUserName(String userName) {
		this.userName = userName;
	}
	
	public String getPassword() {
		return password;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}
	
	@Override
	public String toString() {
		return "Login [userName=" + userName + ", password=" + password + "]";
	}
	
	
}
