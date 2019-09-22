package com.nus.stock;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;


public class Test
{
    public static void main(String[] args)
    {
    	
    	

    	//ApplicationContext  ctxApplicationContext = new ClassPathXmlApplicationContext("basic-config.xml");
    	//StoreProceduresUtils.callLoginStatementInit();
    	long t = System.currentTimeMillis();
    	Process process;
    	String cmd="python3 test/stockDataMining.py";//利用CMD命令调用python，包含两个参数
    	try{
    		System.out.println("start");
    		process = Runtime.getRuntime().exec(cmd);
    		BufferedInputStream in = new BufferedInputStream(process.getInputStream());
    		BufferedInputStream err = new BufferedInputStream(process.getErrorStream());

    		BufferedReader inBr = new BufferedReader(new InputStreamReader(in));
    		BufferedReader errBr = new BufferedReader(new InputStreamReader(err));
    		String lineStr;
    		while ((lineStr = inBr.readLine()) != null) {
    			System.out.println(lineStr);
    		}
    		while ((lineStr = errBr.readLine()) != null) {
    			System.out.println(lineStr);
    		}        
    		int exitValue = process.waitFor();

    		inBr.close();
    		errBr.close();
    		in.close();
    		err.close();
    		long t2=System.currentTimeMillis();
    		System.out.println("start over,cost:"+(t2-t)/1000+"s");

    	}
    	catch (IOException e){
    		e.printStackTrace();
    	} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	try {
			Thread.sleep(1000*1);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    
    
}
