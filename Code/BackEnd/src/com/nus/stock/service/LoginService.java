package com.nus.stock.service;

import com.nus.stock.bean.Login;

public interface LoginService {

	/**
	 * 
	 * @param login
	 * @return
	 */
	public void createLogin(Login login);
	
	/**
	 * 
	 * @param userName
	 * @return
	 */
	public Login findLoginByUserName(String userName);
	
	/**
	 * 
	 * @param login
	 * @return
	 */
	public Login updateLogin(Login login);
	
	/**
	 * 
	 * @param userName
	 */
	public void deleteLoginByUserName(String userName);
	
}
