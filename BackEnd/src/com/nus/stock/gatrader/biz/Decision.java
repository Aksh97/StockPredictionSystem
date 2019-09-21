package com.nus.stock.gatrader.biz;

import java.util.Random;

public enum Decision {
    SELL("sell", 0), 
    BUY("buy",0);
    String name;
    double confidence;
    
    Decision(String name, double confidence) {
    	this.name = name;
    	this.confidence = confidence;
    }
    
    public void setConfidence(double confidence) {
		this.confidence = confidence;
	}
    
    public double getConfidence() {
		return confidence;
	}
    
    public static Decision getRandomDecision(){
        return new Random().nextBoolean() ? BUY : SELL;
    }
    
}
