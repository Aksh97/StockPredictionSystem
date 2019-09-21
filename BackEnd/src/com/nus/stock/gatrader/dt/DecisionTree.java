package com.nus.stock.gatrader.dt;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import com.nus.stock.gatrader.biz.Candle;
import com.nus.stock.gatrader.biz.Decision;
import com.nus.stock.gatrader.biz.Indicator;


public final class DecisionTree implements Serializable{
	private static final long serialVersionUID = 1574482810691091070L;
	private int nodeCount;
    private Node root;
    
    public DecisionTree(){
        this(Indicator.values().length+1);
    }
    
    public DecisionTree(int nodeCount){
        this.nodeCount = nodeCount;
        root = IndicatorNode.getRandomNode();
        for (int i=0; i < nodeCount-1; i++) {
            insertRandomIndicatorNode();
        }
    }
    
    private DecisionTree(int nodeCount, Node root){
        this.nodeCount=nodeCount;
        this.root=root;
    }
    
    public void insertRandomIndicatorNode(){
        Node randomLeaf = getRandomLeafNode();
        IndicatorNode inode = IndicatorNode.getRandomNode();
        if (randomLeaf.parent == null) {
        	System.out.println("mwz insert node");
            if (Math.random() < 0.5)
                root.left = inode;
            else
                root.right = inode;
            inode.parent = root;
        } else {
            if(randomLeaf.parent.left == randomLeaf)
                randomLeaf.parent.left = inode;
            else
                randomLeaf.parent.right = inode;
            inode.parent = randomLeaf.parent;
        }
    }
    
    public void removeRandomIndicatorNode(){
        List<Node> nonleafList = new ArrayList<>();
        getAllNonleafNodes(root, nonleafList);
        List<Node> filter = new ArrayList<>();
        for (Iterator<Node> iterator = nonleafList.iterator(); iterator.hasNext();) {
			Node node = (Node) iterator.next();
			if (node.left instanceof DecisionNode
					&& node.right instanceof DecisionNode) {
				filter.add(node);
			}
		}
        nonleafList = filter;
  
        int index = new Random().nextInt(nonleafList.size());
        Node randomIndicator = nonleafList.get(index);
        if(randomIndicator != null && randomIndicator.parent != null){
            if(randomIndicator.parent.left == randomIndicator)
                randomIndicator.parent.left = DecisionNode.getRandomNode();
            else
                randomIndicator.parent.right = DecisionNode.getRandomNode();
        }
    }
    
    public Node getRandomLeafNode(){
        Node node = root;
        if (node.isLeaf()) {
            return node;
        }
        Node parent;
        while (!node.isLeaf()) {
            parent = node;
            node = Math.random()<0.5 ? node.left : node.right;
            node.parent = parent;
        }
        return node;
    }
    
    public Node getRandomNonleafNode(){
        List<Node> nonleafList = new ArrayList<>();
        getAllNonleafNodes(root, nonleafList);
        
        if (nonleafList.isEmpty())
            return null;
        if (nonleafList.size()==1)
            return nonleafList.get(0);
        int randomIndex = new Random().nextInt(nonleafList.size());

        if (randomIndex == 0)
            randomIndex++;
        return nonleafList.get(randomIndex);
    }
    
    private void getAllNonleafNodes(Node root, List<Node> nonleafList){
        if (!root.isLeaf()){
            nonleafList.add(root);
            getAllNonleafNodes(root.left, nonleafList);
            getAllNonleafNodes(root.right, nonleafList);
        }
    }
    
    public Decision evaluate(Candle c) {
        return root.evaluate(c);
    }
    
    public void mutateRandomDecisionNode(){
        Node decision = getRandomLeafNode();
        ((DecisionNode) decision).mutateDecision();
    }
    
    public void mutateRandomDIndicatorNode(){
        Node indicator = getRandomNonleafNode();
        ((IndicatorNode)indicator).mutateIndicator();
    }
    
    public void mutateRandomDIndicatorNodeValue(){
        Node indicator = getRandomNonleafNode();
        ((IndicatorNode)indicator).mutateIndicatorValue();
    }
    
    public Node getRoot(){
        return this.root;
    }
    
    @Override
    public DecisionTree clone(){
        return new DecisionTree(this.nodeCount,this.root);
    }
    
    @Override
    public String toString(){
        String tree = "";
        
        return tree;
    }
}
