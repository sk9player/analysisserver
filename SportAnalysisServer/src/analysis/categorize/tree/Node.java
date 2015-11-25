package analysis.categorize.tree;

import java.util.HashMap;


public class Node {
	String name = new String();
	Node parentNode;
	Node[] childNodes = new Node[2];
	public Node(String name){
		this.name = name;
	}
	
	public String fork(double[][] data){
		return name;
		
	}
	public String fork(double[][] data,String name){
		return name;
	}
	public void addchildNodes(Node node1,Node node2){
		childNodes[0] = node1;
		childNodes[1] = node2;
	}
	public String deleteNode(String name){
		return name;		
	}
	public void insertchildNode(Node node){
		
	}
	public String print(String name){
		return"";
	}
	public void normarize(String name){
		
	}
	private boolean check(String name){
		if (this.name == name){
			return true;
		}
		return false;
	}
	

}
