package com.nus.stock.gatrader.dt;

import com.nus.stock.gatrader.biz.Candle;
import com.nus.stock.gatrader.biz.Decision;


public class DecisionNode extends Node {
	private static final long serialVersionUID = 4793930382458322476L;
	public Decision decision;
    
    public DecisionNode() {
        this.decision = Decision.BUY;
    }
    
    public DecisionNode(Decision decision) {
        this.decision = decision;
    }
    
    public static DecisionNode getRandomNode() {
        return new DecisionNode(Decision.getRandomDecision());
    }
    
    public void mutateDecision() {
        if (decision == Decision.BUY) {
            decision = Decision.SELL;
        } else {
            decision = Decision.BUY;
        }
    }
    
    @Override
    public Decision evaluate(Candle c){
        return decision;
    }
    
    @Override
    public boolean isLeaf(){
        return true;
    }
        
    @Override
    public String toString(){
        return null;
    }
    
    @Override
    public DecisionNode clone(){
        return new DecisionNode(this.decision);
    }
}
