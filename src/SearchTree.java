import java.util.ArrayList;
import java.util.List;
import java.util.Stack;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SearchTree {
	
	Node root;
	String packageName = "";
	ArrayList<String> exploredStateNames = new ArrayList<String>();
	Stack<Node> frontierStates = new Stack<Node>();
	
	public SearchTree(String name, int childNum, String packageName) {
		this.packageName=packageName;
		root = new Node(name, childNum, null);
		System.out.println(root.stateName);
		frontierStates.add(root);
		
	}
	
	
	//for specifying if the node has already been found
	public boolean isExploredNode(String name) {
		/*
		if(exploredStateNames.contains(name)) {
			return true;
		}*/
		for(String s: exploredStateNames) {
			if(Utilities.isSimilar(s, name)) {
				return true;
			}
		}
		
		return false;
	}
	
	public void addExplored(String name) {
		this.exploredStateNames.add(name);
	}
	
	
	
	public boolean isFrontierEmpty() {
		return frontierStates.empty();
	}
	
	public Node popFrontier() {
		return frontierStates.pop();
	}
	
	public void pushFrontier(Node n) {
		this.frontierStates.push(n);
	}
	
	public Node getRoot() {
		return root;
	}
	
	public String getPackageName() {
		return packageName;
	}
	
	
		

	
	
}

