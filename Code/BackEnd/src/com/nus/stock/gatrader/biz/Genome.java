package com.nus.stock.gatrader.biz;

import com.nus.stock.gatrader.dt.DecisionTree;


public class Genome implements Comparable<Genome> {
    private DecisionTree tree;
    private double fitness = 0.0;
    
    public Genome(int treesize) {
        this.tree = new DecisionTree(treesize);
    }
    
    private Genome(DecisionTree tree, double fitness) {
        this.tree = tree;
        this.fitness = fitness;
    }
    
    @Override
    public Genome clone() {
        return new Genome(this.tree.clone(), this.fitness);
    }
    
    public DecisionTree getDecisionTree(){
        return tree;
    }
    
    public double getFitness(){
        return fitness;
    }
    
    public void setFitness(double fitness){
        this.fitness = fitness;
    }
    
	@Override
	public int compareTo(Genome o) {
		Double xDouble = fitness;
		Double yDouble = o.getFitness();
		return -xDouble.compareTo(yDouble);
	}
    
}
