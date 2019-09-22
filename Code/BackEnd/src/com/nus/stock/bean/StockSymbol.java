package com.nus.stock.bean;

import java.io.Serializable;

public class StockSymbol implements Serializable
{
	private static final long serialVersionUID = 227507743683132058L;
    private String symbol;
    private String fullName;
  
    
	public StockSymbol() {
        super();
    }
	
	

    public String getSymbol() {
		return symbol;
	}



	public void setSymbol(String symbol) {
		this.symbol = symbol;
	}



	

    public static long getSerialversionuid() {
        return serialVersionUID;
    }



	public String getFullName() {
		return fullName;
	}



	public void setFullName(String fullName) {
		this.fullName = fullName;
	}



	@Override
	public String toString() {
		return "StockSymbol [symbol=" + symbol + ", fullName=" + fullName + "]";
	}




 
}
