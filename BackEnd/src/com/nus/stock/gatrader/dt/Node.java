package com.nus.stock.gatrader.dt;

import java.io.Serializable;

import com.nus.stock.gatrader.biz.Candle;
import com.nus.stock.gatrader.biz.Decision;


public class Node implements Serializable{
	private static final long serialVersionUID = 2352484667422485836L;
	public Node left;
    public Node right;
    public Node parent = null;
    
    public Node() {
        left = null;
        right = null;
    }
    
    public Node(Node left, Node right) {
        this.left = left;
        this.right = right;
    }
    
    public boolean isLeaf() {
        return false;
    }
    
    public Decision evaluate(Candle c){
        return null;
    }
    
    @Override
    public Node clone(){
        return new Node(this.left.clone(), this.right.clone());
    }

}
