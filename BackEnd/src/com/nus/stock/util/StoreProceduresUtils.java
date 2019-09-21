package com.nus.stock.util;

import java.io.FileInputStream;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class StoreProceduresUtils {
	private static Connection conn = null;
	private static CallableStatement cs = null;
	private static String driverClassName = "";
	private static String dbUrl = "";
	private static String username = "";
	private static String password = "";
	
	static {
		try {
			dbInitParamsParse();
			Class.forName(driverClassName);
			System.out.println("mmmmwz00");
			conn = DriverManager.getConnection(dbUrl, username, password);
			System.out.println("mmmmwz0011");
		} catch (Exception e) {
			System.out.println("mmmmwz0022");
			e.printStackTrace();
		}
	}

	private static void dbInitParamsParse() {
		try {
			String path = StoreProceduresUtils.class.getResource("/").getPath();
			String websiteUrl = path.replace("/classes", "")
					.replace("%20", " ") + "dbConfig.properties";
			Logger4j.getLogger(StoreProceduresUtils.class).debug(
					"@websiteUrl=" + websiteUrl);
			setParameter(websiteUrl);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static void setParameter(String path) throws Exception {
		Properties properties = new Properties();
		FileInputStream fileInputStream = null;
		try {
			fileInputStream = new FileInputStream(path);
			properties.load(fileInputStream);
			driverClassName = properties
					.getProperty("database.driverClassName");
			dbUrl = properties.getProperty("database.url");
			password = properties.getProperty("database.password");
			username = properties.getProperty("database.username");
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (null != fileInputStream) {
				fileInputStream.close();
				fileInputStream = null;
			}
		}
	}

	public static void callLoginStatementInit()
	{
		try {
			cs = conn
					.prepareCall("{call LOGINPROCEDURES(?,?)}");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public static void callLoginStatement(String userName, String password) {
		try {
			if (null == userName || null == password)
				return;
			cs.setString(1, userName);
			cs.setString(2, password);
			cs.addBatch();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void callStatementFinish()
	{
		try {
			if (null != cs)
				cs.executeBatch();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (null != cs)
			{
				try {
					cs.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
				cs = null;
			}
		}
	}
	
}
